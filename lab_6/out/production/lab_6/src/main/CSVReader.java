package main;

import java.io.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CSVReader {
    BufferedReader reader;
    String delimiter;
    boolean hasHeader;

    List<String> columnLabels = new ArrayList<>();
    Map<String,Integer> columnLabelsToInt = new HashMap<>();

    void parseHeader() throws IOException {
        String line = reader.readLine();
        if (line == null) {
            return;
        }
        String[] header = line.split(delimiter);

        for (int i = 0; i < header.length; i++) {
            columnLabels.add(header[i]);
            columnLabelsToInt.put(header[i],i);
        }
    }
    public CSVReader(String filename,String delimiter,boolean hasHeader) throws IOException {
        reader = new BufferedReader(new FileReader(filename));
        this.delimiter = delimiter;
        this.hasHeader = hasHeader;
        if(hasHeader)
            parseHeader();
    }
    String[]current;
    boolean next() {
        String line = null;
        try {
            line = this.reader.readLine();
        } catch (IOException e) {
            return false;
        }
        if (line == null)
            return false;


        current = this.split(line);
        return true;
    }
    CSVReader(String filename, String delimiter) throws FileNotFoundException {
        reader = new BufferedReader(new FileReader(filename));
        this.delimiter = delimiter;
    }
    CSVReader(String filename) throws FileNotFoundException {
        reader = new BufferedReader(new FileReader(filename));
    }
    public CSVReader(Reader reader, String delimiter, boolean hasHeader) throws IOException {
        this.reader = new BufferedReader(reader);
        this.delimiter = delimiter;
        this.hasHeader = hasHeader;
        if (hasHeader)
            parseHeader();
    }
    CSVReader(CSVReader r) throws IOException {
        this(r.reader,r.delimiter,r.hasHeader);
    }

    List<String> getColumnLabels(){
        return this.columnLabels;
    }

    int getRecordLength(){
        return this.current.length;
    }

    boolean isMissing(int columnLabel){
        try {
            Object a = this.current[columnLabel];
        } catch (ArrayIndexOutOfBoundsException ex){
            return true;
        }
        return !columnLabelsToInt.containsValue(columnLabel);
    }

    boolean isMissing(String columnLabel) {
        return !this.columnLabelsToInt.containsKey(columnLabel);
    }

    String get(int columnIndex){
        if (isMissing(columnIndex) && this.hasHeader)
            return "-1";
        if (this.trimQuotes(this.current[columnIndex]).isEmpty())
            return "-1";
        return this.trimQuotes(this.current[columnIndex]);
    }
    String get(String columnLabel){
        return this.get(this.getColumnIndex(columnLabel));
    }
    int getInt(int columnIndex){
        return Integer.parseInt(get(columnIndex));
    }
    int getInt(String columnLabel){
        return Integer.parseInt(get(columnLabel));
    }
    long getLong(int columnIndex){
        return Long.parseLong(get(columnIndex));
    }
    long getLong(String columnLabel){
        return Long.parseLong(get(columnLabel));
    }
    double getDouble(int columnIndex){
        return Double.parseDouble(get(columnIndex));
    }
    double getDouble(String columnLabel){
        return Double.parseDouble(get(columnLabel));
    }

    public LocalTime getTime(String columnName, String format) {
        return LocalTime.parse(this.get(columnName), DateTimeFormatter.ofPattern(format));
    }

    public LocalTime getTime(int columnIndex, String format) {
        return LocalTime.parse(this.get(columnIndex), DateTimeFormatter.ofPattern(format));
    }

    public LocalDate getDate(String columnName, String format) {
        return LocalDate.parse(this.get(columnName), DateTimeFormatter.ofPattern(format));
    }

    public LocalDate getDate(int columnIndex, String format) {
        return LocalDate.parse(this.get(columnIndex), DateTimeFormatter.ofPattern(format));
    }

    protected String[] split(String line) {
        String[] splitLine;
        splitLine = line.split(this.delimiter +"(?=([^\"]*\"[^\"]*\")*[^\"]*$)",-1);

        for (int i = 0; i < splitLine.length; i++) {
            splitLine[i] = this.trimQuotes(splitLine[i]);
        }


        return splitLine;
    }

    protected String trimQuotes(String str) {
        return str.replaceAll("^\"", "").replaceAll("\"$", "");
    }

    protected int getColumnIndex(String columnName) {
        if (isMissing(columnName))
            return -1;
        return this.columnLabelsToInt.get(columnName);
    }
}

