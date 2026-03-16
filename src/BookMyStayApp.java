import java.util.*;

/**
 * UseCase10BookingCancellation
 *
 * Demonstrates safe cancellation of confirmed bookings
 * with inventory rollback using Stack.
 *
 * @author Chani
 * @version 10.0
 */

/* Reservation Class */
class Reservation {

    private String reservationId;
    private String guestName;
    private String roomType;
    private String roomId;
    private boolean cancelled;

    public Reservation(String reservationId, String guestName, String roomType, String roomId) {
        this.reservationId = reservationId;
        this.guestName = guestName;
        this.roomType = roomType;
        this.roomId = roomId;
        this.cancelled = false;
    }

    public String getReservationId() {
        return reservationId;
    }

    public String getRoomType() {
        return roomType;
    }

    public String getRoomId() {
        return roomId;
    }

    public boolean isCancelled() {
        return cancelled;
    }

    public void cancel() {
        this.cancelled = true;
    }

    public void displayReservation() {
        System.out.println("Reservation ID: " + reservationId +
                " | Guest: " + guestName +
                " | Room Type: " + roomType +
                " | Room ID: " + roomId +
                " | Cancelled: " + cancelled);
    }
}

/* Inventory Service */
class RoomInventory {

    private HashMap<String, Integer> inventory;

    public RoomInventory() {

        inventory = new HashMap<>();

        inventory.put("Single Room", 1);
        inventory.put("Double Room", 1);
        inventory.put("Suite Room", 0);
    }

    public void incrementRoom(String roomType) {
        inventory.put(roomType, inventory.getOrDefault(roomType, 0) + 1);
    }

    public void displayInventory() {

        System.out.println("\nCurrent Inventory:");

        for (Map.Entry<String, Integer> entry : inventory.entrySet()) {
            System.out.println(entry.getKey() + " : " + entry.getValue());
        }
    }
}

/* Booking History */
class BookingHistory {

    private Map<String, Reservation> reservations;

    public BookingHistory() {
        reservations = new HashMap<>();
    }

    public void addReservation(Reservation reservation) {
        reservations.put(reservation.getReservationId(), reservation);
    }

    public Reservation getReservation(String reservationId) {
        return reservations.get(reservationId);
    }
}

/* Cancellation Service */
class CancellationService {

    private Stack<String> rollbackStack;

    public CancellationService() {
        rollbackStack = new Stack<>();
    }

    public void cancelReservation(String reservationId, BookingHistory history, RoomInventory inventory) {

        Reservation reservation = history.getReservation(reservationId);

        if (reservation == null) {
            System.out.println("Cancellation Failed: Reservation does not exist.");
            return;
        }

        if (reservation.isCancelled()) {
            System.out.println("Cancellation Failed: Reservation already cancelled.");
            return;
        }

        rollbackStack.push(reservation.getRoomId());

        reservation.cancel();

        inventory.incrementRoom(reservation.getRoomType());

        System.out.println("\nReservation cancelled successfully.");
        System.out.println("Released Room ID: " + rollbackStack.pop());
    }
}

public class BookMyStayApp {
    public static void main(String[] args) {

        System.out.println("=================================");
        System.out.println("        Book My Stay App");
        System.out.println("        Version 10.0");
        System.out.println("=================================");

        RoomInventory inventory = new RoomInventory();
        BookingHistory history = new BookingHistory();
        CancellationService cancellationService = new CancellationService();

        // Simulate confirmed bookings
        Reservation r1 = new Reservation("R101", "Arjun", "Single Room", "S1");
        Reservation r2 = new Reservation("R102", "Priya", "Double Room", "D1");

        history.addReservation(r1);
        history.addReservation(r2);

        // Display initial state
        r1.displayReservation();
        r2.displayReservation();

        // Cancel reservation
        cancellationService.cancelReservation("R101", history, inventory);

        // Try cancelling again
        cancellationService.cancelReservation("R101", history, inventory);

        // Invalid cancellation
        cancellationService.cancelReservation("R999", history, inventory);

        // Display inventory
        inventory.displayInventory();
    }
}
