package src;
import java.util.ArrayList;
import java.util.List;

/**
 * Generates transition table for all tag combinations.
 * 
 * @author Amy Irving
 * @version July 22, 2017
 *
 */
public class Transition {

	private Transition() {
		// private construcctor.
	}

	/**
	 * Generates the transition table.
	 * 
	 */
	public final static void generateTable() {
		final List<Double> list = new ArrayList<Double>();
		System.out.println();
		for (int i = 0; i < Tags.getTags(false).size(); i++) {
			final Tags currentTag = Tags.getTags(false).get(i);
			// Calculates the P(CurrentTag | Start)
			final double startProbability = (Metadata.INSTANCE.getConscutiveTagCount(currentTag.name(),
					Tags.START.getMyString()) + Metadata.INSTANCE.getSmoothing())
					/ (1 + Metadata.INSTANCE.getMyTotalVocabulary());
			list.add(startProbability);

			// Iterate through rest of the tags and finds P(CurrentTag |
			// PreviousTag)
			for (int j = 0; j < Tags.getTags(false).size(); j++) {
				final Tags PreviousTag = Tags.getTags(false).get(j);
				final double probability = (Metadata.INSTANCE.getConscutiveTagCount(currentTag.name(),
						PreviousTag.name()) + Metadata.INSTANCE.getSmoothing())
						/ (Metadata.INSTANCE.getTagCount(PreviousTag) + Metadata.INSTANCE.getMyTotalVocabulary());
				list.add(probability);
			}

			Metadata.INSTANCE.getMyTransitionTable().put(currentTag, new ArrayList<Double>(list));
			list.clear();
		}
	}

}
