import java.util.*;

/**
 * UseCase8BookingHistoryReport
 *
 * Demonstrates booking history tracking and reporting.
 * Stores confirmed reservations and allows admin reporting.
 *
 * @author Chani
 * @version 8.0
 */

/* Reservation Class */
class Reservation {

    private String reservationId;
    private String guestName;
    private String roomType;

    public Reservation(String reservationId, String guestName, String roomType) {
        this.reservationId = reservationId;
        this.guestName = guestName;
        this.roomType = roomType;
    }

    public String getReservationId() {
        return reservationId;
    }

    public String getGuestName() {
        return guestName;
    }

    public String getRoomType() {
        return roomType;
    }

    public void displayReservation() {
        System.out.println("Reservation ID: " + reservationId +
                " | Guest: " + guestName +
                " | Room Type: " + roomType);
    }
}

/* Booking History Class */
class BookingHistory {

    private List<Reservation> history;

    public BookingHistory() {
        history = new ArrayList<>();
    }

    // Add confirmed reservation
    public void addReservation(Reservation reservation) {
        history.add(reservation);
    }

    // Retrieve all reservations
    public List<Reservation> getAllReservations() {
        return history;
    }
}

/* Booking Report Service */
class BookingReportService {

    public void generateReport(BookingHistory history) {

        List<Reservation> reservations = history.getAllReservations();

        System.out.println("\n===== Booking History Report =====");

        if (reservations.isEmpty()) {
            System.out.println("No bookings found.");
            return;
        }

        for (Reservation r : reservations) {
            r.displayReservation();
        }

        System.out.println("\nTotal Confirmed Bookings: " + reservations.size());
    }
}

public class BookMyStayApp {
    public static void main(String[] args) {

        System.out.println("=================================");
        System.out.println("        Book My Stay App");
        System.out.println("        Version 8.0");
        System.out.println("=================================");

        BookingHistory bookingHistory = new BookingHistory();

        // Simulating confirmed reservations
        bookingHistory.addReservation(new Reservation("R101", "Arjun", "Single Room"));
        bookingHistory.addReservation(new Reservation("R102", "Priya", "Suite Room"));
        bookingHistory.addReservation(new Reservation("R103", "Rahul", "Double Room"));

        // Admin requests report
        BookingReportService reportService = new BookingReportService();

        reportService.generateReport(bookingHistory);
    }
}
