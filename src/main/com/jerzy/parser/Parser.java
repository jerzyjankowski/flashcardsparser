package com.jerzy.parser;

import com.google.common.collect.Lists;
import com.jerzy.parser.provider.CategoriesForLanguageProvider;
import com.jerzy.parser.provider.CategoriesOrderProvider;

import java.io.*;
import java.nio.charset.Charset;
import java.util.*;

public class Parser { 

    private static List<Language> languages = Lists.newArrayList(
            Language.getLanguage("en"), Language.getLanguage("de"), Language.getLanguage("es"), Language.getLanguage("fr"));

    private CategoriesForLanguageProvider categoriesForLanguageProvider = new CategoriesForLanguageProvider();
    private CategoriesOrderProvider categoriesOrderProvider = new CategoriesOrderProvider();

    private String languageTitle;
    private String levelTitle = " [poziom: ";
    private String categoryTitle = ", kategoria: ";
    private String endTitle = "]";
    private Map<Integer, String> categoryMap = new HashMap<>();
    private List<Integer> categoryOrder = new ArrayList<>();
    private String inputStreamFileName;
    private String outputStreamFileName;

    private Set<Integer> foundCategories = new HashSet<>();

    public static void main(String... args) {
        for(Language language: languages) {
            System.out.println("\n\n" + language.getLanguageCode().toUpperCase());
            try {
                new Parser(language).parse();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private Parser(Language language) {
        categoryMap = categoriesForLanguageProvider.getCategories(language);
        categoryOrder = categoriesOrderProvider.getCategoriesOrder(language);
        inputStreamFileName = ".\\input\\b_sb_u1_" + language.getLanguageCode() + ".txtde";
        outputStreamFileName = ".\\output\\" + language.getLanguageCode() + "_words.txt";
        languageTitle = language.getTitle();
    }

    private void parse() throws IOException {
        OutputStream outputStream = new FileOutputStream(outputStreamFileName);
        OutputStreamWriter  outputStreamWriter = new OutputStreamWriter(outputStream, Charset.forName("UTF-16"));

        for(Integer index : categoryOrder) {
            outputStreamWriter.write(categoryMap.get(index) + "\n");
        }

        for(int level = 1; level <= 4; level++) {
            for(Integer i : categoryOrder) {
                parseCategory(i, level, outputStreamWriter);
            }
        }
        outputStreamWriter.close();
    }

    private void parseCategory(int categoryId, int level, OutputStreamWriter  outputStreamWriter) throws IOException {
        String title = languageTitle + levelTitle + level + categoryTitle + categoryMap.get(categoryId) + endTitle;

        outputStreamWriter.write("\n\n\n***************************************************************************\n***<"
                + title + ">***\n***************************************************************************\n");

        InputStream inputStream = new FileInputStream(inputStreamFileName);
        InputStreamReader  inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-16"));

        parseCategoryAndDifficulty(categoryId, level, inputStreamReader, outputStreamWriter);

        inputStreamReader.close();
    }

    private void parseCategoryAndDifficulty(int id, int difficulty, InputStreamReader inputStreamReader,
                                            OutputStreamWriter outputStreamWriter) throws IOException {
        int currentCategoryNo;
        int currentDifficulty;
        int currentType;
        String currentForeignWord;
        String currentPolishWord;


        int data = inputStreamReader.read();
        StringBuffer input = new StringBuffer();
        while(data != -1){
            input.append((char)(data-5));
            data = inputStreamReader.read();
            if(data == '\n' || data == -1) {
                String inputString = input.toString();
                currentCategoryNo = Integer.parseInt(inputString.split(" ")[0].trim());
                currentDifficulty = Integer.parseInt(inputString.split(" ")[2].substring(0, 1));
                currentType = Integer.parseInt(inputString.split(" ")[2].substring(1, 2));
                inputString = inputString.substring(inputString.indexOf(" ")+1, inputString.length());
                inputString = inputString.substring(inputString.indexOf(" ")+1, inputString.length());
                inputString = inputString.substring(inputString.indexOf(" ")+1, inputString.length());
                currentForeignWord = inputString.split("=")[0].trim();
                currentPolishWord = inputString.split("=")[1].trim();

                if(currentCategoryNo == id && currentDifficulty == difficulty) {
                    outputStreamWriter.write(currentPolishWord + ";=;" + currentForeignWord + "\n");
                }
                if(!foundCategories.contains(currentCategoryNo)) {
                    foundCategories.add(currentCategoryNo);
                    System.out.println(currentCategoryNo + ": " + currentPolishWord + " ;=; " + currentForeignWord);
                }
                input = new StringBuffer();
            }
        }
    }
}
