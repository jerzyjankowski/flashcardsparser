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

public class Loader {
    public List<Flashcard> loadFlashcardsFromFile(String inputFilename) throws IOException {
        FlashcardInfo flashcardInfo = null;
        List<Flashcard> flashcards = new ArrayList<>();
        InputStream inputStream = new FileInputStream(inputFilename);
        InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-16"));
        int data = inputStreamReader.read();
        StringBuffer input = new StringBuffer();
        while(data != -1){
            input.append((char)(data));
            data = inputStreamReader.read();
            if(data == '\n' || data == -1) {
                String inputString = input.toString();
                System.out.println(inputString + " ");

                if(inputString.contains("***<")) {
                    flashcardInfo = new FlashcardInfo(
                            inputString.substring(inputString.indexOf("język: ") + 7, inputString.indexOf("język: ") + 9),
                            inputString.substring(inputString.indexOf("poziom: ") + 8, inputString.indexOf("poziom: ") + 9),
                            inputString.substring(inputString.indexOf("kategoria: ") + 11, inputString.indexOf("]>***")));
                    if(inputString.contains(";=;")) {
                        inputString = inputString.substring(inputString.indexOf("]>***") + 5);
                    }
                }
                if(inputString.contains(";=;")) {
                    flashcards.add(new Flashcard(inputString.split(";=;")[0].replace("\n", ""), inputString.split(";=;")[1], flashcardInfo));
                }

                input = new StringBuffer();
            }
        }
        inputStreamReader.close();

        return flashcards;
    }
}
