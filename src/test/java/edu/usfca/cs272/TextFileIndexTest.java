package edu.usfca.cs272;

import java.io.IOException;
import java.lang.reflect.Method;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer.MethodName;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

/**
 * Tests the {@link TextFileIndex} class.
 *
 * @see TextFileIndex
 * @see ForwardIndex
 *
 * @author CS 272 Software Development (University of San Francisco)
 * @version Spring 2022
 */
@TestMethodOrder(MethodName.class)
public class TextFileIndexTest {
	/** Sample text file. */
	private static final Path animals = Path.of("src", "test", "resources", "animals.text");

	/** Sample text file. */
	private static final Path sentences = Path.of("src", "test", "resources", "sentences.md");

	/** Sample empty file. */
	private static final Path empty = Path.of("empty.txt");

	/** Sample simple file. */
	private static final Path hello = Path.of("hello.txt");

	/**
	 * Creates an empty {@link TextFileIndex} and returns it casted as a
	 * {@link ForwardIndex}. If this method does not compile, then the
	 * {@link TextFileIndex} class is not properly implementing the
	 * {@link ForwardIndex} interface.
	 *
	 * @return new empty text file index
	 */
	public static ForwardIndex<Path> createEmpty() {
		return new TextFileIndex();
	}

	/**
	 * Tests of an index with a single initial location and word.
	 */
	@Nested
	@TestMethodOrder(OrderAnnotation.class)
	public class A_SimpleAddTests {
		/** Placeholder for index object being tested. */
		private ForwardIndex<Path> index;

		/**
		 * Initializes an empty text file index.
		 */
		@BeforeEach
		public void initializeIndex() {
			index = createEmpty();
			index.add(hello, "hello");
		}

		/**
		 * Tests the toString() implementation.
		 */
		@Order(1)
		@Test
		public void testStringHello() {
			Assertions.assertTrue(index.toString().contains("hello"),
					"Override toString() with a useful implementation!");
		}

		/**
		 * Test number of locations.
		 */
		@Test
		@Order(2)
		public void testNumPaths() {
			Assertions.assertEquals(1, index.size(), index.toString());
		}

		/**
		 * Tests number of positions for element.
		 */
		@Test
		@Order(3)
		public void testNumWords() {
			Assertions.assertEquals(1, index.size(hello), index.toString());
		}

		/**
		 * Tests location exists in index.
		 */
		@Test
		@Order(4)
		public void testContainsPath() {
			Assertions.assertTrue(index.contains(hello), index.toString());
		}

		/**
		 * Tests word does NOT exist for a location.
		 */
		@Test
		@Order(5)
		public void testContainsWordFalse() {
			Assertions.assertFalse(index.contains(hello, "world"), index.toString());
		}

		/**
		 * Tests word DOES exist for element.
		 */
		@Test
		@Order(6)
		public void testContainsWordTrue() {
			Assertions.assertTrue(index.contains(hello, "hello"), index.toString());
		}

		/**
		 * Tests location is fetched properly.
		 */
		@Test
		@Order(7)
		public void testGetElements() {
			Assertions.assertTrue(index.get().contains(hello), index.toString());
		}

		/**
		 * Tests word is fetched properly.
		 */
		@Test
		@Order(8)
		public void testGetPositions() {
			Assertions.assertTrue(index.get(hello).contains("hello"), index.toString());
		}

		/**
		 * Tests size of elements fetched.
		 */
		@Test
		@Order(9)
		public void testGetPathsSize() {
			Assertions.assertEquals(1, index.get().size(), index.toString());
		}

		/**
		 * Tests size of positions fetched.
		 */
		@Test
		@Order(10)
		public void testGetWordsSize() {
			Assertions.assertEquals(1, index.get(hello).size(), index.toString());
		}

		/**
		 * Tests adding same location/word pair twice has no impact.
		 */
		@Test
		@Order(11)
		public void testDoubleAdd() {
			index.add(hello, "hello");
			Assertions.assertEquals(1, index.size(hello), index.toString());
		}

		/**
		 * Tests adding new word for a location.
		 */
		@Test
		@Order(12)
		public void testAddNewWord() {
			index.add(hello, "world");
			Assertions.assertEquals(2, index.size(hello), index.toString());
		}

