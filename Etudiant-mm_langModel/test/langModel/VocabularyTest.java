package langModel;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.rules.TestName;

import java.io.FileNotFoundException;
import java.io.IOException;

@SuppressWarnings({"unused", "unchecked", "WeakerAccess"})
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
    public void cannotReadFileThrowsException(){
        Vocabulary v = new Vocabulary();
        try {
            v.readVocabularyFile("undefined path unit.test");
            fail();
        }catch(IOException e){
            e.getCause();
        }
    }

    @Test
    public void readIsSameAsWriteRead(){
        Vocabulary v = new Vocabulary();
        try {
            v.readVocabularyFile(VALID_FILE_PATH);
            v.writeVocabularyFile("out.txt");

            Vocabulary voc = new Vocabulary();
            v.readVocabularyFile("out.txt");

            assertEquals(v, voc);
        } catch (IOException e) {
            e.printStackTrace();
        }
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

    public static String VALID_FILE_PATH = "vocab.txt";
}
