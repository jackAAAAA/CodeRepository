package String;

import java.util.*;

public class Word_LeetCode_126 {

    public static List<List<String>> findLadders(String beginWord, String endWord, List<String> wordList) {
        // 先将 wordList 放到哈希表里，便于判断某个单词是否在 wordList 里
        List<List<String>> res = new ArrayList<List<String>>();
        Set<String> wordSet = new HashSet<>(wordList);
        if (!wordSet.contains(endWord)) {
            return res;
        }
        // 第 1 步：使用双向广度优先遍历得到后继结点列表 successors
        // key：字符串，value：广度优先遍历过程中 key 的后继结点列表
        Map<String, Set<String>> successors = new HashMap<>();
        boolean found = bidirectionalBfs(beginWord, endWord, wordSet, successors);
        if (!found) {
            return res;
        }
        // 第 2 步：基于后继结点列表 successors ，使用回溯算法得到所有最短路径列表
        Deque<String> path = new ArrayDeque<String>();
        path.addLast(beginWord);
        dfs(beginWord, endWord, successors, path, res);
        return res;
    }

    private static boolean bidirectionalBfs(String beginWord,
                                            String endWord,
                                            Set<String> wordSet,
                                            Map<String, Set<String>> successors) {
        // 记录访问过的单词
        Set<String> visited = new HashSet<>();
        visited.add(beginWord); visited.add(endWord);

        Set<String> beginVisited = new HashSet<>();
        beginVisited.add(beginWord);
        Set<String> endVisited = new HashSet<>();
        endVisited.add(endWord);

        int wordLen = beginWord.length();
        boolean forward = true; boolean found = false;

        // 在保证了 beginVisited 总是较小（可以等于）大小的集合前提下，&& !endVisited.isEmpty() 可以省略
        while (!beginVisited.isEmpty() && !endVisited.isEmpty()) {
            // 一直保证 beginVisited 是相对较小的集合，方便后续编码
            if (beginVisited.size() > endVisited.size()) {
                Set<String> temp = beginVisited;
                beginVisited = endVisited;
                endVisited = temp;

                // 只要交换，就更改方向，以便维护 successors 的定义
                forward = !forward;
            }
            Set<String> nextLevelVisited = new HashSet<>();
            // 默认 beginVisited 是小集合，因此从 beginVisited 出发
            for (String currentWord : beginVisited) {
                char[] charArray = currentWord.toCharArray();
                for (int i = 0; i < wordLen; i++) {
                    char originChar = charArray[i];
                    for (char j = 'a'; j <= 'z'; j++) {
                        if (charArray[i] == j) {
                            continue;
                        }
                        charArray[i] = j;
                        String nextWord = new String(charArray);
                        if (wordSet.contains(nextWord)) {
                            if (endVisited.contains(nextWord)) {
                                found = true;
                                // 在另一侧找到单词以后，还需把这一层关系添加到「后继结点列表」
                                addToSuccessors(successors, forward, currentWord, nextWord);
                            }

                            if (!visited.contains(nextWord)) {
                                nextLevelVisited.add(nextWord);
                                addToSuccessors(successors, forward, currentWord, nextWord);
                            }
                        }
                    }
                    charArray[i] = originChar;
                }
            }
            beginVisited = nextLevelVisited;
            visited.addAll(nextLevelVisited);
            if (found) {
                break;
            }
        }
        return found;
    }

    private static void dfs(String beginWord,
                     String endWord,
                     Map<String, Set<String>> successors,
                     Deque<String> path,
                     List<List<String>> res) {

        if (beginWord.equals(endWord)) {
            res.add(new ArrayList<>(path));
            return;
        }

        if (!successors.containsKey(beginWord)) {
            return;
        }

        Set<String> successorWords = successors.get(beginWord);
        for (String successor : successorWords) {
            path.addLast(successor);
            dfs(successor, endWord, successors, path, res);
            path.removeLast();
        }
    }

