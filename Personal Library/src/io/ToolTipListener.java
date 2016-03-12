package io;

import java.awt.Color;
import java.awt.Frame;
import java.awt.Label;
import java.awt.Point;
import java.awt.Window;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.SwingUtilities;
import javax.swing.Timer;

public class ToolTipListener implements MouseListener , MouseMotionListener {
	AbstractCanvas	testCanvas;
	Frame				owner;
	Window			tooltip;
	Timer				entryTimer;
	Point				p;
	
	public ToolTipListener(AbstractCanvas ttt , Frame f) {
		testCanvas = ttt;
		owner = f;
		testCanvas.addMouseListener(this);
		testCanvas.addMouseMotionListener(this);
		tooltip = new Window(owner);
		Label label = new Label("" , Label.CENTER);
		label.setBackground(new Color(200 , 220 , 240));
		tooltip.add(label);
		tooltip.pack();
		entryTimer = new Timer(0 , e -> showToolTip(p));
		entryTimer.setRepeats(true);
	}
	
	public void setToolTipText(String s) {
		Label label = (Label) tooltip.getComponent(0);
		label.setText(s);
		tooltip.pack();
	}
	
	public void showToolTip(Point p) {
		SwingUtilities.convertPointToScreen(p , owner);
		tooltip.setLocation(p.x , p.y);
		tooltip.setVisible(true);
	}
	
	private void hideToolTip() {
		tooltip.setVisible(false);
	}
	
	public void mouseEntered(MouseEvent e) {
		entryTimer.start();
	}
	
	public void mouseExited(MouseEvent e) {
		hideToolTip();
	}
	
	public void mousePressed(MouseEvent e) {
		hideToolTip();
		entryTimer.start();
	}
	
	public void mouseMoved(MouseEvent e) {
		p = e.getPoint();
	}
	
	public void mouseReleased(MouseEvent e) {
	}
	
	public void mouseClicked(MouseEvent e) {
	}
	
	public void mouseDragged(MouseEvent e) {
	}
}
