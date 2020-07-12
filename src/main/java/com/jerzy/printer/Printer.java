package com.jerzy.printer;

import com.jerzy.printer.model.Flashcard;
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

    private Loader loader = new Loader();

    public boolean parseAndPrintFlashcards(String inputFilename, String outputFilename) {
        try (PDDocument doc = new PDDocument()) {
            List<Flashcard> flashcards = loader.loadFlashcardsFromFile(inputFilename);
            FONT = PDType0Font.load( doc, new FileInputStream(new File( "src/resource/Roboto-Regular.ttf")), true);

            for(int i = 0; i < flashcards.size(); i += 24) {
                createPage(doc, flashcards, i, true);
                createPage(doc, flashcards, i, false);
            }

            doc.save(outputFilename);
            return true;
        } catch(IOException e) {
            return false;
        }
    }

    private void createPage(PDDocument doc, List<Flashcard> flashcards, int startingFlashcardNumber, boolean isPolish) throws IOException {
        PDPage page = new PDPage(PDRectangle.A4);
        doc.addPage(page);

        try (PDPageContentStream cont = new PDPageContentStream(doc, page)) {
//            printCropBoxInfo(page);
//            drawLines(cont);

            cont.transform(Matrix.getRotateInstance(Math.toRadians(90), 0, 0));
            cont.transform(Matrix.getTranslateInstance(0, -PAGE_WIDTH));

            for (int i = startingFlashcardNumber; i < flashcards.size() && i < startingFlashcardNumber + 24; i++) {
                if(isPolish) {
                    drawText(cont, flashcards.get(i).getFrontWords().get(0), flashcards.get(i).getFlashcardInfoString(), i % 4, (i / 4) % 6);
                }
                else {
                    drawForeignText(cont, flashcards.get(i).getForeignWords().get(0), flashcards.get(i).getFlashcardInfoString(), i % 4, (i / 4) % 6);
                }
            }
        }
    }



    private void printCropBoxInfo(PDPage myPage) {
        PDRectangle cropBox = myPage.getCropBox();
        System.out.println(PAGE_WIDTH + " " + PAGE_HEIGHT);
        System.out.println(cropBox.getWidth() + " " + cropBox.getHeight());
        System.out.println(cropBox.getLowerLeftX() + " " + cropBox.getLowerLeftY());
        System.out.println(cropBox.getUpperRightX() + " " + cropBox.getUpperRightY());
    }

    private void drawLines(PDPageContentStream cont) throws IOException {
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

    private void drawForeignText(PDPageContentStream cont, String text, String flashcardInfoText, int x, int y) throws IOException {
        drawText(cont, text, flashcardInfoText, 3-x, y);
    }

    private void drawText(PDPageContentStream cont, String text, String flashcardInfoText, int xField, int yField) throws IOException {

        List<String> lines;
        float width;
        float lineHeight;
        float height;
        float maxWidth;
        int fontSize = 17;

        do {
            fontSize--;
            lines = new ArrayList<>();
            width = FONT.getStringWidth(text) / 1000 * fontSize;
            lineHeight = FONT.getFontDescriptor().getCapHeight() / 1000 * fontSize;

            if (width > MAX_CARD_CONTENT_WIDTH) {
                String[] words = text.split(" ");
                String line = words[0];
                for (int i = 1; i < words.length; i++) {
                    String newLine = line + " " + words[i];
                    if (FONT.getStringWidth(newLine) / 1000 * fontSize < MAX_CARD_CONTENT_WIDTH) {
                        line = newLine;
                    } else {
                        lines.add(line);
                        line = words[i];
                    }
                }
                lines.add(line);
            } else {
                lines.add(text);
            }
            height = (lines.size() * lineHeight) + ((lines.size() - 1) * (lineHeight / 2));
            maxWidth = 0;
            for (String line : lines) {
                maxWidth = Math.max(maxWidth, FONT.getStringWidth(line) / 1000 * fontSize);
            }
        }
        while(maxWidth > MAX_CARD_CONTENT_WIDTH || height > MAX_CARD_CONTENT_HEIGHT);

        for(int i = 0; i < lines.size(); i++) {
            String lineText = lines.get(i);
            float lineTextWidth = FONT.getStringWidth(lineText)/1000 * fontSize;
            float tx = xField * CARD_WIDTH + CARD_WIDTH / 2 - lineTextWidth / 2;
            float ty = yField * CARD_HEIGHT + CARD_HEIGHT / 2 + height / 2 - lineHeight * (i + 1) - ((lineHeight / 2) * i);

            cont.beginText();
            cont.setFont(FONT, fontSize);
            cont.setNonStrokingColor(128, 128, 128);
            cont.setLeading(fontSize * 0.8f);

            cont.newLineAtOffset(tx, ty);
            cont.showText(lineText);
            cont.endText();
        }

        float flashcardInfoFontSize = 6;
        cont.beginText();
        cont.setFont(FONT, flashcardInfoFontSize);
        cont.setNonStrokingColor(128, 128, 128);
        cont.setLeading(fontSize * 0.8f);

        cont.newLineAtOffset(xField * CARD_WIDTH + CARD_WIDTH / 2 - FONT.getStringWidth(flashcardInfoText)/1000 * flashcardInfoFontSize / 2,
                yField * CARD_HEIGHT + 10);
        cont.showText(flashcardInfoText);
        cont.endText();
    }
}
