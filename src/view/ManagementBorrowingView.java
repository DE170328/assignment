package view;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import model.Book;
import model.Borrowing;
import model.Librarian;
import model.User;

public class ManagementBorrowingView extends MenuGeneric {

    private static Scanner scanner = new Scanner(System.in);
    private static Librarian librarian = new Librarian();

    public ManagementBorrowingView() {
        super("Menu:", new String[]{"Mượn sách", "Trả sách", "Gia hạn mượn", "Tra cứu sách", "Thoát"});
    }

    @Override
    public boolean execute(int choice) {
        switch (choice) {
            case 1:
                borrowBook();
                break;

            case 2:
                returnBook();
                break;

            case 3:
                renewBook();
                break;

            case 4:
                searchBook();
                break;

            case 5:
                System.out.println("Kết thúc chương trình.");
                return true;

            default:
                System.out.println("Lựa chọn không hợp lệ.");
                break;
        }
        return false;
    }

    private void borrowBook() {
        System.out.print("Nhập tên người mượn: ");
        String userName = scanner.nextLine();
        User user = new User(userName);

        System.out.print("Nhập tiêu đề sách: ");
        String bookTitle = scanner.nextLine();
        Book book = new Book(bookTitle);

        // Đọc ngày mượn và ngày trả
        Calendar borrowingDate = readDate("Nhập ngày mượn (ngày/tháng/năm): ");
        Calendar returnDate = readDate("Nhập ngày trả (ngày/tháng/năm): ");

        librarian.borrowBook(user, book, borrowingDate, returnDate);
    }

    private void returnBook() {
        System.out.print("Nhập tên người trả sách: ");
        String returnUserName = scanner.nextLine();
        User returnUser = new User(returnUserName);

        System.out.print("Nhập tiêu đề sách cần trả: ");
        String returnBookTitle = scanner.nextLine();
        Book returnBook = new Book(returnBookTitle);

        librarian.returnBook(returnUser, returnBook);
    }

    private void renewBook() {
        System.out.print("Nhập tên người gia hạn sách: ");
        String renewUserName = scanner.nextLine();
        User renewUser = new User(renewUserName);

        System.out.print("Nhập tiêu đề sách cần gia hạn: ");
        String renewBookTitle = scanner.nextLine();

        // Gia hạn sách
        Book renewBook = new Book(renewBookTitle);
        System.out.print("Nhập tiêu đề sách mới: ");
        String newTitle = scanner.nextLine();
        renewBook.renewBook(newTitle);

        // Đọc ngày trả mới
        Calendar newReturnDate = readDate("Nhập ngày trả mới (ngày/tháng/năm): ");

        librarian.renewBook(renewUser, renewBook, newReturnDate);
    }

    private void searchBook() {
        System.out.print("Nhập tiêu đề sách cần tra cứu: ");
        String searchBookTitle = scanner.nextLine();
        librarian.searchBook(searchBookTitle);
    }

    private Calendar readDate(String message) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        sdf.setLenient(false);

        while (true) {
            System.out.print(message);
            String dateString = scanner.nextLine();
            try {
                Date date = sdf.parse(dateString);
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(date);
                return calendar;
            } catch (ParseException e) {
                System.out.println("Ngày tháng không hợp lệ. Vui lòng nhập lại.");
            }
        }
    }
}
