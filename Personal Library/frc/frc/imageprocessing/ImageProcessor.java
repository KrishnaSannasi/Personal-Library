package frc.imageprocessing;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import io.AbstractCanvas;
import io.CanvasPanel;

public class ImageProcessor {
	private static final int		MASK	= 0x40 , WIDTH = 640 , HEIGHT = WIDTH * 480 / 640;
	private static final double	SCALE	= Math.sqrt(WIDTH * HEIGHT / 307200.);
	private BufferedImage			originalImage , maskedImage , processedImage;
	private ArrayList<Vector>		vertical , horizontal , distances;
	private ArrayList<Point>		allPoints;
	
	public static double getScale() {
		return SCALE;
	}
	
	public static BufferedImage toBufferedImage(Image img) {
		BufferedImage bimage = new BufferedImage(WIDTH , HEIGHT , BufferedImage.TYPE_INT_ARGB);
		Graphics2D bGr = bimage.createGraphics();
		bGr.drawImage(img , 0 , 0 , WIDTH , HEIGHT , 0 , 0 , img.getWidth(null) , img.getHeight(null) , null);
		bGr.dispose();
		return bimage;
	}
	
	public ImageProcessor(Image image) {
		this.originalImage = toBufferedImage(image);
		vertical = new ArrayList<>();
		horizontal = new ArrayList<>();
		distances = new ArrayList<>();
		allPoints = new ArrayList<>();
	}
	
	public void applyMask() {
		int width = originalImage.getWidth(null);
		int height = originalImage.getHeight(null);
		maskedImage = new BufferedImage(width , height , BufferedImage.TYPE_INT_RGB);
		Graphics2D graphics = maskedImage.createGraphics();
		int r , g , b , h , v , pixel;
		for(int x = 0; x < width; x++) {
			for(int y = 0; y < height; y++) {
				pixel = originalImage.getRGB(x , y);
				r = (pixel & 0x00FF0000) >> 16;
				g = (pixel & 0x0000FF00) >> 8;
				b = (pixel & 0x000000FF);
				h = (int) (0xFF * RGBtoHue(r , g , b));
				v = (int) (Math.max(Math.max(r , g) , b));
				h = h & MASK;
				if(v > 0x80)
					v = 0xFF;
				else
					v = 0;
				if(h < 0x10)
					v = 0;
				graphics.setColor(v == 0 ? Color.BLACK : Color.WHITE);
				graphics.fillRect(x , y , 1 , 1);
			}
		}
	}
	
	public void process() {
		applyMask();
		Point p;
		allPoints.clear();
		horizontal.clear();
		vertical.clear();
		ArrayList<Point> points = new ArrayList<>();
		int width = originalImage.getWidth(null);
		int height = originalImage.getHeight(null);
		processedImage = new BufferedImage(width , height , BufferedImage.TYPE_INT_RGB);
		Graphics2D graphics = processedImage.createGraphics();
		graphics.drawImage(maskedImage , 0 , 0 , null);
		for(int x = 1; x < width - 1; x++) {
			for(int y = 1; y < height - 1; y++) {
				double w = .8;
				int avg = (int) (w * getAverage(maskedImage , x , y) + (1 - w) * getAverage(processedImage , x , y));
				boolean test = avg > 0x00FFFFFF / 8;
				if(test) {
					p = new Point(x , y);
					points.add(p);
					allPoints.add(p);
				}
				graphics.setColor(test ? Color.WHITE : Color.BLACK);
				graphics.drawRect(x , y , 1 , 1);
			}
		}
		System.out.println(points.size());
		int mod = points.size() / 150;
		for(int i = 0; i < points.size(); i++) {
			if(i % mod == 0) {
				points.remove(i);
			}
		}
		Vector h = new Vector(1 , 0);
		Vector v = new Vector(0 , 1);
		graphics.setColor(Color.RED);
		for(int i = points.size() - 1; i >= 0; i--) {
			for(int j = i - 1; j >= 0; j--) {
				Vector c = new Vector(points.get(i) , points.get(j));
				if(c.getMagnitude() > 50) {
					double a;
					a = Vector.angleBetween(c , h) * 180 / Math.PI;
					if(Math.abs(a) < 1) {
						horizontal.add(c);
						v.draw(graphics);
					}
					a = Vector.angleBetween(c , v) * 180 / Math.PI;
					if(Math.abs(a) < 1) {
						vertical.add(c);
						v.draw(graphics);
					}
				}
			}
		}
		System.out.println(horizontal.size());
		System.out.println(vertical.size());
	}
	
