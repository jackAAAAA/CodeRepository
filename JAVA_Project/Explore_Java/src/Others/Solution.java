package Others;

import java.util.*;

//LeetCode770
class Solution {
    /**
     * TO DO
     * class Term: e.g. -2*a*b*b*c
     * class Sequence: a list of Terms. e.g. [-2*a*b*b*c, 3*a, 4]
     * flag: 1->add, -1->sub, 0->multi
     * 继续由结果倒推出过程
     */
    class Term implements Comparable<Term>{
        int coef;
        List<String> vars;

        public Term(int n) {
            vars = new ArrayList<>();
            coef = n;
        }

        public Term(String s) {
            vars = new ArrayList<>();
            vars.add(s);
            coef = 1;
        }

        @Override
        public String toString() {
            return String.valueOf(coef + varString());
        }

        public String varString() {
            Collections.sort(vars);
            StringBuilder sb = new StringBuilder();
            for (String s : vars) {
                sb.append('*');
                sb.append(s);
            }
            return sb.toString();
        }

        @Override
        public boolean equals(Object o) {
            return varString().equals(((Term)o).varString());
        }

        @Override
        public int compareTo(Term t) {
            if (vars.size() != t.vars.size()) {
                return t.vars.size() - vars.size();
            }
            return varString().compareTo(t.varString());
        }

        public Term multi(Term t) {
            Term result = new Term(coef);
            result.vars.addAll(vars);
            result.coef *= t.coef;
            result.vars.addAll(t.vars);
            return result;
        }
    }
    class Sequence {
        List<Term> terms;

        public Sequence() {
            terms = new ArrayList<>();
        }

        public Sequence(int n) {
            terms = new ArrayList<>();
            terms.add(new Term(n));
        }

        public Sequence(String s) {
            terms = new ArrayList<>();
            terms.add(new Term(s));
        }

        public Sequence(Term t) {
            terms = new ArrayList<>();
            terms.add(t);
        }

        public Sequence add(Sequence sq) {
            for (Term t2 : sq.terms) {
                boolean found = false;
                for (Term t1 : terms) {
                    if (t1.equals(t2)) {
                        t1.coef += t2.coef;
                        if (t1.coef == 0) {
                            terms.remove(t1);
                        }
                        found = true;
                        break;
                    }
                }
                if (!found && t2.coef != 0) {
                    terms.add(t2);
                }
            }
            return this;
        }

        public Sequence multi(Sequence sq) {
            Sequence result = new Sequence();
            for (Term t1 : terms) {
                for (Term t2 : sq.terms) {
                    result.add(new Sequence(t1.multi(t2)));
                }
            }
            return result;
        }
    }
    private int i;
    public List<String> basicCalculatorIV(String expression, String[] evalvars, int[] evalints) {
        Map<String, Integer> map = new HashMap<>();
        for (int i = 0; i < evalvars.length; i++) {
            map.put(evalvars[i], evalints[i]);
        }
        i = 0;
//        TO DO
        Sequence sq = helper(expression, map);
        List<String> result = new LinkedList<>();
        Collections.sort(sq.terms);
        for (Term t : sq.terms) {
            if (!t.toString().equals("0")) {
                result.add(t.toString());
            }
        }
        return result;
    }
    private Sequence helper(String e, Map<String, Integer> map) {
        Stack<Sequence> stack = new Stack<>();
        int flag = 1;
        stack.push(new Sequence(0));
        while (i < e.length()) {
            if (e.charAt(i) == ' ') {
                i++;
                continue;
            }
            if (e.charAt(i) == '(') {
                i++;
                Sequence sq = helper(e, map);
                addToStack(stack, sq, flag);
            } else if (e.charAt(i) == ')') {
                break;
            } else if (e.charAt(i) == '+') {
                flag = 1;
            } else if (e.charAt(i) == '-') {
                flag = -1;
            } else if (e.charAt(i) == '*') {
                flag = 0;
            } else if (Character.isDigit(e.charAt(i))) {
                int j = i + 1;
                while (j < e.length() && Character.isDigit(e.charAt(j))) {
                    j++;
                }
                int coef = Integer.valueOf(e.substring(i, j));
                i = j - 1;
                addToStack(stack, new Sequence(coef), flag);
            } else {
                int j = i + 1;
                while (j < e.length() && e.charAt(j) != ' ' && e.charAt(j) != ')') {
                    j++;
                }
                String var = e.substring(i, j);
                i = j - 1;
                if (map.containsKey(var)) {
                    addToStack(stack, new Sequence(map.get(var)), flag);
                } else {
                    addToStack(stack, new Sequence(var), flag);
                }
            }
            i++;
        }
        Sequence result = new Sequence();
        while (!stack.isEmpty()) {
            result.add(stack.pop());
        }
        return result;
    }
    private void addToStack(Stack<Sequence> stack, Sequence sq, int flag) {
        if (flag == 0) {
            stack.push(stack.pop().multi(sq));
        } else {
            stack.push(sq.multi(new Sequence(flag)));
        }
    }
}
