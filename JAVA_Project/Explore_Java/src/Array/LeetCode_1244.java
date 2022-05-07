package Array;

public class LeetCode_1244 {
    public static void main(String[] args) {
        Leaderboard leaderboard = new Leaderboard();
        leaderboard.addScore(1, 73);
        leaderboard.addScore(3,39);
        System.out.println("初始时参赛人数：" + leaderboard.count);
        leaderboard.top(1);
        leaderboard.reset(1);
        System.out.println("重置后参赛人数：" + leaderboard.count);
        leaderboard.addScore(1, 100);
        System.out.println("再次添加后参赛人数：" + leaderboard.count);
        leaderboard.top(2);
    }

    static class Leaderboard {

        public Leaderboard() {}
        private int[][] array = new int[12000][2];
        int count = 0; //统计当前参赛选手的人数

        public void addScore(int playerId, int score) {
            int[] Input = new int[2];
            Input[0] = playerId; Input[1] = score;
            int pos = search(playerId);
            if(pos == -1) {
                pos = count;
            }
            insertSort(Input,pos);
        }

        public int top(int K) {
            int tempInd = K > count?count:K;
            int sum = 0;
            for(int i= 0;i < tempInd;i++){
                sum += array[i][1];
            }
            return sum;
        }

        public void reset(int playerId) {
            int ret = search(playerId);
            for(int i = ret+1;i<count;i++){
                array[i-1] = array[i];
            }
            --count;
        }

        private void insertSort(int[] Input,int pos) {
            int i = pos;
            //Update the leaderboard by adding score to the given player's score
            if(pos != count){ //参赛者已在排行榜上，此时pos一定小于count
                Input[1] = array[pos][1]+ Input[1];
            } else {
                count++;
            }
            if(count != 0){ //利用插入排序维护从大到小的顺序
                for (i = pos; i > 0 && Input[1] > array[i-1][1]; i--) {
                    array[i] = array[i-1];
                }
            }
            array[i] = Input;
        }

        public int search(int playerId){
            for(int i = 0; i < count;i++){
                if(array[i][0] == playerId) {
                    return i;
                }
            }
            return -1;
        }

    }
}
