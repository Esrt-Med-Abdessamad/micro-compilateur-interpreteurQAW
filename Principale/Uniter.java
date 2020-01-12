
package Principale;

public class Uniter {
	/** 
	 * @author Esserrati Mohammed Abdessamad
	 */    
    @Override
	public String toString() {
    	if(valeur==null){
    		return "[" + type+ "]";
    	}
		return "[" + type + "," + valeur + "]";
	}
	public enum Type{
        IDENT,ACOLADO,ACOLADF,PARO,PARF,PVIRG,POINT,EGAL,IF,ELSE,SUPE,SUP,INT,NEAT
        ,INFE,INF,DIFF,VIRG,AND,OR,MULT,PLUS,MOINS,DIV,FLOAT,CHAR,CHAINE_CHAR,BOOLEAN,
        TYPE_INT,TYPE_FLOAT,TYPE_BOOLEAN,TYPE_CHAR,TYPE_CHAINE_CHAR,
        MOD,WHILE,FOR,AFF,PROGRAMME,FIN,DOLLAR,DEUX_POINTS,ALORS,FIN_SI,DEC,INC
        ,CONSTANT,VARIBLE,MAIN,TO,READ, WRITE,STEP ,DIVE,COMMENTAIRE, 
        
    }
    private Type type;
    private String valeur;

    public Uniter(Type type, String valeur) {
        this.type = type;
        this.valeur = valeur;
    }

    public Uniter(Type type) {
        this.type = type;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public String getValeur() {
        return valeur;
    }

    public void setValeur(String valeur) {
        this.valeur = valeur;
    }
}