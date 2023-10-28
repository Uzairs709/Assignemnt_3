import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
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
    public static void addBookToList(Book book){
        books.add(book);
    }

    public static void removeBook(int book){
        if(books.contains(books.get(book))) {
            books.remove(book);
        }
    }
    public static void editBook(int index,String title,String author,int year){
        if(books.contains(books.get(index))){
            books.get(index).editItem(title,books.get(index).getPopularityCount(),author,year);
        }
    }
    public static void saveDataToFile() throws IOException {
        File file = new File("items.txt");

        FileWriter writer = new FileWriter(file);

        for(Book book:books) {
            writer.write(book.getTitle()+","+book.getAuthor()+","+book.getYear()+","+book.getPopularityCount()+"\n");
        }

        writer.close();
    }
}

