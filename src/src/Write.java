package src;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.util.Formatter;
import java.util.List;

public class Write {

	public static final String FORMAT = "%4s";
	public static final String LINE_BREAK = "\n";
	public static final String SPACING = "		";

	public static void writeAllData() {

		final StringBuilder sb = new StringBuilder();
		final Formatter fmt = new Formatter(sb);

		// Write emission table
		sb.append(SPACING + "		Emission Table" + LINE_BREAK + LINE_BREAK);
		sb.append(SPACING);
		for (final Tags tag : Tags.getTags(false)) {
			fmt.format("	  	  %-5s", tag.getMyString());
		}
		sb.append(LINE_BREAK);
		for (final String key : Metadata.INSTANCE.getMyEmissionTable().keySet()) {
			fmt.format("%-15s", key);
			for (final Double number : Metadata.INSTANCE.getMyEmissionTable().get(key)) {
				fmt.format("  %6f  ", Double.valueOf(number));
			}
			sb.append(LINE_BREAK);
		}

		sb.append(LINE_BREAK);
		sb.append(LINE_BREAK);
		
		// Write transition table
		sb.append(SPACING + "		Transition Table" + LINE_BREAK + LINE_BREAK);
		sb.append(SPACING);
		for (final Tags tag : Tags.getTags(false)) {
			fmt.format("	  	  %-5s", tag.getMyString());
		}
		sb.append(LINE_BREAK);
		int index = 0;
		for (final Tags rowTag : Tags.getTags(true)) {
			fmt.format("%-15s", rowTag.getMyString());
			for (final Tags columnTag : Tags.getTags(false)) {
				final List<Double> list = Metadata.INSTANCE.getMyTransitionTable().get(columnTag);
				final double number = list.get(index);
				fmt.format("  %6f  ", number);
			}
			index++;
			sb.append(LINE_BREAK);
		}

		sb.append(LINE_BREAK);
		sb.append("Decoded Sequence: ");
		sb.append(LINE_BREAK);
		// Write given sentence to decode with decoded tags
		sb.append(Metadata.INSTANCE.getDecodedSequence());
		
		System.out.println(sb.toString());
		
		try (BufferedWriter writer = new BufferedWriter(
				new OutputStreamWriter(new FileOutputStream(Metadata.OUT_FILE_NAME), "utf-8"))) {
			writer.write(sb.toString());
		} catch (final UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (final FileNotFoundException e) {
			e.printStackTrace();
		} catch (final IOException e) {
			e.printStackTrace();
		}
	}

}
