package cs3500.hw1.filesystem;

/**
 * Behaviors expected of a file in a read-only file system.
 * At the moment searching is a major operation in this system.
 */
public interface ExtFile {
  /**
   * Returns the name of the file as given on creation.
   * @return the name of the file
   */
  String name();

  /**
   * Returns the size of this specific file. The definition is
   * dependent on the type of file.
   * @return the size of the file in bytes
   */
  long size();

  /**
   * Returns the size of this file and all files contained within.
   * Note that some files may not contain other files.
   * @return the size of file and all contained files in bytes
   */
  long totalSize();

  /**
   * Returns true if and only if the given phrase is contained in
   * a file. Note that a phrase is contained in a file if and only if
   * the phrase is in the name of the file or its contents. A phrase
   * is defined as some assortment of characters (meaning it can be a
   * word, part of a word, punctuation, or spaces).
   * @param phrase the phrase to find in the file
   * @return true if and only if the phrase is contained in the file
   * @throws IllegalArgumentException if phrase is null
   */
  boolean search(String phrase);

  /**
   * Returns a human-friendly representation of a file where
   * levels of indentation are applied to indicate how many
   * directories deep each file is.
   * The exact behavior of this method changes depending on the class.
   * @return a view into the filesystem
   */
  String prettyPrint();

  /**
   * Creates a simple representation of the file, usually as
   * the file's name and some property of it. This is useful
   * when debugging objects.
   * @return a debugging representation of the object
   */
  String toString();
}
