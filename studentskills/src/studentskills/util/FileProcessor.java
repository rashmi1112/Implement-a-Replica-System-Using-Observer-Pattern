package studentskills.util;

import studentskills.mytree.TreeHelper;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;

/**
 * Class to parse the input and modify file and send the parsed input to TreeHelper.
 * @author Rashmi Badadale
 */

public final class FileProcessor {
    private BufferedReader reader;
    private BufferedReader reader1;
    private String inputFilePath,modifyFilename ;
    private Queue<String> words;
    private Queue<String> wordsFromModify;

    public FileProcessor(String inputFilePathIn, String modifyFilenameIn)
            throws InvalidPathException, SecurityException, FileNotFoundException, IOException {
        inputFilePath = inputFilePathIn;
        modifyFilename = modifyFilenameIn;
            if (!Files.exists(Paths.get(inputFilePath))) {
                throw new FileNotFoundException("Invalid input file or input file in incorrect location");
            }
        MyLogger.writeMessage("Constructor for File processor class called.\n", MyLogger.DebugLevel.CONSTRUCTOR);
    }

    /**
     * Method to initialize the scanner and read the first line from the input file
     * @param results Results instance
     * @throws IOException On any I/O errors occurred during reading the file
     */

    public void poll(Results results) throws IOException {
        reader = new BufferedReader(new FileReader(new File(inputFilePath)));
        String line = reader.readLine();
        if(line !=null) {
            if (!line.matches("([0-9]+:[a-zA-Z]+,[a-zA-Z]+,[0-9(.)?]+,[a-zA-Z]+,[a-zA-Z0-9(,)?]+)")) {
                results.storeErrorMessages("Invalid input format. Input file format should be" +
                        " <B_NUMBER>:<FIRST_NAME>,<LAST_NAME>,<GPA>,<MAJOR>,[<SKILL>,[<SKILL>, ...]]\n");
                line = reader.readLine();

            }
            words = new LinkedList<>(Arrays.asList(line.split("[,|:]")));
        }
    }

    /**
     * Method to initialize teh scanner and raed the first line from the modify file
     * @param results Results instance
     * @throws IOException On any I/O errors occurred during reading the file
     */
    public void pollModify(Results results) throws IOException{
        reader1 = new BufferedReader(new FileReader(new File(modifyFilename)));
        String line = reader1.readLine();
        if (null != line) {
            if(!line.matches("[0-9]+,[0-9]+,[a-zA-Z0-9(.)?]+:[a-zA-Z0-9(.)?]+")){
                results.storeErrorMessages("Invalid modify file format. Modify file format should be" +
                        " <REPLICA_ID>,<B_NUMBER>,<ORIG_VALUE>:<NEW_VALUE> \n");
                line = reader1.readLine();
            }
            wordsFromModify = new LinkedList<>(Arrays.asList(line.split("[,|:]")));
        }
    }

    /**
     * Methods read the next line from modify  file using scanner.
     * @param results Results isntance
     * @return String Single words from the read line of input file
     * @throws IOException On any I/O errors occurred during reading the file
     */

    public String pollWords(Results results) throws IOException {
        if (null == words) return null;
        if (0 == words.size()) {
            String nextLine = reader.readLine();
            if (null == nextLine) return null;
            if (!nextLine.matches("([0-9]+:[a-zA-Z]+,[a-zA-Z]+,[0-9(.)?]+,[a-zA-Z]+,[a-zA-Z0-9(,)?]+)")) {
                results.storeErrorMessages("Invalid input format. Input file format should be" +
                        " <B_NUMBER>:<FIRST_NAME>,<LAST_NAME>,<GPA>,<MAJOR>,[<SKILL>,[<SKILL>, ...]]\n");
                nextLine = reader.readLine();
                if (null == nextLine) return null;
            }
            words.addAll(Arrays.asList(nextLine.split("[,|:]")));
        }
        return words.remove();
    }

    /**
     * Method to read the next line from modify  file using scanner.
     * @param results Results Instance
     * @return String Single words from the read line from modify file
     * @throws IOException On any I/O errors occurred during reading the file
     */

    public String pollWordsForModify(Results results) throws IOException {
        if (null == wordsFromModify) return null;
        if (0 == wordsFromModify.size()) {
            String nextLine = reader1.readLine();
            if (null == nextLine) return null;
            if (!nextLine.matches("[0-9]+,[0-9]+,[a-zA-Z0-9(.)?]+:[a-zA-Z0-9(.)?]+")) {
                results.storeErrorMessages("Invalid modify file format. Modify file format should be" +
                        " <REPLICA_ID>,<B_NUMBER>,<ORIG_VALUE>:<NEW_VALUE> \n");
                nextLine = reader1.readLine();
                if (null == nextLine) return null;
            }
            wordsFromModify.addAll(Arrays.asList(nextLine.split("[,|:]")));
        }
        return wordsFromModify.remove();
    }

    /**
     * Method to close the scanners created for reading the input & modify file
     * @throws IOException On any I/O errors occurred during reading the file
     */

    public void close() throws IOException {
        reader.close();
        reader1.close();
    }

    public String toString(){
        return "Reading the file contents from Input file " + inputFilePath + " and modify file " + modifyFilename;
    }

    /**
     * Method to parse words from input file and pass them as parameter to the TreeHelper class.
     * @param treeHelper TreeHelper Instance
     * @param results Results class instance
     * @throws IOException On any I/O error during reading the input file
     */

    public void getWords(TreeHelper treeHelper,Results results) throws IOException {
        int bNumber;
        String firstName, lastName, major;
        double gpa;
        Set<String> skills;
        try {
            poll(results);
            String currentWord = pollWords(results);
            if (null == currentWord) {
                throw new IllegalArgumentException("Empty Input File!");
            }
                while (null != currentWord) {
                    skills = new HashSet<>();
                    bNumber = Integer.parseInt(currentWord);
                    firstName = words.remove();
                    lastName = words.remove();
                    gpa = Double.parseDouble(words.remove());
                    major = words.remove();
                    while (words.size() != 0) {
                        skills.add(words.remove());
                    }
                    treeHelper.createNode(bNumber, firstName, lastName, gpa, major, skills);
                    currentWord = pollWords(results);
                }

        } catch (IllegalArgumentException | CloneNotSupportedException illegalArgumentException) {
            illegalArgumentException.printStackTrace();
        }finally {
            MyLogger.writeMessage("Contents of Input file " + inputFilePath + " were read and processed.\n",
                    MyLogger.DebugLevel.FILE_PROCESSOR);
        }
    }

    /**
     * Method to parse words from modify file and pass them as parameter to the TreeHelper class.
     * @param treeHelper TreeHelper Instance
     * @param results Results Instance
     * @throws IOException On any I/O error during reading the modify file
     */

    public void getWordsForModify(TreeHelper treeHelper, Results results) throws IOException {
            int replicaID, bNumber;
                pollModify(results);
                String currentWord = pollWordsForModify(results);
                    while (null != currentWord) {
                        replicaID = Integer.parseInt(currentWord);
                        bNumber = Integer.parseInt(wordsFromModify.remove());
                        var valueToBeChanged = wordsFromModify.remove();
                            var valueChangeTo = wordsFromModify.remove();
                            treeHelper.modifyNode(replicaID, bNumber, valueToBeChanged, valueChangeTo,results);
                        currentWord = pollWordsForModify(results);
                    }
                MyLogger.writeMessage("Contents of Modify file " + modifyFilename + " were read and processed.\n", MyLogger.DebugLevel.FILE_PROCESSOR);
            }
}

