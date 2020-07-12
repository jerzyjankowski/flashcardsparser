package com.jerzy.printer.model;

import java.util.ArrayList;
import java.util.List;

public class Flashcard {
    private List<String> frontWords;
    private List<String> foreignWords;
    private FlashcardInfo flashcardInfo;

    public Flashcard(String polishWord, String foreignWord, FlashcardInfo flashcardInfo) {
        frontWords = new ArrayList<>();
        frontWords.add(polishWord);

        foreignWords = new ArrayList<>();
        foreignWords.add(foreignWord);

        this.flashcardInfo = flashcardInfo;
    }

    public List<String> getFrontWords() {
        return frontWords;
    }

    public List<String> getForeignWords() {
        return foreignWords;
    }

    public String getFlashcardInfoString() {
        return "[" + flashcardInfo.getForeignLanguage() + "] " + flashcardInfo.getLevel() + ". " + flashcardInfo.getCategory();
    }
}
