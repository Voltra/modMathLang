package langModel;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;


/**
 * Class NgramUtils: class containing useful functions to deal with n-grams.
 * 
 * @author N. Hernandez and S. Quiniou (2017)
 *
 */
public class NgramUtils {

	/**
	 * Method counting the number of words in a given sequence 
	 * (the sequence can be a n-gram or a sentence).
	 * 
	 * @param sequence the sequence to consider.
	 * @return the number of words of the given sequence.
	 */
	public static int getSequenceSize (String sequence) {
	    return Arrays.asList(sequence.split("\\s+")).stream()
		.filter(Objects::nonNull)
		.filter(str -> !str.matches("\\s*"))
        //.peek(System.out::println)
        .collect(Collectors.toList())
		.size();
	}

	
	/**
	 * Method parsing a n-gram and returning its history, i.e. the n-1 preceding words.
	 * 
	 * Example: 
	 *   let the ngram be "l' historique de cette phrase";
	 *   the history will be given for the last word of the ngram, here "phrase":
	 *   if the order is 2 then the history will be "cette"; 
	 *   if the order is 3 then it will be "de cette".
	 * 
	 * @param ngram the n-gram to consider.
	 * @param order the order to consider for the n-gram.
	 * @return history of the given n-gram (the length of the history is order-1).  
	 */
	public static String getHistory (String ngram, int order) {
	    if (ngram.split("(\\s+)").length <= 1)
	        return "";

	    String ret = "";
        List<String> seq = Arrays.stream(ngram.split("(\\s+)")).filter(str -> !str.equals(" ")).collect(Collectors.toList());
		seq.remove(seq.size()-1);

        for (int i = seq.size() - order +1; i < seq.size(); i++)
            ret += " " + seq.get(i);

        return ret;
	}


	/**
	 * Method decomposing the given sentence into n-grams of the given order.
	 * 
	 * This method will be used in the LanguageModelInterface class for computing 
	 * the probability of a sentence as the product of the probabilities of its n-grams. 
	 * 
	 * Example
	 * given the sentence "a b c d e f g", with order=3,
	 * it will result in the following list:
	 * [a, a b, a b c, b c d, c d e, d e f, e f g] 
	 * 
	 * @param sentence the sentence to consider.
	 * @param order the maximal order for the n-grams to create from the sentence.
	 * @return the list of n-grams constructed from the sentence.
	 */
	public static List<String> decomposeIntoNgrams (String sentence, int order) {
        List<String> sentenceArray = Arrays.asList(sentence.split("(\\s+)")).stream().filter(str -> !str.equals(" ")).collect(Collectors.toList());
        ArrayList<String> ret = new ArrayList<>();
        int last = 0;

        for (int i = 1; i < order+1; i++) {
            String tmp = "";
            for (int j = 0; j < i; j++)
                tmp += " "+sentenceArray.get(j);
            ret.add(tmp.trim());
            last = i;
        }

        for (int i = last+1; i+order-2 < sentenceArray.size(); i++) {
            String tmp = "";
            for (int j = last; j < i+order-1; j++)
                tmp += " "+sentenceArray.get(j);
            ret.add(tmp.trim());
            last += 1;
        }

	    return ret;
	}
	
	
	/**
	 * Method parsing the given sentence and generate all the combinations of ngrams,
	 * by varying the order n between the given minOrder and maxOrder.
	 * 
	 * This method will be used in the NgramCount class for counting the ngrams 
	 * occurring in a corpus.
	 * 
	 * Algorithm (one possible algo...)
	 * initialize list of ngrams
	 * for n = minOrder to maxOrder (for each order)
	 * 	 for i = 0 to sentence.length-n (parse the whole sentence)
	 *     initialize ngram string parsedSentence
	 *     for j = i to i+n-1 (create a ngram made of the following sequence of words starting from i to i + the order size)
	 *       ngram = ngram + " " + sentence[j]
	 *     add ngramm to list ngrams 
	 * return list ngrams
	 * 
	 * Example
	 * given the sentence "a b c d e f g", with minOrder=1 and maxOrder=3, it will result in the following list:
	 * [a, b, c, d, e, f, g, a b, b c, c d, d e, e f, f g, a b c, b c d, c d e, d e f, e f g]
	 * 
	 * @param sentence the sentence from which to generate n-grams.
	 * @param minOrder the minimal order of the n-grams to create.
	 * @param maxOrder the maximal order of the n-grams to create.
	 * @return a list of generated n-grams from the sentence.
	 */
	public static List<String> generateNgrams (String sentence, int minOrder, int maxOrder) {
        List<String> sentenceArray = Arrays.asList(sentence.split("(\\s+)")).stream().filter(str -> !str.equals(" ")).collect(Collectors.toList());
        ArrayList<String> ret = new ArrayList<>();

        for (int n = minOrder+1; n <= maxOrder+1; n++) {
            for (int i = 0; i <= sentenceArray.size()-n+1; i++) {
                String tmp = "";
                for (int j = i; j < i+n-1; j++)
                    tmp += " "+sentenceArray.get(j);
                ret.add(tmp);
            }
        }

        return ret;
	}
	
	/**
	 * Method parsing a sequence of words and returning the modified string where
	 * out-of-vocabulary words are replaced with the OOV tag.
	 * 
	 * @param s the string to consider.
	 * @param vocab the vocabulary to consider.
	 * @return the sequence of words with OOV tags according to the vocabulary. 
	 */
	public static String getStringOOV(String s, VocabularyInterface vocab) {
        List<String> sentenceArray = Arrays.asList(s.split("(\\s+)")).stream().filter(str -> !str.equals(" ")).collect(Collectors.toList());
        String ret = "";

        for (String word : sentenceArray) {
            if (vocab.contains(word))
                ret += " "+word;
            else ret += " "+Vocabulary.OOV_TAG;
        }

        return  ret;
    }

}
