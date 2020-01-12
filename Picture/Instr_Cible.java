package Picture;


public class Instr_Cible {
	public enum Type{
        LOAD , LOADC , STORE ,ADD , SUB ,MUL ,DIV ,EQUAL, INF,SUP, INFE
        ,SUPE ,JZERO,JUMP ,READ ,WRITEC , WRITE  , END , NEQUAL
    }
    
    private Type operation ;
    private int operant ;

    public Instr_Cible() {
    }
    
     public Instr_Cible(Type operation) {
        this.operation = operation;
    }
    
    public Instr_Cible(Type operation, int operant) {
        this.operation = operation;
        this.operant = operant;
    }

    public Type getOperation() {
        return operation;
    }

    public void setOperation(Type operation) {
        this.operation = operation;
    }

    

    public void setOperant(int operant) {
        this.operant = operant;
    }

    public int getOperant() {
        return operant;
    }
}
