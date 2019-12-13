package main;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

public class Document {
    String title;
    Photo photo;
    List<Section> sections = new ArrayList<>();

    Document setTitle(String title){
        this.title = title;
        return this;
    }

    Document setPhoto(String photoUrl){
        this.photo = new Photo(photoUrl);
        return this;
    }

    Section addSection(String sectionTitle){
        Section s = new Section();
        s.setTitle(sectionTitle);
        this.sections.add(s);
        return s;
    }
    Document addSection(Section s){
        this.sections.add(s);
        return this;
    }

    void writeHTML(PrintStream out){
        // zapisz niezbędne znaczniki HTML
        // dodaj tytuł i obrazek
        // dla każdej sekcji wywołaj section.writeHTML(out)
        out.printf("<?xml version=\"1.0\"?>\n" +
                "<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Strict//EN\"\n" +
                "  \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd\">\n" +
                "<html xmlns=\"http://www.w3.org/1999/xhtml\">\n" +
                "<head><title> %s </title> <meta http-equiv=\"Content-Type\" content=\"application/xhtml+xml;\n" +
                "charset=UTF-8\" /> </head><body>",title);
        out.printf("<h1> %s </h1>", this.title);
        photo.writeHTML(out);

        for(Section s : this.sections){
            s.writeHTML(out);
        }
        out.print("</body></html>");
    }

}
