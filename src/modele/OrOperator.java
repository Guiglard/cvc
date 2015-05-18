package modele;

public class OrOperator extends BinaryOperator {

  public static final String OU = "ou";

  @Override
  public String getLabel() {
    return OU;
  }

  @Override
  public boolean evaluate() {
    return leftHandOperand.evaluate() || rightHandOperand.evaluate();
  }
}