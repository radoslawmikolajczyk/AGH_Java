package main;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;

public class MainClass {
    public static void main(String[] args) {
        Document cv = new Document();
        cv.setTitle("CV - Radosław Mikołajczyk");
        cv.setPhoto("zdjecie.jpeg");
        cv.addSection("Dane osobowe")
                .addParagraph("Miejsce urodzenia: Limanowa")
                .addParagraph("Data urodzenia: 03.02.1998")
                .addParagraph("Telefon kontaktowy: 513 444 398")
                .addParagraph("email: radoslaw.mikolajczyk@o2.pl");
        cv.addSection("Wykształcenie")
                .addParagraph("2011-2014 Gimnazjum im. I PSP AK w Szczawie")
                .addParagraph("2014-2017 LO w CERN 2000 w Piekarach")
                .addParagraph("2018 - obecnie AGH im. Stanisława Staszica w Krakowie, Wydział EAIiIB, kierunek informatyka");

        cv.addSection("Umiejętności")
                .addParagraph(
                        new ParagraphWithList().setContent("Umiejętności:")
                                .addListItem("C")
                                .addListItem("C++")
                                .addListItem("Java")
                );
        cv.addSection("Zainteresowania")
                .addParagraph(new ParagraphWithList().setContent("Zainteresowania:")
                .addListItem("Programowanie")
                .addListItem("Muzyka")
                .addListItem("Sport")
                .addListItem("Sztuka"));

        try {
            cv.writeHTML(new PrintStream(new File("mojecv.html")));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

}
