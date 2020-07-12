package com.jerzy.desktop;

import com.jerzy.printer.Printer;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileNotFoundException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Scanner;

public class FlashcardApp extends Application {
    private String mainDirectory = null;
    private String inputFilename = null;
    private String outputFileDirectory = null;
    private TextField outputFilenameTextField;
    private String outputFileExtension = ".pdf";

    private Printer printer = new Printer();

    public static void main(String[] args) {
        Application.launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("My First JavaFX App");
        readConfig();

        HBox inputFileBox = createFileInputBox(primaryStage, "Select Input File");
        HBox outputDirectoryBox = createDirectoryOutputBox(primaryStage, "Select Output Directory");
        HBox outputFilenameBox = createOutputFilenameBox();
        VBox actionButtonBox = createActionButtonBox();

        VBox vBox = new VBox(inputFileBox, outputDirectoryBox, outputFilenameBox, actionButtonBox);
        VBox.setMargin(inputFileBox, new Insets(0, 0, 4, 0));
        VBox.setMargin(outputDirectoryBox, new Insets(0, 0, 4, 0));
        VBox.setMargin(outputFilenameBox, new Insets(0, 0, 4, 0));

        VBox mainVBox = new VBox(vBox);
        VBox.setMargin(vBox, new Insets(20, 20, 20, 20));
        Scene scene = new Scene(mainVBox, 600, 160);

        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void readConfig() {
        try {
            File myObj = new File("src/resource/config");
            Scanner myReader = new Scanner(myObj);
            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                if (data.contains("mainDirectory: ")) {
                    String value = data.substring(data.indexOf(":") + 2);
                    if (Files.exists(Paths.get(value))) {
                        mainDirectory = value;
                    } else {
                        System.out.println("config incorrect, main directory does not exist");
                    }
                }
            }
            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    private HBox createFileInputBox(Stage primaryStage, String buttonLabel) {
        Button button = new Button(buttonLabel);
        FileChooser fileChooser = new FileChooser();
        if (mainDirectory != null) {
            fileChooser.setInitialDirectory(new File(mainDirectory));
        }
        Label label = new Label("Select input file");
        button.setOnAction(e -> {
            File selectedFile = fileChooser.showOpenDialog(primaryStage);
            if (selectedFile != null) {
                inputFilename = selectedFile.getAbsolutePath();
                label.setText("Selected:        " + inputFilename);
            }
        });
        Region region = new Region();
        HBox.setHgrow(region, Priority.ALWAYS);
//        hbox.setBackground(new Background(new BackgroundFill(Paint.valueOf("#0000FF"), null, null)));
        return new HBox(label, region, button);
    }

    private HBox createDirectoryOutputBox(Stage primaryStage, String buttonLabel) {
        Button button = new Button(buttonLabel);
        DirectoryChooser directoryChooser = new DirectoryChooser();
        if (mainDirectory != null) {
            directoryChooser.setInitialDirectory(new File(mainDirectory));
        }
        Label label = new Label("Select output directory");
        button.setOnAction(e -> {
            File selectedDirectory = directoryChooser.showDialog(primaryStage);
            if (selectedDirectory != null) {
                outputFileDirectory = selectedDirectory.getAbsolutePath();
                label.setText("Selected:        " + outputFileDirectory);
            }
        });
        Region region = new Region();
        HBox.setHgrow(region, Priority.ALWAYS);

        return new HBox(label, region, button);
    }

    private HBox createOutputFilenameBox() {
        Label label = new Label("Select output file name");
        outputFilenameTextField = new TextField();
        outputFilenameTextField.setText("flashcards");
        Label outputFileExtensionLabel = new Label(outputFileExtension);
        Region region = new Region();
        HBox.setHgrow(region, Priority.ALWAYS);
        return new HBox(label, region, outputFilenameTextField, outputFileExtensionLabel);
    }

    private VBox createActionButtonBox() {
        Button button = new Button("parse and print");
        Label resultLabel = new Label("");
        button.setOnAction(e -> {
            if (inputFilename != null && outputFileDirectory != null && outputFilenameTextField.getText() != null) {
                boolean success = printer.parseAndPrintFlashcards(inputFilename, outputFileDirectory + "\\" + outputFilenameTextField.getText() + outputFileExtension);
                resultLabel.setText("success!!!");
            }
        });
        VBox vbox = new VBox(button, resultLabel);
        vbox.setAlignment(Pos.CENTER);
        return vbox;
    }
}
