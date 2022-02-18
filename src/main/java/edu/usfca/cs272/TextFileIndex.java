package edu.usfca.cs272;

import java.nio.file.Path;
import java.util.List;

/**
 * A special type of {@link ForwardIndex} that indexes the UNIQUE words that
 * were found in a text file (represented by {@link Path} objects).
 *
 * @author CS 272 Software Development (University of San Francisco)
 * @version Spring 2022
 */
public class TextFileIndex {
	// TODO Implement the ForwardIndex interface for Path objects
	// TODO Modify this class as necessary

	/**
	 * Demonstrates this class. If this method does not compile, then the
	 * {@link TextFileIndex} class is not properly implementing the
	 * {@link ForwardIndex} interface.
	 *
	 * @param args unused
	 */
	public static void main(String[] args) {
		ForwardIndex<Path> index = new TextFileIndex();

		index.add(Path.of("hello.txt"), List.of("hello", "hola"));
		index.add(Path.of("letters.txt"), List.of("a", "b", "c", "c"));
		index.add(Path.of("letters.txt"), List.of("b", "e"));
		index.add(Path.of("planets.txt"), List.of("earth", "mars"));

		System.out.println(index);
	}
}
