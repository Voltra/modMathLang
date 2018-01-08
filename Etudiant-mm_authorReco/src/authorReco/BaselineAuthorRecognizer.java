package authorReco;


import java.util.*;

import authorEval.*;


/**
 * Class BaselineAuthorRecognizer: a baseline author recognition system that "recognizes" 
 * the author of a sentence by randomly choosing one author recognized by the recognition system.
 * 
 * @author N. Hernandez and S. Quiniou (2017)
 *
 */
public class BaselineAuthorRecognizer extends AuthorRecognizerAbstractClass {
	/**
	 * Random generator to pick up an author.
	 */
	protected Random rand;

	

	/**
	 * Constructor.
	 * 
	 * @param authorFile the file path of the file containing 
	 * the names of the authors recognized by the system.
	 */
	public BaselineAuthorRecognizer(String authorFile) {
		super();
		loadAuthorFile(authorFile);
		//initializes the random generator attribute
		rand = new Random(System.currentTimeMillis());
	}

	
	
	/**
	 * Method recognizing and returning the author of the given sentence 
	 * (the unknown author can also be picked up).
	 * 
	 * @param sentence the sentence whose author is to recognize.
	 * @return the author of the sentence as recognized by the recognition system.
	 */
	public String recognizeAuthorSentence(String sentence) {	
		return authors.get(rand.nextInt(authors.size()));
	}
	
	
	
	/**
	 * Main method.
	 * 
	 * @param args arguments of the main method.
	 */
	public static void main(String[] args) {
		//initialization of the recognition system
		final String CURR_PATH = System.getProperty("user.dir");
		final String CONFIG = CURR_PATH + "/lm/small_author_corpus/fichConfig_bigram_1000sentences.txt";
		final String FILE = CURR_PATH + "/data/small_author_corpus/validation/sentences_100sentences.txt";
		final String OUTPUT = CURR_PATH + "/data/output/baseline.txt";
		final String REF = CURR_PATH + "/data/small_author_corpus/validation/authors_100sentences_ref.txt";
		AuthorRecognizerAbstractClass bar = new BaselineAuthorRecognizer(CONFIG);
		
		
		//computation of the hypothesis author file
        bar.recognizeFileLanguage(FILE, OUTPUT);
		

		//computation of the performance of the recognition system
        double perf = RecognizerPerformance.evaluate(REF, OUTPUT);
        System.out.println("perf: " + perf);
	}
}
