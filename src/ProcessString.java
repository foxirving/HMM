import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Scanner;

public class ProcessString {

	private ProcessString() {
		// Private Constructor.
	}

	public static void generateWordMap() {

		/**
		 * Gets the HashMap and StringBuidler from Metadata, populates it, then
		 * sets the total vocabulary.
		 */
		Metadata.INSTANCE.setMyTotalVocabulary(processString(readFile(), Metadata.INSTANCE.getMyWordMap(),
				Metadata.INSTANCE.getMyTagOrder()));

	}

	/**
	 * Reads in a text file and returns it as a string.
	 * 
	 * @param theFileName
	 *            - file to become string.
	 * @return the string created from reading theFileName.
	 */
	public final static String readFile() {
		String str = "";
		try (Scanner sc = new Scanner(new File(Metadata.INSTANCE.getFileName()));) {
			// "\Z" means "end of string"
			str = sc.useDelimiter("\\Z").next().trim();
			// "\r" and "\n" are line breaks in linux and windows respectively.
			str = str.replaceAll("\\r", " ").replaceAll("\\n", " ");
			str = str.replaceAll("\\s+", " ");

		} catch (final FileNotFoundException e) {
			e.printStackTrace();
		}
		return str;
	}

	/**
	 * String format is Word/Tag. We separate each word in the sentence and then
	 * separate the words from the tag. Creates nodes and adds them to HashMap.
	 * 
	 * @param theString
	 *            - the String being processed.
	 */
	public final static int processString(final String theString, final HashMap<String, WordNode> theWordMap,
			final StringBuilder theTagOrder) {
		final String[] wordArray = theString.split("\\s+");
		int totalVocabulary = 0;

		for (final String word : wordArray) {
			final String[] splitWord = word.split(Metadata.TAG_DIVIDER);

			// Tests if such tag exists in Tags class.
			if (Tags.tagExists(splitWord[1])) {
				/*
				 * If the HashMap contains the word, we increment the tag found
				 * with it. Else we create a node, increment the tag found with
				 * it and add it to the HashMap.
				 */
				if (theWordMap.containsKey(splitWord[0])) {
					final WordNode node = theWordMap.get(splitWord[0]);
					node.incrementTag(splitWord[1]);
				} else {
					final WordNode node = new WordNode(splitWord[0]);
					node.incrementTag(splitWord[1]);
					theWordMap.put(splitWord[0], node);
					totalVocabulary++;
				}

				// Keep track of the order that each tag appears.
				theTagOrder.append(splitWord[1]);
				theTagOrder.append(Metadata.TAG_DIVIDER);
			} else {
				System.out.println("Tag does not exist in Tags Enum Class, this Word/Tag was not added to HashMap");
				System.out.println("Word: " + splitWord[0] + ", Tag: " + splitWord[1]);
			}
		}
		return totalVocabulary;

	}

}
