package ASTTree;

public class OperandNode extends Node{
    private double value;

    public OperandNode(double value) {
        this.value = value;
    }

    @Override
    public double getValue() {
        return value;
    }
}
