package com.jerzy.parser.provider;

import com.jerzy.parser.Language;

import java.util.HashMap;
import java.util.Map;

public class TypesForLanguageProvider {

    public Map<Integer, String> getTypes(Language language) {
        switch(language) {
            case ENGLISH:
                return getEnglishTypes();
            case GERMAN:
                return getGermanTypes();
            case SPANISH:
                return getSpanishTypes();
            case FRENCH:
                return getFrencTypes();
        }
        throw new IllegalStateException("No categories specified for language");
    }

    public Map<Integer, String> getEnglishTypes() {
        Map<Integer, String> categoryMap = new HashMap<>();
        return categoryMap;
    }

    public Map<Integer, String> getGermanTypes() {
        Map<Integer, String> categoryMap = new HashMap<>();
        return categoryMap;
    }

    public Map<Integer, String> getSpanishTypes() {
        Map<Integer, String> categoryMap = new HashMap<>();
        return categoryMap;
    }

    public Map<Integer, String> getFrencTypes() {
        Map<Integer, String> categoryMap = new HashMap<>();
        return categoryMap;
    }
}
