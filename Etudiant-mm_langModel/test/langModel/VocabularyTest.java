package langModel;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestName;

public class VocabularyTest {
    @Test
    public void sizeIsZeroIfNew(){}

    /**
     * The following code displays a separator
     * between each method output
     *
     * (manually added)
     **/
    @Rule
    public TestName name = new TestName();

    @Before
    public void printSeparator()
    {
        System.out.println("\n=== " + name.getMethodName() + " =====================");
    }
}
