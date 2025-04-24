import java.util.*;

class Library {
    // 使用ArrayList存储所有图书
    private final List<Book> allBooks = new ArrayList<>();

    // 使用HashMap存储借阅记录：key为学生ID，value为所借书籍Set
    private final Map<String, Set<Book>> borrowRecords = new HashMap<>();

    // 添加书籍（List操作）
    public void addBook(Book book) {
        if (!allBooks.contains(book)) {  // 通过equals方法判断是否存在
            allBooks.add(book);
            System.out.println("《" + book.Title() + "》添加成功！");
        } else {
            System.out.println("⚠️ 该书已存在！");
        }
    }

    // 借书（Map+Set操作）
    public void borrowBook(String studentId, String bookId) {
        // 查找书籍（遍历List）
        Book targetBook = allBooks.stream()
                .filter(book -> book.Id().equals(bookId))
                .findFirst()
                .orElse(null);

        if (targetBook != null) {
            // 使用computeIfAbsent初始化Set（避免空指针）
            Set<Book> borrowedBooks = borrowRecords.computeIfAbsent(
                    studentId,
                    k -> new HashSet<>()
            );

            if (borrowedBooks.add(targetBook)) {  // Set去重判断
                System.out.println("✅ 借阅成功！");
            } else {
                System.out.println("⚠️ 不能重复借阅同一本书！");
            }
        } else {
            System.out.println("⚠️ 书籍不存在！");
        }
    }

    // 还书（Set删除操作）
    public void returnBook(String studentId, String bookId) {
        if (!borrowRecords.containsKey(studentId)) {
            System.out.println("⚠️ 该学生没有借书记录！");
            return;
        }

        Set<Book> books = borrowRecords.get(studentId);
        boolean isRemoved = books.removeIf(book -> book.Id().equals(bookId));

        if (isRemoved) {
            System.out.println("✅ 归还成功！");
            // 如果学生没有其他借书，清理空Set
            if (books.isEmpty()) {
                borrowRecords.remove(studentId);
            }
        } else {
            System.out.println("⚠️ 未找到对应的借阅记录！");
        }
    }

    // 查询借阅数量（Map查询）
    public void showBorrowStatus(String studentId) {
        Set<Book> books = borrowRecords.getOrDefault(studentId, Collections.emptySet());
        System.out.println("\n📚 学生 " + studentId + " 的借阅清单：");
        books.forEach(book ->
                System.out.println("  - 《" + book.Title() + "》")
        );
        System.out.println("总计: " + books.size() + " 本");
    }

    // 显示所有书籍（List遍历）
    public void showAllBooks() {
        System.out.println("\n=== 馆藏图书清单 ===");
        allBooks.forEach(book ->
                System.out.printf(
                        "ID: %-4s | 书名: %-15s | 作者: %-10s%n",
                        book.Id(), book.Title(), book.Author()
                )
        );
        System.out.println("总计: " + allBooks.size() + " 本");
    }
}
