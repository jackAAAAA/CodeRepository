package Tree;

import java.util.*;

//TO DO:
//1.参照LeetCode_112/LeetCode_113来完成本题；
//2.总结函数Arrays.asList()的用法。
//2021.10.20完成

/**
 * 根节点箬与要查找的target相同，那么仅会返回包含根节点的集合。
 */

public class FindPathInNode {
    public static void main(String[] args) {
        Deque<String> nodes = new ArrayDeque<>();
        nodes.addAll(Arrays.asList("1,5,X,X,0,3,4,X,X,5,X,X,".split(",")));
        TreeNode root = new TreeNode().buildTree(nodes);
        root.print();
        int target = 5;
        List<List<Integer>> res = findPathInNode(root, target);
        if (res.size() > 0) {
            int len = res.size();
            for (int i = 0; i < len; ++i) {
                for (int j = 0; j < res.get(i).size(); ++j) {
                    System.out.print(res.get(i).get(j) + " ");
                }
                System.out.println();
            }
        } else {
            System.out.println("The target doesn't in the tree!");
        }
    }

    static List<List<Integer>> findPathInNode(TreeNode root, int target) {
        List<List<Integer>> res = new ArrayList<>();
        find(root, target, new ArrayList<>(), res);
        return res;
    }

    static void find(TreeNode root, int target, List<Integer> templist, List<List<Integer>> res) {
        if (root == null) {
            return;
        }
        templist.add(root.value);
        if (root.value == target) {
            res.add(new ArrayList<>(templist));
            templist.remove(templist.size() - 1);
            return;
        }
        find(root.left, target, templist, res);
        find(root.right, target, templist, res);
        templist.remove(templist.size() - 1);
    }
}
