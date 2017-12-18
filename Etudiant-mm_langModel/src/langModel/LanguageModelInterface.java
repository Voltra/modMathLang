package langModel;


/**
 * Interface LanguageModelInterface: interface to describe a language model.
 * 
 * @author N. Hernandez and S. Quiniou (2017)
 *
 */
public interface LanguageModelInterface {

	/**
	 * Getter of the language model order.
	 * In practice it will get the maximal order of the NgramCountsInterface structure.
	 * 
	 * @return the maximal order of the language model.
	 */
	public int getLMOrder();

	
	/**
	 * Setter associating the current language model to a NgramCountsInterface object
	 * and the vocabulary to a VocabularyInterface object.
	 * 
	 * @param ngramCounts the NgramCountsInterface object to set.
	 * @param vocab the VocabularyInterface object to set.
	 */
	public void setNgramCounts(NgramCountsInterface ngramCounts, VocabularyInterface vocab);


	/**
	 * Method computing and returning the probability of the given n-gram,
	 * using the NgramCountsInterface structure.
	 * An implementation can consider the Laplace backoff smoothing.
	 * Another one can compute the log-probability instead of the probability...
	 * 
	 * @param ngram the n-gram whose probability to compute.
	 * @return the probability of the given n-gram.
	 */
	public Double getNgramProb (String ngram);

	
	/**
	 * Method computing and returning the probability of the given sentence,
	 * according to its n-grams.
	 * 
	 * @param sentence the sentence whose probability to compute.
	 * @return the probability of the given sentence.
	 */
	public Double getSentenceProb (String sentence);
}
