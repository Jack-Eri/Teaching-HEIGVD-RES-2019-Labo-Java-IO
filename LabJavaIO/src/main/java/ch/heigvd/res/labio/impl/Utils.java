package ch.heigvd.res.labio.impl;

import java.util.logging.Logger;

/**
 *
 * @author Olivier Liechti
 */
public class Utils {

  private static final Logger LOG = Logger.getLogger(Utils.class.getName());

  /**
   * This method looks for the next new line separators (\r, \n, \r\n) to extract
   * the next line in the string passed in arguments. 
   * 
   * @param lines a string that may contain 0, 1 or more lines
   * @return an array with 2 elements; the first element is the next line with
   * the line separator, the second element is the remaining text. If the argument does not
   * contain any line separator, then the first element is an empty string.
   */
  public static String[] getNextLine(String lines) {

    boolean newLineFound = false;
    int i = 0;

    for (; i < lines.length(); ++i) {
      char current = lines.charAt(i);

      // New line starts in next index if current is a \n or the last char is a \r
      if (current == '\n' || (i + 1 == lines.length() && current == '\r')) {
        i++;
        newLineFound = true;
        break;

        // otherwise if the previous char is a \r new line starts at current index
      } else if (i > 0 && lines.charAt(i - 1) == '\r') {
        newLineFound = true;
        break;
      }
    }

    if (newLineFound) {
      return new String[] {lines.substring(0, i), lines.substring(i)};
    } else {
      return new String[] {"", lines};
    }

  }

}
