package edu.usfca.cs272;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * An index to store locations and the words found at those locations. Makes no
 * assumption about order or duplicates.
 *
 * @param <E> The type of location stored (e.g. String, Path, or URL)
 *
 * @author CS 272 Software Development (University of San Francisco)
 * @version Spring 2022
 */
public interface ForwardIndex<E> {
	/**
	 * Adds the location and word.
	 *
	 * @param location the location the word was found
	 * @param word the word found
	 */
	public void add(E location, String word);

	/**
	 * Adds the location and the provided words.
	 *
	 * @param location the location the words were found
	 * @param words the words found at that location
	 */
	public default void add(E location, List<String> words) {
		// TODO Add default add(E, List) implementation
		throw new UnsupportedOperationException("Not yet implemented.");
	}

	/**
	 * Adds the location and the provided words.
	 *
	 * @param location the location the words were found
	 * @param words the words found at that location
	 */
	public default void add(E location, String[] words) {
		add(location, Arrays.asList(words));
	}

	/**
	 * Adds all of the locations and words from the other index to this index.
	 *
	 * @param other the other index to add
	 */
	public default void addAll(ForwardIndex<E> other) {
		// TODO Add default addAll implementation
		throw new UnsupportedOperationException("Not yet implemented.");
	}

	/**
	 * Returns the number of locations stored in the index.
	 *
	 * @return 0 if the index is empty, otherwise the number of locations in the
	 *   index
	 */
	public int size();

	/**
	 * Returns the number of words stored for the given path.
	 *
	 * @param location the location to lookup
	 * @return 0 if the location is not in the index or has no words, otherwise
	 *   the number of words stored for that element
	 */
	public int size(E location);

	/**
	 * Determines whether the location is stored in the index.
	 *
	 * @param location the location to lookup
	 * @return {@true} if the location is stored in the index
	 */
	public boolean contains(E location);

	/**
	 * Determines whether the location is stored in the index and the word is
	 * stored for that location.
	 *
	 * @param location the location to lookup
	 * @param word the word in that location to lookup
	 * @return {@true} if the location and word is stored in the index
	 */
	public boolean contains(E location, String word);

	/**
	 * Returns an unmodifiable view of the locations stored in the index.
	 *
	 * @return an unmodifiable view of the locations stored in the index
	 * @see Collections#unmodifiableCollection(Collection)
	 */
	public Collection<E> get();

	/**
	 * Returns an unmodifiable view of the words stored in the index for the
	 * provided location, or an empty collection if the location is not in the
	 * index.
	 *
	 * @param location the location to lookup
	 * @return an unmodifiable view of the words stored for the location
	 * @see Collections#unmodifiableCollection(Collection)
	 */
	public Collection<String> get(E location);
}
