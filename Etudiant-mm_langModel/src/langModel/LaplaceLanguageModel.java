package langModel;


import java.util.Objects;

/**
 * Class LaplaceLanguageModel: class inheriting the class NaiveLanguageModel by creating 
 * a n-gram language model using a Laplace smoothing.
 * 
 * @author ... (2017)
 *
 */
public class LaplaceLanguageModel extends NaiveLanguageModel {

	@Override
	public Double getNgramProb(String ngram) {
		NoNullParams.assertNoneNull(ngram);

		if(!NoNullParams.noneIsNull(this.ngramCounts, this.vocabulary))
		    return Double.NaN;

        int order = this.getLMOrder();
        System.out.println("order:"+order);
        System.out.println("ngram:"+ngram);
        double words_amount = this.vocabulary.getSize();
		String history = NgramUtils.getHistory(ngram, order);

		double ngram_occ = this.ngramCounts.getCounts(ngram);
        double hist_occ = this.ngramCounts.getCounts(history);

        if(hist_occ+words_amount-1 == 0)
            return Double.NaN;

        return Math.abs(
        		(1 + ngram_occ) / (hist_occ + words_amount - 1)
		);
	}
}
