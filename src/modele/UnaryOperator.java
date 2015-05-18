package modele;

public abstract class UnaryOperator extends CombinaisonValeurCritère {

  public CombinaisonValeurCritère rightHandOperand;

  public CombinaisonValeurCritère rightHandOperand(CombinaisonValeurCritère cvc) {
    rightHandOperand = cvc;
    return this;
  }

  @Override
  public String toString() {
    return getLabel() + " " + rightHandOperand.toString();
  }
}