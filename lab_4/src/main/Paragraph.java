package main;

import java.io.PrintStream;

public class Paragraph {
    String content;

    Paragraph setContent(String content){
        this.content = content;
        return this;
    }

    void writeHTML(PrintStream out){
        out.printf("<p> %s </p>", content);
    }
}
