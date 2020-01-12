package Principale;
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
import java.util.ArrayList;


import java.util.Iterator;

import Principale.AnalyseurLexical;
import Principale.Uniter;
import Principale.Uniter.Type;
import Principale.cst;
import Principale.semantique;
import Principale.symbole;
import Principale.tablecsts;
import intrepreteur.Pile;

public class AnalyseurSyntaxiqueTeste {
	private Pile<Uniter> pile;
	private AnalyseurLexical Al;
        private semantique se;
        private tablecsts tcst;
	private ArrayList<Uniter> uniters = new ArrayList();
	private Uniter uniter ;
       int c=0;semantique s=new semantique();  tablecsts t23=new tablecsts();
         int indicetblecst=0;
         //ifidentificateur(pour la verification des types(sémantique)//
    private int pse=0;
    private boolean iffacteur=true;
    private boolean ifterme=true;
    private boolean ifsimplex=true;
    private boolean ifexp=true;
//valfact(pour les valeurs des constantes//
    private float valfact;
     private float valterme;
       private float valsexp;
     
//   private String Erreur;
//	private Uniter pse;
    
	public AnalyseurSyntaxiqueTeste(String texte) {
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
		iffacteur=true; ifterme=true ;ifexp=true ;ifsimplex=true;valfact=0;valterme=0;valsexp=0;
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
		s.affichertable(); t23.affichercst();return true;
						
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
            
		Uniter.Type t=uniters.get(pse).getType();
                if(pse < uniters.size() && t==Type.TYPE_INT||t==Type.TYPE_FLOAT){			
                    pse++;Uniter u=uniters.get(pse);
			if(!terminal(Type.IDENT)){
				return false;
			}
			pse++;
			if(!terminal(Type.AFF)){
				return false;
			}
			pse++;
			 Uniter.Type typeexp = expression();
		 if(  t==Type.TYPE_INT &&typeexp==Type.FLOAT ||(t!=Type.TYPE_INT&&t!=Type.TYPE_FLOAT)){
                      System.err.println("INCOMPATIBLE TYPES");return false;                       
                       }else{
                     String iden=u.getValeur();
                     Iterator itr =s.tablesymb.iterator();int w=0;int i=0;/* symbole s= (symbole) se.getTableSymb().elementAt(i);
            if(s.iden.equals(uniter.getValeur())){
             typefact = s.type;
            }*/
                         while(itr.hasNext()){
                             symbole h=  s.tablesymb.get(i);
                   if(h.iden.equals(iden)){
                     w=1;System.err.println("erreur semantique")  ;break;  
                   }itr.next();i++;}
                         if(w==1){return false;}
                    if(w==0){  
         s.getTableSymb().add(c,new symbole(iden,indicetblecst));  c++;
       t23.tablecst.add(indicetblecst,new cst(iden,valsexp));indicetblecst++;
                         }                                         
			if(!terminal(Type.PVIRG)){
				return false;
			}
			pse++;
			if(pse < uniters.size() && uniters.get(pse).getType()!=Type.ACOLADF){
				if(!decl_const()){
					return false;				}			}				}
	}return true;}
	public boolean decl_varibale(){
		 Uniter.Type t=uniters.get(pse).getType();
            if(pse < uniters.size() && uniters.get(pse).getType()==Type.TYPE_INT||uniters.get(pse).getType()==Type.TYPE_FLOAT){
		
                pse++;
                Uniter u=uniters.get(pse);
		if(!terminal(Type.IDENT)){
			return false;
		}
		pse++;
		if(!terminal(Type.AFF)){
			return false;
		}
		pse++;
                Uniter.Type typeexp = expression();
		 if( t==Type.TYPE_INT &&typeexp==Type.FLOAT ||(t!=Type.TYPE_INT&&t!=Type.TYPE_FLOAT)){                 
               ; System.err.println("INCOMPATIBLE TYPES");return false;
                       }else{
                     String iden=u.getValeur();  
                         Iterator itr =s.tablesymb.iterator();int w=0;int i=0;
                         while(itr.hasNext()){
                             symbole h=s.tablesymb.get(i);
                   if(h.iden.equals(iden)){
                     w=1;System.err.println("erreur semantique");break;    
                   }itr.next();i++;}
                         if(w==1){return false;}
                    if(w==0){ 
                    	
                    symbole sy=  new symbole(iden,typeexp);
                    symbole tr=new symbole("eza");
                    s.getTableSymb().add(c,sy);
                    sy.adresse=tr.hashCode();  
                    c++;            }                
 		
                if(c%2==0){
                   pse++;
                }
		if(!terminal(Type.PVIRG)){
			return false;
		}
                
		pse++;
		if(pse < uniters.size() && uniters.get(pse).getType()!=Type.ACOLADF){
			  if(!decl_varibale()){
				return false;
			}
		}
	}
		// possible de faire declaration comme int x=0,y,z; donc on va augmenter le nevaux de ...
	} ;return true;}
	
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
           Uniter u=uniters.get(pse);String iden=u.getValeur();  symbole h=null;
                         Iterator itr =s.tablesymb.iterator();int w=0;int i=0;
                         while(itr.hasNext()){
                              h=s.tablesymb.get(i);
                   if(h.iden.equals(iden)){
                     w=1;break;    
                   }itr.next();i++;}
                         if(w==0){return false;}
                         Uniter.Type j= h.type; 
		
			
                    pse++;
			if(!terminal(Type.AFF)){
				return false;
			}
			pse++;
                        Uniter.Type t=expression();
			if(ifexp==false){			//		simple_expression  ou   expression    ;
				return false;}
                                if(!j.equals(t)){
                                    return false;
                                }
		
