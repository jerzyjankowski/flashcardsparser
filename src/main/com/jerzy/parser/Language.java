package com.jerzy.parser;

public enum Language {
    ENGLISH("en", "Angielskie słowa"),
    GERMAN("de", "Niemieckie słowa"),
    SPANISH("es", "Hiszpańskie słowa"),
    FRENCH("fr", "Francuskie słowa");

    private String languageCode;
    private String title;

    private Language(String languageCode, String title) {
        this.languageCode = languageCode;
        this.title = title;
    }

    public static Language getLanguage(String languageCode) {
        for(Language language: Language.values()) {
            if(languageCode.equals(language.languageCode)) {
                return language;
            }
        }
        throw new IllegalArgumentException("Not known language code");
    }

    public String getLanguageCode() {
        return languageCode;
    }

    public String getTitle() {
        return title;
    }
}
