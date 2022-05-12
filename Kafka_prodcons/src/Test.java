
public class Test {
	
	public static void main(String[] args) {
	String line="Tunisia 45454";
	String[] parts = line.split("\\t+");
	System.out.println(parts.length);
	System.out.println(parts[0]);
	}	
}
