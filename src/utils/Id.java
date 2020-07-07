package utils;

public class Id {
    private String id;
    private int length;
    private boolean asciionly;

    public Id(String[] s){
        id = s[0].toLowerCase();
        length = Integer.parseInt(s[1].toLowerCase());
        if (s.length > 2)
            asciionly = Boolean.parseBoolean(s[2]);
        else
            asciionly = false;
    }

    public Id(String id, String length, boolean bool) {
        setAll(id.toLowerCase(), length, bool);
    }

    @Override
    public String toString() {
        return id;
    }

    public String getString() {
        return id + " : " + length + " : " + asciionly;
    }

    public int getLength() {
        return length;
    }

    public String getId() {
        return id;
    }

    public boolean getAscii() {
        return asciionly;
    }

    public void setAll(String id, String length, boolean bool) {
        this.id = id;
        this.asciionly = bool;
        try {
            this.length = Integer.parseInt(length);
        }
        catch (NumberFormatException e){
            this.length = 0;
        }
    }
}