    private static void addToSuccessors(Map<String, Set<String>> successors, boolean forward,
                                 String currentWord, String nextWord) {
        if (!forward) {
            String temp = currentWord;
            currentWord = nextWord;
            nextWord = temp;
        }

        // Java 1.8 以后支持
//        successors.computeIfAbsent(currentWord, a -> new HashSet<>());
//        successors.get(currentWord).add(nextWord);

        // 维护 successors 的定义
        if (successors.containsKey(currentWord)) {
            successors.get(currentWord).add(nextWord);
        } else {
            Set<String> newSet = new HashSet<>();
            newSet.add(nextWord);
            successors.put(currentWord, newSet);
        }

    }

    //Sigle bfs
    public static List<List<String>> findLadders2(String beginWord, String endWord, List<String> wordList) {
        List<List<String>> res = new ArrayList<List<String>>();
        Set<String> dict = new HashSet<String>(wordList);
        if (!dict.contains(endWord)) {
            return res;
        }
        dict.remove(endWord);
        //stps用来记录不同的单词都转换成中间的一个单词（非endWord）
        Map<String, Integer> steps = new HashMap<String, Integer>();
        steps.put(beginWord, 0);
        Map<String, List<String>> from = new HashMap<String, List<String>>();
        int step = 1;
        boolean found = false;
        int wordLen = beginWord.length();
        Queue<String> queue = new LinkedList<String>();
        queue.offer(beginWord);
        while (!queue.isEmpty()) {
            int size = queue.size();
            for (int i = 0; i < size; ++i) {
                String curWord = queue.poll();
                char[] charArray = curWord.toCharArray();
                for (int j = 0; j < wordLen; ++j) {
                    char origin = charArray[j];
                    for (char c = 'a'; c <= 'z'; ++c) {
                        if (charArray[j] == c) {
                            continue;
                        }
                        charArray[j] = c;
                        String nextWord = String.valueOf(charArray);
                        if (nextWord.equals(endWord)) {
                            from.putIfAbsent(nextWord, new LinkedList<String>());
                            from.get(nextWord).add(curWord);
                            found = true;
                        }
                        if (steps.containsKey(nextWord) && step == steps.get(nextWord)) {
                            from.get(nextWord).add(curWord);
                        }
                        if (dict.contains(nextWord)) {
                            dict.remove(nextWord);
                            from.putIfAbsent(nextWord, new ArrayList<String>());
                            from.get(nextWord).add(curWord);
                            steps.put(nextWord, step);
                            queue.offer(nextWord);
                        }
                    }
                    charArray[j] = origin;
                }
            }
            ++step;
            if (found) {
                break;
            }
        }
        if (found) {
            Deque<String> path = new ArrayDeque<String>();
            path.addLast(endWord);
            backtrack(from, path, beginWord, endWord, res);
        }
        return res;
    }
    public static void backtrack(Map<String, List<String>> from, Deque<String> path, String beginWord, String cur, List<List<String>> res) {
        if (cur.equals(beginWord)) {
            res.add(new ArrayList<String>(path));
            return;
        }
        for (String precursor : from.get(cur)) {
            path.addFirst(precursor);
            backtrack(from, path, beginWord, precursor, res);
            path.removeFirst();
        }
    }


