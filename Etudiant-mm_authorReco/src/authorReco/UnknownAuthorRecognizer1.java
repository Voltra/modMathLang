package authorReco;


import java.util.*;

import authorEval.*;
import langModel.*;


/**
 * Class UnknownAuthorRecognizer1: a first author recognition system that recognizes 
 * the author of a sentence by using the language models read from a configuration system.
 * (unknown authors can be detected)
 * 
 * @author N. Hernandez and S. Quiniou (2017)
 *
 */
public class UnknownAuthorRecognizer1 extends AuthorRecognizer1 {

	/**
	 * Constructor.
	 * 
	 * @param configFile the file path of the configuration file containing the information 
	 * on the language models (author, name and file path).
	 * @param vocabFile the file path of the file containing the common vocabulary
	 * for all the language models used in the recognition system. 
	 * @param authorFile the file path of the file containing 
	 * the names of the authors recognized by the system.
	 */
	public UnknownAuthorRecognizer1(String configFile, String vocabFile, String authorFile) {
		super(configFile, vocabFile, authorFile);
	}

	
	
	/**
	 * Method recognizing and returning the author of the given sentence 
	 * (the unknown author can also be picked up).
	 * 
	 * @param sentence the sentence whose author is to recognize.
	 * @return the author of the sentence as recognized by the recognition system.
	 */
	public String recognizeAuthorSentence(String sentence) {
		return authorIsUnknown(sentence) ? UNKNOWN_AUTHOR : super.recognizeAuthorSentence(sentence);
	}

	private double getSimilarityDelta(){ return Math.pow(10, -6);  }

	private boolean authorIsUnknown(String sentence){ //TODO: Find something that would actually work :/
		final double DELTA = this.getSimilarityDelta();
		Map<String, Double> probs = super.getProbabilityMap(sentence, this.configLangModels);
		double isSame = probs.entrySet().stream()
        .map(Map.Entry::getValue)
        .reduce(-404.0, (acc, elem)->{
            if(acc == -404.0)
                return elem;

            if(acc == -1.0)
                return acc;

            double inf = acc - DELTA;
            double sup = acc + DELTA;

            if(inf <= elem && elem <= sup)
                return elem;
            else
                return -1.0;
        });

		return isSame < 0;
	}

	
	
	/**
	 * Main method.
	 * 
	 * @param args arguments of the main method.
	 */
	public static void main(String[] args) {
		//initialization of the recognition system
		final String CURR_PATH = System.getProperty("user.dir");
		final String CONFIG = CURR_PATH + "/data/author_corpus/test/config.txt";
		final String FILE = CURR_PATH + "/data/author_corpus/test/sentences.txt";
		final String OUTPUT = CURR_PATH + "/data/author_corpus/test/authors-hyp1.txt";
		//final String REF = CURR_PATH + "/data/small_author_corpus/validation/authors_100sentences_ref.txt";
		final String VOCAB = CURR_PATH + "/src/lm/small_author_corpus/corpus_20000.vocab";
		final String AUTHOR_FILE = CURR_PATH + "/data/small_author_corpus/validation/authors.txt";
		AuthorRecognizerAbstractClass bar = new UnknownAuthorRecognizer1(CONFIG, VOCAB, AUTHOR_FILE);


		//computation of the hypothesis author file
		bar.recognizeFileLanguage(FILE, OUTPUT);
	}

}
