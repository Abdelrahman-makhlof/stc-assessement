

public class ProblemSolving {

    public static void main(String[] args) {
        String[] inputs = {"abd(jnb)asdf", "abdjnbasdf", "dd(df)a(ghhh)"};

        for (String input : inputs) {

            System.out.println("Input: " + input);
            System.out.println("Output: " + reverse(input));
            System.out.println("");

        }
    }

    public static String reverse(String input) {
        var result = new StringBuilder();
        var subString = new StringBuilder();
        int openParenthesesCount = 0;

        for (var character : input.toCharArray()) {

            if (character == '(') {
                openParenthesesCount++;
                if (openParenthesesCount > 1) {
                    result.append(subString.reverse());
                    subString = new StringBuilder();
                }

            } else if (character == ')') {
                result.append(subString.reverse());
                subString = new StringBuilder();
                openParenthesesCount--;

            } else {
                if (openParenthesesCount == 0)
                    result.append(character);
                else
                    subString.append(character);
            }
        }

        return result.toString();
    }

