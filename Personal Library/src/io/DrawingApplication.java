package io;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class DrawingApplication extends Application {
    public static void launch(String[] args) {
        Application.launch(args);
    }
    
    public static DrawingApplication    app = null;
    public static AbstractCanvasFX canvas;
    
    public DrawingApplication() {
        app = this;
    }
    
    @Override
    public void start(Stage primaryStage) {
        if(canvas == null)
            throw new NullPointerException("Canvas is null you idiot");
        
        Group root = new Group();
        
        root.getChildren().add(canvas);
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
        
        canvas.start();
    }
}
