import java.util.Objects;

class Book {
    private final String id;
    private final String title;
    private final String author;

    public Book(String id, String title, String author) {
        this.id = id;
        this.title = title;
        this.author = author;
    }

    // 重写equals和hashCode：通过id判断是否为同一本书
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Book book = (Book) o;
        return Objects.equals(id, book.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    // Getter方法
    public String Id() { return id; }
    public String Title() { return title; }
    public String Author() { return author; }
}