package intrepreteur;

public class Instruction {
	
    public enum Type{
        LOAD , LOADC , STORE ,ADD , SUB ,MUL ,DIV ,EQUAL, INF,SUP, INFE
        ,SUPE ,JZERO,JUMP ,READ ,WRITEC , WRITE  , END,NEQUAL, }
    private Type operation ;
    private String operant ;
    public static String resultat;

    public Instruction() {
    }

     public Instruction(Type operation) {
        this.operation = operation;
    }
    
    public Instruction(Type operation, String operant) {
        this.operation = operation;
        this.operant = operant;
    }

    public Type getOperation() {
        return operation;
    }

    public void setOperation(Type operation) {
        this.operation = operation;
    }

    

    public void setOperant(String operant) {
        this.operant = operant;
    }

    @Override
	public String toString() {
    	if(operant==null){
    		return "Instruction [operation=" + operation + ", operant=" + operant + "]\n";
    	}
		return "Instruction [operation=" + operation + ", operant=" + operant + "]\n";
	}
    public String toString1() {
    	String s=operation.toString().toLowerCase () ;
    	if(operant==null){
    		return  s+ "\r\n";
    	}
		return s + " " +(int) Float.parseFloat(operant) + "\r\n";
	}
	public String getOperant() {
        return operant;
    }
    
    
}
