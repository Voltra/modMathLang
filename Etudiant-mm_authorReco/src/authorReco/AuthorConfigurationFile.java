package authorReco;


import java.util.*;

import langModel.*;


/**
 * Abstract AuthorConfigurationFile: class representing a configuration file indicating the file paths of the author language models.
 * 
 * @author N. Hernandez and S. Quiniou (2017)
 *
 */
public class AuthorConfigurationFile {
	/**
	 * Map of ngram counter file paths associated with each author (each author can be 
	 * associated with several language models). 
	 * The keys to the first map are the first names of the authors in lowercase (e.g., "zola"), the keys 
	 * to the second map are the names associated with each file containing a language model 
	 * (e.g., "zola-bigrams"), and the values of the second map are the file paths associated with the given author name
	 * (e.g., "ngramCounts/zola-bigrams.lm").
	 */
	protected Map<String,Map<String,String>> authorNgramCountMap;


	
	/**
	 * Constructor.
	 */
	public AuthorConfigurationFile() {
		authorNgramCountMap = new HashMap<String,Map<String,String>>();
	}

	
	/**
	 * Accessor to the authorNgramCountMap attribute.
	 * 
	 * @return the map of author file paths of the recognition system.
	 */	
	protected Map<String,Map<String,String>> getAuthorNgramCountMap() {
		return this.authorNgramCountMap;
	}
	
	
	/**
	 * Accessor to the authors of the authorNgramCountMap attribute.
	 * 
	 * @return the set of authors for which at least one file path is declared.
	 */
	protected Set<String> getAuthors() {
		return getAuthorNgramCountMap().keySet();
	}
	
	
	/**
	 * Accessor to the language model names of a particular author in the authorNgramCountMap attribute.
	 * 
	 * @param author the author to consider.
	 * @return the set of language model names for the given author.
	 */
	protected Set<String> getNgramCountNames(String author) {
		return getAuthorNgramCountMap().get(author).keySet();
	}
	
	
	/**
	 * Method returning a collection of language model file paths associated with an author.
	 * 
	 * @param author the author to consider.
	 * @return the collection of language model files for the given author.
	 */
	protected Collection<String> getNgramCountPath(String author) {
		return getAuthorNgramCountMap().get(author).values();
	}
	
	
	/**
	 * Accessor to the language model file path of a particular language model name in the authorNgramCountMap attribute.
	 * 
	 * @param author the author to consider.
	 * @param ngramCountName the name of the language model to consider.
	 * @return the file path of the language model whose language and model name are given.
	 */
	protected String getNgramCountPath(String author, String ngramCountName) {
		return getAuthorNgramCountMap().get(author).get(ngramCountName);
	}
	
	
	/**
	 * Method adding a language model file path to the authorNgramCountMap attribute.
	 * 
	 * @param author the author to consider.
	 * @param ngramCountName the name of the language model to add.
	 * @param ngramCountFilePath the file path of the language model to add.
	 */
	private void addTuple2AuthorNgramCountMap(String author, String ngramCountName, String ngramCountFilePath) {
		Map<String,String> namePathMap = null;
		if (! getAuthorNgramCountMap().containsKey(author)) 
			 namePathMap = new HashMap<String,String>();
		else 
			namePathMap = getAuthorNgramCountMap().get(author);
		 namePathMap.put(ngramCountName, ngramCountFilePath);
		 getAuthorNgramCountMap().put(author,namePathMap);
	}
	
	
	/**
	 * Method parsing a configuration file where each line is a tuple in the following format:
	 * author ngramCountName ngramCountFilePath
	 * (each value is separated by a whitespace character; lines started with # are not considered)
	 * This method initialized the authorNgramCountMap attribute with the names and file paths of the 
	 * language models used in the recognition system.
	 * 
	 * @param configFile the file path of the configuration file containing the information 
	 * on the language models (author, name and file path).
	 */
	public void loadNgramCountPath4Lang (String configFile) {
		authorNgramCountMap.clear();
		
		List<String> configLines = MiscUtils.readTextFileAsStringList(configFile);

		for (String configLine : configLines) {
			if (!configLine.startsWith("#")) {
				String configLineParameters [] = configLine.split("\\s+");
				addTuple2AuthorNgramCountMap(configLineParameters[0], configLineParameters[1], configLineParameters[2]);
			}
		}
	}
	
}
