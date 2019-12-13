package main;

import java.io.PrintStream;

public class Photo {
    Photo(String url){
        this.url =url;
    }
    String url;
    void writeHTML(PrintStream out){
        out.printf("<img src=\"%s\" alt=\"Smiley face\" height=\"200\" width=\"200\"/>\n",url);
    }
}
