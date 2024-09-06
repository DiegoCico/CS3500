import cs3500.lab1.SortUtils;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;

public class ExamplarSorting {  

    @Test
    public void sortByAlphabeticalIgnoreCase() {
        Comparator<String> c = String::compareToIgnoreCase;
        List<String> lists = List.of("Banana", "apple", "grape", "Kiwi");
        List<String> sorted = SortUtils.sortStrings(lists, c);
        List<String> expected = List.of("apple", "Banana", "grape", "Kiwi");  
        Assert.assertEquals("Strings should be sorted alphabetically, ignoring case", expected, sorted);
    }

    @Test
    public void sortByWordCount() {
        Comparator<String> c = Comparator.comparingInt(s -> s.length());
        List<String> lists = List.of("cat", "dog and cat", "fish", "elephant and giraffe");
        List<String> sorted = SortUtils.sortStrings(lists, c);
        List<String> expected = List.of("cat", "fish", "dog and cat", "elephant and giraffe");  
        Assert.assertEquals("Strings should be sorted by word count", expected, sorted);
    }

    @Test
    public void sortByWordCountReversed() {
        Comparator<String> c = Comparator.comparingInt(s -> s.length());
        List<String> lists = List.of("cat", "dog and cat", "fish", "elephant and giraffe");
        List<String> sorted = SortUtils.sortStrings(lists, c.reversed());
        List<String> expected = List.of("elephant and giraffe", "dog and cat", "fish", "cat");  
        Assert.assertEquals("Strings should be sorted by word count", expected, sorted);
    }

    @Test
    public void sortMixedLengthStrings() {
        Comparator<String> c = Comparator.comparingInt(String::length);
        List<String> lists = List.of("a", "elephant", "cat", "dog", "rat");
        List<String> sorted = SortUtils.sortStrings(lists, c);
        List<String> expected = List.of("a", "cat", "dog", "rat", "elephant"); 
        Assert.assertEquals("Strings should be sorted by length", expected, sorted);
    }

    @Test
    public void sortCaseSensitive() {
        Comparator<String> c = Comparator.naturalOrder();
        List<String> lists = List.of("banana", "Apple", "grape", "Kiwi");
        List<String> sorted = SortUtils.sortStrings(lists, c);
        List<String> expected = List.of("Apple", "Kiwi", "banana", "grape"); 
        Assert.assertEquals("Strings should be sorted case-sensitively", expected, sorted);
    }

    @Test
    public void sortSameWord(){
        Comparator<String> c = Comparator.naturalOrder();
        List<String> lists = List.of("banana", "banana", "banana", "banana");
        List<String> sorted = SortUtils.sortStrings(lists, c);
        List<String> expected = List.of("banana", "banana", "banana", "banana"); 
        Assert.assertEquals("Strings should be sorted case-sensitively", expected, sorted);
    }

    @Test
    public void sortEmptyList(){
        Comparator<String> c = Comparator.naturalOrder();
        List<String> lists = List.of("", "", "", "");
        List<String> sorted = SortUtils.sortStrings(lists, c);
        List<String> expected = List.of("", "", "", ""); 
        Assert.assertEquals("Strings should be sorted case-sensitively", expected, sorted);
      }

    @Test
    public void sortWithEmptyStrings() {
        Comparator<String> c = Comparator.naturalOrder();
        List<String> lists = List.of("", "banana", "", "apple");
        List<String> sorted = SortUtils.sortStrings(lists, c);
        List<String> expected = List.of("", "", "apple", "banana");  
        Assert.assertEquals("Empty strings should be sorted first", expected, sorted);
    }

    @Test
    public void sortWithNullsLast() {
        List<String> lists = new ArrayList<>(Arrays.asList("banana", null, "apple", null, "grape"));
        List<String> sorted = SortUtils.sortStrings(lists, Comparator.nullsLast(Comparator.naturalOrder()));
        List<String> expected = new ArrayList<>(Arrays.asList("apple", "banana", "grape", null, null));
        Assert.assertEquals("Null values should appear last", expected, sorted);
    }

    @Test 
    public void sortWithNullsFirst() {
      List<String> lists = new ArrayList<>(Arrays.asList("banana",null,"grape",null,"apple"));
      List<String> sorted = SortUtils.sortStrings(lists, Comparator.nullsFirst(Comparator.comparingInt(string -> string.length())));
      List<String> expected = new ArrayList<>(Arrays.asList(null,null,"grape","apple","banana"));
      Assert.assertEquals("Empty lists should be sorted", expected, sorted);
    }

    @Test
    public void sortByLastCharacter() {
        Comparator<String> c = Comparator.comparingInt(s -> s.charAt(s.length() - 1));
        List<String> lists = List.of("apple", "banana", "grape", "kiwi");
        List<String> sorted = SortUtils.sortStrings(lists, c);
        List<String> expected = List.of("banana", "apple", "grape", "kiwi");  
        Assert.assertEquals("Strings should be sorted by last character", expected, sorted);
    }

    @Test
    public void sortEmpty(){
      List<String> lists = new ArrayList<>();
      List<String> sorted = SortUtils.sortStrings(lists, Comparator.naturalOrder());
      List<String> expected = List.of();
      Assert.assertEquals("Null values should appear last", expected, sorted);
    }

    @Test
    public void sortWithSpecialCharacters() {
      List<String> lists = List.of("apple", "@banana", "#grape", "kiwi");
      List<String> sorted = SortUtils.sortStrings(lists, Comparator.naturalOrder());
      List<String> expected = List.of("#grape", "@banana", "apple", "kiwi");
      Assert.assertEquals("Strings with special characters should be sorted before alphabetic strings", expected, sorted);
    }

    @Test
    public void sortIntegersAscending() {
      List<Integer> numbers = List.of(5, 2, 9, 1, 3);
      List<Integer> sorted = SortUtils.sort(numbers, Comparator.naturalOrder());
      List<Integer> expected = List.of(1, 2, 3, 5, 9);
      Assert.assertEquals("Integers should be sorted in ascending order", expected, sorted);
    }

    @Test
    public void sortIntegersDescending() {
      List<Integer> numbers = List.of(5, 2, 9, 1, 3);
      List<Integer> sorted = SortUtils.sort(numbers, Comparator.reverseOrder());
      List<Integer> expected = List.of(9,5,3,2,1);
      Assert.assertEquals("Integers should be sorted in ascending order", expected, sorted);
    }

    @Test 
    public void sortIntegersDescendingWithNull() {
      List<Integer> lists = new ArrayList<>(Arrays.asList(1,null,2,null,3));
      List<Integer> sorted = SortUtils.sort(lists, Comparator.nullsFirst(Comparator.naturalOrder()));
      List<Integer> expected = new ArrayList<>(Arrays.asList(null,null,1,2,3));
      Assert.assertEquals("Empty lists should be sorted", expected, sorted);
    }

    @Test
    public void sortWithOddFirst() {
      Comparator<Integer> c = Comparator.comparingInt((Integer n) -> {
          return (n % 2 == 0) ? 1 : 0; 
      }).thenComparingInt(n -> n); 
      List<Integer> numbers = List.of(5, 2, 9, 1, 3, 7);
      List<Integer> sorted = SortUtils.sort(numbers, c);
      List<Integer> expected = List.of(1, 3, 5, 7, 9, 2); 
      Assert.assertEquals("Integers should be sorted with odd numbers first", expected, sorted);
    }
}
