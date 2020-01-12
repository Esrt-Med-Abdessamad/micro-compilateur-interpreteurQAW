package Principale;

import java.util.ArrayList;
import Principale.Uniter.Type;
import intrepreteur.Pile;

public class Main {
	private Pile<Uniter> pile;
	private AnalyseurLexical Al;
	private ArrayList<Uniter> uniters = new ArrayList<>();
	private Uniter uniter ;
    private int pse=0;

//   private String Erreur;
//	private Uniter pse;
    
	public Main(String texte) {
		Al = new AnalyseurLexical(texte);
        uniter = Al.lireUniter();
        while(!uniter.getType().equals(Uniter.Type.NEAT)){
        	System.out.print(uniter.getType().toString()+"\t");
            uniters.add(uniter);
            uniter = Al.lireUniter();
        }System.out.println();
			
	}
	
	public boolean AnalyseDescendante(){	
        pile.empiler(new Uniter(Type.NEAT));
        pile.empiler(new Uniter(Type.PROGRAMME));
		while (true) {
			if( pile.sommet().estNonTerminale()){
				
			}else{
				if(pile.sommet().getType()== Type.NEAT ){
					if ( uniters.get(pse).getType() == Type.NEAT) {
						return true;
					} else {
						return false;
					}
				}else{
					if (pile.sommet().getType()==uniters.get(pse).getType()) {
						pse++;
						pile.depiler();
					} else {
						return false;
					}
				}
			}
		}
	}
	
	public boolean AnalyseProceduraleIndirecte(){
		if(!terminal(Type.MOD)){
			return false;
		}
		pse++;
		if(!terminal(Type.IDENT)){
			return false;
		}
		pse++;
		if(!terminal(Type.MOD)){
			return false;
		}
		pse++;
		if(!terminal(Type.ACOLADO)){
		//	System.out.println("ERREUR : auvraire l'acolad");
			return false;
		}
		pse++;
		if(!bloc()){
			return false;
		}
		pse++;
		if (!terminal(Type.ACOLADF)) {
			return false;
		}
	//	System.out.println(uniters.size()-1+uniters.get(uniters.size()-1).getType().toString());
		if(pse!=(uniters.size()-1)){
			System.out.println("n'est pas correct"+"	"+pse+"		"+(uniters.size()-1));return false;
		}
		System.out.println("Programme correct!"+"	"+pse+"		"+(uniters.size()-1));
		return true;
						
	}
	
	public boolean bloc(){
		if (pse < uniters.size() && uniters.get(pse).getType()==Type.CONSTANT) {
			pse++;
			if(!terminal(Type.ACOLADO)){
				return false;
			}
			pse++;
			if(!decl_const()){
				return false;
			}
			if(!terminal(Type.ACOLADF)){
				return false;
			}
			pse++;
		}
		if(pse < uniters.size() && uniters.get(pse).getType()==Type.VARIBLE){
			pse++;
			if(!terminal(Type.ACOLADO)){
				return false;
			}
			pse++;
			if(!decl_varibale()){
				return false;
			}
			if(!terminal(Type.ACOLADF)){
				return false;
			}
			pse++;
		}
		if(!Main()){
			return false;
		}
		return true;
	}
	
