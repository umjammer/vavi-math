/*
 * http://www.dreamincode.net/code/snippet3489.htm
 */

package vavi.math;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;
import java.util.Scanner;

import vavi.util.memoization.Memoize;


/**
 * Reverse Polish Notation interpreter. 
 */
public class Rpn {
    
    /** + */
    private static final char OP_CHAR_ADD = '+';
    /** - */
    private static final char OP_CHAR_SUBTRACT = '-';
    /** * */
    private static final char OP_CHAR_MULTIPLY = '*';
    /** / */
    private static final char OP_CHAR_DIVIDE = '/';

    /** */
    public enum Op {
        ADD("+"),
        SUBTRACT("-"),
        MULTIPLY("*"),
        DIVIDE("/");
        String string;
        Op(String string) {
            this.string = string;
        }
        public String toString() {
            return string;
        }
    }

    /**
     * @param expr white space separation, ie. "1 2 +"
     */
    public double doubleValue(String expr) {
        Deque<Double> stack = new ArrayDeque<Double>();
        Scanner scan = new Scanner(expr);
        while (scan.hasNext()) {
            if (scan.hasNextDouble()) {
                stack.push(scan.nextDouble());
            } else {
                String part = scan.next();
                double a, b;
                for (int i = 0; i < part.length(); i++) {
                    switch (part.charAt(i)) {
                    case OP_CHAR_ADD:
                        a = stack.pop();
                        b = stack.pop();
                        stack.push(b + a);
                        break;
                    case OP_CHAR_SUBTRACT:
                        a = stack.pop();
                        b = stack.pop();
                        stack.push(b - a);
                        break;
                    case OP_CHAR_MULTIPLY:
                        a = stack.pop();
                        b = stack.pop();
                        stack.push(b * a);
                        break;
                    case OP_CHAR_DIVIDE:
                        a = stack.pop();
                        b = stack.pop();
                        stack.push(b / a);
                        break;
                    }
                }
            }
        }
        scan.close();
        return stack.pop();
    }

    /**
     * @param expr is only int value accepted.
     */
    public Rational rationalValue(String expr) throws RationalException {
        Deque<Rational> stack = new ArrayDeque<Rational>();
        Scanner scan = new Scanner(expr);
        while (scan.hasNext()) {
            if (scan.hasNextInt()) {
                stack.push(new Rational(scan.nextInt()));
            } else {
                String part = scan.next();
                Rational a, b;
                for (int i = 0; i < part.length(); i++) {
                    switch (part.charAt(i)) {
                    case OP_CHAR_ADD:
                        a = stack.pop();
                        b = stack.pop();
                        stack.push(b.add(a));
                        break;
                    case OP_CHAR_SUBTRACT:
                        a = stack.pop();
                        b = stack.pop();
                        stack.push(b.subtract(a));
                        break;
                    case OP_CHAR_MULTIPLY:
                        a = stack.pop();
                        b = stack.pop();
                        stack.push(b.multiply(a));
                        break;
                    case OP_CHAR_DIVIDE:
                        a = stack.pop();
                        b = stack.pop();
                        stack.push(b.divide(a));
                        break;
                    default:
                        throw new IllegalArgumentException(expr);
                    }
                }
            }
        }
        scan.close();
        return stack.pop();
    }

    /** not thread safe, for performance */
    private Deque<Rational> stack = new ArrayDeque<Rational>();

    /**
     * @param expr is only Rational value, and char operator accepted.
     * @throws ClassCastException
     */
    public Rational rationalValue(Object[] expr) throws RationalException {
        stack.clear();
        for (Object obj : expr) {
            if (Rational.class.isInstance(obj)) {
                stack.push(Rational.class.cast(obj));
            } else {
                Rational a, b;
                switch (Op.class.cast(obj)) {
                case ADD:
                    a = stack.pop();
                    b = stack.pop();
                    stack.push(b.add(a));
                    break;
                case SUBTRACT:
                    a = stack.pop();
                    b = stack.pop();
                    stack.push(b.subtract(a));
                    break;
                case MULTIPLY:
                    a = stack.pop();
                    b = stack.pop();
                    stack.push(b.multiply(a));
                    break;
                case DIVIDE:
                    a = stack.pop();
                    b = stack.pop();
                    stack.push(b.divide(a));
                    break;
                }
            }
        }
        return stack.pop();
    }

