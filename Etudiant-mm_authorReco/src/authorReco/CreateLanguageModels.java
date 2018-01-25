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
	    String VOCAB = "src/lm/small_author_corpus/corpus_20000.vocab";
        File folder = new File("data/author_corpus/train");
        File[] listOfFiles = folder.listFiles();

        Vocabulary vocab = new Vocabulary();
        vocab.readVocabularyFile(VOCAB);
        for (File file : listOfFiles) {
            if (file.isFile()) {
                System.out.println("Processing file " + file.getName());
                NgramCounts ngramCounts = new NgramCounts();
                ngramCounts.scanTextFile(file.getPath(), vocab, 3);
                String outputPath = "data/test/models/";
                //vocab.writeVocabularyFile(outputPath+file.getName()+".vocab");
                ngramCounts.writeNgramCountFile(outputPath+file.getName()+".ngram");
            }
        }
	}

}
