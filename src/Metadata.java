import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public enum Metadata {

	INSTANCE;

	/**
	 * Standardize syntax for separating tags in a string.
	 */
	public static final String TAG_DIVIDER = "/";

	/**
	 * Standardize syntax for separating tags in a string.
	 */
	public static final String OUT_FILE_NAME = "ReadMe.txt";

	private String fileName = "";
	private String stringToDecode = "";

	/**
	 * Smoothing factor when calculating probabilities.
	 */
	private double smoothing = 0.1;

	/**
	 * Total number of unique words found in file.
	 */
	private int myTotalVocabulary = 0;

	/**
	 * Map of all words contained in file, Key being the string word, Value
	 * being Node with meta data.
	 */
	private HashMap<String, WordNode> myWordMap;

	/**
	 * String keeping track of the order in which each tag is found.
	 */
	private StringBuilder myTagOrder;

	/**
	 * The HashMap containing all the probabilities for words|tags. Key is the
	 * word, the list is a list of tags taken from Tags class. The order that
	 * each tag is listed in Tags class is the same order of the probabilities
	 * in each list.
	 */
	private HashMap<String, List<Double>> myEmissionTable;

	/**
	 * The HashMap containing all the probabilities for tag(i)|tag(i-1). Key is
	 * the current tag(i), the list is a list of tags taken from Tags class. The
	 * order that each tag is listed in Tags class is the same order of the
	 * probabilities in each list including start tag.
	 */
	public HashMap<Tags, List<Double>> myTransitionTable;

	public HashMap<String, ProbabilityNode> myGraph;

	private String myPrevWord = "";

	/**
	 * The probability of the P(<Start>).
	 */
	private double myStartProbability;

	private String myDecodedString = "";

	Metadata() {
		initialize();
	}

	private void initialize() {
		myWordMap = new HashMap<String, WordNode>();
		myEmissionTable = new HashMap<String, List<Double>>();
		myTransitionTable = new HashMap<Tags, List<Double>>();
		myGraph = new HashMap<String, ProbabilityNode>();

		myTagOrder = new StringBuilder();
		myTagOrder.append(Tags.START.getMyString());
		myTagOrder.append(TAG_DIVIDER);

		myStartProbability = 1.0;
	}

	/**
	 * Finds the number of occurrences for a given tag.
	 * 
	 * @param theTag
	 *            - the tag we're finding the count for.
	 * @param theTagOrder
	 *            - The String containing the orders found for each tag.
	 * @return the number of occurrences of theTag.
	 */
	public final int getTagCount(final Tags theTag) {
		int count = 0;
		final Pattern pattern = Pattern.compile(TAG_DIVIDER + theTag.name() + TAG_DIVIDER);
		final Matcher matcher = pattern.matcher(myTagOrder.toString());
		while (matcher.find()) {
			count++;
		}
		return count;
	}

	/**
	 * Returns the number of times two tags appear next to one another.
	 * 
	 * @param theCurrentTag
	 *            - Current tag finding the number of occurrences for.
	 * @param thePreviousTag
	 *            - Previous tag finding the number of occurrences for.
	 * @param theTagOrder
	 *            - The String containing the orders found for each tag.
	 * @return Number of occurrences for thePreviousTag/theCurrentTag in
	 *         theTagOrder.
	 */
	public final int getConscutiveTagCount(final String theCurrentTag, final String thePreviousTag) {
		int count = 0;
		final Pattern pattern = Pattern.compile(thePreviousTag + TAG_DIVIDER + theCurrentTag);
		final Matcher matcher = pattern.matcher(myTagOrder.toString());
		while (matcher.find()) {
			count++;
		}
		return count;
	}

	public final String getDecodedSequence() {
		if (stringToDecode == null || myDecodedString == null) {
			return "One or both strings are null";
		}
		final String[] sentence = stringToDecode.trim().split(" ");
		final String[] tags = myDecodedString.split(TAG_DIVIDER);


		final StringBuilder sb = new StringBuilder();
		for (int i = 0; i < sentence.length; i++) {
			sb.append(sentence[i] + TAG_DIVIDER + tags[i + 1] + " ");
		}
		return sb.toString();
	}

	/**
	 * Gets the probability - P(theCurrentWord|theCurrentTag).
	 * 
	 * @param theCurrentWord
	 *            - the word we're finding the probability of.
	 * @param theCurrentTag
	 *            - the tag we're finding the probability of.
	 * @return P(theCurrentWord|theCurrentTag), or default probability if word
	 *         does not exist.
	 */
	public double getEmissionTableProbability(final String theCurrentWord, final Tags theCurrentTag) {
		if (myEmissionTable.containsKey(theCurrentWord)) {
			return myEmissionTable.get(theCurrentWord).get(Tags.getTagIndex(theCurrentTag, false));
		}
		return getDefaultProbability(theCurrentTag);
	}

	/**
	 * Returns the probability - P(theCurrentTag|thePrevTag).
	 * 
	 * @param theCurrentTag
	 *            - the current tag we're getting the probability for.
	 * @param thePrevTag
	 *            - the previous tag we're getting the probability for.
	 * @return P(theCurrentTag|thePrevTag).
	 */
	public double getTransitionTableProbability(final Tags theCurrentTag, final Tags thePrevTag) {
		return myTransitionTable.get(theCurrentTag).get(Tags.getTagIndex(thePrevTag, true));
	}

	public HashMap<String, ProbabilityNode> getMyGraph() {
		return myGraph;
	}

	public double getMyStartProbability() {
		return myStartProbability;
	}

	public void setMyStartProbability(final double startProbability) {
		this.myStartProbability = startProbability;
	}

	public HashMap<Tags, List<Double>> getMyTransitionTable() {
		return myTransitionTable;
	}

	public HashMap<String, List<Double>> getMyEmissionTable() {
		return myEmissionTable;
	}

	public double getDefaultProbability(final Tags theTag) {
		return smoothing / (getTagCount(theTag) + myTotalVocabulary);
	}

	public String getFileName() {
		return fileName;
	}

	public String getMyDecodedString() {
		return myDecodedString;
	}

	public void setMyDecodedString(final String myDecodedString) {
		this.myDecodedString = myDecodedString;
	}

	public void setFileName(final String theFileName) {
		fileName = theFileName;
	}

	public String getStringToDecode() {
		return stringToDecode;
	}

	public void setStringToDecode(final String theStringToDecode) {
		stringToDecode = theStringToDecode;
	}

	public double getSmoothing() {
		return smoothing;
	}

	public void setSmoothing(final double thSmoothing) {
		smoothing = thSmoothing;
	}

	public int getMyTotalVocabulary() {
		return INSTANCE.myTotalVocabulary;
	}

	public void setMyTotalVocabulary(final int myTotalVocabulary) {
		INSTANCE.myTotalVocabulary = myTotalVocabulary;
	}

	public HashMap<String, WordNode> getMyWordMap() {
		return INSTANCE.myWordMap;
	}

	public void setMyWordMap(final HashMap<String, WordNode> myWordMap) {
		INSTANCE.myWordMap = myWordMap;
	}

	public StringBuilder getMyTagOrder() {
		return INSTANCE.myTagOrder;
	}

	public String getMyPrevWord() {
		return myPrevWord;
	}

	public void setMyPrevWord(final String myPrevWord) {
		this.myPrevWord = myPrevWord;
	}

}
