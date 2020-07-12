package com.jerzy.printer.model;

public class FlashcardInfo {
    private String foreignLanguage;
    private String level;
    private String category;

    public FlashcardInfo(String foreignLanguage, String level, String category) {
        this.foreignLanguage = foreignLanguage;
        this.level = level;
        this.category = category;
    }

    public String getForeignLanguage() {
        return foreignLanguage;
    }

    public String getLevel() {
        return level;
    }

    public String getCategory() {
        return category;
    }
}
