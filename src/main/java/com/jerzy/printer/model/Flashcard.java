package com.jerzy.printer.model;

public class Flashcard {
    private String frontText;
    private String backText;
    private FlashcardInfo flashcardInfo;

    public Flashcard(String frontText, String backText, FlashcardInfo flashcardInfo) {
        this.frontText = frontText;
        this.backText = backText;

        this.flashcardInfo = flashcardInfo;
    }

    public String getFrontText() {
        return frontText;
    }

    public String getBackText() {
        return backText;
    }

    public String getFlashcardInfoString() {
        return "[" + flashcardInfo.getForeignLanguage() + "] " + flashcardInfo.getLevel() + ". " + flashcardInfo.getCategory();
    }
}
