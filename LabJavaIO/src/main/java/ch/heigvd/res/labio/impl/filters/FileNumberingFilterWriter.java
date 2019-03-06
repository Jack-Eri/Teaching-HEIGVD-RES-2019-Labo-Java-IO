package ch.heigvd.res.labio.impl.filters;

import java.io.FilterWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.logging.Logger;

/**
 * This class transforms the streams of character sent to the decorated writer.
 * When filter encounters a line separator, it sends it to the decorated writer.
 * It then sends the line number and a tab character, before resuming the write
 * process.
 *
 * Hello\n\World -> 1\Hello\n2\tWorld
 *
 * @author Olivier Liechti
 */
public class FileNumberingFilterWriter extends FilterWriter {

  private static final Logger LOG = Logger.getLogger(FileNumberingFilterWriter.class.getName());
  private int lineNumber = 0;
  private char lastChar;

  public FileNumberingFilterWriter(Writer out) {
    super(out);
  }

  @Override
  public void write(String str, int off, int len) throws IOException {

    int max = Math.min(str.length(), off + len);
    for (int i = off; i < max; ++i) {
      write((int) str.charAt(i));
    }
  }

  @Override
  public void write(char[] cbuf, int off, int len) throws IOException {

    //TODO: ask about implementation

    int max = Math.min(cbuf.length, off + len);
    for (int i = off; i < max; ++i) {
      write((int) cbuf[i]);
    }
  }

  @Override
  public void write(int c) throws IOException {

    // Detect first line
    if (lineNumber == 0) {

      writeLineNumber();
      out.write(c);

    } else {

      if ((char) c == '\n') {
        out.write(c);
        writeLineNumber();

      } else if (lastChar == '\r' && (char) c != '\n') {
        writeLineNumber();
        out.write(c);

      } else {
        out.write(c);
      }
    }

    lastChar = (char) c;
  }

  private void writeLineNumber() throws IOException {
    out.write(++lineNumber + "\t");
  }
}
