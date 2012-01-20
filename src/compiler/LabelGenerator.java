package compiler;

public class LabelGenerator {
	private static int nextLabel = 0;
	
	public static String generateLabel() {
		return "L" + nextLabel++;
	}
}
