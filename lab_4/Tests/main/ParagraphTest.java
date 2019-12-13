package main;


import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.*;

class ParagraphTest {

    protected String output(Paragraph paragraph) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintStream out = new PrintStream(outputStream);

        paragraph.writeHTML(out);

        String output = "";

        output = outputStream.toString(StandardCharsets.UTF_8);

        return output;
    }

    @org.junit.jupiter.api.Test
    void setContent() {
        Paragraph p1 = new Paragraph();
        p1.setContent("Tytul");
        String s = "Tytul";
        assertEquals(p1.content, s);
    }

    @org.junit.jupiter.api.Test
    void writeHTML() {
        Paragraph p1 = new Paragraph();
        p1.setContent("Tytul");
        String out = this.output(p1);
        assertTrue(out.contains("<p>"));
        assertTrue(out.contains("</p>"));
        assertTrue(out.contains("Tytul"));
    }
}