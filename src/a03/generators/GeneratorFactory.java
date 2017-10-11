package a03.generators;

import a03.enumerations.Difficulty;
import a03.enumerations.Level;

public class GeneratorFactory {
	public Generator getGenerator(Difficulty difficulty, Level level) {
		if(difficulty == Difficulty.EASY) {
			if(level == Level.EQUATIONS) {
				return new EasyEquationsGenerator(difficulty);
			}else {
				return new EasyNumbersGenerator(difficulty);
			}
		}else {
			if(level == Level.EQUATIONS) {
				return new HardEquationsGenerator(difficulty);
			}else {
				return new HardNumbersGenerator(difficulty);
			}
		}
	}
}
