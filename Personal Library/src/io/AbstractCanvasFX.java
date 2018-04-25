package io;

import javafx.animation.AnimationTimer;
import javafx.beans.value.WritableValue;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

public abstract class AbstractCanvasFX extends Pane implements WritableValue<AbstractCanvasFX> {
    private GraphicsContext         g;
    protected Canvas                canvas;
    protected Group                 root;
    private volatile AnimationTimer animation;
    
    private int  width , height;
    
    protected double  targetUPS = 60;
    protected boolean doUpdateKeepUp;
    
    public AbstractCanvasFX(int width , int height) {
        this.width = width;
        this.height = height;
        
        setWidth(width);
        setHeight(height);
        setMaxWidth(width);
        setMaxHeight(height);
        setMinWidth(width);
        setMinHeight(height);
        
    }
    
    public void start() {
        canvas = new Canvas(width , height);
        g = canvas.getGraphicsContext2D();
        
        getChildren().add(canvas);
        
        setup();
        
        final long startTime = System.nanoTime();
        
        animation = new AnimationTimer() {
            long lastTime = startTime;
            
            @Override
            public void handle(long now) {
                double deltaT = (now - lastTime) / 1e9;
                
                tick(deltaT);
                render(g);
                lastTime = now;
                
                if(deltaT * targetUPS < 1) {
                    double slp = 1 / targetUPS - deltaT;
                    long slpm = (int) (slp * 1000);
                    try {
                        Thread.sleep(slpm);
                    }
                    catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
            
            @Override
            public void stop() {
                super.stop();
                cleanup();
            }
        };
        
        animation.start();
    }
    
    public void stop() {
//        timeline.stop();
        animation.stop();
        animation = null;
    }
    
    public void clearScreen() {
        g.clearRect(0 , 0 , width , height);
    }
    
    @Override
    public AbstractCanvasFX getValue() {
        return this;
    }
    
    @Override
    public void setValue(AbstractCanvasFX value) {
        
    }
    
    public void setup() {
        
    }
    
    public void cleanup() {
        
    }
    
    public void setBackground(Color color) {
        setBackground(new Background(new BackgroundFill(color , CornerRadii.EMPTY , Insets.EMPTY)));
    }
    
    public abstract void tick(double deltaT);
    
    public abstract void render(GraphicsContext g);
}
