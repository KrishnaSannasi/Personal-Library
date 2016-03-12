package io.logger;

import java.awt.Color;

public class StyleOptions {
	public static final String	FONT	= "Courier New";
	public static final int		PLAIN	= 0 , BOLD = 1 , ITALICS = 2 , UNDERLINE = 4 , STRIKE_THROUGH = 8;
	public String					font;
	public Color					color;
	public boolean					bold , italics , underline , strikethrough;
	
	public StyleOptions() {
		this(FONT);
	}
	
	public StyleOptions(String font) {
		this(font , Color.WHITE);
	}
	
	public StyleOptions(Color color) {
		this(FONT , color);
	}
	
	public StyleOptions(Color color , int mod) {
		this(FONT , color , mod);
	}
	
	public StyleOptions(Color color , boolean bold , boolean italics , boolean underline , boolean strikethrough) {
		this(FONT , color , bold , italics , underline , strikethrough);
	}
	
	public StyleOptions(String font , Color color) {
		this(font , color , 0);
	}
	
	public StyleOptions(String font , Color color , int mod) {
		this(font , color , (mod & BOLD) != 0 , (mod & ITALICS) != 0 , (mod & UNDERLINE) != 0 , (mod & STRIKE_THROUGH) != 0);
	}
	
	public StyleOptions(String font , Color color , boolean bold , boolean italics , boolean underline , boolean strikethrough) {
		this.font = font;
		this.color = color;
		this.bold = bold;
		this.italics = italics;
		this.underline = underline;
		this.strikethrough = strikethrough;
	}
}
