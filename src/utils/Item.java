package utils;

import java.util.Iterator;
import java.util.TreeMap;

public class Item implements Comparable<Object>{
    private String name;
    private TreeMap<String, Id> ids;

    public Item(String[] info){
        ids = new TreeMap<>();
        if (info.length > 0)
            this.name = info[0].toLowerCase();
        if (info.length > 1) {
            String[] strs = info[1].split(" \\$, ");
            for (String s : strs) {
                String[] sg = s.split(" : ");
                ids.put(sg[0].toLowerCase(), new Id(sg));
            }
        }
    }

    public Item(String name, String id, String length){
        ids = new TreeMap<>();
        this.name = name.toLowerCase();
        ids.put(id, new Id(id, length));
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public TreeMap<String, Id> getIds() {
        return ids;
    }

    public void addId(Id id) {
        ids.put(id.getId(), id);
    }

    @Override
    public String toString() {
        return name;
    }

    public String getString() {
        return name + " $: " + getIdsString();
    }

    public String names(){
        return "oui";
    }

    @Override
    public int compareTo(Object o) {
        if (o instanceof Item){
            if (name.length() > ((Item) o).name.length())
                return 1;
            if (name.length() < ((Item) o).name.length())
                return -1;
            for (int i = 0; i < name.length(); i++) {
                if (name.charAt(i) > ((Item) o).name.charAt(i)) {
                    return 1;
                }
                if (name.charAt(i) < ((Item) o).name.charAt(i)) {
                    return -1;
                }
            }
        }
        return 0;
    }

    public String getIdsString(){
        Iterator<String> itr = ids.keySet().iterator();
        StringBuilder str = new StringBuilder();
        while (itr.hasNext()){
            Id id = ids.get(itr.next());
            str.append(id.getString());
            if (itr.hasNext())
                str.append(" $, ");
        }
        return str.toString();
    }
}
