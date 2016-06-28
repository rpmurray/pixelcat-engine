package com.rpm.pixelcat.interpreter;

public class Operation implements Expression {
    private Integer operatorId;
    private Expression expression1;
    private Expression expression2;

    public Operation(Integer operatorId, Expression expression1, Expression expression2) {
        this.operatorId = operatorId;
        this.expression1 = expression1;
        this.expression2 = expression2;
    }

    public Integer getOperatorId() {
        return operatorId;
    }

    public Expression getExpression1() {
        return expression1;
    }

    public Expression getExpression2() {
        return expression2;
    }

    public String getOperatorToken() {
        return Tokens.terminalNames[operatorId];
    }

    @Override
    public String toString() {
        return "{" + expression1 + " '" + getOperatorToken() + "' " + expression2 + '}';
    }

    public String toStringVerbose() {
        return "Operation{" +
            "operatorId=" + operatorId +
            ", operatorToken=" + getOperatorToken() +
            ", expression1=" + expression1 +
            ", expression2=" + expression2 +
            '}';
    }
}
