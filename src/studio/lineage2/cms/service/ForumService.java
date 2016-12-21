package studio.lineage2.cms.service;

import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import studio.lineage2.cms.model.Theme;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 Eanseen
 02.06.2016
 */
@Service public class ForumService
{
	@Value("${forum.link}") private String forumLink;
	@Value("${forum.url}") private String forumUrl;
	@Value("${forum.user}") private String forumUser;
	@Value("${forum.password}") private String forumPassword;
	@Value("${forum.tablesPrefix}") private String forumTablesPrefix;
	@Value("${forum.type}") private @Getter String forumType;
	@Value("${forum.topicsCount}") private int forumTopicsCount;

	private HikariDataSource connectionPool;
	private @Getter List<Theme> themes;

	@Scheduled(initialDelay = 1000, fixedRate = 60000)
	public void load()
	{
		if(forumUrl.isEmpty())
		{
			return;
		}

		init();

		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rset = null;

		try
		{
			conn = connectionPool.getConnection();
			switch(forumType)
			{
				case "ipb4":
				{
					stmt = conn.prepareStatement("SELECT topics.tid, topics.title, topics.last_post, topics.last_poster_id, topics.last_poster_name FROM " + forumTablesPrefix + "topics AS topics ORDER BY last_post DESC LIMIT " + forumTopicsCount);
					rset = stmt.executeQuery();
				}
				break;
				case "xenforo":
				{
					stmt = conn.prepareStatement("SELECT thread_id, title, last_post_date, last_post_user_id, last_post_username FROM " + forumTablesPrefix + "xf_thread ORDER BY `xf_thread`.`last_post_date` DESC LIMIT " + forumTopicsCount);
					rset = stmt.executeQuery();
				}
				break;
			}
			if(rset != null)
			{
				themes.clear();
				while(rset.next())
				{
					themes.add(new Theme(rset.getInt(1), rset.getString(2), rset.getInt(3), rset.getInt(4), rset.getString(5)));
				}
			}
		}
		catch(Exception ignored)
		{
		}
		finally
		{
			close(conn, stmt, rset);
		}
	}

	private void init()
	{
		if(connectionPool == null)
		{
			HikariConfig config = new HikariConfig();
			config.setDataSourceClassName(MysqlDataSource.class.getName());
			config.setConnectionTimeout(120000);
			config.setIdleTimeout(600000);
			config.setLeakDetectionThreshold(0);
			config.setMaxLifetime(1800000);
			config.setMaximumPoolSize(50);

			config.addDataSourceProperty("url", forumUrl);
			config.addDataSourceProperty("user", forumUser);
			config.addDataSourceProperty("password", forumPassword);

			connectionPool = new HikariDataSource(config);
		}
		if(themes == null)
		{
			themes = new ArrayList<>();
		}
	}

	private void close(Connection conn, PreparedStatement preparedStatement, ResultSet rset)
	{
		try
		{
			if(rset != null)
			{
				rset.close();
			}
		}
		catch(Exception ignored)
		{
		}
		try
		{
			if(preparedStatement != null)
			{
				preparedStatement.close();
			}
		}
		catch(Exception ignored)
		{
		}
		try
		{
			if(conn != null)
			{
				conn.close();
			}
		}
		catch(Exception ignored)
		{
		}
	}
}