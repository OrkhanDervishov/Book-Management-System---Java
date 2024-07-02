package entities.book;

public abstract class AbstractWork {

    protected  String title;
    protected  String author;

    public AbstractWork(String title, String author) {
        setTitle(title);
        setAuthor(author);
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        if (title == null || title.strip().equals("")) {
            this.title = "Unknown";
            return;
        } else if (title.length() < 64) {
            this.title = title;
        }

    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        if (author == null || author.strip().equals("")) {
            this.author = "Unknown";
            return;
        }
        else if (author.length() < 64) {
            this.author = author;
        }
    }

    // public static void writeLastIndex() {
    //     try (BufferedWriter bw = new BufferedWriter(new FileWriter("./program_settings/book_index.txt"))) {
    //         bw.write(index);

    //     } catch (IOException e) {
    //         System.out.println("error during writing last books index");
    //     }
    // }

    // public static void readLastIndex() {
    //     try (BufferedReader br = new BufferedReader(new FileReader("./program_settings/book_index.txt"))) {

    //         index = Integer.parseInt(br.readLine());

    //     } catch (IOException e) {
    //         System.out.println("error during writing last books index");
    //     }
    // }
}
