import java.util.Scanner;
import java.util.logging.Logger;

/**
 * Created by Sandaka Wijesinghe.
 * Created on 5/8/2022.
 */
public class BloomFilter {

    private static final Logger logger = Logger.getLogger(BloomFilter.class.getName());

    public static void main(String args[]) throws Exception {

        Scanner myObj = new Scanner(System.in);
        System.out.print("Enter Text: ");

        String inputValue = myObj.nextLine();  // Read user input
        checkAvailability(inputValue);
    }

    private static boolean checkAvailability(String input) {
        WordChecker wordChecker = new WordCheckerImpl();
        boolean isExists = false;
        try {
            isExists = wordChecker.isWordExists(input);
        } catch (Exception e) {
            logger.info("Error while check the availability of word ::: " + input + " EXCEPTION found::: " + e);
        }

        System.out.println("Your value is exists in the list: " + isExists);
        return isExists;
    }
}
