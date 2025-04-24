import java.util.Scanner;

class Cmd {
    private final Library library;
    private final Scanner scanner;
    private boolean isRunning;

    public Cmd(Library library) {
        this.library = library;
        this.scanner = new Scanner(System.in);
        this.isRunning = true;
    }

    // 核心循环
    public void start() {
        printWelcome();

        while (isRunning) {
            printMenu();
            int choice = readInt("请选择操作：", 0, 5);
            handleChoice(choice);
        }

        scanner.close();
    }

    // 欢迎信息
    private void printWelcome() {
        System.out.println("\n=== 欢迎使用图书馆管理系统 ===");
    }

    // 主菜单
    private void printMenu() {
        System.out.println("\n======== 主菜单 ========");
        System.out.println("1. 添加图书");
        System.out.println("2. 借阅图书");
        System.out.println("3. 归还图书");
        System.out.println("4. 查看所有图书");
        System.out.println("5. 查询借阅状态");
        System.out.println("0. 退出系统");
    }

    // 输入处理
    private int readInt(String prompt, int min, int max) {
        while (true) {
            try {
                System.out.print(prompt);
                int input = Integer.parseInt(scanner.nextLine());
                if (input >= min && input <= max) {
                    return input;
                }
                System.out.printf("⚠️ 请输入 %d~%d 之间的数字！%n", min, max);
            } catch (NumberFormatException e) {
                System.out.println("⚠️ 请输入有效数字！");
            }
        }
    }

    // 命令分发
    private void handleChoice(int choice) {
        switch (choice) {
            case 1 -> handleAddBook();
            case 2 -> handleBorrowBook();
            case 3 -> handleReturnBook();
            case 4 -> library.showAllBooks();
            case 5 -> handleQueryStatus();
            case 0 -> exitSystem();
            default -> System.out.println("⚠️ 无效操作！");
        }
    }

    // 各功能处理器
    private void handleAddBook() {
        System.out.println("\n=== 添加新书 ===");
        String id = readString("输入书籍ID：");
        String title = readString("输入书名：");
        String author = readString("输入作者：");
        library.addBook(new Book(id, title, author));
    }

    private void handleBorrowBook() {
        System.out.println("\n=== 借阅图书 ===");
        String stuId = readString("输入学生ID：");
        String bookId = readString("输入书籍ID：");
        library.borrowBook(stuId, bookId);
    }

    private void handleReturnBook() {
        System.out.println("\n=== 归还图书 ===");
        String stuId = readString("输入学生ID：");
        String bookId = readString("输入书籍ID：");
        library.returnBook(stuId, bookId);
    }

    private void handleQueryStatus() {
        String stuId = readString("输入要查询的学生ID：");
        library.showBorrowStatus(stuId);
    }

    // 通用输入方法
    private String readString(String prompt) {
        System.out.print(prompt);
        return scanner.nextLine().trim();
    }

    // 退出系统
    private void exitSystem() {
        isRunning = false;
        System.out.println("\n感谢使用，再见！");
    }
}
