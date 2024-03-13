import java.util.ArrayList;
import java.util.List;

class Book {
    private String writer;
    private String name;
    private int quantity;

    public Book(String writer, String name, int quantity) {
        this.writer = writer;
        this.name = name;
        this.quantity = quantity;
    }

    public String getWriter() {
        return writer;
    }

    public String getName() {
        return name;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}

class Customer implements Comparable<Customer> {
    private int registrationYear;
    private String customerId;
    private int startReservationDate;
    private int totalReservationDay;
    private String desiredBookName;

    public Customer(int registrationYear, String customerId, int startReservationDate,
            int totalReservationDay, String desiredBookName) {
        this.registrationYear = registrationYear;
        this.customerId = customerId;
        this.startReservationDate = startReservationDate;
        this.totalReservationDay = totalReservationDay;
        this.desiredBookName = desiredBookName;
    }

    public int getRegistrationYear() {
        return registrationYear;
    }

    public String getCustomerId() {
        return customerId;
    }

    public int getStartReservationDate() {
        return startReservationDate;
    }

    public int getTotalReservationDay() {
        return totalReservationDay;
    }

    public String getDesiredBookName() {
        return desiredBookName;
    }

    @Override
    public int compareTo(Customer other) {
        if (this.startReservationDate != other.startReservationDate) {
            return Integer.compare(this.startReservationDate, other.startReservationDate);
        } else {
            return Integer.compare(this.totalReservationDay, other.totalReservationDay);
        }
    }
}

class Heap {
    private List<Customer> heap;

    public Heap() {
        heap = new ArrayList<>();
    }

    public void insert(Customer customer) {
        heap.add(customer);
        siftUp(heap.size() - 1);
    }

    public Customer remove() {
        if (isEmpty()) {
            throw new IllegalStateException("Heap is empty");
        }
        Customer root = heap.get(0);
        heap.set(0, heap.get(heap.size() - 1));
        heap.remove(heap.size() - 1);
        siftDown(0);
        return root;
    }

    public boolean isEmpty() {
        return heap.isEmpty();
    }

    private void siftUp(int index) {
        int parentIndex = (index - 1) / 2;
        while (index > 0 && heap.get(index).compareTo(heap.get(parentIndex)) < 0) {
            swap(index, parentIndex);
            index = parentIndex;
            parentIndex = (index - 1) / 2;
        }
    }

    private void siftDown(int index) {
        int leftChildIndex = 2 * index + 1;
        int rightChildIndex = 2 * index + 2;
        int smallestIndex = index;
        if (leftChildIndex < heap.size() && heap.get(leftChildIndex).compareTo(heap.get(smallestIndex)) < 0) {
            smallestIndex = leftChildIndex;
        }
        if (rightChildIndex < heap.size() && heap.get(rightChildIndex).compareTo(heap.get(smallestIndex)) < 0) {
            smallestIndex = rightChildIndex;
        }
        if (smallestIndex != index) {
            swap(index, smallestIndex);
            siftDown(smallestIndex);
        }
    }

    private void swap(int i, int j) {
        Customer temp = heap.get(i);
        heap.set(i, heap.get(j));
        heap.set(j, temp);
    }

    public List<Customer> getHeap() {
        return heap;
    }
}

class LibraryManager {
    private List<Book> books;
    private Heap waitingCustomers;

    public LibraryManager() {
        books = new ArrayList<>();
        waitingCustomers = new Heap();
    }

    public void addBook(Book book) {
        books.add(book);
    }

    public void addCustomer(Customer customer) {
        waitingCustomers.insert(customer);
    }

    public void processDay(int day) {
        System.out.println("Day " + day + ":");
        System.out.println("Customer info:");
        printWaitingCustomers(day);
        System.out.println("Book info:");
        printBooks();
    }

    private void printWaitingCustomers(int currentDay) {
        boolean hasWaitingCustomer = false;
        for (int i = 0; i < waitingCustomers.getHeap().size(); i++) {
            Customer customer = waitingCustomers.getHeap().get(i);
            if (customer.getStartReservationDate() <= currentDay &&
                customer.getStartReservationDate() + customer.getTotalReservationDay() > currentDay) {
                hasWaitingCustomer = true;
                int waitDays = currentDay - customer.getStartReservationDate();
                System.out.println(
                    customer.getCustomerId() + " waits " + customer.getDesiredBookName() +
                    " since day " + customer.getStartReservationDate() + ".");
            }
        }
        if (!hasWaitingCustomer) {
            System.out.println("No waiting customer");
        }
    }


    private void printBooks() {
        for (Book book : books) {
            System.out.println(
                    book.getWriter() + "," + book.getName() + "," + book.getQuantity());
        }
    }

    public boolean hasAvailableBook(String bookName) {
        for (Book book : books) {
            if (book.getName().equals(bookName) && book.getQuantity() > 0) {
                return true;
            }
        }
        return false;
    }

    public void removeBook(String bookName) {
        for (Book book : books) {
            if (book.getName().equals(bookName)) {
                book.setQuantity(book.getQuantity() - 1);
                break;
            }
        }
    }
}


