package tests;



import java.util.List;

import application.TrieHybrid;





public class TestEquilibre {
	
	public static void main(String[] args) {
		
		TrieHybrid arbre = new TrieHybrid();
		
		String text = 
				"A quel genial professeur de dactylographie sommes nous redevables de la superbe phrase ci dessous, un "
+ "modele du genre, que toute dactylo connait par coeur puisque elle fait appel a chacune des touches du "
+ "clavier de la machine a ecrire ?";
		
		String[] mot = text.split(" ");
		
		for(String m : mot)
		{
			
			arbre = arbre.insert_and_equilibre(arbre, m);
		}
		
		
		
		System.out.println("la hauteur : " + arbre.hauteur(arbre));
		
		
		List<String> mots = arbre.lister_mot(arbre);
		
		for(String m : mots)
			System.out.println(m);
		
		
	}

}
