package io.logger;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GraphicsEnvironment;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultCaret;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;

public class Logger implements AutoCloseable {
	public static final Logger LOGGER = new Logger();
	
	private static final List<Character>	PUNCTUATION_LIST;
	public static final Dimension				BOUNDS	= new Dimension(500 , 600);
	private static final Object				DARKER	= new Object() , BRIGHTER = new Object() , NONE = new Object();
	
	public static final int FONT_SIZE = 15;
	
	public volatile LoggerTimeDisplay timeDisplay;
	
	private volatile HashMap<String , Style>	styleMap;
	private volatile JFrame							frame;
	private volatile JScrollPane					scroll;
	private volatile Boolean						closed;
	volatile JTextPane								pane;
	
	public boolean logToSTD;
	
	static {
		if(!LOGGER.closed) {
			char[] punctuationArray = {'{' , '}' , '(' , ')' , '[' , ']' , '<' , '>' , '.' , ',' , '/' , '\\' , ':' , ';'};
			PUNCTUATION_LIST = new LinkedList<>();
			for(char c: punctuationArray) {
				PUNCTUATION_LIST.add(c);
			}
			JFrame frame = Logger.LOGGER.frame = new JFrame("Logger");
			Logger.LOGGER.timeDisplay = LoggerTimeDisplay.MILLISECONDS;
			Logger.LOGGER.addTo(frame);
			frame.setSize(650 , 650);
			frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
			frame.setVisible(false);
		}
		else {
			PUNCTUATION_LIST = null;
		}
	}
	
	public static synchronized void logLine(String log) {
		LOGGER.log(log);
	}
	
	public static synchronized void logLine(String log , String timeFormat) {
		LOGGER.log(log , timeFormat);
	}
	
	public static synchronized void logLine(LoggerType type , String log) {
		LOGGER.log(type , log);
	}
	
	public static synchronized void logLine(LoggerType type , String log , String timeFormat) {
		LOGGER.log(type , log , timeFormat);
	}
	
	public static synchronized void logBlank() {
		LOGGER.blank();
	}
	
