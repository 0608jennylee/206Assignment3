package a03;

public enum Level {
	EASY(9), HARD(99);
	
	private final int _range;
	
	private Level(int range){
		_range = range;
	}
	
	public int getRange(){
		return _range;
	}
}
