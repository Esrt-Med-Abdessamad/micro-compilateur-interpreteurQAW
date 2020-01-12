package Principale;

import java.util.ArrayList;
/** 
 * @author Esserrati Mohammed Abdessamad
 */
public class tablecsts {
    public ArrayList<cst> tablecst=new ArrayList<>();
    public String affichercst(){
    	String s="table csts :";
    	for (int i = 0; i < tablecst.size(); i++) {
			s+="\n\t"+tablecst.get(i).iden.toString()+"\t"+tablecst.get(i).valeur;
		}
    	return s;
    }
}
class cst {
    public cst(String iden2, float valsexp) {
		iden=iden2;valeur=valsexp;
	}
	String iden;
    float valeur;
}
