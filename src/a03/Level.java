package a03;

public enum Level {
	EASYNUMBERS(9), HARDNUMBERS(99), EASYEQUATIONS(9), HARDEQUATIONS(99);
	
	private final int _range;
	
	private Level(int range){
		_range = range;
	}
	
	public int getRange(){
		return _range;
	}
}
