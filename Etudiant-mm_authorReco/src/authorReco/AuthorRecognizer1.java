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
		//TODO
	}
	
	
	
	/**
	 * Method recognizing and returning the author of the given sentence 
	 * (the unknown author can also be picked up).
	 * 
	 * @param sentence the sentence whose author is to recognize.
	 * @return the author of the sentence as recognized by the recognition system.
	 */
	public String recognizeAuthorSentence(String sentence) {
		//TODO
		return UNKNOWN_AUTHOR;
	}
	
	
	
	/**
	 * Main method.
	 * 
	 * @param args arguments of the main method.
	 */
	public static void main(String[] args) {
		//initialization of the recognition system
		
		
		//computation of the hypothesis author file
		
		
		//computation of the performance of the recognition system
		
	}
}
