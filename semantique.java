/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Principale;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Vector;

import intrepreteur.Instruction;
/** 
 * @author Esserrati Mohammed Abdessamad
 */
public class semantique {
    public Vector<symbole> tablesymb=new Vector<symbole>();
    
    public Vector<symbole> getTableSymb() {
        return tablesymb;
    }
    public String affichertable(){
	    int i=0;String t="" ;String s= "";
	    Iterator<symbole> itr =tablesymb.iterator();
	    while(itr.hasNext()){
		    if(tablesymb.elementAt(i).adresse==-1){
		    		s+="\t"+tablesymb.elementAt(i).iden+"   ;    "
		    			+tablesymb.elementAt(i).indicetblecst+"\n";
		    }
		    if(tablesymb.elementAt(i).indicetblecst==-1){
		    		t+="\t"+tablesymb.elementAt(i).iden+"   ;    "
		    			+tablesymb.elementAt(i).adresse+"\n";
		        
		    }
		    itr.next();i++;
	    }
	    return("les variables:\n"+t+"les constantes:\n"+s);
    }
    public static void main(String[] args){
         semantique s=new semantique(); symbole b=new symbole("iden",Uniter.Type.INT);s.tablesymb.add(b);s.affichertable();
    }
    }