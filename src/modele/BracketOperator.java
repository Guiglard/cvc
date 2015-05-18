package modele;

public class BracketOperator extends UnaryOperator {

  public static final String RIGHT_BRACKET = ")";
  public static final String LEFT_BRACKET = "(";

  @Override
  public boolean evaluate() {
    return rightHandOperand.evaluate();
  }

  @Override
  public String getLabel() {
    return LEFT_BRACKET;
  }
  
  @Override
  public String toString() {
    return LEFT_BRACKET + rightHandOperand.toString() + RIGHT_BRACKET;
  }
}