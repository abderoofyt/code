package JsonUtilitiesTests;

import JsonUtils.Decode;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.junit.Test;
import World.World;

import static org.junit.Assert.assertTrue;

public class JsonDecodeTests {

    @Test
    public void testNoArgForward() {
        Decode decoder;
        JSONObject response;

        response = new JSONObject();
        response.put("robot", "CrashTestDummy");
        response.put("command", "forward");
        response.put("arguments", new JSONArray());
        decoder = new Decode(new World(), response);
        assertTrue(decoder.getArguments().isBlank());
    }

}
