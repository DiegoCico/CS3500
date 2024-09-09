package cs3500.hw1.filesystem;

import cs3500.hw1.filesystem.ContentFile;

/**
 * A content file that only contains strings (i.e. chunk of characters) as content.
 * The size of a StringFile is the number of characters in its content.
 */

public class StringFile implements ContentFile {

  /**
   * Creates a file with string information.
   * @param name name of the file
   * @param contents information to be stored in the file
   * @throws NullPointerException if name or contents are null
   * @throws IllegalArgumentException if name is empty (0 characters)
   */
  public StringFile(String name, String contents) {
    throw new RuntimeException("If you got this to run, your tests compile correctly against the data definitions.");
  }

  @Override
  public String contents() {
    throw new RuntimeException("If you got this to run, your tests compile correctly against the data definitions.");
  }

  @Override
  public String name() {
    throw new RuntimeException("If you got this to run, your tests compile correctly against the data definitions.");
  }

  @Override
  public long size() {
    throw new RuntimeException("If you got this to run, your tests compile correctly against the data definitions.");
  }

  @Override
  public long totalSize() {
    throw new RuntimeException("If you got this to run, your tests compile correctly against the data definitions.");
  }

  @Override
  public boolean search(String phrase) {
    throw new RuntimeException("If you got this to run, your tests compile correctly against the data definitions.");
  }

  @Override
  public String prettyPrint() {
    throw new RuntimeException("If you got this to run, your tests compile correctly against the data definitions.");
  }
}
