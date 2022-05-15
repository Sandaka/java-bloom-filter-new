import java.io.BufferedReader;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.math.BigInteger;
import java.net.URL;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sandaka Wijesinghe.
 * Date: 5/8/22
 */
public class WordCheckerImpl implements WordChecker {

    int arrayLength = 1000;

    int wordBitArray[];


    /**
     * create bit array with given length.
     * set all the elements in the array as zero on initialization.
     */
    WordCheckerImpl() {
        wordBitArray = new int[arrayLength];
        for (int i = 0; i < arrayLength; i++) {
            wordBitArray[i] = 0;
        }
    }

    /**
     * check weather the hash value of the given word is exists in the array
     *
     * @param word
     * @return
     */
    public boolean isWordExists(String word) throws Exception {

        URL url = new URL("http://codekata.com/data/wordlist.txt"); // get from online resource
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(url.openStream()));

        // Read file and set available hash values in array
        //BufferedReader bufferedReader = new BufferedReader(new FileReader("/Users/sandakawijesinghe/Documents/MyProjects/java-bloom-filter/src/main/resources/text.txt")); // local
        MessageDigest messageDigest = MessageDigest.getInstance("MD5"); // get MD5 instance
        String line = bufferedReader.readLine();

        //List<String> hashValueSet = new ArrayList<String>();

        while (line != null) {
            messageDigest.update(line.getBytes(), 0, line.length()); // create MD5 hash
            String hashValue = new BigInteger(1, messageDigest.digest()).toString(10);
            setPosition(hashValue);
            line = bufferedReader.readLine();

            //System.out.println("MD5: " + hashValue + " "+line);
            //hashValueSet.add(line);
            //System.out.println(line);
        }
        bufferedReader.close();

        //System.out.println("list size: " + hashValueSet.size());

        // find the word is available
        String wordMD5 = convertStringToMD5(word);

        boolean isAvailable = true;
        int wordLengthMD5 = getMD5WordLength(wordMD5);
        int remainCount = wordLengthMD5;

        for (int i = 0; i < wordLengthMD5; i = i + 3) {
            if (remainCount < 3 && remainCount > 0) {
                String remainingHash = wordMD5.substring(i, wordLengthMD5);
                int intValue = Integer.parseInt(remainingHash);

                // check value in bit array
                if (wordBitArray[intValue] != 1) {
                    isAvailable = false;
                    break;
                }

            }

            if (remainCount > 3) {
                String subPart = wordMD5.substring(i, i + 3);
                int intValue = Integer.parseInt(subPart);
                if (wordBitArray[intValue] != 1) {
                    isAvailable = false;
                    break;
                }
            }
            remainCount = remainCount - 3;
        }
        return isAvailable;
    }

    /**
     * update array by one when its found the position.
     * change the index value from zero to one.
     *
     * @param position
     */
    private void updateArray(int position) {
        wordBitArray[position] = 1;

    }

    /**
     * Taking hash value of the word and check by three digits
     *
     * @param hashValueMD5
     */
    private void setPosition(String hashValueMD5) {

        int wordLengthMD5 = getMD5WordLength(hashValueMD5);
        int remainCount = wordLengthMD5;

        for (int i = 0; i < wordLengthMD5; i = i + 3) {

            //when the remain count is less than 3
            if (remainCount < 3 && remainCount > 0) {
                String remainingHash = hashValueMD5.substring(i, wordLengthMD5);
                int intValue = Integer.parseInt(remainingHash);
                updateArray(intValue);
            }

            if (remainCount > 3) {
                String subPart = hashValueMD5.substring(i, i + 3); // divided by three
                int intValue = Integer.parseInt(subPart);
                updateArray(intValue);
            }
            remainCount = remainCount - 3;
        }
    }


    /**
     * Convert input string value to MD5 hash.
     *
     * @param text
     * @return
     * @throws Exception
     */
    private String convertStringToMD5(String text) throws Exception {
        MessageDigest messageDigest = MessageDigest.getInstance("MD5");
        messageDigest.update(text.getBytes(), 0, text.length());
        String hashString = new BigInteger(1, messageDigest.digest()).toString(10);
        return hashString;
    }

    private int getMD5WordLength(String wordMD5) {
        return wordMD5.length();
    }
}
