package com.jerzy.printer;

public class FlashcardInfo {
    private String foreignLanguage;
    private int level;
    private String category;

    public FlashcardInfo(String foreignLanguage, int level, String category) {
        this.foreignLanguage = foreignLanguage;
        this.level = level;
        this.category = category;
    }

    public String getForeignLanguage() {
        return foreignLanguage;
    }

    public void setForeignLanguage(String foreignLanguage) {
        this.foreignLanguage = foreignLanguage;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}
