package authorEval;


import java.util.HashMap;
import java.util.Map;
import java.util.List;

import langModel.*;



/**
 * Class RecognizerPerformance: class to compute the performance of a recognition system.
 * @author N. Hernandez and S. Quiniou (2017)
 *
 */
public class RecognizerPerformance {

	/**
	 * Method using the sentences given in two files to compute the accuracy of a recognition system, i.e. the number
	 * of sentences whose author is correctly recognized by the system.
	 * 
	 * The first file contains the author annotation of each sentence of a reference file and the second
	 * file contains the hypothesis author of each reference sentence as given by the recognition system whose
	 * performance is evaluated. 
	 * 
	 * @param refAuthorPath the path of the file containing the reference annotation of sentences of the reference file.
	 * @param hypAuthorPath the path of the file containing the hypothesis sentences given by a recognition system. 
	 * @return the accuracy of the recognition system.
	 */
	public static double evaluate (String refAuthorPath, String hypAuthorPath) {
		List<String> ref = MiscUtils.readTextFileAsStringList(refAuthorPath);
		List<String> hyp = MiscUtils.readTextFileAsStringList(hypAuthorPath);
		
		return evaluate(ref,hyp);	
	}
	
	
	/**
	 * Method using the sentences given in two lists to compute the accuracy of a recognition system, i.e. the number
	 * of sentences whose author is correctly recognized by the system.
	 * 
	 * The first list contains the author annotation of each sentence of a reference list and the second
	 * list contains the hypothesis author of each reference sentence as given by the recognition system whose
	 * performance is evaluated. 
	 * 
	 * @param refAuthor the list containing the reference annotation of the sentences of a reference list.
	 * @param hypAuthor the list containing the hypothesis sentences given by a recognition system. 
	 * @return the accuracy of the recognition system.
	 */
	public static double evaluate (List<String> refAuthor, List<String> hypAuthor) {
		if (refAuthor.size() != hypAuthor.size()) {
			System.err.println("Error: ref and hyp lists must have the same number of sentences");
			return -1.0;
		}
		
		Double correct = 0.0;
		for (int i = 0 ; i < refAuthor.size() ; i++ ) 
			if (refAuthor.get(i).equalsIgnoreCase(hypAuthor.get(i))) 
				correct++;

		return (double) correct/ refAuthor.size();	
	}
	
	
	
	/**
	 * Method using the sentences given in two files to compute the accuracy of a recognition system for
	 * each author, i.e. the number of sentences whose author is correctly recognized by the system.
	 * 
	 * The first file contains the author annotation of each sentence of a reference file and the second
	 * file contains the hypothesis author of each reference sentence as given by the recognition system whose
	 * performance is evaluated. 
	 * 
	 * @param refAuthorPath the path of the file containing the reference annotation of a sentences of the reference file.
	 * @param hypAuthorPath the path of the file containing the hypothesis sentences given by a recognition system. 
	 * @return a String containing the accuracy of the recognition system for each author.
	 */
	public static String evaluateAuthors (String refAuthorPath, String hypAuthorPath) {
		
		List<String> gold = MiscUtils.readTextFileAsStringList(refAuthorPath);
		List<String> hyp = MiscUtils.readTextFileAsStringList(hypAuthorPath);
		
		return evaluateAuthors(gold,hyp);	
	}
	
	
	/**
	 * Method using the sentences given in two lists to compute the accuracy of a recognition system for
	 * each author, i.e. the number of sentences whose author is correctly recognized by the system.
	 * 
	 * The first list contains the author annotation of each sentence of a reference list and the second
	 * list contains the hypothesis author of each reference sentence as given by the recognition system whose
	 * performance is evaluated. 
	 * 
	 * @param refAuthor the list containing the reference annotation of the sentences of a reference list.
	 * @param hypAuthor the list containing the hypothesis sentences given by a recognition system. 
	 * @return a String containing the accuracy of the recognition system for each author.
	 */
	public static String evaluateAuthors (List<String> refAuthor, List<String> hypAuthor) {
		if (refAuthor.size() != hypAuthor.size()) {
			System.err.println("Error: gold and hyp files must have the same number of lines");
			return "ERROR : refAuthor has " + refAuthor.size() + " lines and hypAuthor has " + hypAuthor.size() + " lines";
		}		
		
		//computes the number of correct sentences for each author
		int nbCorrect = 0;
		Map<String, Integer> nbSentAuthor = new HashMap<String, Integer>();
		Map<String, Integer> nbCorrectAuthor = new HashMap<String, Integer>();
		String lang;
		
		for(int i=0; i<refAuthor.size(); i++){
			lang = refAuthor.get(i);
			//increments the number of sentences of the current gold author
			if(nbSentAuthor.containsKey(lang)){
				nbSentAuthor.put(lang, nbSentAuthor.get(lang)+1);
			}
			else{
				nbSentAuthor.put(lang, 1);
			}
			//increments the number of correct sentences of the current gold author if equal to the current hyp author
			if(hypAuthor.get(i).equalsIgnoreCase(lang)){
				nbCorrect ++;
				if(nbCorrectAuthor.containsKey(lang)){
					nbCorrectAuthor.put(lang, nbCorrectAuthor.get(lang)+1);
				}
				else{
					nbCorrectAuthor.put(lang, 1);
				}
			}
		}
		
		//sets up the result string with the results
		String str_res = "";
		double score;
		
		for(String l : nbSentAuthor.keySet()){
			if(nbCorrectAuthor.containsKey(l))
				score = (double) nbCorrectAuthor.get(l) / nbSentAuthor.get(l);
			else
				score = 0;
			str_res += l + " : " + score + " (" + nbSentAuthor.get(l) + ")\n";
		}
		score = (double) nbCorrect / refAuthor.size();
		str_res += "-> total : " + score + "\n";
		
		return str_res;
	}

}
