package cs3500.hw1.filesystem;

import org.junit.Assert;
import org.junit.Test;

import java.util.List;

/**
 * A set of tests to make sure the file system classes, like ReadOnlyFileSystem,
 * SimpleDirectory, and StringFile, are working as expected.
 */
public class ExamplarFileSystem {

  /**
   * Makes sure a StringFile contains the right content.
   */
  @Test
  public void testStringFileContents() {
    ContentFile file = new StringFile("file1", "Hello World!");
    Assert.assertEquals("Content mismatch", "Hello World!", file.contents());
  }

  /**
   * Tests if a search for a phrase in a StringFile returns true when it's there.
   */
  @Test
  public void testSearchInStringFile() {
    ContentFile file = new StringFile("file1", "Searchable content");
    Assert.assertTrue("Phrase not found", file.search("Search"));
  }

  /**
   * Tests if a search for a phrase in a StringFile returns true when it's there.
   */
  @Test (expected = IllegalArgumentException.class)
  public void testSearchInADirectoryStringFileNull() {
    ContentFile file = new StringFile("file1", "Searchable content");
    ExtDirectory subDir = new SimpleDirectory("subDir", List.of(), List.of());
    ExtDirectory dir = new SimpleDirectory("root", List.of(subDir), List.of(file));
    Assert.assertFalse("Phrase not found", dir.search(null));
  }

  /**
   * Tests if a search for a phrase not in the file returns false.
   */
  @Test
  public void testSearchInStringFileFailure() {
    ContentFile file = new StringFile("file1", "This is some content");
    Assert.assertFalse("Phrase shouldn't be found", file.search("NotInFile"));
  }

  /**
   * Tests if the search in the ReadOnlyFileSystem works.
   */
  @Test
  public void testFileSystemSearch() {
    ContentFile file = new StringFile("file1", "Find me");
    ExtDirectory root = new SimpleDirectory("root", List.of(), List.of(file));
    ReadOnlyFileSystem fileSystem = new ReadOnlyFileSystem(100 * Size.KILOBYTE.inBytes, root);

    Assert.assertTrue("Phrase should be found in system", fileSystem.search("Find"));
  }

  /**
   * Checks if a StringFile constructor throws an IllegalArgumentException for an empty name.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testStringFileConstructorEmptyName() {
    new StringFile("", "content");
  }

  /**
   * Makes sure a null content in StringFile throws a NullPointerException.
   */
  @Test(expected = NullPointerException.class)
  public void testStringFileConstructorNullContent() {
    new StringFile("file1", null);
  }

  /**
   * Tests if SimpleDirectory constructor throws NullPointerException for null contents.
   */
  @Test(expected = NullPointerException.class)
  public void testSimpleDirectoryConstructorNullContents() {
    new SimpleDirectory("dir", List.of(), null);
  }

  /**
   * Makes sure the filesystem throws NullPointerException when the root directory is null.
   */
  @Test(expected = NullPointerException.class)
  public void testNullRootDirectoryInFileSystem() {
    new ReadOnlyFileSystem(100 * Size.KILOBYTE.inBytes, null);
    new ReadOnlyFileSystem(100 * Size.KILOBYTE.inBytes, null);
  }

  /**
   * Tests if the search is case-sensitive.
   */
  @Test
  public void testSearchCaseSensitivity() {
    ContentFile file = new StringFile("file1", "Hello World");
    Assert.assertTrue("Case-sensitive search failed", file.search("Hello"));
    Assert.assertFalse("Case mismatch", file.search("hello"));
  }

  /**
   * Tests if searching for an empty string returns true.
   */
  @Test
  public void testSearchEmptyPhrase() {
    ContentFile file = new StringFile("file1", "content");
    Assert.assertTrue("Empty string should be found", file.search(""));
  }

