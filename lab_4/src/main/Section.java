package main;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

public class Section {
    String title;
    List<Paragraph> paragraps = new ArrayList<>();

    Section setTitle(String title){
        this.title = title;
        return this;
    }
    Section addParagraph(String paragraphText){
        this.paragraps.add(new Paragraph().setContent(paragraphText));
        return this;
    }
    Section addParagraph(Paragraph p){
        this.paragraps.add(p);
        return this;
    }
    Section addParagraph(ParagraphWithList p){
        this.paragraps.add(p);
        return this;
    }

    void writeHTML(PrintStream out){
        out.printf("<h2>%s</h2>", this.title);
        for (Paragraph p : paragraps){
            p.writeHTML(out);
        }
    }
}
