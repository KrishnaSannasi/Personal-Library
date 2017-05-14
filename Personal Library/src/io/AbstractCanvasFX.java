package io;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.value.WritableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.Pane;
import javafx.util.Duration;

public abstract class AbstractCanvasFX extends Pane implements WritableValue<AbstractCanvasFX> {
    private GraphicsContext   g;
    private Canvas            canvas;
    private volatile Timeline timeline;
    
    private int  width , height;
    private long lastTime;
    
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
        
        timeline = new Timeline();
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.setAutoReverse(false);
        KeyFrame kf = new KeyFrame(Duration.seconds(1 / targetUPS) , new EventHandler<ActionEvent>() {
            public void handle(ActionEvent ae) {
                long currentNanoTime = System.nanoTime();
                double dt = (currentNanoTime - lastTime) / 1e9;
                lastTime = currentNanoTime;
                
                if(dt * targetUPS < .1)
                    return;
                
                tick(dt);
                g.save();
                render(g);
                g.restore();
            }
        });
        
        timeline.getKeyFrames().add(kf);
        timeline.play();
    }
    
    public void stop() {
        timeline.stop();
        cleanup();
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
    
    public abstract void tick(double deltaT);
    
    public abstract void render(GraphicsContext g);
}
