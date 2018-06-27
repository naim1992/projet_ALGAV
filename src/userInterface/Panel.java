//package userInterface;
//
//import java.awt.Color;
//import java.awt.Dimension;
//import java.awt.Graphics;
//import java.io.BufferedReader;
//import java.io.File;
//import java.io.FileNotFoundException;
//import java.io.FileReader;
//import java.io.IOException;
//
//import javax.swing.JPanel;
//import javax.swing.JTree;
//import javax.swing.tree.DefaultMutableTreeNode;
//
//import application.TrieHybrid;
//
//
//public class Panel extends JPanel{
//
//	public TrieHybrid arbre;
//	private int x;
//	
//	int i = 1;
//	
//	
//	private final String text = "A quel genial professeur de dactylographie sommes nous redevables de la superbe phrase ci dessous, un "
//			+ "modele du genre, que toute dactylo connait par coeur puisque elle fait appel a chacune des touches du "
//			+ "clavier de la machine a ecrire ?";
//	
//	
//	/**
//	 * 
//	 */
//	private static final long serialVersionUID = 1L;
//
//
//	public Panel(TrieHybrid arbre) throws FileNotFoundException, IOException {
//		this.arbre = arbre;
//		//arbre.construire(arbre, text);
//		try (BufferedReader br = new BufferedReader(new FileReader(new File(
//				"Shakespeare/comedy_errors.txt")))) {
//			// Début de calcul
//			for (String line; (line = br.readLine()) != null;) {
//				arbre.ajouter_Mot(arbre, line);
//			}
//			}
//		
//		
//		this.setBackground(Color.WHITE);
//		this.setPreferredSize(new Dimension(500, 500));
//		//this.setPreferredSize(new Dimension(6000, 2500));
//		
//
//		this.x = 3000 ;
//		
//		
//		
//	}
//	
//	
//	public void drawArbre(TrieHybrid arbre, Graphics g, int dx, int dy)
//	{
//		if(arbre == null)
//			g.drawOval(dx, dy, 10, 10);
//		
//		else
//			if(arbre != null)
//			{
//				char k = arbre.getCle();
//				arbre.setX(dx);
//				
//				
//				
//				g.drawOval(dx, dy, 20, 20);
//				g.drawString(String.valueOf(arbre.getCle()), dx + 5, dy + 15);
//				
//
//				if(arbre.getFilsC() == null)
//				{
//				drawArbre(arbre.getFilsC(), g, dx + 5 , dy + 20);
//				}
//				else
//				{
//					drawArbre(arbre.getFilsC(), g, dx , dy + 50);
//					g.drawLine(dx + 10, dy + 20, dx + 10, dy + 55);
//				}
//				
//				if(arbre.getFilsG() == null && arbre.getFilsD() == null)
//				{
//					g.setColor(Color.red);
//					drawArbre(arbre.getFilsG(), g, dx - 10, dy + 10);
//					drawArbre(arbre.getFilsG(), g, dx + 20, dy + 10);
//				}
//				else
//				{
//						g.setColor(Color.black);
//						
//						int h = arbre.hauteur(arbre);
//						
//						
//						if(h == 0)
//							h = 1;
//						
//						if(arbre.getFilsG() == null)
//						{
//							drawArbre(arbre.getFilsG(), g, arbre.getX()  - 10 ,  dy + 10);
//							drawArbre(arbre.getFilsD(), g, arbre.getX()  + 100 , dy + 50);
//							g.drawLine(dx + 10, dy + 20, dx + 100, dy + 55);
//						}
//						else
//						{
//							if(arbre.getFilsD() == null)
//							{
//								drawArbre(arbre.getFilsG(), g, arbre.getX()  - 100 - h * h,  dy + 50);
//								g.drawLine(dx + 10, dy + 20, dx - 90 - h * h , dy + 55);
//								drawArbre(arbre.getFilsD(), g, arbre.getX()  + 20 , dy + 10);
//							}
//							else
//							{
//								drawArbre(arbre.getFilsG(), g, arbre.getX()  - 40 * h ,  dy + 50);
//								g.drawLine(dx + 10, dy + 20, arbre.getX()  - 40 * h + 10, dy + 55);
//								drawArbre(arbre.getFilsD(), g, arbre.getX()  + 40 * h , dy + 50);
//								g.drawLine(dx + 10, dy + 20, arbre.getX()  + 40 * h , dy + 55);
//							}
//						}			
//				}
//			}
//			
//	}
//	
//	public DefaultMutableTreeNode drawArbreWithJTree(TrieHybrid arbre)
//	{
//		// TODO essayes avec les niveau des branches
//		
//		DefaultMutableTreeNode monArbre;
//		
//		if(arbre == null)
//		  return new DefaultMutableTreeNode("");
//		
//		monArbre = new DefaultMutableTreeNode(arbre.getCle());
//		
//		
//		monArbre.add(drawArbreWithJTree(arbre.getFilsC()));
//		monArbre.add(drawArbreWithJTree(arbre.getFilsG()));
//		monArbre.add(drawArbreWithJTree(arbre.getFilsD()));
//		
//		return monArbre;
//		
//		
//	}
//	
//
//	public void draw()
//	{
//	JTree monArbre = new JTree(drawArbreWithJTree(arbre));
//	
//	this.add(monArbre);
//	
//	}
//	
//	
//	
//	
////	public void paint(Graphics g)
////	{
////		g.setColor(Color.BLACK);
////		drawArbre(arbre, g, x, 0);
////		repaint();
////		
////		
////	}
//	
//}
package userInterface;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

