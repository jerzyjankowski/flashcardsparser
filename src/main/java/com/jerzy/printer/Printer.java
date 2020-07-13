package com.jerzy.printer;

import com.jerzy.printer.model.Flashcard;
import com.jerzy.printer.model.FlashcardConfig;
import com.jerzy.printer.model.PageType;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.*;
import org.apache.pdfbox.util.Matrix;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Printer {

    public static final String FONT_PATH = "src/resource/Roboto-Regular.ttf";
    public static final int FLASHCARDS_PER_PAGE = 24;
    public static final int INFO_FONT_SIZE = 6;
    private static PDType0Font FONT;
    private static Integer VERTICALS_NO = 6;
    private static Integer HORIZONTALS_NO = 4;
    private static Float PAGE_WIDTH = PDRectangle.A4.getWidth();
    private static Float PAGE_HEIGHT = PDRectangle.A4.getHeight();
    private static Float CARD_WIDTH = PAGE_HEIGHT/4;
    private static Float CARD_HEIGHT = PAGE_WIDTH/6;
    private static Float MAX_CARD_CONTENT_WIDTH = CARD_WIDTH - 10;
    private static Float MAX_CARD_CONTENT_HEIGHT = CARD_HEIGHT - 10;
    private static Float[] VERTICALS_X = {0.0f, CARD_HEIGHT, 2 * CARD_HEIGHT, 3 * CARD_HEIGHT, 4 * CARD_HEIGHT,
            5 * CARD_HEIGHT, 6 * CARD_HEIGHT};
    private static Float[] HORIZONTALS_Y = {0.0f, CARD_WIDTH, 2 * CARD_WIDTH, 3 * CARD_WIDTH, 4 * CARD_WIDTH};

    private Parser parser = new Parser();

    public boolean parseAndPrintFlashcardsAndReturnIfSuccess(String inputFilename, String outputFilename, boolean pdfWithGrid) {
        try (PDDocument doc = new PDDocument()) {
            List<Flashcard> flashcards = parser.parseFlashcardsFromFile(inputFilename);
            FONT = PDType0Font.load( doc, new FileInputStream(new File(FONT_PATH)), true);

            for(int i = 0; i < flashcards.size(); i += FLASHCARDS_PER_PAGE) {
                createPage(doc, flashcards.subList(i, i + FLASHCARDS_PER_PAGE), new PageType(true, pdfWithGrid));
                createPage(doc, flashcards.subList(i, i + FLASHCARDS_PER_PAGE), new PageType(false, pdfWithGrid));
            }

            doc.save(outputFilename);
            return true;
        } catch(IOException e) {
            return false;
        }
    }

    private void createPage(PDDocument doc, List<Flashcard> flashcards, PageType pageType) throws IOException {
        PDPage page = new PDPage(PDRectangle.A4);
        doc.addPage(page);
//        printCropBoxInfo(page); // INFO: left for debugging

        try (PDPageContentStream pageContentStream = new PDPageContentStream(doc, page)) {
            if (pageType.isGridded()) {
                drawGrid(pageContentStream);
            }

            pageContentStream.transform(Matrix.getRotateInstance(Math.toRadians(90), 0, 0));
            pageContentStream.transform(Matrix.getTranslateInstance(0, -PAGE_WIDTH));

            for (int i = 0; i < flashcards.size() && i < FLASHCARDS_PER_PAGE; i++) {
                drawFlashcard(pageContentStream, pageType, flashcards.get(i), i);
            }
        }
    }

    // INFO: left for debugging
    private void printCropBoxInfo(PDPage myPage) {
        PDRectangle cropBox = myPage.getCropBox();
        System.out.println(PAGE_WIDTH + " " + PAGE_HEIGHT);
        System.out.println(cropBox.getWidth() + " " + cropBox.getHeight());
        System.out.println(cropBox.getLowerLeftX() + " " + cropBox.getLowerLeftY());
        System.out.println(cropBox.getUpperRightX() + " " + cropBox.getUpperRightY());
    }

    private void drawGrid(PDPageContentStream cont) throws IOException {
        cont.setStrokingColor(192, 192, 192);
        for(int i = 1; i < VERTICALS_NO; i++) {
            cont.moveTo(VERTICALS_X[i], 0);
            cont.lineTo(VERTICALS_X[i], PAGE_HEIGHT);
            cont.stroke();
        }

        for(int i = 1; i < HORIZONTALS_NO; i++) {
            cont.moveTo(0, HORIZONTALS_Y[i]);
            cont.lineTo(PAGE_WIDTH, HORIZONTALS_Y[i]);
            cont.stroke();
        }
    }

    private void drawFlashcard(PDPageContentStream pageContentStream, PageType pageType, Flashcard flashcard, int index) throws IOException {
        String text = pageType.isFront() ? flashcard.getFrontText() : flashcard.getBackText();
        String info = flashcard.getFlashcardInfoString();
        int column = pageType.isFront() ? (index % 4) : (3 - index % 4);
        int row = (index / 4) % 6;

        FlashcardConfig config = computeFlashcardConfig(text);
        drawText(pageContentStream, config, column, row);
        drawInfo(pageContentStream, info, column, row);
    }

    private FlashcardConfig computeFlashcardConfig(String text) throws IOException {
        FlashcardConfig config = new FlashcardConfig();

        do {
            config.setFontSize(config.getFontSize() - 1);
            config.setLines(new ArrayList<>());
            config.setWidth(FONT.getStringWidth(text) / 1000 * config.getFontSize());
            config.setLineHeight(FONT.getFontDescriptor().getCapHeight() / 1000 * config.getFontSize());

            if (config.getWidth() > MAX_CARD_CONTENT_WIDTH) {
                String[] words = text.split(" ");
                String line = words[0];
                for (int i = 1; i < words.length; i++) {
                    String newLine = line + " " + words[i];
                    if (FONT.getStringWidth(newLine) / 1000 * config.getFontSize() < MAX_CARD_CONTENT_WIDTH) {
                        line = newLine;
                    } else {
                        config.getLines().add(line);
                        line = words[i];
                    }
                }
                config.getLines().add(line);
            } else {
                config.getLines().add(text);
            }
            config.setHeight((config.getLines().size() * config.getLineHeight()) + ((config.getLines().size() - 1) * (config.getLineHeight() / 2)));
            config.setMaxWidth(0);
            for (String line : config.getLines()) {
                config.setMaxWidth(Math.max(config.getMaxWidth(), FONT.getStringWidth(line) / 1000 * config.getFontSize()));
            }
        }
        while (config.getMaxWidth() > MAX_CARD_CONTENT_WIDTH || config.getHeight() > MAX_CARD_CONTENT_HEIGHT);
        return config;
    }

    private void drawText(PDPageContentStream contentStream, FlashcardConfig config, int column, int row) throws IOException {
        for (int i = 0; i < config.getLines().size(); i++) {
            String lineText = config.getLines().get(i);
            float lineTextWidth = FONT.getStringWidth(lineText) / 1000 * config.getFontSize();
            float tx = column * CARD_WIDTH + CARD_WIDTH / 2 - lineTextWidth / 2;
            float ty = row * CARD_HEIGHT + CARD_HEIGHT / 2 + config.getHeight() / 2 -
                    config.getLineHeight() * (i + 1) - ((config.getLineHeight() / 2) * i);

            contentStream.beginText();
            contentStream.setFont(FONT, config.getFontSize());
            contentStream.setNonStrokingColor(128, 128, 128);
            contentStream.setLeading(config.getFontSize() * 0.8f);

            contentStream.newLineAtOffset(tx, ty);
            contentStream.showText(lineText);
            contentStream.endText();
        }

    }
    private void drawInfo(PDPageContentStream contentStream, String text, int column, int row) throws IOException {
        contentStream.beginText();
        contentStream.setFont(FONT, INFO_FONT_SIZE);
        contentStream.setNonStrokingColor(128, 128, 128);
        contentStream.setLeading(13);

        contentStream.newLineAtOffset(column * CARD_WIDTH + CARD_WIDTH / 2 - FONT.getStringWidth(text)/1000 * INFO_FONT_SIZE / 2,
                row * CARD_HEIGHT + 10);
        contentStream.showText(text);
        contentStream.endText();
    }
}
