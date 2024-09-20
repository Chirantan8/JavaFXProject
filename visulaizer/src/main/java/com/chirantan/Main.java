package com.chirantan;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class Main extends Application {
    @Override
    public void start(Stage primaryStage) {
        // Create the VisualPane for sorting visualization
        VisualPane visualPane = new VisualPane();

        // Create buttons for different sorting algorithms and reset/stop
        Button bubbleSortButton = new Button("Bubble Sort");
        Button insertionSortButton = new Button("Insertion Sort");
        Button selectionSortButton = new Button("Selection Sort");
       
        Button resetButton = new Button("Reset");
        Button stopButton = new Button("Stop");

        // Set button actions to start the corresponding sorting algorithm
        bubbleSortButton.setOnAction(e -> {
            visualPane.resetArray(); // Reset before sorting
            visualPane.startBubbleSort();
        });
        insertionSortButton.setOnAction(e -> {
            visualPane.resetArray(); // Reset before sorting
            visualPane.startInsertionSort();
        });
        selectionSortButton.setOnAction(e -> {
            visualPane.resetArray(); // Reset before sorting
            visualPane.startSelectionSort();
        });

       
        
        // Set reset button action to reset the array and clear sorting
        resetButton.setOnAction(e -> visualPane.resetArray());

        // Set stop button action to stop the sorting animation
        stopButton.setOnAction(e -> visualPane.stopSorting());

        // Create a horizontal box (HBox) to hold the buttons at the top
        HBox buttonBox = new HBox(10, bubbleSortButton, insertionSortButton, selectionSortButton, resetButton, stopButton);
        buttonBox.setStyle("-fx-padding: 10; -fx-alignment: center;");

        // Create a BorderPane to arrange the visual pane and buttons
        BorderPane root = new BorderPane();
        root.setCenter(visualPane);
        root.setTop(buttonBox); // Buttons are at the top now

        // Set up the scene and stage
        Scene scene = new Scene(root, 800, 600);
        primaryStage.setTitle("Sorting Visualizer");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