import application.HybridToFile;
import application.IHybrid;
import application.PatriciaTrie;
import application.TrieHybrid;

public class Panel extends JPanel{

	
	
//	private final String text = "A quel genial professeur de dactylographie sommes nous redevables de la superbe phrase ci dessous, un "
//			+ "modele du genre, que toute dactylo connait par coeur puisque elle fait appel a chacune des touches du "
//			+ "clavier de la machine a ecrire ?";
	private final String text = "algav";
	
	String[] mots;
	TrieHybrid hybrid = new TrieHybrid();
	PatriciaTrie trie = new PatriciaTrie();
	
	private static final long serialVersionUID = 1L;
	BufferedImage inputImage ;

	public Panel(TrieHybrid hybrid) throws InterruptedException {
		this.hybrid =hybrid ;
//		mots = text.split(" ");
//		for(String m : mots)
//		{
//			hybrid.ajouter_Mot(hybrid, m);
//			trie.ajouter_Mot(trie, m);
//		}
		
		this.setBackground(Color.WHITE);
		hybride_graphe();
		
	
	}
	
	
	public void hybride_graphe() throws InterruptedException
	{

		try {
			  
				long debut = System.currentTimeMillis();
				File file = new File("images/hybrid.dot");
				FileWriter writer = new FileWriter(file);
			
				
				writer.write("digraph Test{ \n");
				HybridToFile.ToFile(hybrid, writer, "r") ;//  ToFile(hybrid, writer, "r");
				writer.write("}");
				writer.close();

			long fin = System.currentTimeMillis();
			System.out.println(fin - debut);
			
			String[] cmd = {"bash", "-c", " dot -Tjpg images/hybrid.dot -o images/hybrid.jpg"};
			Runtime.getRuntime().exec(cmd);
			Thread.sleep(1000);
		   
			inputImage = ImageIO.read(getClass().getResourceAsStream("/hybrid.jpg"));
		    
			this.setPreferredSize(new Dimension(inputImage.getWidth(), inputImage.getHeight()));
			
			repaint();
			
			
		} catch (IOException   e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
		
	}
	
	public void PatriciaGraphe()
	{

		try {
			FileWriter writer = new FileWriter("images/patricia.dot");
			writer.write("digraph Test{ \n");
			writer.write("node[shape=record]\n");
			HybridToFile.ToFile(trie, writer, "R");
			writer.write("}");
			writer.close();
			
			String[] cmd = {"/bin/bash", "-c", " dot -Tjpeg images/patricia.dot -o images/patricia.jpeg"};
			Runtime.getRuntime().exec(cmd);

			 
			Thread.sleep(1000);
		    inputImage = ImageIO.read(getClass().getResourceAsStream("/patricia.jpeg"));
		    
		    this.setPreferredSize(new Dimension(inputImage.getWidth(), inputImage.getHeight()));
		    
			repaint();
			
		} catch (IOException | InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
			
	}
	
	@Override
	public void paintComponent(Graphics g)
	{
		
			 super.paintComponent(g);
			
			if(inputImage != null)
		    {	 
			 g.drawImage(inputImage, 0, 0, inputImage.getWidth()   , inputImage.getHeight()  , null);
		     g.dispose();
		    }
			
			
	     
	}
	
}
