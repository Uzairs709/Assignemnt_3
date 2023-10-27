import java.io.FileNotFoundException;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) throws FileNotFoundException {
        GuiMainForm form=new GuiMainForm();
        Library library=new Library();


        ArrayList<Book> books =library.loadFromFile();

        for(Book book:books){
        form.addRow(book);
        }



    }
}
