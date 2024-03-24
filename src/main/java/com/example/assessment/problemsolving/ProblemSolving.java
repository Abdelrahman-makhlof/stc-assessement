package main.java.com.example.assessment.problemsolving;

public class ProblemSolving {

    public static void main(String[] args) {
        String[] inputs = {"abd(jnb)asdf", "abdjnbasdf", "dd(df)a(ghhh)"};
        for (String input : inputs) {
            if(input.matches("\\(([^()]+)\\)")){

                System.out.println("Input: " + input);
                System.out.println("Output: " + reverseSubstringInParentheses(input));
            }else{


            }

        }
    }

    public static String reverseSubstringInParentheses(String input) {

        StringBuilder result = new StringBuilder();
        StringBuilder substring = new StringBuilder();
        int openCount = 0;

        for (char c : input.toCharArray()) {
            if (c == '(') {
                openCount++;
                if (openCount > 1) {
                    result.append(substring.reverse());
                    substring = new StringBuilder();
                }
            } else if (c == ')') {
                openCount--;
                result.append(substring.reverse());
            } else {
                if (openCount == 0) {
                    result.append(c);
                } else {
                    substring.append(c);
                }
            }
        }
        return result.toString();
    }

}
