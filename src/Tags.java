import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;

/**
 * This class keeps track of all known tags to be used when generating emission
 * table, transition table, and HMM.
 * 
 * @author Amy Irving
 * @version July 22, 2017
 *
 */
public enum Tags {

	/**
	 * Tags found in a file and their string value.
	 */
	START("<START>"), N("N"), V("V"), CONJ("CONJ"), PRO("PRO");

	/**
	 * String value for Tag enum.
	 */
	private final String myString;

	/**
	 * Tag constructor
	 * 
	 * @param theString
	 *            - name of the tag.
	 */
	Tags(final String theString) {
		this.myString = theString;
	}

	/**
	 * Returns a list of tags for this class. if includeStart is true, we return
	 * all tags including start. else, we return all tags not including start.
	 * 
	 * @param includeStart
	 *            - return a list with Start tag in it.
	 * @return a list of tags.
	 */
	public static List<Tags> getTags(final boolean includeStart) {
		if (includeStart) {
			return new ArrayList<Tags>(EnumSet.allOf(Tags.class));
		}
		final List<Tags> list = new ArrayList<Tags>(EnumSet.allOf(Tags.class));
		list.remove(START);
		return list;
	}

	/**
	 * Returns the index of the tag. if includeStart is true, we return index of
	 * tag from a list that includes start. else, we return index from a list
	 * that doesn't include start.
	 * 
	 * @param theTag
	 *            - the tag we're searching for.
	 * @param includeStart
	 *            - whether or not we search from list that includes start tag.
	 * @return index of tag.
	 */
	public static int getTagIndex(final Tags theTag, final boolean includeStart) {
		return getTags(includeStart).indexOf(theTag);
	}

	/**
	 * Checks if specific tag exists in Tags class.
	 * 
	 * @param theString
	 *            - the tag we're searching for.
	 * @return true if the tag exists, false otherwise.
	 */
	public static boolean tagExists(final String theString) {
		for (final Tags tag : Tags.getTags(false)) {
			if (tag == Tags.valueOf(theString)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * @return String name of the Tag Enum.
	 */
	public String getMyString() {
		return myString;
	}

}
