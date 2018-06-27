package application;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class PatriciaTrie {

	private TreeMap<String, PatriciaTrie> arbres;
	public final String fin = " ";

	public PatriciaTrie() {
		setArbres(new TreeMap<String, PatriciaTrie>());
	}

	/** recuperer un prefixe 
	 * 
	 * Input : mot1 et mot2
	 * Output : le prefixe des deux mots
	 * 
	 * */
	public String commence_par_meme(String mot1, String mot2) {
		String sb = "";

		if (mot1.equals(mot2))
			return mot1;
		else if (mot1.startsWith(mot2))
			return mot2;
		else if (mot2.startsWith(mot1))
			return mot1;
		else {
			if (mot1.charAt(0) == mot2.charAt(0))
				return sb + String.valueOf(mot1.charAt(0)) + commence_par_meme(mot1.substring(1), mot2.substring(1));
		}

		return sb;
	}

	/**
	 * fonction pour ajouter un mots dans un Patricia Trie
	 * @param arbre
	 * @param mot
	 * @return PatriciaTrie
	 */
	
	public PatriciaTrie ajouter_Mot(PatriciaTrie arbre, String mot) {

		mot = mot.replaceAll(fin, "").concat(fin);
		String enCommun = "";

		if (arbre != null && arbre.getArbres().isEmpty()) {
			arbre.getArbres().put(mot, null);
		} else {
			for (Map.Entry<String, PatriciaTrie> entry : arbre.getArbres().entrySet()) {
				String key = entry.getKey();
				enCommun = commence_par_meme(key, mot);

				if (key.equals(mot))
					break;

				
				String k = key.substring(enCommun.length());
				String m = mot.substring(enCommun.length());

				if (!enCommun.equals("")) {
					PatriciaTrie t1 = new PatriciaTrie();
					
					// prefixe et la cle sont les memes 
					if (k.equals("")) {
						arbre.getArbres().get(key).arbres
								.putAll(ajouter_Mot(arbre.getArbres().get(key), m).getArbres());
					} 
					// deux mots qui ont un prefixe en commun
					else {

						t1.getArbres().put(k, entry.getValue());

						arbre.getArbres().remove(key);
						arbre.getArbres().put(enCommun, t1);

						arbre.getArbres().get(enCommun).arbres
								.putAll(ajouter_Mot(arbre.getArbres().get(enCommun), m).getArbres());
					}
					break;
				}
			}
			// pas de prefixe en commun
			if (enCommun.equals(""))
				arbre.getArbres().put(mot, null);

		}

		return arbre;
	}
    
	
	/**
	 * fonction pour ajouter tous les mots dans une liste
	 * @param arbre
	 * @param mots
	 * @param mot
	 */
	public void ajouter_mot(PatriciaTrie arbre, List<String> mots, StringBuilder mot) {
		if (arbre == null)
			return;

		for (Map.Entry<String, PatriciaTrie> entry : arbre.getArbres().entrySet()) {
			String k = entry.getKey();

			mot.append(k);
			
			// cas de mot fini
			if (k.endsWith(" "))
				mots.add(mot.toString());
			
			// appel recursive sur le fils de noeud interne
			ajouter_mot(entry.getValue(), mots, mot);
			
			// on garde la partie de mot trouvé dans le noeud interne pere
			String m = mot.toString().substring(0, mot.length() - k.length());
			mot = mot.replace(0, mot.length(), m);
		}
	}

	/**
	 * fonction qui fait appel a ajouter_mot(PatriciaTrie, list, "") pour lister les 
	 * mots presents dans notre structure 
	 * @param arbre
	 * @return liste de mots
	 */
	public List<String> lister_mot(PatriciaTrie arbre) {

		List<String> mots = new ArrayList<>();
		StringBuilder mot = new StringBuilder();
		ajouter_mot(arbre, mots, mot);
		return mots;

	}

	/**
	 * fonction pour chercher un mot dans notre structure
	 * @param arbre
	 * @param mot
	 * @return boolean (mot trouvé ou pas)
	 */
	public boolean chercher_mot(PatriciaTrie arbre, String mot) {
		if (arbre == null)
			return false;

		mot = mot.replaceAll(fin, "").concat(fin);

		for (Map.Entry<String, PatriciaTrie> entry : arbre.getArbres().entrySet()) {
			String k = entry.getKey();

			if (!commence_par_meme(k, mot).equals("")) {
				// mot trouvé
				if (k.endsWith(fin))
					return true;
				
				// lancer la recherche sur le fils de noeud interne 
				return chercher_mot(entry.getValue(), mot.substring(k.length()));

			}
		}

		return false;
	}

	/**
	 * fonction pour compter le nombre de mots presents dans notre structure
	 * @param arbre
	 * @return nombre de mots
	 */
	public int compte_mot(PatriciaTrie arbre) {
		int i = 0;

		if (arbre == null)
			return 0;

		for (Map.Entry<String, PatriciaTrie> entry : arbre.getArbres().entrySet()) {
			String k = entry.getKey();

			if (k.endsWith(fin))
				i = i + 1;
			else
				i = i + compte_mot(entry.getValue());
		}

		return i;
	}
	/**
	 * fonction pour compter le nombre de nils presents dans notre structure
	 * @param arbre
	 * @return nombre de nils
	 */
	public int compter_Nil(PatriciaTrie arbre) {
		int i = 0;

		if (arbre == null) {
			return (i = i + 1);
		}

		for (Map.Entry<String, PatriciaTrie> entry : arbre.getArbres().entrySet()) {
			i = i + compter_Nil(entry.getValue());
		}

		return i;
	}

	/**
	 * fonction pour calculer la hauteur de la structure
	 * @param arbre
	 * @return hauteur
	 */
	public int hauteur(PatriciaTrie arbre) {
		int max = 0;

		if (arbre == null)
			return max;
		//recuperer le max des hauteurs des noeuds interne
		for (Map.Entry<String, PatriciaTrie> entry : arbre.getArbres().entrySet()) {
			max = Math.max(max, 1 + hauteur(entry.getValue()));
		}
		return max;
	}

	/**
	 * fonction pour calculer la profendeur moyenne de notre structure
	 * @param level
	 * @return tableau de deux entier {somme des profendeurs de l'arbre et de ses sous arbres, nbr des sous arbres }
	 */
	
	public int[] profendeur_moyenne_interne(int level) {
		int[] result = { 0, 0 };
		int[] tmp;

		for (Map.Entry<String, PatriciaTrie> entry : arbres.entrySet()) {
			PatriciaTrie fils = entry.getValue();
			if (fils != null) {
				tmp = fils.profendeur_moyenne_interne(level + 1);
				result[0] += tmp[0];
				result[1] += tmp[1];
			} else {
				result[0] += level + 1;
				result[1] += 1;
			}

		}

		return result;
	}

	/**
	 * fonction qui fait appel a profendeur_moyenne_interne()
	 * @return profendeur moyenne
	 */
	public double profendeur_moyenne() {
		int[] result = profendeur_moyenne_interne(0);

		if (result[1] == 0)
			return 0;

		return result[0] / result[1];
	}

	
	/**
	 * fonction pour calculer le nombre de mots qui le prefixe donné en paramatre
	 * @param arbre 
	 * @param prefixe
	 * @return le nobre des mots qui ont ce prefixe
	 */
	public int prefixe(PatriciaTrie arbre, String mot) {
		int nbr = 0;

		if (arbre == null)
			return nbr;

		for (Map.Entry<String, PatriciaTrie> entry : arbre.getArbres().entrySet()) {
			String k = entry.getKey();
			PatriciaTrie trie = entry.getValue();
			String m = commence_par_meme(k, mot);

			if (k.endsWith(fin) && mot.equals(""))
				nbr = nbr + 1;
			else if (mot.equals("") && !k.endsWith(fin))
				nbr = nbr + prefixe(trie, mot);
			else if (!m.equals("")) {
				// la cle est un mot fini et la meme avec le prefixe
				if (k.replaceAll(" ", "").equals(mot) && k.endsWith(fin))
					nbr = nbr + 1;
				else
				// quand il reste une partie de la clé ex : prefixe de 'd' et la cle est 'da'
				if (commence_par_meme(mot, m).equals(mot))
					nbr = nbr + prefixe(trie, "");
				else
					nbr = nbr + prefixe(trie, mot.substring(m.length()));

				return nbr;
			}
		}

		return nbr;

	}

	/**
	 * fonction pour supprimer un mot dans notre structure
	 * @param arbre
	 * @param mot
	 * @return patricia trie sans le mot supprimer
	 */
	public PatriciaTrie suppression(PatriciaTrie arbre, String mot) {

		mot = mot.replaceAll(fin, "").concat(fin);
		if (arbre == null)
			return arbre;

		for (Map.Entry<String, PatriciaTrie> entry : arbre.getArbres().entrySet()) {
			String key = entry.getKey();
			if (key.equals(mot)) {
				arbre.getArbres().remove(key);
				return arbre;
			} else if (!commence_par_meme(key, mot).equals("")) {
				String m = mot.substring(key.length());
				entry.setValue(suppression(entry.getValue(), m));
				if (entry.getValue() != null && entry.getValue().getArbres().size() == 1) {
					String newKey = key.concat(entry.getValue().getArbres().firstKey());
					arbre.getArbres().remove(key);
					arbre.getArbres().put(newKey, null);
				}
				return arbre;
			}

		}

		return arbre;
	}

	/**
	 * fonction qui fusionne deux patricia trie
	 * @param arbre1
	 * @param arbre2
	 * @return patricia trie resultant de la fusion des deux arbre
	 */
	public PatriciaTrie fusion(PatriciaTrie arbre1, PatriciaTrie arbre2) {
		// TODO sans loop
		PatriciaTrie trie = new PatriciaTrie();

		if (arbre1 == null)
			return arbre2;

		if (arbre2 == null)
			return arbre1;

		if (arbre1.getArbres().isEmpty())
			return trie;

		String key1 = arbre1.getArbres().firstKey();
		PatriciaTrie fils1 = arbre1.getArbres().get(key1);
		String enCommun = "";

		for (Map.Entry<String, PatriciaTrie> entry : arbre2.getArbres().entrySet()) {
			String key2 = entry.getKey();
			PatriciaTrie fils2 = entry.getValue();

			if (key1.equals(key2)) {
				trie.arbres.put(key1, fusion(fils1, fils2));
				arbre2.getArbres().remove(key1);
				break;
			} else {
				enCommun = commence_par_meme(key1, key2);
				if (!enCommun.equals("")) {
					PatriciaTrie t2 = new PatriciaTrie();

					if (key1.equals(enCommun)) {
						t2.getArbres().put(key2.substring(key1.length(), key2.length()), fils2);
						trie.getArbres().put(key1, fusion(fils1, t2));

					} else if (key2.equals(enCommun)) {
						t2.getArbres().put(key1.substring(key2.length(), key1.length()), fils1);
						trie.getArbres().put(key2, fusion(fils2, t2));

					} else {

						t2.arbres.put(key1.substring(0, enCommun.length()), fils1);
						t2.arbres.put(key2.substring(0, enCommun.length()), fils2);

						trie.getArbres().put(enCommun, t2);

					}

					arbre2.getArbres().remove(key2);
					break;
				}

			}

		}

		if (enCommun.equals("")) {
			trie.getArbres().put(key1, fils1);
		}

		arbre1.getArbres().pollFirstEntry();

		trie.getArbres().putAll(fusion(arbre1, arbre2).getArbres());
		trie.getArbres().putAll(arbre2.getArbres());

		return trie;
	}


	/**
	 * fonction qui transforme un trie hybrid en patricia trie
	 * @param h_arbre
	 * @param word
	 * @return patricia_trie
	 */
	public PatriciaTrie hybrid_to_Patricia_interne(TrieHybrid h_arbre, String word) {
		PatriciaTrie result = new PatriciaTrie();

		if (h_arbre == null)
			return null;

		word += Character.toString(h_arbre.getCle());

		TrieHybrid filsC = h_arbre.getFilsC();

		if (h_arbre.isFiniched() && filsC == null) {
			result.getArbres().put(word.concat(fin), null);
		} else

		if (h_arbre.isFiniched()) {
			result.getArbres().put(word, hybrid_to_Patricia_interne(filsC, ""));
			result.getArbres().get(word).getArbres().put(fin, null);

		} else

		if (filsC != null && (filsC.getFilsG() != null || filsC.getFilsD() != null)) {
			result.getArbres().put(word, hybrid_to_Patricia_interne(filsC, ""));

			if (filsC.getFilsG() != null)
				result.getArbres().get(word).getArbres()
						.putAll(hybrid_to_Patricia_interne(filsC.getFilsG(), "").getArbres());

			if (filsC.getFilsD() != null)
				result.getArbres().get(word).getArbres()
						.putAll(hybrid_to_Patricia_interne(filsC.getFilsD(), "").getArbres());
		} else {
			while (filsC != null && filsC.getFilsG() == null && filsC.getFilsD() == null && filsC.getFilsC() != null) {

				word += Character.toString(filsC.getCle());

				if (filsC.isFiniched()) {
					result.getArbres().put(word, hybrid_to_Patricia_interne(filsC.getFilsC(), ""));
					result.getArbres().get(word).getArbres().put(fin, null);
					return result;

				}

				filsC = filsC.getFilsC();

			}

			if (filsC.getFilsG() != null) {
				if (!result.getArbres().containsKey(word))
					result.getArbres().put(word, hybrid_to_Patricia_interne(filsC.getFilsG(), ""));
				else
					result.getArbres().get(word).getArbres()
							.putAll(hybrid_to_Patricia_interne(filsC.getFilsG(), "").getArbres());

			}

			if (filsC.getFilsD() != null) {
				if (!result.getArbres().containsKey(word))
					result.getArbres().put(word, hybrid_to_Patricia_interne(filsC.getFilsD(), ""));
				else
					result.getArbres().get(word).getArbres()
							.putAll(hybrid_to_Patricia_interne(filsC.getFilsD(), "").getArbres());

			}

			if (filsC.getFilsC() != null) {
				if (!result.getArbres().containsKey(word))
					result.getArbres().put(word, hybrid_to_Patricia_interne(filsC, ""));
				else
					result.getArbres().get(word).getArbres().putAll(hybrid_to_Patricia_interne(filsC, "").getArbres());
			}

			if (filsC.isFiniched()) {
				word += Character.toString(filsC.getCle());
				result.getArbres().put(word.concat(fin), null);
			}

		}
		if (h_arbre.getFilsG() != null)
			result.getArbres().putAll(hybrid_to_Patricia(h_arbre.getFilsG()).getArbres());

		if (h_arbre.getFilsD() != null)
			result.getArbres().putAll(hybrid_to_Patricia(h_arbre.getFilsD()).getArbres());

		return result;
	}

	/**
	 * fonction qui appelle la fonction hybrid_to_patricia_interne
	 * @param trie_hybrid
	 * @return patricia trie
	 */
	public PatriciaTrie hybrid_to_Patricia(TrieHybrid h_arbre) {
		PatriciaTrie result = new PatriciaTrie();

		TrieHybrid th = h_arbre;

		result.getArbres().putAll(hybrid_to_Patricia_interne(th, "").getArbres());

		// parcourir ts les fils gauches et droits
		if (th.getFilsG() != null)
			result.getArbres().putAll(hybrid_to_Patricia(th.getFilsG()).getArbres());

		if (th.getFilsD() != null)
			result.getArbres().putAll(hybrid_to_Patricia(th.getFilsD()).getArbres());

		if (h_arbre.getFilsC() != null) {
			result.getArbres().putAll(hybrid_to_Patricia_interne(h_arbre, "").getArbres());
		}

		return result;
	}

	public TreeMap<String, PatriciaTrie> getArbres() {
		return arbres;
	}

	public void setArbres(TreeMap<String, PatriciaTrie> arbres) {
		this.arbres = arbres;
	}

}
