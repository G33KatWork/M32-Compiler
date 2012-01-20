package compiler;

import java.util.Hashtable;
import java.util.Map.Entry;

public class GlobalSymbolTable implements ISymbolTable
{	
	private Hashtable<String, Declaration> symTbl = new Hashtable<String, Declaration>();
	
	@Override
	public void addVariable(String id, String value) throws SymbolAlreadyDefinedException
	{
		if(symTbl.containsKey(id))
			throw new SymbolAlreadyDefinedException("Symbol " + id + " already defined.");
		
		symTbl.put(id, new GlobalVariableDeclaration(id, value));
	}
	
	@Override
	public void addConstant(String id, String value) throws SymbolAlreadyDefinedException
	{
		if(symTbl.containsKey(id))
			throw new SymbolAlreadyDefinedException("Symbol " + id + " already defined.");
		
		symTbl.put(id, new ConstantDeclaration(id, value));
	}
	
	public void addProcedure(String id, FunctionSymbolTable procedureSymTbl) throws SymbolAlreadyDefinedException
	{
		if(symTbl.containsKey(id))
			throw new SymbolAlreadyDefinedException("Symbol " + id + " already defined.");
		
		symTbl.put(id, new ProcedureDeclaration(id, procedureSymTbl));
	}
	
	public void addFunction(String id, FunctionSymbolTable procedureSymTbl) throws SymbolAlreadyDefinedException
	{
		if(symTbl.containsKey(id))
			throw new SymbolAlreadyDefinedException("Symbol " + id + " already defined.");
		
		symTbl.put(id, new FunctionDeclaration(id, procedureSymTbl));
	}
	
	public String getSymbol(String id) throws UnknownSymbolException
	{
		if(!symTbl.containsKey(id))
			throw new UnknownSymbolException("Symbol " + id + " wasn't defined.");
		
		return symTbl.get(id).getValue();
	}
	
	@Override
	public boolean isVariable(String id) throws UnknownSymbolException {
		if(!symTbl.containsKey(id))
			throw new UnknownSymbolException("Symbol " + id + " wasn't defined.");
		
		return symTbl.get(id).getType() == DeclType.VARIABLE;
	}
	
	public boolean isFunction(String id) throws UnknownSymbolException {
		if(!symTbl.containsKey(id))
			throw new UnknownSymbolException("Symbol " + id + " wasn't defined.");
		
		return symTbl.get(id).getType() == DeclType.FUNCTION;
	}
	
	public boolean isConstant(String id) throws UnknownSymbolException {
		if(!symTbl.containsKey(id))
			throw new UnknownSymbolException("Symbol " + id + " wasn't defined.");
		
		return symTbl.get(id).getType() == DeclType.CONSTANT;
	}
	
	public FunctionSymbolTable getFunctionSymbolTable(String id) throws Exception {
		if(!symTbl.containsKey(id))
			throw new UnknownSymbolException("Symbol " + id + " wasn't defined.");
		
		if(symTbl.get(id).getType() == DeclType.FUNCTION)
			return ((FunctionDeclaration)symTbl.get(id)).getSymbolTable();
		else if(symTbl.get(id).getType() == DeclType.PROCEDURE)
			return ((ProcedureDeclaration)symTbl.get(id)).getSymbolTable();
		else
			throw new Exception("INTERNAL ERROR: no func/proc");
	}
	
	public void setFunctionCode(String id, String code) throws Exception {
		if(!symTbl.containsKey(id))
			throw new UnknownSymbolException("Symbol " + id + " wasn't defined.");
		
		if(symTbl.get(id).getType() == DeclType.FUNCTION)
			((FunctionDeclaration)symTbl.get(id)).setCode(code);
		else if(symTbl.get(id).getType() == DeclType.PROCEDURE)
			((ProcedureDeclaration)symTbl.get(id)).setCode(code);
		else
			throw new Exception("INTERNAL ERROR: no func/proc");
	}
	
	public String generateVariableTable() {
		StringBuilder s = new StringBuilder();
		
		for(Entry<String, Declaration> entry : symTbl.entrySet())
		{
			Declaration d = entry.getValue();
			if(d.getType() == DeclType.VARIABLE)
				s.append(d.getName() + ":\t DW " + ((GlobalVariableDeclaration)d).getInitialValue() + "\n");
		}
		
		return s.toString();
	}
	
	public String generateFunctions() {
		StringBuilder s = new StringBuilder();
		
		for(Entry<String, Declaration> entry : symTbl.entrySet())
		{
			Declaration d = entry.getValue();
			if(d.getType() == DeclType.PROCEDURE)
				s.append(d.getName() + ":\n" + ((ProcedureDeclaration)d).getBody() + "\n\n");
			else if(d.getType() == DeclType.FUNCTION)
				s.append(d.getName() + ":\n" + ((FunctionDeclaration)d).getBody() + "\n\n");
		}
		
		return s.toString();
	}
}
