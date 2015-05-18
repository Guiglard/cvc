package process;

import java.util.*;

import modele.*;

public abstract class SymbolParser extends CombinaisonParser {

  protected CombinaisonValeurCritère readSymbol;

  public SymbolParser() {
  }

  public static List<Class> getSymbolParsers() {
    List<Class> parsers = new ArrayList<>();
    parsers.add(BinaryOperatorParser.class);
    parsers.add(BracketParser.class);
    parsers.add(OperatorNotParser.class);
    parsers.add(CriterionParser.class);
    return parsers;
  }

  public static boolean isOperator(String combinaison, String operator) {
    return combinaison.length() >= operator.length() && operator.equalsIgnoreCase(combinaison.substring(0, operator.length()));
  }

  protected abstract boolean isParsable(String expression);

  public CombinaisonValeurCritère parse(String expression, CombinaisonValeurCritère cvc) {
    this.cvc = cvc;
    this.expression = expression;
    readSymbol = getOperator();
    doStuff();
    return this.cvc;
  }

  protected abstract void doStuff();

  protected abstract CombinaisonValeurCritère getOperator();

  private static CombinaisonValeurCritère assignToRightHandOperand(CombinaisonValeurCritère cvc, CombinaisonValeurCritère readSymbol) {
    if (cvc instanceof UnaryOperator) {
      ((UnaryOperator) cvc).rightHandOperand(readSymbol);
    } else {
      cvc = readSymbol;
    }
    return cvc;
  }

  protected static class BinaryOperatorParser extends SymbolParser {

    @Override
    protected boolean isParsable(String expression) {
      return isOperator(expression, OrOperator.OU) || isOperator(expression, AndOperator.ET);
    }

    @Override
    protected void doStuff() {
      ((BinaryOperator) readSymbol).leftHandOperand(cvc);
      cvc = readSymbol;
      expression = expression.substring(readSymbol.longueur()).trim();
    }

    @Override
    protected CombinaisonValeurCritère getOperator() {
      return isOperator(expression, OrOperator.OU) ? new OrOperator() : new AndOperator();
    }
  }

  protected static class CriterionParser extends SymbolParser {
    @Override
    protected boolean isParsable(String expression) {
      return true;
    }

    @Override
    protected void doStuff() {
      cvc = assignToRightHandOperand(cvc, readSymbol);
      expression = expression.substring(readSymbol.longueur()).trim();
    }

    @Override
    protected CombinaisonValeurCritère getOperator() {
      // todo : expression.substring(0, getNextOperatorIndex())
      return new Criterion(expression);
    }
  }

  protected static class OperatorNotParser extends SymbolParser {
    @Override
    protected boolean isParsable(String expression) {
      return isOperator(expression, NotOperator.NOT);
    }

    @Override
    protected void doStuff() {
      cvc = assignToRightHandOperand(cvc, readSymbol);
      String nextExpression = getNextExpression(expression.substring(readSymbol.longueur()).trim());
      expression = expression.substring(nextExpression.length() + readSymbol.longueur() + 1).trim();
      ((UnaryOperator) readSymbol).rightHandOperand(new CombinaisonParser().parse(nextExpression));
    }

    @Override
    protected CombinaisonValeurCritère getOperator() {
      return new NotOperator();
    }
  }

  protected static class BracketParser extends SymbolParser {
    @Override
    protected boolean isParsable(String expression) {
      return isOperator(expression, BracketOperator.LEFT_BRACKET);
    }

    @Override
    protected void doStuff() {
      cvc = assignToRightHandOperand(cvc, readSymbol);
      String nextExpression = getNextExpression(expression).trim();
      expression = expression.substring(nextExpression.length()).trim();
      ((UnaryOperator) readSymbol).rightHandOperand(new CombinaisonParser().parse(nextExpression.substring(1, nextExpression.length() - 1)));
    }

    @Override
    protected CombinaisonValeurCritère getOperator() {
      return new BracketOperator();
    }
  }
}