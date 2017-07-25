package src;

/**
 * Main driving class.
 * 
 * @author Amy Irving
 * @version July 22, 2017
 *
 */
public class Driver {

	private Driver() {
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

		start();

	}

	public static void start() {
		ProcessString.generateWordMap();
		
		System.out.println(Metadata.INSTANCE.getMyWordMap());

		Emission.generateTable();
		System.out.println(Metadata.INSTANCE.getMyEmissionTable());
		Transition.generateTable();
		System.out.println(Metadata.INSTANCE.getMyTransitionTable());
		
		Decoding.Decode();

		Write.writeAllData();
	}

}
