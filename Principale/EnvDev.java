package Principale;

import java.awt.EventQueue;
import java.awt.FileDialog;
import java.awt.Frame;
import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JMenuBar;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;
import java.util.Vector;
import javax.swing.JMenu;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JMenuItem;
import javax.swing.JTextPane;

import java.awt.Toolkit;
import javax.swing.JLabel;
import java.awt.Color;
import java.awt.Font;
import javax.swing.SwingConstants;
import javax.swing.text.BadLocationException;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;

import intrepreteur.Interpreteur;
import java.awt.TextArea;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
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
 * 
 * 
 * @author Esserrati Mohammed Abdessamad
 *
 */
public class EnvDev {

	private JFrame frmMiniCompilateurtp;
	private JLabel lblNewLabel;
	JTextPane  codeSource;
	JTextPane console;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					EnvDev window = new EnvDev();
					window.frmMiniCompilateurtp.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public EnvDev() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
private void initialize() {
		frmMiniCompilateurtp = new JFrame();
		frmMiniCompilateurtp.setIconImage(Toolkit.getDefaultToolkit().getImage(EnvDev.class.getResource("/Picture/logo.png")));
		frmMiniCompilateurtp.setTitle("Mini Compilateur(Tp)");
		frmMiniCompilateurtp.setBounds(0, 0, 853, 614);
		frmMiniCompilateurtp.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		lblNewLabel = new JLabel("");
		lblNewLabel.setBounds(244, 31, 383, 24);
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 13));
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 66,  frmMiniCompilateurtp.getBounds().width-40, 217);
		
		codeSource = new JTextPane();
		scrollPane.setViewportView(codeSource);
		
		JButton btnNewButton = new JButton("Open file");
		btnNewButton.setBounds(10, 294, 159, 31);
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				codeSource.setText(lirefichier());
				}
		});
		//frmMiniCompilateurtp.setBounds(0, 0, 653, 614);
		JLabel lblSourceprogramme = new JLabel("code source :");
		lblSourceprogramme.setBounds(10, 31, 224, 24);
		lblSourceprogramme.setBackground(Color.YELLOW);
		lblSourceprogramme.setHorizontalAlignment(SwingConstants.LEFT);
		lblSourceprogramme.setFont(new Font("Tahoma", Font.BOLD, 20));
		lblSourceprogramme.setForeground(Color.GRAY);
		
		JButton btnNewButton_1 = new JButton("Save");
		btnNewButton_1.setBounds(179, 294, 129, 31);
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String txt=codeSource.getText();
				save(txt);
				
			}});
		
		JButton btnNewButton_2 = new JButton("Compiler");
		btnNewButton_2.setBounds(327, 294, 145, 31);
		btnNewButton_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				texte=codeSource.getText();
				AnalyseurSyntaxique AS1 = new AnalyseurSyntaxique(texte);
				
				if(AS1.AnalyseProceduraleIndirecte()){
					console.setText("");
					StyledDocument doc = console.getStyledDocument();
			        javax.swing.text.Style style = console.addStyle("I'm a Style", null);
			        ////	lexical
			        StyleConstants.setForeground(style, Color.red);
			        try { doc.insertString(doc.getLength(), "résultat d'analyse lexical :\n  Chaîne correct!\n",style); }
			        catch (BadLocationException e){}

			        StyleConstants.setForeground(style, Color.blue);
			        try { 
			        	for (int i = 0; i < AS1.uniters.size(); i++) {
			        		doc.insertString(doc.getLength(), AS1.uniters.get(i).toString()+"\t",style);
			        	} 
			        }catch (BadLocationException e){
			        	
			        }
			        
			        ////	syntaxique
			        StyleConstants.setForeground(style, Color.red);
			        try { doc.insertString(doc.getLength(), "\n--------------------------------------- \n résultat d'analyse syntaxique :\n ",style); }
			        catch (BadLocationException e){}

			        StyleConstants.setForeground(style, Color.blue);
			        try { doc.insertString(doc.getLength()," Programme correct!\n",style); }
			        catch (BadLocationException e){}
			        
			        ////	sémantique
			        StyleConstants.setForeground(style, Color.red);
			        try { doc.insertString(doc.getLength(), "---------------------------------------\n résultat d'analyse sémantique :\n ",style); }
			        catch (BadLocationException e){}

			        StyleConstants.setForeground(style, Color.blue);
			        try { doc.insertString(doc.getLength(),AS1.s.affichertable()+"\n"+ AS1.t23.affichercst(),style); }
			        catch (BadLocationException e){}
				}else{
					console.setText("Programme incorrect!");
				}
				/**index=0;
			      l=lire();
			    
			      if(l==lexique.ERROR){
			          JOptionPane.showMessageDialog(null, "chaine refusée(veillez inserer d'autres éléments)");
			      }
			      if(l==lexique.DIEZ){
			          JOptionPane.showMessageDialog(null, "chaine accéptée");
			      }
			     
			   String paragraphe="";
			   while(l!=lexique.DIEZ&&l!=lexique.ERROR){
			          paragraphe+=(lexeme + ":" + l )+"\n";			            
			          l=lire();
			   }
			   console.setText(paragraphe);*/
			}
		});
		
		JMenuBar menuBar = new JMenuBar();
		menuBar.setBounds(0, 0, 1184, 32);
		
		JMenu menu = new JMenu();
		menu.setText("File");
		menu.setMnemonic('f');
		menuBar.add(menu);
		
		JMenuItem mntmOpenFile = new JMenuItem();
		mntmOpenFile.setText("Open file...");
		mntmOpenFile.setMnemonic('o');
		menu.add(mntmOpenFile);
		mntmOpenFile.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
            	codeSource.setText(lirefichier());
            }
        });
		
		JMenuItem menuItem_1 = new JMenuItem();
		menuItem_1.setText("Save");
		menuItem_1.setMnemonic('s');
		menu.add(menuItem_1);
		menuItem_1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
            	String txt=codeSource.getText();
				save(txt);
            }
        });
		
		JMenuItem menuItem_2 = new JMenuItem();
		menuItem_2.setText("Save As ...");
		menuItem_2.setMnemonic('a');
		menuItem_2.setDisplayedMnemonicIndex(5);
		menu.add(menuItem_2);
		menuItem_2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
            	String txt=codeSource.getText();
				saveAs(txt);
            }
        });
		
		JMenuItem menuItem_3 = new JMenuItem();
		menuItem_3.setText("Exit");
		menuItem_3.setMnemonic('x');
		menu.add(menuItem_3);
		menuItem_3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
            	System.exit(0);
            }
        });
		
		
		JMenu menu_2 = new JMenu();
		menu_2.setText("Help");
		menu_2.setMnemonic('h');
		menuBar.add(menu_2);
		JMenuItem qprsolution = new JMenuItem("Projet Mini compilateur ?");
		qprsolution.setMnemonic('x');
		qprsolution.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
            	
            }
        });
		
		menu_2.add(qprsolution);
		JMenuItem menuItem_9 = new JMenuItem();
		menuItem_9.setText("About");
		menuItem_9.setMnemonic('a');
		menuItem_9.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
            	
            }
        });
		menu_2.add(menuItem_9);
		
		JMenu mnCodeCibleFile = new JMenu();
		mnCodeCibleFile.setText("Code Cible File");
		mnCodeCibleFile.setMnemonic('f');
		menuBar.add(mnCodeCibleFile);
		
		JMenuItem mntmOpenFilevic = new JMenuItem();
		mntmOpenFilevic.setText("Open file(vic)...");
		mntmOpenFilevic.setMnemonic('o');
		mntmOpenFilevic.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
            	//open file VIC hhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhh
            	String txt="";
            	Frame yourJFrame = null;
            	FileDialog fd = new FileDialog(yourJFrame, "Choose a file", FileDialog.LOAD);
            	fd.setDirectory("C:\\Users\\A\\Desktop\\comp\\source\\");
            	fd.setFile("*.txt");
            	fd.setVisible(true);
            	String filename = fd.getFile();
            	if (filename != null){
            		lblNewLabel.setText("C:\\Users\\A\\Desktop\\comp\\source\\" + filename);
            		BufferedReader IN = null;
            		try {
            			IN = new BufferedReader (new FileReader("C:\\Users\\A\\Desktop\\comp\\source\\" + filename));
            		} catch (FileNotFoundException e1) {
            			e1.printStackTrace();
            		}
            		String ligne;
            		String [] mot;
            		try {
            			while ((ligne = IN.readLine ())!= null){
            				mot = ligne.split (" ");
            				for (int i = 0; i < mot.length; i++){
            					txt+=mot [i]+" ";}txt+="\n";
            			}
            		} catch (IOException e) {
            		e.printStackTrace();
            		}
            		try {
						new Interpreteur(txt);
					} catch (Exception e) {
						console.setText("");
						StyledDocument doc = console.getStyledDocument();
				        javax.swing.text.Style style = console.addStyle("I'm a Style", null);
						////				Exception in Interpreteur
				        StyleConstants.setForeground(style, Color.red);
				        try { doc.insertString(doc.getLength(), "résultat d'Interpreteur : \n ",style); }
				        catch (BadLocationException e2){}

				        StyleConstants.setForeground(style, Color.blue);
				        try { 
				        	
				        		doc.insertString(doc.getLength(),e+"Exception in Interpreteur",style);
				        	
				        }catch (BadLocationException e1){
				        	
				        }
					}
    		        
            		
            }
            }
        });
		mnCodeCibleFile.add(mntmOpenFilevic);
		
		JMenuItem mntmSavevicAs = new JMenuItem();
		mntmSavevicAs.setText("Save As (vic) ...");
		mntmSavevicAs.setMnemonic('a');
		mntmSavevicAs.setDisplayedMnemonicIndex(5);
		mntmSavevicAs.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
            	texte=codeSource.getText();
				AnalyseurSyntaxique AS1 = new AnalyseurSyntaxique(texte);
				if(AS1.AnalyseProceduraleIndirecte()){
					AS1.saveAs();
					console.setText("");
					StyledDocument doc = console.getStyledDocument();
			        javax.swing.text.Style style = console.addStyle("I'm a Style", null);
			        ////	lexical
			        StyleConstants.setForeground(style, Color.red);
			        try { doc.insertString(doc.getLength(), "résultat d'analyse lexical : Vrai\n ",style); }
			        catch (BadLocationException e){}

			        StyleConstants.setForeground(style, Color.blue);
			        try { 
			        	for (int i = 0; i < AS1.uniters.size(); i++) {
			        		doc.insertString(doc.getLength(), AS1.uniters.get(i).toString()+" ",style);
			        	} 
			        }catch (BadLocationException e){
			        	
			        }
			        
			        ////	syntaxique
			        StyleConstants.setForeground(style, Color.red);
			        try { doc.insertString(doc.getLength(), "\n \n résultat d'analyse syntaxique :\n ",style); }
			        catch (BadLocationException e){}

			        StyleConstants.setForeground(style, Color.blue);
			        try { doc.insertString(doc.getLength()," Programme correct!\n",style); }
			        catch (BadLocationException e){}
			        
			        ////	sémantique
			        StyleConstants.setForeground(style, Color.red);
			        try { doc.insertString(doc.getLength(), "\n résultat d'analyse sémantique :\n ",style); }
			        catch (BadLocationException e){}

			        StyleConstants.setForeground(style, Color.blue);
			        try { doc.insertString(doc.getLength(),AS1.s.affichertable()+"\n"+ AS1.t23.affichercst(),style); }
			        catch (BadLocationException e){}
				//	console.setText("Programme correct! \n"+AS1.s.affichertable()+"\n"+ AS1.t23.affichercst());
				}else{
					console.setText("Programme incorrect!");
				}
            	//Save As VIC hhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhh
            }
        });
		mnCodeCibleFile.add(mntmSavevicAs);
		
		JLabel lblConsole = new JLabel("Console");
		lblConsole.setBounds(10, 336, 239, 24);
		lblConsole.setBackground(Color.ORANGE);
		lblConsole.setHorizontalAlignment(SwingConstants.LEFT);
		lblConsole.setForeground(Color.GRAY);
		lblConsole.setFont(new Font("Tahoma", Font.BOLD, 20));
		
		JButton button = new JButton("Run");
		button.setBounds(482, 294, 145, 31);
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

		        
		        Scanner scanner = null;
			
				
		        File f = new File("C:\\Users\\A\\Desktop\\comp\\source\\vic.txt");
		        
		        try {
					scanner = new Scanner(new BufferedReader(new FileReader(f)));
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		        String text="";
		        while(scanner.hasNextLine()){
		            text += scanner.nextLine()+"\n";
		        }
		        try {
					new Interpreteur(text);
				} catch (Exception e) {
					console.setText("");
					StyledDocument doc = console.getStyledDocument();
			        javax.swing.text.Style style = console.addStyle("I'm a Style", null);
					////				Exception in Interpreteur
			        StyleConstants.setForeground(style, Color.red);
			        try { doc.insertString(doc.getLength(), "résultat d'Interpreteur : \n ",style); }
			        catch (BadLocationException e2){}

			        StyleConstants.setForeground(style, Color.blue);
			        try { 
			        	
			        		doc.insertString(doc.getLength(),e+"Exception in Interpreteur",style);
			        	
			        }catch (BadLocationException e1){
			        	
			        }
				}
			//  console.setText(Instruction.resultat);
			}
		});
		frmMiniCompilateurtp.getContentPane().setLayout(null);
		frmMiniCompilateurtp.getContentPane().add(lblNewLabel);
		frmMiniCompilateurtp.getContentPane().add(scrollPane);
		
		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(10, 371, 813, 207);
		frmMiniCompilateurtp.getContentPane().add(scrollPane_1);
		
		console = new JTextPane();
		scrollPane_1.setViewportView(console);
		frmMiniCompilateurtp.getContentPane().add(lblSourceprogramme);
		frmMiniCompilateurtp.getContentPane().add(btnNewButton);
		frmMiniCompilateurtp.getContentPane().add(btnNewButton_1);
		frmMiniCompilateurtp.getContentPane().add(btnNewButton_2);
		frmMiniCompilateurtp.getContentPane().add(menuBar);
		frmMiniCompilateurtp.getContentPane().add(lblConsole);
		frmMiniCompilateurtp.getContentPane().add(button);
	}

