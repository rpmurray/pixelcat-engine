package info.masterfrog.pixelcat.interpreter;

public class Numeric implements Expression {
    private Double value;

    public Numeric(Double value) {
        this.value = value;
    }

    public Double getValue() {
        return value;
    }

    @Override
    public String toString() {
        return "<" + value + '>';
    }

    public String toStringVerbose() {
        return "Numeric{" +
            "value=" + value +
            '}';
    }
}
