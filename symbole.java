package Principale;
/** 
 * @author Esserrati Mohammed Abdessamad
 */
public class symbole {
	
    public String iden;
    public int adresse=-1;
    public Uniter.Type type;
    public int indicetblecst=-1; 		//Indcs: indice dans la table des constantes
    int c=1;
    
    public String getIden() {
        return iden;
    }
     public symbole(String iden){
         this.iden=iden;
     }
    public symbole(String iden,Uniter.Type type){  
        this.iden=iden;
        this.type=type;
        this.indicetblecst=-1;
    }
    public symbole(String iden,int indicetblecst,Uniter.Type type){
        this.iden=iden;
        this.indicetblecst=indicetblecst;  
        this.type=type;
    }
    public symbole(String iden,int indicetblecst){
        this.iden=iden;
        this.indicetblecst=indicetblecst;  
        this.type=type;
    }
   
}

