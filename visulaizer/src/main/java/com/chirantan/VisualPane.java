package com.chirantan;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.control.Label; 
import javafx.util.Duration;

import java.util.Random;

public class VisualPane extends Pane {
    private final int arraySize = 50;
    private final int[] array = new int[arraySize];
    private final Rectangle[] bars = new Rectangle[arraySize];
    private Timeline timeline; 
    private Timeline updateTimeline; 
    private int comparisonCount; 
    private int arrayAccessCount; 

    private final Label comparisonLabel = new Label("Comparisons: 0");
    private final Label accessLabel = new Label("Array Accesses: 0");

    public VisualPane() {
        generateArray();
        setupLabels(); 
        resetCounters(); 
        setBackgroundImage(); // Set background image

        widthProperty().addListener((obs, oldVal, newVal) -> drawArray());
        heightProperty().addListener((obs, oldVal, newVal) -> drawArray());
    }

    private void generateArray() {
        Random rand = new Random();
        for (int i = 0; i < arraySize; i++) {
            array[i] = rand.nextInt(400) + 50;
        }
        drawArray(); 
    }

    private void drawArray() {
        getChildren().clear(); // Clear existing bars and labels
    
        double width = getWidth() / arraySize; // Calculate new bar width based on pane width
        for (int i = 0; i < arraySize; i++) {
            bars[i] = new Rectangle(i * width, getHeight() - array[i], width - 2, array[i]);
            bars[i].setFill(Color.SKYBLUE); // Use named color instead of RGB
            this.getChildren().add(bars[i]);
        }
    
        // Re-add labels
        getChildren().add(comparisonLabel);
        getChildren().add(accessLabel);
        updateLabels();
    }
    

    private void setBackgroundImage() {
        String imageUrl = getClass().getResource("/1533419.jpg").toExternalForm();
        setStyle("-fx-background-image: url('" + imageUrl + "'); " +
                  "-fx-background-size: cover; " +
                  "-fx-background-repeat: no-repeat; " +
                  "-fx-background-position: center;");
    }

    private void setupLabels() {
        comparisonLabel.setLayoutX(10);
        comparisonLabel.setLayoutY(10);
        accessLabel.setLayoutX(10);
        accessLabel.setLayoutY(30);
        comparisonLabel.setTextFill(Color.BLACK);
        accessLabel.setTextFill(Color.BLACK);
        comparisonLabel.setStyle("-fx-font-size: 25;");
        accessLabel.setStyle("-fx-font-size: 25;");
    }

    private void updateLabels() {
        comparisonLabel.setText("Comparisons: " + comparisonCount);
        accessLabel.setText("Array Accesses: " + arrayAccessCount);
    }

    private void swap(int i, int j) {
        int temp = array[i];
        array[i] = array[j];
        array[j] = temp;

        bars[i].setHeight(array[i]);
        bars[i].setY(getHeight() - array[i]);
        bars[j].setHeight(array[j]);
        bars[j].setY(getHeight() - array[j]);
    }

    private void startUpdateTimeline() {
        updateTimeline = new Timeline(new KeyFrame(Duration.millis(100), e -> updateLabels()));
        updateTimeline.setCycleCount(Timeline.INDEFINITE);
        updateTimeline.play();
    }

    private void stopUpdateTimeline() {
        if (updateTimeline != null) {
            updateTimeline.stop();
        }
    }

    public void startBubbleSort() {
        stopSorting();
        resetCounters();
        startUpdateTimeline();

        timeline = new Timeline();
        for (int i = 0; i < array.length - 1; i++) {
            for (int j = 0; j < array.length - i - 1; j++) {
                int finalJ = j;
                KeyFrame frame = new KeyFrame(Duration.millis(100*i), e -> {
                    comparisonCount++;
                    arrayAccessCount += 2; 
                    if (array[finalJ] > array[finalJ + 1]) {
                        swap(finalJ, finalJ + 1);
                    }
                });
                timeline.getKeyFrames().add(frame);
            }
        }

        timeline.setOnFinished(e -> stopUpdateTimeline());
        timeline.play();
    }

    public void startInsertionSort() {
        stopSorting();
        resetCounters();
        startUpdateTimeline();

        timeline = new Timeline();
        for (int i = 1; i < array.length; i++) {
            int finalI = i;
            KeyFrame frame = new KeyFrame(Duration.millis(100 * i), e -> {
                int key = array[finalI];
                int j = finalI - 1;

                arrayAccessCount += 2;

                while (j >= 0 && array[j] > key) {
                    comparisonCount++;
                    arrayAccessCount++;
                    array[j + 1] = array[j];
                    bars[j + 1].setHeight(array[j]);
                    bars[j + 1].setY(getHeight() - array[j]);
                    j--;
                }
                array[j + 1] = key;
                arrayAccessCount++;
                bars[j + 1].setHeight(key);
                bars[j + 1].setY(getHeight() - key);
            });
            timeline.getKeyFrames().add(frame);
        }

        timeline.setOnFinished(e -> stopUpdateTimeline());
        timeline.play();
    }

    public void startSelectionSort() {
        stopSorting();
        resetCounters();
        startUpdateTimeline();

        timeline = new Timeline();
        for (int i = 0; i < array.length - 1; i++) {
            int finalI = i;
            KeyFrame frame = new KeyFrame(Duration.millis(100 * i), e -> {
                int minIndex = finalI;
                for (int j = finalI + 1; j < array.length; j++) {
                    comparisonCount++;
                    arrayAccessCount++;
                    if (array[j] < array[minIndex]) {
                        minIndex = j;
                    }
                }
                swap(finalI, minIndex);
            });
            timeline.getKeyFrames().add(frame);
        }

        timeline.setOnFinished(e -> stopUpdateTimeline());
        timeline.play();
    }

    public void stopSorting() {
        if (timeline != null) {
            timeline.stop();
        }
        stopUpdateTimeline();
    }

    public void resetArray() {
        generateArray(); 
        resetCounters(); 
        stopUpdateTimeline(); 
    }

    private void resetCounters() {
        comparisonCount = 0;
        arrayAccessCount = 0;
        updateLabels(); 
    }
}
