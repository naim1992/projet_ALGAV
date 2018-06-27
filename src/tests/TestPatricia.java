package tests;

import java.util.List;

import application.PatriciaTrie;
import application.TrieHybrid;

public class TestPatricia {
	
public static void main(String[] args) {
		
		PatriciaTrie arbre = new PatriciaTrie();
	
		arbre.ajouter_Mot(arbre, "loup");
		arbre.ajouter_Mot(arbre, "le");
		arbre.ajouter_Mot(arbre, "leve");
		arbre.ajouter_Mot(arbre, "dans");
		arbre.ajouter_Mot(arbre, "car");
		arbre.ajouter_Mot(arbre, "somme");
		arbre.ajouter_Mot(arbre, "dactylographie");
		arbre.ajouter_Mot(arbre, "dactylo");
		
		
		
		System.out.println("recherche de l : " + arbre.chercher_mot(arbre, "l"));
		System.out.println("nbr mots dans arbre : " + arbre.compte_mot(arbre));
		System.out.println("nbr Nil dans arbre : " + arbre.compter_Nil(arbre));
		System.out.println("hauteur arbre : " + arbre.hauteur(arbre));
		System.out.println("prefix de  d : " + arbre.prefixe(arbre, "d"));
		System.out.println("prefendeur moyenne : " + arbre.profendeur_moyenne());
		
		/**
		try(BufferedReader br = new BufferedReader(new FileReader(new
				 File("Shakespeare/comedy_errors.txt")))) {
				 for(String line; (line = br.readLine()) != null; ) {
				 // System.out.println(line);
				 arbre.ajouter_Mot(arbre, line);
				 }
		}catch(Exception e)
		{
			
		}
		*/
		
		System.out.println("----------------- liste des mots ----------------------------");
		List<String> mots = arbre.lister_mot(arbre);
		
		for(String m : mots)
			System.out.println(m);
		
		System.out.println("----------------- suppression  ----------------------------");
		arbre = arbre.suppression(arbre, "le");
		
		mots = arbre.lister_mot(arbre);
		
		for(String m : mots)
			System.out.println(m);
		
		
		System.out.println("----------------- transformation  ----------------------------");
		
		TrieHybrid h_arbre = new TrieHybrid();
		
		h_arbre.ajouter_Mot(h_arbre, "loup");
		h_arbre.ajouter_Mot(h_arbre, "le");
		h_arbre.ajouter_Mot(h_arbre, "leve");
		h_arbre.ajouter_Mot(h_arbre, "dans");
		h_arbre.ajouter_Mot(h_arbre, "car");
		h_arbre.ajouter_Mot(h_arbre, "somme");
		h_arbre.ajouter_Mot(h_arbre, "dactylographie");
		h_arbre.ajouter_Mot(h_arbre, "dactylo");
		
		arbre = arbre.hybrid_to_Patricia(h_arbre);
		h_arbre.supprimer_Mot(h_arbre, "car");
		mots = arbre.lister_mot(arbre);
		
		for(String m : mots)
			System.out.println(m);
	
	}


}
