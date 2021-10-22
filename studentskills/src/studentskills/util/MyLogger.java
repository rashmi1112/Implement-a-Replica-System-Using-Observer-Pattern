package studentskills.util;

/**
 * Logger to set the debug level and print the debug messages.
 * @author Rashmi Badadale
 */
public class MyLogger {
    /**
     * Enum to store various level for debugging.
     */
    public enum DebugLevel {
        CONSTRUCTOR,
        FILE_PROCESSOR,
        NODE,
        TREE,
        OBSERVER_PATTERN,
        OUTPUT_DATA_STORED,
        RESULTS,
        NONE
    }

    private static DebugLevel debugLevel;

    /**
     * Method to set the debug level
     * @param levelIn int debug level value from the command line
     */
    public static void setDebugValue (int levelIn) {
        switch (levelIn) {
            case 7: debugLevel = DebugLevel.RESULTS; break;
            case 6: debugLevel = DebugLevel.OUTPUT_DATA_STORED; break;
            case 5: debugLevel = DebugLevel.OBSERVER_PATTERN; break;
            case 4: debugLevel = DebugLevel.TREE; break;
            case 3: debugLevel = DebugLevel.NODE; break;
            case 2: debugLevel = DebugLevel.CONSTRUCTOR; break;
            case 1: debugLevel = DebugLevel.FILE_PROCESSOR; break;
            default: debugLevel = DebugLevel.NONE; break;
        }
    }

    public static void setDebugValue (DebugLevel levelIn) {
        debugLevel = levelIn;
    }

    /**
     * Method to print the debug message on the std output.
     * @param message String debug message
     * @param levelIn int debug level
     */
    public static void writeMessage (String     message  ,
                                     DebugLevel levelIn ) {
        if (levelIn == debugLevel)
            System.out.println(message);
    }

    /**
     * Overriding toString method
     * @return String
     */

    public String toString() {
        return "The debug level has been set to the following " + debugLevel;
    }

}
