# pokerAssignment

In order to run the mvn managed project contained in this reposity you will need to have maven installed, please follow the instructions:

0. You can find instructions on how to download maven from the [official project site](https://maven.apache.org/install.html)
1.Clone this repository to your local machine.
2.Navigate to pokerAssignment/pokerProject/
3.Run the following command to compile the java sources. `mvn compile`
4.Now to create an executable JAR file out of our project. `mvn clean package`
5.Finally, you can run the packed program. `mvn exec:java -Dexec.args="INPUT_PATH OUTPUT_PATH"`
  *INPUT_PATH is the full path to where the pokerdata.txt file is located *
  *OUTPUT_PATH is the full path to where the output file will be created after execution *
