package Json;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class Utilities {

    public Utilities() {

    }

    /**
     * Converts the given String into a JSONObject. If the String cannot be converted,
     * the function returns null.
     *
     * @param s   the String to convert into a JSONObject.
     * @return JSONObject : if the String can be converted into a JSONObject.
     *         null       : if the String cannot be converted into a JSONObject.
     */
    public JSONObject stringToJson(String s) {
        JSONParser parser;

        try {
            parser = new JSONParser();
            return (JSONObject) parser.parse(s);
        } catch (Exception ParseException) {
            return null;
        }
    }

}
