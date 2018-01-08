package langModel;

import static org.junit.Assert.*;

import com.sun.org.apache.xerces.internal.impl.xpath.regex.Match;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.rules.TestName;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;

@RunWith(Parameterized.class)
@SuppressWarnings({"unused", "unchecked", "WeakerAccess"})
public class LaplaceLanguageModelTest {
    private LaplaceLanguageModel lm;
    private String phrase;
    private int order;
    private double inf;
    private double sup;

    @Parameterized.Parameters
    public static Collection data(){
        return Arrays.asList(new Object[][]{
                {"<s> Antoine écoute une chanson </s>", 1, Math.pow(10, -5), 2*Math.pow(10, -5)},
                {"<s> Antoine écoute une chanson </s>", 2, Math.pow(10, -4), 2* Math.pow(10, -4)},
                {"<s> Lionel écoute une chanson </s>", 1, Math.pow(10, -5), 2*Math.pow(10, -5)},
                {"<s> Lionel écoute une chanson </s>", 2, 3*Math.pow(10, -5), 4*Math.pow(10, -5)},
        });
    }

    public LaplaceLanguageModelTest(String p, int i, double inf, double sup) throws IOException {
        this.phrase = p;
        this.order = i;
        this.inf = inf;
        this.sup = sup;

        this.lm = new LaplaceLanguageModel();
        VocabularyInterface vocab = new Vocabulary();
        vocab.readVocabularyFile(VALID_VOCAB);
        NgramCountsInterface ng = new NgramCounts();
        ng.scanTextFile(VALID_CORPUS, vocab, this.order);
        ng.writeNgramCountFile(VALID_NGRAMS);

        this.lm.setNgramCounts(ng, vocab);
    }

    @Test
    public void test(){
        Double proba = this.lm.getSentenceProb(this.phrase);
        assertNotNull(proba);
        assertTrue(this.inf <= proba && proba<= this.sup);
    }



    public static final String CURR_PATH = System.getProperty("user.dir") + "/test/langModel/";
    public static final String VALID_VOCAB = CURR_PATH + "lp-vocab.txt";
    public static final String VALID_CORPUS = CURR_PATH + "lp-corpus.txt";
    public static final String VALID_NGRAMS = CURR_PATH + "lp-ngram.txt";
}
