package utils;

import java.io.*;
import java.util.TreeMap;

public class SaveFile {
    public String path;
    public TreeMap<String, Item> items;

    public SaveFile(String name) throws IOException {
        items = new TreeMap<>();
        path = System.getProperty("user.dir") + "\\" + name;
        open();
    }

    public void open() throws IOException {
        items.clear();
        File file = new File(path);
        if (!file.exists())
            return;
        BufferedReader br = new BufferedReader(new FileReader(file));
        String line;
        while ((line = br.readLine()) != null) {
            if (!line.equals("")) {
                String[] strs = line.split(" \\$: ");
                items.put(strs[0], new Item(line.split(" \\$: ")));
            }
        }
    }

    public void save() throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter(path));
        for (String str : items.keySet()){
            if (items.get(str).getName().equals("New"))
                continue;
            writer.write(items.get(str).getString()+"\n");
        }
        writer.close();
    }
}
