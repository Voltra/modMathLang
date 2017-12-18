package langModel;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.rules.TestName;

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

    public static String VALID_FILE_PATH = "ngram.txt";
}
