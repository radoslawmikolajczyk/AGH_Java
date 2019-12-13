package main;

import java.io.PrintStream;

public class ListItem {
    String content;

    ListItem (String content){
        this.content = content;
    }
    void writeHTML(PrintStream out){
        out.printf("<li> %s </li>", content);
    }
}
