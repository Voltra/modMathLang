package langModel;

public class MainTest {

    public static void main(String[] args) {
        System.out.println("Should be 'cette' : "+NgramUtils.getHistory("l' historique de cette phrase", 2));
        System.out.println("Should be 'de cette' : "+NgramUtils.getHistory("l' historique de cette phrase", 3));
    }
}
