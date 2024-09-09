package cs3500.hw1.filesystem;

import org.junit.Assert;
import org.junit.Test;

import java.util.List;

import edu.neu.TestWeight;

public class ExamplarFileSystem {

  //Test that path works (operation on file system that takes in a File)
  @Test
  public void testPathInEmptyRootDirectory() {
    ExtDirectory root = new SimpleDirectory("root", List.of(), List.of());
    ReadOnlyFileSystem fileSystem =
        new ReadOnlyFileSystem(10 * Size.KILOBYTE.inBytes
            , root);
    Assert.assertEquals("Checking capacity of filesystem",
        10 * Size.KILOBYTE.inBytes,
	fileSystem.capacity());
  }

    
}
