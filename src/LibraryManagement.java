import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class LibraryManagement {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter log file name:");
        String fileName = scanner.nextLine();

        File file = new File(fileName);
        try {
            Scanner fileScanner = new Scanner(file);

            List<Book> books = new ArrayList<>();
            List<Integer> days = new ArrayList<>();
            List<Customer> customers = new ArrayList<>();

            // Parse book info
            fileScanner.nextLine(); // Skip BOOK INFO header
            String line = fileScanner.nextLine();
            while (!line.startsWith("**DAY INFO**")) {
                String[] bookData = line.split(",");
                String writer = bookData[0];
                String name = bookData[1];
                int quantity = Integer.parseInt(bookData[2]);
                books.add(new Book(writer, name, quantity));
                line = fileScanner.nextLine();
            }

            days.add(14);
            // Parse day info
            fileScanner.nextLine(); // Skip DAY INFO header
            line = fileScanner.nextLine();
            while (!line.startsWith("***CUSTOMER INFO***")) {
                int day = Integer.parseInt(line);
                days.add(day);
                line = fileScanner.nextLine();
            }
            // Add day 14 to the list
            

            // Parse customer info
            line = fileScanner.nextLine();
            while (fileScanner.hasNextLine()) {
                String[] customerData = line.split(",");
                int registrationYear = Integer.parseInt(customerData[0]);
                String customerId = customerData[1];
                int startReservationDate = Integer.parseInt(customerData[2]);
                int totalReservationDay = Integer.parseInt(customerData[3]);
                String desiredBookName = customerData[4];
                customers.add(new Customer(registrationYear, customerId, startReservationDate,
                        totalReservationDay, desiredBookName));
                line = fileScanner.nextLine();
            }

            fileScanner.close();

            LibraryManager libraryManager = new LibraryManager();
            for (Book book : books) {
                libraryManager.addBook(book);
            }
            for (Customer customer : customers) {
                libraryManager.addCustomer(customer);
            }

            for (int day : days) {
                libraryManager.processDay(day);
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        scanner.close();
    }
}