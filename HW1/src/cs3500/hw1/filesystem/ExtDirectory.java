package cs3500.hw1.filesystem;

import java.util.List;

/**
 * Expected behaviors of a Directory. A Directory (or folder)
 * is a file that contains a list of files.
 */
public interface ExtDirectory extends ExtFile {

  /**
   * Returns a new list of all files (including directories) in the directory.
   * The list is sorted by the name of the files.
   * Modifying this list has no effect on the filesystem.
   * @return non-empty list of files contained inside this directory
   */
  List<ExtFile> contents();

  /**
   * Returns a human-friendly representation of a file where
   * levels of indentation are applied to indicate how many
   * directories deep each file is.
   * For directories, the files are printed in alphabetical
   * order and the internal contents of internal directories
   * are also printed.
   * @return a view into the filesystem
   */
  String prettyPrint();

}