private String lirefichier(){
	String txt="";
	Frame yourJFrame = null;
	FileDialog fd = new FileDialog(yourJFrame, "Choose a file", FileDialog.LOAD);
	fd.setDirectory("C:\\Users\\A\\Desktop\\comp\\");
	fd.setFile("*.lp.txt");
	fd.setVisible(true);
	String filename = fd.getFile();
	if (filename != null){
		lblNewLabel.setText("C:\\Users\\A\\Desktop\\comp\\" + filename);
		BufferedReader IN = null;
		try {
			IN = new BufferedReader (new FileReader("C:\\Users\\A\\Desktop\\comp\\" + filename));
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		}
		String ligne;
		String [] mot;
		try {
			while ((ligne = IN.readLine ())!= null){
				mot = ligne.split (" ");
				for (int i = 0; i < mot.length; i++){
					txt+=mot [i]+" ";}txt+="\n";
			}
		} catch (IOException e) {
		e.printStackTrace();
		}
		
		return(txt);
}
	return "";
}

private void save(String txt){
	if(lblNewLabel.getText().equals(""))
		saveAs(txt);
	else
		try{
			File ff=new File(lblNewLabel.getText()); // définir l'arborescence
			ff.createNewFile();
			FileWriter ffw=new FileWriter(ff);
			ffw.write(txt);  // écrire une ligne dans le fichier resultat.txt
			ffw.write("\n"); // forcer le passage à la ligne
			ffw.close(); // fermer le fichier à la fin des traitements
			} catch (Exception e) {}
}

