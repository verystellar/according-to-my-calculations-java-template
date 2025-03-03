package org.shunting;
import java.util.Stack;

public class Main {
  public String getGreeting() {
    return "Hello World!";
  }

  public static void main(String[] args) {
    System.out.println(new Main().getGreeting());
    // Prints out the number 7
    System.out.println(evaluate("2 + 5"));

    // Prints out the number 33
    System.out.println(evaluate("3 + 6 * 5"));

    // Prints out the number 20
    System.out.println(evaluate("4 * ( 2 + 3 )"));

    // Prints out the number 2
    System.out.println(evaluate("( 7 + 9 ) / 8"));
  }
  static String infixToPostfix(String infix) { //rosetta code implementation
        /* To find out the precedence, we take the index of the
           token in the ops string and divide by 2 (rounding down).
           This will give us: 0, 0, 1, 1, 2 */
    final String ops = "-+/*^";

    StringBuilder sb = new StringBuilder();
    Stack<Integer> s = new Stack<>();

    for (String token : infix.split("\\s")) {
      if (token.isEmpty())
        continue;
      char c = token.charAt(0);
      int idx = ops.indexOf(c);

      // check for operator
      if (idx != -1) {
        if (s.isEmpty())
          s.push(idx);

        else {
          while (!s.isEmpty()) {
            int prec2 = s.peek() / 2;
            int prec1 = idx / 2;
            if (prec2 > prec1 || (prec2 == prec1 && c != '^'))
              sb.append(ops.charAt(s.pop())).append(' ');
            else break;
          }
          s.push(idx);
        }
      }
      else if (c == '(') {
        s.push(-2); // -2 stands for '('
      }
      else if (c == ')') {
        // until '(' on stack, pop operators.
        while (s.peek() != -2)
          sb.append(ops.charAt(s.pop())).append(' ');
        s.pop();
      }
      else {
        sb.append(token).append(' ');
      }
    }
    while (!s.isEmpty())
      sb.append(ops.charAt(s.pop())).append(' ');
    return sb.toString();
  }

  static boolean isNumber(String token) {
    try {
      Integer.parseInt(token);
      return true;
    } catch (NumberFormatException e) {
      return false;
    }
  }

  static int evaluatePostfix(String infix) {
    Stack<Integer> stack = new Stack<>();

    for (String token : infix.split("\\s")) {
      if (isNumber(token)) {
        stack.push(Integer.parseInt(token));
      }
      else { //evaluate current char as operator
        int val1 = stack.pop();
        int val2 = stack.pop();

        switch (token) {
          case "+":
            stack.push(val1+val2);
            break;
          case "-":
            stack.push(val2-val1);
            break;
          case "*":
            stack.push(val1*val2);
            break;
          case "/":
            stack.push(val2/val1);
            break;
        }
      }
    }
    return stack.pop();
  }
  static int evaluate(String expression){
    String Postfix = infixToPostfix(expression);
    return evaluatePostfix(Postfix);
  }
  
}