		/**
		 * Tests adding new location.
		 */
		@Test
		@Order(13)
		public void testAddNewPath() {
			index.add(Path.of("world.txt"), "world");
			Assertions.assertEquals(2, index.size(), index.toString());
		}
	}

	/**
	 * Tests empty index.
	 */
	@Nested
	@TestMethodOrder(OrderAnnotation.class)
	public class B_EmptyTests {
		/** Placeholder for index object being tested. */
		private ForwardIndex<Path> index;

		/**
		 * Initializes an empty text file index.
		 */
		@BeforeEach
		public void initializeIndex() {
			index = createEmpty();
		}

		/**
		 * Tests the toString() implementation.
		 */
		@Order(1)
		@Test
		public void testStringEmpty() {
			Assertions.assertFalse(index.toString().contains("TextFileIndex@"),
					"Override toString() with a useful implementation!");
		}

		/**
		 * Tests that there are no locations.
		 */
		@Test
		@Order(2)
		public void testNumPaths() {
			Assertions.assertEquals(0, index.size(), index.toString());
		}

		/**
		 * Tests that there are no words for a location not in our index.
		 */
		@Test
		@Order(3)
		public void testNumWords() {
			Assertions.assertEquals(0, index.size(empty), index.toString());
		}

		/**
		 * Tests that a location does not exist as expected.
		 */
		@Test
		@Order(4)
		public void testContainsPath() {
			Assertions.assertFalse(index.contains(empty), index.toString());
		}

		/**
		 * Tests that a word does not exist as expected.
		 */
		@Test
		@Order(5)
		public void testContainsWord() {
			Assertions.assertFalse(index.contains(empty, "empty"), index.toString());
		}

		/**
		 * Tests that no locations are fetched as expected.
		 */
		@Test
		@Order(6)
		public void testGetPaths() {
			Assertions.assertTrue(index.get().isEmpty(), index.toString());
		}

		/**
		 * Tests that no words are fetched as expected.
		 */
		@Test
		@Order(7)
		public void testGetWords() {
			Assertions.assertTrue(index.get(empty).isEmpty(), index.toString());
		}
	}

	/**
	 * Tests the addAll implementations.
	 */
	@Nested
	@TestMethodOrder(OrderAnnotation.class)
	public class C_AddAllTests {
		/** The target index. */
		private ForwardIndex<Path> index;

		/** The other index. */
		private ForwardIndex<Path> other;

		/**
		 * Initializes the indexes.
		 */
		@BeforeEach
		public void initializeIndex() {
			index = createEmpty();
			other = createEmpty();
		}

		/**
		 * Tests adding array of one word.
		 */
		@Test
		@Order(1)
		public void testAddAllFalse() {
			index.add(hello, new String[] { "hello" });
			Assertions.assertEquals(1, index.size(hello), index.toString());
		}

		/**
		 * Tests adding array with two words.
		 */
		@Test
		@Order(2)
		public void testAddAllTrue() {
			index.add(hello, new String[] { "hello", "world" });
			Assertions.assertEquals(2, index.size(hello), index.toString());
		}

		/**
		 * Tests adding array with two words and one duplicate.
		 */
		@Test
		@Order(3)
		public void testAddAllDuplicate() {
			index.add(hello, new String[] { "hello", "world", "hello" });
			Assertions.assertEquals(2, index.size(hello), index.toString());
		}

		/**
		 * Tests adding array with mixed adds.
		 */
		@Test
		@Order(4)
		public void testMixedAdds() {
			index.add(hello, "hello");
			index.add(hello, new String[] { "hello", "world" });
			Assertions.assertEquals(2, index.size(hello), index.toString());
		}

		/**
		 * Tests adding an empty index to an empty index.
		 */
		@Test
		@Order(5)
		public void testEmptyEmpty() {
			index.addAll(other);
			Assertions.assertEquals(index.size(), 0, index.toString());
			Assertions.assertEquals(other.size(), 0, other.toString());
		}

