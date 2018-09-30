package com.jerzy.printer;

import com.google.common.collect.Lists;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.*;
import org.apache.pdfbox.util.Matrix;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
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

    public static void main(String[] args) throws IOException {
        List<Flashcard> flashcards = createSampleFlashcards2();

        try (PDDocument doc = new PDDocument()) {
            FONT = PDType0Font.load( doc, new FileInputStream(new File( "src/resource/Roboto-Regular.ttf")), true);

            PDPage polishPage = new PDPage(PDRectangle.A4);
            doc.addPage(polishPage);

            try (PDPageContentStream cont = new PDPageContentStream(doc, polishPage)) {
                printCropBoxInfo(polishPage);
                drawLines(cont);

                cont.transform(Matrix.getRotateInstance(Math.toRadians(90) , 0, 0));
                cont.transform(Matrix.getTranslateInstance(0, -PAGE_WIDTH));

                for(int i = 0; i < flashcards.size(); i++) {
                    drawText(cont, flashcards.get(i).getPolishWords().get(0), i % 4, (i / 4) % 6);
                }
            }

            PDPage foreignPage = new PDPage(PDRectangle.A4);
            doc.addPage(foreignPage);

            try (PDPageContentStream cont = new PDPageContentStream(doc, foreignPage)) {
                printCropBoxInfo(foreignPage);
                drawLines(cont);

                cont.transform(Matrix.getRotateInstance(Math.toRadians(90) , 0, 0));
                cont.transform(Matrix.getTranslateInstance(0, -PAGE_WIDTH));

                for(int i = 0; i < flashcards.size(); i++) {
                    drawForeignText(cont, flashcards.get(i).getForeignWords().get(0), i % 4, (i / 4) % 6);
                }
            }

            doc.save("./output/first.pdf");
        }
    }

    private static List<Flashcard> createSampleFlashcards() {
        return Lists.newArrayList(
                new Flashcard("odholować", "abschleppen"),
                new Flashcard("absolutny, bezwzględny", "absolut"),
                new Flashcard("szanować, poważać", "achten"),
                new Flashcard("baczność, uwaga", "die Achtung"),
                new Flashcard("akt, czyn", "der Akt"),
                new Flashcard("jednak, jednakże", "allerdings"),
                new Flashcard("drugi, następny, inny", "ander"),
                new Flashcard("zmiana", "die Änderung"),
                new Flashcard("doceniać", "anerkennen"),
                new Flashcard("wydawać się", "anmuten"),
                new Flashcard("przyjmować, akceptować", "annehmen"),
                new Flashcard("dopasowywać", "anpassen"),
                new Flashcard("zamiast", "anstatt"),
                new Flashcard("rozpoczynać, przystąpić", "antreten"),
                new Flashcard("rodzajnik", "der Artikel"),
                new Flashcard("aspekt", "der Aspekt"),
                new Flashcard("świadczyć, zdobyć", "aufbringen"),
                new Flashcard("przestawać, kończyć", "aufhören"),
                new Flashcard("rozwiązanie, rozpuszczenie", "die Auflösung"),
                new Flashcard("stawić, ustawiać", "aufstellen"),
                new Flashcard("pojawić się", "auftauchen"),
                new Flashcard("wykazywać", "aufweisen"),
                new Flashcard("wyprowadzać", "ausführen"),
                new Flashcard("wymówka, wykręt", "die Ausrede")
        );
    }

    private static List<Flashcard> createSampleFlashcards2() {
        return Lists.newArrayList(
                new Flashcard("zamieniać, wymieniać (się na coś)", "austauschen"),
                new Flashcard("przystosowany dla niepełnosprawnych", "behindertengerecht"),
                new Flashcard("notabene, w dodatku, nawiasem mówiąc", "übrigens"),
                new Flashcard("oddawać buty do reperacji", "die Schuhe reparieren lassen"),
                new Flashcard("uczenie się języków obcych", "das Fremdsprachenlernen"),
                new Flashcard("wyskakujące okienko z reklamą", "Pop-up Fenster"),
                new Flashcard("dobrze się bawić, prowadzić miłą rozmowę", "sich gut unterhalten"),
                new Flashcard("spotykać się z przyjaciółmi", "sich mit den Freunden treffen"),
                new Flashcard("jedyny w swoim rodzaju, znakomity", "einzigartig"),
                new Flashcard("kłócić się o coś", "streiten sich über etwas"),
                new Flashcard("poprosić o kartę", "um die Speisekarte bitten"),
                new Flashcard("być z kimś w związku", "in einer Beziehung mit jmdm sein"),
                new Flashcard("irytujący, denerwujący, nieprzyjemny", "ärgerlich"),
                new Flashcard("brać na siebie odpowiedzialność (czas.)", "verantworten"),
                new Flashcard("być zatrudnionym, pracować zawodowo", "berufstätig sein"),
                new Flashcard("być zaproszonym na spotkanie", "zum Treffen eingeladen sein"),
                new Flashcard("niewypłacalność, upadłość, bankructwo", "die Insolvenz"),
                new Flashcard("ciśnienie powietrza (atmosferyczne)", "der Luftdruck"),
                new Flashcard("świadomy zagrożeń dla środowiska", "umweltbewusst"),
                new Flashcard("włamywanie się do systemów komputerowych", "das Hacking"),
                new Flashcard("być bezradnym i opuszczonym", "hilflos und verlassen sein"),
                new Flashcard("załatwiać bilety, zaopatrzyć się w bilety", "Karten besorgen"),
                new Flashcard("Szanowni Państwo", "SehrgeehrteDamenundHerrenHerren"),
                new Flashcard("oblać egzamin", "die Prüfung nicht bestehen")
        );
    }

    private static void printCropBoxInfo(PDPage myPage) {
        PDRectangle cropBox = myPage.getCropBox();
        System.out.println(PAGE_WIDTH + " " + PAGE_HEIGHT);
        System.out.println(cropBox.getWidth() + " " + cropBox.getHeight());
        System.out.println(cropBox.getLowerLeftX() + " " + cropBox.getLowerLeftY());
        System.out.println(cropBox.getUpperRightX() + " " + cropBox.getUpperRightY());
    }

    private static void drawLines(PDPageContentStream cont) throws IOException {
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

    private static void drawForeignText(PDPageContentStream cont, String text, int x, int y) throws IOException {
        drawText(cont, text, 3-x, y);
    }

    private static void drawText(PDPageContentStream cont, String text, int xField, int yField) throws IOException {

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
    }
}
