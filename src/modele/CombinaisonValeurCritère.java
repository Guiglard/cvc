package modele;

public abstract class CombinaisonValeurCritère {

  public int longueur() {
    return getLabel().length();
  }

  abstract boolean evaluate();

  abstract String getLabel();
}