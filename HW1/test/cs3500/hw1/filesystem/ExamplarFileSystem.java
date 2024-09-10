package cs3500.hw1.filesystem;

import org.junit.Assert;
import org.junit.Test;

import java.util.List;

import edu.neu.TestWeight;

public class ExamplarFileSystem {

  /**
   * Test if the path works when the root directory is empty.
   */
  @Test
  public void testPathInEmptyRootDirectory() {
    ExtDirectory root = new SimpleDirectory("root", List.of(), List.of());
    ReadOnlyFileSystem fileSystem =
            new ReadOnlyFileSystem(10 * Size.KILOBYTE.inBytes, root);
    Assert.assertEquals("Checking capacity of filesystem",
            10 * Size.KILOBYTE.inBytes, fileSystem.capacity());
  }

  /**
   * Test the capacity of the filesystem when initialized with a specific size.
   */
  @Test
  public void testCapacityOfFileSystem() {
    ExtDirectory root = new SimpleDirectory("root", List.of(), List.of());
    ReadOnlyFileSystem fileSystem = new ReadOnlyFileSystem(10 * Size.KILOBYTE.inBytes, root);

    Assert.assertEquals("Capacity of filesystem should be 10 KB", 10 * Size.KILOBYTE.inBytes, fileSystem.capacity());
  }

  /**
   * Test the capacity of the filesystem when set to zero.
   */
  @Test
  public void testCapacityOfEmptyFileSystem() {
    try {
      ExtDirectory root = new SimpleDirectory("root", List.of(), List.of());
      ReadOnlyFileSystem fileSystem = new ReadOnlyFileSystem(0, root);
      Assert.assertEquals("Capacity of filesystem should be 0 KB", 0, fileSystem.capacity());
    } catch (RuntimeException e) {
      Assert.assertTrue(true);
    }
  }

  /**
   * Test if StringFile contains the expected content.
   */
  @Test
  public void testStringFileContents() {
    ContentFile file = new StringFile("file1", "Hello World!");

    Assert.assertEquals("File contents should be 'Hello World!'", "Hello World!", file.contents());
  }

  /**
   * Test if an empty directory returns no contents.
   */
  @Test
  public void testEmptyDirectoryContents() {
    ExtDirectory dir = new SimpleDirectory("root", List.of(), List.of());

    Assert.assertTrue("Directory contents should be empty", dir.contents().isEmpty());
  }

  /**
   * Test if a directory with files returns the correct number of files.
   */
  @Test
  public void testDirectoryWithFiles() {
    ContentFile file1 = new StringFile("file1", "Data1");
    ContentFile file2 = new StringFile("file2", "Data2");
    ExtDirectory dir = new SimpleDirectory("root", List.of(), List.of(file1, file2));

    List<ExtFile> files = dir.contents();

    Assert.assertEquals("Directory should contain 2 files", 2, files.size());
  }

  /**
   * Test if a search for a phrase in a StringFile returns true when found.
   */
  @Test
  public void testSearchInStringFile() {
    ContentFile file = new StringFile("file1", "Searchable content");

    Assert.assertTrue("Phrase 'Search' should be found in the file", file.search("Search"));
  }

  /**
   * Test if a search for a phrase not in the file returns false.
   */
  @Test
  public void testSearchInStringFileFailure() {
    ContentFile file = new StringFile("file1", "This is some content");

    Assert.assertFalse("Phrase 'NotInFile' should not be found in the file", file.search("NotInFile"));
  }

  /**
   * Test if a search in the ReadOnlyFileSystem works correctly.
   */
  @Test
  public void testFileSystemSearch() {
    ContentFile file = new StringFile("file1", "Find me");
    ExtDirectory root = new SimpleDirectory("root", List.of(), List.of(file));
    ReadOnlyFileSystem fileSystem = new ReadOnlyFileSystem(100 * Size.KILOBYTE.inBytes, root);

    Assert.assertTrue("Phrase 'Find' should be found in the system", fileSystem.search("Find"));
  }

  /**
   * Test if an empty file name throws an IllegalArgumentException.
   * @throws IllegalArgumentException if the file name is empty.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testStringFileConstructorEmptyName() {
    new StringFile("", "content");
  }

  /**
   * Test if a null content in StringFile constructor throws a NullPointerException.
   * @throws NullPointerException when contents are null
   */
  @Test(expected = NullPointerException.class)
  public void testStringFileConstructorNullContent() {
    new StringFile("file1", null);
  }

