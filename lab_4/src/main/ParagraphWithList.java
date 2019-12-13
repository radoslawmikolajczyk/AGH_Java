package main;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

public class ParagraphWithList extends Paragraph {
    List<UnorderedList> unorderedLists;
    ParagraphWithList () {
        super();
        this.unorderedLists = new ArrayList<>();
    }
    ParagraphWithList addItemToList(UnorderedList ul){
        this.unorderedLists.add(ul);
        return this;
    }
    ParagraphWithList addListItem(String content){
        this.unorderedLists.add(new UnorderedList().addListItem(content));
        return this;
    }

    public ParagraphWithList setContent(String content) {
        super.setContent(content);
        return this;
    }

    public void writeHTML(PrintStream out) {
        out.printf("<p><ul>%s", this.content);
        for (UnorderedList ul : unorderedLists){
            ul.writeHTML(out);
        }
        out.print("</ul></p>");
    }
}
