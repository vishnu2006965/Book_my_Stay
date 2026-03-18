import java.io.*;
import java.util.*;

class Reservation implements Serializable {
    private String reservationId;
    private String roomType;

    public Reservation(String reservationId, String roomType) {
        this.reservationId = reservationId;
        this.roomType = roomType;
    }

    public String getReservationId() {
        return reservationId;
    }

    public String getRoomType() {
        return roomType;
    }

    public String toString() {
        return reservationId + " - " + roomType;
    }
}

class RoomInventory implements Serializable {
    private Map<String, Integer> inventory = new HashMap<>();

    public void addRoom(String type, int count) {
        inventory.put(type, count);
    }

    public Map<String, Integer> getInventory() {
        return inventory;
    }

    public void display() {
        System.out.println("--- Inventory ---");
        for (Map.Entry<String, Integer> e : inventory.entrySet()) {
            System.out.println(e.getKey() + " : " + e.getValue());
        }
    }
}

class PersistenceService {
    private static final String FILE_NAME = "hotel_state.ser";

    public void save(RoomInventory inventory, List<Reservation> history) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILE_NAME))) {
            oos.writeObject(inventory);
            oos.writeObject(history);
            System.out.println("State saved successfully.");
        } catch (IOException e) {
            System.out.println("Error saving state.");
        }
    }

    public Object[] load() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(FILE_NAME))) {
            RoomInventory inventory = (RoomInventory) ois.readObject();
            List<Reservation> history = (List<Reservation>) ois.readObject();
            System.out.println("State loaded successfully.");
            return new Object[]{inventory, history};
        } catch (Exception e) {
            System.out.println("No previous state found. Starting fresh.");
            return null;
        }
    }
}

public class UseCase12 {
    public static void main(String[] args) {
        PersistenceService service = new PersistenceService();

        Object[] data = service.load();

        RoomInventory inventory;
        List<Reservation> history;

        if (data != null) {
            inventory = (RoomInventory) data[0];
            history = (List<Reservation>) data[1];
        } else {
            inventory = new RoomInventory();
            inventory.addRoom("Standard", 3);
            inventory.addRoom("Suite", 2);

            history = new ArrayList<>();
            history.add(new Reservation("RES201", "Standard"));
            history.add(new Reservation("RES202", "Suite"));
        }

        inventory.display();

        System.out.println("--- Booking History ---");
        for (Reservation r : history) {
            System.out.println(r);
        }

        service.save(inventory, history);
    }
}