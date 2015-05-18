package modele;

public abstract class BinaryOperator extends UnaryOperator {

  public CombinaisonValeurCritère leftHandOperand;

  public CombinaisonValeurCritère leftHandOperand(CombinaisonValeurCritère cvc) {
    leftHandOperand = cvc;
    return this;
  }

  @Override
  public String toString() {
    return leftHandOperand.toString() + " " + super.toString();
  }
}