    public static void main(String[] args) {
//        List<String> wordList = new ArrayList<String>();
//        wordList.add("hot");
//        wordList.add("dot");
//        wordList.add("dog");
//        wordList.add("lot");
//        wordList.add("log");
//        wordList.add("cog");

        String[] wordList2 = {"aaaaaaaaaa","caaaaaaaaa","cbaaaaaaaa","daaaaaaaaa","dbaaaaaaaa","eaaaaaaaaa","ebaaaaaaaa","faaaaaaaaa","fbaaaaaaaa","gaaaaaaaaa","gbaaaaaaaa","haaaaaaaaa","hbaaaaaaaa","iaaaaaaaaa","ibaaaaaaaa","jaaaaaaaaa","jbaaaaaaaa","kaaaaaaaaa","kbaaaaaaaa","laaaaaaaaa","lbaaaaaaaa","maaaaaaaaa","mbaaaaaaaa","naaaaaaaaa","nbaaaaaaaa","oaaaaaaaaa","obaaaaaaaa","paaaaaaaaa","pbaaaaaaaa","qaaaaaaaaa","qbaaaaaaaa","raaaaaaaaa","rbaaaaaaaa","saaaaaaaaa","sbaaaaaaaa","taaaaaaaaa","tbaaaaaaaa","uaaaaaaaaa","ubaaaaaaaa","vaaaaaaaaa","vbaaaaaaaa","waaaaaaaaa","wbaaaaaaaa","xaaaaaaaaa","xbaaaaaaaa","yaaaaaaaaa","ybaaaaaaaa","zaaaaaaaaa","zbaaaaaaaa","bbaaaaaaaa","bdaaaaaaaa","bdbaaaaaaa","beaaaaaaaa","bebaaaaaaa","bfaaaaaaaa","bfbaaaaaaa","bgaaaaaaaa","bgbaaaaaaa","bhaaaaaaaa","bhbaaaaaaa","biaaaaaaaa","bibaaaaaaa","bjaaaaaaaa","bjbaaaaaaa","bkaaaaaaaa","bkbaaaaaaa","blaaaaaaaa","blbaaaaaaa","bmaaaaaaaa","bmbaaaaaaa","bnaaaaaaaa","bnbaaaaaaa","boaaaaaaaa","bobaaaaaaa","bpaaaaaaaa","bpbaaaaaaa","bqaaaaaaaa","bqbaaaaaaa","braaaaaaaa","brbaaaaaaa","bsaaaaaaaa","bsbaaaaaaa","btaaaaaaaa","btbaaaaaaa","buaaaaaaaa","bubaaaaaaa","bvaaaaaaaa","bvbaaaaaaa","bwaaaaaaaa","bwbaaaaaaa","bxaaaaaaaa","bxbaaaaaaa","byaaaaaaaa","bybaaaaaaa","bzaaaaaaaa","bzbaaaaaaa","bcbaaaaaaa","bcdaaaaaaa","bcdbaaaaaa","bceaaaaaaa","bcebaaaaaa","bcfaaaaaaa","bcfbaaaaaa","bcgaaaaaaa","bcgbaaaaaa","bchaaaaaaa","bchbaaaaaa","bciaaaaaaa","bcibaaaaaa","bcjaaaaaaa","bcjbaaaaaa","bckaaaaaaa","bckbaaaaaa","bclaaaaaaa","bclbaaaaaa","bcmaaaaaaa","bcmbaaaaaa","bcnaaaaaaa","bcnbaaaaaa","bcoaaaaaaa","bcobaaaaaa","bcpaaaaaaa","bcpbaaaaaa","bcqaaaaaaa","bcqbaaaaaa","bcraaaaaaa","bcrbaaaaaa","bcsaaaaaaa","bcsbaaaaaa","bctaaaaaaa","bctbaaaaaa","bcuaaaaaaa","bcubaaaaaa","bcvaaaaaaa","bcvbaaaaaa","bcwaaaaaaa","bcwbaaaaaa","bcxaaaaaaa","bcxbaaaaaa","bcyaaaaaaa","bcybaaaaaa","bczaaaaaaa","bczbaaaaaa","bccbaaaaaa","bccdaaaaaa","bccdbaaaaa","bcceaaaaaa","bccebaaaaa","bccfaaaaaa","bccfbaaaaa","bccgaaaaaa","bccgbaaaaa","bcchaaaaaa","bcchbaaaaa","bcciaaaaaa","bccibaaaaa","bccjaaaaaa","bccjbaaaaa","bcckaaaaaa","bcckbaaaaa","bcclaaaaaa","bcclbaaaaa","bccmaaaaaa","bccmbaaaaa","bccnaaaaaa","bccnbaaaaa","bccoaaaaaa","bccobaaaaa","bccpaaaaaa","bccpbaaaaa","bccqaaaaaa","bccqbaaaaa","bccraaaaaa","bccrbaaaaa","bccsaaaaaa","bccsbaaaaa","bcctaaaaaa","bcctbaaaaa","bccuaaaaaa","bccubaaaaa","bccvaaaaaa","bccvbaaaaa","bccwaaaaaa","bccwbaaaaa","bccxaaaaaa","bccxbaaaaa","bccyaaaaaa","bccybaaaaa","bcczaaaaaa","bcczbaaaaa","bcccbaaaaa","bcccdaaaaa","bcccdbaaaa","bccceaaaaa","bcccebaaaa","bcccfaaaaa","bcccfbaaaa","bcccgaaaaa","bcccgbaaaa","bccchaaaaa","bccchbaaaa","bccciaaaaa","bcccibaaaa","bcccjaaaaa","bcccjbaaaa","bccckaaaaa","bccckbaaaa","bccclaaaaa","bccclbaaaa","bcccmaaaaa","bcccmbaaaa","bcccnaaaaa","bcccnbaaaa","bcccoaaaaa","bcccobaaaa","bcccpaaaaa","bcccpbaaaa","bcccqaaaaa","bcccqbaaaa","bcccraaaaa","bcccrbaaaa","bcccsaaaaa","bcccsbaaaa","bccctaaaaa","bccctbaaaa","bcccuaaaaa","bcccubaaaa","bcccvaaaaa","bcccvbaaaa","bcccwaaaaa","bcccwbaaaa","bcccxaaaaa","bcccxbaaaa","bcccyaaaaa","bcccybaaaa","bccczaaaaa","bccczbaaaa","bccccbaaaa","bccccdaaaa","bccccdbaaa","bcccceaaaa","bccccebaaa","bccccfaaaa","bccccfbaaa","bccccgaaaa","bccccgbaaa","bcccchaaaa","bcccchbaaa","bcccciaaaa","bccccibaaa","bccccjaaaa","bccccjbaaa","bcccckaaaa","bcccckbaaa","bcccclaaaa","bcccclbaaa","bccccmaaaa","bccccmbaaa","bccccnaaaa","bccccnbaaa","bccccoaaaa","bccccobaaa","bccccpaaaa","bccccpbaaa","bccccqaaaa","bccccqbaaa","bccccraaaa","bccccrbaaa","bccccsaaaa","bccccsbaaa","bcccctaaaa","bcccctbaaa","bccccuaaaa","bccccubaaa","bccccvaaaa","bccccvbaaa","bccccwaaaa","bccccwbaaa","bccccxaaaa","bccccxbaaa","bccccyaaaa","bccccybaaa","bcccczaaaa","bcccczbaaa","bcccccbaaa","bcccccdaaa","bcccccdbaa","bccccceaaa","bcccccebaa","bcccccfaaa","bcccccfbaa","bcccccgaaa","bcccccgbaa","bccccchaaa","bccccchbaa","bccccciaaa","bcccccibaa","bcccccjaaa","bcccccjbaa","bccccckaaa","bccccckbaa","bccccclaaa","bccccclbaa","bcccccmaaa","bcccccmbaa","bcccccnaaa","bcccccnbaa","bcccccoaaa","bcccccobaa","bcccccpaaa","bcccccpbaa","bcccccqaaa","bcccccqbaa","bcccccraaa","bcccccrbaa","bcccccsaaa","bcccccsbaa","bccccctaaa","bccccctbaa","bcccccuaaa","bcccccubaa","bcccccvaaa","bcccccvbaa","bcccccwaaa","bcccccwbaa","bcccccxaaa","bcccccxbaa","bcccccyaaa","bcccccybaa","bccccczaaa","bccccczbaa","bccccccbaa","bccccccdaa","bccccccdba","bcccccceaa","bcccccceba","bccccccfaa","bccccccfba","bccccccgaa","bccccccgba","bcccccchaa","bcccccchba","bcccccciaa","bcccccciba","bccccccjaa","bccccccjba","bcccccckaa","bcccccckba","bcccccclaa","bcccccclba","bccccccmaa","bccccccmba","bccccccnaa","bccccccnba","bccccccoaa","bccccccoba","bccccccpaa","bccccccpba","bccccccqaa","bccccccqba","bccccccraa","bccccccrba","bccccccsaa","bccccccsba","bcccccctaa","bcccccctba","bccccccuaa","bccccccuba","bccccccvaa","bccccccvba","bccccccwaa","bccccccwba","bccccccxaa","bccccccxba","bccccccyaa","bccccccyba","bcccccczaa","bcccccczba","bcccccccba","bcccccccda","bcccccccdb","bcccccccea","bccccccceb","bcccccccfa","bcccccccfb","bcccccccga","bcccccccgb","bcccccccha","bccccccchb","bcccccccia","bcccccccib","bcccccccja","bcccccccjb","bcccccccka","bccccccckb","bcccccccla","bccccccclb","bcccccccma","bcccccccmb","bcccccccna","bcccccccnb","bcccccccoa","bcccccccob","bcccccccpa","bcccccccpb","bcccccccqa","bcccccccqb","bcccccccra","bcccccccrb","bcccccccsa","bcccccccsb","bcccccccta","bccccccctb","bcccccccua","bcccccccub","bcccccccva","bcccccccvb","bcccccccwa","bcccccccwb","bcccccccxa","bcccccccxb","bcccccccya","bcccccccyb","bcccccccza","bccccccczb","bccccccccb","dccccccccb","ddcccccccb","eccccccccb","edcccccccb","fccccccccb","fdcccccccb","gccccccccb","gdcccccccb","hccccccccb","hdcccccccb","iccccccccb","idcccccccb","jccccccccb","jdcccccccb","kccccccccb","kdcccccccb","lccccccccb","ldcccccccb","mccccccccb","mdcccccccb","nccccccccb","ndcccccccb","occccccccb","odcccccccb","pccccccccb","pdcccccccb","qccccccccb","qdcccccccb","rccccccccb","rdcccccccb","sccccccccb","sdcccccccb","tccccccccb","tdcccccccb","uccccccccb","udcccccccb","vccccccccb","vdcccccccb","wccccccccb","wdcccccccb","xccccccccb","xdcccccccb","yccccccccb","ydcccccccb","zccccccccb","zdcccccccb","cdcccccccb","cbcccccccb","cbdccccccb","cccccccccb","ccdccccccb","cfcccccccb","cfdccccccb","cgcccccccb","cgdccccccb","chcccccccb","chdccccccb","cicccccccb","cidccccccb","cjcccccccb","cjdccccccb","ckcccccccb","ckdccccccb","clcccccccb","cldccccccb","cmcccccccb","cmdccccccb","cncccccccb","cndccccccb","cocccccccb","codccccccb","cpcccccccb","cpdccccccb","cqcccccccb","cqdccccccb","crcccccccb","crdccccccb","cscccccccb","csdccccccb","ctcccccccb","ctdccccccb","cucccccccb","cudccccccb","cvcccccccb","cvdccccccb","cwcccccccb","cwdccccccb","cxcccccccb","cxdccccccb","cycccccccb","cydccccccb","czcccccccb","czdccccccb","cedccccccb","cebccccccb","cebdcccccb","cecccccccb","cecdcccccb","cefccccccb","cefdcccccb","cegccccccb","cegdcccccb","cehccccccb","cehdcccccb","ceiccccccb","ceidcccccb","cejccccccb","cejdcccccb","cekccccccb","cekdcccccb","celccccccb","celdcccccb","cemccccccb","cemdcccccb","cenccccccb","cendcccccb","ceoccccccb","ceodcccccb","cepccccccb","cepdcccccb","ceqccccccb","ceqdcccccb","cerccccccb","cerdcccccb","cesccccccb","cesdcccccb","cetccccccb","cetdcccccb","ceuccccccb","ceudcccccb","cevccccccb","cevdcccccb","cewccccccb","cewdcccccb","cexccccccb","cexdcccccb","ceyccccccb","ceydcccccb","cezccccccb","cezdcccccb","ceedcccccb","ceebcccccb","ceebdccccb","ceeccccccb","ceecdccccb","ceefcccccb","ceefdccccb","ceegcccccb","ceegdccccb","ceehcccccb","ceehdccccb","ceeicccccb","ceeidccccb","ceejcccccb","ceejdccccb","ceekcccccb","ceekdccccb","ceelcccccb","ceeldccccb","ceemcccccb","ceemdccccb","ceencccccb","ceendccccb","ceeocccccb","ceeodccccb","ceepcccccb","ceepdccccb","ceeqcccccb","ceeqdccccb","ceercccccb","ceerdccccb","ceescccccb","ceesdccccb","ceetcccccb","ceetdccccb","ceeucccccb","ceeudccccb","ceevcccccb","ceevdccccb","ceewcccccb","ceewdccccb","ceexcccccb","ceexdccccb","ceeycccccb","ceeydccccb","ceezcccccb","ceezdccccb","ceeedccccb","ceeebccccb","ceeebdcccb","ceeecccccb","ceeecdcccb","ceeefccccb","ceeefdcccb","ceeegccccb","ceeegdcccb","ceeehccccb","ceeehdcccb","ceeeiccccb","ceeeidcccb","ceeejccccb","ceeejdcccb","ceeekccccb","ceeekdcccb","ceeelccccb","ceeeldcccb","ceeemccccb","ceeemdcccb","ceeenccccb","ceeendcccb","ceeeoccccb","ceeeodcccb","ceeepccccb","ceeepdcccb","ceeeqccccb","ceeeqdcccb","ceeerccccb","ceeerdcccb","ceeesccccb","ceeesdcccb","ceeetccccb","ceeetdcccb","ceeeuccccb","ceeeudcccb","ceeevccccb","ceeevdcccb","ceeewccccb","ceeewdcccb","ceeexccccb","ceeexdcccb","ceeeyccccb","ceeeydcccb","ceeezccccb","ceeezdcccb","ceeeedcccb","ceeeebcccb","ceeeebdccb","ceeeeccccb","ceeeecdccb","ceeeefcccb","ceeeefdccb","ceeeegcccb","ceeeegdccb","ceeeehcccb","ceeeehdccb","ceeeeicccb","ceeeeidccb","ceeeejcccb","ceeeejdccb","ceeeekcccb","ceeeekdccb","ceeeelcccb","ceeeeldccb","ceeeemcccb","ceeeemdccb","ceeeencccb","ceeeendccb","ceeeeocccb","ceeeeodccb","ceeeepcccb","ceeeepdccb","ceeeeqcccb","ceeeeqdccb","ceeeercccb","ceeeerdccb","ceeeescccb","ceeeesdccb","ceeeetcccb","ceeeetdccb","ceeeeucccb","ceeeeudccb","ceeeevcccb","ceeeevdccb","ceeeewcccb","ceeeewdccb","ceeeexcccb","ceeeexdccb","ceeeeycccb","ceeeeydccb","ceeeezcccb","ceeeezdccb","ceeeeedccb","ceeeeebccb","ceeeeebdcb","ceeeeecccb","ceeeeecdcb","ceeeeefccb","ceeeeefdcb","ceeeeegccb","ceeeeegdcb","ceeeeehccb","ceeeeehdcb","ceeeeeiccb","ceeeeeidcb","ceeeeejccb","ceeeeejdcb","ceeeeekccb","ceeeeekdcb","ceeeeelccb","ceeeeeldcb","ceeeeemccb","ceeeeemdcb","ceeeeenccb","ceeeeendcb","ceeeeeoccb","ceeeeeodcb","ceeeeepccb","ceeeeepdcb","ceeeeeqccb","ceeeeeqdcb","ceeeeerccb","ceeeeerdcb","ceeeeesccb","ceeeeesdcb","ceeeeetccb","ceeeeetdcb","ceeeeeuccb","ceeeeeudcb","ceeeeevccb","ceeeeevdcb","ceeeeewccb","ceeeeewdcb","ceeeeexccb","ceeeeexdcb","ceeeeeyccb","ceeeeeydcb","ceeeeezccb","ceeeeezdcb","ceeeeeedcb","ceeeeeebcb","ceeeeeebdb","ceeeeeeccb","ceeeeeecdb","ceeeeeefcb","ceeeeeefdb","ceeeeeegcb","ceeeeeegdb","ceeeeeehcb","ceeeeeehdb","ceeeeeeicb","ceeeeeeidb","ceeeeeejcb","ceeeeeejdb","ceeeeeekcb","ceeeeeekdb","ceeeeeelcb","ceeeeeeldb","ceeeeeemcb","ceeeeeemdb","ceeeeeencb","ceeeeeendb","ceeeeeeocb","ceeeeeeodb","ceeeeeepcb","ceeeeeepdb","ceeeeeeqcb","ceeeeeeqdb","ceeeeeercb","ceeeeeerdb","ceeeeeescb","ceeeeeesdb","ceeeeeetcb","ceeeeeetdb","ceeeeeeucb","ceeeeeeudb","ceeeeeevcb","ceeeeeevdb","ceeeeeewcb","ceeeeeewdb","ceeeeeexcb","ceeeeeexdb","ceeeeeeycb","ceeeeeeydb","ceeeeeezcb","ceeeeeezdb","ceeeeeeedb","ceeeeeeebb","ceeeeeeebc","ceeeeeeecb","ceeeeeeecc","ceeeeeeefb","ceeeeeeefc","ceeeeeeegb","ceeeeeeegc","ceeeeeeehb","ceeeeeeehc","ceeeeeeeib","ceeeeeeeic","ceeeeeeejb","ceeeeeeejc","ceeeeeeekb","ceeeeeeekc","ceeeeeeelb","ceeeeeeelc","ceeeeeeemb","ceeeeeeemc","ceeeeeeenb","ceeeeeeenc","ceeeeeeeob","ceeeeeeeoc","ceeeeeeepb","ceeeeeeepc","ceeeeeeeqb","ceeeeeeeqc","ceeeeeeerb","ceeeeeeerc","ceeeeeeesb","ceeeeeeesc","ceeeeeeetb","ceeeeeeetc","ceeeeeeeub","ceeeeeeeuc","ceeeeeeevb","ceeeeeeevc","ceeeeeeewb","ceeeeeeewc","ceeeeeeexb","ceeeeeeexc","ceeeeeeeyb","ceeeeeeeyc","ceeeeeeezb","ceeeeeeezc","ceeeeeeeec","aaaaaaaaaz","aaaaaaaabz","aaaaaaacbz","aaaaaaacbc","aaaaaadcbc","aaaaaedcbc","aaaaaedccc","aaaafedccc","aaaafecccc","aaaffecccc","aafffecccc","aaffcecccc","aaffcccccc","acffcccccc","acfccccccc","accccccccc","accccccccb","accccccccf","acccccccff","accccccfff","acccccffff","acccccfffc","accccefffc","accceefffc","acceeefffc","aceeeefffc","aceeeeeffc","aceeeeeefc","aceeeeeeec","aeeeeeeeec"};
        //将字符串数组转换成ArrayList
        List<String> wordList = Arrays.asList(wordList2);

        String beginWord = "aaaaaaaaaa";
        String endWord = "ceeeeeeeec";
        List<List<String>> res = findLadders2(beginWord, endWord, wordList);
        System.out.println(res);
    }
}