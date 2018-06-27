package application;

import java.util.ArrayList;
import java.util.List;

public class TrieHybrid implements IHybrid {

	private char cle;
	private TrieHybrid filsG;
	private TrieHybrid filsC;
	private TrieHybrid filsD;
	private boolean finiched;
	private int x;

	public TrieHybrid() {

		filsG = null;
		filsD = null;
		filsC = null;
	}

	@Override
	public char getCle() {
		return cle;
	}

	public void setCle(char c) {
		this.cle = c;
	}

	public void setFilsG(TrieHybrid filsG) {
		this.filsG = filsG;
	}

	public void setFilsC(TrieHybrid filsC) {
		this.filsC = filsC;
	}

	public void setFilsD(TrieHybrid filsD) {
		this.filsD = filsD;
	}

	public void setFiniched(boolean finiched) {
		this.finiched = finiched;
	}

	@Override
	public TrieHybrid getFilsG() {
		return filsG;
	}

	@Override
	public TrieHybrid getFilsC() {
		return filsC;
	}

	@Override
	public TrieHybrid getFilsD() {
		return filsD;
	}

	@Override
	public boolean isFiniched() {
		return finiched;
	}

	@Override
	public TrieHybrid ajouter_Mot(TrieHybrid arbre, String mot) {

		if (arbre == null)
			arbre = new TrieHybrid();

		if (arbre.getCle() == '\u0000') {

			arbre.cle = mot.charAt(0);

			if (mot.length() == 1) {
				arbre.finiched = true;
				arbre.cle = mot.charAt(0); // Pourquoi refaire ici?
			} else {
				mot = mot.substring(1);
				arbre.filsC = ajouter_Mot(arbre.getFilsC(), mot);
			}

		} else {
			if (arbre.getCle() == mot.charAt(0)) {
				mot = mot.substring(1);
				if (mot.equals(""))
					arbre.finiched = true;
				else
					arbre.filsC = ajouter_Mot(arbre.getFilsC(), mot);
			} else if (arbre.getCle() > mot.charAt(0)) {
				arbre.filsG = ajouter_Mot(arbre.getFilsG(), mot);
			} else
				arbre.filsD = ajouter_Mot(arbre.getFilsD(), mot);
		}

		return arbre;
	}

	public TrieHybrid ajouter_abr_D(TrieHybrid arbre, TrieHybrid arbre1) {
		// ajouter un arbre sur le fils droit

		if (arbre == null)
			arbre = arbre1;
		else {
			// TODO a verifier pour ajouter a gauche ou a droite
			arbre.setFilsD(ajouter_abr_D(arbre.getFilsD(), arbre1));
		}
		return arbre;

	}

	@Override
	public TrieHybrid supprimer_Mot(TrieHybrid arbre, String mot) {

		if (arbre == null)
			return null;
		try {
			if (arbre.getCle() == mot.charAt(0)) {
				if (arbre.isFiniched() && arbre.getFilsC() != null && mot.length() > 1) 
				{
					arbre.filsC = supprimer_Mot(arbre.getFilsC(), mot.substring(1));
				} 
				else 
					if (arbre.isFiniched() && arbre.getFilsC() != null) 
					{
						arbre.finiched = false;
					} 
					else 
					if (arbre.isFiniched() && arbre.getFilsC() == null) 
					{
						TrieHybrid abr = arbre.getFilsD();
						arbre = arbre.getFilsG();
						arbre = ajouter_abr_D(arbre, abr);
					} 
					else
					{
						arbre.filsC = supprimer_Mot(arbre.getFilsC(), mot.substring(1));
						if(arbre.getFilsC() == null)
						{
							TrieHybrid abr = arbre.getFilsD();
							arbre = arbre.getFilsG();
							arbre = ajouter_abr_D(arbre, abr);
						}
					}

			} else if (arbre.getCle() > mot.charAt(0)) {
				arbre.filsG = supprimer_Mot(arbre.getFilsG(), mot);
			} else {
				arbre.filsD = supprimer_Mot(arbre.getFilsD(), mot);
			}

		} catch (Exception e) {
		}

		return arbre;

	}

	@Override
	public boolean chercher_mot(TrieHybrid arbre, String mot) {

		if (arbre == null) {
			return false;
		} else {
			if (mot.length() >= 1 && arbre.getCle() == mot.charAt(0)) {
				if (mot.length() == 1 && arbre.isFiniched()) {
					return true;
				} else {
					mot = mot.substring(1);
					return chercher_mot(arbre.getFilsC(), mot);
				}
			} else if (mot.length() >= 1 && arbre.getCle() > mot.charAt(0)) {
				return chercher_mot(arbre.getFilsG(), mot);
			} else if (mot.length() >= 1 && arbre.getCle() < mot.charAt(0))
				return chercher_mot(arbre.getFilsD(), mot);
		}

		return false;
	}

