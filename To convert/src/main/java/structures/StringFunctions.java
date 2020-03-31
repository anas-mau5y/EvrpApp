package structures;

public final class StringFunctions {

    public static String changeCharacter(String sourceString, int charIndex, char newChar)
    {
        return (charIndex > 0 ? sourceString.substring(0, charIndex) : "")
                + Character.toString(newChar) + (charIndex < sourceString.length() - 1 ? sourceString.substring(charIndex + 1) : "");
    }

    //------------------------------------------------------------------------------------
    //	This method replicates the classic C string function 'isxdigit' (and 'iswxdigit').
    //------------------------------------------------------------------------------------
    public static boolean isXDigit(char character)
    {
        if (Character.isDigit(character))
            return true;
        else if ("ABCDEFabcdef".indexOf(character) > -1)
            return true;
        else
            return false;
    }

    //------------------------------------------------------------------------------------
    //	This method replicates the classic C string function 'strchr' (and 'wcschr').
    //------------------------------------------------------------------------------------
    public static String strChr(String stringToSearch, char charToFind)
    {
        int index = stringToSearch.indexOf(charToFind);
        if (index > -1)
            return stringToSearch.substring(index);
        else
            return null;
    }

    //------------------------------------------------------------------------------------
    //	This method replicates the classic C string function 'strrchr' (and 'wcsrchr').
    //------------------------------------------------------------------------------------
    public static String strRChr(String stringToSearch, char charToFind)
    {
        int index = stringToSearch.lastIndexOf(charToFind);
        if (index > -1)
            return stringToSearch.substring(index);
        else
            return null;
    }

    //------------------------------------------------------------------------------------
    //	This method replicates the classic C string function 'strstr' (and 'wcsstr').
    //------------------------------------------------------------------------------------
    public static String strStr(String stringToSearch, String stringToFind)
    {
        int index = stringToSearch.indexOf(stringToFind);
        if (index > -1)
            return stringToSearch.substring(index);
        else
            return null;
    }

    //------------------------------------------------------------------------------------
    //	This method replicates the classic C string function 'strtok' (and 'wcstok').
    //------------------------------------------------------------------------------------
    private static String activeString;
    private static int activePosition;
    public static String strTok(String stringToTokenize, String delimiters)
    {
        if (stringToTokenize != null)
        {
            activeString = stringToTokenize;
            activePosition = -1;
        }

        //the stringToTokenize was never set:
        if (activeString == null)
            return null;

        //all tokens have already been extracted:
        if (activePosition == activeString.length())
            return null;

        //bypass delimiters:
        activePosition++;
        while (activePosition < activeString.length() && delimiters.indexOf(activeString.charAt(activePosition)) > -1)
        {
            activePosition++;
        }

        //only delimiters were left, so return null:
        if (activePosition == activeString.length())
            return null;

        //get starting position of string to return:
        int startingPosition = activePosition;

        //read until next delimiter:
        do
        {
            activePosition++;
        } while (activePosition < activeString.length() && delimiters.indexOf(activeString.charAt(activePosition)) == -1);

        return activeString.substring(startingPosition, activePosition);
    }
}