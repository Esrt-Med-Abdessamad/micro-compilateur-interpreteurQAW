package Principale;
/**
 * @author Esserrati Mohammed Abdessamad
 */
import java.awt.FileDialog;
import java.awt.Frame;
import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Iterator;

import javax.swing.JOptionPane;

import org.omg.CORBA.portable.ValueOutputStream;

import Principale.AnalyseurLexical;
import Principale.Uniter;
import Principale.Uniter.Type;
import Principale.semantique;
import Principale.tablecsts;
import intrepreteur.Instruction;
import intrepreteur.Pile;

public class AnalyseurSyntaxique {
	private Pile<Uniter> pile;
	private AnalyseurLexical Al;
	public ArrayList<Uniter> uniters = new ArrayList<Uniter>();
	private int pse=0;int adr=0;
	private Uniter uniter ;	
    private  int c=0;	// indice pour les symbole ;
    public semantique s=new semantique();
    public tablecsts t23=new tablecsts();
    private  int indicetblecst=0;
  //ifidentificateur(pour la verification des types(sémantique)//
    
    private boolean iffacteur=true;
    private boolean ifterme=true;
    private boolean ifsimplex=true;
    private boolean ifexp=true;
//valfact(pour les valeurs des constantes//
    
    private float valfact;
    private float valterme;
    private float valsexp;
     //PARTIE ERREURS
 private String Erreur="Erreur : ";
 int erreurif;int erreurwhile;int erreuraff;
//	private Uniter pse;
    
    // pour la generation de code :
    private ArrayList<Instruction> vic = new ArrayList<>();
    private int CO = 0;
    private int nonCONSTANT=0;
    
