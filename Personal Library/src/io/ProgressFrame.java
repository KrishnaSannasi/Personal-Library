package io;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JProgressBar;

public class ProgressFrame {
    public static boolean       useGradient;
    private static JProgressBar progressBar;
    private static JFrame       frame;
    private static Color        foreGround;
    private static float        mr , mg , mb , br , bg , bb;
    
    static {
        init();
    }
    
    public static void setMax(int max) {
        progressBar.setMaximum(max);
    }
    
    public static void setMin(int min) {
        progressBar.setMinimum(min);
    }
    
    public static void show() {
        frame.setVisible(true);
    }
    
    public static void hide() {
        frame.setVisible(false);
    }
    
    public static void setForeground(int r , int g , int b) {
        if(isBadDist(r , g , b , progressBar.getBackground()))
            throw new IllegalArgumentException("Foreground and Background would be too similar");
        br = progressBar.getBackground().getRed() / 256f;
        bg = progressBar.getBackground().getGreen() / 256f;
        bb = progressBar.getBackground().getBlue() / 256f;
        mr = r / 256f - br;
        mg = g / 256f - bg;
        mb = b / 256f - bb;
        
        progressBar.setForeground(new Color(r , g , b));
        foreGround = progressBar.getForeground();
    }
    
    public static void setBackground(int r , int g , int b) {
        if(isBadDist(r , g , b , progressBar.getForeground()))
            throw new IllegalArgumentException("Foreground and Background would be too similar");
        br = r / 256f;
        bg = g / 256f;
        bb = b / 256f;
        mr = progressBar.getForeground().getRed() / 256f - br;
        mg = progressBar.getForeground().getGreen() / 256f - bg;
        mb = progressBar.getForeground().getBlue() / 256f - bb;
        
        progressBar.setBackground(new Color(r , g , b));
    }
    
    private static boolean isBadDist(int r , int g , int b , Color color) {
        double dist = Math.sqrt(Math.pow(r - color.getRed() , 2) + Math.pow(g - color.getGreen() , 2) + Math.pow(b - color.getBlue() , 2));
        return (useGradient ? 75 : 30) > dist;
    }
    
    public static void increment(int i) {
        update(progressBar.getValue() + i);
    }
    
    public static void update(int i) {
        if(i >= progressBar.getMinimum() && i <= progressBar.getMaximum()) {
            int diff = progressBar.getMaximum() - progressBar.getMinimum();
            progressBar.setValue(i);
            Color c;
            if(useGradient)
                c = new Color(mr * i / diff + br , mg * i / diff + bg , mb * i / diff + bb);
            else
                c = foreGround;
            progressBar.setForeground(c);
        }
    }
    
    private static void init() {
        progressBar = new JProgressBar();
        progressBar.setBorderPainted(false);
        progressBar.setPreferredSize(new Dimension(250 , 50));
        
        frame = new JFrame();
        frame.add(progressBar);
        frame.pack();
        
        progressBar.setBackground(Color.WHITE);
        foreGround = progressBar.getForeground();
    }
}
