package cs3500.hw1.filesystem;

public interface FileSystem {

  /**
   * Returns the size of a file system, defined as the size of all files
   * contained within it.
   * @return the size of the files in the system in bytes
   */
  long used();

  /**
   * Returns the amount of bytes the file system can handle.
   * @return the capacity of the system in bytes
   */
  long capacity();

  /**
   * Returns true if and only if the phrase is found in the name
   * of a file or a file's contents.
   * @param phrase the phrase to look for
   * @return true if and only if that phrase exists in the system
   * @throws IllegalArgumentException if phrase is null
   */
  boolean search(String phrase);

  /**
   * Returns a human-readable view of the filesystem.
   * Contents of directories are preceded by a straight line and
   * two spaces. An example of output follows
   * +made-up-name/
   * |  +empty_dir/
   * |  +lib/
   * |  |  +file1
   * |  +usr/
   * |  |  +lib/
   * |  |  |  java.exe
   * |  |  secret.txt
   * @return an indented human-readable view of the filesystem
   */
  String prettyPrint();
}
