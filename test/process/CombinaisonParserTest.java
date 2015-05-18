package process;

import org.junit.*;

public class CombinaisonParserTest {

  @Test
  public void testGetIndexOpérateurBinaireSuivant() {
    String combinaison = "C1";
    Assert.assertEquals(combinaison.length(), CombinaisonParser.getIndexOpérateurBinaireSuivant(combinaison));
    combinaison = "C1 ou C2";
    Assert.assertEquals(3, CombinaisonParser.getIndexOpérateurBinaireSuivant(combinaison));
    combinaison = "non C1";
    Assert.assertEquals(0, CombinaisonParser.getIndexOpérateurBinaireSuivant(combinaison));
  }

  @Test
  public void testTraduire_crtière() {
    String combinaison = "C1";
    Assert.assertEquals(combinaison, new CombinaisonParser().parse(combinaison).toString());
  }

  @Test
  public void testTraduire_opérateurBinaire() {
    String combinaison = "C1 et C2";
    Assert.assertEquals(combinaison, new CombinaisonParser().parse(combinaison).toString());
  }

  @Test
  public void testTraduire_opérateurBinaireComplexe() {
    String combinaison = "C1 et C2 ou C3";
    Assert.assertEquals(combinaison, new CombinaisonParser().parse(combinaison).toString());
  }

  @Test
  public void testTraduire_non() {
    String combinaison = "non C1";
    Assert.assertEquals(combinaison, new CombinaisonParser().parse(combinaison).toString());
  }

  @Test
  public void testTraduire_nonComplexe() {
    String combinaison = "non C1 ou non C2";
    Assert.assertEquals(combinaison, new CombinaisonParser().parse(combinaison).toString());
  }

  @Test
  public void testTraduire_opérateurBinaireParenthèses() {
    String combinaison = "(C2 ou C3)";
    Assert.assertEquals(combinaison, new CombinaisonParser().parse(combinaison).toString());
  }

  @Test
  public void testTraduire_opérateurBinaireComplexeParenthèses() {
    String combinaison = "C1 et (C2 ou C3)";
    Assert.assertEquals(combinaison, new CombinaisonParser().parse(combinaison).toString());
  }

  @Test
  public void testGetIndexParenthèseFermanteCorrespondante() {
    String combinaison = "(C1) ";
    Assert.assertEquals(3, CombinaisonParser.getIndexParenthèseFermanteCorrespondante(combinaison));
    combinaison = "(C1 et (C3 ou non C4))";
    Assert.assertEquals(21, CombinaisonParser.getIndexParenthèseFermanteCorrespondante(combinaison));
    combinaison = "(C1 et (C2 ou C3)) ou C4";
    Assert.assertEquals(17, CombinaisonParser.getIndexParenthèseFermanteCorrespondante(combinaison));
  }

  @Test
  public void testGetChaineCombinaisonSuivante() {
    String combinaison = "C1";
    Assert.assertEquals("C1", CombinaisonParser.getNextExpression(combinaison));
    combinaison = "C1 et C2";
    Assert.assertEquals("C1", CombinaisonParser.getNextExpression(combinaison));
    combinaison = "(C1 et (C2 ou C3))";
    Assert.assertEquals("(C1 et (C2 ou C3))", CombinaisonParser.getNextExpression(combinaison));
    combinaison = "non (C1 et (C2 ou C3)) ou C4";
    Assert.assertEquals("non (C1 et (C2 ou C3))", CombinaisonParser.getNextExpression(combinaison));
  }
}
