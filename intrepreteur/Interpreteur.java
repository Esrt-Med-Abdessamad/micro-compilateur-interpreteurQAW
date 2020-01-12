package intrepreteur;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

import javax.swing.JOptionPane;
/**
Ce projet est développé par Un étudiant ingénieur informatique en deuxième année
Projet :
Réalisation d’un micro-compilateur/interpréteur

Le sujet
Le projet consiste à réaliser un micro compilateur et un micro-interpréteur associé.
Etapes à suivre :
	Déterminer le langage source ; déterminer les niveaux lexical, syntaxique et sémantique. Pour chaque niveau, il faut déterminer les catégories associées (catégories lexicales, catégories syntaxiques, conditions ou contraintes sémantiques à vérifier, etc.) et il faut fournir leur définition selon la grammaire appropriée (grammaire régulière pour le niveau lexical, grammaire hors contexte pour le niveau syntaxique).
	Réalisation de l’analyseur lexical, et plus spécifiquement de la procédure Lire_unite() qui reconnait la prochaine unité et sa catégorie.
	Réalisation de l’analyseur lexico-syntaxique qui intègre et utilise la procédure Lire_unite() ;
	Réalisation de l’analyseur sémantique qui correspond à « une couche » qu’on ajoute à l’analyseur lexico-syntaxique
	Réalisation de la génération du code qui correspond aussi à une couche qu’on ajoute à l’analyseur lexico-syntaxico-sémantique.
	Réalisation de l’interpréteur. Cette étape est indépendante des étapes antérieures.

Organisation
Le projet est à réaliser en groupes, chaque groupe doit être composé de 3 binômes ; un binôme pour l’analyse, un autre pour la génération et un troisième pour l’interpréteur.
**/
/** 
 * @author Esserrati Mohammed Abdessamad
 */
public class Interpreteur {
	
 	private HashMap<Integer,String> pileStatique = new HashMap<>();
    Pile<String> pile = new Pile<>();
    ArrayList<Instruction> vic = new ArrayList<>();
    int CO = 0;
    int Boucle_infinie=0;
    
    public  Interpreteur(String text) {
    	initialise(text);
    	Instruction instructionCorant ;
    	//System.out.println(vic.toString());
        while(!vic.get(CO).getOperation().equals(Instruction.Type.END) ){
        	//System.out.println(CO);
        	if(Boucle_infinie>=1000){
        		JOptionPane.showMessageDialog(null, "Attention:\n Boucle infinie");break;
        	}
        	Boucle_infinie++;
            instructionCorant = vic.get(CO) ;
            CO++;
            switch(instructionCorant.getOperation()){
                case LOAD : 
               // 	System.out.println(pileStatique.toString());
                    pile.empiler(pileStatique.get(Integer.valueOf(instructionCorant.getOperant())));
               //     System.out.println(pile.toString());
                    break;
                case LOADC :
                    int operant = Integer.parseInt(instructionCorant.getOperant());
                    pile.empiler(Integer.toString(operant));
           //         System.out.println(pile.sommet()+"\t"+pile.toString());
                    break;
                case ADD :
                        Integer o1 = Integer.decode(pile.depiler());
                        Integer o2 = Integer.decode(pile.depiler());
                        int c = o1 + o2;
                        pile.empiler(Integer.toString(c));
                    break;
                case SUB :
                    	Integer o11 = Integer.decode(pile.depiler());
                    	Integer o21 = Integer.decode(pile.depiler());
                    	int c1 = o11 - o21;
                    	pile.empiler(Integer.toString(c1));
                    break;
                case MUL :
                	Integer o31 = Integer.decode(pile.depiler());
                	Integer o32 = Integer.decode(pile.depiler());
                	int c2 = o31 * o32;
                	pile.empiler(Integer.toString(c2));
             //   	System.out.println(pile.toString());
                break;
                case DIV :
                	Integer o41 = Integer.decode(pile.depiler());
                	Integer o42 = Integer.decode(pile.depiler());
                	int c3 = o41 / o42;
                	pile.empiler(Integer.toString(c3));
                break;
                case STORE :
                    pileStatique.put(Integer.valueOf(instructionCorant.getOperant()),pile.depiler());
                //    System.out.println(pileStatique.toString());
                    break;
                case WRITE :
                    int address = Integer.parseInt(instructionCorant.getOperant());
                    System.out.println(pileStatique.get(address));
                    JOptionPane.showMessageDialog(null, pileStatique.get(address));
                    break;
                case WRITEC :
                    System.out.println(instructionCorant.getOperant());
                    JOptionPane.showMessageDialog(null, instructionCorant.getOperant());
                    break;
                case JUMP :
                    CO = Integer.parseInt(instructionCorant.getOperant());
                    break;
                case READ :
                //	Scanner sc = new Scanner(System.in);
                //	String read = sc.nextLine();
                	String read=JOptionPane.showInputDialog("Entrez la valeur:");
     
                	 pileStatique.put(Integer.valueOf(instructionCorant.getOperant()),read);
              //       System.out.println(pileStatique.toString());
                    break;
                case EQUAL :
                    int x1 = Integer.decode(pile.depiler());
                    int x2 = Integer.decode(pile.depiler());
                    if(x1 == x2){
                        pile.empiler(Integer.toString(1));
                    }else {
                        pile.empiler(Integer.toString(0));
                    }
                    break;
                case SUP :
                    int x41 = Integer.decode(pile.depiler());
                    int x42 = Integer.decode(pile.depiler());
                    if(x41 > x42){
                        pile.empiler(Integer.toString(1));
                    }else {
                        pile.empiler(Integer.toString(0));
                    }
                    break;
                case INF :
                //	System.out.println("nice");
                    int x51 = Integer.decode(pile.depiler());
                    int x52 = Integer.decode(pile.depiler());
                    if(x51 < x52){
                        pile.empiler(Integer.toString(1));
                    }else {
                        pile.empiler(Integer.toString(0));
                    }
                  //  System.out.println(pile.toString());
                    break;
                case INFE :
                    int x71 = Integer.decode(pile.depiler());
                    int x72 = Integer.decode(pile.depiler());
                    if(x71 <= x72){
                        pile.empiler(Integer.toString(1));
                    }else {
                        pile.empiler(Integer.toString(0));
                    }
                    break;
                case SUPE :
                    int x61 = Integer.decode(pile.depiler());
                    int x62 = Integer.decode(pile.depiler());
                    if(x61 >= x62){
                        pile.empiler(Integer.toString(1));
                    }else {
                        pile.empiler(Integer.toString(0));
                    }
                    break;
                case JZERO :
                    int i = Integer.decode(pile.depiler());
                    if(i==0){
                        CO = Integer.parseInt(instructionCorant.getOperant());
                    }
                    break;
                case NEQUAL :
                	int x611 = Integer.decode(pile.depiler());
                    int x622 = Integer.decode(pile.depiler());
                    if(x611 != x622){
                        pile.empiler(Integer.toString(1));
                    }else {
                        pile.empiler(Integer.toString(0));
                    }
                    break;
                	
			default:
				break;
            }
        }
    }
    
