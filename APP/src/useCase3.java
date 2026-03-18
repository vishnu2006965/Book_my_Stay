import java.util.HashMap;
import java.util.Map;

class RoomInventory {
    private Map<String, Integer> inventory;

    public RoomInventory() {
        inventory = new HashMap<>();
    }

    public void addRoomType(String type, int count) {
        inventory.put(type, count);
    }

    public int getAvailability(String type) {
        return inventory.getOrDefault(type, 0);
    }

    public void updateAvailability(String type, int count) {
        if (inventory.containsKey(type)) {
            inventory.put(type, count);
        } else {
            System.out.println("Room type does not exist: " + type);
        }
    }

    public void displayInventory() {
        System.out.println("--- Room Inventory ---");
        for (Map.Entry<String, Integer> entry : inventory.entrySet()) {
            System.out.println(entry.getKey() + " : " + entry.getValue());
        }
    }
}

public class useCase3 {
    public static void main(String[] args) {
        RoomInventory inventory = new RoomInventory();

        inventory.addRoomType("Single", 5);
        inventory.addRoomType("Double", 3);
        inventory.addRoomType("Suite", 2);

        inventory.displayInventory();

        System.out.println("Availability of Double: " + inventory.getAvailability("Double"));

        inventory.updateAvailability("Suite", 4);

        inventory.displayInventory();

        inventory.updateAvailability("Deluxe", 1);
    }
}