package myGroup;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * @author diego cisneros
 */

public class AppTest {


  @Test
  public void testConvertValues() {
    Integer conversionTestValue1 = 14;
    Integer conversionTestValue2 = 3;
    Integer conversionTestValue3 = 11;

    HandAnalyzer handAnalyzer = new HandAnalyzer();
    assertEquals(conversionTestValue1, handAnalyzer.convertValues("A"));
    assertEquals(conversionTestValue2, handAnalyzer.convertValues("3"));
    assertEquals(conversionTestValue3, handAnalyzer.convertValues("J"));
  }

  @Test
  public void testGetHands() {
    HandAnalyzer handAnalyzer = new HandAnalyzer();
    List<String> handsData = new ArrayList<>();
    handsData.add("8C TS KC 9H 4S 7D 2S 5D 3S AC");
    handsData.add("5C AD 5D AC 9C 7C 5H 8D TD KS");

    Card card1 = new Card("8", "C");
    Card card2 = new Card("T", "S");
    Card card3 = new Card("K", "C");
    Card card4 = new Card("9", "H");
    Card card5 = new Card("4", "S");

    Card card6 = new Card("5", "C");
    Card card7 = new Card("A", "D");
    Card card8 = new Card("5", "D");
    Card card9 = new Card("A", "C");
    Card card10 = new Card("9", "C");

    List<Card> cardList = new ArrayList<>();
    cardList.add(card1);
    cardList.add(card2);
    cardList.add(card3);
    cardList.add(card4);
    cardList.add(card5);

    Hand hand1 = new Hand();
    hand1.setCards(cardList);

    List<Card> cardList2 = new ArrayList<>();
    cardList2.add(card6);
    cardList2.add(card7);
    cardList2.add(card8);
    cardList2.add(card9);
    cardList2.add(card10);

    Hand hand2 = new Hand();
    hand2.setCards(cardList2);

    List<Hand> handListExpected = new ArrayList<>();
    handListExpected.add(hand1);
    handListExpected.add(hand2);

    assertEquals(handListExpected, handAnalyzer.getHands(handsData, 0, 5));
  }


  @Test
  public void testEvaluator() {
    HandAnalyzer handAnalyzer = new HandAnalyzer();
    Card card1 = new Card("A", "C");
    Card card2 = new Card("11", "D");
    Card card3 = new Card("2", "S");
    Card card4 = new Card("A", "S");
    Card card5 = new Card("9", "C");

    List<Card> cardList = new ArrayList<>();
    cardList.add(card1);
    cardList.add(card2);
    cardList.add(card3);
    cardList.add(card4);
    cardList.add(card5);

    Hand hand = new Hand();
    Hand handExpected = new Hand();
    hand.setCards(cardList);
    handExpected.setCards(cardList);
    handExpected.setRank(2);

    assertEquals(handExpected, handAnalyzer.evaluator(hand));

  }


  @Test
  public void testCompareHands() {
    HandAnalyzer handAnalyzer = new HandAnalyzer();
    Card card1 = new Card("A", "C");
    Card card2 = new Card("11", "D");
    Card card3 = new Card("2", "S");
    Card card4 = new Card("A", "S");
    Card card5 = new Card("9", "C");
    Card card6 = new Card("A", "H");
    Card card7 = new Card("K", "D");
    Card card8 = new Card("Q", "S");
    Card card9 = new Card("J", "S");
    Card card10 = new Card("T", "C");

    List<Card> cardList = new ArrayList<>();
    cardList.add(card1);
    cardList.add(card2);
    cardList.add(card3);
    cardList.add(card4);
    cardList.add(card5);

    Hand hand1 = new Hand();
    hand1.setCards(cardList);

    List<Card> cardList2 = new ArrayList<>();
    cardList2.add(card6);
    cardList2.add(card7);
    cardList2.add(card8);
    cardList2.add(card9);
    cardList2.add(card10);

    Hand hand2 = new Hand();
    hand2.setCards(cardList2);


    assertEquals(hand2, handAnalyzer.compareHands(handAnalyzer.evaluator(hand1), handAnalyzer.evaluator(hand2)));

  }
}
