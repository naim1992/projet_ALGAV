package tests;

import java.util.List;

import application.PatriciaTrie;

public class Test_fusion {
	
	
	public static void main(String[] args) {
		
		PatriciaTrie arbre1 = new PatriciaTrie();
		PatriciaTrie arbre2 = new PatriciaTrie();
		
		String text1 = "A quel genial professeur de dactylographie sommes nous redevables de la superbe phrase ci dessous, un "
				+ "modele du genre, que";
		
		String text2 = "toute dactylo connait par coeur puisque elle fait appel a chacune des touches du "
				+ "clavier de la machine a ecrire ?";
		
		String[] mot1 = text1.split(" ");
		String[] mot2 = text2.split(" ");
		
		for(int i = 0; i < mot1.length ; i++)
			arbre1.ajouter_Mot(arbre1 ,mot1[i]);
		
		
		
		for(int i = 0; i < mot2.length ; i++)
		{
			arbre2.ajouter_Mot(arbre2, mot2[i]);
		
		}
		
		
		

		PatriciaTrie result = new PatriciaTrie();
		result = result.fusion(arbre1, arbre2);
		
		System.out.println("----------------- liste des mots  ----------------------------");

		List<String> mots = result.lister_mot(result);
		
		for(String m : mots)
			System.out.println(m);
		
		
		
		
	}

}
