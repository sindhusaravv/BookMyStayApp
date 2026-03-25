import java.util.*;

class AddOnService {
    private String serviceName;
    private double cost;

    public AddOnService(String serviceName, double cost) {
        this.serviceName = serviceName;
        this.cost = cost;
    }

    public String getServiceName() {
        return serviceName;
    }

    public double getCost() {
        return cost;
    }
}

class AddOnServiceManager {

    private Map<String, List<AddOnService>> reservationServices = new HashMap<>();

    public void addService(String reservationId, AddOnService service) {
        reservationServices.putIfAbsent(reservationId, new ArrayList<>());
        reservationServices.get(reservationId).add(service);
    }

    public List<AddOnService> getServices(String reservationId) {
        return reservationServices.getOrDefault(reservationId, new ArrayList<>());
    }

    public double calculateTotalCost(String reservationId) {
        double total = 0;

        for (AddOnService service : getServices(reservationId)) {
            total += service.getCost();
        }

        return total;
    }
}

 class UseCase7AddOnServiceSelection {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        AddOnServiceManager manager = new AddOnServiceManager();

        System.out.print("Enter Reservation ID: ");
        String reservationId = sc.nextLine();

        System.out.print("How many services to add? ");
        int n = sc.nextInt();
        sc.nextLine();

        for (int i = 0; i < n; i++) {
            System.out.print("Enter service name: ");
            String name = sc.nextLine();

            System.out.print("Enter service cost: ");
            double cost = sc.nextDouble();
            sc.nextLine();

            manager.addService(reservationId, new AddOnService(name, cost));
        }

        System.out.println("\nServices for Reservation: " + reservationId);

        for (AddOnService s : manager.getServices(reservationId)) {
            System.out.println(s.getServiceName() + " - ₹" + s.getCost());
        }

        double total = manager.calculateTotalCost(reservationId);
        System.out.println("Total Add-On Cost: ₹" + total);

        sc.close();
    }
}