package main;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import java.io.FileReader;
import java.io.IOException;
import java.io.StringReader;
import java.time.format.DateTimeFormatter;

import static org.junit.jupiter.api.Assertions.*;

class CSVReaderTest {

    @Test
    void test_exception_is_thrown_for_invalid_reader() {
        Executable callback = () -> new CSVReader("non-existent-file.csv", ",", true);

        assertThrows(IOException.class, callback, "Exception was expected to be thrown for non-existent file.");
    }

    @Test
    void test_header_is_read_correctly() {
        String text = "a,b,c\n123.4,567.8,91011.12";

        try {
            CSVReader reader = new CSVReader(new StringReader(text), ",", true);

            assertArrayEquals(new String[]{"a", "b", "c"}, reader.getColumnLabels().toArray());
        } catch (IOException e) {
            fail("Exception was thrown, but was not expected");
        }
    }

    @Test
    void test_values_are_returned_correctly_when_header_is_available() {
        String text = "name,age,balance,pesel,date,time\nJohn Doe,20,11213.95,13311494985,27-11-2017,11:12:20";

        try {
            CSVReader reader = new CSVReader(new StringReader(text), ",", true);

            reader.next();

            assertEquals("John Doe", reader.get("name"));
            assertEquals(20, reader.getInt("age"));
            assertEquals(11213.95D, reader.getDouble("balance"));
            assertEquals(13311494985L, reader.getLong("pesel"));
            assertEquals("2017-11-27", reader.getDate("date", "dd-MM-yyyy").format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
            assertEquals("11:12", reader.getTime("time", "HH:mm:ss").format(DateTimeFormatter.ofPattern("HH:mm")));
        } catch (IOException e) {
            fail("Exception was thrown, but was not expected");
        }
    }


    @Test
    void test_values_are_returned_correctly_with_no_header() {
        String text = "John Doe,20,11213.95,13311494985";

        try {
            CSVReader reader = new CSVReader(new StringReader(text), ",", false);

            reader.next();

            assertEquals("John Doe", reader.get(0));
            assertEquals(20, reader.getInt(1));
            assertEquals(11213.95D, reader.getDouble(2));
            assertEquals(13311494985L, reader.getLong(3));
        } catch (IOException e) {
            fail("Exception was thrown, but was not expected");
        }
    }

    @Test
    void test_values_are_returned_correctly_with_colon_as_delimiter() {
        String text = "name;age;balance;pesel\nJohn Doe;20;11213.95;13311494985";

        try {
            CSVReader reader = new CSVReader(new StringReader(text), ";", true);

            reader.next();

            assertEquals("John Doe", reader.get("name"));
            assertEquals(20, reader.getInt("age"));
            assertEquals(11213.95D, reader.getDouble("balance"));
            assertEquals(13311494985L, reader.getLong("pesel"));
        } catch (IOException e) {
            fail("Exception was thrown, but was not expected");
        }
    }

    @Test
    void test_multiple_lines_are_correctly_read() {
        String text = "name,age,balance,pesel\nJohn Doe,20,11213.95,13311494985\nMiranda Jones,21,27234.24,93110158709";

        try {
            CSVReader reader = new CSVReader(new StringReader(text), ",", true);

            assertTrue(reader.next());

            assertEquals("John Doe", reader.get("name"));
            assertEquals(20, reader.getInt("age"));
            assertEquals(11213.95D, reader.getDouble("balance"));
            assertEquals(13311494985L, reader.getLong("pesel"));

            assertTrue(reader.next());

            assertEquals("Miranda Jones", reader.get("name"));
            assertEquals(21, reader.getInt("age"));
            assertEquals(27234.24D, reader.getDouble("balance"));
            assertEquals(93110158709L, reader.getLong("pesel"));

            assertFalse(reader.next());
        } catch (IOException e) {
            fail("Exception was thrown, but was not expected");
        }
    }

    @Test
    void test_user_can_get_all_elements_when_lines_are_uneven() {
        String text = "1,2,3\n1,2";

        try {
            CSVReader reader = new CSVReader(new StringReader(text), ",", false);

            reader.next();

            assertEquals(3, reader.getRecordLength());

            reader.next();

            assertEquals(2, reader.getRecordLength());
        } catch (IOException e) {
            fail("Exception was thrown, but was not expected");
        }
    }

    @Test
    void test_when_invalid_line_is_encountered_is_missing_returns_true() {
        String text = "a,b,c\n1,2,3\n1,2\n1,2,3";

        try {
            CSVReader reader = new CSVReader(new StringReader(text), ",", true);

            reader.next();

            assertFalse(reader.isMissing("a") || reader.isMissing(0));
            assertFalse(reader.isMissing("b") || reader.isMissing(1));
            assertFalse(reader.isMissing("c") || reader.isMissing(2));
            assertTrue(reader.isMissing("d") && reader.isMissing(3));

            reader.next();

            assertFalse(reader.isMissing("a") || reader.isMissing(0));
            assertFalse(reader.isMissing("b") || reader.isMissing(1));
            assertFalse(reader.isMissing("c") && reader.isMissing(2));
            assertTrue(reader.isMissing("d") && reader.isMissing(3));

            reader.next();

            assertFalse(reader.isMissing("a") || reader.isMissing(0));
            assertFalse(reader.isMissing("b") || reader.isMissing(1));
            assertFalse(reader.isMissing("c") || reader.isMissing(2));
            assertTrue(reader.isMissing("d") && reader.isMissing(3));
        } catch (IOException e) {
            fail("Exception was thrown, but was not expected");
        }
    }
}