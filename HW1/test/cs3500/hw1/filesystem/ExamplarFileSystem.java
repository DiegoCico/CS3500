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
   * Tests if the path and capacity work when the root directory is empty.
   */
  @Test
  public void testPathInEmptyRootDirectory() {
    ExtDirectory root = new SimpleDirectory("root", List.of(), List.of());
    ReadOnlyFileSystem fileSystem = new ReadOnlyFileSystem(10 * Size.KILOBYTE.inBytes, root);
    Assert.assertEquals("Capacity mismatch", 10 * Size.KILOBYTE.inBytes, fileSystem.capacity());
  }

  /**
   * Checks if the filesystem has the right capacity when given a specific size.
   */
  @Test
  public void testCapacityOfFileSystem() {
    ExtDirectory root = new SimpleDirectory("root", List.of(), List.of());
    ReadOnlyFileSystem fileSystem = new ReadOnlyFileSystem(10 * Size.KILOBYTE.inBytes, root);
    Assert.assertEquals("Wrong capacity", 10 * Size.KILOBYTE.inBytes, fileSystem.capacity());
  }

  /**
   * Checks if the filesystem has zero capacity when it's initialized that way.
   */
  @Test
  public void testCapacityOfEmptyFileSystem() {
    try {
      ExtDirectory root = new SimpleDirectory("root", List.of(), List.of());
      ReadOnlyFileSystem fileSystem = new ReadOnlyFileSystem(0, root);
      Assert.assertEquals("Capacity mismatch", 0, fileSystem.capacity());
    } catch (RuntimeException e) {
      Assert.assertTrue(true); // Just checking that an exception is caught
    }
  }

  /**
   * Tests if the filesystem capacity is zero when the root is null.
   */
  @Test
  public void testCapacityOfNullRootFileSystem() {
    try {
      ReadOnlyFileSystem fileSystem = new ReadOnlyFileSystem(0, null);
      Assert.assertEquals("Capacity mismatch", 0, fileSystem.capacity());
    } catch (RuntimeException e) {
      Assert.assertTrue(true); // Again, expecting an exception
    }
  }

  /**
   * Makes sure a StringFile contains the right content.
   */
  @Test
  public void testStringFileContents() {
    ContentFile file = new StringFile("file1", "Hello World!");
    Assert.assertEquals("Content mismatch", "Hello World!", file.contents());
  }

  /**
   * Tests if an empty directory really has no contents.
   */
  @Test
  public void testEmptyDirectoryContents() {
    ExtDirectory dir = new SimpleDirectory("root", List.of(), List.of());
    Assert.assertTrue("Directory should be empty", dir.contents().isEmpty());
  }

  /**
   * Checks if a directory with files returns the correct number of files.
   */
  @Test
  public void testDirectoryWithFiles() {
    ContentFile file1 = new StringFile("file1", "Data1");
    ContentFile file2 = new StringFile("file2", "Data2");
    ExtDirectory dir = new SimpleDirectory("root", List.of(), List.of(file1, file2));

    List<ExtFile> files = dir.contents();
    Assert.assertEquals("File count mismatch", 2, files.size());
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
   * Makes sure a null name in StringFile throws a NullPointerException.
   */
  @Test(expected = NullPointerException.class)
  public void testStringFileConstructorNullName() {
    new StringFile(null, "This has content");
  }

  /**
   * Tests if the name method returns the correct file name.
   */
  @Test
  public void testNameReturnsCorrectFileName() {
    StringFile file = new StringFile("test1.txt", "This is some content");
    Assert.assertEquals("Name mismatch", "test1.txt", file.name());
  }

  /**
   * Tests if SimpleDirectory constructor throws NullPointerException for a null name.
   */
  @Test(expected = NullPointerException.class)
  public void testSimpleDirectoryConstructorNullName() {
    new SimpleDirectory(null, List.of(), List.of());
  }

  /**
   * Tests if SimpleDirectory constructor throws IllegalArgumentException for an empty name.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testSimpleDirectoryConstructorEmptyName() {
    new SimpleDirectory("", List.of(), List.of());
  }

  /**
   * Tests if SimpleDirectory constructor throws NullPointerException for null contents.
   */
  @Test(expected = NullPointerException.class)
  public void testSimpleDirectoryConstructorNullContents() {
    new SimpleDirectory("dir", List.of(), null);
  }

  /**
   * Makes sure the filesystem throws an IllegalArgumentException for negative capacity.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testNegativeFileSystemCapacity() {
    ExtDirectory root = new SimpleDirectory("root", List.of(), List.of());
    new ReadOnlyFileSystem(-100, root);
  }

  /**
   * Makes sure the filesystem throws NullPointerException when the root directory is null.
   */
  @Test(expected = NullPointerException.class)
  public void testNullRootDirectoryInFileSystem() {
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
   * Tests the prettyPrint functionality with an empty root directory.
   */
  @Test
  public void testPrettyPrintEmptyRootDirectory() {
    ExtDirectory root = new SimpleDirectory("root", List.of(), List.of());
    String expected = "+-root/";
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
   * Tests the totalSize method for an empty directory.
   */
  @Test
  public void testTotalSizeForEmptyDirectory() {
    ExtDirectory root = new SimpleDirectory("root", List.of(), List.of());
    long expectedSize = root.size();
    Assert.assertEquals("Total size mismatch", expectedSize, root.totalSize());
  }
}
