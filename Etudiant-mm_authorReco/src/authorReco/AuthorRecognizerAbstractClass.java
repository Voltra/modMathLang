package authorReco;


import java.util.*;

import langModel.*;


/**
 * Abstract class AuthorRecognizerAbstractClass: abstract class that every author recognition system has to extend.
 * 
 * @author N. Hernandez and S. Quiniou (2017)
 *
 */
public abstract class AuthorRecognizerAbstractClass {
	/**
	 * the constant for unknown author names.
	 */
	public static final String UNKNOWN_AUTHOR = "unknown";
	
	
	/**
	 * The configuration file containing the information of the language models used in the recognition system.
	 */
	protected AuthorConfigurationFile configLangModels;
	
	/**
	 * The common vocabulary associated to the language models.
	 */
	protected VocabularyInterface vocabularyLM;
	
	/**
	 * List of authors recognized by the system. 
	 */
	protected List<String> authors; 
	
	

	/**
	 * Constructor.
	 */
	public AuthorRecognizerAbstractClass() {
		configLangModels = new AuthorConfigurationFile();
		vocabularyLM = new Vocabulary();
		authors = new LinkedList<String>();
	}
	

	
	/**
	 * Accessor to the configuration file.
	 * 
	 * @return the configuration file of the recognition system.
	 */
	public AuthorConfigurationFile getAuthorConfigurationFile() {
		return this.configLangModels;
	}
	
	
	/**
	 * Accessor to the vocabulary attribute.
	 * 
	 * @return the common vocabulary used by the language models of the recognition system.
	 */
	public VocabularyInterface getVocabularyLM() {
		return this.vocabularyLM;
	}
	
	
	/**
	 * Accessor to the authors attribute.
	 * 
	 * @return the list of authors recognized by the recognition system.
	 */
	public List<String> getAuthors() {
		return this.authors;
	}
	
	
	
	/**
	 * Method setting the configuration file, from a configuration file path.
	 * 
	 * @param configFile the file path of the configuration file containing the information 
	 * on the language models (author, name and file path).
	 */
	public void loadAuthorConfigurationFile(String configFile) {
		configLangModels.loadNgramCountPath4Lang(configFile);
	}


	/**
	 * Method setting the vocabulary, from a given file path.	
	 * 
	 * @param vocabFile the file path of the file containing the common vocabulary
	 * for all the language models used in the recognition system. 
	 */
	public void loadVocabularyFile(String vocabFile) {
		vocabularyLM.readVocabularyFile(vocabFile);
	}
	
	
	/**
	 * Method setting the authors, from a given file path.	
	 * 
	 * @param authorFile the file path of the file containing 
	 * the names of the authors recognized by the system. 
	 */
	public void loadAuthorFile(String authorFile) {
		authors.clear();
		
		List<String> authorLines = MiscUtils.readTextFileAsStringList(authorFile);
		for (String author : authorLines) {
			authors.add(author);
		}
	}
	
	
	
	/**
	 * Method recognizing and returning the author of the given sentence 
	 * (the unknown author can also be picked up).
	 * 
	 * @param sentence the sentence whose author is to recognize.
	 * @return the author of the sentence as recognized by the recognition system.
	 */
	public abstract String recognizeAuthorSentence(String sentence);

	

	/**
	 * Method processing the sentences of a given reference file (one per line),
	 * and writing the hypothesis author of each sentence in another file.
	 * 
	 * @param testSentenceFilePath the reference file containing the sentences whose author is to recognize.
	 * @param hypAuthorFilePath the hypothesis file of authors recognized by the recognition system.
	 */
	public void recognizeFileLanguage(String testSentenceFilePath, String hypAuthorFilePath) {
		//reads the sentences to recognize
		List<String> sentences = MiscUtils.readTextFileAsStringList(testSentenceFilePath);

		//predicts the author of each sentence
		String authorPredict;
		StringBuffer authorPrediction = new StringBuffer();

		int i = 1;
		for (String sentence: sentences) {
			authorPredict = recognizeAuthorSentence(sentence);
			authorPrediction.append(authorPredict);
			authorPrediction.append("\n");
			if(i % 1000 == 0)
				System.out.println("Phrases traitées jusqu'à " + i);
			i ++;
		}
		
		MiscUtils.writeFile(authorPrediction.toString(), hypAuthorFilePath, false);
	}	
}