	/*
	public void genBoxes() {
		applyMask();
		allPoints.clear();
		int width = originalImage.getWidth(null);
		int height = originalImage.getHeight(null);
		boxedImage = new BufferedImage(width , height , BufferedImage.TYPE_INT_RGB);
		Graphics2D graphics = boxedImage.createGraphics();
		graphics.drawImage(maskedImage , 0 , 0 , null);
		printTime("gb");
		for(int x = 1; x < width - 1; x++) {
			for(int y = 1; y < height - 1; y++) {
				double w = .8;
				int avg = (int) (w * getAverage(maskedImage , x , y) + (1 - w) * getAverage(boxedImage , x , y));
				boolean test = avg > 0x00FFFFFF / 8;
				if(test) {
					allPoints.add(new Point(x , y));
				}
				graphics.setColor(test ? Color.WHITE : Color.BLACK);
				graphics.drawLine(x , y , x , y);
			}
		}
		printTime("l1");
		ArrayList<Point> points = new ArrayList<>();
		int mod = allPoints.size() / 150;
		for(int i = 0; i < allPoints.size(); i++) {
			if(i % mod == 0)
				points.add(allPoints.get(i));
		}
		horizontal.clear();
		vertical.clear();
		printTime("filter");
		final Vector h = new Vector(1 , 0);
		final Vector v = new Vector(0 , 1);
		for(int i = 0; i < allPoints.size(); i++) {
			Point point = allPoints.get(i);
			for(int j = i + 1; j < allPoints.size(); j++) {
				Vector vector = new Vector(point , allPoints.get(j));
				double tol = 10;
				double a = h.getAngleDegree(vector);
				if((a - 90 < tol || a - 90 > -tol) && vector.getMagnitude() >= 25 * SCALE) {
					vertical.add(vector);
					graphics.setColor(Color.RED);
	//					vector.draw(graphics);
				}
				tol = 5;
				a = (a + 90) % 360;
				if((a - 60 < tol || a - 60 > -tol) && vector.getMagnitude() < 100 * SCALE && vector.getMagnitude() > 25 * SCALE) {
					horizontal.add(vector);
					graphics.setColor(Color.CYAN);
	//					vector.draw(graphics);
				}
			}
		}
		printTime("gv");
	}
	*/
	public Point findCenter() {
//		genBoxes();
		process();
		double x = 0 , y = 0;
		Vector h = null , v1 = null , v2 = null;
		for(Vector vector: horizontal) {
			if(h == null || vector.getMagnitude() > h.getMagnitude())
				h = vector;
		}
		Point hm = h.getMiddle();
		for(Vector vector: vertical) {
			if(v1 == null || vector.getMagnitude() > v1.getMagnitude())
				if(hm.distance(vector.getMiddle()) < 120 * SCALE) {
					v1 = vector;
				}
		}
		Point pv1 = v1.getMiddle();
		double d = h.getMagnitude() , v2mag = 0;
		for(Vector vector: vertical) {
			if(v2 == null || vector.getMagnitude() > v2mag)
				if(pv1.distance(vector.getMiddle()) > d && hm.distance(vector.getMiddle()) < 120 * SCALE) {
					v2 = vector;
					v2mag = v2.getMagnitude();
				}
		}
		vertical.clear();
		horizontal.clear();
		horizontal.add(h);
		vertical.add(v1);
		vertical.add(v2);
		System.out.println(h);
		System.out.println(v1);
		System.out.println(v2);
		x = v1.getMiddle().getX() + v2.getMiddle().getX();
		y = v1.getMiddle().getY() + v2.getMiddle().getY();
		return new Point(x / 2 , y / 2);
	}
	
