package modele;

public class AndOperator extends BinaryOperator {

  public static final String ET = "et";

  @Override
  public String getLabel() {
    return ET;
  }

  @Override
  public boolean evaluate() {
    return leftHandOperand.evaluate() && rightHandOperand.evaluate();
  }
}