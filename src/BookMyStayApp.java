import java.util.*;

/**
 * UseCase11ConcurrentBookingSimulation
 *
 * Demonstrates thread-safe booking processing
 * using synchronized access to shared resources.
 *
 * @author Chani
 * @version 11.0
 */

/* Reservation Class */
class Reservation {

    private String guestName;
    private String roomType;

    public Reservation(String guestName, String roomType) {
        this.guestName = guestName;
        this.roomType = roomType;
    }

    public String getGuestName() {
        return guestName;
    }

    public String getRoomType() {
        return roomType;
    }
}

/* Thread-Safe Room Inventory */
class RoomInventory {

    private Map<String, Integer> inventory = new HashMap<>();

    public RoomInventory() {
        inventory.put("Single Room", 2);
        inventory.put("Double Room", 1);
        inventory.put("Suite Room", 1);
    }

    /* Critical Section */
    public synchronized boolean allocateRoom(String roomType, String guestName) {

        int available = inventory.getOrDefault(roomType, 0);

        if (available > 0) {

            inventory.put(roomType, available - 1);

            System.out.println(Thread.currentThread().getName() +
                    " → Booking confirmed for " + guestName +
                    " (" + roomType + ")");

            return true;
        } else {

            System.out.println(Thread.currentThread().getName() +
                    " → Booking failed for " + guestName +
                    " (No " + roomType + " available)");

            return false;
        }
    }

    public void displayInventory() {

        System.out.println("\nFinal Inventory State:");

        for (Map.Entry<String, Integer> entry : inventory.entrySet()) {
            System.out.println(entry.getKey() + " : " + entry.getValue());
        }
    }
}

/* Booking Processor Thread */
class BookingProcessor extends Thread {

    private Queue<Reservation> bookingQueue;
    private RoomInventory inventory;

    public BookingProcessor(Queue<Reservation> bookingQueue, RoomInventory inventory, String name) {
        super(name);
        this.bookingQueue = bookingQueue;
        this.inventory = inventory;
    }

    public void run() {

        while (true) {

            Reservation reservation;

            synchronized (bookingQueue) {

                if (bookingQueue.isEmpty())
                    return;

                reservation = bookingQueue.poll();
            }

            if (reservation != null) {
                inventory.allocateRoom(
                        reservation.getRoomType(),
                        reservation.getGuestName()
                );
            }
        }
    }
}

public class BookMyStayApp {
    public static void main(String[] args) {

        System.out.println("=================================");
        System.out.println("        Book My Stay App");
        System.out.println("        Version 11.0");
        System.out.println("=================================");

        RoomInventory inventory = new RoomInventory();

        Queue<Reservation> bookingQueue = new LinkedList<>();

        /* Simulated concurrent booking requests */
        bookingQueue.add(new Reservation("Arjun", "Single Room"));
        bookingQueue.add(new Reservation("Priya", "Single Room"));
        bookingQueue.add(new Reservation("Rahul", "Single Room"));
        bookingQueue.add(new Reservation("Sneha", "Suite Room"));
        bookingQueue.add(new Reservation("Karan", "Suite Room"));

        /* Multiple booking threads */
        Thread t1 = new BookingProcessor(bookingQueue, inventory, "Thread-1");
        Thread t2 = new BookingProcessor(bookingQueue, inventory, "Thread-2");
        Thread t3 = new BookingProcessor(bookingQueue, inventory, "Thread-3");

        t1.start();
        t2.start();
        t3.start();

        try {
            t1.join();
            t2.join();
            t3.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        inventory.displayInventory();
    }
}
