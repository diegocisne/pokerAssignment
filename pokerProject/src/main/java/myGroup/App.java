package myGroup;

/**
 * @author diego cisneros
 */

public class App {


  public static void main(String[] args) {

    String inputLocation = args[0];
    String outputLocation = args[1];

    HandAnalyzer handAnalyser = new HandAnalyzer();
    handAnalyser.analyze(inputLocation, outputLocation);

  }

}
