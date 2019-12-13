package main;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

public class UnorderedList {
    List<ListItem> itemList = new ArrayList<>();

    UnorderedList addListItem(String text){
        this.itemList.add(new ListItem(text));
        return this;
    }
    void writeHTML(PrintStream out){
        for (ListItem li : itemList)
            li.writeHTML(out);
    }
}
