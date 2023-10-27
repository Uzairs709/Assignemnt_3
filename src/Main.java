import java.io.FileNotFoundException;
import java.util.ArrayList;

public class Main {
   private static Library library=new Library();
    private static ArrayList<Book> books;
    public static void main(String[] args) throws FileNotFoundException {
        GuiMainForm form=new GuiMainForm();
        books =library.loadFromFile();
        for(Book book:books){
        form.addRow(book);
        }
    }
    public static ArrayList<Integer> getAllPopularityCount(){
        ArrayList<Integer> popularityCounts=new ArrayList<>();
        for(Book book:books){
            popularityCounts.add(book.getPopularityCount());
        }
        return popularityCounts;
    }

    public static ArrayList<String> getBookNames() {
       ArrayList<String> bookNames=new ArrayList<>();
       for (Book book:books){
           bookNames.add(book.getTitle());
       }
       return bookNames;
    }
}

