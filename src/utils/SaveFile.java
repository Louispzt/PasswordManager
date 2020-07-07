package utils;

import java.io.*;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.TreeMap;

public class SaveFile {
    public String path;
    public TreeMap<String, Item> items;
    private ArrayList<String> arr;
    private RSA rsa;

    public SaveFile(RSA rsa) throws IOException {
        this.rsa = rsa;
        items = new TreeMap<>();
        path = System.getProperty("user.dir") + "\\" + "save";
        open();
    }

    public void open() throws IOException {
        items.clear();
        File file = new File(path);
        arr = new ArrayList<>();
        if (!file.exists())
            return;
        BufferedReader br = new BufferedReader(new FileReader(file));
        String line = br.readLine();
        while (line != null) {
            if (line.startsWith("id: ")){
                String key = line.substring(4);
                BigInteger bi = new BigInteger(key);
                if (rsa.decode(bi).equals("saveFile")){
                    while ((line = br.readLine()) != null && !line.startsWith("id: ")){
                        if (line.equals(""))
                            continue;
                        String[] strs = rsa.decode(line).split(" \\$: ");
                        items.put(strs[0], new Item(strs));
                    }
                }
                else{
                    arr.add(line);
                    while ((line = br.readLine()) != null && !line.startsWith("id: ")){
                        arr.add(line);
                    }
                }
            } else {
                line = br.readLine();
            }
        }
    }

    public void save() throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter(path));
        writer.write("id: " + rsa.code("saveFile", false) +"\n");
        for (String str : items.keySet()){
            writer.write(rsa.code(items.get(str).getString(), false)+"\n");
        }
        for (String str : arr){
            writer.write(str+"\n");
        }
        writer.close();
    }
}
