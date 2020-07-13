package com.jerzy.printer;

import com.jerzy.printer.model.Flashcard;
import com.jerzy.printer.model.FlashcardInfo;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

public class Parser {

    public static final String SIDES_SPLITTER = ";=;";
    public static final String INFO_LANGUAGE_LABEL = "język: ";
    public static final String INFO_LEVEL_LABEL = "poziom: ";
    public static final String INFO_CATEGORY_LABEL = "kategoria: ";
    public static final String INFO_START = "***<";
    public static final String INFO_END = "]>***";
    public static final Charset CHARSET = Charset.forName("UTF-16");

    public List<Flashcard> parseFlashcardsFromFile(String inputFilename) throws IOException {
        List<Flashcard> flashcards = new ArrayList<>();
        InputStream inputStream = new FileInputStream(inputFilename);
        InputStreamReader inputStreamReader = new InputStreamReader(inputStream, CHARSET);
        int data = inputStreamReader.read();
        StringBuffer input = new StringBuffer();
        while(data != -1){
            input.append((char)(data));
            data = inputStreamReader.read();
            if(data == '\n' || data == -1) {
                updateFlashcardsWithFlashcardFromInput(flashcards, input);
                input = new StringBuffer();
            }
        }
        inputStreamReader.close();

        return flashcards;
    }

    private void updateFlashcardsWithFlashcardFromInput(List<Flashcard> flashcards, StringBuffer input) {
        String inputString = input.toString();
        if(inputString.contains(INFO_START)) {
            FlashcardInfo flashcardInfo = new FlashcardInfo(
                    inputString.substring(inputString.indexOf(INFO_LANGUAGE_LABEL) + 7, inputString.indexOf(INFO_LANGUAGE_LABEL) + 9),
                    inputString.substring(inputString.indexOf(INFO_LEVEL_LABEL) + 8, inputString.indexOf(INFO_LEVEL_LABEL) + 9),
                    inputString.substring(inputString.indexOf(INFO_CATEGORY_LABEL) + 11, inputString.indexOf(INFO_END)));
            inputString = inputString.substring(inputString.indexOf(INFO_END) + 5);
            flashcards.add(
                    new Flashcard(
                            inputString.split(SIDES_SPLITTER)[0].replace("\n", ""),
                            inputString.split(SIDES_SPLITTER)[1],
                            flashcardInfo));
        }
    }
}

