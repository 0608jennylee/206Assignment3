package a03;

public enum Difficulty {
	EASY(9), HARD(99);
	
	private final int _range;
	
	private Difficulty(int range){
		_range = range;
	}
	
	public int getRange(){
		return _range;
	}
}
