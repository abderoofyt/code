package JsonTest;

import Json.Decode;
import Robot.IRobot;
import Robot.Position;
import Robot.Types.NormalRobot;
import TerminalView.Display;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class DecodeTest {

    private JSONObject createLaunchResponse() {
        JSONObject response;
        JSONObject data;
        JSONObject state;
        JSONArray position;

        response = new JSONObject();
        data = new JSONObject();
        state = new JSONObject();
        position = new JSONArray();
        response.put("result", "OK");
        //position
        position.add(0);
        position.add(0);
        //data
        data.put("position", position);
        data.put("visibility", 5);
        data.put("reload", 10);
        data.put("repair", 5);
        data.put("mine", 5);
        data.put("shields", 5);
        response.put("data", data);
        //state
        state.put("position", position);
        state.put("direction", "NORTH");
        state.put("shields", 5);
        state.put("shots", 4);
        state.put("status", "NORMAL");
        response.put("state", state);
        return response;
    }

    @Test
    public void decodeLaunchCommand() {
        IRobot robot;
        Decode decoder;

        robot = new NormalRobot("CrashTestDummy");
        decoder = new Decode(createLaunchResponse(), robot, new Display(robot));
        assertEquals(decoder.getCommand(), "launch");
    }

    private JSONObject createStateResponse() {
        JSONObject response;
        JSONObject state;
        JSONArray position;

        response = new JSONObject();
        state = new JSONObject();
        position = new JSONArray();
        //position
        position.add(0);
        position.add(0);
        //state
        state.put("position", position);
        state.put("direction", "NORTH");
        state.put("shields", 5);
        state.put("shots", 4);
        state.put("status", "NORMAL");
        response.put("state", state);
        return response;
    }

    @Test
    public void decodeStateCommand() {
        IRobot robot;
        Decode decoder;

        robot = new NormalRobot("CrashTestDummy");
        decoder = new Decode(createStateResponse(), robot, new Display(robot));
        assertEquals(decoder.getCommand(), "state");
    }

    private JSONObject createLookResponse() {
        JSONObject response;
        JSONObject data;
        JSONObject state;
        JSONArray position;

        response = new JSONObject();
        data = new JSONObject();
        state = new JSONObject();
        position = new JSONArray();
        response.put("result", "OK");
        //position
        position.add(0);
        position.add(0);
        //data
        data.put("objects", new JSONArray());
        response.put("data", data);
        //state
        state.put("position", position);
        state.put("direction", "NORTH");
        state.put("shields", 5);
        state.put("shots", 4);
        state.put("status", "NORMAL");
        response.put("state", state);
        return response;
    }

    @Test
    public void decodeLookCommand() {
        IRobot robot;
        Decode decoder;

        robot = new NormalRobot("CrashTestDummy");
        decoder = new Decode(createLookResponse(), robot, new Display(robot));
        assertEquals(decoder.getCommand(), "look");
    }

    private JSONObject createMovementResponse() {
        JSONObject response;
        JSONObject data;
        JSONObject state;
        JSONArray position;

        response = new JSONObject();
        data = new JSONObject();
        state = new JSONObject();
        position = new JSONArray();
        response.put("result", "OK");
        //position
        position.add(0);
        position.add(0);
        //data
        data.put("message", "Done");
        response.put("data", data);
        //state
        state.put("position", position);
        state.put("direction", "NORTH");
        state.put("shields", 5);
        state.put("shots", 4);
        state.put("status", "NORMAL");
        response.put("state", state);
        return response;
    }

    @Test
    public void decodeMovementResponse() {
        IRobot robot;
        Decode decoder;

        robot = new NormalRobot("CrashTestDummy");
        decoder = new Decode(createMovementResponse(), robot, new Display(robot));
        assertEquals(decoder.getCommand(), "movement");
    }

    private JSONObject createTurnResponse() {
        JSONObject response;
        JSONObject data;
        JSONObject state;
        JSONArray position;

        response = new JSONObject();
        data = new JSONObject();
        state = new JSONObject();
        position = new JSONArray();
        response.put("result", "OK");
        //position
        position.add(0);
        position.add(0);
        //data
        data.put("message", "Done");
        response.put("data", data);
        //state
        state.put("position", position);
        state.put("direction", "SOUTH");
        state.put("shields", 5);
        state.put("shots", 4);
        state.put("status", "NORMAL");
        response.put("state", state);
        return response;
    }

    @Test
    public void decodeTurnResponse() {
        IRobot robot;
        Decode decoder;

        robot = new NormalRobot("CrashTestDummy");
        robot.setPosition(new Position(0,0));
        robot.setDirection(IRobot.Direction.NORTH);
        decoder = new Decode(createTurnResponse(), robot, new Display(robot));
        assertEquals(decoder.getCommand(), "turn");
    }

    private JSONObject createReloadResponse() {
        JSONObject response;
        JSONObject data;
        JSONObject state;
        JSONArray position;

        response = new JSONObject();
        data = new JSONObject();
        state = new JSONObject();
        position = new JSONArray();
        response.put("result", "OK");
        //position
        position.add(0);
        position.add(0);
        //data
        data.put("message", "Done");
        response.put("data", data);
        //state
        state.put("position", position);
        state.put("direction", "NORTH");
        state.put("shields", 5);
        state.put("shots", 4);
        state.put("status", "RELOADING");
        response.put("state", state);
        return response;
    }

    @Test
    public void decodeReloadResponse() {
        IRobot robot;
        Decode decoder;

        robot = new NormalRobot("CrashTestDummy");
        decoder = new Decode(createReloadResponse(), robot, new Display(robot));
        assertEquals(decoder.getCommand(), "reload");
    }

    private JSONObject createRepairResponse() {
        JSONObject response;
        JSONObject data;
        JSONObject state;
        JSONArray position;

        response = new JSONObject();
        data = new JSONObject();
        state = new JSONObject();
        position = new JSONArray();
        response.put("result", "OK");
        //position
        position.add(0);
        position.add(0);
        //data
        data.put("message", "Done");
        response.put("data", data);
        //state
        state.put("position", position);
        state.put("direction", "NORTH");
        state.put("shields", 5);
        state.put("shots", 4);
        state.put("status", "REPAIRING");
        response.put("state", state);
        return response;
    }

    @Test
    public void decodeRepairResponse() {
        IRobot robot;
        Decode decoder;

        robot = new NormalRobot("CrashTestDummy");
        decoder = new Decode(createRepairResponse(), robot, new Display(robot));
        assertEquals(decoder.getCommand(), "repair");
    }

    private JSONObject createMineResponse() {
        JSONObject response;
        JSONObject data;
        JSONObject state;
        JSONArray position;

        response = new JSONObject();
        data = new JSONObject();
        state = new JSONObject();
        position = new JSONArray();
        response.put("result", "OK");
        //position
        position.add(0);
        position.add(0);
        //data
        data.put("message", "Done");
        response.put("data", data);
        //state
        state.put("position", position);
        state.put("direction", "NORTH");
        state.put("shields", 5);
        state.put("shots", 4);
        state.put("status", "SETMINE");
        response.put("state", state);
        return response;
    }

    @Test
    public void decodeMineResponse() {
        IRobot robot;
        Decode decoder;

        robot = new NormalRobot("CrashTestDummy");
        decoder = new Decode(createMineResponse(), robot, new Display(robot));
        assertEquals(decoder.getCommand(), "mine");
    }

    private JSONObject createFireResponse() {
        JSONObject response;
        JSONObject data;
        JSONObject state;

        response = new JSONObject();
        data = new JSONObject();
        state = new JSONObject();
        response.put("result", "OK");
        //data
        data.put("message", "Miss");
        response.put("data", data);
        //state
        state.put("shots", 0);
        response.put("state", state);
        return response;
    }

    @Test
    public void decodeFireResponse() {
        IRobot robot;
        Decode decoder;

        robot = new NormalRobot("CrashTestDummy");
        decoder = new Decode(createFireResponse(), robot, new Display(robot));
        assertEquals(decoder.getCommand(), "fire");
    }

}
