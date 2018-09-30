package com.jerzy.printer;

import java.util.ArrayList;
import java.util.List;

public class Flashcard {
    private List<String> polishWords;
    private List<String> foreignWords;

    public Flashcard(String polishWord, String foreignWord) {
        polishWords = new ArrayList<>();
        polishWords.add(polishWord);

        foreignWords = new ArrayList<>();
        foreignWords.add(foreignWord);
    }

    public void addWords(String polishWord, String foreignWord) {
        if(polishWords == null) {
            polishWords = new ArrayList<>();
        }
        polishWords.add(polishWord);

        if(foreignWords == null) {
            foreignWords = new ArrayList<>();
        }
        foreignWords.add(foreignWord);
    }

    public List<String> getPolishWords() {
        return polishWords;
    }

    public List<String> getForeignWords() {
        return foreignWords;
    }
}
