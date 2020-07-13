package com.jerzy.printer;

import com.jerzy.printer.model.Flashcard;
import com.jerzy.printer.model.FlashcardInfo;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Parser {

    public static final String SIDES_SPLITTER = ";=;";
    public static final String INFO_LANGUAGE_LABEL = "język: ";
    public static final String INFO_LEVEL_LABEL = "poziom: ";
    public static final String INFO_CATEGORY_LABEL = "kategoria: ";
    public static final String INFO_START = "***<";
    public static final String INFO_END = "]>***";
    public static final String CHARSET_NAME = "UTF-16";

    public List<Flashcard> parseFlashcardsFromFile(String inputFilename) throws IOException {
        List<Flashcard> flashcards = new ArrayList<>();
        File file = new File(inputFilename);
        Scanner scanner = new Scanner(file, CHARSET_NAME);
        while (scanner.hasNextLine()) {
            updateFlashcardsWithFlashcardFromInput(flashcards, scanner.nextLine());
        }
        scanner.close();
        return flashcards;
    }

    private void updateFlashcardsWithFlashcardFromInput(List<Flashcard> flashcards, String line) {
        if(line.contains(INFO_START)) {
            FlashcardInfo flashcardInfo = new FlashcardInfo(
                    line.substring(line.indexOf(INFO_LANGUAGE_LABEL) + 7, line.indexOf(INFO_LANGUAGE_LABEL) + 9),
                    line.substring(line.indexOf(INFO_LEVEL_LABEL) + 8, line.indexOf(INFO_LEVEL_LABEL) + 9),
                    line.substring(line.indexOf(INFO_CATEGORY_LABEL) + 11, line.indexOf(INFO_END)));
            line = line.substring(line.indexOf(INFO_END) + 5);
            flashcards.add(
                    new Flashcard(
                            line.split(SIDES_SPLITTER)[0].replace("\n", ""),
                            line.split(SIDES_SPLITTER)[1],
                            flashcardInfo));
        }
    }
}

