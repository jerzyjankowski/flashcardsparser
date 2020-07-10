package com.jerzy.parser.provider;

import com.jerzy.parser.Language;

import java.util.*;

public class CategoriesForLanguageProvider {

    public Map<Integer, String> getCategories(Language language) {
        switch(language) {
            case ENGLISH:
                return getEnglishCategories();
            case GERMAN:
                return getGermanCategories();
            case SPANISH:
                return getSpanishCategories();
            case FRENCH:
                return getFrenchCategories();
        }
        throw new IllegalStateException("No categories specified for language");
    }

    private Map<Integer, String> getEnglishCategories() {
        Map<Integer, String> categoryMap = new HashMap<>();
        categoryMap.put(1, "Zwierzęta");
        categoryMap.put(2, "Ciało i wygląd zewnętrzny");
        categoryMap.put(3, "Pieniądze i biznes");
        categoryMap.put(4, "Ubrania i rzeczy osobiste");
        categoryMap.put(5, "Inne");
        categoryMap.put(6, "Narzędzia i majsterkowanie");
        categoryMap.put(7, "Rodzina");
        categoryMap.put(8, "Żywność i kuchnia");
        categoryMap.put(9, "Pogoda i geografia");
        categoryMap.put(10, "Historia");
        categoryMap.put(11, "Mieszkanie");
        categoryMap.put(12, "Zbrodnia i kara");
        categoryMap.put(13, "Media");
        categoryMap.put(14, "Miłość i seks");
        categoryMap.put(15, "Matematyka i liczenie");
        categoryMap.put(16, "Kierunek i ruch");
        categoryMap.put(17, "Kultura i sztuka");
        categoryMap.put(18, "Polityka");
        categoryMap.put(19, "Myśli, uczucia i cechy");
        categoryMap.put(20, "Religia i wiara");
        categoryMap.put(21, "Szkoła");
        categoryMap.put(22, "Nauka");
        categoryMap.put(23, "Kształty, kolory i przedmioty");
        categoryMap.put(24, "Sport");
        categoryMap.put(25, "Czas");
        categoryMap.put(26, "Miasto i wieś");
        categoryMap.put(27, "Podróże");
        categoryMap.put(28, "Wojsko i wojna");
        categoryMap.put(29, "Zakupy");
        categoryMap.put(30, "W pracy");
        categoryMap.put(31, "Choroby i leczenie");
        categoryMap.put(32, "Samochód");
        categoryMap.put(36, "Czasowniki frazowe");
        categoryMap.put(50, "Wyrażenia ogólne");
        categoryMap.put(52, "Rośliny i natura");
        categoryMap.put(53, "Bajki, zabawki");
        categoryMap.put(55, "Życie towarzyskie");
        categoryMap.put(56, "Zwroty");
        return categoryMap;
    }

    private Map<Integer, String> getGermanCategories() {
        Map<Integer, String> categoryMap = new HashMap<>();
        categoryMap.put(1,"Rośliny i zwierzęta");
        categoryMap.put(2,"Człowiek i jego wygląd");
        categoryMap.put(3,"Biznes i pieniądze");
        categoryMap.put(4,"Ubrania i rzeczy osobiste");
        categoryMap.put(7,"Rodzina i życie rodzinne");
        categoryMap.put(8,"Jedzenie");
        categoryMap.put(9,"Pogoda, geografia i środowisko");
        categoryMap.put(10,"Wyrażenia ogólne");
        categoryMap.put(11,"Dom i czynności codzienne");
        categoryMap.put(12,"Państwo i prawo");
        categoryMap.put(13,"Sztuka, literatura i muzyka");
        categoryMap.put(14,"Miłość i seks");
        categoryMap.put(16,"Ruch i lokalizacja");
        categoryMap.put(17,"Wieś i ogród");
        categoryMap.put(19,"Charakter, uczucia i emocje");
        categoryMap.put(20,"Siły wyższe i religia");
        categoryMap.put(21,"Szkoła");
        categoryMap.put(22,"Nauka i technika");
        categoryMap.put(23,"Kolory, kształty i materiały");
        categoryMap.put(24,"Sport");
        categoryMap.put(25,"Czas, miary i wagi");
        categoryMap.put(26,"Miasto");
        categoryMap.put(27,"Podróże");
        categoryMap.put(28,"Historia, wojna i wojsko");
        categoryMap.put(29,"Robienie zakupów");
        categoryMap.put(30,"W pracy");
        categoryMap.put(31,"Medycyna");
        categoryMap.put(32,"Pojazdy i transport");
        categoryMap.put(33,"Idiomy");
        categoryMap.put(34,"Czas wolny, rozrywka i hobby");
        categoryMap.put(35,"Komunikowanie się i media");
        categoryMap.put(36,"Społeczność");
        categoryMap.put(37,"Reakcja czasowników");
        return categoryMap;
    }

