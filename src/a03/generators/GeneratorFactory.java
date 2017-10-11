package a03.generators;

import a03.enumerations.Level;

public class GeneratorFactory {
	public Generator getGenerator(Level level) {
		switch (level){
		case EASYNUMBERS:
			return new EasyNumbersGenerator(level);
		case HARDNUMBERS:
			return new HardNumbersGenerator(level);
		case EASYEQUATIONS:
			return new EasyEquationsGenerator(level);
		case HARDEQUATIONS:
			return new HardEquationsGenerator(level);
		}
		return null;
	}
}