			if(!terminal(Type.PVIRG)){
			;	return false;
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
                        Uniter.Type t=expression();
			if(ifexp==false){
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
			if(ifexp==false){
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
	
	
	public Uniter.Type expression(){
		
            Uniter.Type typsexp =  simple_expression();
            if(ifsimplex==false){
			ifexp= false;
		}else{
           
           
	//	pse++;
		if(pse < uniters.size() && (uniters.get(pse).getType()==Type.EGAL ||uniters.get(pse).getType()==Type.SUP
				||uniters.get(pse).getType()==Type.SUPE||uniters.get(pse).getType()==Type.INF
				||uniters.get(pse).getType()==Type.INFE||uniters.get(pse).getType()==Type.DIFF))
		{
			pse++;
                        simple_expression();
                        if(ifsimplex==false){
			ifexp= false;
		}}
		
	}return typsexp;}
	
	public Uniter.Type simple_expression(){
	
            Uniter.Type type =uniters.get(pse).getType();
      Uniter.Type typese = type ;
          	
            if(pse < uniters.size() && (uniters.get(pse).getType()==Type.MOINS ||uniters.get(pse).getType()==Type.PLUS))
		{
			pse++;
                         typese = terme();
                        valsexp=valterme;
                         
                         if(ifterme==false){
            ifsimplex=false;
        }}else{
                     Uniter.Type type1 = terme();
                     typese=type1;valsexp=valterme;
		float val1=valterme;
		
		while(pse < uniters.size() && (uniters.get(pse).getType()==Type.MOINS ||uniters.get(pse).getType()==Type.PLUS||uniters.get(pse).getType()==Type.OR)){
			Uniter.Type typeop = uniters.get(pse).getType();
                 pse++;
                     Uniter.Type type2 = terme();
                     float val2=valterme;
                      if(ifterme==false){
            ifsimplex=false;valsexp=0;break;          //might delete =0 after
            
        }if(typeop==Type.MOINS){
            valsexp-=val1-val2;
        }if(typeop==Type.PLUS){
            valsexp-=val1+val2;
        }
			if(type2.equals(Uniter.Type.INT) && typese.equals(Uniter.Type.INT)){
                typese = Uniter.Type.INT;
            }else{
                typese = Uniter.Type.FLOAT;
            }
		}
                         if(ifsimplex==false){
                             System.err.println("erreur sexp");
                         }}
		return typese; 
	}
	public Uniter.Type terme(){
		
    
                Uniter.Type type1 = facteur();valterme=valfact;
                Uniter.Type typeterme = type1;
                if(iffacteur==false){
			ifterme= false;
		}else{
                float val1=valfact;
		while(pse < uniters.size() && (uniters.get(pse).getType()==Type.MULT ||uniters.get(pse).getType()==Type.DIVE||uniters.get(pse).getType()==Type.DIV||uniters.get(pse).getType()==Type.AND)){
            Uniter.Type typeop =uniters.get(pse).getType();
                    pse++;
                    
                    Uniter.Type type2 = facteur();
                    float val2=valfact;
                    if(iffacteur==false){
			ifterme= false; System.err.println("erreur terme");valterme=0;break;		}
                    if(typeop==Type.DIV){
                            valterme/=val1/val2;}
                    if(typeop==Type.MULT){
                            valterme*=val1*val2; }              
		 if(type2.equals(Uniter.Type.INT) && typeterme.equals(Uniter.Type.INT)){
                typeterme = Uniter.Type.INT;
            }else{
                typeterme = Uniter.Type.FLOAT;
            }	
		}		                
	};  return typeterme;}
	public  Uniter.Type facteur() {
	  Uniter.Type typefact=null;symbole p=null;
           if(terminal(Type.IDENT)){String z=uniters.get(pse).getValeur();
            pse++; 
                 int i=0;
                Iterator itr = s.getTableSymb().iterator();
                 while(itr.hasNext()){
                    p= s.tablesymb.get(i);
            if(p.iden.equals(z)){
             typefact = p.type;break;
            }
                    itr.next();i++;
        }        
	            pse++;
	    }else if(terminal(Type.INT)){
            valfact =Integer.parseInt(uniters.get(pse).getValeur());      //parseint 
	            pse++;
                      typefact=Type.INT;
	    }else if(terminal(Type.FLOAT)){
	          valfact =Float.parseFloat(uniters.get(pse).getValeur());   //parsefloaat
                pse++;           
                typefact=Type.FLOAT;
	    }else{
	           iffacteur=false;
	    }
		return typefact;
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
		try{ 
            String texte2="\"ABDESSSAMAD\"";
		String texte1=/*"% ProgrameName % { constant{int Xx=100 ; int Y=7; char Cc= \'a\';} "
				+ */"% ProgrameName % {  "
				+ "variable {int e=5;}main{}}";//"constant {int c=100;}main{}}";
			/*	+ "write(\"ABDESSSAMAD\");while(x<12){x=x-y;}}}";
		String texte = "% progrm % {constant { int i =7 ;char c = \'a\' ;"
				+ "string s=\"ABDESSSAMAD\" ;float f=7 ;boolean b=false;}"
				+ " c {char c = \'a\' ;} main { for(i=0 to 10 step 1){} read (c);"
				+ "write(\"ABDESSSAMAD\",\"ABD ESSSA MAD\",\"ABDE SS SAMAD\");if( 3 ){}} }"
				+ "# {  XM / MS/CDFS GRG";*/
               
		AnalyseurSyntaxique AS1 = new AnalyseurSyntaxique(texte1);
		System.out.println(AS1.AnalyseProceduraleIndirecte());
		/*AnalyseurSyntaxique AS = new AnalyseurSyntaxique(texte);
		System.out.println(AS.AnalyseProceduraleIndirecte());
		AnalyseurSyntaxique AS2 = new AnalyseurSyntaxique(texte2);
		System.out.println(AS2.AnalyseProceduraleIndirecte());*/
              
    
               
               
	}catch(Exception e){
            System.err.println(e);
        }}
}
/**
 *
 * @author hp
 */


/**
% ProgrameName % {
canstant{
	int Xx=100 ; int Y=7;char Cc='a';
}

variable{
	int x,y;
	char v,c='t';
}

main {
	for(i=0 to 12 step 1){
		x=x+i;
	}
	write(" for i ");
	while(x<12){
		x=x-y;
	}
}
}
*/
/**
 * @author 
 * @version 1.0
 */
 
