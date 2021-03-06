package langModel;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;


/**
 * Class Vocabulary: class implementing the interface VocabularyInterface.
 * 
 * @author ... (2017)
 *
 */
@SuppressWarnings({"unused", "unchecked", "WeakerAccess"})
public class Vocabulary implements VocabularyInterface {
	/**
	 * The set of words corresponding to the vocabulary.
	 */
	protected Set<String> vocabulary;

	
	/**
	 * Constructor.
	 */
	public Vocabulary(){
		this.vocabulary = new HashSet<>();
	}
	
	
	@Override
	public int getSize() {
		return this.vocabulary.size();
	}

	@Override
	public Set<String> getWords() {
		return new HashSet<>(this.vocabulary);
	}

	@Override
	public boolean contains(String word) {
		NoNullParams.assertNoneNull(word);
		return this.vocabulary.contains(word.toLowerCase());
	}

	@Override
	public void addWord(String word) {
		NoNullParams.assertNoneNull(word);
		this.vocabulary.add(word.toLowerCase());
	}

	@Override
	public void removeWord(String word) {
		NoNullParams.assertNoneNull(word);
		this.vocabulary.remove(word.toLowerCase());
	}

	@Override
	public void scanNgramSet(Set<String> ngramSet) {
		NoNullParams.assertNoneNull(ngramSet);
		ngramSet.stream()
        .map(String::toLowerCase)
		.map(str -> Arrays.stream(str.split("\\s+")).filter(s -> !s.isEmpty()).collect(Collectors.toList()))
		.forEach(strs -> strs.forEach(this::addWord));
        //.forEach(this::addWord);
	}

	@Override
	public void readVocabularyFile(String filePath) throws IOException {
		NoNullParams.assertNoneNull(filePath);

		Set<String> set = new HashSet<>();
		MiscUtils.readTextFileAsStringList(filePath).stream()//Stream<String> :: lines
        .map(String::toLowerCase)//Stream<String> :: lines to lowercase
		.map(line -> NgramUtils.decomposeIntoNgrams(line, 1))//Stream<List<String>> :: lines to ngrams
		.filter(Objects::nonNull)//Stream<List<String>> :: remove nulls
		.forEach(set::addAll);//Stream<List<String>> :: add all ngrams to the tmp set
		this.scanNgramSet(set);
	}

	@Override
	public void writeVocabularyFile(String filePath) {
		NoNullParams.assertNoneNull(filePath);

		String fileContent = this.vocabulary.stream()//Stream<String> :: words
        .map(String::toLowerCase)//Stream<String> :: words to lowercase
        .map(str -> str+"\n")//Stream<String> :: words with a new line character
		.reduce("", String::concat);//String :: concatenate words

		MiscUtils.writeFile(fileContent, filePath, false);
	}

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Vocabulary)) return false;
        Vocabulary that = (Vocabulary) o;
        return Objects.equals(vocabulary, that.vocabulary);
    }

    @Override
    public int hashCode() {
        return Objects.hash(vocabulary);
    }
}
