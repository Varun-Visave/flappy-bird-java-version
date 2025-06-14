package application.FlappyBird;

import java.net.URL;
import java.util.Random;
import java.util.ResourceBundle;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Font;
import javafx.util.Duration;

public class PrimaryController implements Initializable {
	@FXML
	private ImageView bird;
	@FXML
	private ImageView background;
	@FXML
	private ImageView background1, exitButton, restartButton;
	@FXML
	private AnchorPane anchorPane;
	Label scoreLabel;
//	public double opposingGravity = 55;
	public double opposingGravity = 6;
	public double gravity = 3.2;
	public int pipeSpeed = 4;
	int scores = 0;
	Random random = new Random();

	// Define the range
	int minT = -250;
	int maxT = -180;

	// Correct ranges for bottom pipe
	int minB = 360;
	int maxB = 420;

	ImageView bottomPipeImage;
	ImageView topPipeImage;
	Image BpipeImage = new Image(getClass().getResource("bottompipe.png").toString());
	Image TpipeImage = new Image(getClass().getResource("toppipe.png").toString());
	Timeline timeline;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		restartButton.setVisible(false);
		restartButton.setOnMouseClicked(event->{
			bird.setX(0);
			bird.setY(0);
			restartButton.setVisible(false);
			anchorPane.getChildren().remove(bottomPipeImage);
			anchorPane.getChildren().remove(topPipeImage);
			App.birdIsOn = true;
			makePipe();
			timeline.play();
		});
		exitButton.setOnMouseClicked(event2 -> {
			Platform.exit();
		});
		makePipe();
		KeyFrame k1 = new KeyFrame(Duration.millis(16.0), event -> {
			if (bottomPipeImage.getX() < -10) {
				// remove the previous children loaded from the anchorpane
				anchorPane.getChildren().remove(scoreLabel);
				scores++;
				scoreLabel.setText("Total Score :  " + Integer.toString(scores));
				anchorPane.getChildren().remove(topPipeImage);
				anchorPane.getChildren().remove(bottomPipeImage);
				makePipe();
				anchorPane.getChildren().add(scoreLabel);
			}
			bottomPipeImage.setX(bottomPipeImage.getX() - pipeSpeed);
			topPipeImage.setX(topPipeImage.getX() - pipeSpeed);

			background.setX(background.getX() - 1);

			if (background.getX() == -450) {
//				System.out.println("this value was hit by background 1");
				background.setX(448.0);
			}
			background1.setX(background1.getX() - 1);
			if (background1.getX() == -900) {
//				System.out.println("this value was hit by background 2");
				background1.setX(-1);
			}
//			if (App.birdIsOn) {
			bird.setY(bird.getY() + gravity);
			if (bird.getRotate() > 20) {
				bird.setRotate(20);
//				}
			} else {
				bird.setRotate(bird.getRotate() + 1.8);
			}
			if (bird.getBoundsInParent().intersects(topPipeImage.getBoundsInParent())
					|| bird.getBoundsInParent().intersects(bottomPipeImage.getBoundsInParent())) {
				restartButton.setVisible(true);
				scores = 0;
				App.birdIsOn = false;
				timeline.stop();
			}
		});
		timeline = new Timeline(k1);
		timeline.setCycleCount(Timeline.INDEFINITE);
		timeline.play();
		scoreLabel = new Label("Total Scores : " + scores);
		scoreLabel.setLayoutX(10);
		scoreLabel.setLayoutY(10);
		scoreLabel.setFont(new Font("Poor Richard", 31));
		anchorPane.getChildren().add(scoreLabel);
	}

	private void makePipe() {
		// Generate random number for top pipe

		int randomNumberForTopPipe = minT + random.nextInt(maxT - minT + 1);

		// Generate random number for bottom pipe
		int randomNumberForBottomPipe = minB + random.nextInt(maxB - minB + 1);
		bottomPipeImage = new ImageView(BpipeImage);
		bottomPipeImage.setX(450);
		bottomPipeImage.setY(randomNumberForBottomPipe);
		bottomPipeImage.setFitWidth(300);
		bottomPipeImage.setFitHeight(450);
		bottomPipeImage.setPreserveRatio(true);
		anchorPane.getChildren().add(bottomPipeImage);

		topPipeImage = new ImageView(TpipeImage);
		topPipeImage.setX(450);
		topPipeImage.setY(randomNumberForTopPipe);
		topPipeImage.setFitWidth(300);
		topPipeImage.setFitHeight(450);
		topPipeImage.setPreserveRatio(true);
		anchorPane.getChildren().add(topPipeImage);
	}

	@SuppressWarnings("exports")
	public ImageView getBird() {
		return bird;
	}

	@SuppressWarnings("exports")
	public AnchorPane getAnchorPane() {
		return anchorPane;
	}

}