    //-----

    /** */
    public static final char NUM = 'a';
    /** */
    public static final char OP = '@';

    /**
     * http://www.ozzu.com/programming-forum/math-programming-question-t59970.html
     * not thread safe... 
     */
    public static char[][] generatePattern(int ops, int nums) {
        List<char[]> result = new ArrayList<char[]>();
        generatePattern(ops, nums, "", result);
        return result.toArray(new char[result.size()][]);
    }

    /** */
    @Memoize
    private static void generatePattern(int ops, int nums, String str, List<char[]> result) {
        if (ops == 0) {
            if (nums == 1) {
                result.add((NUM + str).toCharArray());
            } else {
                generatePattern(ops, nums - 1, NUM + str, result);
            }
        } else {
            if (nums > ops) {
                generatePattern(ops - 1, nums, OP + str, result);
                generatePattern(ops, nums - 1, NUM + str, result);
            }
        }
    }
    
    //-----

    private static class Intermediate {
        // subexpression string
        String expr;
        // the operator used to create this expression
        String oper;

        Intermediate(String expr, String oper) {
            this.expr = expr;
            this.oper = oper;
        }
    }

    /**
     */
    public static String toInfix(String postfix) {
        // Assumption: the postfix expression to be processed is space-delimited.
        // Split the individual tokens into an array for processing.
        return toInfix(postfix.split("\\s"));
    }

    /**
     * Postfix to Infix
     * @see "http://www.codeproject.com/Articles/405361/Converting-Postfix-Expressions-to-Infix"
     */
    public static String toInfix(Object[] postfixTokens) {
        // Create stack for holding intermediate infix expressions
        Deque<Intermediate> stack = new ArrayDeque<Intermediate>();

        for (Object token : postfixTokens) {
            if (Op.class.isInstance(token)) {
                token = Op.class.cast(token).string;
            }
            if (token.equals(Op.ADD.string) || token.equals(Op.SUBTRACT.string)) {
                // Get the left and right operands from the stack.
                // Note that since + and - are lowest precedence operators,
                // we do not have to add any parentheses to the operands.
                Intermediate rightIntermediate = stack.pop();
                Intermediate leftIntermediate = stack.pop();

                // construct the new intermediate expression by combining the left and right 
                // expressions using the operator (token).
                String newExpr = leftIntermediate.expr + " " + token + " " + rightIntermediate.expr;

                // Push the new intermediate expression on the stack
                stack.push(new Intermediate(newExpr, String.class.cast(token)));
            } else if (token.equals(Op.MULTIPLY.string) || token.equals(Op.DIVIDE.string)) {
                String leftExpr, rightExpr;
                
                // Get the intermediate expressions from the stack.  
                // If an intermediate expression was constructed using a lower precedent
                // operator (+ or -), we must place parentheses around it to ensure 
                // the proper order of evaluation.
                
                Intermediate rightIntermediate = stack.pop();
                if (rightIntermediate.oper.trim().equals(Op.ADD.string) || rightIntermediate.oper.trim().equals(Op.SUBTRACT.string)) {
                    rightExpr = "(" + rightIntermediate.expr.trim() + ")";
                } else {
                    rightExpr = rightIntermediate.expr;
                }

                Intermediate leftIntermediate = stack.pop();
                if (leftIntermediate.oper.trim().equals(Op.ADD.string) || leftIntermediate.oper.trim().equals(Op.SUBTRACT.string)) {
                    leftExpr = "(" + leftIntermediate.expr.trim() + ")";
                } else {
                    leftExpr = leftIntermediate.expr;
                }

                // construct the new intermediate expression by combining the left and right 
                // using the operator (token).
                String newExpr = leftExpr + " " + token + " " + rightExpr;

                // Push the new intermediate expression on the stack
                stack.push(new Intermediate(newExpr, String.class.cast(token)));
            } else {
                // Must be a number. Push it on the stack.
                stack.push(new Intermediate(String.valueOf(token), ""));
            }
        }

        // The loop above leaves the final expression on the top of the stack.
        return stack.peek().expr.trim();
    }
}

/* */
