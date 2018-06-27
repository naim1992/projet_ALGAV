package application;
import java.util.List;


public interface IHybrid {

	public char getCle();
	public TrieHybrid getFilsG();
	public TrieHybrid getFilsC();
	public TrieHybrid getFilsD();
	public boolean isFiniched();
	
	public TrieHybrid ajouter_Mot(TrieHybrid arbre, String mot);
	public boolean chercher_mot(TrieHybrid arbre, String mot);
	public int compte_mot(TrieHybrid arbre);
	public List<String> lister_mot(TrieHybrid arbre);
	public int compter_Nil(TrieHybrid arbre);
	public int hauteur(TrieHybrid arbre);
	public double profendeur_moyenne();
	public int prefixe(TrieHybrid arbre, String mot);
	TrieHybrid supprimer_Mot(TrieHybrid arbre, String mot);
	

	
	
}
