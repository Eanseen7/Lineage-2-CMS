package studio.lineage2.cms.model;

import lombok.Getter;
import lombok.Setter;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.TimeZone;

/**
 Eanseen
 29.10.2015
 */
public class Theme
{
	private @Getter @Setter int topicId;
	private @Getter @Setter String titleFull;
	private @Getter @Setter String title;
	private @Getter @Setter String date;
	private @Getter @Setter int userId;
	private @Getter @Setter String name;

	public Theme(int topicId, String title, int date, int userId, String name)
	{
		SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy HH:mm");
		format.setTimeZone(TimeZone.getTimeZone("GMT+3"));

		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(date * 1000L);

		this.topicId = topicId;
		this.titleFull = title;
		this.title = title.length() > 25 ? title.substring(0, 25) + "..." : title;
		this.date = format.format(calendar.getTime());
		this.userId = userId;
		this.name = name;
	}
}