	private Logger() {
		closed = false;
		logToSTD = false;
		styleMap = new HashMap<>();
		pane = new JTextPane();
		pane.setAutoscrolls(true);
		scroll = new JScrollPane(pane , ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS , ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		pane.setPreferredSize(BOUNDS);
		pane.setMinimumSize(BOUNDS);
		pane.setMaximumSize(BOUNDS);
		pane.setEditable(false);
		timeDisplay = LoggerTimeDisplay.NANOSECONDS;
		pane.setBackground(Color.BLACK);
		((DefaultCaret) pane.getCaret()).setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
	}
	
	@Override
	public void close() {
		closed = true;
		if(frame != null) {
			frame.dispose();
		}
		if(styleMap != null) {
			styleMap.clear();
		}
		timeDisplay = null;
		frame = null;
		pane = null;
		scroll = null;
		styleMap = null;
	}
	
	public void hide() {
		frame.setVisible(false);
	}
	
	public void show() {
		frame.setVisible(true);
	}
	
	public void removeAllKeywords() {
		styleMap.clear();
	}
	
	public void removeKeyword(String keyword) {
		styleMap.remove(keyword);
	}
	
	public void addKeyWord(String keyword , StyleOptions options) {
		addKeyWord(keyword , options , 0);
	}
	
	public void addKeyWord(String keyword , StyleOptions options , int tone) {
		if(closed)
			throw new IllegalStateException("Logger closed");
		if(keyword.contains(" "))
			throw new IllegalArgumentException("Invalid keyword (no spaces)");
		if(pane.getBackground().equals(options.color))
			throw new IllegalArgumentException("Invalid color");
		if(!Arrays.asList(GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames()).contains(options.font))
			throw new IllegalArgumentException("Invalid font");
		Style style = pane.addStyle("KEYWORD: " + LoggerType.ERROR.text , null);
		Color c = options.color;
		if(c == null) {
			c = pane.getBackground();
			if(tone > 0)
				style.addAttribute("color.tone" , BRIGHTER);
			else if(tone < 0)
				style.addAttribute("color.tone" , DARKER);
			else
				style.addAttribute("color.tone" , NONE);
		}
		else {
			style.addAttribute("color.tone" , NONE);
			if(tone > 0)
				c = c.brighter();
			else if(tone < 0)
				c = c.darker();
		}
		StyleConstants.setForeground(style , c);
		StyleConstants.setFontSize(style , FONT_SIZE);
		StyleConstants.setFontFamily(style , options.font);
		StyleConstants.setBold(style , options.bold);
		StyleConstants.setItalic(style , options.italics);
		StyleConstants.setStrikeThrough(style , options.strikethrough);
		StyleConstants.setUnderline(style , options.underline);
		styleMap.put(keyword.toLowerCase() , style);
	}
	
	public void addTo(Container container) {
		container.add(scroll , BorderLayout.CENTER);
	}
	
	public synchronized void blank() {
		if(closed)
			throw new IllegalStateException("Logger closed");
		synchronized(closed) {
			final Style defaultStyle = pane.getStyle(LoggerType.NORMAL.text);
			StyledDocument doc = pane.getStyledDocument();
			try {
				doc.insertString(doc.getLength() , "\n" , defaultStyle);
			}
			catch(BadLocationException e) {
			}
		}
	}
	
	public synchronized void log(String log) {
		if(timeDisplay != LoggerTimeDisplay.OTHER)
			log(log , "");
		else
			throw new IllegalArgumentException("Needs time format");
	}
	
	public synchronized void log(String log , String timeFormat) {
		log(LoggerType.NORMAL , log , timeFormat);
	}
	
	public synchronized void log(LoggerType type , String log) {
		if(timeDisplay != LoggerTimeDisplay.OTHER)
			log(type , log , "");
		else
			throw new IllegalArgumentException("Needs time format");
	}
	
	public synchronized void log(LoggerType type , String log , String timeFormat) {
		if(closed)
			throw new IllegalStateException("Logger closed");
		synchronized(closed) {
			final Style defaultStyle = pane.getStyle(type.text);
			
			String timeText;
			switch(timeDisplay) {
				case NANOSECONDS:
					timeText = Long.toString(System.nanoTime() , Character.MAX_RADIX).toUpperCase();
					break;
				case MILLISECONDS:
					timeText = Long.toString(System.currentTimeMillis() , Character.MAX_RADIX).toUpperCase();
					break;
				case DATE_MONTH_YEAR:
				case MONTH_DATE_YEAR:
				case YEAR_MONTH_DATE:
					timeFormat = timeDisplay.getFormat();
				case OTHER:
					timeText = new SimpleDateFormat(timeFormat).format(Calendar.getInstance().getTime());
					break;
				default:
					timeText = "??????????????";
					break;
			}
			String header = String.format("%s> Log: " , timeText.replace(" " , "0"));
			try {
				StyledDocument doc = pane.getStyledDocument();
				doc.insertString(doc.getLength() , header , pane.getStyle(LoggerType.NORMAL.text));
				final Color background = pane.getBackground() , foreground = StyleConstants.getForeground(defaultStyle);
				for(String w: log.split(" ")) {
					String word = w.toLowerCase();
					for(char puncuation: PUNCTUATION_LIST) {
						word = word.replace(puncuation + "" , "");
					}
					if(styleMap.containsKey(word)) {
						Style style = styleMap.get(word);
						if(StyleConstants.getForeground(style).equals(background)) {
							Color color = StyleConstants.getForeground(style);
							Object colorTone = style.getAttribute("color.tone");
							if(colorTone == BRIGHTER)
								StyleConstants.setForeground(style , foreground.brighter());
							else if(colorTone == DARKER)
								StyleConstants.setForeground(style , foreground.darker());
							else
								StyleConstants.setForeground(style , foreground);
							doc.insertString(doc.getLength() , w , style);
							StyleConstants.setForeground(style , color);
						}
						else {
							boolean isPunc = PUNCTUATION_LIST.contains(w.charAt(0));
							String temp = "";
							for(char c: w.toCharArray()) {
								if(isPunc != PUNCTUATION_LIST.contains(c)) {
									doc.insertString(doc.getLength() , temp , isPunc ? defaultStyle : style);
									isPunc = !isPunc;
									temp = "";
								}
								temp += c;
							}
							doc.insertString(doc.getLength() , temp , isPunc ? defaultStyle : style);
						}
					}
					else {
						doc.insertString(doc.getLength() , w , pane.getStyle(type.text));
					}
					doc.insertString(doc.getLength() , " " , defaultStyle);
				}
				doc.insertString(doc.getLength() , "\n" , pane.getStyle(type.text));
				if(logToSTD)
					System.out.print(header + log);
			}
			catch(BadLocationException e) {
				e.printStackTrace();
			}
		}
	}
}
