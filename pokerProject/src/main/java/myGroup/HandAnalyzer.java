package myGroup;

import lombok.extern.slf4j.Slf4j;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author diego cisneros
 */
@Slf4j
public class HandAnalyzer {
  Integer player1Wins = 0;
  Integer player2Wins = 0;
  Integer neitherWins = 0;

  /**
   * Simple method used to compare the hands rank and determine the winner
   *
   * @param hand1 first hand to compare
   * @param hand2 second hand to compare
   * @return the winner hand
   */
  public Hand compareHands(Hand hand1, Hand hand2) {
    evaluator(hand1);
    evaluator(hand2);
    if (hand1.getRank() > hand2.getRank()) {
      log.info("hand1 won");
      player1Wins++;
      return hand1;
    } else if (hand1.getRank() < hand2.getRank()) {
      log.info("hand2 won");
      player2Wins++;
      return hand2;
    } else {
      log.info("Neither player wins");
      neitherWins++;
      return null;
    }
  }

  /**
   * Method in charge of determining the hand's rank based on a deterministi  algorithm that takes into account the hand's cards' suite and value
   *
   * @param hand the hand to be evaluated
   * @return hand with the appropriate rank assigned
   */
  public Hand evaluator(Hand hand) {
    LinkedHashMap<Integer, Long> reverseSortedMap = new LinkedHashMap<>();

    List<Integer> values = hand.getCards().stream().map((card) -> {
      return convertValues(card.getValue());
    }).sorted(Comparator.reverseOrder()).collect(Collectors.toList());

    Map<Integer, Long> map = values.stream()
        .collect(Collectors.groupingBy(s -> s, Collectors.counting()));

    map.entrySet().stream().sorted(Map.Entry.comparingByKey(Comparator.reverseOrder()))
        .forEachOrdered(x -> reverseSortedMap.put(x.getKey(), x.getValue()));

    if (reverseSortedMap.size() == 4) {
      hand.setRank(2);
    } else if (reverseSortedMap.size() == 3) {
      if (reverseSortedMap.containsValue(3L)) {
        hand.setRank(4);
      }
      if (reverseSortedMap.containsValue(2L)) {
        hand.setRank(3);
      }
    } else if (reverseSortedMap.size() == 2) {
      if (reverseSortedMap.containsValue(3L)) {
        hand.setRank(7);
      }
      if (reverseSortedMap.containsValue(4L)) {
        hand.setRank(8);
      }
    }
    Boolean flush = true;
    String s = hand.getCards().get(0).getSuit();
    for (Card c : hand.getCards()) {
      if (!s.equals(c.getSuit())) {
        flush = false;
      }
    }
    if ((Integer) reverseSortedMap.keySet().toArray()[0] - (Integer) reverseSortedMap.keySet().toArray()[reverseSortedMap.keySet().toArray().length - 1] == 4) {
      hand.setRank(5);
    }
    if (hand.getRank() != null && hand.getRank() == 5 && flush) {
      hand.setRank(9);
    } else if (hand.getRank() != null && hand.getRank() < 6 && flush) {
      hand.setRank(6);
    }
    if ((Integer) reverseSortedMap.keySet().toArray()[0] == 14 && flush) {
      hand.setRank(10);
    }

    if (hand.getRank() == null) {
      hand.setRank(1);
    }
    log.info("{}", hand);
    return hand;

  }

  /**
   * Method to convert all values to their Integer representation
   *
   * @param val the input value to be converted
   * @return the numeric representation of the value provided
   */
  public Integer convertValues(String val) {
    if (val.equals("2")) {
      return 2;
    } else if (val.equals("3")) {
      return 3;
    } else if (val.equals("4")) {
      return 4;
    } else if (val.equals("5")) {
      return 5;
    } else if (val.equals("6")) {
      return 6;
    } else if (val.equals("7")) {
      return 7;
    } else if (val.equals("8")) {
      return 8;
    } else if (val.equals("9")) {
      return 9;
    } else if (val.equals("T")) {
      return 10;
    } else if (val.equals("J")) {
      return 11;
    } else if (val.equals("Q")) {
      return 12;
    } else if (val.equals("K")) {
      return 13;
    } else if (val.equals("A")) {
      return 14;
    } else return -1;
  }

  /**
   * Method used to extract the hands of each player out of the supplied lines of data
   *
   * @param handsData  the supplied lines of data
   * @param startIndex the beginning index of the characters to be taken into account for the hands
   * @param endIndex   the index of the final characters to be taken into account for the hands
   * @return hands built from the data
   */
  public List<Hand> getHands(List<String> handsData, Integer startIndex, Integer endIndex) {
    return handsData.stream().map(
        (handData) -> {
          String allCardsInHand = String.join(",", Arrays.copyOfRange(handData.split(" "), startIndex, endIndex));
          List<Card> cardsInHand = Arrays.stream(allCardsInHand.split(","))
              .map(cardData -> new Card(cardData.substring(0, 1), cardData.substring(1, 2))).collect(Collectors.toList());

          Hand hand = new Hand();
          hand.setCards(cardsInHand);
          return hand;
        }
    ).collect(Collectors.toCollection(ArrayList::new));

  }

  public void analyze(String inputLocation, String outputLocation) {
    Path path = Paths.get(inputLocation);
    List<String> handsData = null;
    List<Hand> firstPlayerHands = null;
    List<Hand> secondPlayerHand = null;


    try {
      handsData = Files.readAllLines(path);
    } catch (IOException e) {
      e.printStackTrace();
    }

    firstPlayerHands = getHands(handsData, 0, 5);

    secondPlayerHand = getHands(handsData, 5, 10);

    log.info("{}", handsData);

    for (int i = 0; i < firstPlayerHands.size(); i++) {
      compareHands(firstPlayerHands.get(i), secondPlayerHand.get(i));
    }

    try {
      FileWriter fileWriter = new FileWriter(outputLocation);
      try (PrintWriter printWriter = new PrintWriter(fileWriter)) {
        printWriter.printf("1:%d \n", player1Wins);
        printWriter.printf("2:%d \n", player2Wins);
        printWriter.printf("3:%d \n", neitherWins);
        printWriter.printf("4: ---------PLAYER 1 --------- | ------ PLAYER 2 -------------- \n");
        printWriter.printf("\t\t\tnot attempted");

      }
    } catch (IOException e) {
      e.printStackTrace();
    }
    log.info("first player won in total : " + player1Wins);
    log.info("second player won in total : " + player2Wins);
    log.info("neither player won in total : " + neitherWins);
  }

}
