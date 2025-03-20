package ASTTree;

public class OperatorNode extends Node {
    private char operator;
    private Node left, right;

    public OperatorNode(char operator, Node left, Node right) {
        this.operator = operator;
        this.left = left;
        this.right = right;
    }

    @Override
    public double getValue() {
        double leftValue = (left != null) ? left.getValue() : 0.0;
        double rightValue = (right != null) ? right.getValue() : 0.0;
        switch (operator){
            case '+': return leftValue + rightValue;
            case '-': return leftValue - rightValue;
            case '×': return leftValue * rightValue;
            case '÷': return leftValue / rightValue;
            case '%': return leftValue  * (rightValue / 100);
            default: return 0;
        }
    }
}
