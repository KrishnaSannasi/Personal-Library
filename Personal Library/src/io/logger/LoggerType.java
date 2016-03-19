package io.logger;

import java.awt.Color;

import javax.swing.text.Style;
import javax.swing.text.StyleConstants;

public enum LoggerType {
	NORMAL(Color.WHITE) ,
	CREATE("CREATE" , Color.GREEN) ,
	WARNING("WARNING" , Color.YELLOW) ,
	IMPORTANT("IMPORTANT" , Color.ORANGE) ,
	ERROR("ERROR" , Color.RED),;
	public final String text;
	
	private LoggerType(Color color) {
		this.text = "";
		setupStyle(color);
	}
	
	private LoggerType(String text , Color color) {
		this.text = " " + text;
		setupStyle(color);
	}
	
	private void setupStyle(Color color) {
		Logger.LOGGER.pane.addStyle(text , null);
		Style style = Logger.LOGGER.pane.getStyle(text);
		StyleConstants.setForeground(style , color);
		StyleConstants.setFontSize(style , Logger.FONT_SIZE);
		StyleConstants.setFontFamily(style , StyleOptions.FONT);
		
		StyleConstants.setBold(style , false);
		StyleConstants.setItalic(style , false);
		StyleConstants.setStrikeThrough(style , false);
		StyleConstants.setUnderline(style , false);
	}
}
