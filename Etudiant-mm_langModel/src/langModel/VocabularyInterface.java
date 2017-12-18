package langModel;


import java.io.IOException;
import java.util.Set;


/**
 * Interface VocabularyInterface: interface to represent the vocabulary associated to a language model 
 * (it can be made of words or characters).
 * 
 * @author N. Hernandez and S. Quiniou (2017)
 *
 */
public interface VocabularyInterface {

	/**
	 * Tags for special words.
	 */
	public static String OOV_TAG = "<unk>"; //out-of-vocabulary words
	public static String SOS_TAG = "<s>"; //start of sentence tag
	public static String EOS_TAG = "</s>"; //end of sentence tag
	
	
	
	/**
	 * Getter of the size of the vocabulary 
	 * 
	 * @return the number of words in the vocabulary.
	 */
	public int getSize();
	
	
	/**
	 * Method returning the list of words of the vocabulary.
	 * 
	 * @return the words of the vocabulary.
	 */
	public Set<String> getWords();

	
	/**
	 * Method testing if the word is present in the vocabulary.
	 * 
	 * @param word the word to consider.
	 * @return true if the word is in the vocabulary, else otherwise.
	 */
	public boolean contains (String word);
	
	
	/**
	 * Method adding a word to the vocabulary.
	 * 
	 * @param word the word to add.
	 */
	public void addWord(String word);
	
	
	/**
	 * Method removing a word from the vocabulary.
	 * 
	 * @param word the word to remove.
	 */
	public void removeWord (String word);
	
	
	/**
	 * Method parsing the given set of n-grams and, for each n-gram, listing 
	 * its words and adding them to the vocabulary.
	 * The set of n-grams can come from the set of n-grams present in a NgramCountsInterface object.
	 * 
	 * @param ngramSet the set of n-grams whose words to add to the vocabulary.
	 */
	public void scanNgramSet (Set<String> ngramSet);
	

	/**
	 * Method reading a vocabulary from a file containing one word per line.
	 * 
	 * @param filePath the path of the file containing the vocabulary.
	 */
	public void readVocabularyFile (String filePath) throws IOException;

	
	/**
	 * Method writing a vocabulary to a file with one word per line.
	 * 
	 * @param filePath the path of the file o contain the vocabulary.
	 */
	public void writeVocabularyFile (String filePath);
	
}
