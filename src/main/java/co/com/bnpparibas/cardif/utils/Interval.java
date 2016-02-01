package co.com.bnpparibas.cardif.utils;

import java.util.Date;

import org.joda.time.DateTime;

public class Interval {

	private Date initDate;
	private Date finalDate;

	public Interval(DateTime t1, DateTime t2) {
		this.initDate = t1.toDate();
		this.finalDate = t2.toDate();
	}

	public Date getInitDate() {
		return initDate;
	}

	public void setInitDate(Date initDate) {
		this.initDate = initDate;
	}

	public Date getFinalDate() {
		return finalDate;
	}

	public void setFinalDate(Date finalDate) {
		this.finalDate = finalDate;
	}

}
