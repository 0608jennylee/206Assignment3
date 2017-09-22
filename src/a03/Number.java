package a03;

public enum Number {
	ONE("tahi", 1), TWO("rua", 2), THREE("toru", 3), FOUR("whaa", 4), FIVE("Rima", 5), SIX("ono", 6), SEVEN("whitu", 7), EIGHT("waru", 8), NINE("iwa", 9), TEN("tekau", 10);
	
	private final String maoriName;
	private final int number;
	
	private Number(String Mname, int num) {
		maoriName = Mname;
		number = num;
	}
	
	public String getMaoriName() {
		return maoriName;
	}
	
	public int getNumber() {
		return number;
	}
}