	public boolean decl_const(){
		if(pse < uniters.size() && uniters.get(pse).getType()==Type.TYPE_INT){
			pse++;
			if(!terminal(Type.IDENT)){
				return false;
			}
			pse++;
			if(!terminal(Type.AFF)){
				return false;
			}
			pse++;
			if(!terminal(Type.INT)){
				return false;
			}
			pse++;
			if(!terminal(Type.PVIRG)){
				return false;
			}
			pse++;
			if(pse < uniters.size() && uniters.get(pse).getType()!=Type.ACOLADF){
				if(!decl_const()){
					return false;
				}
			}
		}
		if(pse < uniters.size() && uniters.get(pse).getType()==Type.TYPE_FLOAT){
			pse++;
			if(!terminal(Type.IDENT)){
				return false;
			}
			pse++;
			if(!terminal(Type.AFF)){
				return false;
			}
			pse++;
			if(!terminal(Type.FLOAT) && !terminal(Type.INT)){
				return false;
			}
			pse++;
			if(!terminal(Type.PVIRG)){
				return false;
			}
			pse++;
			if(pse < uniters.size() && uniters.get(pse).getType()!=Type.ACOLADF){
				if(!decl_const()){
					return false;
				}
			}
		}
		if(pse < uniters.size() && uniters.get(pse).getType()==Type.TYPE_BOOLEAN){
			pse++;
			if(!terminal(Type.IDENT)){
				return false;
			}
			pse++;
			if(!terminal(Type.AFF)){
				return false;
			}
			pse++;
			if(!terminal(Type.BOOLEAN)){
				return false;
			}
			pse++;
			if(!terminal(Type.PVIRG)){
				return false;
			}
			pse++;
			if(pse < uniters.size() && uniters.get(pse).getType()!=Type.ACOLADF){
				if(!decl_const()){
					return false;
				}
			}
		}
		if(pse < uniters.size() && uniters.get(pse).getType()==Type.TYPE_CHAINE_CHAR){
			pse++;
			if(!terminal(Type.IDENT)){
				return false;
			}
			pse++;
			if(!terminal(Type.AFF)){
				return false;
			}
			pse++;
			if(!terminal(Type.CHAINE_CHAR)){
				return false;}
			pse++;
			if(!terminal(Type.PVIRG)){
				return false;
			}
			pse++;
			//System.out.println("nice  "+uniters.get(pse).getType().toString());
			if(pse < uniters.size() && uniters.get(pse).getType()!=Type.ACOLADF){
				if(!decl_const()){
					return false;
				}
			}
		}
		if(pse < uniters.size() && uniters.get(pse).getType()==Type.TYPE_CHAR){
			pse++;
			if(!terminal(Type.IDENT)){
				return false;
			}
			pse++;
			if(!terminal(Type.AFF)){
				return false;
			}
			pse++;
			if(!terminal(Type.CHAR)){
				return false;
			}
			pse++;
			if(!terminal(Type.PVIRG)){
				return false;
			}
			pse++;
			if(pse < uniters.size() && uniters.get(pse).getType()!=Type.ACOLADF){
				if(!decl_const()){
					return false;
				};
			}
		}
		return true;
	}
	
	public boolean decl_varibale(){
		if(pse < uniters.size() && uniters.get(pse).getType()==Type.TYPE_INT){
		pse++;
		if(!terminal(Type.IDENT)){
			return false;
		}
		pse++;
		if(!terminal(Type.AFF)){
			return false;
		}
		pse++;
		if(!terminal(Type.INT)){
			return false;
		}
		pse++;
		if(!terminal(Type.PVIRG)){
			return false;
		}
		pse++;
		if(pse < uniters.size() && uniters.get(pse).getType()!=Type.ACOLADF){
			if(!decl_const()){
				return false;
			}
		}
	}
	if(pse < uniters.size() && uniters.get(pse).getType()==Type.TYPE_FLOAT){
		pse++;
		if(!terminal(Type.IDENT)){
			return false;
		}
		pse++;
		if(!terminal(Type.AFF)){
			return false;
		}
		pse++;
		if(!terminal(Type.FLOAT)){
			return false;
		}
		pse++;
		if(!terminal(Type.PVIRG)){
			return false;
		}
		pse++;
		if(pse < uniters.size() && uniters.get(pse).getType()!=Type.ACOLADF){
			if(!decl_const()){
				return false;
			}
		}
	}
	if(pse < uniters.size() && uniters.get(pse).getType()==Type.TYPE_BOOLEAN){
		pse++;
		if(!terminal(Type.IDENT)){
			return false;
		}
		pse++;
		if(!terminal(Type.AFF)){
			return false;
		}
		pse++;
		if(!terminal(Type.BOOLEAN)){
			return false;
		}
		pse++;
		if(!terminal(Type.PVIRG)){
			return false;
		}
		pse++;
		if(pse < uniters.size() && uniters.get(pse).getType()!=Type.ACOLADF){
			if(!decl_const()){
				return false;
			}
		}
	}
	if(pse < uniters.size() && uniters.get(pse).getType()==Type.TYPE_CHAINE_CHAR){
		pse++;
		if(!terminal(Type.IDENT)){
			return false;
		}
		pse++;
		if(!terminal(Type.AFF)){
			return false;
		}
		pse++;
		if(!terminal(Type.CHAINE_CHAR)){
			return false;}
		pse++;
		if(!terminal(Type.PVIRG)){
			return false;
		}
		pse++;
		if(pse < uniters.size() && uniters.get(pse).getType()!=Type.ACOLADF){
			if(!decl_const()){
				return false;
			}
		}
	}
	if(pse < uniters.size() && uniters.get(pse).getType()==Type.TYPE_CHAR){
		pse++;
		if(!terminal(Type.IDENT)){
			return false;
		}
		pse++;
		if(!terminal(Type.AFF)){
			return false;
		}
		pse++;
		if(!terminal(Type.CHAR)){
			return false;
		}
		pse++;
		if(!terminal(Type.PVIRG)){
			return false;
		}
		pse++;
		if(pse < uniters.size() && uniters.get(pse).getType()!=Type.ACOLADF){
			if(!decl_const()){
				return false;
			};
		}
	}
	return true;
		// possible de faire declaration comme int x=0,y,z; donc on va augmenter le nevaux de ...
	}
	
