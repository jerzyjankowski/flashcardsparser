package com.jerzy.parser.provider;

import com.jerzy.parser.Language;

import java.util.Arrays;
import java.util.List;

public class CategoriesOrderProvider {
    public List<Integer> getCategoriesOrder(Language language) {
        switch(language) {
            case ENGLISH:
                return getEnglishCategoriesOrder();
            case GERMAN:
                return getGermanCategoriesOrder();
            case SPANISH:
                return getSpanishCategoriesOrder();
            case FRENCH:
                return getFrenchCategoriesOrder();
        }
        throw new IllegalStateException("No categoriesOrder specified for language");
    }

    public List<Integer> getEnglishCategoriesOrder() {
        return Arrays.asList(50, 11, 55, 2, 4, 7, 8, 15, 25, 23, 16, 14, 19, 30, 3, 29, 9, 52, 1, 27, 26, 32, 13,
                22, 6, 53, 21, 17, 24, 18, 10, 28, 12, 31, 20, 5, 56, 36);
    }

    private List<Integer> getGermanCategoriesOrder() {
        return Arrays.asList(10, 11, 34, 2, 4, 7, 8, 25, 23, 16, 14, 19, 30, 3, 29, 9, 32, 27, 26,
                17, 1, 36, 35, 22, 21, 13, 24, 12, 28, 31, 20, 33, 37);
    }

    private List<Integer> getSpanishCategoriesOrder() {
        return Arrays.asList(1, 5, 14, 9, 18, 10, 12, 13, 16, 17, 2, 44, 3, 8, 46, 4, 11, 27, 32, 28, 30, 15, 38, 39,
                41, 40, 20, 22, 21, 23, 24, 26, 33, 45, 31, 19, 42, 7, 6, 34, 29, 36, 35, 37, 25, 43);
    }

    private List<Integer> getFrenchCategoriesOrder() {
        return Arrays.asList(56, 50, 11, 42, 34, 2, 4, 7, 8, 25, 23, 16, 43, 19, 37, 30, 3, 29, 9, 32, 39, 27, 26, 17,
                1, 14, 35, 38, 21, 13, 24, 40, 12, 28, 36, 31, 41, 20, 5, 6);
    }

    private List<Integer> getNewLanguageCategoriesOrder() {
        return Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19,
                20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31, 32, 33, 34, 35, 36, 37, 38, 39,
                40, 41, 42, 43, 44, 45, 46, 47, 48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 58, 59,
                60, 61, 22, 23, 24, 25, 26, 27, 28, 29, 70, 71, 72, 73, 74, 75, 76, 77, 78, 79, 80);
    }
}
