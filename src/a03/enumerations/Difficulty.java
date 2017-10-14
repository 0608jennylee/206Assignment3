package a03.enumerations;

public enum Difficulty {
	EASY(9, 1), HARD(99, 1), CUSTOM(99, 1);
	
	private int _max;
	private int _min;
	
	private Difficulty(int max, int min){
		_max = max;
		_min = min;
	}
	
	public void setMax(int max) {
		_max = max;
	}
	
	public int getMax() {
		return _max;
	}
	
	public void setMin(int min) {
		_min = min;
	}
	
	public int getMin() {
		return _min;
	}
	
}
