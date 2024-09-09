package cs3500.hw1.filesystem;

import cs3500.hw1.filesystem.ExtDirectory;
import cs3500.hw1.filesystem.FileSystem;

/**
 * A basic read-only file system. A file system is a container of files.
 * The system always contains a directory called the root directory
 * that can then contain multiple files or other directories.
 **
 * This file system keeps track of the capacity of the hard disk
 * it is normally placed (technically "mounted") upon.
 */
public class ReadOnlyFileSystem implements FileSystem {

  /**
   * Creates a read only file system with the given capacity and
   * containing the files in the given root directory.
   * @param capacity the amount of bytes this file system can hold
   * @param root the top-most directory of the system
   * @throws IllegalArgumentException if capacity is negative
   * @throws NullPointerException if root is null
   * @throws IllegalArgumentException if root's total size is greater than the capacity
   */
  public ReadOnlyFileSystem(long capacity, ExtDirectory root) {
    throw new RuntimeException("If you got this to run, your tests compile correctly against the data definitions.");
  }

  @Override
  public long used() {
    throw new RuntimeException("If you got this to run, your tests compile correctly against the data definitions.");
  }

  @Override
  public long capacity() {
    throw new RuntimeException("If you got this to run, your tests compile correctly against the data definitions.");
  }

  @Override
  public boolean search(String word) {
    throw new RuntimeException("If you got this to run, your tests compile correctly against the data definitions.");
  }

  @Override
  public String prettyPrint() {
    throw new RuntimeException("If you got this to run, your tests compile correctly against the data definitions.");
  }
}