  /**
   * Test if SimpleDirectory constructor throws NullPointerException when given a null name.
   * @throws NullPointerException when given null name
   */
  @Test(expected = NullPointerException.class)
  public void testSimpleDirectoryConstructorNullName() {
    new SimpleDirectory(null, List.of(), List.of());
  }

  /**
   * Test if SimpleDirectory constructor throws IllegalArgumentException when given an empty name.
   * @throws IllegalArgumentException when given empty name
   */
  @Test(expected = IllegalArgumentException.class)
  public void testSimpleDirectoryConstructorEmptyName() {
    new SimpleDirectory("", List.of(), List.of());
  }

  /**
   * Test if SimpleDirectory constructor throws NullPointerException when given null contents.
   * @throws NullPointerException when given null contents
   */
  @Test(expected = NullPointerException.class)
  public void testSimpleDirectoryConstructorNullContents() {
    new SimpleDirectory("dir", List.of(), null);
  }

  /**
   * Test if SimpleDirectory constructor throws NullPointerException when all parameters are null.
   * @throws NullPointerException when all parameters are null
   */
  @Test(expected = NullPointerException.class)
  public void testSimpleDirectoryConstructorAllNull() {
    new SimpleDirectory(null, null, null);
  }

  /**
   * Test if negative filesystem capacity throws IllegalArgumentException.
   *
   * @throws IllegalArgumentException if the filesystem capacity is negative.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testNegativeFileSystemCapacity() {
    ExtDirectory root = new SimpleDirectory("root", List.of(), List.of());
    new ReadOnlyFileSystem(-100, root);
  }

  /**
   * Test if null root directory in filesystem constructor throws NullPointerException.
   *
   * @throws NullPointerException if the root directory is null.
   */
  @Test(expected = NullPointerException.class)
  public void testNullRootDirectoryInFileSystem() {
    new ReadOnlyFileSystem(100 * Size.KILOBYTE.inBytes, null);
  }

  /**
   * Test if search is case-sensitive.
   */
  @Test
  public void testSearchCaseSensitivity() {
    ContentFile file = new StringFile("file1", "Hello World");
    Assert.assertTrue("Phrase 'Hello' should be found", file.search("Hello"));
    Assert.assertFalse("Phrase 'hello' should not be found (case-sensitive)", file.search("hello"));
  }

  /**
   * Test if searching for an empty string returns true.
   */
  @Test
  public void testSearchEmptyPhrase() {
    ContentFile file = new StringFile("file1", "content");
    Assert.assertTrue("Empty string should be found in the file", file.search(""));
  }

  /**
   * Tests the prettyPrint functionality of a directory with no files.
   */
  @Test
  public void testPrettyPrintEmptyDirectory() {
    ExtDirectory root = new SimpleDirectory("root", List.of(), List.of());

    String expected = "+-root/";
    Assert.assertEquals("Pretty print should show the directory with no contents", expected, root.prettyPrint());
  }

  /**
   * Tests the prettyPrint functionality of a directory with 2 files.
   */
  @Test
  public void testPrettyPrintWithFiles() {
    ContentFile file1 = new StringFile("a.txt", "Content 1");
    ContentFile file2 = new StringFile("b.txt", "Content 2");
    ExtDirectory root = new SimpleDirectory("root", List.of(), List.of(file1, file2));

    String expected = "+-root/\n" +
            "| +-a.txt\n" +
            "| +-b.txt";

    Assert.assertEquals("Pretty print should show the files in the root directory", expected, root.prettyPrint());
  }

  /**
   * Tests the prettyPrint functionality of a nested directories with files.
   */
  @Test
  public void testPrettyPrintNestedDirectories() {
    ContentFile file1 = new StringFile("file1.txt", "Content 1");
    ContentFile file2 = new StringFile("file2.txt", "Content 2");
    ExtDirectory subDir = new SimpleDirectory("lib", List.of(), List.of(file2));
    ExtDirectory root = new SimpleDirectory("root", List.of(subDir), List.of(file1));

    String expected = "+-root/\n" +
            "| +-file1.txt\n" +
            "| +-lib/\n" +
            "| | +-file2.txt";
    Assert.assertEquals("Pretty print should show nested directories with proper indentation", expected, root.prettyPrint());
  }

}
