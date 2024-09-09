package cs3500.hw1.filesystem;

/**
 * Expected behaviors of a content file. A content file is a file
 * that does NOT contain other files according to the file system.
 * (This definition allows files like ZIP files to be content files).
 */
public interface ContentFile extends ExtFile {
  /**
   * Returns the contents of the file as a String when possible.
   * The contents will be exactly the same as what was entered in
   * the constructor.
   * (Same behavior as using the command 'strings' in a terminal
   * for a file)
   * @return the contents of the file as readable characters
   */
  String contents();
}
