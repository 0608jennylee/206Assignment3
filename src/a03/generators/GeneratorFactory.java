package a03.generators;

import a03.enumerations.Difficulty;
import a03.enumerations.Level;

public class GeneratorFactory {
	public Generator getGenerator(Difficulty difficulty, Level level, int questions) {
		if(difficulty == Difficulty.CUSTOM) {
			return new CustomGenerator(difficulty, questions, level);
		}else {
			if(level == Level.EQUATIONS) {
				return new EquationsGenerator(difficulty);
			}else{
				return new NumbersGenerator(difficulty);
			}
		}
	}
}
