package studio.lineage2.cms.model;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.text.SimpleDateFormat;
import java.util.TimeZone;

/**
 Eanseen
 27.05.2016
 */
@Entity @Table(name = "info") public class Info
{
	@Id @GeneratedValue(generator = "increment") @GenericGenerator(name = "increment", strategy = "increment") @Column(name = "id") private @Getter long id;
	@Column(name = "title") private @Getter @Setter String title;
	@Column(name = "content", length = 100000) private @Getter @Setter String content;
	@Column(name = "image", length = 100000) private @Getter @Setter byte[] image;
	@Column(name = "link") private @Getter @Setter String link;
	@Column(name = "date") private @Getter @Setter long date;

	public String getDateS()
	{
		SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy HH:mm");
		format.setTimeZone(TimeZone.getTimeZone("GMT+3"));
		return format.format(date);
	}
}