	@Override
	public int compte_mot(TrieHybrid arbre) {

		if (arbre == null)
			return 0;
		if (arbre.finiched)
			return 1 + compte_mot(arbre.filsG) + compte_mot(arbre.filsC)
					+ compte_mot(arbre.filsD);
		else
			return compte_mot(arbre.filsG) + compte_mot(arbre.filsC)
					+ compte_mot(arbre.filsD);

	}

	public void ajouter_mot(TrieHybrid arbre, List<String> mots, StringBuilder mot) {

		if (arbre == null)
			return;

		ajouter_mot(arbre.filsG, mots, mot);

		mot.append(arbre.getCle());

		if (arbre.isFiniched()) {
			mots.add(mot.toString());

		}

		ajouter_mot(arbre.filsC, mots, mot);

		if (mot.length() > 0)
			mot.deleteCharAt(mot.length() - 1);

		ajouter_mot(arbre.filsD, mots, mot);

	}

	@Override
	public List<String> lister_mot(TrieHybrid arbre) {

		List<String> mots = new ArrayList<>();
		StringBuilder mot = new StringBuilder();
		ajouter_mot(arbre, mots, mot);
		return mots;

	}

	@Override
	public int compter_Nil(TrieHybrid arbre) {

		if (arbre == null)
			return 1;
		else
			return compter_Nil(arbre.filsG) + compter_Nil(arbre.filsC)
					+ compter_Nil(arbre.filsD);

	}

	@Override
	public int hauteur(TrieHybrid arbre) {

		if (arbre == null)
			return 0;
		else
			return 1 + Math.max(
					Math.max(hauteur(arbre.filsG), hauteur(arbre.filsC)),
					hauteur(arbre.filsD));
	}

	
	public int[] profendeur_moyenne_interne(int level)
	{
		int[] result = {0,0};
		int[] tmp;
		
		
		
		if(filsC != null)
		{
			tmp = filsC.profendeur_moyenne_interne(level + 1);
			result[0] += tmp[0];
			result[1] += tmp[1];
		}
		else
		{
			result[0] += level;
			result[1] += 1;
		}
		
		if(filsG != null)
		{
			tmp = filsG.profendeur_moyenne_interne(level + 1);
			result[0] += tmp[0];
			result[1] += tmp[1];
		}
		else
		{
			result[0] += level;
			result[1] += 1;
		}
		
		if(filsD != null)
		{
			tmp = filsD.profendeur_moyenne_interne(level + 1);
			result[0] += tmp[0];
			result[1] += tmp[1];
		}
		else
		{
			result[0] += level;
			result[1] += 1;
		}
	
		return result;
		
	}
	
	
	
	@Override
	public double profendeur_moyenne() {
		
		int[] result = new int[2];
		
		result = profendeur_moyenne_interne(0);
		
		if(result[1] == 0)
			return 0;
		
		System.out.println("nombre de noeuds : " + result[1]);
		System.out.println("somme des profendeur : " + result[0]);
		
		return result[0] / result[1];
	}

	@Override
	public int prefixe(TrieHybrid arbre, String mot) {

		if (arbre == null)
			return 0;

		if (mot.length() > 0) {
			if (arbre.finiched && arbre.getCle() == mot.charAt(0)
					&& mot.length() == 1)
				return 1 + prefixe(arbre.getFilsC(), mot.substring(1))
						+ prefixe(arbre.getFilsG(), mot)
						+ prefixe(arbre.getFilsD(), mot);

			if (arbre.getCle() > mot.charAt(0))
				return prefixe(arbre.getFilsG(), mot);

			if (arbre.getCle() < mot.charAt(0))
				return prefixe(arbre.getFilsD(), mot);

			if (arbre.getCle() == mot.charAt(0))
				return prefixe(arbre.getFilsC(), mot.substring(1));

		}

		if (arbre.isFiniched())
			return 1 + prefixe(arbre.getFilsC(), mot)
					+ prefixe(arbre.getFilsG(), mot)
					+ prefixe(arbre.getFilsD(), mot);

		return prefixe(arbre.getFilsC(), mot) + prefixe(arbre.getFilsG(), mot)
				+ prefixe(arbre.getFilsD(), mot);

	}

	public void construire(TrieHybrid arbre, String text) {
		String[] mots = text.split(" ");

		for (int i = 0; i < mots.length; i++)
			this.ajouter_Mot(arbre, mots[i]);

	}

	
	
	
	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public TrieHybrid patricia_to_hybrid_interne(String mot) {
		TrieHybrid arbre = new TrieHybrid();

		if (mot.length() == 0)
			return null;

		if (mot.length() == 2 && mot.endsWith(" ")) {
			arbre.cle = mot.charAt(0);
			arbre.finiched = true;
			return arbre;
		}

		arbre.cle = mot.charAt(0);
		arbre.filsC = patricia_to_hybrid_interne(mot.substring(1));

		return arbre;

	}