    private Map<Integer, String> getSpanishCategories() {
        Map<Integer, String> categoryMap = new HashMap<>();
        categoryMap.put(1, "Zwroty podstawowe");
        categoryMap.put(2, "Liczby");
        categoryMap.put(3, "Daty i czas");
        categoryMap.put(4, "Kierunek i ruch");
        categoryMap.put(5, "Pojęcia ogólne");
        categoryMap.put(6, "Kraje i narodowości");
        categoryMap.put(7, "Geografia i nazwy geograficzne");
        categoryMap.put(8, "Kształty, kolory, określenia");
        categoryMap.put(9, "Czynności, przedmioty osobiste");
        categoryMap.put(10, "Wygląd zewnętrzny, części ciała");
        categoryMap.put(11, "Myśli, uczucia i cechy charakteru");
        categoryMap.put(12, "Ubrania");
        categoryMap.put(13, "Rodzina");
        categoryMap.put(14, "Dom i mieszkanie");
        categoryMap.put(15, "Zakupy");
        categoryMap.put(16, "Żywność i jedzenie");
        categoryMap.put(17, "Kuchnia, restauracja i bar");
        categoryMap.put(18, "Czas wolny");
        categoryMap.put(19, "Kultura i sztuka");
        categoryMap.put(20, "Podróż");
        categoryMap.put(21, "Turystyka");
        categoryMap.put(22, "Wakacje");
        categoryMap.put(23, "Samochód");
        categoryMap.put(24, "Środki komunikacji i transportu");
        categoryMap.put(25, "Wypadki");
        categoryMap.put(26, "Poczta i telefon");
        categoryMap.put(27, "Komputer i Internet");
        categoryMap.put(28, "Bank i pieniądze");
        categoryMap.put(29, "UE i gospodarka");
        categoryMap.put(30, "Biznes i przedsiębiorczość");
        categoryMap.put(31, "Szkoła");
        categoryMap.put(32, "Praca");
        categoryMap.put(33, "Środki masowego przekazu");
        categoryMap.put(34, "Państwo i życie publiczne");
        categoryMap.put(35, "Zbrodnia i kara");
        categoryMap.put(36, "Wojna i pokój");
        categoryMap.put(37, "Zdrowie i medycyna");
        categoryMap.put(38, "Pogoda i klimat");
        categoryMap.put(39, "Natura i środowisko");
        categoryMap.put(40, "Zwierzęta");
        categoryMap.put(41, "Rośliny");
        categoryMap.put(42, "Sport");
        categoryMap.put(43, "Religia i wiara");
        categoryMap.put(44, "Matematyka i liczenie");
        categoryMap.put(45, "Narzędzia i majsterkowanie");
        categoryMap.put(46, "Surowce i materiały");
        return categoryMap;
    }

