import java.util.*;

class CancellationException extends Exception {
    public CancellationException(String message) {
        super(message);
    }
}

class Reservation {
    String reservationId;
    String roomType;

    public Reservation(String reservationId, String roomType) {
        this.reservationId = reservationId;
        this.roomType = roomType;
    }
}

class BookingService {

    Map<String, Integer> inventory = new HashMap<>();
    Map<String, Reservation> bookings = new HashMap<>();
    Stack<String> rollbackStack = new Stack<>();

    public BookingService() {
        inventory.put("Deluxe", 2);
        inventory.put("Suite", 1);
        inventory.put("Standard", 3);
    }

    public void bookRoom(String reservationId, String roomType) {

        int available = inventory.getOrDefault(roomType, 0);

        if (available <= 0) {
            System.out.println("Booking Failed: No rooms available.");
            return;
        }

        inventory.put(roomType, available - 1);
        bookings.put(reservationId, new Reservation(reservationId, roomType));

        System.out.println("Booking confirmed for " + reservationId);
    }

    public void cancelBooking(String reservationId) throws CancellationException {

        if (!bookings.containsKey(reservationId)) {
            throw new CancellationException("Reservation does not exist.");
        }

        Reservation r = bookings.get(reservationId);

        rollbackStack.push(reservationId);

        inventory.put(r.roomType, inventory.get(r.roomType) + 1);

        bookings.remove(reservationId);

        System.out.println("Booking cancelled for " + reservationId);
    }

    public void showInventory() {
        System.out.println("\nInventory:");
        for (String type : inventory.keySet()) {
            System.out.println(type + " -> " + inventory.get(type));
        }
    }
}

class UseCase10BookingCancellation {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        BookingService service = new BookingService();

        service.showInventory();

        System.out.print("\nEnter Reservation ID: ");
        String id = sc.nextLine();

        System.out.print("Enter Room Type: ");
        String type = sc.nextLine();

        // Booking
        service.bookRoom(id, type);
        service.showInventory();

        // Cancellation
        System.out.print("\nEnter Reservation ID to cancel: ");
        String cancelId = sc.nextLine();

        try {
            service.cancelBooking(cancelId);
        } catch (CancellationException e) {
            System.out.println("Cancellation Failed: " + e.getMessage());
        }

        service.showInventory();

        sc.close();
    }
}