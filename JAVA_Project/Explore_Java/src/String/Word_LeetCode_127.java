package String;

import java.util.*;

public class Word_LeetCode_127  {
    public static void main(String[] args) {

        String start = "hit", end = "cog";
        List<String> wordList = Arrays.asList("hot","dot","dog","lot","log","cog");
        int step = ladderLength(start, end, wordList);
        System.out.println(step + " " + res);

    }

    private static List<String> res = new ArrayList<String>();

    public static int ladderLength(String start, String end, List<String> wordList) {
        Set<String> wordSet = new HashSet<String>(wordList);
        if (wordSet.size() == 0 || !wordSet.contains(end)) {
            return 0;
        }

        Set<String> visited = new HashSet<String>();
//        visited.add(start); visited.add(end);
        Set<String> startVisited = new HashSet<String>();
        startVisited.add(start);
        Set<String> endVisited = new HashSet<String>();
        endVisited.add(end);

        int step = 1;
        while (!startVisited.isEmpty() && !endVisited.isEmpty()) {
            if (startVisited.size() > endVisited.size()) {
                Set<String> temp = startVisited;
                startVisited = endVisited;
                endVisited = temp;
            }
            Set<String> nextVisited = new HashSet<String>();
            for (String word : startVisited) {
                if (replace(word, endVisited, nextVisited, visited, wordSet)) {
                    return ++step;
                }
            }
            startVisited = nextVisited;
            ++step;
        }

        return 0;
    }

    private static boolean replace(String word, Set<String> endVisited, Set<String> nextVisited, Set<String> visited, Set<String> wordSet) {
        char[] ca = word.toCharArray();
        for (int i = 0; i < ca.length; ++i) {
            char oc = ca[i];
            for (char c = 'a'; c <= 'z'; ++c) {
                if (c == ca[i]) {
                    continue;
                }
                ca[i] = c;
                String next = String.valueOf(ca);
                if (wordSet.contains(next)) {
                    if (endVisited.contains(next)) {
                        return true;
                    }
                    if (!visited.contains(next)) {
                        res.add(next);
                        nextVisited.add(next);
                        visited.add(next);
                    }
                }
            }
            ca[i] = oc;
        }
        return false;
    }

}

