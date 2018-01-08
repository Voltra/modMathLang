package authorReco;


import java.util.*;

import authorEval.*;
import langModel.*;


/**
 * Class AuthorRecognizer1: a first author recognition system that recognizes 
 * the author of a sentence by using the language models read from a configuration system.
 * (no unknown author can be detected)
 * 
 * @author N. Hernandez and S. Quiniou (2017)
 *
 */
public class AuthorRecognizer1 extends AuthorRecognizerAbstractClass {
	/**
	 * Map of LanguageModels associated with each author (each author can be 
	 * associated with several language models). 
	 * The keys to the first map are the names of the authors (e.g., "zola"), the keys 
	 * to the second map are the names associated with each file containing a language model 
	 * (e.g., "zola-bigrams"), and the values of the second map are the LanguageModel objects 
	 * built from the file path given in the AuthorConfigurationFile attribute.
	 */
	protected Map<String, Map<String, LanguageModelInterface>> authorLangModelsMap;
	
	
	
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
	public AuthorRecognizer1(String configFile, String vocabFile, String authorFile) {
		super();
		super.loadAuthorConfigurationFile(configFile);
		super.loadVocabularyFile(vocabFile);
		super.loadAuthorFile(authorFile);
	}
	
	
	
	/**
	 * Method recognizing and returning the author of the given sentence 
	 * (the unknown author can also be picked up).
	 * 
	 * @param sentence the sentence whose author is to recognize.
	 * @return the author of the sentence as recognized by the recognition system.
	 */
	public String recognizeAuthorSentence(String sentence) {
		AuthorConfigurationFile cfg = this.configLangModels;
		Map<String, Double> probabilities = this.getProbabilityMap(sentence, cfg);

        Map.Entry<String, Double> defaultEntry = new Map.Entry<String, Double>() {
            Double val = 0.0;
            @Override public String getKey() { return AuthorRecognizerAbstractClass.UNKNOWN_AUTHOR; }
            @Override public Double getValue() { return this.val; }
            @Override public Double setValue(Double value) { this.val = value; return value; }
        };

        return probabilities.entrySet().stream()
        .reduce(defaultEntry, (acc, elem) -> {
            if(elem.getValue() > acc.getValue())
                return elem;
            return acc;
        }).getKey();

		//return UNKNOWN_AUTHOR;
	}
	
	public Map<String, Double> getProbabilityMap(String sentence, AuthorConfigurationFile cfg){
        Map<String, Double> probabilities = new HashMap<>();

        for(String author : cfg.getAuthors()){
            Double bestProb = cfg.getNgramCountPath(author).stream()//Stream<String> :: lm paths
            .map(path -> { //Stream<NgramCountsInterface> :: ngram counts
                NgramCountsInterface n = new NgramCounts();
                n.readNgramCountsFile(path);
                return n;
            }).map(ngramCount -> { //Stream<LanguageModelInterface> :: lm from ngram counts
                LanguageModelInterface lm = new LaplaceLanguageModel();
                lm.setNgramCounts(ngramCount, super.getVocabularyLM());
                return lm;
            }).map(lm -> lm.getSentenceProb(sentence)) //Stream<Double> :: probability from the model
            //.peek(System.out::println)
            .reduce(Double::max) //Optional<Double> ::  best proba if one
            .orElse(0.0);//Double :: best proba (or 0)

            probabilities.put(author, bestProb);
        }

        return probabilities;
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
        final String OUTPUT = CURR_PATH + "/data/output/rec1.txt";
        final String REF = CURR_PATH + "/data/small_author_corpus/validation/authors_100sentences_ref.txt";
        final String VOCAB = CURR_PATH + "/lm/small_author_corpus/corpus_20000.vocab";
        final String AUTHOR_FILE = CURR_PATH + "/data/small_author_corpus/validation/authors.txt";
        AuthorRecognizerAbstractClass bar = new AuthorRecognizer1(CONFIG, VOCAB, AUTHOR_FILE);


        //computation of the hypothesis author file
        bar.recognizeFileLanguage(FILE, OUTPUT);


        //computation of the performance of the recognition system
        System.out.println( RecognizerPerformance.evaluateAuthors(REF, OUTPUT) );
	}
}
