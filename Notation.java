
import java.util.regex.Pattern;

import javafx.application.Application;
import javafx.stage.Stage;
/**
 * The Notation class extends Application
 * @author djusu
 *
 */
public class Notation extends Application{

    static MyQueue<Character> outterResult;
    static MyStack<Character>stickshere;
    private static String[] operators = {"+", "-", "*", "/"};

    private static final String operation = "-+*/";
  
	private static final String number = "0012345678";

    /**
     * @param from infix to postfix
     * @return
     */
    public static String convertInfixToPostfix(String inner) throws InvalidNotationFormatException {
        if (!include(inner)||inner == null || inner.length() == 0) {
            throw new InvalidNotationFormatException("Will result to invalid input");
        }
        outterResult = new MyQueue<Character>(inner.length());
        stickshere = new MyStack<Character>(inner.length());
        try {
            for (int j = 0; j < inner.length(); j++) {
                char ch = inner.charAt(j);
                switch (ch) {
                    case '+':
                    case '-':
                        full(ch, 1);
                        break;
                    case '*': 
                    case '/':
                        full(ch, 2); 
                        break; 
                    case '(': 
                        stickshere.push('('); 
                        break;
                    case ')': 
                        gotParen(ch); 
                        break;
                    default: 
                        outterResult.enqueue(ch);
                        break;
                }
            }
            while (!stickshere.isEmpty()) {
                outterResult.enqueue(stickshere.pop());

            }
        } catch (Exception ex) {
            System.out.println(ex);
        }
        return outterResult.toString(); // return postfix
    }

    public static String convertPostfixToInfix(String expression) throws InvalidNotationFormatException {
        String infix = "";
        String nextCh;
        String position = "";
        //remove all spaces
        String s = "";
        while (expression.indexOf(' ') >= 0) {
            expression = expression.replaceAll(" ", "");
        }
        //introducing space after each token
        for (int i = 0; i < expression.length(); i++) {
            s = s + expression.charAt(i) + " ";
        }
        expression = s.trim();

        MyStack<String> postfixs = new MyStack<String>(expression.length());
        MyStack<String> infixs = new MyStack<String>(expression.length());

        try {
            for (int i = 0; i < expression.length(); i++) {
                infixs.push(Character.toString(expression.charAt(i)));
            }

            while (!infixs.isEmpty()) {
                postfixs.push(infixs.pop());
            }

            while (!postfixs.isEmpty()) {
                nextCh = postfixs.pop();
                if (nextCh.equalsIgnoreCase("+") || nextCh.equalsIgnoreCase("-")
                        || nextCh.equalsIgnoreCase("*") || nextCh.equalsIgnoreCase("/")
                        || nextCh.equalsIgnoreCase("%")) {
                    if (!position.isEmpty()) {
                        // push the operand
                        infixs.push(position);
                        position = "";
                    }
                    try {
                        String meg2 = infixs.pop();
                        String meg1 = infixs.pop();

                        if (!postfixs.isEmpty()) {
                            // surround expression with parenthesis
                            String pos = "(" + meg1 + nextCh + meg2 + ")";
                            infixs.push(pos);
                        } else {
                            // does not require surrounding parenthesis
                            String exp = meg1 + nextCh + meg2;
                           
                            infixs.push(exp);
                        }
                    } catch (StackUnderflowException e) {
                        throw new InvalidNotationFormatException("\nCannot process user expression"
                                + "\nERROR - The postfix expression you entered is invalid"
                                + "\n Allows for additional processing of expressions\n\n");

                    }

                } else if (addMore(nextCh) || nextCh.equals(".")) {
                    // nextChar is a digit
                    position += nextCh;
                } else if (nextCh.equalsIgnoreCase(" ")) {
                    // To forgo all spaces
                    if (!position.isEmpty()) {
                        // push the operand
                        infixs.push(position);
                        position = "";
                    }

                } else if (Pattern.matches("[A-Za-z]", nextCh.toString())) {
                    // nextChar is a letter
                    position += nextCh;

                } else {
                    // unsupported character
                    s = ("Invalid: \"" + nextCh + "\"");
                    s += ("ERROR - unsupport value or character!");
                    s += ("Will terminated with error");
                    throw new InvalidNotationFormatException(s);

                }
            }

            infix = infixs.top();
            if (infixs.top().equals("")) {
                // unable to convert Postfix expression 
                throw new InvalidNotationFormatException("Cannot convert Postfix expression: \n  \""
                        + expression + "\"");
            } else {
                //Converted Postfix expression 
                //Get raid of trailing space(s) from postfix expression and infix conversion
                while (expression.endsWith(" ")) {
                    expression = expression.trim();
                }
                while (infix.endsWith(" ")) {
                    infix = infix.substring(0, infix.length() - 2);
                }

            }
        } catch (Exception ex) {
            throw new InvalidNotationFormatException(s);
        }
        return "(" + infix + ")";

    }

    
    
    
    
    
    
