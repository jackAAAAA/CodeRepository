import java.util.HashSet;
import java.util.Set;

public class homework_week4 {

    //LemonadeChange
    private boolean lemonadeChange(int[] bills) {
        int five = 0, ten = 0;
        for (int bill : bills) {
            if (bill == 0) {
                ++five;
            }else if (bill == 10) {
                if (five == 0) {
                    return false;
                }
                five--;
                ten++;
            }else {
                if (five > 0 && ten > 0) {
                    five--;
                    ten--;
                }else if (five >= 0) {
                    five -= 3;
                }else {
                    return false;
                }
            }
        }
        return true;
    }

    //Robot Walking
    public int RobotSim(int[] commands,int[][] obstacles) {
        int[] dx = new int[]{0,1,0,-1};
        int[] dy = new int[]{1,0,-1,0};
        int x = 0, y = 0, di = 0;

        Set<Long> obstacleSet = new HashSet();
        for (int[] obstacle: obstacles) {
            long ox = (long) obstacle[0] + 30000;
            long oy = (long) obstacle[0] + 30000;
            obstacleSet.add((ox << 10) + oy);
        }

        int ans = 0;
        for (int cmd: commands) {
            if(cmd == -2) {
                di = (di + 3) % 4;
            }else if (cmd == -1) {
                di = (di + 3) % 4;
            }else {
                for (int k = 0; k < cmd; ++k) {
                    int nx = x + dx[di];
                    int ny = y + dy[di];
                    long code = (((long) nx + 30000) << 16) + ((long) ny + 30000);
                    if (!obstacleSet.contains(code)) {
                        x = nx;
                        y = ny;
                        ans = Math.max(ans,x*x+y*y);
                    }
                }
            }
        }
        return ans;
    }

}
