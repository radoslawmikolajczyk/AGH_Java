package main;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.*;

class ListItemTest {
    protected String output(ListItem listItem) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintStream out = new PrintStream(outputStream);

        listItem.writeHTML(out);

        String output = "";

        output = outputStream.toString(StandardCharsets.UTF_8);

        return output;
    }
    @org.junit.jupiter.api.Test
    void writeHTML() {
        ListItem l1 = new ListItem("Umiejętności");
        String out = this.output(l1);

        assertTrue(out.contains("<li>"));
        assertTrue(out.contains("</li>"));
        assertTrue(out.contains("Umiejętności"));
    }
}