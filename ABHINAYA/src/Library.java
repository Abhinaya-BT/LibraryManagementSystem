import java.util.*;

class Book {
    String title;
    String author;
    boolean isAvailable;

    Book(String title, String author) {
        this.title = title;
        this.author = author;
        this.isAvailable = true;
    }

    public String toString() {
        return title + " by " + author + " - " + (isAvailable ? "Available" : "Not Available");
    }
}

class Borrower {
    String name;
    String id;

    Borrower(String name, String id) {
        this.name = name;
        this.id = id;
    }

    public String toString() {
        return name + " (ID: " + id + ")";
    }
}

class Transaction {
    Borrower borrower;
    Book book;
    String dateBorrowed;
    String dateReturned;

    Transaction(Borrower borrower, Book book, String dateBorrowed) {
        this.borrower = borrower;
        this.book = book;
        this.dateBorrowed = dateBorrowed;
        this.dateReturned = "Not Returned";
    }

    void returnBook(String dateReturned) {
        this.dateReturned = dateReturned;
    }

    public String toString() {
        return "Borrower: " + borrower.name + " - Book: " + book.title + " - Borrowed on: " +
                dateBorrowed + " - Returned on: " + dateReturned;
    }
}

public class Library {
    static Scanner scanner = new Scanner(System.in);
    static List<Book> books = new ArrayList<>();
    static List<Borrower> borrowers = new ArrayList<>();
    static List<Transaction> transactions = new ArrayList<>();

    public static void main(String[] args) {
        int choice;
        do {
            System.out.println("\nLibrary Management System");
            System.out.println("1. Add Book");
            System.out.println("2. Add Borrower");
            System.out.println("3. Borrow Book");
            System.out.println("4. Return Book");
            System.out.println("5. View Books");
            System.out.println("6. View Borrowers");
            System.out.println("7. View Transactions");
            System.out.println("8. Exit");
            System.out.print("Enter your choice: ");
            choice = Integer.parseInt(scanner.nextLine());

            switch (choice) {
                case 1 -> addBook();
                case 2 -> addBorrower();
                case 3 -> borrowBook();
                case 4 -> returnBook();
                case 5 -> viewBooks();
                case 6 -> viewBorrowers();
                case 7 -> viewTransactions();
                case 8 -> System.out.println("Exiting...");
                default -> System.out.println("Invalid choice.");
            }
        } while (choice != 8);
    }

    static void addBook() {
        System.out.print("Enter book title: ");
        String title = scanner.nextLine();
        System.out.print("Enter book author: ");
        String author = scanner.nextLine();
        books.add(new Book(title, author));
        System.out.println("Book added successfully.");
    }

    static void addBorrower() {
        System.out.print("Enter borrower name: ");
        String name = scanner.nextLine();
        System.out.print("Enter borrower ID: ");
        String id = scanner.nextLine();
        borrowers.add(new Borrower(name, id));
        System.out.println("Borrower added successfully.");
    }

    static void borrowBook() {
        System.out.print("Enter borrower ID: ");
        String borrowerId = scanner.nextLine();
        Borrower borrower = findBorrowerById(borrowerId);
        if (borrower == null) {
            System.out.println("Borrower not found.");
            return;
        }

        System.out.print("Enter book title: ");
        String bookTitle = scanner.nextLine();
        Book book = findBookByTitle(bookTitle);
        if (book == null || !book.isAvailable) {
            System.out.println("Book not available.");
            return;
        }

        System.out.print("Enter date borrowed (YYYY-MM-DD): ");
        String dateBorrowed = scanner.nextLine();

        book.isAvailable = false;
        Transaction transaction = new Transaction(borrower, book, dateBorrowed);
        transactions.add(transaction);
        System.out.println("Book borrowed successfully.");
    }

    static void returnBook() {
        System.out.print("Enter borrower ID: ");
        String borrowerId = scanner.nextLine();
        System.out.print("Enter book title: ");
        String bookTitle = scanner.nextLine();

        for (Transaction transaction : transactions) {
            if (transaction.borrower.id.equals(borrowerId) &&
                    transaction.book.title.equalsIgnoreCase(bookTitle) &&
                    transaction.dateReturned.equals("Not Returned")) {

                System.out.print("Enter return date (YYYY-MM-DD): ");
                String returnDate = scanner.nextLine();
                transaction.returnBook(returnDate);
                transaction.book.isAvailable = true;
                System.out.println("Book returned successfully.");
                return;
            }
        }
        System.out.println("Transaction not found.");
    }

    static void viewBooks() {
        System.out.println("\nList of Books:");
        for (Book book : books) {
            System.out.println(book);
        }
    }

    static void viewBorrowers() {
        System.out.println("\nList of Borrowers:");
        for (Borrower borrower : borrowers) {
            System.out.println(borrower);
        }
    }

    static void viewTransactions() {
        System.out.println("\nList of Transactions:");
        for (Transaction transaction : transactions) {
            System.out.println(transaction);
        }
    }

    static Borrower findBorrowerById(String id) {
        for (Borrower b : borrowers) {
            if (b.id.equals(id)) return b;
        }
        return null;
    }

    static Book findBookByTitle(String title) {
        for (Book b : books) {
            if (b.title.equalsIgnoreCase(title)) return b;
        }
        return null;
    }
}
