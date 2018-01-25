package authorReco;

import authorEval.RecognizerPerformance;

import java.util.Map;

public class UnknownAuthorRecognizer2 extends UnknownAuthorRecognizer1{
    /**
     * Constructor.
     *
     * @param configFile the file path of the configuration file containing the information
     *                   on the language models (author, name and file path).
     * @param vocabFile  the file path of the file containing the common vocabulary
     *                   for all the language models used in the recognition system.
     * @param authorFile the file path of the file containing
     */
    public UnknownAuthorRecognizer2(String configFile, String vocabFile, String authorFile) {
        super(configFile, vocabFile, authorFile);
    }

    public String recognizeAuthorSentence(String sentence){
        AuthorConfigurationFile cfg = super.configLangModels;
        Map<String, Double> probabilities = super.getProbabilityMap(sentence, cfg);

        Map.Entry<String, Double> defaultEntry = new Map.Entry<String, Double>() {
            Double val = 0.0;
            @Override public String getKey() { return AuthorRecognizerAbstractClass.UNKNOWN_AUTHOR; }
            @Override public Double getValue() { return this.val; }
            @Override public Double setValue(Double value) { this.val = value; return value; }
        };

        Map.Entry<String, Double> entry = probabilities.entrySet().stream()
        .reduce(defaultEntry, (acc, elem) -> {
            if(elem.getValue() > acc.getValue())
                return elem;
            return acc;
        });

        System.out.println(entry.getValue());
        return entry.getValue() < getMin() ? BaselineAuthorRecognizer.UNKNOWN_AUTHOR : entry.getKey();
    }

    public Double getMin(){ return Math.pow(10, -200); }

    public static void main(String[] args) {
        //initialization of the recognition system
        final String CURR_PATH = System.getProperty("user.dir");
        final String CONFIG = CURR_PATH + "/data/author_corpus/test/config.txt";
        final String FILE = CURR_PATH + "/data/author_corpus/test/sentences.txt";
        final String OUTPUT = CURR_PATH + "/data/author_corpus/test/authors-hyp2.txt";
        //final String REF = CURR_PATH + "/data/small_author_corpus/validation/authors_100sentences_ref.txt";
        final String VOCAB = CURR_PATH + "/src/lm/small_author_corpus/corpus_20000.vocab";
        final String AUTHOR_FILE = CURR_PATH + "/data/small_author_corpus/validation/authors.txt";
        AuthorRecognizerAbstractClass bar = new UnknownAuthorRecognizer2(CONFIG, VOCAB, AUTHOR_FILE);


        //computation of the hypothesis author file
        bar.recognizeFileLanguage(FILE, OUTPUT);


        //computation of the performance of the recognition system
       // System.out.println( RecognizerPerformance.evaluateAuthors(REF, OUTPUT) );
    }

}
