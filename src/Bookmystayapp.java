import java.util.*;

class InvalidBookingException extends Exception {
    public InvalidBookingException(String message) {
        super(message);
    }
}

class BookingValidator {

    private static final Set<String> validRoomTypes =
            new HashSet<>(Arrays.asList("Deluxe", "Suite", "Standard"));

    public static void validate(String roomType, int availableRooms) throws InvalidBookingException {

        if (!validRoomTypes.contains(roomType)) {
            throw new InvalidBookingException("Invalid room type selected.");
        }

        if (availableRooms <= 0) {
            throw new InvalidBookingException("No rooms available for selected type.");
        }
    }
}

class BookingService {

    private Map<String, Integer> inventory = new HashMap<>();

    public BookingService() {
        inventory.put("Deluxe", 2);
        inventory.put("Suite", 1);
        inventory.put("Standard", 3);
    }

    public void bookRoom(String roomType) throws InvalidBookingException {

        int available = inventory.getOrDefault(roomType, 0);

        BookingValidator.validate(roomType, available);

        inventory.put(roomType, available - 1);

        System.out.println("Booking successful for " + roomType);
    }

    public void displayInventory() {
        System.out.println("\nCurrent Inventory:");
        for (String type : inventory.keySet()) {
            System.out.println(type + " -> " + inventory.get(type));
        }
    }
}
 class UseCase9ErrorHandlingValidation {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        BookingService service = new BookingService();

        service.displayInventory();

        System.out.print("\nEnter room type to book: ");
        String roomType = sc.nextLine();

        try {
            service.bookRoom(roomType);
        } catch (InvalidBookingException e) {
            System.out.println("Booking Failed: " + e.getMessage());
        }

        service.displayInventory();

        sc.close();
    }
}