	public boolean Main(){
		if(!terminal(Type.MAIN)){
			return false;
		}
		pse++;
		if(!terminal(Type.ACOLADO)){
			return false;
		}
		pse++;
		if(pse < uniters.size() && uniters.get(pse).getType()!=Type.ACOLADF){
			if(!instruction()){
				return false;
			}
		}
		if(!terminal(Type.ACOLADF)){
			return false;
		}
		return true;
	}
	public boolean instruction(){
		if(pse < uniters.size() && uniters.get(pse).getType()==Type.IDENT){
			pse++;
			if(!terminal(Type.AFF)){
				return false;
			}
			pse++;
			if(!expression()){			//		simple_expression  ou   expression    ;
				return false;
			}
			if(!terminal(Type.PVIRG)){
				return false;
			}
			pse++;
			if(pse < uniters.size() && uniters.get(pse).getType()!=Type.ACOLADF){
				if(!instruction()){
					return false;
				}
			}
		}
		if(pse < uniters.size() && uniters.get(pse).getType()==Type.IF){
			pse++;
			if(!terminal(Type.PARO)){
				return false;
			}
			pse++;
			if(!expression()){
				return false;
			}
			if(!terminal(Type.PARF)){
				return false;
			}
			pse++;
			if(!terminal(Type.ACOLADO)){
				return false;
			}
			pse++;
			if(!instruction()){
				return false;
			}
			if(!terminal(Type.ACOLADF)){
				return false;
			}
			pse++;
			if(pse < uniters.size() && uniters.get(pse).getType()==Type.ELSE){
				pse++;
				if(!terminal(Type.ACOLADO)){
					return false;
				}
				pse++;
				if(!instruction()){
					return false;
				}
				if(!terminal(Type.ACOLADF)){
					return false;
				}
				pse++;
			}
			if(pse < uniters.size() && uniters.get(pse).getType()!=Type.ACOLADF){
				if(!instruction()){
					return false;
				}
			}
		//on va faire  une chose pour la possibilite de declarer 
		//      une seul instruction sans acolado
		/**	if(uniters.get(pse).getType()!=Type.ACOLADO){
				pse++;
				instruction();
				terminal(Type.ACOLADF);
				
			}*/
		}
		if(pse < uniters.size() && uniters.get(pse).getType()==Type.WHILE){
			pse++;
			if(!terminal(Type.PARO)){
				return false;
			}
			pse++;
			if(!expression()){
				return false;
			}
			if(!terminal(Type.PARF)){
				return false;
			}
			pse++;
			if(!terminal(Type.ACOLADO)){
				return false;
			}
			pse++;
			if(!instruction()){
				return false;
			}
			if(!terminal(Type.ACOLADF)){
				return false;
			}
			pse++;
			if(pse < uniters.size() && uniters.get(pse).getType()!=Type.ACOLADF){
				if(!instruction()){
					return false;
				}
			}
		}
		if(pse < uniters.size() && uniters.get(pse).getType()==Type.FOR){
			pse++;
			if(!terminal(Type.PARO)){
				return false;
			}
			pse++;
			if(!terminal(Type.IDENT)){
				return false;
			}
			pse++;
			if(!terminal(Type.AFF)){
				return false;
			}
			pse++;
			if(!terminal(Type.INT)){
				return false;
			}
			pse++;
			if(!terminal(Type.TO)){
				return false;
			}
			pse++;
			if(!terminal(Type.INT)){
				return false;
			}
			pse++;
			if(!terminal(Type.STEP)){
				return false;
			}
			pse++;
			if(!terminal(Type.INT)){
				return false;
			}
			pse++;
			if(!terminal(Type.PARF)){
				return false;
			}
			pse++;
			if(!terminal(Type.ACOLADO)){
				return false;
			}
			pse++;
			if(!instruction()){
				return false;
			}
			if(!terminal(Type.ACOLADF)){
				return false;
			}
			pse++;
			if(pse < uniters.size() && uniters.get(pse).getType()!=Type.ACOLADF){
				if(!instruction()){
					return false;
				}
			}
		}
		
		if(pse < uniters.size() && uniters.get(pse).getType()==Type.READ){
			pse++;
			if(!terminal(Type.PARO)){
				return false;
			}
			pse++;
			if(!terminal(Type.IDENT)){
				return false;
			}
			pse++;
			if(!terminal(Type.PARF)){
				return false;
			}
			pse++;
			if(!terminal(Type.PVIRG)){
				return false;
			}
			pse++;
			if(pse < uniters.size() && uniters.get(pse).getType()!=Type.ACOLADF){
				if(!instruction()){
					return false;
				}
			}
		}
		if(pse < uniters.size() && uniters.get(pse).getType()==Type.WRITE){
			pse++;
			if(!terminal(Type.PARO)){
				return false;
			}
			pse++;
			if(!terminal(Type.CHAINE_CHAR)){
				return false;
			}
			pse++;
			while(terminal(Type.VIRG)){
				pse++;
				if(!terminal(Type.CHAINE_CHAR)){
					return false;
				}
				pse++;
			}
// n'est pas terminer comment afficher les valeur calculer ;
		//	pse++;
			if(!terminal(Type.PARF)){
				return false;
			}
			pse++;
			if(!terminal(Type.PVIRG)){
				return false;
			}
			pse++;
			if(pse < uniters.size() && uniters.get(pse).getType()!=Type.ACOLADF){
				if(!instruction()){
					return false;
				}
			}
		}
		
		// Ajouter Switch case case .... , do while ,  les fonction ....
		return true;
	}
	
	
	public boolean expression(){
		if(!simple_expression()){
			return false;
		}
	//	pse++;
		if(pse < uniters.size() && (uniters.get(pse).getType()==Type.EGAL ||uniters.get(pse).getType()==Type.SUP
				||uniters.get(pse).getType()==Type.SUPE||uniters.get(pse).getType()==Type.INF
				||uniters.get(pse).getType()==Type.INFE||uniters.get(pse).getType()==Type.DIFF))
		{
			pse++;
			if(!simple_expression()){
				return false;
			}
		}
		return true;
	}
	
