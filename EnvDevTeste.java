package Principale;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.FileDialog;
import java.awt.Frame;
import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextPane;
import javax.swing.border.EmptyBorder;
import javax.swing.text.BadLocationException;
import javax.swing.text.MutableAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;
import javax.jws.soap.SOAPBinding.Style;
import javax.swing.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
public class EnvDevTeste {
    public static void main(String[] args) {
        JTextPane textPane = new JTextPane();
        StyledDocument doc = textPane.getStyledDocument();

        javax.swing.text.Style style = textPane.addStyle("I'm a Style", null);
        StyleConstants.setForeground(style, Color.red);

        try { doc.insertString(doc.getLength(), "BLAH ",style); }
        catch (BadLocationException e){}

        StyleConstants.setForeground(style, Color.blue);

        try { doc.insertString(doc.getLength(), "BLEH",style); }
        catch (BadLocationException e){}

        JFrame frame = new JFrame("Test");
        frame.getContentPane().add(textPane);
        frame.pack();
        frame.setVisible(true);
    }
}
/**public class EnvDevTeste extends JFrame {

	
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JScrollPane scrollPane;
	private JTextPane txtpnNewneofipzo;
	private AnalyseurLexical Al;
	private Uniter uniter ;
    private int pse;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					EnvDevTeste frame = new EnvDevTeste();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public EnvDevTeste() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 487);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 0, 414, 210);
		contentPane.add(scrollPane);
		
		txtpnNewneofipzo = new JTextPane();
		
		txtpnNewneofipzo.setText("for i in range while \r\n if ident {} else do constant variable { ( for ) }");
		scrollPane.setViewportView(txtpnNewneofipzo);
		JButton btnNewButton = new JButton("save");
		btnNewButton.addActionListener(new ActionListener() {
public void actionPerformed(ActionEvent arg0) {
	
	Al = new AnalyseurLexical(txtpnNewneofipzo.getText());
	uniter = Al.lireUniter();System.out.print(uniter.getType()+"\t");
	while(!uniter.getType().equals(Uniter.Type.NEAT)){
		int i=0;
		for (Principale.Uniter.Type iterable_element : Uniter.uniterBlue) {
			if(uniter.getType()==iterable_element && i==0){
				setJTextPaneFont(txtpnNewneofipzo,false,null,null,null,null,null,null,Color.blue,null);
				i++;
			}
		}
		for (Principale.Uniter.Type iterable_element : Uniter.uniterNoir ) {
			if(uniter.getType()==iterable_element && i==0){
				setJTextPaneFont(txtpnNewneofipzo,false,null,null,null,null,null,null,Color.GREEN,null);
				i++;
			}
		}
		for (Principale.Uniter.Type iterable_element : Uniter.uniterRouge) {
			if(uniter.getType()==iterable_element && i==0){
				setJTextPaneFont(txtpnNewneofipzo,false,null,null,null,null,null,null,Color.orange,null);
				i++;
			}
		}
		//System.out.println(i);
		if(i==0){
			System.out.println("hhhh");
				setJTextPaneFont(txtpnNewneofipzo,false,null, null,null,null,null,null,Color.BLACK,null);
		}
		
		//doc.setCharacterAttributes(iStart, textPane.getSelectionEnd()-iStart, attrs, false);
		pse=Al.index;
		uniter = Al.lireUniter();System.out.print(uniter.getType()+"\t");
	}System.out.println();
	
}
});
		btnNewButton.setBounds(335, 221, 89, 23);
		contentPane.add(btnNewButton);
		
		JButton btnLirefichier = new JButton("lireFichier");
		btnLirefichier.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				txtpnNewneofipzo.setText(lirefichier());
			}
		});
		btnLirefichier.setBounds(113, 221, 89, 23);
		contentPane.add(btnLirefichier);
		
		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(59, 338, 338, 100);
		contentPane.add(scrollPane_1);
		
		JLabel lblNew = new JLabel("NEW  eofipzo");
		scrollPane_1.setViewportView(lblNew);
		
	}
	public  void setJTextPaneFont(JTextPane textPane, boolean bAllText, String sFontName, Boolean bBold, Boolean bItalic, Boolean bUnderline, Boolean bStrikeThrough, Integer iSize, Color oColor, Integer iAlignment) {
		
        MutableAttributeSet attrs = textPane.getInputAttributes();

        if (sFontName != null) {
        	StyleConstants.setFontFamily(attrs, sFontName);
        }
        if (bBold != null) {
        	StyleConstants.setBold(attrs, bBold);
        }
        if (bItalic != null) {
        	StyleConstants.setItalic(attrs, bItalic);
        }
        if (iSize != null) {
        	StyleConstants.setFontSize(attrs, iSize);
        }
        if (oColor != null) {
        	StyleConstants.setForeground(attrs, oColor);
        }
        if (bUnderline != null) {
        	StyleConstants.setUnderline(attrs, bUnderline);
        }
        if (bStrikeThrough != null) {
        	StyleConstants.setStrikeThrough(attrs, bStrikeThrough);
        }
        
        StyledDocument doc = textPane.getStyledDocument();

        if (!bAllText) {
        	// on modifie uniquement le texte sélectionné
        //	int iStart = textPane.getSelectionStart();
        	doc.setCharacterAttributes(pse, Al.index - pse, attrs, false);
        }
        else {
        	// on modifie le style du jtext lui même
        	textPane.setCharacterAttributes(attrs, false);
        }
        
        if (iAlignment != null) {
        	StyleConstants.setAlignment(attrs, iAlignment);
        	doc.setParagraphAttributes(textPane.getSelectionStart(), textPane.getSelectionEnd() - textPane.getSelectionStart(), attrs, false);
        }
    }

private String lirefichier(){
	String txt="";
	//Frame yourJFrame = null;
	//FileDialog fd = new FileDialog(yourJFrame, "Choose a file", FileDialog.LOAD);
	//fd.setDirectory("C:\\Users\\A\\Desktop\\comp\\");
	//fd.setFile("*.lp.txt");
	//fd.setVisible(true);
//	String filename = fd.getFile();
	//if (filename != null){
		//textPane.setText("C:\\Users\\A\\Desktop\\comp\\" + filename);
		BufferedReader IN = null;
		try {
			//frmMiniCompilateurtp.setIconImage(Toolkit.getDefaultToolkit().getImage(EnvDev.class.getResource("/Picture/logo.png")));

			IN = new BufferedReader (new FileReader("C:/Users/A/workspace/Comp/src/Picture/new.txt"));
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
		//System.out.println(txt);
		return(txt);
//}
//	return "";
}

private void save(String txt){
		try{
			File ff=new File(txtpnNewneofipzo.getText()); // définir l'arborescence
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
			ffw.write(txt); 
			ffw.write("\n"); 
			ffw.close(); 
			txtpnNewneofipzo.setText("C:\\Users\\A\\Desktop\\comp\\" + filename+".lp.txt");
			} catch (Exception e) {}
	
		
}
}
}**/

