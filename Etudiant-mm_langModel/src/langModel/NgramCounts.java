package langModel;

import java.io.IOException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;


/**
 * Class NgramCounts: class implementing the interface NgramCountsInterface. 
 * 
 * @author N. Hernandez and S. Quiniou (2017)
 *
 */
@SuppressWarnings({"unused", "unchecked", "WeakerAccess"})
public class NgramCounts implements NgramCountsInterface {
	/**
	 * The maximal order of the n-gram counts.
	 */
	protected int order;

	/**
	 * The map containing the counts of each n-gram.
	 */
	protected Map<String, Integer> ngramCounts;

	/**
	 * The total number of words in the corpus.
	 * In practice, the total number of words will be increased when parsing a corpus 
	 * or when parsing a NgramCountsInterface file (only if the ngram encountered is a unigram one).
	 */
	protected int nbWordsTotal;
	
	
	/**
	 * Constructor.
	 */
	public NgramCounts(){
		this.ngramCounts = new HashMap<>();
		this.setMaximalOrder(0);
        this.nbWordsTotal = 0;
	}


	/**
	 * Setter of the maximal order of the ngrams considered.
	 * 
	 * In practice, the method will be called when parsing the training corpus, 
	 * or when parsing the NgramCountsInterface file (using the maximal n-gram length encountered).
	 * 
	 * @param order the maximal order of n-grams considered.
	 */
	private void setMaximalOrder (int order) {
		this.order = order;
	}

	
	@Override
	public int getMaximalOrder() {
		return this.order;
	}

	
	@Override
	public int getNgramCounterSize() {
		return this.ngramCounts.size();
	}

	protected int computeWordsTotal(){
	    return this.ngramCounts.values()
        .stream()
        .reduce(0, Integer::sum);
    }

    protected void updateWordsTotal(){
	    this.nbWordsTotal = this.computeWordsTotal();
    }

	@Override
	public int getTotalWordNumber(){
	    this.updateWordsTotal();
		return this.nbWordsTotal;
	}
	
	
	@Override
	public Set<String> getNgrams() {
		return this.ngramCounts.keySet();
	}

	
	@Override
	public int getCounts(String ngram) {
		NoNullParams.assertNoneNull(ngram);

		return this.ngramCounts.getOrDefault(ngram.toLowerCase(), 0);
	}

	public Map<String, Integer> getNgramCounts(){
	    return new HashMap<>(this.ngramCounts);
    }
	

	@Override
	public void incCounts(String ngram) {
		NoNullParams.assertNoneNull(ngram);

		int currCount = this.getCounts(ngram.toLowerCase());
		this.setCounts(ngram.toLowerCase(), currCount+1);
	}

	
	@Override
	public void setCounts(String ngram, int counts) {
		NoNullParams.assertNoneNull(ngram, counts);

		if(this.ngramCounts.containsKey(ngram.toLowerCase()))
		    this.ngramCounts.replace(ngram.toLowerCase(), counts);
		else
		    this.ngramCounts.put(ngram.toLowerCase(), counts);
	}


	@Override
	public void scanTextFile(String filePath, VocabularyInterface vocab, int maximalOrder) throws IOException {
		NoNullParams.assertNoneNull(filePath, vocab, maximalOrder);

		Map<String, Integer> ngramCounts = MiscUtils.readTextFileAsStringList(filePath)
        .stream()//Stream<String> :: lines
        .filter(Objects::nonNull)//Stream<String> :: remove null objects
        .filter(line -> !line.matches("\\s+"))//Stream<String> :: remove "whitespaces only" strings
        .map(String::toLowerCase)//Stream<String> :: set to lowercase
        .map(line -> NgramUtils.getStringOOV(line, vocab))//Stream<String> :: replace unknown tokens by the unknown token (<unk>)
        .map(line -> NgramUtils.decomposeIntoNgrams(line, maximalOrder))//Stream<List<String>> :: decompose a line into ngams
        .filter(Objects::nonNull)//Stream<List<String>> :: remove null objects
        .map(ngrams -> {
            Map<String, Integer> map = new HashMap<>();
            ngrams.stream()//Stream<String> ::  ngrams
            .filter(Objects::nonNull)//Stream<String> :: remove nulls
            .forEach(ngram -> {
                if(!map.containsKey(ngram))
                    map.put(ngram, 0);

                int count = map.get(ngram);
                map.replace(ngram, count+1);
            });//Stream<String> :: insert ngram counts

            return map;
        })//Stream<HashMap<String, Integer>> :: transform ngrams to ngram counts
        .reduce(new HashMap<>(), (acc, elem)->{
            elem.entrySet()//Set<Map.Entry<String, Integer>> :: ngram counts
			.forEach(entry -> {
                if(!acc.containsKey(entry.getKey()))
                    acc.put(entry.getKey(), entry.getValue());
                else{
                    int oldCount = acc.get(entry.getKey());
                    acc.replace(entry.getKey(), oldCount + entry.getValue());
                }
            });//Set<Entry<String, Integer>> ::  merge all ngram counts

            return acc;
        });//HashMap<String, Integer> :: processed merged ngramCounts

		this.ngramCounts = ngramCounts;
		this.updateWordsTotal();
		this.setMaximalOrder(maximalOrder);
    }


    @Override
	public void writeNgramCountFile(String filePath) {
		NoNullParams.assertNoneNull(filePath);

		String fileContent = this.ngramCounts.entrySet()//Set<Entry<String, Integer>> :: ngram counts
        .stream()//Stream<Entry<String, Integer>> :: ngram counts
        .map(entry -> entry.getKey() + "\t" + entry.getValue() + "\n")//Stream<String> ::  converted to its string representation
        .reduce("", String::concat);//String :: concatenate ngram counts

		MiscUtils.writeFile(fileContent, filePath, false);
	}

	
	@Override
	public void readNgramCountsFile(String filePath) throws IOException {
	    NoNullParams.assertNoneNull(filePath);

	    final String re = "^([^\\d]+)\\t(\\d+)$";
	    Pattern pre = Pattern.compile(re);
		List<String> fromFile = MiscUtils.readTextFileAsStringList(filePath)
        .stream()//List<String>
        .filter(Objects::nonNull)//List<String>
        .filter(line -> line.matches(re))//List<String>
        .filter(line -> pre.matcher(line).matches())//List<String>
        .filter(line -> pre.matcher(line).groupCount()==2)//List<String>
        .collect(Collectors.toList());//List<String>

		Map<String, Integer> ngramCounts = new HashMap<>();
		for(String line : fromFile){
            Matcher m = pre.matcher(line);
            String ngram = m.group(1);
            String amount_str = m.group(2);
            int amount;

            try{
                amount = Integer.parseInt(amount_str);
            }catch(Throwable t){
                continue;
            }

            ngramCounts.put(ngram.toLowerCase(), amount);
        }

        this.updateWordsTotal();
		this.ngramCounts = ngramCounts;
		this.order = ngramCounts.keySet().stream()
        .map(ngram -> ngram.split("\\s+").length)
        .reduce(0, Integer::max);
	}

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof NgramCounts)) return false;
        NgramCounts that = (NgramCounts) o;
        return order == that.order &&
                nbWordsTotal == that.nbWordsTotal &&
                Objects.equals(ngramCounts, that.ngramCounts);
    }

    @Override
    public int hashCode() {
        return Objects.hash(order, ngramCounts, nbWordsTotal);
    }
}
