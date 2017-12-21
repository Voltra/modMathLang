package langModel;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.rules.TestName;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@SuppressWarnings({"unused", "unchecked", "WeakerAccess"})
public class NgramCountsTest {
    @Test
    public void orderIsZeroWhenInitialize(){
        NgramCounts n = new NgramCounts();
        assertEquals(0, n.getMaximalOrder());
    }

    @Test
    public void totalAmountOfWordsIsZeroWhenInitialize(){
        NgramCounts n = new NgramCounts();
        assertEquals(0, n.getTotalWordNumber());
    }

    @Test
    public void undefinedWordGivesZeroAsCount(){
        NgramCounts n = new NgramCounts();
        assertEquals(0, n.getCounts("unit.test"));
    }

    @Test
    public void undefinedWordGivesOneAsCountAfterIncrement(){
        NgramCounts n = new NgramCounts();
        n.incCounts("unit.test");
        assertEquals(1, n.getCounts("unit.test"));
    }

    @Test
    public void newCountIsTheCountThatHasBeenSet(){
        NgramCounts n = new NgramCounts();
        String ut = "unit.test";
        n.incCounts(ut);
        n.incCounts(ut);
        n.incCounts(ut);
        n.incCounts(ut);
        n.incCounts(ut);

        n.setCounts(ut, 120);
        assertEquals(120, n.getCounts(ut));
    }

    @Test
    public void readCreatesCorrectObject() {
        NgramCounts n = new NgramCounts();
        try {
            n.readNgramCountsFile(VALID_FILE_PATH);
        } catch (IOException e) {
            fail("unexpected IOException");
            return;
        }

        boolean isSame = n.getNgramCounts()
        .entrySet()
        .stream()
        .map(entry -> {
            return VALID_FILE_CONTENT.containsKey(entry.getKey())
            && VALID_FILE_CONTENT.get(entry.getKey()).equals(entry.getValue());
        }).reduce(true, Boolean::logicalAnd);

        assertTrue(isSame);
    }

    @Test
    public void readIsTheSameAsReadWriteRead(){
        NgramCounts n = new NgramCounts();
        try {
            n.readNgramCountsFile(VALID_FILE_PATH);
        } catch (IOException e) {
            fail("unexpected IOException");
            return;
        }

        final String fileName = "output_ngram.txt";
        n.writeNgramCountFile(fileName);
        NgramCounts read = new NgramCounts();
        try {
            read.readNgramCountsFile(fileName);
        } catch (IOException e) {
            fail("unexpected IOException");
            return;
        }

        Map<String, Integer> readM = n.getNgramCounts();
        Map<String, Integer> rwr = read.getNgramCounts();

        boolean isSame = readM.entrySet().stream()
        .map(entry -> {
            String key = entry.getKey();
            Integer value = entry.getValue();
            return rwr.containsKey(key)
            && rwr.get(key).equals(value);
        }).reduce(true, Boolean::logicalAnd);

        assertTrue(isSame);
    }

    /**
     * The following code displays a separator
     * between each method output
     *
     * (manually added)
     **/
    @Rule
    public TestName name = new TestName();

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Before
    public void printSeparator()
    {
        System.out.println("\n=== " + name.getMethodName() + " =====================");
    }

    @After
    public void printEnd(){
        String str = "====";
        for(int i = 0, size = name.getMethodName().length() ; i < size ; i+=1)
            str += "=";
        str += "======================";
        System.out.println(str);
    }

    public static final String CURR_PATH = System.getProperty("user.dir") + "/test/langModel/";
    public static final String VALID_FILE_PATH = CURR_PATH + "ngram.txt";
    public static final Map<String, Integer> VALID_FILE_CONTENT = new HashMap<String, Integer>(){{
        put("<s>", 2);
        put("unit", 2);
        put("test", 1);
        put("rules", 3);
    }};
}
