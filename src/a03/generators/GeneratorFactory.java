package a03.generators;

import a03.enumerations.Difficulty;
import a03.enumerations.Level;

/**
 * A generator factory that handles returning the required generator depending on the difficulty and level.
 * @author Edwar Zhang, Jenny Lee
 *
 */
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
