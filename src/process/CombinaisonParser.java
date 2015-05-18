package process;

import modele.CombinaisonValeurCritère;

public class CombinaisonParser {

  protected String expression;
  protected CombinaisonValeurCritère cvc;

  public CombinaisonValeurCritère parse(String expression) {
    this.expression = expression;
    return parse();
  }

  private CombinaisonValeurCritère parse() {
    while (expression.length() != 0) {
      for (Class parserClass : SymbolParser.getSymbolParsers()) {
        try {
          SymbolParser parser = (SymbolParser) parserClass.newInstance();
          if (parser.isParsable(expression)) {
            cvc = parser.parse(expression, cvc);
            expression = parser.getExpression();
            break;
          }
        } catch (InstantiationException | IllegalAccessException e) {
          e.printStackTrace();
        }
      }
    }
    return cvc;
  }

  public static String getNextExpression(String chaine) {
    if (isParenthèseOuvrante(chaine)) {
      return chaine.substring(0, getIndexParenthèseFermanteCorrespondante(chaine) + 1);
    } else if (isNon(chaine)) {
      return chaine.substring(0, 4).concat(getNextExpression(chaine.substring(4)));
    }
    int indexOpérateurBinaireSuivant = getIndexOpérateurBinaireSuivant(chaine);
    return indexOpérateurBinaireSuivant == chaine.length() ? chaine : chaine.substring(0, indexOpérateurBinaireSuivant - 1);
  }

  public static int getIndexParenthèseFermanteCorrespondante(String chaine) {
    int compteurParenthèses = 1;
    String caractèreCourant = "";
    int i = 1;
    while (i < chaine.length() - 1) {
      caractèreCourant = chaine.substring(i, i + 1);
      if (")".equals(caractèreCourant)) {
        --compteurParenthèses;
      } else if ("(".equals(caractèreCourant)) {
        ++compteurParenthèses;
      }
      if (compteurParenthèses == 0) {
        return i;
      }
      i++;
    }
    return i;
  }

  static int getIndexOpérateurBinaireSuivant(String chaine) {
    if (isEt(chaine) || isOu(chaine) || chaine.length() == 0) {
      return 0;
    }
    return 1 + getIndexOpérateurBinaireSuivant(chaine.substring(1));
  }

  private static boolean isNon(String combinaison) {
    return combinaison.length() >= 3 && "non".equalsIgnoreCase(combinaison.substring(0, 3));
  }

  private static boolean isParenthèseOuvrante(String combinaison) {
    return combinaison.length() >= 1 && "(".equalsIgnoreCase(combinaison.substring(0, 1));
  }

  private static boolean isOu(String combinaison) {
    return combinaison.length() >= 2 && "OU".equalsIgnoreCase(combinaison.substring(0, 2));
  }

  private static boolean isEt(String combinaison) {
    return combinaison.length() >= 2 && "ET".equalsIgnoreCase(combinaison.substring(0, 2));
  }

  public String getExpression() {
    return expression;
  }

  public CombinaisonValeurCritère getCvc() {
    return cvc;
  }
}