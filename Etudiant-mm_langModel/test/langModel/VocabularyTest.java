package langModel;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestName;

public class VocabularyTest {
    @Test
    public void sizeIsZeroIfNew(){
        Vocabulary v = new Vocabulary();
        assertEquals(v.getSize(), 0);
    }

    @Test
    public void doesntContainIfNew(){
        Vocabulary v = new Vocabulary();
        assertFalse(v.contains("unit.test"));
    }

    @Test
    public void sizeIncrementsfAddsWord(){
        Vocabulary v = new Vocabulary();
        int oldSize = v.getSize();
        v.addWord("");
        assertEquals(oldSize+1, v.getSize());
    }

    @Test
    public void sizeDecrementsIfRemovesWord(){
        Vocabulary v = new Vocabulary();
        v.addWord("unit.test");
        int oldSize = v.getSize();
        v.removeWord("unit.test");
        assertEquals(oldSize-1, v.getSize());
    }

    @Test
    public void cannotReadFieThrowsException(){
        Vocabulary v = new Vocabulary();
        //TODO: Expects exception
        v.readVocabularyFile("undefined path unit.test");
    }



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
