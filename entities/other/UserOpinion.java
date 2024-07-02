package entities.other;

public class UserOpinion {
    private String user;
    private String book;
    private String author;

    public UserOpinion(String user, String book, String author) {
        this.user = user;
        this.book = book;
        this.author = author;
    }

    public String getWriter() {
        return user;
    }

    public String getBookTo() {
        return book;
    }

    public String getBookAuthor(){
        return author;
    }
}