	public AnalyseurSyntaxique(String texte) {
		Al = new AnalyseurLexical(texte);
        uniter = Al.lireUniter();
        while(!uniter.getType().equals(Uniter.Type.NEAT)){
        	System.out.print(uniter.getType().toString()+"\t");
            uniters.add(uniter);
            uniter = Al.lireUniter();
        }System.out.println();
			
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
            JOptionPane.showMessageDialog(null, Erreur);
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
		vic.add(new Instruction(intrepreteur.Instruction.Type.END));
		System.out.println(s.affichertable()); 
		System.out.println(t23.affichercst());
				
		System.out.println("Programme correct!"+"	"+pse+"		"+(uniters.size()-1));
		save();
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
				Erreur=Erreur+"erreur syntaxique(declaration constante)";return false;
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
			Erreur=Erreur+"erreur syntaxique(declaration variable)";	return false;
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
         eviteCommentaires();   
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
         	 Iterator<symbole> itr =s.tablesymb.iterator();int w=0;int i=0;
             while(itr.hasNext()){
                symbole h=s.tablesymb.get(i);
                  if(h.iden.equals(iden)){
                    w=1;System.err.println("erreur semantique");break;    
                  }itr.next();i++;}
                  if(w==1){return false;}
                   if(w==0){
       
           // ArrayList E= new ArrayList();E.
        	symbole sy	= new symbole(iden,indicetblecst,typeexp);
            s.tablesymb.add(c,sy);  c++;
            t23.tablecst.add(indicetblecst,new cst(iden,valsexp));
            indicetblecst++;
                   }                 
			if(!terminal(Type.PVIRG)){
				return false;
			}
			pse++;
			eviteCommentaires();
			if(pse < uniters.size() && uniters.get(pse).getType()!=Type.ACOLADF){
				if(!decl_const()){
					return false;				}			}				}
	}return true;}
	public boolean decl_varibale(){
		nonCONSTANT=1;
		Uniter.Type t=uniters.get(pse).getType();
		eviteCommentaires();
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
             Erreur=Erreur+"erreur semantique(incomptaible types)(declaration variable)"; System.err.println("INCOMPATIBLE TYPES");return false;
        }else{
        	String iden=u.getValeur();  
            Iterator<symbole> itr =s.tablesymb.iterator();int w=0;int i=0;
            while(itr.hasNext()){
                 symbole h=s.tablesymb.get(i);
                 if(h.iden.equals(iden)){
                	 w=1;System.err.println("erreur semantique");
                	 break;    
                 }itr.next();i++;}
                   if(w==1){return false;}
                    if(w==0){
                    	symbole sy	=  new symbole(iden,typeexp);
                    	
                    	s.tablesymb.add(c,sy);
                    	if(typeexp.equals(Uniter.Type.INT)){
                    		sy.adresse=adr;adr++;
                    	}if(typeexp.equals(Uniter.Type.FLOAT)){
                    		sy.adresse=adr;adr=adr+2;
                    	}
                    	c++;
                    	//System.out.println(tr.hashCode());
                    	vic.add(new Instruction(intrepreteur.Instruction.Type.STORE,String.valueOf(sy.adresse)));
                    }                
 		
              	  
		if(!terminal(Type.PVIRG)){
			return false;
		}
                
		pse++;
		eviteCommentaires();
		if(pse < uniters.size() && uniters.get(pse).getType()!=Type.ACOLADF){
			  if(!decl_varibale()){
				return false;
			}
		}
	}
		// possible de faire declaration comme int x=0,y,z; donc on va augmenter le nevaux de ...
	} 
     return true;
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
		eviteCommentaires();
		if(pse < uniters.size() && uniters.get(pse).getType()!=Type.ACOLADF){
			if(!instruction()){
				if(erreurif==1){
					Erreur=Erreur+"erreur syntaxique(if)";
				}if(erreuraff==1){
					Erreur=Erreur+"erreur syntaxique(aff)";
				}if(erreurwhile==1){
					Erreur=Erreur+"erreur syntaxique(while)";
				}return false;
			}
		}
		if(!terminal(Type.ACOLADF)){
			return false;
		}
		return true;
	}
	public boolean instruction(){
		eviteCommentaires();
		if(pse < uniters.size() && uniters.get(pse).getType()==Type.IDENT){
			erreuraff=1; Uniter u=uniters.get(pse);String iden=u.getValeur();  symbole h=null;
           Iterator<symbole> itr =s.tablesymb.iterator();int w=0;int i=0;
           while(itr.hasNext()){
              h=s.tablesymb.get(i);
              if(h.iden.equals(iden)&& s.tablesymb.elementAt(i).adresse!=-1){//
            	  w=1;break;    
              }
              if(h.iden.equals(iden)&&h.adresse==-1){
            	  Erreur=Erreur+"on ne change pas la valeur de la constante mr kabaj   ";return false; 
              }
              itr.next();
              i++;
           }
           if(w==0){Erreur=Erreur+"identificateur non initialisé";return false;
           }
           
           Uniter.Type j= h.type; 
           pse++;
           if(!terminal(Type.AFF)){
        	   return false;
           }
           pse++;
           Uniter.Type t=expression();
           if(ifexp==false){			//		simple_expression  ou   expression    ;
        	   return false;
			}
            if(!j.equals(t)){
            	return false;
            }
        	vic.add(new Instruction(intrepreteur.Instruction.Type.STORE,String.valueOf(h.adresse)));
			if(!terminal(Type.PVIRG)){
				return false;
			}
			pse++;
			eviteCommentaires();
			if(pse < uniters.size() && uniters.get(pse).getType()!=Type.ACOLADF){
				if(!instruction()){
					Erreur=Erreur+"erreur syntaxique";return false;
				}
			}
		}
		if(pse < uniters.size() && uniters.get(pse).getType()==Type.IF){
			erreurif=1;
			pse++;
			if(!terminal(Type.PARO)){
				;return false;
			}
			pse++;
            Uniter.Type t=expression();
            
            if (!t.equals(Uniter.Type.BOOLEAN)) {
            	System.err.println("expression non booleene /// if");
            	Erreur=Erreur+"erreur sémantique :expression non booleene /// if";
            	return false;
			}
            
			if(ifexp==false){
				Erreur=Erreur+"erreur syntaxique:instruction if(au niveau de l'expression)";
			}
			if(!terminal(Type.PARF)){
				return false;
			}
			int co1=vic.size();
			pse++;
			if(!terminal(Type.ACOLADO)){
				return false;
			}
			pse++;
			if(!instruction()){
				Erreur=Erreur+"erreur syntaxique";return false;
			}
			if(!terminal(Type.ACOLADF)){
				return false;
			}
			pse++;
			eviteCommentaires();
			if(pse < uniters.size() && uniters.get(pse).getType()==Type.ELSE){
				
				int co2=vic.size();
				
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
				
				vic.add(co1, new Instruction(intrepreteur.Instruction.Type.JZERO,String.valueOf(co2+2)));
				vic.add(co2+1, new Instruction(intrepreteur.Instruction.Type.JUMP,String.valueOf(vic.size())));
				
			}else{
				vic.add(co1, new Instruction(intrepreteur.Instruction.Type.JZERO,String.valueOf(vic.size()+1)));
			}
			eviteCommentaires();
			if(pse < uniters.size() && uniters.get(pse).getType()!=Type.ACOLADF){
				if(!instruction()){
					return false;
				}
			}
			}
		if(pse < uniters.size() && uniters.get(pse).getType()==Type.WHILE){
			erreurwhile=1;pse++;
			if(!terminal(Type.PARO)){
				return false;
			}
			pse++;
			int co11=vic.size();
			
			Uniter.Type t=expression();
			if (!t.equals(Uniter.Type.BOOLEAN)) {
            	System.err.println("expression non booleene ///while");
            	return false;
			}
			if(ifexp==false){
				return false;
			}
			if(!terminal(Type.PARF)){
				return false;
			}
			int co22=vic.size();
			
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
			vic.add(new Instruction(intrepreteur.Instruction.Type.JUMP,String.valueOf(co11)));
			vic.add(co22, new Instruction(intrepreteur.Instruction.Type.JZERO, String.valueOf(vic.size()+1)));
			pse++;
			eviteCommentaires();
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
			eviteCommentaires();
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
			pse++;Uniter u=uniters.get(pse);
			if(!terminal(Type.IDENT)){
				return false;
			}//
			 String iden=u.getValeur();  symbole h=null;
	           Iterator<symbole> itr =s.tablesymb.iterator();int w=0;int i=0;
	           while(itr.hasNext()){
	              h=s.tablesymb.get(i);
	              if(h.iden.equals(iden)&& s.tablesymb.elementAt(i).adresse!=-1){//
	            	  w=1;break;    
	              }
	              itr.next();
	              i++;
	           }
	           if(w==0){
	        	   return false;
	           }
	           vic.add(new Instruction(intrepreteur.Instruction.Type.READ,String.valueOf(h.adresse) ));
	          // Uniter.Type j= h.type; 
			pse++;
			if(!terminal(Type.PARF)){
				return false;
			}
			pse++;
			if(!terminal(Type.PVIRG)){
				return false;
			}
                        
			pse++;
			eviteCommentaires();
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
			eviteCommentaires();
			if (pse < uniters.size() && uniters.get(pse).getType()==Type.INT) {
				vic.add(new Instruction(intrepreteur.Instruction.Type.WRITEC, uniters.get(pse).getValeur()));
			}else
				if (pse < uniters.size() && uniters.get(pse).getType()==Type.FLOAT) {
					vic.add(new Instruction(intrepreteur.Instruction.Type.WRITEC, uniters.get(pse).getValeur()));
				}else
					if (pse < uniters.size() && uniters.get(pse).getType()==Type.IDENT) {
						Uniter u =uniters.get(pse);
						String iden=u.getValeur();  symbole h=null;
				           Iterator<symbole> itr =s.tablesymb.iterator();int w=0;int i=0;
				           while(itr.hasNext()){
				              h=s.tablesymb.get(i);
				              if(h.iden.equals(iden)){//
				            	  w=1;break;    
				              }
				              itr.next();
				              i++;
				           }
				           if(w==0){
				        	   return false;
				           }
				           if(s.tablesymb.elementAt(i).adresse!=-1){
					           vic.add(new Instruction(intrepreteur.Instruction.Type.WRITE, String.valueOf(h.adresse)));}
					           else{vic.add(new Instruction(intrepreteur.Instruction.Type.WRITEC, String.valueOf(t23.tablecst.get(h.indicetblecst).valeur)));}
						}else
						if(pse < uniters.size() && uniters.get(pse).getType()==Type.CHAINE_CHAR){
							vic.add(new Instruction(intrepreteur.Instruction.Type.WRITEC, uniters.get(pse).getValeur().toString()));
							//System.err.println(uniters.get(pse).getValeur());
						}else
							return false;
			
			pse++;
			eviteCommentaires();
			while(pse < uniters.size() && uniters.get(pse).getType()==Type.VIRG){
				pse++;
				if (pse < uniters.size() && uniters.get(pse).getType()==Type.INT) {
					vic.add(new Instruction(intrepreteur.Instruction.Type.WRITEC, uniters.get(pse).getValeur()));
				}else
					if (pse < uniters.size() && uniters.get(pse).getType()==Type.FLOAT) {
						vic.add(new Instruction(intrepreteur.Instruction.Type.WRITEC, uniters.get(pse).getValeur()));
					}else
						if (pse < uniters.size() && uniters.get(pse).getType()==Type.IDENT) {
							Uniter u =uniters.get(pse);
							String iden=u.getValeur();  symbole h=null;
					           Iterator<symbole> itr =s.tablesymb.iterator();int w=0;int i=0;
					           while(itr.hasNext()){
					              h=s.tablesymb.get(i);
					              if(h.iden.equals(iden) ){//
					            	  w=1;break;    
					              }
					              itr.next();
					              i++;
					           }
					           if(w==0){
					        	   return false;
					           }
					           if(s.tablesymb.elementAt(i).adresse!=-1){
					           vic.add(new Instruction(intrepreteur.Instruction.Type.WRITE, String.valueOf(h.adresse)));}
					           else{vic.add(new Instruction(intrepreteur.Instruction.Type.WRITEC, String.valueOf(t23.tablecst.get(h.indicetblecst).valeur)));}
						}else
							if(pse < uniters.size() && uniters.get(pse).getType()==Type.CHAINE_CHAR){
								vic.add(new Instruction(intrepreteur.Instruction.Type.WRITEC, uniters.get(pse).getValeur()));
							}else
								return false;
				pse++;
				eviteCommentaires();
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
			pse++;eviteCommentaires();
			if(pse < uniters.size() && uniters.get(pse).getType()!=Type.ACOLADF){
				if(!instruction()){
					return false;
				}
			}
		}
		erreurif=0;erreurwhile=0;erreuraff=0;
		// Ajouter Switch case case .... , do while ,  les fonction ....
		return true;
	}
	
	
	public Uniter.Type expression(){
		ifexp=true;
        Uniter.Type typsexp =  simple_expression();
        if(ifsimplex==false){
			ifexp= false;
		}else{
			eviteCommentaires();
		if(pse < uniters.size() && (uniters.get(pse).getType()==Type.EGAL ||uniters.get(pse).getType()==Type.SUP
			||uniters.get(pse).getType()==Type.SUPE||uniters.get(pse).getType()==Type.INF
			||uniters.get(pse).getType()==Type.INFE||uniters.get(pse).getType()==Type.DIFF))
		{	Type u=uniters.get(pse).getType();
			pse++;
			simple_expression();
            if(ifsimplex==false){
            	ifexp= false;Erreur=Erreur+"erreur syntaxique(2 eme partie de l'expression)";
            }
            typsexp=Uniter.Type.BOOLEAN;
            if(u==Type.SUPE){
            	vic.add(new Instruction(intrepreteur.Instruction.Type.SUPE));
            }
            if(u==Type.SUP){
            	vic.add(new Instruction(intrepreteur.Instruction.Type.SUP));
            }
            if(u==Type.INF){
            	vic.add(new Instruction(intrepreteur.Instruction.Type.INF));
            }
            if(u==Type.INFE){
            	vic.add(new Instruction(intrepreteur.Instruction.Type.INFE));
            }
            if(u==Type.EGAL){
            	vic.add(new Instruction(intrepreteur.Instruction.Type.EQUAL));
            }
            if(u==Type.DIFF){
            	vic.add(new Instruction(intrepreteur.Instruction.Type.NEQUAL));
            }
        }
		
	}return typsexp;}
	
	public Uniter.Type terme(){
		Uniter.Type typeterme = null;
		if(iffacteur==false){
			ifterme= false;
		}else{
			Uniter.Type type1 = facteur();
			valterme=valfact;
			typeterme = type1;
			float val1=valfact;
			eviteCommentaires();
			while(pse < uniters.size() && (uniters.get(pse).getType()==Type.MULT ||uniters.get(pse).getType()==Type.DIVE||uniters.get(pse).getType()==Type.DIV||uniters.get(pse).getType()==Type.AND)){
            Uniter.Type typeop =uniters.get(pse).getType();
                    pse++;
                    
                    Uniter.Type type2 = facteur();
                    float val2=valfact;
                    if(iffacteur==false){
			ifterme= false; Erreur=Erreur+"erreur terme(second facteur non reconnue)";System.err.println("erreur terme");valterme=0;break;		
			}if(nonCONSTANT==1){
				if(typeop==Type.DIV){
	            	 vic.add(new Instruction(intrepreteur.Instruction.Type.DIV));}
            if(typeop==Type.MULT){
           	 vic.add(new Instruction(intrepreteur.Instruction.Type.MUL));; } 
			}else{
                    if(typeop==Type.DIV){
                            valterme=valterme/val2;}
                    if(typeop==Type.MULT){
                            valterme=valterme*val2; } }             
		 if(type2.equals(Uniter.Type.INT) && typeterme.equals(Uniter.Type.INT)){
                typeterme = Uniter.Type.INT;
            }else{
                typeterme = Uniter.Type.FLOAT;
            }
		 eviteCommentaires();
		}		                
	};  return typeterme;}
	
	public Uniter.Type simple_expression(){
		Uniter.Type typeop = null;
		ifsimplex=true;
		Uniter.Type typese = null;
	   eviteCommentaires();   	
	  if(pse < uniters.size() && (uniters.get(pse).getType()==Type.MOINS ||uniters.get(pse).getType()==Type.PLUS))
		{typeop = uniters.get(pse).getType();pse++;}
	  if(ifterme==false){
	        ifsimplex=false; System.err.println("erreur sexp");
	   }else{
	   Uniter.Type type1 = terme();
	   typese=type1;
	   if(typeop==Type.MOINS){
		   valsexp=-valterme;
	   }else{
		   valsexp=+valterme;
	   }
	   eviteCommentaires();
	   while(pse < uniters.size() && (uniters.get(pse).getType()==Type.MOINS ||uniters.get(pse).getType()==Type.PLUS||uniters.get(pse).getType()==Type.OR)){
			typeop = uniters.get(pse).getType();
	        pse++;
	        Uniter.Type type2 = terme();
	        float val2=valterme;
	        if(ifterme==false){
	        	
	        	ifsimplex=false;Erreur=Erreur+"erreur syntaxique(simpleexp)(2 eme terme de la simple expression non reconnue)";System.err.println("erreur sexp");
	        	break;      
	        }if(nonCONSTANT!=0){
				if(typeop==Type.MOINS){
	            	 vic.add(new Instruction(intrepreteur.Instruction.Type.SUB));}
	       if(typeop==Type.PLUS){
	      	 vic.add(new Instruction(intrepreteur.Instruction.Type.ADD));; } 
			}else{
	        if(typeop==Type.MOINS){
	        	valsexp =valsexp-val2;
	        }
	        if(typeop==Type.PLUS){
	        	valsexp =valsexp+val2;
	        }}
			if(type2.equals(Uniter.Type.INT) && typese.equals(Uniter.Type.INT)){
	            typese = Uniter.Type.INT;
	        }else{
	            typese = Uniter.Type.FLOAT;
	        }
			eviteCommentaires();
		}
	    }
		return typese; 
	}

	public  Uniter.Type facteur() {
	  Uniter.Type typefact=null;symbole p=null;
	  //terminal(Type.IDENT)
	  eviteCommentaires();
      if(pse < uniters.size() && (uniters.get(pse).getType()==Type.IDENT)){
    	  String z=uniters.get(pse).getValeur();
          int i=0;int indice=-1;
          Iterator<symbole> itr = s.tablesymb.iterator();
          while(itr.hasNext()){
        	  p= s.tablesymb.get(i);
        	  if(p.iden.equals(z)){
        		  typefact = p.type;
        		  indice= p.indicetblecst;
        		  break;
        	  }
              itr.next();i++;//System.out.println("i=  	"+i);
          }
          if(typefact==null){
        	  Erreur=Erreur+"err : l'identificateur "+z+" n'est pas declaré";
        	  System.err.println("err : l'identificateur "+z+" n'est pas declaré");
        	  iffacteur=false;
          }
          if (indice!=-1) {
        	  valfact=t23.tablecst.get(indice).valeur;
          }else{
        	  vic.add(new Instruction(intrepreteur.Instruction.Type.LOAD,String.valueOf(p.adresse)));
        	  pse++;
        	  return typefact;
          }
	      pse++;
	    }else if(pse < uniters.size() && (uniters.get(pse).getType()==Type.INT)){
            valfact =Integer.parseInt(uniters.get(pse).getValeur());      //parseint 
	        pse++;
            typefact=Type.INT;
	    }else if(pse < uniters.size() && (uniters.get(pse).getType()==Type.FLOAT)){
	         valfact =Float.parseFloat(uniters.get(pse).getValeur());   //parsefloaat
             pse++;
             typefact=Type.FLOAT;
	    }else{
	           iffacteur=false;Erreur=Erreur+"erreur syntaxique(facteur non reconnue)";
	    }
      if(nonCONSTANT ==1){
    	  vic.add(new Instruction(intrepreteur.Instruction.Type.LOADC,String.valueOf(valfact)));
      }
      return typefact;
	}
	
	public Boolean terminal(Type t/**,String s*/) {
		
		eviteCommentaires();
		if(pse < uniters.size() && uniters.get(pse).getType()!=t){
			System.err.println(pse+"    "+ uniters.size());
			System.err.println(t.toString());
			Erreur=Erreur+t.toString()+"   ";
			return false;
			//MsgERREURE=s;
		}
		if(pse >= uniters.size()){
			System.err.println(pse+"  >  "+ uniters.size());
			System.err.println(t.toString());
			Erreur=Erreur+"   "+t.toString()+"   ";
			return false;
		}
		System.out.println(pse+"    "+ uniters.size());
		System.out.println(t.toString());
		return true;
	}
	
	public void eviteCommentaires(){//ajouter avant de tester toutes les uniter l'e
		while(pse < uniters.size() && uniters.get(pse).getType()==Type.COMMENTAIRE){
			pse++;
		}
	}
	
	
	public static void main(String[] args) {
		try{ 
			String texte1="% ProgrameName % { constant{int ii=3*3+4;int iii=3+4+ii; } "
					+ "variable{int e=3+5 ;int j=0;}main{j=3; write(\" ABDESSSAMAD \",j);if( ii==1){j  =/*dd*/e +3+ii/3;}else{j=j+1*iii; }while(ii==1){j=j+1*iii; }}}";
			//contre exemple	int fg=9;	int fghj=3+fg/3;
			AnalyseurSyntaxique AS1 = new AnalyseurSyntaxique(texte1);
			System.out.println(AS1.AnalyseProceduraleIndirecte());
		}catch(Exception e){
            System.err.println(e);
        }
		}
		/*String texte = "% progrm % {constant { int i =7 ;char c = \'a\' ;"
					+ "string s=\"ABDESSSAMAD\" ;float f=7 ;boolean b=false;}"
					+ " c {char c = \'a\' ;} main { for(i=0 to 10 step 1){} read (c);"
					+ ";if( 3 ){}} }"
					+ "# {  XM / MS/CDFS GRG";
		 * AnalyseurSyntaxique AS = new AnalyseurSyntaxique(texte);
		System.out.println(AS.AnalyseProceduraleIndirecte());*/
	public void save(){
		String txt="";
		for (int i = 0; i < vic.size(); i++) {
			
			//System.out.println(i+" - "+vic.get(i).toString1());
			//txt+=vic.get(i).getOperation().toString()+" "+vic.get(i).getOperant().toString()+"\n";
			txt=txt+vic.get(i).toString1();
	
		}
		
			try{
				File ff=new File("C:\\Users\\A\\Desktop\\comp\\source\\vic.txt"); // définir l'arborescence
				ff.createNewFile();
				FileWriter ffw=new FileWriter(ff);
				ffw.write(txt);  // écrire une ligne dans le fichier resultat.txt
				ffw.write("\r\n"); // forcer le passage à la ligne
				ffw.close(); // fermer le fichier à la fin des traitements
				} catch (Exception e) {}
		System.out.print(txt);
			
	
	}
public  void saveAs(){
		String txt="";
		for (int i = 0; i < vic.size(); i++) {
			
			//System.out.println(i+" - "+vic.get(i).toString1());
			//txt+=vic.get(i).getOperation().toString()+" "+vic.get(i).getOperant().toString()+"\n";
			txt=txt+vic.get(i).toString1();
	
		}
		Frame yourJFrame = null;
		FileDialog fd = new FileDialog(yourJFrame, "Choose a file", FileDialog.SAVE);
		fd.setDirectory("C:\\Users\\A\\Desktop\\comp\\source\\");
		fd.setFile("*.txt");
		fd.setVisible(true);
		String filename = fd.getFile();
		if (filename != null){
			try{
				File ff=new File("C:\\Users\\A\\Desktop\\comp\\source\\" + filename+".txt"); // définir l'arborescence
				ff.createNewFile();
				FileWriter ffw=new FileWriter(ff);
				ffw.write(txt);  // écrire une ligne dans le fichier resultat.txt
				ffw.write("\r\n"); // forcer le passage à la ligne
				ffw.close(); // fermer le fichier à la fin des traitements
				}
			catch (Exception e) {
				
			}
		
			
	}
	}
	
}