	public TrieHybrid patricia_hybrid(TrieHybrid trie,PatriciaTrie arbre) {
		
		if(arbre == null || arbre.getArbres().isEmpty())
			return trie;
		
		if(trie != null )
		{ 
			trie.filsC = patricia_hybrid(trie.filsC, arbre);
		}	
		
		if(trie != null && trie.filsC != null && Character.toString(trie.filsC.getCle()).equals(" "))
		{
			trie.finiched = true;
			trie.filsC = trie.filsC.filsD;
		
		}
		
		if(!arbre.getArbres().isEmpty())
		{
		String key = arbre.getArbres().firstKey();
		
		trie = patricia_to_hybrid_interne(key);
		trie.filsC = patricia_hybrid(trie.filsC, arbre.getArbres().get(key));

		if(trie != null && trie.filsC != null && Character.toString(trie.filsC.getCle()).equals(" "))
		{
			trie.finiched = true;
			trie.filsC = trie.filsC.filsD;
		
		}
		
		
		arbre.getArbres().pollFirstEntry();
		trie.filsD = patricia_hybrid(trie.filsD, arbre);
		}
	
		
		return trie;
	}
	
	
public TrieHybrid patricia_to_hybride(PatriciaTrie arbre)
{
		
		if(arbre == null || arbre.getArbres().isEmpty())
			return null;
		
		TrieHybrid result = new TrieHybrid();
		PatriciaTrie trie = arbre;
		
		String key = arbre.getArbres().firstKey();
		result = patricia_to_hybrid_interne(key);
		result.filsC = patricia_hybrid(result.filsC, arbre.getArbres().get(key));
		
		if(result!= null && result.filsC != null && Character.toString(result.filsC.getCle()).equals(" "))
		{
			result.finiched = true;
			result.filsC = result.filsC.filsD;
		
		}
		trie.getArbres().pollFirstEntry();
		result.filsD = patricia_to_hybride(trie);
		
		return result;
	}

public int hauteur_equilbrer(TrieHybrid arbre)
{
	
	
	if(arbre == null)
		return 0;
	
	else
		return 1 + Math.max(hauteur_equilbrer(arbre.getFilsG()), hauteur_equilbrer(arbre.getFilsD()));

}

public TrieHybrid rotationGauche(TrieHybrid arbre)
{
	TrieHybrid b = arbre.getFilsD();
	if(b != null)
	{
	arbre.setFilsD(b.getFilsG());
	b.setFilsG(arbre);
	return b;
	}
	
	return arbre;
}

public  TrieHybrid rotationDroite(TrieHybrid arbre)
{
	
	TrieHybrid b = arbre.getFilsG();
	if(b != null)
	{
	arbre.setFilsG(b.getFilsD());
	b.setFilsD(arbre);
	return b;
	}
	
	return arbre;
}

public TrieHybrid equilibrer(TrieHybrid arbre)
{
	int h_filsG = hauteur_equilbrer(arbre.getFilsG());
	int h_filsD = hauteur_equilbrer(arbre.getFilsD());
	
	if(h_filsG - h_filsD >= 2)
	{
		if(hauteur_equilbrer(arbre.getFilsG().getFilsG()) < hauteur_equilbrer(arbre.getFilsG().getFilsD()))
		{
			arbre.setFilsG(rotationGauche(arbre.getFilsG()));
		}
		arbre =  rotationDroite(arbre);
	}
	else
	{
		if( h_filsD - h_filsG >= 2)
		{
			if(hauteur_equilbrer(arbre.getFilsD().getFilsD()) < hauteur_equilbrer(arbre.getFilsD().getFilsG()))
			{
				arbre.setFilsD(rotationDroite(arbre.getFilsD()));
			}
			arbre =  rotationGauche(arbre);
		}
	}

	return arbre;
}

public TrieHybrid insert_and_equilibre(TrieHybrid arbre, String mot)
{
	if (arbre == null)
		arbre =  new TrieHybrid();

	if (arbre.getCle() == '\u0000') {

		arbre.setCle( mot.charAt(0));

		if (mot.length() == 1) {
			arbre.setFiniched(true);
			arbre.setCle(mot.charAt(0)); 
		} else {
			mot = mot.substring(1);
			arbre.setFilsC(insert_and_equilibre(arbre.getFilsC(), mot));
		}

	} else {
		if (arbre.getCle() == mot.charAt(0)) {
			mot = mot.substring(1);
			if (mot.equals(""))
				arbre.setFiniched(true);
			else
				arbre.setFilsC(insert_and_equilibre(arbre.getFilsC(), mot));
		} else if (arbre.getCle() > mot.charAt(0)) {
			arbre.setFilsG(insert_and_equilibre(arbre.getFilsG(), mot));
			
		} else
		{
			arbre.setFilsD(insert_and_equilibre(arbre.getFilsD(), mot));
			
		}
	}
	arbre = equilibrer(arbre);
	return arbre;
}


}
