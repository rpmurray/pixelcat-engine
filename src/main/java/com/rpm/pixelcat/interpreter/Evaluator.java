package com.rpm.pixelcat.interpreter;

import java.util.ArrayList;
import java.util.List;

public class Evaluator {
    protected static Evaluator instance;

    public static Evaluator getInstance() {
        if (instance == null) {
            instance = new Evaluator();
        }

        return instance;
    }

    /**
     * protected constructor for singleton
     */
    protected Evaluator() {
        // do nothing
    }

    public List<Numeric> run(List<Expression> expressions) throws Exception {
        // setup
        List<Numeric> results = new ArrayList<>();

        for (Expression expression : expressions) {
            // evaluate expression
            Numeric result = evaluateExpression(expression);

            // add result
            results.add(result);
        }

        return results;
    }

    private Numeric evaluateExpression(Expression expression) throws Exception {
        // setup
        Numeric result = null;

        // handle types of expressions
        if (expression instanceof Numeric) {
            result = (Numeric) expression;
        } else if (expression instanceof Operation) {
            result = evaluateOperation((Operation) expression);
        } else {
            throw new Exception("Illegal expression type: " + expression.getClass());
        }

        return result;
    }

    private Numeric evaluateOperation(Operation operation) throws Exception {
        // setup
        Numeric result = null, value1 = null, value2 = null;

        // evaluate expression 1
        if (operation.getExpression1() instanceof Numeric) {
            value1 = (Numeric) operation.getExpression1();
        } else {
            value1 = evaluateExpression(operation.getExpression1());
        }

        // evaluate expression 2
        if (operation.getExpression2() instanceof Numeric) {
            value2 = (Numeric) operation.getExpression2();
        } else {
            value2 = evaluateExpression(operation.getExpression2());
        }

        switch (operation.getOperatorId()) {
            case Tokens.PLUS:
                result = new Numeric(value1.getValue() + value2.getValue());
                break;
            case Tokens.MINUS:
                result = new Numeric(value1.getValue() - value2.getValue());
                break;
            case Tokens.MUL:
                result = new Numeric(value1.getValue() * value2.getValue());
                break;
            case Tokens.DIV:
                result = new Numeric(value1.getValue() / value2.getValue());
                break;
            default:
                throw new Exception("Illegal operator type: " + operation.getOperatorId() + ":" + operation.getOperatorToken());
        }

        return result;
    }
}
