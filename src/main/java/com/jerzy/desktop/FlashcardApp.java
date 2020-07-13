package com.jerzy.desktop;

import com.jerzy.printer.Printer;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
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
    public static final String APP_TITLE = "PDF Flashcards Maker";

    public static final String CONFIG_PATH = "src/resource/config";
    public static final String MAIN_DIRECTORY_CONFIG_LABEL = "mainDirectory: ";
    public static final String CONFIG_SPLITTER = ":";
    public static final String BAD_CONFIG_MESSAGE = "config incorrect, main directory does not exist";
    public static final String CONFIG_FILE_NOT_FOUND_MESSAGE = "File not found.";

    public static final String SELECT_INPUT_FILE_LABEL = "Select input file";
    public static final String SELECT_INPUT_FILE_BUTTON_LABEL = "Select Input File";
    public static final String SELECTED_LABEL_PREFIX = "Selected:        ";
    public static final String SELECT_OUTPUT_DIRECTORY_LABEL = "Select output directory";
    public static final String SELECT_OUTPUT_DIRECTORY_BUTTON_LABEL = "Select Output Directory";
    public static final String SELECT_OUTPUT_FILE_NAME_LABEL = "Select output file name";
    public static final String DEFAULT_OUTPUT_FILE_NAME = "flashcards";
    public static final String OUTPUT_FILE_EXTENSION = ".pdf";
    public static final String CHECKBOX_WITH_GRID_TITLE = "pdf with lines";
    public static final String ACTION_BUTTON_LABEL = "parse and print";
    public static final String INITIAL_RESULT_MESSAGE = "";
    public static final String GOOD_RESULT_MESSAGE = "success!!!";
    public static final String BAD_RESULT_MESSAGE = "sadly something went wrong...";

    private final Printer printer = new Printer();

    private String mainDirectory = null;
    private String inputFilename = null;
    private String outputFileDirectory = null;
    private TextField outputFilenameTextField;

    public static void main(String[] args) {
        Application.launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle(APP_TITLE);
        readConfig();

        HBox inputFileBox = createFileInputBox(primaryStage);
        HBox outputDirectoryBox = createDirectoryOutputBox(primaryStage);
        HBox outputFilenameBox = createOutputFilenameBox();
        VBox actionButtonBox = createActionButtonBox();

        VBox vBox = new VBox(inputFileBox, outputDirectoryBox, outputFilenameBox, actionButtonBox);
        VBox.setMargin(inputFileBox, new Insets(0, 0, 4, 0));
        VBox.setMargin(outputDirectoryBox, new Insets(0, 0, 4, 0));
        VBox.setMargin(outputFilenameBox, new Insets(0, 0, 4, 0));

        VBox mainVBox = new VBox(vBox);
        VBox.setMargin(vBox, new Insets(20, 20, 20, 20));
        Scene scene = new Scene(mainVBox, 600, 180);

        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void readConfig() {
        try {
            File myObj = new File(CONFIG_PATH);
            Scanner myReader = new Scanner(myObj);
            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                if (data.contains(MAIN_DIRECTORY_CONFIG_LABEL)) {
                    String value = data.substring(data.indexOf(CONFIG_SPLITTER) + 2);
                    if (Files.exists(Paths.get(value))) {
                        mainDirectory = value;
                    } else {
                        System.out.println(BAD_CONFIG_MESSAGE);
                    }
                }
            }
            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println(CONFIG_FILE_NOT_FOUND_MESSAGE);
            e.printStackTrace();
        }
    }

    private HBox createFileInputBox(Stage primaryStage) {
        Button button = new Button(SELECT_INPUT_FILE_BUTTON_LABEL);
        FileChooser fileChooser = new FileChooser();
        if (mainDirectory != null) {
            fileChooser.setInitialDirectory(new File(mainDirectory));
        }
        Label label = new Label(SELECT_INPUT_FILE_LABEL);
        button.setOnAction(e -> {
            File selectedFile = fileChooser.showOpenDialog(primaryStage);
            if (selectedFile != null) {
                inputFilename = selectedFile.getAbsolutePath();
                label.setText(SELECTED_LABEL_PREFIX + inputFilename);
            }
        });
        Region region = new Region();
        HBox.setHgrow(region, Priority.ALWAYS);
        return new HBox(label, region, button);
    }

    private HBox createDirectoryOutputBox(Stage primaryStage) {
        Button button = new Button(SELECT_OUTPUT_DIRECTORY_BUTTON_LABEL);
        DirectoryChooser directoryChooser = new DirectoryChooser();
        if (mainDirectory != null) {
            directoryChooser.setInitialDirectory(new File(mainDirectory));
        }
        Label label = new Label(SELECT_OUTPUT_DIRECTORY_LABEL);
        button.setOnAction(e -> {
            File selectedDirectory = directoryChooser.showDialog(primaryStage);
            if (selectedDirectory != null) {
                outputFileDirectory = selectedDirectory.getAbsolutePath();
                label.setText(SELECTED_LABEL_PREFIX + outputFileDirectory);
            }
        });
        Region region = new Region();
        HBox.setHgrow(region, Priority.ALWAYS);

        return new HBox(label, region, button);
    }

    private HBox createOutputFilenameBox() {
        Label label = new Label(SELECT_OUTPUT_FILE_NAME_LABEL);
        outputFilenameTextField = new TextField();
        outputFilenameTextField.setText(DEFAULT_OUTPUT_FILE_NAME);
        Label outputFileExtensionLabel = new Label(OUTPUT_FILE_EXTENSION);
        Region region = new Region();
        HBox.setHgrow(region, Priority.ALWAYS);
        return new HBox(label, region, outputFilenameTextField, outputFileExtensionLabel);
    }

    private VBox createActionButtonBox() {
        CheckBox checkBox = new CheckBox(CHECKBOX_WITH_GRID_TITLE);
        Button button = new Button(ACTION_BUTTON_LABEL);
        Label resultLabel = new Label(INITIAL_RESULT_MESSAGE);
        button.setOnAction(e -> {
            if (inputFilename != null && outputFileDirectory != null && outputFilenameTextField.getText() != null) {
                String outputFileName = outputFileDirectory + "\\" + outputFilenameTextField.getText() + OUTPUT_FILE_EXTENSION;
                boolean success = printer.parseAndPrintFlashcardsAndReturnIfSuccess(
                        inputFilename, outputFileName, checkBox.isSelected());
                if (success) {
                    resultLabel.setText(GOOD_RESULT_MESSAGE);
                } else {
                    resultLabel.setText(BAD_RESULT_MESSAGE);
                }
            }
        });
        VBox vbox = new VBox(checkBox, button, resultLabel);
        VBox.setMargin(checkBox, new Insets(4));
        vbox.setAlignment(Pos.CENTER);
        return vbox;
    }
}
