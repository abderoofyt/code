package JsonTest;

import Json.Utilities;
import org.json.simple.JSONObject;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class UtilitiesTest {

    @Test
    public void testJsonObject() {
        Utilities utils;
        JSONObject obj;

        utils = new Utilities();
        obj = new JSONObject();
        obj.put("test", "a");
        assertEquals((String)utils.stringToJson(obj.toString()).get("test"), "a");
    }

    @Test
    public void testEmptyJsonObject() {
        Utilities utils;
        JSONObject obj;

        utils = new Utilities();
        obj = new JSONObject();
        assertEquals(utils.stringToJson(obj.toString()).toString(), "{}");
    }

    @Test
    public void testString() {
        Utilities utils;

        utils = new Utilities();
        assertEquals(utils.stringToJson("hello"), null);
    }

}