    private Map<Integer, String> getFrenchCategories() {
        Map<Integer, String> categoryMap = new HashMap<>();
        categoryMap.put(1, "Rośliny i zwierzęta");
        categoryMap.put(2, "Człowiek i jego wygląd");
        categoryMap.put(3, "Bank i pieniądze");
        categoryMap.put(4, "Ubrania i dodatki");
        categoryMap.put(5, "Inne");
        categoryMap.put(6, "Inne");
        categoryMap.put(7, "Rodzina i życie rodzinne");
        categoryMap.put(8, "Jedzenie");
        categoryMap.put(9, "Pogoda, geografia i środowisko");
        categoryMap.put(11, "Dom i mieszkanie");
        categoryMap.put(12, "Państwo i życie publiczne");
        categoryMap.put(13, "Kultura i sztuka");
        categoryMap.put(14, "Nauka o języku");
        categoryMap.put(16, "Ruch i lokalizacja");
        categoryMap.put(17, "Wieś i ogród");
        categoryMap.put(19, "Charakter i usposobienie");
        categoryMap.put(20, "Siły wyższe i religia");
        categoryMap.put(21, "Szkoła i nauka");
        categoryMap.put(23, "Kształty, materiały i określenia");
        categoryMap.put(24, "Sport");
        categoryMap.put(25, "Czas, miary i wagi");
        categoryMap.put(26, "Miasto");
        categoryMap.put(27, "Podróż i zwiedzanie");
        categoryMap.put(28, "Historia, wojsko i wojna");
        categoryMap.put(29, "Zakupy");
        categoryMap.put(30, "Praca i biznes");
        categoryMap.put(31, "Stan zdrowia i medycyna");
        categoryMap.put(32, "Pojazdy i transport");
        categoryMap.put(34, "Czas wolny i hobby");
        categoryMap.put(35, "Poczta i telefon");
        categoryMap.put(36, "Zbrodnia i kara");
        categoryMap.put(37, "Komputer i Internet");
        categoryMap.put(38, "Środki masowego przekazu");
        categoryMap.put(39, "Samochód i wypadki");
        categoryMap.put(40, "Kraje i narodowości");
        categoryMap.put(41, "Ekologia, ochrona środowiska");
        categoryMap.put(42, "Codzienne czynności, rzeczy osobiste");
        categoryMap.put(43, "Uczucia i związki");
        categoryMap.put(50, "Wyrażenia ogólne");
        categoryMap.put(56, "Zwroty podstawowe");
        return categoryMap;
    }

    private Map<Integer, String> getNewLanguageCategories() {
        Map<Integer, String> categoryMap = new HashMap<>();
        categoryMap.put(1, "1");
        categoryMap.put(2, "2");
        categoryMap.put(3, "3");
        categoryMap.put(4, "4");
        categoryMap.put(5, "5");
        categoryMap.put(6, "6");
        categoryMap.put(7, "7");
        categoryMap.put(8, "8");
        categoryMap.put(9, "9");
        categoryMap.put(10, "10");
        categoryMap.put(11, "11");
        categoryMap.put(12, "12");
        categoryMap.put(13, "13");
        categoryMap.put(14, "14");
        categoryMap.put(15, "15");
        categoryMap.put(16, "16");
        categoryMap.put(17, "17");
        categoryMap.put(18, "18");
        categoryMap.put(19, "19");
        categoryMap.put(20, "20");
        categoryMap.put(21, "21");
        categoryMap.put(22, "22");
        categoryMap.put(23, "23");
        categoryMap.put(24, "24");
        categoryMap.put(25, "25");
        categoryMap.put(26, "26");
        categoryMap.put(27, "27");
        categoryMap.put(28, "28");
        categoryMap.put(29, "29");
        categoryMap.put(30, "30");
        categoryMap.put(31, "31");
        categoryMap.put(32, "32");
        categoryMap.put(33, "33");
        categoryMap.put(34, "34");
        categoryMap.put(35, "35");
        categoryMap.put(36, "36");
        categoryMap.put(37, "37");
        categoryMap.put(38, "38");
        categoryMap.put(39, "39");
        categoryMap.put(40, "40");
        categoryMap.put(41, "41");
        categoryMap.put(42, "42");
        categoryMap.put(43, "43");
        categoryMap.put(44, "44");
        categoryMap.put(45, "45");
        categoryMap.put(46, "46");
        categoryMap.put(47, "47");
        categoryMap.put(48, "48");
        categoryMap.put(49, "49");
        categoryMap.put(50, "50");
        categoryMap.put(51, "51");
        categoryMap.put(52, "52");
        categoryMap.put(53, "53");
        categoryMap.put(54, "54");
        categoryMap.put(55, "55");
        categoryMap.put(56, "56");
        categoryMap.put(57, "57");
        categoryMap.put(58, "58");
        categoryMap.put(59, "59");
        categoryMap.put(60, "60");
        categoryMap.put(61, "61");
        categoryMap.put(62, "62");
        categoryMap.put(63, "63");
        categoryMap.put(64, "64");
        categoryMap.put(65, "65");
        categoryMap.put(66, "66");
        categoryMap.put(67, "67");
        categoryMap.put(68, "68");
        categoryMap.put(69, "69");
        categoryMap.put(70, "70");
        categoryMap.put(71, "71");
        categoryMap.put(72, "72");
        categoryMap.put(73, "73");
        categoryMap.put(74, "74");
        categoryMap.put(75, "75");
        categoryMap.put(76, "76");
        categoryMap.put(77, "77");
        categoryMap.put(78, "78");
        categoryMap.put(79, "79");
        categoryMap.put(80, "80");
        return categoryMap;
    }
}