	public final void getDistanceLines(Graphics g) {
		findCenter();
		Vector side1 = vertical.get(0) , side2 = vertical.get(1);
		for(int i = 0; i < allPoints.size(); i++) {
			for(int j = i + 1; j < allPoints.size(); j++) {
				Vector v = new Vector(allPoints.get(i) , allPoints.get(j));
				Point middle = v.getMiddle();
				if(side1.getMagnitude() < v.getMagnitude() && middle.distance(vertical.get(0).getMiddle()) < 20 * SCALE)
					side1 = v;
				if(side2.getMagnitude() < v.getMagnitude() && middle.distance(vertical.get(1).getMiddle()) < 20 * SCALE)
					side2 = v;
			}
		}
		distances.add(side1);
		distances.add(side2);
		g.setColor(Color.ORANGE);
		distances.forEach(e -> {
			System.out.println("D>" + e.getMagnitude());
			e.draw(g);
		});
	}
	
	private int getAverage(BufferedImage image , int x , int y) {
		int sum = 0;
		for(int dx = -1; dx <= 1; dx++) {
			for(int dy = -1; dy <= 1; dy++) {
				if(dx == 0 && dy == 0)
					continue;
				sum += image.getRGB(x - dx , y - dy) & 0x00FFFFFF;
			}
		}
		return sum / 8;
	}
	
	public Image getOriginalImage() {
		return originalImage;
	}
	
	public Image getMaskedImage() {
		if(maskedImage == null)
			applyMask();
		return maskedImage;
	}
	
	public BufferedImage getBoxedImage() {
		if(processedImage == null)
			process();
		return processedImage;
	}
	
	public static final float RGBtoHue(int r , int g , int b) {
		float fr = r / 255f , fg = g / 255f , fb = b / 255f;
		float cx = Math.max(Math.max(fr , fg) , fb);
		float delta = cx - Math.min(Math.min(fr , fg) , fb);
		float h = 60f;
		if(delta == 0)
			h = 0;
		else if(cx == fr)
			h *= (fg - fb) / delta % 6;
		else if(cx == fg)
			h *= (fb - fr) / delta + 2;
		else if(cx == fb)
			h *= (fr - fg) / delta + 4;
		return h / 360;
	}
	
	static double	last;
	static boolean	flag	= false;
	
	public static void printTime(Object o) {
		printTime(o.toString());
	}
	
	public static void printTime(String s) {
		if(flag)
			System.out.println(s + " " + (System.currentTimeMillis() / 1000. - last));
		last = System.currentTimeMillis() / 1000.;
//		flag = true;
	}
	
	public static void main(String[] args) throws IOException {
		printTime(0);
		printTime(1);
		ImageProcessor imageProcessor = new ImageProcessor(ImageIO.read(new File(args[0])));
		printTime(2);
		Point point = imageProcessor.findCenter();
		printTime(3);
		new CanvasPanel(new AbstractCanvas(1200 , 675) {
			private static final long serialVersionUID = -8207383438441530930L;
			private int next = 0;
			private double scalex , scaley;
			
			{
				this.showFPS = false;
			}
			
			@Override
			public void tick() {
				scalex = (double) getWidth() / imageProcessor.originalImage.getWidth();
				scaley = (double) getHeight() / imageProcessor.originalImage.getHeight();
			}
			
			@Override
			public void render(Graphics g) {
				printTime("REDNER");
				g.clearRect(0 , 0 , getWidth() , getHeight());
				switch(next % 3) {
					case 0:
						g.drawImage(imageProcessor.originalImage , 0 , 0 , getWidth() , getHeight() , null);
						break;
					case 1:
						g.drawImage(imageProcessor.maskedImage , 0 , 0 , getWidth() , getHeight() , null);
						break;
					case 2:
						g.drawImage(imageProcessor.processedImage , 0 , 0 , getWidth() , getHeight() , null);
						break;
				}
				g.setColor(Color.GREEN);
				if(point != null)
					g.fillOval((int) (point.getX() * scalex) - 10 , (int) (point.getY() * scaley) - 10 , 20 , 20);
				imageProcessor.getDistanceLines(imageProcessor.processedImage.createGraphics());
//				printTime();
			}
			
			@Override
			public void mouseClicked(MouseEvent e) {
				next++;
			}
		});
	}
}
