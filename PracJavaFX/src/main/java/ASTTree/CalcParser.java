package ASTTree;

public class CalcParser {
    private String expression;
    private int position;
    private OperandNode lastOperand;

    public CalcParser(String expression) {
        this.expression = expression.replaceAll("\\s+", "");
        this.position = 0;
    }
    public Node parse(){
        return parseExpression();
    }
    private Node parseExpression() {
        Node left = parseTerm();
        while (position < expression.length() && (expression.charAt(position) == '+' || expression.charAt(position) == '-')) {
            char operator = expression.charAt(position);
            position++;
            Node right = parseTerm();
            left = new OperatorNode(operator, left, right);
        }
        return left;
    }
    private Node parseTerm() {
        Node left = parseFactor();
        if(left instanceof OperandNode) {
            lastOperand = (OperandNode) left;
        }
        while (position < expression.length() && (expression.charAt(position) == '×' || expression.charAt(position) == '÷')) {
            char operator = expression.charAt(position);
            position++;
            Node right = parseFactor();
            left = new OperatorNode(operator, left, right);
        }
        return left;
    }
    private Node parseFactor() {
        if (position < expression.length() && expression.charAt(position) == '(') {
            position++;
            Node node = parseExpression();
            if (position < expression.length() && expression.charAt(position) == ')') {
                position++;
            }
            return node;
        }

        StringBuilder number = new StringBuilder();
        if(position == 0 && expression.charAt(position) == '-') {
            number.append(expression.charAt(position));
            position++;
        }
        while (position < expression.length() && (Character.isDigit(expression.charAt(position)) || expression.charAt(position) == '.')){
            number.append(expression.charAt(position));
            position++;
        }

        OperandNode operandNode = new OperandNode(Double.parseDouble(number.toString()));
        if(position < expression.length() && expression.charAt(position) == '%'){
            position++;
            return new OperatorNode('×', lastOperand, new OperatorNode('÷', operandNode, new OperandNode(100)));
        }
        return operandNode;
    }
}