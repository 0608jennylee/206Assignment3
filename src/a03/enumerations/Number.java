package a03.enumerations;

public enum Number {
	ONE("tahi", 1), TWO("rua", 2), THREE("toru", 3), FOUR("whā",4), FIVE("rima", 5), SIX("ono", 6), SEVEN("whitu", 7), EIGHT("waru", 8), NINE("iwa", 9), TEN("tekau", 10);
	
	private final String _maoriName;
	private final int _number;
	
	private Number(String Mname, int num) {
		_maoriName = Mname;
		_number = num;
	}
	
	public String getMaoriName() {
		return _maoriName;
	}
	
	public int getNumber() {
		return _number;
	}
}
