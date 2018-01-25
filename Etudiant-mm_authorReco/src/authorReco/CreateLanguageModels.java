package authorReco;


import langModel.*;

import java.io.File;
import java.util.List;


/**
 * Class CreateLanguageModels: a class to create the language models used in the recognition systems.
 * 
 * @author N. Hernandez and S. Quiniou (2017)
 *
 */
public class CreateLanguageModels {

	/**
	 * Main method.
	 * 
	 * @param args arguments of the main method.
	 */
	public static void main(String[] args) {
        File folder = new File("data/author_corpus/train");
        File[] listOfFiles = folder.listFiles();

        for (File file : listOfFiles) {
            if (file.isFile()) {
                System.out.println("Processing file " + file.getName());
                Vocabulary vocab = new Vocabulary();
                vocab.readVocabularyFile(file.getPath());
                NgramCounts ngramCounts = new NgramCounts();
                ngramCounts.scanTextFile(file.getPath(), vocab, 3);
                String outputPath = "src/lm/test/";
                vocab.writeVocabularyFile(outputPath+file.getName()+".vocab");
                ngramCounts.writeNgramCountFile(outputPath+file.getName()+".ngram");
            }
        }
	}

}
