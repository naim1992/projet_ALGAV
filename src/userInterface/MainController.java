package userInterface;

import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;


import application.TrieHybrid;
import application.HybridToFile;
import application.PatriciaTrie;
import application.TimeCompare;
import javafx.beans.InvalidationListener;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.ScrollEvent;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class MainController {

	@FXML
	private TextField wordText;

	@FXML
	private Button HybridBtn;

	@FXML
	private Button addWordBtn;

	@FXML
	private Button listerWordBtn;

	@FXML
	private Button searchWordBtn;

	@FXML
	private Button chooseFileBtn;

	@FXML
	private Button directoryAddBtn;

	@FXML
	private TextArea textAreaHybrid;

	@FXML
	private TextField filePath;

	@FXML
	private TextArea textAreaPatricia;

	@FXML
	private TextArea textArea;

	@FXML
	private Button compterNilBtn;

	@FXML
	private Button compterMotBtn;

	@FXML
	private Button hauteurBtn;

	@FXML
	private Button drawBtn;
	
	@FXML
	private Button fileBtn;
	
	@FXML
	private Button deleteWordBtn;
	
	@FXML
	private Button patriciaDrawBtn;
	
	@FXML
	private Button profondeurBtn;
	
	

	private String folderPath;
	private PatriciaTrie patriciatrie;
	private TrieHybrid trieHybrid;
	BufferedImage inputImage;
	private Stage stage ;
	private Stage stagePatricia ;

	public void restartAll(ActionEvent event) {
		textAreaHybrid.clear();
		textAreaPatricia.clear();
		filePath.clear();
		textArea.clear();
		wordText.clear();
		folderPath = "";
		patriciatrie = null;
		trieHybrid = null;
	}

	public void hybridBtnAction(ActionEvent event) {
		FileChooser fileChooser = new FileChooser();
		FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("TXT files (*.txt)", "*.txt");
		fileChooser.getExtensionFilters().add(extFilter);
		File fiel = fileChooser.showOpenDialog(new Stage());
		System.out.println(fiel.getName());
	}

	public void chooseFileHandler(ActionEvent event) {
		FileChooser fileChooser = new FileChooser();
		File directory = fileChooser.showOpenDialog(new Stage());
		if (directory != null && directory.isFile()) {
			filePath.appendText(directory.getAbsolutePath());
			folderPath = directory.getPath();
		}
	}
	public void chooseDirectoryHandler(ActionEvent event) {
		DirectoryChooser directoryChooser = new DirectoryChooser();
		File directory = directoryChooser.showDialog(new Stage());
		if (directory != null && directory.isDirectory()) {
			filePath.appendText(directory.getAbsolutePath());
			folderPath = directory.getPath();
		}
	}

	public void addDrirectoryHandler(ActionEvent event) throws IOException {
		if (patriciatrie == null)
			patriciatrie = new PatriciaTrie();
		if (trieHybrid == null)
			trieHybrid = new TrieHybrid();
		if (folderPath != null && patriciatrie != null && trieHybrid != null) {
			long resPatricia = TimeCompare.parseDirectoryAddingTime(patriciatrie, folderPath);
			textAreaPatricia.appendText("- Temps ecoulé en (ms) Pour l'ajout des fichiers situés dans : \n" + folderPath
					+ "\n" + "est de " + resPatricia + "ms \n");
			long resHybrid = TimeCompare.parseDirectoryAddingTime(trieHybrid, folderPath);
			textAreaHybrid.appendText("- Temps ecoulé en (ms) Pour l'ajout des fichiers situés dans : \n" + folderPath
					+ "\n" + "est de " + resHybrid + "ms \n");

		}
	}

	public void addWordHandler(ActionEvent event) {

		if (patriciatrie == null)
			patriciatrie = new PatriciaTrie();
		if (trieHybrid == null)
			trieHybrid = new TrieHybrid();
		String[] word = textArea.getText().split("\\s+"); // wordText.getText();
		try {
			for (String string : word) {
				patriciatrie.ajouter_Mot(patriciatrie, string);
				trieHybrid.ajouter_Mot(trieHybrid, string);
			}
			textAreaPatricia.appendText(
					"- Le/s mot/s ***   " + textArea.getText() + "   *** a bien été ajouté au Patricia Trie \n");
			textAreaHybrid.appendText(
					"- Le/s mot/s ***  " + textArea.getText() + "   *** a bien été ajouté au Trie Hybrid \n");
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	public void listerMotsHandler(ActionEvent event) {
		if (patriciatrie != null) {
			for (String str : patriciatrie.lister_mot(patriciatrie)) {
				textAreaPatricia.appendText(str + "\n");
			}
		}

		if (trieHybrid != null) {
			for (String str : trieHybrid.lister_mot(trieHybrid)) {
				textAreaHybrid.appendText(str + "\n");
			}
		}
	}

	public void searchWordHandler(ActionEvent event) {
		if (patriciatrie == null)
			patriciatrie = new PatriciaTrie();
		if (trieHybrid == null)
			trieHybrid = new TrieHybrid();
		String word[] = textArea.getText().split("\\s+");
		try {
			for (String string : word) {
				if (patriciatrie.chercher_mot(patriciatrie, string)) {
					textAreaPatricia
							.appendText("- Le mot ***   " + string + "   *** est  présent dans Patricia Trie \n");
				} else {
					textAreaPatricia
							.appendText("- Le mot ***   " + string + "   *** n'est pas présent dans Patricia Trie \n");
				}
				if (trieHybrid.chercher_mot(trieHybrid, string)) {
					textAreaHybrid
							.appendText("- Le mot ***  " + string + "   *** est bien présent dans Trie Hybrid \n");
				} else {
					textAreaHybrid
							.appendText("- Le mot ***  " + string + "   *** n'est pas présent dans Trie Hybrid \n");
				}
			}
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	public void compterNilHandler(ActionEvent event) {
		if (patriciatrie != null) {
			textAreaPatricia.appendText("- Le nombre de pointeur NIL dans la structure est "
					+ patriciatrie.compter_Nil(patriciatrie) + "\n");
		}
		if (trieHybrid != null) {
			textAreaHybrid.appendText(
					"- Le nombre de pointeur NIL dans la structure est " + trieHybrid.compter_Nil(trieHybrid) + "\n");
		}
	}

	public void compterMotsHandler(ActionEvent event) {
		if (patriciatrie != null) {
			textAreaPatricia.appendText(
					"- Le nombre de MOTS dans la structure est " + patriciatrie.compte_mot(patriciatrie) + "\n");
		}
		if (trieHybrid != null) {
			textAreaHybrid.appendText(
					"- Le nombre de MOTS dans la structure est " + trieHybrid.compte_mot(trieHybrid) + "\n");
		}
	}

	public void hauteurHandler(ActionEvent event) {
		if (patriciatrie != null) {
			textAreaPatricia
					.appendText("- La HAUTEUR de la structure est " + patriciatrie.hauteur(patriciatrie) + "\n");
		}
		if (trieHybrid != null) {
			textAreaHybrid.appendText("- La HAUTEUR de la structure est " + trieHybrid.hauteur(trieHybrid) + "\n");
		}

	}

	public void countPrefixHandler(ActionEvent event) {
		String word[] = textArea.getText().split("\\s+");
		for (String string : word) {
			if (trieHybrid != null) {
				textAreaHybrid.appendText("- La nombre de mots la structure avec le prefix " + "***" + string
						+ "*** est " + trieHybrid.prefixe((trieHybrid), string) + "\n");
			}
			if (patriciatrie != null) {
				textAreaPatricia.appendText("- La nombre de mots la structure avec le prefix " + "***" + string
						+ "*** est " + patriciatrie.prefixe((patriciatrie), string) + "\n");
			}
		}

	}

	public void drawTreeHandler(ActionEvent event) throws FileNotFoundException, IOException, InterruptedException {
		try {
			final DoubleProperty zoomProperty = new SimpleDoubleProperty(200);
			if(stage == null ) stage = new Stage();
			//stage.close();
			ImageView image = new ImageView();
			hybride_graphe();
			InputStream is = new BufferedInputStream(new FileInputStream("images/hybrid.jpeg"));
			Image img = new Image(is);
			ScrollPane scrollp = new ScrollPane();
			image.setPreserveRatio(true);
			zoomProperty.addListener(new InvalidationListener() {
				@Override
				public void invalidated(javafx.beans.Observable args) {
					image.setFitWidth(zoomProperty.get() * 4);
					image.setFitHeight(zoomProperty.get() * 3);
				}
			});
			scrollp.addEventFilter(ScrollEvent.ANY, new EventHandler<ScrollEvent>() {
				@Override
				public void handle(ScrollEvent event) {
					if (event.getDeltaY() > 0) {
						zoomProperty.set(zoomProperty.get() * 1.1);
					} else if (event.getDeltaY() < 0) {
						zoomProperty.set(zoomProperty.get() / 1.1);
					}
				}
			});
			image.setImage(img);
			scrollp.setContent(image);
			stage.setScene(new Scene(scrollp, img.getWidth()+10, img.getHeight()+10));
			stage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public void drawPatriciaHandler(ActionEvent event) throws FileNotFoundException, IOException, InterruptedException {
		try {
			final DoubleProperty zoomProperty = new SimpleDoubleProperty(200);
			if(stagePatricia == null ) stagePatricia = new Stage();
			//stage.close();
			ImageView image = new ImageView();
			patricia_graphe();
			InputStream is = new BufferedInputStream(new FileInputStream("images/patricia.jpeg"));
			Image img = new Image(is);
			ScrollPane scrollp = new ScrollPane();
			image.setPreserveRatio(true);
			zoomProperty.addListener(new InvalidationListener() {
				@Override
				public void invalidated(javafx.beans.Observable args) {
					image.setFitWidth(zoomProperty.get() * 4);
					image.setFitHeight(zoomProperty.get() * 3);
				}
			});
			scrollp.addEventFilter(ScrollEvent.ANY, new EventHandler<ScrollEvent>() {
				@Override
				public void handle(ScrollEvent event) {
					if (event.getDeltaY() > 0) {
						zoomProperty.set(zoomProperty.get() * 1.1);
					} else if (event.getDeltaY() < 0) {
						zoomProperty.set(zoomProperty.get() / 1.1);
					}
				}
			});
			image.setImage(img);
			scrollp.setContent(image);
			stagePatricia.setScene(new Scene(scrollp, img.getWidth()+10, img.getHeight()+10));
			stagePatricia.show();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void hybride_graphe() throws InterruptedException {

		try {

			long debut = System.currentTimeMillis();
			File file = new File("images/hybrid.dot");
			FileWriter writer = new FileWriter(file);

			writer.write("digraph Test{ \n");
			HybridToFile.ToFile(trieHybrid, writer, "r");// ToFile(hybrid, writer, "r");
			writer.write("}");
			writer.close();

			long fin = System.currentTimeMillis();
			System.out.println(fin - debut);
			

			String[] cmd = { "/bin/bash", "-c", " dot -Tjpeg images/hybrid.dot -o images/hybrid.jpeg" };
			Runtime.getRuntime().exec(cmd);
			
			Thread.sleep(1000);

		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public void patricia_graphe() throws InterruptedException {

		try {

			long debut = System.currentTimeMillis();
			File file = new File("images/patricia.dot");
			FileWriter writer = new FileWriter(file);

			writer.write("digraph Test{ \n");
			writer.write("node[shape=record]\n");
			HybridToFile.ToFile(patriciatrie, writer, "r");// ToFile(hybrid, writer, "r");
			writer.write("}");
			writer.close();
			
			long fin = System.currentTimeMillis();
			System.out.println(fin - debut);
			
			String[] cmd = { "/bin/bash", "-c", " dot -Tjpeg images/patricia.dot -o images/patricia.jpeg" };
			Runtime.getRuntime().exec(cmd);
			
			Thread.sleep(1000);

		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public void deleteWordHandler(ActionEvent event) {
		String [] words = textArea.getText().split(" ");
		if (patriciatrie != null) {
			for (String string : words) {
				patriciatrie.suppression(patriciatrie, string);
				textAreaPatricia.appendText("- Le mot "+string+" a bien été supprimé"+"\n");
			}
			
		}
		if (trieHybrid != null) {
			for (String string : words) {
				trieHybrid.supprimer_Mot(trieHybrid, string);
				textAreaHybrid.appendText("- Le mot "+string+" a bien été supprimé"+"\n");
			}
		}
	}
	public void reequilibrerHybridandler(ActionEvent event) throws FileNotFoundException, IOException, InterruptedException {
		if (trieHybrid != null) {
			trieHybrid =  trieHybrid.equilibrer(trieHybrid);
			drawTreeHandler(event);
		}
	}
	
	public void profondeurHandler(ActionEvent event) {
		if (patriciatrie != null) {
			textAreaPatricia.appendText("- La profondeur moyenne de la structure est " + patriciatrie.profendeur_moyenne()+"\n");
		}
		if (trieHybrid != null) {
			textAreaHybrid.appendText("- La profondeur moyenne de la structure est " + trieHybrid.profendeur_moyenne()+"\n");
		}
	}
}
