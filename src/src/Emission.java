package src;
import java.util.ArrayList;
import java.util.List;

/**
 * Class generates an emission table for all words and tags.
 * 
 * @author Amy Irving
 * @version July 22, 2017
 *
 */
public class Emission {

	private Emission() {
		// Private Constructor.
	}

	/**
	 * Generates the emission table. References MetaData.INSTANCE for all data.
	 * 
	 */
	public static final void generateTable() {
		final List<Double> list = new ArrayList<Double>();
		for (final WordNode node : Metadata.INSTANCE.getMyWordMap().values()) {
			for (int i = 0; i < Tags.getTags(false).size(); i++) {
				final int count = node.getCount(Tags.getTags(false).get(i));
				final int tagTotal = Metadata.INSTANCE.getTagCount(Tags.getTags(false).get(i));
				final double probability = (count + Metadata.INSTANCE.getSmoothing())
						/ (tagTotal + Metadata.INSTANCE.getMyTotalVocabulary());
				list.add(probability);
			}
			Metadata.INSTANCE.getMyEmissionTable().put(node.getMyStringName(), new ArrayList<Double>(list));
			list.clear();
		} // end for
	}

}
