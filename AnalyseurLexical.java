package Principale;


public class AnalyseurLexical {

    private String text;
    public int index = 0;
   // private int psei=0;
   // private int psef=0;
    
    public AnalyseurLexical(String text) {
        this.text = text;
    }
    public Uniter lireUniter(){
        char c = getChar();
        if(c == '#'){
            return new Uniter(Uniter.Type.NEAT);
        }
        while(Character.isWhitespace(c)){
            c = getChar();
            if(c == '#'){
                return new Uniter(Uniter.Type.NEAT);
            }
        }
        if(c == '{'){
            return new Uniter(Uniter.Type.ACOLADO);
        }else if(c == '}'){
            return new Uniter(Uniter.Type.ACOLADF);
        }else if(c == '>'){
            c = getChar();
            if(c == '='){
                return  new Uniter(Uniter.Type.SUPE);
            }else{
                delireChar();
                return new Uniter(Uniter.Type.SUP);
            }
        }else if(c == '='){
            c = getChar();
            if(c == '='){
                return new Uniter(Uniter.Type.EGAL);
            }else{
                delireChar();
                return new Uniter(Uniter.Type.AFF);
            }
        }else if(c == '<'){
            c = getChar();
            if(c == '='){
                return new Uniter(Uniter.Type.INFE);
            }else if(c == '>'){
                return new Uniter(Uniter.Type.DIFF);
            }else{
                delireChar();
                return new Uniter(Uniter.Type.INF);
            }
        }else if(c == '-'){
        	c = getChar();
        	if(c == '-'){
                return new Uniter(Uniter.Type.DEC);
            }else{
                delireChar();
                return new Uniter(Uniter.Type.MOINS);
            }
        }else if(c == ':'){
            return new Uniter(Uniter.Type.DEUX_POINTS);
        }else if(c == '*'){
            return new Uniter(Uniter.Type.MULT);
        }else if(c == '/'){
        	 c = getChar();
             if(c == '/'){
                 return  new Uniter(Uniter.Type.DIVE);
             }else if(c=='*'){
            	 c =getChar();
             //	String valeur="";
             	while(Character.isLetter(c) || Character.isDigit(c) || Character.isWhitespace(c)){
            			///valeur=valeur+c;
            			c =getChar();
            		}
            		if(c == '*'){
            			c =getChar();
            			if(c=='/')
            			return new Uniter(Uniter.Type.COMMENTAIRE);
            		}
            		return new Uniter(Uniter.Type.NEAT);
             }else{
                 delireChar();
                 return new Uniter(Uniter.Type.DIV);
             }
            
        }else if(c == '+'){
        	c = getChar();
        	if(c == '+'){
                return new Uniter(Uniter.Type.INC);
            }else{
                delireChar();
                return new Uniter(Uniter.Type.PLUS);
            }
        }else if(c == '%'){
            return new Uniter(Uniter.Type.MOD);
        }else if(c == '('){
            return new Uniter(Uniter.Type.PARO);
        }else if(c == ')'){
            return new Uniter(Uniter.Type.PARF);
        }else if(c==','){
            return new Uniter(Uniter.Type.VIRG);
        }else if(c == ';'){
            return new Uniter(Uniter.Type.PVIRG);
        }else if(c == '.'){
            return new Uniter(Uniter.Type.POINT);
        }else if(c == '$'){
            return new Uniter(Uniter.Type.DOLLAR);
        }else if(Character.isLetter(c)){
            String valeur = ""+c;
            c = getChar();
            if(c == '#'){
                return new Uniter(Uniter.Type.IDENT,valeur);
            }
            while(Character.isLetter(c) || Character.isDigit(c)){
                valeur+=c;
                c = getChar();
            }
            delireChar();
            if(valeur.equals("int")){
                return new Uniter(Uniter.Type.TYPE_INT);
            }else if(valeur.equals("float")){
                return new Uniter(Uniter.Type.TYPE_FLOAT);
            }else if(valeur.equals("char")){
                return new Uniter(Uniter.Type.TYPE_CHAR);
            }else if(valeur.equals("string")){
                return new Uniter(Uniter.Type.TYPE_CHAINE_CHAR);
            }else if(valeur.equals("boolean")){
                return new Uniter(Uniter.Type.TYPE_BOOLEAN);
            }else if(valeur.equals("programme")){
                return new Uniter(Uniter.Type.PROGRAMME);
            }else if(valeur.equals("fin")){
                return new Uniter(Uniter.Type.FIN);
            }else if(valeur.equals("if")){
                return new Uniter(Uniter.Type.IF);
            }else if(valeur.equals("while")){
                return new Uniter(Uniter.Type.WHILE);
            }else if(valeur.equals("for")){
                return new Uniter(Uniter.Type.FOR);
            }else if(valeur.equals("else")){
                return new Uniter(Uniter.Type.ELSE);
            }else if(valeur.equals("main")){
                return new Uniter(Uniter.Type.MAIN);
            }else if(valeur.equals("to")){
                return new Uniter(Uniter.Type.TO);
            }else if(valeur.equals("step")){
                return new Uniter(Uniter.Type.STEP);
            }else if(valeur.equals("constant")){
                return new Uniter(Uniter.Type.CONSTANT);
            }else if(valeur.equals("variable")){
                return new Uniter(Uniter.Type.VARIBLE);
            }else if(valeur.equals("read")){
                return new Uniter(Uniter.Type.READ);
            }else if(valeur.equals("write")){
                return new Uniter(Uniter.Type.WRITE);
            }else if(valeur.equals("true")){
                return new Uniter(Uniter.Type.BOOLEAN,"true");
            }else if(valeur.equals("false")){
                return new Uniter(Uniter.Type.BOOLEAN,"false");
            }
            return new Uniter(Uniter.Type.IDENT, valeur);
        }else if(Character.isDigit(c)){
            String valeur = ""+c;
            c = getChar();
            if(c == '#'){
                return new Uniter(Uniter.Type.INT,valeur);
            }
            while(Character.isDigit(c)){
                valeur+=c;
                c = getChar();
                if(c == '#'){
                    return new Uniter(Uniter.Type.INT,valeur);
                }
            }
            if(c == '.'){    
                c = getChar();
                if(c == '#'){
                    delireChar();
                    return new Uniter(Uniter.Type.INT,valeur);
                }
                valeur+=c;
                if(Character.isDigit(c)){
                    c= getChar();
                    if(c == '#'){
                        return new Uniter(Uniter.Type.FLOAT,valeur);
                    }
                    while(Character.isDigit(c)){
                        valeur+=c;
                        c = getChar();
                        if(c == '#'){
                            return new Uniter(Uniter.Type.FLOAT,valeur);
                        }
                    }
                    delireChar();
                    return new Uniter(Uniter.Type.FLOAT, valeur);
                }else {
                    delireChar();
                    delireChar();
                    return new Uniter(Uniter.Type.INT,valeur);
                }
            }else {
                delireChar();
                return new Uniter(Uniter.Type.INT, valeur);
            }
        }else if(c == '\'') {
        	 c =getChar();
        	if(Character.isLetter(c) || Character.isDigit(c)){
        		String valeur=""+c;
        		c =getChar();
        		if(c == '\''){
        			return new Uniter(Uniter.Type.CHAR,valeur);
        		}else{
        			delireChar();
        			delireChar();
        		}
        	}else{
        		delireChar();
        	}
        }else if(c == '"') {
        	c =getChar();
        	String valeur="";
        	while(Character.isLetter(c) || Character.isDigit(c) || Character.isWhitespace(c)){
       			valeur=valeur+c;
       			c =getChar();
       		}
       		if(c == '"'){ return new Uniter(Uniter.Type.CHAINE_CHAR,valeur);}
       }
        return new Uniter(Uniter.Type.NEAT);
    }
    
    private char getChar(){
        if(index >= text.length()){
        	
            return '#';
        }
        char c = text.charAt(index);
        index++;
        return c;
    }
    
    private void delireChar(){
        index--;
    }   
}
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