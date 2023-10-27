
public class Book {
    public String getAuthor() {
        return author;
    }

    public int getYear() {
        return year;
    }

    public String getTitle() {
        return title;
    }

    public int getPopularityCount() {
        return popularityCount;
    }

    private String author;
    private int year;
    private String title;
    private int popularityCount;

    public Book(String title, int popularityCount,  String author, int year){
        this.title=title;
        this.popularityCount=popularityCount;
        this.author=author;
        this.year=year;
    }


    public void editItem(String title, int popularityCount,String author, int year) {
        this.title=title;
        this.popularityCount=popularityCount;
        this.author=author;
        this.year=year;

    }



}