  /**
   * Tests if searching for an empty dir returns true.
   */
  @Test
  public void testSearchEmptyDirectory() {
    ExtDirectory dir = new SimpleDirectory("dir", List.of(), List.of());
    Assert.assertTrue("Empty string should be found", dir.search(""));
  }

  /**
   * Tests the prettyPrint functionality with an empty root directory.
   */
  @Test
  public void testPrettyPrintEmptyRootDirectory() {
    ExtDirectory root = new SimpleDirectory("root", List.of(), List.of());
    String expected = "+-root/";
    Assert.assertEquals("Pretty print mismatch", expected, root.prettyPrint());
  }

  /**
   * Tests the prettyPrint functionality with an empty root directory.
   */
  @Test
  public void testPrettyPrintTwoRootDirectory() {
    ExtDirectory dir = new SimpleDirectory("Dir", List.of(), List.of());
    ExtDirectory root = new SimpleDirectory("root", List.of(dir), List.of());

    String expected = "+-root/\n" +
                      "| +-Dir/";
    Assert.assertEquals("Pretty print mismatch", expected, root.prettyPrint());
  }

  /**
   * Tests the totalSize method with multiple files.
   */
  @Test
  public void testTotalSizeWithMultipleFiles() {
    ContentFile file1 = new StringFile("file1.txt", "Hello");
    ContentFile file2 = new StringFile("file2.txt", "World");
    ExtDirectory root = new SimpleDirectory("root", List.of(), List.of(file1, file2));

    long expectedSize = file1.size() + file2.size() + root.size();
    Assert.assertEquals("Total size mismatch", expectedSize, root.totalSize());
  }

  /**
   * Tests the totalSize method with multiple files and subdirectories.
   */
  @Test
  public void testTotalSizeWithMultipleFilesAndSubDirectories() {
    ContentFile file1 = new StringFile("file1.txt", "Hello");
    ContentFile file2 = new StringFile("file2.txt", "World");
    ExtDirectory subDir = new SimpleDirectory("subDir", List.of(), List.of(file2));
    ExtDirectory root = new SimpleDirectory("root", List.of(subDir), List.of(file1));

    long expectedSize = file1.size() + file2.size() + root.size() + subDir.size();
    Assert.assertEquals("Total size mismatch", expectedSize, root.totalSize());
  }

  /**
   * Tests the to try to add a new content.
   */
  @Test
  public void testAddingContents() {
    ContentFile file1 = new StringFile("file1.txt", "Hello");
    ExtDirectory root = new SimpleDirectory("root", List.of(), List.of(file1));
    ContentFile file2 = new StringFile("file2.txt", "World");
    root.contents().add(file2);

    Assert.assertEquals("Total size mismatch", List.of(file1), root.contents());
  }

  /**
   * Tests the totalSize method for an empty directory.
   */
  @Test
  public void testTotalSizeForEmptyDirectory() {
    ExtDirectory root = new SimpleDirectory("root", List.of(), List.of());
    long expectedSize = root.size();
    Assert.assertEquals("Total size mismatch", expectedSize, root.totalSize());
  }

  /**
   * Tests if the filesystem throws an IllegalArgumentException when the capacity
   * is less than the total size of the directory's contents.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testFileSystemCapacityLessThanDirectorySize() {
    ContentFile file1 = new StringFile("file1.txt", "This is some content");
    ContentFile file2 = new StringFile("file2.txt", "Another file content");

    ExtDirectory root = new SimpleDirectory("root", List.of(), List.of(file1, file2));

    long totalSize = file1.size() + file2.size() + root.size();
    new ReadOnlyFileSystem(totalSize - 1, root);
  }

  /**
   * Tests if the getting the contents and then changing it
   */
  @Test
  public void testContentFileChangingContents() {
    ContentFile file1 = new StringFile("file1.txt", "This is some content");
    Assert.assertEquals("Locating Content", "This is some content", file1.contents());
    file1 = new StringFile("file2.txt", "Another file content");
    Assert.assertEquals("Locating Content", "Another file content", file1.contents());
  }




}
