import java.util.EnumMap;

/**
 * Node class keeps track of all meta data collected when processing a string.
 * 
 * @author Amy Irving
 * @version July 22, 2017
 *
 */
public class WordNode {

	/**
	 * The word associated with the node.
	 */
	private final String myStringName;

	/**
	 * EnumMap to keep track of number of tag occurrences found for this word.
	 */
	private final EnumMap<Tags, Integer> myTagCount;

	/**
	 * Node constructor.
	 * 
	 * @param myStringName
	 *            - The word associated with the node.
	 */
	public WordNode(final String myStringName) {
		this.myStringName = myStringName;

		/*
		 * EnumMap to keep track of number of tag occurrences found for this word.
		 */
		this.myTagCount = new EnumMap<Tags, Integer>(Tags.class);
		for (final Tags tag : Tags.getTags(false)) {
			myTagCount.put(tag, 0);
		}
	}

	/**
	 * Increments the number of occurrences in EnumMap for specified Tag.
	 * 
	 * @param theString -  the string value of the tag being incremented.
	 */
	public final void incrementTag(final String theString) {
		final Tags key = Tags.valueOf(theString);
		myTagCount.put(key, myTagCount.get(key).intValue() + 1);
	}

	/**
	 * Gets the number of occurenced of this tag for this word.
	 * 
	 * @param theTag - the tag we're getting the value from.
	 * @return the number of times this tag occures for this word.
	 */
	public final int getCount(final Tags theTag) {
		return myTagCount.get(theTag).intValue();
	}

	/**
	 * @return the String name for this node.
	 */
	public final String getMyStringName() {
		return myStringName;
	}

	@Override
	public String toString() {
		return "[WordNode: Name = " + myStringName + ", myTagCount = " + myTagCount + "]";
	}
	
	

}