		/**
		 * Tests adding an empty index to a simple index.
		 */
		@Test
		@Order(6)
		public void testSimpleEmpty() {
			index.add(hello, "hello");
			index.addAll(other);
			Assertions.assertEquals(index.get(hello).size(), 1, index.toString());
			Assertions.assertEquals(other.get(hello).size(), 0, other.toString());
		}

		/**
		 * Tests adding a simple index to an empty index.
		 */
		@Test
		@Order(7)
		public void testEmptySimple() {
			other.add(hello, "hello");
			index.addAll(other);
			Assertions.assertEquals(index.get(hello).size(), 1, index.toString());
			Assertions.assertEquals(other.get(hello).size(), 1, other.toString());
		}

		/**
		 * Tests adding a simple index to a simple index.
		 */
		@Test
		@Order(8)
		public void testSimpleSimpleDifferent() {
			index.add(hello, "hello");
			other.add(hello, "world");
			index.addAll(other);
			Assertions.assertEquals(index.get(hello).size(), 2, index.toString());
			Assertions.assertEquals(other.get(hello).size(), 1, other.toString());
		}

		/**
		 * Tests adding a simple index to a simple index.
		 */
		@Test
		@Order(9)
		public void testSimpleSimpleSame() {
			index.add(hello, "hello");
			other.add(hello, "hello");
			index.addAll(other);
			Assertions.assertEquals(index.get(hello).size(), 1, index.toString());
			Assertions.assertEquals(other.get(hello).size(), 1, other.toString());
		}

		/**
		 * Tests adding a simple index to a simple index.
		 */
		@Test
		@Order(10)
		public void testComplex() {
			index.add(Path.of("hello.txt"), List.of("hello", "hola"));
			index.add(Path.of("letters.txt"), List.of("a", "b", "c", "c"));

			other.add(Path.of("letters.txt"), List.of("b", "e"));
			other.add(Path.of("planets.txt"), List.of("earth", "mars"));

			index.addAll(other);

			Assertions.assertEquals(index.size(), 3, index.toString());
			Assertions.assertEquals(index.get(Path.of("letters.txt")).size(), 4, index.toString());
			Assertions.assertEquals(other.get(Path.of("letters.txt")).size(), 2, other.toString());
		}
	}

	/**
	 * Tests of an index with a single initial location and word.
	 */
	@Nested
	@TestMethodOrder(OrderAnnotation.class)
	public class D_ModificationTests {
		/** Placeholder for index object being tested. */
		private ForwardIndex<Path> index;

		/**
		 * Initializes an empty text file index.
		 */
		@BeforeEach
		public void initializeIndex() {
			index = createEmpty();
			index.add(hello, "hello");
		}

		/**
		 * Tests that attempts to modify paths in index fails.
		 */
		@Test
		@Order(1)
		public void testPathsClear() {
			Collection<Path> elements = index.get();

			try {
				elements.clear();
			}
			catch (Exception e) {
				System.err.println("Unable to modify.");
			}

			Assertions.assertTrue(index.contains(hello), index.toString());
		}

		/**
		 * Tests that attempts to modify paths in index fails.
		 */
		@Test
		@Order(2)
		public void testPathsAdd() {
			Collection<Path> elements = index.get();

			try {
				elements.add(empty);
			}
			catch (Exception e) {
				System.err.println("Unable to modify.");
			}

			Assertions.assertFalse(index.contains(empty), index.toString());
		}

		/**
		 * Tests that attempts to modify words in index fails.
		 */
		@Test
		@Order(3)
		public void testPositionsClear() {
			Collection<String> elements = index.get(hello);

			try {
				elements.clear();
			}
			catch (Exception e) {
				System.err.println("Unable to modify.");
			}

			Assertions.assertTrue(index.contains(hello, "hello"));
		}

		/**
		 * Tests that attempts to modify words in index fails.
		 */
		@Test
		@Order(4)
		public void testPositionsAdd() {
			Collection<String> elements = index.get(hello);

			try {
				elements.add("world");
			}
			catch (Exception e) {
				System.err.println("Unable to modify.");
			}

			Assertions.assertFalse(index.contains(hello, "world"));
		}
	}

	/**
	 * Tests real text files.
	 */
	@Nested
	@TestMethodOrder(OrderAnnotation.class)
	public class E_RealIndexTests {
		/** Placeholder for index object being tested. */
		private ForwardIndex<Path> index;

