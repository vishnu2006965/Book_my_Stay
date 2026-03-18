abstract class Room {
    protected String type;
    protected int beds;
    protected double price;

    public Room(String type, int beds, double price) {
        this.type = type;
        this.beds = beds;
        this.price = price;
    }

    public String getType() {
        return type;
    }

    public int getBeds() {
        return beds;
    }

    public double getPrice() {
        return price;
    }

    public abstract String getFeatures();
}

class SingleRoom extends Room {
    public SingleRoom() {
        super("Single", 1, 1500);
    }

    public String getFeatures() {
        return "Single Bed, Free WiFi";
    }
}

class DoubleRoom extends Room {
    public DoubleRoom() {
        super("Double", 2, 2500);
    }

    public String getFeatures() {
        return "Double Bed, AC, Free WiFi";
    }
}

class SuiteRoom extends Room {
    public SuiteRoom() {
        super("Suite", 3, 4000);
    }

    public String getFeatures() {
        return "Luxury Suite, AC, WiFi, TV, Mini Bar";
    }
}

public class UseCase2 {
    public static void main(String[] args) {
        Room single = new SingleRoom();
        Room doubleRoom = new DoubleRoom();
        Room suite = new SuiteRoom();

        int singleAvailability = 5;
        int doubleAvailability = 3;
        int suiteAvailability = 2;

        System.out.println("Room Type: " + single.getType());
        System.out.println("Beds: " + single.getBeds());
        System.out.println("Price: ₹" + single.getPrice());
        System.out.println("Features: " + single.getFeatures());
        System.out.println("Available: " + singleAvailability);
        System.out.println("-----------------------------");

        System.out.println("Room Type: " + doubleRoom.getType());
        System.out.println("Beds: " + doubleRoom.getBeds());
        System.out.println("Price: ₹" + doubleRoom.getPrice());
        System.out.println("Features: " + doubleRoom.getFeatures());
        System.out.println("Available: " + doubleAvailability);
        System.out.println("-----------------------------");

        System.out.println("Room Type: " + suite.getType());
        System.out.println("Beds: " + suite.getBeds());
        System.out.println("Price: ₹" + suite.getPrice());
        System.out.println("Features: " + suite.getFeatures());
        System.out.println("Available: " + suiteAvailability);
    }
}