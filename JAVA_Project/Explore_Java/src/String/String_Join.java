package String;

import java.util.ArrayDeque;
import java.util.Deque;

public class String_Join {
    public static void main(String[] args) {
        Deque<String> path = new ArrayDeque<>() {{offerLast("11"); offerLast("22"); offerFirst("333");}};
        String test = String.join(".", path);
        System.out.println(test);
    }
}
