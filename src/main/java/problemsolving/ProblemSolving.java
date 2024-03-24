package problemsolving;

import java.util.Stack;

public class ProblemSolving {

    public static void main(String[] args) {
        String[] inputs = {"dd(df)a(ghHh)", "sas(fg)f(gh", "abd(jnb)asdf", "abdjnbasdf", "dd(df)a(ghhh)"};

        String regex = "^[a-z()]{1,2000}$";

        for (String input : inputs) {

            System.out.println("Input: " + input);

            if (input.matches(regex) && checkBalancedParentheses(input)) {
                System.out.println("Output: " + reverseSubString(input));
            } else {
                System.out.println("Output: invalid string");
            }

            System.out.println("---");

        }
    }

    static boolean checkBalancedParentheses(String input) {
        Stack<Character> stack = new Stack<>();
        for (char ch : input.toCharArray()) {
            if (ch == '(') {
                stack.push(ch);
            } else if (ch == ')') {
                if (stack.isEmpty() || stack.pop() != '(') {
                    return false;
                }
            }
        }

        return stack.isEmpty();
    }

    public static String reverseSubString(String input) {
        var result = new StringBuilder();
        var subString = new StringBuilder();

        int openParenthesesCount = 0;

        for (var character : input.toCharArray()) {

            if (character == '(') {
                openParenthesesCount++;
                if (openParenthesesCount > 1) {
                    result.append("(").append(subString.reverse()).append(")");
                    subString = new StringBuilder();
                }

            } else if (character == ')') {
                result.append("(").append(subString.reverse()).append(")");
                subString = new StringBuilder();
                openParenthesesCount--;

            } else {
                if (openParenthesesCount == 0) {
                    result.append(character);
                } else {
                    subString.append(character);
                }
            }

        }

        return result.toString();
    }

}

