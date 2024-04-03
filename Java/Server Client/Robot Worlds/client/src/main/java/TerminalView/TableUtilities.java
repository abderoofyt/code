
package TerminalView;

import Robot.IRobot;
import Robot.Position;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TableUtilities {
        private IRobot robot;

        //do not delete
        public TableUtilities(IRobot robot) {
            this.robot = robot;
        }

        private String pad(String s, int n) {
            String s1;

            s1 = String.format("%" + n + "s", s);
            return String.format("%-" + n + "s", s1);
        }

        private String makeTextBold(String s) {
            return "\033[0;1m"+s+"\u001B[0m";
        }

        private String createTable(List<List<String>> rows) {
            StringBuilder sb;

            sb = new StringBuilder();
            sb.append("+--------------------------------------------------------------------+\n");
            sb.append("| "+makeTextBold("ROBOT HEALTH")+"                                                       |\n");
            sb.append("+--------------------------------------------------------------------+\n");
            for (int i = 0; i < rows.size(); i++) {
                List<String> row = rows.get(i);
                for (int j = 0; j < row.size(); j++) {
                    String item = row.get(j);
                    item = (i == 0) ? "\u001B[41m"+pad(item, 12)+"\u001B[0m" : pad(item, 12);
                    sb.append("|"+item+"|");
                }
                sb.append("\n");
            }
            sb.append("+--------------------------------------------------------------------+");
            return sb.toString();
        }

        private String createPositionField(Position position) {
            if (position == null) {
                return "";
            }
            return "["+position.getX()+","+position.getY()+"]";
        }

        private List<List<String>> createRows(IRobot robot) {
            List<List<String>> rows;

            rows = new ArrayList<>();
            rows.add(Arrays.asList("POSITION", "DIRECTION", "SHIELDS","SHOTS", "STATUS"));
            rows.add(Arrays.asList(createPositionField(robot.getPosition()), robot.getDirection().toString(), String.valueOf(robot.getShieldStrength()), String.valueOf(robot.getShots()), "NORMAL"));
            return rows;
        }

        //do not delete
        public String getHealthTable() {
            return createTable(createRows(robot));
        }

}
