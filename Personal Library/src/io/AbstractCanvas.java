package io;

import java.awt.Canvas;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.util.HashMap;

import javax.swing.JFrame;

public abstract class AbstractCanvas extends Canvas implements Runnable , KeyListener , MouseListener , MouseMotionListener , MouseWheelListener {
    private static final long serialVersionUID = -2757654850592996937L;
    
    private volatile HashMap<Integer , Boolean> keyMap;
    public final Dimension                      DIMENTION;
    private BufferStrategy                      strategy;
    public JFrame                               frame;
    public double                               deltaT;
    
    private volatile boolean done;
    public volatile boolean  showFPS   = false , useWASD = true , useARROW = true;
    protected volatile int   targetFPS = 20;
    protected int            SHOW_WIDTH , SHOW_HEIGHT;
    
    public AbstractCanvas(int width , int height) {
        this(width , height , width , height);
    }
    
    public AbstractCanvas(int width , int height , int showWidth , int showHeight) {
        this(null , width , height , showWidth , showHeight , false);
    }
    
    public AbstractCanvas(JFrame frame , int width , int height) {
        this(frame , width , height , width , height);
    }
    
    public AbstractCanvas(JFrame frame , int width , int height , int showWidth , int showHeight) {
        this(frame , width , height , showWidth , showHeight , false);
    }
    
    public AbstractCanvas(JFrame frame , int width , int height , boolean start) {
        this(frame , width , height , width , height , start);
    }
    
    public AbstractCanvas(JFrame frame , int width , int height , int showWidth , int showHeight , boolean start) {
        super();
        keyMap = new HashMap<>();
        this.SHOW_WIDTH = showWidth;
        this.SHOW_HEIGHT = showHeight;
        DIMENTION = new Dimension(width , height);
        setSize(DIMENTION);
        setPreferredSize(DIMENTION);
        setMinimumSize(DIMENTION);
        setMaximumSize(DIMENTION);
        done = true;
        this.frame = frame;
        this.addMouseListener(this);
        this.addMouseMotionListener(this);
        this.addMouseWheelListener(this);
        this.addKeyListener(this);
        this.setFocusable(true);
        this.requestFocus();
        this.setFocusTraversalKeysEnabled(false);
        setIgnoreRepaint(true);
        
        keyMap.put(KeyEvent.VK_UP , false);
        keyMap.put(KeyEvent.VK_DOWN , false);
        keyMap.put(KeyEvent.VK_LEFT , false);
        keyMap.put(KeyEvent.VK_RIGHT , false);
        
        keyMap.put(KeyEvent.VK_W , false);
        keyMap.put(KeyEvent.VK_A , false);
        keyMap.put(KeyEvent.VK_S , false);
        keyMap.put(KeyEvent.VK_D , false);
    }
    
    public AbstractCanvas(Dimension dim) {
        this(dim.width , dim.height);
    }
    
    public abstract void tick();
    
    public abstract void render(Graphics2D g);
    
    public boolean isUpPressed() {
        return useARROW && getKey(KeyEvent.VK_UP) || useWASD && getKey(KeyEvent.VK_W);
    }
    
    public boolean isDownPressed() {
        return useARROW && getKey(KeyEvent.VK_DOWN) || useWASD && getKey(KeyEvent.VK_S);
    }
    
    public boolean isRightPressed() {
        return useARROW && getKey(KeyEvent.VK_RIGHT) || useWASD && getKey(KeyEvent.VK_D);
    }
    
    public boolean isLeftPressed() {
        return useARROW && getKey(KeyEvent.VK_LEFT) || useWASD && getKey(KeyEvent.VK_A);
    }
    
    public boolean getKey(int keyCode) {
        Boolean b = keyMap.get(keyCode);
        return b != null && b == true;
    }
    
    public boolean getKey(KeyEvent event) {
        return getKey(event.getKeyCode());
    }
    
    public void setKey(KeyEvent event , boolean state) {
        keyMap.put(event.getKeyCode() , state);
    }
    
    @Override
    public int getWidth() {
        return DIMENTION.width;
    }
    
    @Override
    public int getHeight() {
        return DIMENTION.height;
    }
    
    public void start() {
        done = false;
        new Thread(this).start();
    }
    
    public void stop() {
        done = true;
    }
    
    public boolean isDone() {
        return done;
    }
    
    @Override
    public final void run() {
        setVisible(true);
        long lastFrameTime = System.nanoTime() , thisFrameTime;
        double fps;
        try {
            createBufferStrategy(2);
        }
        catch (Exception e) {
            return;
        }
        strategy = getBufferStrategy();
        while(!done) {
            BufferedImage image = new BufferedImage(getWidth() , getHeight() , BufferedImage.TYPE_INT_ARGB);
            do {
                thisFrameTime = System.nanoTime();
                fps = 1000000000. / (thisFrameTime - lastFrameTime);
            } while(fps > targetFPS && targetFPS != -1);
            deltaT = 1 / fps;
            lastFrameTime = System.nanoTime();
            if(frame != null && showFPS)
                frame.setTitle(String.format("FPS: %.3f   %d" , fps , targetFPS));
            tick();
            render(image.createGraphics());
            try {
                strategy.getDrawGraphics().drawImage(image , 0 , 0 , getWidth() , getHeight() , 0 , 0 , SHOW_WIDTH , SHOW_HEIGHT , null);
            }
            catch (Exception e) {
            }
            if(!strategy.contentsLost()) {
                strategy.show();
            }
            Thread.yield();
        }
    }
    
    public void clearScreen(Graphics2D g) {
        g.setColor(getBackground());
        g.fillRect(0 , 0 , getWidth() , getHeight());
    }
    
    @Override
    public void mouseDragged(MouseEvent e) {
    }
    
    @Override
    public void mouseMoved(MouseEvent e) {
    }
    
    @Override
    public void mouseClicked(MouseEvent e) {
    }
    
    @Override
    public void mouseEntered(MouseEvent e) {
    }
    
    @Override
    public void mouseExited(MouseEvent e) {
    }
    
    @Override
    public void mousePressed(MouseEvent e) {
    }
    
    @Override
    public void mouseReleased(MouseEvent e) {
    }
    
    @Override
    public void keyPressed(KeyEvent e) {
    }
    
    @Override
    public void keyReleased(KeyEvent e) {
    }
    
    @Override
    public void keyTyped(KeyEvent e) {
    }
    
    @Override
    public void mouseWheelMoved(MouseWheelEvent e) {
    }
}
