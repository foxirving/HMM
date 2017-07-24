/**
 * Main driving class.
 * 
 * @author Amy Irving
 * @version July 22, 2017
 *
 */
public class Main {

	private Main() {
		// Private constructor.
	}

	/**
	 * Main method. Reads a file, processes string, creates Hashmap with data,
	 * generates emission table and transition table. Then decodes a given
	 * sentence.
	 * 
	 * Writes out all data to a file.
	 */
	public static void main(final String[] args) {

		Metadata.INSTANCE.setFileName(args[0]);
		Metadata.INSTANCE.setStringToDecode(args[1]);
		Metadata.INSTANCE.setSmoothing(Double.valueOf(args[2]));
		Metadata.INSTANCE.setMyStartProbability((Double.valueOf(args[3])));

		ProcessString.generateWordMap();

		Emission.generateTable();
		Transition.generateTable();

		System.out.println(Metadata.INSTANCE.getMyEmissionTable());
		System.out.println(Metadata.INSTANCE.getMyTransitionTable());

		Decoding.Decode();

		System.out.println();
		for (final String key : Metadata.INSTANCE.getMyGraph().keySet()) {
			System.out.println(key + "		" + Metadata.INSTANCE.getMyGraph().get(key));
			System.out.println();
		}

		System.out.println(Metadata.INSTANCE.getMyDecodedString());
	}

}
