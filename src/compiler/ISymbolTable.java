package compiler;

public interface ISymbolTable {
	public void addConstant(String id, String value) throws SymbolAlreadyDefinedException;
	public void addVariable(String id, String value) throws SymbolAlreadyDefinedException;
	
	public String getSymbol(String id) throws UnknownSymbolException;
	public boolean isVariable(String id) throws UnknownSymbolException;
	public boolean isFunction(String id) throws UnknownSymbolException;
	public boolean isConstant(String id) throws UnknownSymbolException;
}