private void saveAs(String txt){
	Frame yourJFrame = null;
	FileDialog fd = new FileDialog(yourJFrame, "Choose a file", FileDialog.SAVE);
	fd.setDirectory("C:\\Users\\A\\Desktop\\comp\\");
	fd.setFile("*.lp.txt");
	fd.setVisible(true);
	String filename = fd.getFile();
	if (filename != null){
		try{
			File ff=new File("C:\\Users\\A\\Desktop\\comp\\" + filename+".lp.txt"); // définir l'arborescence
			ff.createNewFile();
			FileWriter ffw=new FileWriter(ff);
			ffw.write(txt);  // écrire une ligne dans le fichier resultat.txt
			ffw.write("\n"); // forcer le passage à la ligne
			ffw.close(); // fermer le fichier à la fin des traitements
			lblNewLabel.setText("C:\\Users\\A\\Desktop\\comp\\" + filename+".lp.txt");
			} catch (Exception e) {}
	
		
}
}
enum lexique{
    DIEZ,PARENTHESE,INT,BOOLEAN,INF,INFEG,SUPP,SUPPEG,ENTIER,REEL,IDENTIFICATEUR,VIRGULE,POINT,
    POINTVIRGULE,DEUXPOINTS,PLUS,MOINS,DIVISE,MULT, EGALE, FOR,IF,ELSE,BREAK,CONTINUE,CLASS, ERROR,
    GILLEMET, INTEROGATION, EXCLAMATION, ACOLATED, ACOLATEG
  }
