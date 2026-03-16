import java.io.*;
import java.util.*;

/**
 * UseCase12DataPersistenceRecovery
 *
 * Demonstrates persistence and recovery of system state
 * using Java serialization.
 *
 * @author Chani
 * @version 12.0
 */

/* Reservation Class */
class Reservation implements Serializable {

    private static final long serialVersionUID = 1L;

    private String reservationId;
    private String guestName;
    private String roomType;

    public Reservation(String reservationId, String guestName, String roomType) {
        this.reservationId = reservationId;
        this.guestName = guestName;
        this.roomType = roomType;
    }

    public void display() {
        System.out.println(reservationId + " | " + guestName + " | " + roomType);
    }
}

/* System State Class */
class SystemState implements Serializable {

    private static final long serialVersionUID = 1L;

    Map<String, Integer> inventory;
    List<Reservation> bookingHistory;

    public SystemState(Map<String, Integer> inventory, List<Reservation> bookingHistory) {
        this.inventory = inventory;
        this.bookingHistory = bookingHistory;
    }
}

/* Persistence Service */
class PersistenceService {

    private static final String FILE_NAME = "hotel_state.dat";

    /* Save system state */
    public static void saveState(SystemState state) {

        try (ObjectOutputStream out =
                     new ObjectOutputStream(new FileOutputStream(FILE_NAME))) {

            out.writeObject(state);
            System.out.println("\nSystem state saved successfully.");

        } catch (IOException e) {
            System.out.println("Error saving system state: " + e.getMessage());
        }
    }

    /* Load system state */
    public static SystemState loadState() {

        File file = new File(FILE_NAME);

        if (!file.exists()) {
            System.out.println("No previous state found. Starting fresh.");
            return null;
        }

        try (ObjectInputStream in =
                     new ObjectInputStream(new FileInputStream(FILE_NAME))) {

            SystemState state = (SystemState) in.readObject();
            System.out.println("System state restored successfully.");
            return state;

        } catch (Exception e) {
            System.out.println("Failed to restore state. Starting fresh.");
            return null;
        }
    }
}

/* Main Application */
public class BookMyStayApp {

    public static void main(String[] args) {

        System.out.println("=================================");
        System.out.println("        Book My Stay App");
        System.out.println("        Version 12.0");
        System.out.println("=================================");

        Map<String, Integer> inventory = new HashMap<>();
        List<Reservation> history = new ArrayList<>();

        /* Attempt recovery */
        SystemState recoveredState = PersistenceService.loadState();

        if (recoveredState != null) {
            inventory = recoveredState.inventory;
            history = recoveredState.bookingHistory;
        } else {

            /* Initialize default data */
            inventory.put("Single Room", 3);
            inventory.put("Double Room", 2);
            inventory.put("Suite Room", 1);

            history.add(new Reservation("R101", "Arjun", "Single Room"));
            history.add(new Reservation("R102", "Priya", "Suite Room"));
        }

        /* Display current state */
        System.out.println("\nCurrent Inventory:");
        for (Map.Entry<String, Integer> entry : inventory.entrySet()) {
            System.out.println(entry.getKey() + " : " + entry.getValue());
        }

        System.out.println("\nBooking History:");
        for (Reservation r : history) {
            r.display();
        }

        /* Save system state before shutdown */
        SystemState state = new SystemState(inventory, history);
        PersistenceService.saveState(state);

        System.out.println("\nSystem shutting down...");
    }
}