    /**
     * a method to evaluatePostfixExpression to evaluate the postfix expression.
     * To take in a string and return a double
     * @param postfixExpr
     * @return
     * @throws will throw InvalidNotationFormatException if the information is not met
     */
	public static Double evaluatePostfixExpression(String postfix) throws InvalidNotationFormatException {
        //checking for validity    
        if (postfix == null || postfix.length() == 0) {
            throw new InvalidNotationFormatException("Invalid postfix expression!");
        }
        MyStack<Double> stack = new MyStack<Double>();
        try {
            char[] in = postfix.toCharArray();

            for (char c : in) {
                if (retrieves(c)) {
                	//To convert char into double/Narrowing data
                    stack.push((double)(c - '0')); 
                } else if (magnitude(c)) {
                    Double mag1 = 0.0;
                    Double mag2 = 0.0;
                    if (stack.isEmpty()) {
                        throw new InvalidNotationFormatException("Will read invalid format");
                    }
                    mag1 = stack.pop();
                    if (stack.isEmpty()) {
                        throw new InvalidNotationFormatException("Will read invalid format");
                    }
                    mag2 = stack.pop();
                    //Now putting operands into use
                    Double getResults;
                    switch (c) {
                        case '*':
                            getResults = mag1 * mag2;
                            stack.push(getResults);
                            break;
                        case '/':
                            getResults = mag2 / mag1;
                            stack.push(getResults);
                            break;
                        case '+':
                            getResults = mag1 + mag2;
                            stack.push(getResults);
                            break;
                        case '-':
                            getResults = mag2 - mag1;
                            stack.push(getResults);
                            continue;
                            
                    }
                }
            }
            return stack.pop();
        } catch (Exception ex) {
            throw new InvalidNotationFormatException("Will print invalid postfix expression!");
        }
        
    }
	
    /**
     * @param s To check if String parenthesis is balanced
     */
    private static boolean include(String t) {
        int empty = 0;
        for (int i = 0; i < t.length(); ++i) {
            char r = t.charAt(i);
            if (r == '(') {
                ++empty;
            } else if (r == ')') {
                --empty;
                if (empty < 0) {
                    return false;
                }
            }
        }
       return empty == 0;
    }

    private static boolean magnitude(char check) {
        return operation.indexOf(check) >= 0;
    }

    
	private static boolean retrieves(char check) {
        return number.indexOf(check) >= 0;
    }

    private static boolean addMore(String string) {
        try {
        	//Using wrapper class to change data type
            Integer.parseInt(string);
            return true;
        } catch (NumberFormatException numForEx) {
            return false;
        }
    }

    private static void full(char opThis, int prec1) throws StackUnderflowException, QueueOverflowException, StackOverflowException {
        while (!stickshere.isEmpty()) {
            char opTop = ((String) (stickshere.pop() + "")).charAt(0);
            if (opTop == '(') {
                stickshere.push(opTop);
                break;
            }// it's an operator
            else {// precedence of new op
                int prec2;
                if (opTop == '+' || opTop == '-') {
                    prec2 = 1;
                } else {
                    prec2 = 2;
                }
                if (prec2 < prec1) 
                { 
                    stickshere.push(opTop);
                    break;
                } else 
                {
                    outterResult.enqueue(opTop); 
                }
            }
        }
        stickshere.push(opThis);
    }

    private static void gotParen(char ch) throws StackUnderflowException, QueueOverflowException {
        while (!stickshere.isEmpty()) {
            char chx = ((String) (stickshere.pop() + "")).charAt(0);
            if (chx == '(') {
                break;
            } else {
                outterResult.enqueue(chx);
            }
        }
    }
    @SuppressWarnings("unused")
	private int takeOut(String n) {
        for (int i = 0; i < operators.length; i++) {
            if (n.equals(operators[i])) {
                return i;
            }
        }
        return operators.length;
    }

	@Override
	public void start(Stage arg) throws Exception {
		// TODO Auto-generated method stub
		
	}

}

