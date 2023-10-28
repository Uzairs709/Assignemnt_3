import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Scanner;

public class Library {
    public ArrayList<Book> loadFromFile() throws FileNotFoundException {
        // Create a Scanner object to read the CSV file
        Scanner scanner = new Scanner(new FileReader("items.txt"));
        ArrayList<Book> books = new ArrayList<>();
        // Iterate over the lines in the file
        while (scanner.hasNextLine()) {
            String[] data = scanner.nextLine().split(",");

            String title = data[0];
            String author = data[1];
            int year = Integer.parseInt(data[2].trim());
            int popularityCount = Integer.parseInt(data[3].trim());
            Book book = new Book(title, popularityCount, author, year);
            books.add(book);

        }
        return books;
    }


}