String texte;
lexique l;
int i=0;
Vector<Character> lexeme=new Vector<Character>();
int index=0;
 String str="";
char lirecar(){
   char z = texte.charAt(index);
    index++;
    return z;
}
void retour(){
    index--;
}
  
 lexique lire(){
      
     lexeme.removeAllElements();
     i=0;
    
     char z = lirecar();
      if(z=='#'){
          
           return lexique.DIEZ;
           
         }
      while(Character.isWhitespace(z)) {
          z=lirecar();
          if(z=='#'){
            
               return lexique.DIEZ;
          }
      }
      if(z=='<'){
          z=lirecar();
         lexeme.add(i,z);
          if(z=='='){
               lexeme.add(i+1,z);
              return lexique.INFEG;
          
          }
          retour();
          return lexique.INF;
      }if(z=='>'){
          z=lirecar();
          lexeme.add(i,z);
          if(z=='='){
               lexeme.add(i+1,z);
              return lexique.SUPPEG;
             
          }
          retour();
          return lexique.SUPP;
 } if(z=='='){
       lexeme.add(i,z);
     return lexique.EGALE;
    
 }if(z==','){
       lexeme.add(i,z);
     return lexique.VIRGULE;
    
 }
 if(z=='?'){
       lexeme.add(i,z);
     return lexique.INTEROGATION;
    
 }if(z=='!'){
       lexeme.add(i,z);
     return lexique.EXCLAMATION;
    
 }if(z=='{'){
     lexeme.add(i,z);
   return lexique.ACOLATED;
  
}if(z=='}'){
    lexeme.add(i,z);
  return lexique.ACOLATEG;
 
}
 if(z=='.'){
       lexeme.add(i,z);
     return lexique.POINT;
 }if(z==':'){
       lexeme.add(i,z);
     return lexique.DEUXPOINTS;
 }if(z==';'){
       lexeme.add(i,z);
     return lexique.POINTVIRGULE;
     }if(z==')'){
       lexeme.add(i,z);
         return lexique.PARENTHESE;
     }if(z=='('){
       lexeme.add(i,z);
         return lexique.PARENTHESE;
     }if(z=='"'){
       lexeme.add(i,z);
         return lexique.GILLEMET;
     
     }if(z=='+'){
       lexeme.add(i,z);
         return lexique.PLUS;
     }if(z=='-'){
       lexeme.add(i,z);
         return lexique.MOINS;
     }if(z=='*'){
       lexeme.add(i,z);
         return lexique.MULT;
     }if(z=='/'){
       lexeme.add(i,z);
         return lexique.DIVISE;
     }
     
    
             
     if(
         Character.isLetter(z)){
        str+=z;
         lexeme.add(i,z);
         i++;
           z=lirecar();
           while(Character.isDigit(z)||Character.isLetter(z)){ 
        
            str+=z;
               lexeme.add(i,z);
              
         i++;
         z=lirecar();
  
           }
           retour();
            if(str.equals("for")){
                 return lexique.FOR;
             }if(str.equals("boolean")){
                 return lexique.BOOLEAN;
             }if(str.equals("int")){
                 return lexique.INT;
             }if(str.equals("break")){
                 return lexique.BREAK;
             }if(str.equals("continue")){               /*keywords*/
                 return lexique.CONTINUE;
             }if(str.equals("else")){
                 return lexique.ELSE;
             }if(str.equals("class")){
                 return lexique.CLASS;
             }
           return lexique.IDENTIFICATEUR;
     }
     if(Character.isDigit(z)){
        
         lexeme.add(i,z);
         i++;
          z=lirecar();
         while(Character.isDigit(z)){
             lexeme.add(i,z);
         i++;
          z=lirecar();
         }
        
         if(z=='.'){
          
             lexeme.add(i,z);
         i++;
            z=lirecar();
             if(Character.isDigit(z)){
                 lexeme.add(i,z);
         i++;
                 z=lirecar();
                 while(Character.isDigit(z)){
                     lexeme.add(i,z);
         i++;
         z=lirecar();
                 }
                 retour();
                 
                 return lexique.REEL;
             }
         }
        retour();
         return lexique.ENTIER;
     }
    
      
       return lexique.ERROR;    
  }
}