    private void initialise(String  text){
        String[] insts = text.split("\n");
        for(String inst : insts){
            String[] ops = inst.split(" ");
            String operation = ops[0];
            String operant ;
            if (ops.length == 2) {
            	operant = ops[1] ;
			} else {
				operant = "";
			}
            if(operation.equals("load")){
                vic.add(new Instruction(Instruction.Type.LOAD, operant));
            }else if(operation.equals("loadc")){
                vic.add(new Instruction(Instruction.Type.LOADC, operant));
            }else if(operation.equals("add")){
                vic.add(new Instruction(Instruction.Type.ADD));
            }else if(operation.equals("read")){
                vic.add(new Instruction(Instruction.Type.READ, operant));
            }else if(operation.equals("end")){
                vic.add(new Instruction(Instruction.Type.END));
            }else if(operation.equals("store")){
                vic.add(new Instruction(Instruction.Type.STORE, operant));
            }else if(operation.equals("write")){
                vic.add(new Instruction(Instruction.Type.WRITE, operant));
            }else if(operation.equals("writec")){
                vic.add(new Instruction(Instruction.Type.WRITEC, operant));
            }else if(operation.equals("jump")){
                vic.add(new Instruction(Instruction.Type.JUMP, operant));
            }else if(operation.equals("sub")){
                vic.add(new Instruction(Instruction.Type.SUB));
            }else if(operation.equals("mul")){
                vic.add(new Instruction(Instruction.Type.MUL));
            }else if(operation.equals("div")){
                vic.add(new Instruction(Instruction.Type.DIV));
            }else if(operation.equals("sup")){
                vic.add(new Instruction(Instruction.Type.SUP));
            }else if(operation.equals("inf")){
                vic.add(new Instruction(Instruction.Type.INF));
            }else if(operation.equals("equal")){
                vic.add(new Instruction(Instruction.Type.EQUAL));
            }else if(operation.equals("jzero")){
                vic.add(new Instruction(Instruction.Type.JZERO, operant));
            }else if(operation.equals("supe")){
                vic.add(new Instruction(Instruction.Type.SUPE));
            }else if(operation.equals("infe")){
                vic.add(new Instruction(Instruction.Type.INFE));
            }
        }
    }
    private static Scanner scanner;

	public static void main(String[] args) throws FileNotFoundException {
      
       try {
		System.out.println(3/0);
	} catch (Exception e) {
		System.out.println("/ by zero");
	}
        
        File f = new File("C:\\Users\\A\\Desktop\\comp\\source.txt");
        
        scanner = new Scanner(new BufferedReader(new FileReader(f)));
        String text="";
        while(scanner.hasNextLine()){
            text += scanner.nextLine()+"\n";
        }
        new Interpreteur(text);
    }
}