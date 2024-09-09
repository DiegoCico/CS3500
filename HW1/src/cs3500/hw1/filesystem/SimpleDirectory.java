package cs3500.hw1.filesystem;

import java.util.List;

import cs3500.hw1.filesystem.ContentFile;
import cs3500.hw1.filesystem.ExtDirectory;
import cs3500.hw1.filesystem.ExtFile;

/**
 * Implementation of a directory as a container of files.
 * Of note is the directory is also part of an undirected graph,
 * meaning it contains references to its parent node.
 */
public class SimpleDirectory implements ExtDirectory {

/**
 * Creates a directory of the given name with the contents given.
 * Note the contents given must NOT include the . and .. directories.
 * If the parent is null, this directory is assumed to be a root directory.
 *
 * @param name     the name of the directory
 * @param dirs     the directories contained in the directory
 * @param contents the content files contained in the directory
 * @throws NullPointerException     if name, dirs, or contents are null
 * @throws IllegalArgumentException if name is empty (0 characters)
 * @throws IllegalArgumentException if dirs contains this directory
 */
  public SimpleDirectory(String name, List<ExtDirectory> dirs, List<ContentFile> contents) {
    throw new RuntimeException("If you got this to run, your tests compile correctly against the data definitions.");
  }

  @Override
  public List<ExtFile> contents() {
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
