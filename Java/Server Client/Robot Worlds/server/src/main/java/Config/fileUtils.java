package Config;

import Robot.Position;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class fileUtils {

    /**
     * Creates JSONObject with the boundary's top left and bottom right positions.
     *
     * @param topLeftX       the top left x-value of the 'world'-simulation's boundary.
     * @param topLeftY       the top left y-value of the 'world'-simulation's boundary.
     * @param bottomRightX   the bottom right x-value of the 'world'-simulation's boundary.
     * @param bottomRightY   the bottom right y-value of the 'world'-simulation's boundary.
     * @return the JSONObject containing the boundary's top left and bottom right positions.
     */
    private static JSONObject createBoundaryJsonObject(int topLeftX, int topLeftY, int bottomRightX, int bottomRightY) {
        JSONObject boundary;
        JSONArray topLeft;
        JSONArray bottomRight;

        boundary = new JSONObject();
        topLeft = new JSONArray();
        bottomRight = new JSONArray();
        topLeft.add(topLeftX);
        topLeft.add(topLeftY);
        boundary.put("topLeftPosition", topLeft);
        bottomRight.add(bottomRightX);
        bottomRight.add(bottomRightY);
        boundary.put("bottomRightPosition", bottomRight);
        return boundary;
    }

    /**
     * Creates a JSONObject object containing the 'world'-simulation's boundary positions,
     * visibility, shield repair time, weapon reload time, mine set time, and max shield
     * strength.
     *
     * @return the JSONObject object containing the 'world'-simulation's information.
     */
    private static JSONObject createWorldJson() {
        JSONObject obj;

        obj = new JSONObject();
        //boundary coordinates (bottomLeft/topRight)
        obj.put("boundary", createBoundaryJsonObject(-200,200,200,-200));
        //boundary width in steps
        obj.put("boundaryWidth", 400);
        //boundary height in steps
        obj.put("boundaryHeight", 400);
        //amount of steps the robot can see forward/right/back/left
        obj.put("visibility", 10);
        //shield repair time in seconds
        obj.put("shieldRepairTime", 10);
        //weapon reload time in seconds
        obj.put("weaponReloadTime", 10);
        //mine set time in seconds
        obj.put("mineSetTime", 10);
        //shield strength max in hits
        obj.put("maxShieldStrength", 10);
        return obj;
    }

    /**
     * Retrieves the file path or the file with the given name.
     *
     * @param filename   the file name of the file to determine
     *                   the file path of.
     * @return the file path of the file.
     * @throws IOException if the file is not found.
     */
    private static String getFilePath(String filename) throws IOException {
        File f;

        f = new File(filename);
        if (!f.exists()) {
            f.createNewFile();
        }
        return f.getAbsolutePath();
    }

    /**
     * Adds the 'world'-simulation's JSONObject object to the configuration file.
     *
     * @return true  : if the JSONObject object was added to th configuration file.
     *         false : if the JSONObject object was not added to the configuration file.
     */
    public static boolean addJsonToFile() {
        FileWriter writer;
        String filename;

        filename = "src/main/java/Config/.config.txt";
        try {
            writer = new FileWriter(getFilePath(filename));
            writer.write(createWorldJson().toString());
            writer.flush();
            writer.close();
            return true;
        } catch (IOException e) {
            return false;
        }
    }

    /**
     * Retrieves the JSONObject object from a file with the given file path.
     *
     * @param filepath   the file path of the file containing a JSONObject object.
     * @return the JSONObject : the JSONObject object retrieved from the file.
     *         null           : if the file does not contain a JSONObject object.
     */
    private static JSONObject getJsonFileContent(String filepath) {
        JSONParser parser;

        parser = new JSONParser();
        try {
            return (JSONObject) parser.parse(new FileReader(filepath));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Retrieves the given key's value from the JSONObject object.
     *
     * @param obj   the JSONObject.
     * @param key   the key used to retrieve the value from the JSONObject.
     * @return   the given key's value.
     */
    private static Object getValueFromJson(JSONObject obj, String key) {
        return obj.get(key);
    }

    /**
     * Retrieve the 'world'-simulation's top left boundary position from the
     * configuration file.
     *
     * @return the top left boundary Position object.
     */
    public static Position getBoundaryTopLeft() {
        JSONObject obj;
        JSONArray topLeft;
        int x;
        int y;

        try {
            obj = getJsonFileContent(getFilePath("src/main/java/Config/.config.txt"));
            topLeft = (JSONArray) getValueFromJson((JSONObject) obj.get("boundary"), "topLeftPosition");
            x = ((Number)topLeft.get(0)).intValue();
            y = ((Number)topLeft.get(1)).intValue();
            return new Position(x, y);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Retrieve the 'world'-simulation's bottom right boundary position from the
     * configuration file.
     *
     * @return the bottom right boundary Position object.
     */
    public static Position getBoundaryBottomRight() {
        JSONObject obj;
        JSONArray bottomRight;
        int x;
        int y;

        try {
            obj = getJsonFileContent(getFilePath("src/main/java/Config/.config.txt"));
            bottomRight = (JSONArray) getValueFromJson((JSONObject) obj.get("boundary"), "bottomRightPosition");
            x = ((Number)bottomRight.get(0)).intValue();
            y = ((Number)bottomRight.get(1)).intValue();
            return new Position(x, y);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Retrieve the 'world'-simulation's weapon reload time from the
     * configuration file.
     *
     * @return the weapon reload time.
     */
    public static int getWeaponReloadTime() {
        JSONObject obj;

        try {
            obj = getJsonFileContent(getFilePath("src/main/java/Config/.config.txt"));
            return ((Number)getValueFromJson(obj, "weaponReloadTime")).intValue();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return -1;
    }

    /**
     * Retrieve the 'world'-simulation's visibility variable from the
     * configuration file.
     *
     * @return the visibility variable.
     */
    public static int getVisibility() {
        JSONObject obj;

        try {
            obj = getJsonFileContent(getFilePath("src/main/java/Config/.config.txt"));
            return ((Number)getValueFromJson(obj, "visibility")).intValue();
        } catch (Exception e) {
            return -1;
        }
    }

    /**
     * Retrieve the 'world'-simulation's shield repair time from the
     * configuration file.
     *
     * @return the shield repair time.
     */
    public static int getRepairTime() {
        JSONObject obj;

        try {
            obj = getJsonFileContent(getFilePath("src/main/java/Config/.config.txt"));
            return ((Number)getValueFromJson(obj, "shieldRepairTime")).intValue();
        } catch (Exception e) {
            return -1;
        }
    }

    /**
     * Retrieve the 'world'-simulation's mine set time from the
     * configuration file.
     *
     * @return the mine set time.
     */
    public static int getTimeToSetMine() {
        JSONObject obj;

        try {
            obj = getJsonFileContent(getFilePath("src/main/java/Config/.config.txt"));
            return ((Number)getValueFromJson(obj, "mineSetTime")).intValue();
        } catch (Exception e) {
            return -1;
        }
    }

    /**
     * Retrieve the 'world'-simulation's maximum shield strength from the
     * configuration file.
     *
     * @return the maximum shield strength.
     */
    public static int getMaxShieldStrength() {
        JSONObject obj;

        try {
            obj = getJsonFileContent(getFilePath("src/main/java/Config/.config.txt"));
            return ((Number)getValueFromJson(obj, "maxShieldStrength")).intValue();
        } catch (Exception e) {
            return -1;
        }
    }
}
