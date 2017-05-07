package io;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class DrawingApplication extends Application {
    public static void launch(String[] args) {
        AbstractCanvasFX canvasFX = canvas;
        Application.launch(args);
        canvasFX.stop();
    }
    
    public static DrawingApplication app = null;
    public static AbstractCanvasFX   canvas;
    
    public AbstractCanvasFX canvasFX;
    
    public DrawingApplication() {
        app = this;
        canvasFX = canvas;
    }
    
    @Override
    public void start(Stage primaryStage) {
        if(canvasFX == null)
            throw new NullPointerException("Canvas is null you idiot");
        
        Group root = new Group();
        
        root.getChildren().add(canvasFX);
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
        
        canvasFX.start();
    }
}
