package modele;

public class Criterion extends CombinaisonValeurCritère {

  public String combinaison;
  public boolean valeur;

  public Criterion(String combinaison) {
    this.combinaison = combinaison.trim();
  }

  @Override
  public String toString() {
    return combinaison;
  }

  @Override
  public boolean equals(Object obj) {
    return obj instanceof Criterion ? combinaison.equals(((Criterion) obj).combinaison) : false;
  }

  @Override
  public boolean evaluate() {
    return valeur;
  }

  @Override
  String getLabel() {
    return combinaison;
  }
}