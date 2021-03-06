package io;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class CanvasPanel extends JPanel {
    private AbstractCanvas          canvas;
    public final GridBagConstraints c;
    
    public CanvasPanel() {
        this(null);
    }
    
    public CanvasPanel(AbstractCanvas initialCanvas) {
        this.canvas = initialCanvas;
        c = new GridBagConstraints();
        if(initialCanvas != null) {
            setCanvas(initialCanvas , true);
        }
        setBackground(Color.WHITE);
        setLayout(new GridBagLayout());
        setVisible(true);
    }
    
    public void start() {
        setVisible(true);
        canvas.start();
    }
    
    public AbstractCanvas getCanvas() {
        return canvas;
    }
    
    public void setCanvas(AbstractCanvas canvas) {
        setCanvas(canvas , false);
    }
    
    public void setCanvas(AbstractCanvas canvas , boolean start) {
        if(this.canvas != null) {
            this.canvas.stop();
            remove(this.canvas);
        }
        this.canvas = canvas;
        add(canvas);
        if(start)
            canvas.start();
    }
    
    public static JFrame createFrame(CanvasPanel panel) {
        JFrame frame = new JFrame();
        
        frame.add(panel);
        frame.setResizable(false);
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        panel.canvas.frame = frame;
        
        return frame;
    }
}
