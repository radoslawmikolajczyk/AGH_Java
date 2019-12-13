package main;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.*;

class PhotoTest {
    protected String output(Photo photo) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintStream out = new PrintStream(outputStream);

        photo.writeHTML(out);

        String output = "";

        output = outputStream.toString(StandardCharsets.UTF_8);

        return output;
    }
    @org.junit.jupiter.api.Test
    void writeHTML() {
        Photo photo = new Photo("http://example.com");
        String out = this.output(photo);
        assertTrue(out.contains("<img"));
        assertTrue(out.contains("src=\"http://example.com\""));
        assertTrue(out.contains("/>"));
    }
}