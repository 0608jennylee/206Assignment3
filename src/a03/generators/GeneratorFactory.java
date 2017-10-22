package a03.generators;

import a03.enumerations.Difficulty;
import a03.enumerations.Level;

public class GeneratorFactory {
	public Generator getGenerator(Difficulty difficulty, Level level, int questions) {
		if(level == Level.EQUATIONS) {
			return new EquationsGenerator(difficulty);
		}else if(level == Level.NUMBERS){
			return new NumbersGenerator(difficulty);
		}else {
			return new CustomGenerator(difficulty, questions);
		}
	}
}
