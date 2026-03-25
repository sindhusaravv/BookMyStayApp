import java.util.*;

class BookingRequest {
    String guestName;
    String roomType;

    public BookingRequest(String guestName, String roomType) {
        this.guestName = guestName;
        this.roomType = roomType;
    }
}

class BookingSystem {

    private Map<String, Integer> inventory = new HashMap<>();

    public BookingSystem() {
        inventory.put("Deluxe", 1);
        inventory.put("Suite", 1);
        inventory.put("Standard", 2);
    }

    public synchronized void bookRoom(BookingRequest request) {

        String roomType = request.roomType;
        int available = inventory.getOrDefault(roomType, 0);

        if (available > 0) {
            System.out.println(Thread.currentThread().getName() +
                    " booked " + roomType + " for " + request.guestName);

            inventory.put(roomType, available - 1);
        } else {
            System.out.println(Thread.currentThread().getName() +
                    " FAILED booking for " + request.guestName +
                    " (No " + roomType + " rooms left)");
        }
    }

    public void showInventory() {
        System.out.println("\nFinal Inventory:");
        for (String type : inventory.keySet()) {
            System.out.println(type + " -> " + inventory.get(type));
        }
    }
}

class BookingThread extends Thread {

    private BookingSystem system;
    private BookingRequest request;

    public BookingThread(BookingSystem system, BookingRequest request) {
        this.system = system;
        this.request = request;
    }

    public void run() {
        system.bookRoom(request);
    }
}

 class UseCase11ConcurrentBookingSimulation {

    public static void main(String[] args) {

        BookingSystem system = new BookingSystem();

        BookingThread t1 = new BookingThread(system, new BookingRequest("Sindhu", "Deluxe"));
        BookingThread t2 = new BookingThread(system, new BookingRequest("Rahul", "Deluxe"));
        BookingThread t3 = new BookingThread(system, new BookingRequest("Anu", "Suite"));
        BookingThread t4 = new BookingThread(system, new BookingRequest("Kiran", "Suite"));

        t1.start();
        t2.start();
        t3.start();
        t4.start();

        try {
            t1.join();
            t2.join();
            t3.join();
            t4.join();
        } catch (InterruptedException e) {
            System.out.println("Thread interrupted");
        }

        system.showInventory();
    }
}