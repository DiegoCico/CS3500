package cs3500.hw1.filesystem;

/**
 * Representation of different size values in bytes for a file system.
 * Useful to have to make size related code readable and prevent
 * unit errors caused by forgetting the value type.
 *
 * @implNote This is a value-class implemented in an enumeration.
 *           Value-class: A class created to hold unchanging values.
 *           Enumerations: A special class used for classes with limited number of
 *               values (like currency values).
 *               See the Java Safari lecture notes for more details on enumerations.
 */
public enum Size {
  BYTE(1),
  KILOBYTE(1024),
  MEGABYTE(1024 * 1024),
  GIGABYTE(1024 * 1024 * 1024);

  //value in bytes
  //@implNote: It is okay to access this field directly in code
  // because this class exists to represent values and this field
  // cannot be modified.
  // This was done by design.
  public final long inBytes;

  Size(long bytes) {
    this.inBytes = bytes;
  }
}
