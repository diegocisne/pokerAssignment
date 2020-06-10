package myGroup;

import lombok.Data;

import java.util.List;

/**
 * @author diego cisneros
 */
@Data
public class Hand {
  private List<Card> cards;
  private Integer rank;

}
