/*
 * @lc app=leetcode.cn id=874 lang=java
 *
 * [874] 模拟行走机器人
 */

// @lc code=start
class Solution {
    public int robotSim(int[] commands, int[][] obstacles) {
        int[][] dire = {{0, 1}, {1, 0}, {0, -1}, {-1, 0}};
        Set<Pair<Integer, Integer>> obSet = new HashSet<>();
        for (int i = 0; i < obstacles.length; ++i) {
            obSet.add(new Pair<>(obstacles[i][0], obstacles[i][1]));
        }
        int curdire = 0, curX = 0, curY = 0, ans = 0;
        for (int com : commands) {
            if (com == -1) {
                curdire = (curdire + 1) % 4;
            } else if (com == -2) {
                curdire = (curdire + 3) % 4;
            } else {
                while (com-- > 0) {
                    int newX = curX + dire[curdire][0];
                    int newY = curY + dire[curdire][1];
                    if (!obSet.contains(new Pair<>(newX, newY))) {
                        curX = newX; curY = newY;
                        ans = Math.max(ans, curX * curX + curY * curY);
                    } else {
                        break;
                    }
                }
            }
        }
        return ans;
    }
}
// @lc code=end

