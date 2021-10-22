package studentskills.driver;

import studentskills.mytree.TreeHelper;
import studentskills.util.FileDisplayInterface;
import studentskills.util.FileProcessor;
import studentskills.util.MyLogger;
import studentskills.util.Results;
import studentskills.util.StdoutDisplayInterface;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

/**
 * @author Rashmi Badadale
 */


public class Driver {
    public static final int NUMBER_OF_REPLICAS = 3;
    private static final int REQUIRED_NUMBER_OF_CMDLINE_ARGS = 7;

    public static void main(String[] args) throws IOException {
        FileProcessor fileProcessor = null;
        Results  results = null;
        try {
            if ((args.length != REQUIRED_NUMBER_OF_CMDLINE_ARGS) ||
                    (args[0].equals("${input}")) ||
                    (args[1].equals("${modify}")) ||
                    (args[2].equals("${out1}")) ||
                    (args[3].equals("${out2}")) ||
                    (args[4].equals("${out3}")) ||
                    (args[5].equals("${error}")) ||
                    (args[6].equals("${debug}"))) {

                throw new IllegalArgumentException("Error: Incorrect number of arguments. Program accepts " + REQUIRED_NUMBER_OF_CMDLINE_ARGS + " arguments.");
            }
            if (args[0].isEmpty() || args[1].isEmpty() || args[2].isEmpty() ||
                    args[3].isEmpty() || args[4].isEmpty() || args[5].isEmpty() || args[6].isEmpty()) {
                throw new IllegalArgumentException(" Provided Invalid arguments");
            }
            if (Integer.parseInt(args[6]) < 0 || Integer.parseInt(args[6]) > 7) {
                throw new IllegalArgumentException("Debug value should be between 1 and 6.");
            }
            TreeHelper treeHelper = new TreeHelper(args[2], args[3], args[4]);
            fileProcessor = new FileProcessor(args[0], args[1]);
            MyLogger.setDebugValue(Integer.parseInt(args[6]));
            results = new Results(args[5]);
            List<Results> listOfResultsInstances;
            fileProcessor.getWords(treeHelper, results);
            fileProcessor.getWordsForModify(treeHelper, results);
            listOfResultsInstances = treeHelper.printTrees();
            FileDisplayInterface fileDisplayInterface;
            StdoutDisplayInterface stdoutDisplayInterface;
            fileDisplayInterface = listOfResultsInstances.get(0);
            stdoutDisplayInterface = listOfResultsInstances.get(0);
            fileDisplayInterface.writeToFile();
            stdoutDisplayInterface.writeToStdout();
            MyLogger.writeMessage("Output contents from stored data structure printed to file " + args[2] + ".",
                    MyLogger.DebugLevel.RESULTS);
            fileDisplayInterface = listOfResultsInstances.get(1);
            stdoutDisplayInterface = listOfResultsInstances.get(1);
            stdoutDisplayInterface.writeToStdout();
            fileDisplayInterface.writeToFile();
            MyLogger.writeMessage("Output contents from stored data structure printed to file " + args[3] + ".",
                    MyLogger.DebugLevel.RESULTS);
            fileDisplayInterface = listOfResultsInstances.get(2);
            stdoutDisplayInterface = listOfResultsInstances.get(2);
            fileDisplayInterface.writeToFile();
            stdoutDisplayInterface.writeToStdout();
            MyLogger.writeMessage("Output contents from stored data structure printed to file " + args[4] + ".",
                    MyLogger.DebugLevel.RESULTS);
            results.printErrorFile();
            MyLogger.writeMessage("All errors/exceptions printed into Error file" + args[5] + ".",
                    MyLogger.DebugLevel.RESULTS);
        } catch (NumberFormatException numberFormatException) {
            System.err.println("Debug value should be an integer");
            numberFormatException.printStackTrace();
            System.exit(0);
        } catch (IllegalArgumentException illegalArgumentException) {
            illegalArgumentException.printStackTrace();
            System.exit(0);
        } catch (FileNotFoundException fileNotFoundException) {
            assert results != null;
            results.storeErrorMessages("File Not found!");
        } catch (SecurityException | IOException securityException) {
            securityException.printStackTrace();
        } finally {
            assert fileProcessor != null;
            fileProcessor.close();
        }
    }
}

