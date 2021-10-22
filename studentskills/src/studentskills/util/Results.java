package studentskills.util;


import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Set;

/**
 * Results stores the individual processed input from AVL tree class and all the errors
 * and stores it into a StringBuilder object and prints the output on standard output
 * and output file and errors in error file.
 *
 * Implements the FileDisplayInterface and StdoutDisplayInterface
 *
 * @author Rashmi Badadale
 */

public class Results implements FileDisplayInterface,StdoutDisplayInterface {

    String fileName;
    StringBuilder stringBuilder = new StringBuilder();
    StringBuilder stringBuilderForErrorMsg = new StringBuilder();

    /**
     * Constructor for initializing the StringBuilder and the output filename.
     * @param outputFileName output filename/error filename
     */
    public Results(String outputFileName){
       fileName = outputFileName;
    }

    /**
     * Overriding the toString() method
     * @return String
     */

    public String toString() {
        return "Implements the FileDisplayInterface and StdoutDisplayInterface for storing the results from the AVL tree class" +
                "into a data structure";
    }

    /**
     *Stores the BNumber and Set of skills into StringBuilder object
     * @param bNumberIn output line to be written to the output file and the standard console.
     * @param skillsIn Set of Strings
     */
    public void storeResults (int bNumberIn, Set<String> skillsIn){
        stringBuilder.append(bNumberIn);
        for (String string: skillsIn){
            stringBuilder.append(",");
            stringBuilder.append(string);
        }
        stringBuilder.append("\n");
    }

    /**
     * Stores the errors into StringBuilder object
     * @param errMsgIn String error message
     */

    public void storeErrorMessages(String errMsgIn){
        stringBuilderForErrorMsg.append(errMsgIn);
    }

    /**
     * Creates PrintStream for printing the stored error messages into the error file
     * @throws IOException On any errors while writing into the error file
     */
    public void printErrorFile() throws IOException {
        PrintStream printStream = new PrintStream(fileName);
        System.setErr(printStream);
        printStream.println(stringBuilderForErrorMsg);
        printStream.close();
    }

    /**
     * Creates BufferedWriter for printing the stored output into the output file
     * @throws IOException On any errors while writing into the output file
     */

    @Override
    public void writeToFile() throws IOException {
        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(fileName));
        bufferedWriter.write(stringBuilder.toString());
        bufferedWriter.close();

    }

    /**
     * Writes the output from the stringBuilder on to the std output
     */

    @Override
    public void writeToStdout() {
        System.out.println(stringBuilder.toString());
    }
}
