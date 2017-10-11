package a03.errors;

public class EnumConversionError extends RuntimeException{

	private static final long serialVersionUID = 8820885514039304460L;
	
	EnumConversionError(){
		super("String does not have a corresponding Enumeration");
	}

}
