import java.util.*;


class Reservation {
    private String reservationId;
    private String guestName;
    private String roomType;
    private double price;

    public Reservation(String reservationId, String guestName, String roomType, double price) {
        this.reservationId = reservationId;
        this.guestName = guestName;
        this.roomType = roomType;
        this.price = price;
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

    public double getPrice() {
        return price;
    }
}

class BookingHistory {
    private List<Reservation> history = new ArrayList<>();

    public void addReservation(Reservation r) {
        history.add(r);
    }

    public List<Reservation> getAllReservations() {
        return history;
    }
}

class BookingReportService {

    public void showAllBookings(List<Reservation> reservations) {
        System.out.println("\n--- Booking History ---");

        for (Reservation r : reservations) {
            System.out.println(
                    r.getReservationId() + " | " +
                            r.getGuestName() + " | " +
                            r.getRoomType() + " | ₹" +
                            r.getPrice()
            );
        }
    }

    public void generateSummary(List<Reservation> reservations) {
        int totalBookings = reservations.size();
        double totalRevenue = 0;

        for (Reservation r : reservations) {
            totalRevenue += r.getPrice();
        }

        System.out.println("\n--- Summary Report ---");
        System.out.println("Total Bookings: " + totalBookings);
        System.out.println("Total Revenue: ₹" + totalRevenue);
    }
}

public class UseCase8BookingHistoryReport {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        BookingHistory history = new BookingHistory();
        BookingReportService reportService = new BookingReportService();

        System.out.print("Enter number of bookings: ");
        int n = sc.nextInt();
        sc.nextLine();

        for (int i = 0; i < n; i++) {

            System.out.print("\nEnter Reservation ID: ");
            String id = sc.nextLine();

            System.out.print("Enter Guest Name: ");
            String name = sc.nextLine();

            System.out.print("Enter Room Type: ");
            String room = sc.nextLine();

            System.out.print("Enter Price: ");
            double price = sc.nextDouble();
            sc.nextLine();

            history.addReservation(new Reservation(id, name, room, price));
        }

        reportService.showAllBookings(history.getAllReservations());

        reportService.generateSummary(history.getAllReservations());

        sc.close();
    }
}}