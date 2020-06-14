package utils;

public class Id {
    private String id;
    private int length;

    public Id(String[] s){
        id = s[0].toLowerCase();
        length = Integer.parseInt(s[1].toLowerCase());
    }

    public Id(String id, String length) {
        setIdAndLength(id.toLowerCase(), length);
    }

    @Override
    public String toString() {
        return id;
    }

    public String getString() {
        return id + " : " + length;
    }

    public int getLength() {
        return length;
    }

    public String getId() {
        return id;
    }

    public void setIdAndLength(String id, String length) {
        this.id = id;
        try {
            this.length = Integer.parseInt(length);
        }
        catch (NumberFormatException e){
            this.length = 0;
        }
    }
}
