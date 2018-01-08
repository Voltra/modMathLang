package langModel;

import langModel.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;


public class Application_LanguageModels {

	public static void main(String[] args) throws IOException {
		String sentence1 = "<s> antoine écoute une chanson </s>";
		String sentence2 = "<s> lionel écoute une chanson </s>";


		/* création des vocabulaires */
		
		VocabularyInterface vocab1, vocab2;
		
		//création du premier vocabulaire
		vocab1 = new Vocabulary();
		vocab1.readVocabularyFile("lm/small_corpus/vocabulary1_in.txt");
		
		
		//création du second vocabulaire
		vocab2 = new Vocabulary();
		vocab2.readVocabularyFile("lm/small_corpus/vocabulary2_in.txt");
		
		
		
		/* création des modèles de langage */
		
		NgramCountsInterface ngramCounts_bigram_vocab1, ngramCounts_bigram_vocab2;
		ngramCounts_bigram_vocab1 = new NgramCounts();
		ngramCounts_bigram_vocab2 = new NgramCounts();
		ngramCounts_bigram_vocab1.readNgramCountsFile("lm/small_corpus/ngramCounts_bigram_vocabulary1.txt");
		ngramCounts_bigram_vocab2.readNgramCountsFile("lm/small_corpus/ngramCounts_bigram_vocabulary2.txt");
		LanguageModelInterface lm_bigram_vocab1, lm_bigram_laplace_vocab1, lm_bigram_vocab2, lm_bigram_laplace_vocab2;
		
		//création du modèle de langage sans lissage, avec le premier vocabulaire
		lm_bigram_vocab1 = new NaiveLanguageModel();
		lm_bigram_vocab1.setNgramCounts(ngramCounts_bigram_vocab1, vocab1);
		
		
		//création du modèle de langage avec lissage, avec le premier vocabulaire
		lm_bigram_laplace_vocab1 = new LaplaceLanguageModel();
		lm_bigram_laplace_vocab1.setNgramCounts(ngramCounts_bigram_vocab1, vocab1);
		
		
		//création du modèle de langage sans lissage, avec le second vocabulaire
		lm_bigram_vocab2 = new NaiveLanguageModel();
		lm_bigram_vocab2.setNgramCounts(ngramCounts_bigram_vocab2, vocab2);
				
		
		//création du modèle de langage avec lissage, avec le second vocabulaire
		lm_bigram_laplace_vocab2 = new LaplaceLanguageModel();
		lm_bigram_laplace_vocab2.setNgramCounts(ngramCounts_bigram_vocab2, vocab2);
		
		
		
		/* utilisation des modèles de langage */
		List<LanguageModelInterface> models = new ArrayList<LanguageModelInterface>(){{
		    add(lm_bigram_vocab1);
		    add(lm_bigram_vocab2);
		    add(lm_bigram_laplace_vocab1);
		    add(lm_bigram_laplace_vocab2);
        }};

		for(LanguageModelInterface model : models){
		    Double p1 = model.getSentenceProb(sentence1);
            Double p2 = model.getSentenceProb(sentence2);

            System.out.println("<LanguageModelInterface>");
            System.out.println("\tP(s1) = "+p1);
            System.out.println("\tP(s2) = "+p2);
            System.out.println("</LanguageModelInterface>");
            System.out.println("");
        }

        /*LanguageModelInterface newModel = new LaplaceLanguageModel();
		VocabularyInterface vocabulary = new Vocabulary();
		NgramCountsInterface ngramCounts = new NgramCounts();
		Set<String> vocab_set = new HashSet<>();
		MiscUtils.readTextFileAsStringList("data/small_corpus/corpus_fr_train.txt").stream()
        .map(line -> NgramUtils.decomposeIntoNgrams(line, 1))
        .forEach(vocab_set::addAll);
		vocabulary.scanNgramSet(vocab_set);

		ngramCounts.scanTextFile("data/small_corpus/corpus_fr_train.txt", vocabulary, 1);
		newModel.setNgramCounts(ngramCounts, vocabulary);
		ngramCounts.writeNgramCountFile("data/small_corpus/ngram.txt");
		vocabulary.writeVocabularyFile("data/small_corpus/vocab.txt");*/
	}

}
