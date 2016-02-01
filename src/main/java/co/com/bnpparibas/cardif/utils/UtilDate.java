package co.com.bnpparibas.cardif.utils;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import co.com.bnpparibas.cardif.utils.Interval;

public class UtilDate {

	public static Interval getInterval() {
		DateTimeZone timeZone = DateTimeZone.forID("America/Bogota");
		DateTime now = new DateTime(timeZone);
		DateTime firstDay = now.withTimeAtStartOfDay();
		DateTime lastDay = now.plusMonths(1);
		return new Interval(firstDay, lastDay);
	}

}
