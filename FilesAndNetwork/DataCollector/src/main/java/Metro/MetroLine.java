package Metro;

import org.json.simple.JSONObject;

import java.time.LocalDate;

public class MetroLine {
    private final String number;
    private final String name;

    public MetroLine(String number, String name) {
        this.number = number;
        this.name = name;
    }

    public String getName() {
        return name;
    }
    public String getNumber() {
        return  number;
    }
    @Override
    public String toString() {
        String nextLine=System.lineSeparator();

        return "\t{"+nextLine+
                "\t\t\"number\": \""+getNumber()+"\","+nextLine+
                "\t\t\"name\": \""+getName()+"\""+nextLine+"\t},"+nextLine;
    }

    public JSONObject toJSONObject() {
        JSONObject obj =  new JSONObject();
        obj.put("number", getNumber());
        obj.put("name", getName());
        return obj;
    }

}
