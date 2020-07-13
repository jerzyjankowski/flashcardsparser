package com.jerzy.printer.model;

public class Flashcard {
    private String frontWord;
    private String backWord;
    private FlashcardInfo flashcardInfo;

    public Flashcard(String frontWord, String backWord, FlashcardInfo flashcardInfo) {
        this.frontWord = frontWord;
        this.backWord = backWord;

        this.flashcardInfo = flashcardInfo;
    }

    public String getFrontWord() {
        return frontWord;
    }

    public String getBackWord() {
        return backWord;
    }

    public String getFlashcardInfoString() {
        return "[" + flashcardInfo.getForeignLanguage() + "] " + flashcardInfo.getLevel() + ". " + flashcardInfo.getCategory();
    }
}