		/**
		 * Initializes an empty text file index.
		 */
		@BeforeEach
		public void initializeIndex() {
			index = createEmpty();

			index.add(animals, getWords(animals));
			index.add(sentences, getWords(sentences));
		}

		/**
		 * Testing whether index was created properly.
		 */
		@Order(1)
		@Test
		public void testAnimalPaths() {
			Assertions.assertTrue(index.contains(animals), index.toString());
		}

		/**
		 * Testing whether index was created properly.
		 */
		@Order(2)
		@Test
		public void testSentencesPaths() {
			Assertions.assertTrue(index.contains(sentences), index.toString());
		}

		/**
		 * Testing whether index was created properly.
		 */
		@Order(3)
		@Test
		public void testAnimals() {
			Assertions.assertEquals(8, index.size(animals), index.toString());
		}

		/**
		 * Testing whether index was created properly.
		 */
		@Order(4)
		@Test
		public void testSentences() {
			Assertions.assertEquals(41, index.size(sentences), index.toString());
		}

		/**
		 * Testing whether index was created properly.
		 */
		@Order(5)
		@Test
		public void testPaths() {
			Set<Path> expected = Set.of(animals, sentences);
			Assertions.assertTrue(index.get().containsAll(expected),
					index.toString());
		}

		/**
		 * Testing whether index was created properly.
		 */
		@Order(6)
		@Test
		public void testWords() {
			Set<String> expected = Set.of("okapi", "mongoose", "loris", "axolotl",
					"narwhal", "platypus", "echidna", "tarsier");

			Assertions.assertTrue(index.get(animals).containsAll(expected),
					index.toString());
		}
	}

	/**
	 * Tests of an index with a single initial location and word.
	 */
	@Nested
	@TestMethodOrder(OrderAnnotation.class)
	public class F_ApproachTests {
		/**
		 * Testing whether implemented default methods in the interface only.
		 */
		@Order(1)
		@Test
		public void testAddList() {
			String debug = "\nDo not override default methods in TextFileIndex!" +
					" For this homework, only implement those in the interface.\n";

			Method[] methods = TextFileIndex.class.getMethods();

			long found = Arrays.stream(methods)
					.filter(m -> m.getDeclaringClass().equals(TextFileIndex.class))
					.filter(m -> m.getName().startsWith("add"))
					.filter(m -> m.toString().contains("List"))
					.count();

			Assertions.assertTrue(found == 0, debug);
		}

		/**
		 * Testing whether implemented default methods in the interface only.
		 */
		@Order(2)
		@Test
		public void testAddArray() {
			String debug = "\nDo not override default methods in TextFileIndex!" +
					" For this homework, only implement those in the interface.\n";

			Method[] methods = TextFileIndex.class.getMethods();

			long found = Arrays.stream(methods)
					.filter(m -> m.getDeclaringClass().equals(TextFileIndex.class))
					.filter(m -> m.getName().startsWith("add"))
					.filter(m -> m.toString().contains("String[]"))
					.count();

			Assertions.assertTrue(found == 0, debug);
		}

		/**
		 * Testing whether implemented default methods in the interface only.
		 */
		@Order(3)
		@Test
		public void testAddForwardIndex() {
			String debug = "\nDo not override default methods in TextFileIndex!" +
					" For this homework, only implement those in the interface.\n";

			Method[] methods = TextFileIndex.class.getMethods();

			long found = Arrays.stream(methods)
					.filter(m -> m.getDeclaringClass().equals(TextFileIndex.class))
					.filter(m -> m.getName().startsWith("add"))
					.filter(m -> m.toString().contains("ForwardIndex"))
					.count();

			Assertions.assertTrue(found == 0, debug);
		}
	}

	/**
	 * Helper method to quickly read in a small text file and return words.
	 *
	 * @param path the path
	 * @return the words
	 */
	private static String[] getWords(Path path) {
		try {
			return Files.readString(path, StandardCharsets.UTF_8).toLowerCase().split("\\W+");
		}
		catch (IOException e) {
			Assertions.fail(e);
			return null;
		}
	}
}
