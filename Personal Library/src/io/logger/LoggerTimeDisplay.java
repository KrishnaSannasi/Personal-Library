package io.logger;

public enum LoggerTimeDisplay {
	NANOSECONDS ,
	MILLISECONDS ,
	DATE_MONTH_YEAR("ddMMYYYYkkmmssSSSS" , false) ,
	YEAR_MONTH_DATE("yyyyMMddkkmmssSSSS" , false) ,
	MONTH_DATE_YEAR("MMddyyyykkmmssSSSS" , false) ,
	OTHER;
	private final boolean edittable;
	private String format;
	
	private LoggerTimeDisplay() {
		this("");
	}
	
	private LoggerTimeDisplay(String format) {
		this(format , true);
	}
	
	private LoggerTimeDisplay(String format , boolean edittable) {
		this.format = format;
		this.edittable = edittable;
	}
	
	public String getFormat() {
		return format;
	}
	
	public void setFormat(String format) {
		if(edittable)
			this.format = format;
	}
}
