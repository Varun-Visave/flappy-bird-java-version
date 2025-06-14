package application.FlappyBird;

import java.io.IOException;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;	
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

public class App extends Application {
    private Scene scene;
    private Timeline flapTimeline;
    private PrimaryController gameController;
//    switch for bird movement
    static boolean birdIsOn = true;
    
//    The  max values the pipes should have are 430 460 for bottom pipe

    @Override
    public void start(@SuppressWarnings("exports") Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("primary.fxml"));
        Parent root = fxmlLoader.load();
        gameController = fxmlLoader.getController();

        
        scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Flappy Bird");
        stage.initStyle(StageStyle.TRANSPARENT);
        
        // Set up a Timeline for smooth flap control
        flapTimeline = new Timeline(new KeyFrame(Duration.millis(5), event -> {
        }));
        flapTimeline.setCycleCount(16);

        scene.addEventHandler(KeyEvent.KEY_PRESSED, event -> {
        	if(birdIsOn) {
            if (event.getCode() == KeyCode.SPACE) {
                flapTimeline.stop(); 
                flapTimeline.getKeyFrames().clear();
                KeyFrame k1 = new KeyFrame(Duration.millis(10), e -> {
                    gameController.getBird().setY(gameController.getBird().getY() - gameController.opposingGravity);
                    gameController.getBird().setRotate(-15);
                });
                flapTimeline.getKeyFrames().add(k1);
                flapTimeline.play(); 
                }
//                System.out.println(gameController.getBird().getY());
            }
        });

        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
