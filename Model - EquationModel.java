package model;
import java.util.Stack;
public class EquationModel {
    public double evaluate(String expression) {
        return evaluatePostfix(toPostfix(preprocess(expression)));
    }
    // Обработка выражения: замена /| на специальный оператор %
    private String preprocess(String expression) {
        return expression.replace("/|", "%");
    }
    private String toPostfix(String infix) {
        StringBuilder output = new StringBuilder();
        Stack<Character> stack = new Stack<>();
        String operators = "+-*/^%";
        for (int i = 0; i < infix.length(); i++) {
            char token = infix.charAt(i);
          
            if (Character.isDigit(token) || token == '.') {
                output.append(token);
            } else if (operators.indexOf(token) != -1) {
                output.append(" ");
                while (!stack.isEmpty() && precedence(stack.peek()) >= precedence(token)) {
                    output.append(stack.pop()).append(" ");
                }
                stack.push(token);
            } else if (token == '(') {
                stack.push(token);
            } else if (token == ')') {
                while (!stack.isEmpty() && stack.peek() != '(') {
                    output.append(" ").append(stack.pop());
                }
                if (!stack.isEmpty() && stack.peek() == '(') {
                    stack.pop();
                }
            }
        }

        while (!stack.isEmpty()) {
            output.append(" ").append(stack.pop());
        }

        return output.toString();
    }

    private double evaluatePostfix(String postfix) {
        Stack<Double> stack = new Stack<>();
        String[] tokens = postfix.trim().split("\\s+");
        for (String token : tokens) {
            switch (token) {
                case "+": stack.push(stack.pop() + stack.pop()); break;
                case "-": {
                    double b = stack.pop(), a = stack.pop();
                    stack.push(a - b);
                    break;
                }
                case "*": stack.push(stack.pop() * stack.pop()); break;
                case "/": {
                    double b = stack.pop(), a = stack.pop();
                    stack.push(a / b);
                    break;
                }
                case "^": {
                    double b = stack.pop(), a = stack.pop();
                    stack.push(Math.pow(a, b));
                    break;
                }
                case "%": {
                    double b = stack.pop(), a = stack.pop();
                    stack.push((double) ((long) a / (long) b));
                    break;
                }
                default:
                    stack.push(Double.parseDouble(token));
            }
        }

        return stack.pop();
    }
    private int precedence(char op) {
        return switch (op) {
            case '^' -> 4;
            case '*', '/', '%' -> 3;
            case '+', '-' -> 2;
            default -> 1;
        };
    }
}
