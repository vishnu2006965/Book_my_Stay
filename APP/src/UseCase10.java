import java.util.*;

class Reservation {
    private String reservationId;
    private String roomType;
    private String roomId;

    public Reservation(String reservationId, String roomType, String roomId) {
        this.reservationId = reservationId;
        this.roomType = roomType;
        this.roomId = roomId;
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
}

class RoomInventory {
    private Map<String, Integer> inventory = new HashMap<>();

    public void addRoom(String type, int count) {
        inventory.put(type, count);
    }

    public void increment(String type) {
        inventory.put(type, inventory.getOrDefault(type, 0) + 1);
    }

    public void display() {
        System.out.println("--- Inventory ---");
        for (Map.Entry<String, Integer> e : inventory.entrySet()) {
            System.out.println(e.getKey() + " : " + e.getValue());
        }
    }
}

class CancellationService {
    private Map<String, Reservation> reservations = new HashMap<>();
    private Stack<String> rollbackStack = new Stack<>();
    private RoomInventory inventory;

    public CancellationService(RoomInventory inventory) {
        this.inventory = inventory;
    }

    public void addReservation(Reservation r) {
        reservations.put(r.getReservationId(), r);
    }

    public void cancel(String reservationId) {
        if (!reservations.containsKey(reservationId)) {
            System.out.println("Invalid cancellation. Reservation not found: " + reservationId);
            return;
        }

        Reservation r = reservations.remove(reservationId);

        rollbackStack.push(r.getRoomId());

        inventory.increment(r.getRoomType());

        System.out.println("Cancelled Reservation: " + reservationId +
                " | Room Released: " + r.getRoomId());
    }

    public void displayRollbackStack() {
        System.out.println("--- Rollback Stack ---");
        for (String id : rollbackStack) {
            System.out.println(id);
        }
    }
}

public class UseCase10{
    public static void main(String[] args) {
        RoomInventory inventory = new RoomInventory();
        inventory.addRoom("Standard", 1);
        inventory.addRoom("Suite", 0);

        CancellationService service = new CancellationService(inventory);

        service.addReservation(new Reservation("RES101", "Standard", "ST101"));
        service.addReservation(new Reservation("RES102", "Suite", "SU201"));

        service.cancel("RES101");
        service.cancel("RES999");

        inventory.display();
        service.displayRollbackStack();
    }
}