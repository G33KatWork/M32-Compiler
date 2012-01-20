package compiler;

import java.util.Hashtable;
import java.util.Map.Entry;

public class FunctionSymbolTable implements ISymbolTable {
	private GlobalSymbolTable parentTable;
	private Hashtable<String, Declaration> symTbl = new Hashtable<String, Declaration>();
	
	private int nextParameterOffset = 0;
	private int nextLocalVariableOffset = 0;
	
	public FunctionSymbolTable(GlobalSymbolTable parent) {
		this.parentTable = parent;
	}
	
	public void addParameter(String id) throws SymbolAlreadyDefinedException
	{
		if(symTbl.containsKey(id))
			throw new SymbolAlreadyDefinedException("Symbol " + id + " already defined.");
		
		symTbl.put(id, new ParameterDeclaration(id, nextParameterOffset));
		nextParameterOffset++;
	}

	@Override
	public void addConstant(String id, String value) throws SymbolAlreadyDefinedException
	{
		if(symTbl.containsKey(id))
			throw new SymbolAlreadyDefinedException("Symbol " + id + " already defined.");
		
		symTbl.put(id, new ConstantDeclaration(id, value));
	}
	
	@Override
	public void addVariable(String id, String value) throws SymbolAlreadyDefinedException {
		if(symTbl.containsKey(id))
			throw new SymbolAlreadyDefinedException("Symbol " + id + " already defined.");
		
		symTbl.put(id, new LocalVariableDeclaration(id, value, nextLocalVariableOffset));
		nextLocalVariableOffset++;
	}

	@Override
	public String getSymbol(String id) throws UnknownSymbolException {
		if(!symTbl.containsKey(id))
			return parentTable.getSymbol(id);
		
		return symTbl.get(id).getValue();
	}

	@Override
	public boolean isVariable(String id) throws UnknownSymbolException {
		if(!symTbl.containsKey(id))
			return parentTable.isVariable(id);
		else
			return symTbl.get(id).getType() == DeclType.LOCAL_VARIABLE || symTbl.get(id).getType() == DeclType.PARAMETER;
	}
	
	public int getParameterCount() {
		int count = 0;
		for(Entry<String, Declaration> entry : symTbl.entrySet())
		{
			Declaration d = entry.getValue();
			if(d.getType() == DeclType.PARAMETER)
				count++;
		}
		return count;
	}
	
	public int getLocalVariableCount() {
		int count = 0;
		for(Entry<String, Declaration> entry : symTbl.entrySet())
		{
			Declaration d = entry.getValue();
			if(d.getType() == DeclType.LOCAL_VARIABLE)
				count++;
		}
		return count;
	}
	
	public String getLocalVariableInitCode() {
		String code = "";
		for(Entry<String, Declaration> entry : symTbl.entrySet())
		{
			Declaration d = entry.getValue();
			if(d.getType() == DeclType.LOCAL_VARIABLE)
				code += "\tMOV " + d.getValue() + ", " + ((LocalVariableDeclaration)d).getInitialValue() + "\n";
		}
		return code;
	}

	@Override
	public boolean isFunction(String id) throws UnknownSymbolException {
		return parentTable.isFunction(id);
	}

	@Override
	public boolean isConstant(String id) throws UnknownSymbolException {
		if(!symTbl.containsKey(id))
			return parentTable.isConstant(id);
		else
			return symTbl.get(id).getType() == DeclType.CONSTANT;
	}
}