	public boolean simple_expression(){
		if(pse < uniters.size() && (uniters.get(pse).getType()==Type.MOINS ||uniters.get(pse).getType()==Type.PLUS))
		{
			pse++;
		}
		if(!terme()){
			return false;
		}
		while(pse < uniters.size() && (uniters.get(pse).getType()==Type.MOINS ||uniters.get(pse).getType()==Type.PLUS||uniters.get(pse).getType()==Type.OR)){
			pse++;
			if(!terme()){
				return false;
			}
		}
		return true;
	}
	public boolean terme(){
		if(!facteur()){
			return false;
		}
		while(pse < uniters.size() && (uniters.get(pse).getType()==Type.MULT ||uniters.get(pse).getType()==Type.DIVE||uniters.get(pse).getType()==Type.DIV||uniters.get(pse).getType()==Type.AND)){
			pse++;
			if(!facteur()){
				return false;
			}
		}
		return true;
	}
	public boolean facteur(){
	    if(terminal(Type.IDENT)){
	            pse++;
	    }else if(terminal(Type.INT)){
	            pse++;
	    }else if(terminal(Type.FLOAT)){
	            pse++;
	    }else if(terminal(Type.CHAR)){
            pse++;
	    }else{
	            return false;
	    }
		return true;
	}
	public boolean variable(){
	/**	if(pse < uniters.size() && !(uniters.get(pse).getType()==Type.IDENT)){
			pse++;
			return false;
		}
		return true;*/
		return terminal(Type.IDENT);
	}
	
	// probleme de exprission n'est pas pointee sur le suivat
	public Boolean terminal(Type t/**,String s*/) {
		System.out.println(pse+"    "+ uniters.size());
		if(/**erreure=true &&*/ pse < uniters.size() && uniters.get(pse).getType()!=t){
			System.out.println(t.toString());					
			return false;
			//MsgERREURE=s;
		}
		if(pse >= uniters.size()){
			System.out.println(t.toString());					
			return false;
		}
		System.out.println(t.toString());
		return true;
	}
	public static void main(String[] args) {
		String texte2="\"ABDESSSAMAD\"";
		String texte1="% ProgrameName % { constant{int Xx=100 ; int Y=7; char Cc= \'a\';} "
				+ "variable {char c=\'t\';} main {for(i=0 to 12 step 1){x=x+i;}"
				+ "write(\"ABDESSSAMAD\");while(x<12){x=x-y;}}}";
		String texte = "% progrm % {constant { int i =7 ;char c = \'a\' ;"
				+ "string s=\"ABDESSSAMAD\" ;float f=7 ;boolean b=false;}"
				+ " variable {char c = \'a\' ;} main { for(i=0 to 10 step 1){} read (c);"
				+ "write(\"ABDESSSAMAD\",\"ABD ESSSA MAD\",\"ABDE SS SAMAD\");if( 3 ){}} }"
				+ "# {  XM / MS/CDFS GRG";
		Main AS1 = new Main(texte1);
		System.out.println(AS1.AnalyseProceduraleIndirecte());
		Main AS = new Main(texte);
		System.out.println(AS.AnalyseProceduraleIndirecte());
		Main AS2 = new Main(texte2);
		System.out.println(AS2.AnalyseProceduraleIndirecte());
	}
}