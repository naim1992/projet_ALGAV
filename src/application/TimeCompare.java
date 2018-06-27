package application;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.TreeMap;

import helpers.HelperClass;



public abstract class TimeCompare {

	public static  Long parseDirectoryAddingTime(Object arbre, String path)
			throws IOException {
		TreeMap<Long, Object> result = new TreeMap<>();
		File folder = new File(path);
		ArrayList<Long> times = new ArrayList();
		long startTime = 0;
		long endTime = 0;
		long elapsedTime = 0;
		Object trie;

		if (folder.isDirectory()) {
			for (final File fileEntry : folder.listFiles()) {
				try (BufferedReader br = new BufferedReader(new FileReader(
						fileEntry))) {
					// Début de calcul
					startTime = System.currentTimeMillis();
					for (String line; (line = br.readLine()) != null;) {
						if (arbre != null && arbre instanceof PatriciaTrie) {
							((PatriciaTrie) arbre).ajouter_Mot(
									(PatriciaTrie) arbre, line);
						} else if (arbre != null && arbre instanceof TrieHybrid) {
							//System.out.println("arbre n'est pas nul je vais ajout�");
							((TrieHybrid) arbre).ajouter_Mot((TrieHybrid) arbre, line);
						}
					}
					endTime = System.currentTimeMillis();
					// Fin de calcul
					elapsedTime = endTime - startTime;
					times.add(elapsedTime);
				}
			}
			if (arbre != null) {
				
			}
			//result.put(HelperClass.countTime(times), arbre);
		}
		if (folder.isFile()) {
			try (BufferedReader br = new BufferedReader(new FileReader(folder))) {
				// Début de calcul
				startTime = System.currentTimeMillis();
				for (String line; (line = br.readLine()) != null;) {
					if (arbre instanceof PatriciaTrie) {
						//trie = new PatriciaTrie();
						((PatriciaTrie) arbre).ajouter_Mot((PatriciaTrie) arbre,
								line);
					} else if (arbre instanceof TrieHybrid) {
						//trie = new Noeud();
						((TrieHybrid) arbre).ajouter_Mot((TrieHybrid) arbre, line);
					}
				}
				endTime = System.currentTimeMillis();
				// Fin de calcul
				elapsedTime = endTime - startTime;
				times.add(elapsedTime);
			}
		}
		
		return HelperClass.countTime(times);

	}
}
