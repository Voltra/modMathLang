package langModel;


/**
 * Class NaiveLanguageModel: class implementing the interface LanguageModelInterface by creating a naive language model,
 * i.e. a n-gram language model with no smoothing.
 * 
 * @author ... (2017)
 *
 */
public class NaiveLanguageModel implements LanguageModelInterface {
	/**
	 * The NgramCountsInterface corresponding to the language model.
	 */
	protected NgramCountsInterface ngramCounts;
	
	/**
	 * The vocabulary of the language model.
	 */
	protected VocabularyInterface vocabulary;
	
	
	/**
	 * Constructor.
	 */
	public NaiveLanguageModel(){
		this.ngramCounts = new NgramCounts();
		this.vocabulary = new Vocabulary();
	}
	

	@Override
	public int getLMOrder() {
		return ngramCounts.getMaximalOrder();
	}

	@Override
	public void setNgramCounts(NgramCountsInterface ngramCounts, VocabularyInterface vocab) {
		this.ngramCounts = ngramCounts;
		this.vocabulary = vocab;
	}

	@Override
	public Double getNgramProb(String ngram) {
        if (ngram.equals(Vocabulary.SOS_TAG))
            return 1.0;
        else if (ngramCounts.getMaximalOrder() == 1 && !ngram.equals(Vocabulary.SOS_TAG))
            return new Double(ngramCounts.getCounts(ngram)/ngramCounts.getTotalWordNumber());
        else if (ngramCounts.getMaximalOrder() > 2 && !ngram.equals(Vocabulary.EOS_TAG) && ngramCounts.getCounts(ngram) != 0)
            return new Double(ngramCounts.getCounts(ngram)/ngramCounts.getCounts(NgramUtils.getHistory(ngram, ngramCounts.getMaximalOrder())));
        else return 0.0;
	}

	@Override
	public Double getSentenceProb(String sentence) {
		return NgramUtils.decomposeIntoNgrams(sentence, ngramCounts.getMaximalOrder()).stream().map(this::getNgramProb).reduce(1.0, (probaTotal, probaNgram) -> probaNgram*probaTotal);
	}

}
