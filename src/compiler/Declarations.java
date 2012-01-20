package compiler;

enum DeclType {
	CONSTANT, VARIABLE, PROCEDURE, FUNCTION, PARAMETER, LOCAL_VARIABLE
}

abstract class Declaration {
	protected DeclType type;
	protected String name;
	
	Declaration(DeclType type, String name)
	{
		this.type = type;
		this.name = name;
	}
	
	DeclType getType() {
		return this.type;
	}
	
	String getName() {
		return this.name;
	}
	
	abstract String getValue();
}

class ConstantDeclaration extends Declaration {
	private String value;
	
	ConstantDeclaration(String name, String value) {
		super(DeclType.CONSTANT, name);
		this.value = value;
	}
	
	String getValue() {
		return this.value;
	}
}

class GlobalVariableDeclaration extends Declaration {
	private String initialValue;
	
	GlobalVariableDeclaration(String name, String initialValue) {
		super(DeclType.VARIABLE, name);
		this.initialValue = initialValue;
	}

	@Override
	String getValue() {
		return "@" + this.name;
	}
	
	String getInitialValue() {
		return this.initialValue;
	}
}

class LocalVariableDeclaration extends Declaration {
	private String initialValue;
	private int offset;
	
	LocalVariableDeclaration(String name, String initialValue, int offset) {
		super(DeclType.LOCAL_VARIABLE, name);
		this.initialValue = initialValue;
		this.offset = offset;
	}

	@Override
	String getValue() {
		return "@" + String.format("0%XH",-this.offset) + "[R7]";
	}
	
	String getInitialValue() {
		return this.initialValue;
	}
}

class ProcedureDeclaration extends Declaration {
	private FunctionSymbolTable tbl;
	private String code = "";
	
	public ProcedureDeclaration(String name, FunctionSymbolTable symTbl) {
		super(DeclType.PROCEDURE, name);
		this.tbl = symTbl;
	}
	
	@Override
	String getValue() {
		return this.name;
	}
	
	String getBody() {
		return this.code;
	}
	
	void setCode(String code) {
		this.code = code;
	}
	
	FunctionSymbolTable getSymbolTable() {
		return this.tbl;
	}
}

class FunctionDeclaration extends Declaration {
	private FunctionSymbolTable tbl;
	private String code = "";
	
	public FunctionDeclaration(String name, FunctionSymbolTable symTbl) {
		super(DeclType.FUNCTION, name);
		this.tbl = symTbl;
	}
	
	@Override
	String getValue() {
		return this.name;
	}
	
	String getBody() {
		return this.code;
	}
	
	void setCode(String code) {
		this.code = code;
	}
	
	FunctionSymbolTable getSymbolTable() {
		return this.tbl;
	}
}

class ParameterDeclaration extends Declaration {
	int offset;
	
	public ParameterDeclaration(String name, int offset) {
		super(DeclType.PARAMETER, name);
		this.offset = offset;
	}
	
	@Override
	String getValue() {
		//+2 for old basepointer and return value
		return "@" + (3 + this.offset) + "[R7]";
	}
}
