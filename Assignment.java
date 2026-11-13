import javax.swing.JOptionPane;

class SecretPass {
    // Minimum required data fields per UML diagram
    private String inString;
    private int stringLen;
    private String outString;

    // 50-element lookup array corresponding exactly to Table 1 (Indices 0 to 49)
    private static final char[] CHAR_TABLE = {
        'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', // 0 - 9
        '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', // 10 - 19
        'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', // 20 - 29
        ' ', '!', '@', '#', '$', '%', 'u', 'v', 'w', 'x', // 30 - 39 (' ' at 30 represents ws)
        'y', 'z', '<', '>', '?', '^', '&', '*', ';', ':'  // 40 - 49
    };

    // Default constructor
    public SecretPass() {
        this.inString = "";
        this.stringLen = 0;
        this.outString = "";
    }

    // Parameterized constructor
    public SecretPass(String inString) {
        this.inString = inString;
        this.stringLen = getStringLen(inString);
        String shifted = shiftChar(inString, this.stringLen);
        this.outString = reversedString(shifted);
    }

    // Compute number of characters excluding whitespace(s)
    public int getStringLen(String inString) {
        if (inString == null) {
            return 0;
        }
        int count = 0;
        for (int i = 0; i < inString.length(); i++) {
            if (inString.charAt(i) != ' ') {
                count++;
            }
        }
        this.stringLen = count;
        return count;
    }

    // Check validity: only small letters (a-z), numbers (0-9), or whitespace (' ')
    public Boolean checkStringValidity(String inString) {
        if (inString == null || inString.isEmpty()) {
            return false;
        }
        for (int i = 0; i < inString.length(); i++) {
            char c = inString.charAt(i);
            boolean isLower = (c >= 'a' && c <= 'z');
            boolean isDigit = (c >= '0' && c <= '9');
            boolean isSpace = (c == ' ');
            
            if (!isLower && !isDigit && !isSpace) {
                return false;
            }
        }
        return true;
    }

    // Shift each character forward by stringLen positions according to Table 1
    public String shiftChar(String inString, int stringLen) {
        if (!checkStringValidity(inString)) {
            return "invalid";
        }

        StringBuilder shifted = new StringBuilder();
        for (int i = 0; i < inString.length(); i++) {
            char c = inString.charAt(i);
            int index = findCharIndex(c);
            if (index != -1) {
                int newIndex = (index + stringLen) % CHAR_TABLE.length;
                shifted.append(CHAR_TABLE[newIndex]);
            }
        }
        return shifted.toString();
    }

    // Reverse the string literal
    public String reversedString(String inString) {
        if (inString == null) {
            return "";
        }
        return new StringBuilder(inString).reverse().toString();
    }

    // Helper method to locate character index in Table 1
    private int findCharIndex(char c) {
        for (int i = 0; i < CHAR_TABLE.length; i++) {
            if (CHAR_TABLE[i] == c) {
                return i;
            }
        }
        return -1;
    }

    // Getters for additional convenience
    public String getInString() {
        return inString;
    }

    public String getOutString() {
        return outString;
    }
}

public class Assignment {
    public static void main(String[] args) {
        // Prompt user via JOptionPane Input Dialog matching the screenshot
        String input = JOptionPane.showInputDialog(
            null,
            "Enter a string (only small letters, 0-9 or whitespace)",
            "Input",
            JOptionPane.QUESTION_MESSAGE
        );

        // Exit gracefully if user clicks Cancel or closes the dialog
        if (input == null) {
            System.exit(0);
        }

        SecretPass sp = new SecretPass(input);

        /* 
         * OPTIONAL RE-PROMPT LOOP:
         * Uncomment this block if your lecturer strictly grades Step 2's text instruction 
         * ("if this condition is not met, prompt user for a new input") instead of the 3rd screenshot.
         *
         * while (!sp.checkStringValidity(input)) {
         *     input = JOptionPane.showInputDialog(
         *         null,
         *         "Invalid input! Enter a string (only small letters, 0-9 or whitespace)",
         *         "Input",
         *         JOptionPane.QUESTION_MESSAGE
         *     );
         *     if (input == null) System.exit(0);
         * }
         */

        int len = sp.getStringLen(input);
        String shifted = sp.shiftChar(input, len);
        String reversed = sp.reversedString(shifted);

        // Format result message to match the "SecretPass result" dialog screenshot
        String resultMessage = "inString: " + input + "\n"
                             + "length: " + len + "\n"
                             + "shiftedChar: " + shifted + "\n"
                             + "reversedString: " + reversed;

        JOptionPane.showMessageDialog(
            null,
            resultMessage,
            "SecretPass result",
            JOptionPane.INFORMATION_MESSAGE
        );
    }
}