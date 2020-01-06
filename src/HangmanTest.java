import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class HangmanTest {
    public static void main(String[] args) throws FileNotFoundException {
        Scanner input = new Scanner(new File("dictionary.txt"));
        List<String> dictionary = new ArrayList<String>();
        while (input.hasNext())
            dictionary.add(input.next().toLowerCase());
        System.out.println(dictionary);
        HangmanManager m = new HangmanManager(dictionary, 20, 2);
        m.record('m');
        m.record('a');
        m.record('j');
    }
}
