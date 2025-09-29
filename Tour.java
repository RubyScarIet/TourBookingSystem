import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Tour {
    private static int count = 0;

    private String tourId;
    private String tourName;
    private String fromPlace;
    private String destination;
    private String dayStart;
    private float numberOfDays;
    private double price;
    private int maxOfPassengers;
    private int numberOfPassengers;
    private int numberOfGuides;
    private ArrayList<String> tourGuides;

    public Tour(String tourName, String from, String destination, String dayStart, float numberOfDays, double price, int maxOfPassengers, int numberOfGuides) {
        count++;
        this.tourId = "TOUR" + String.format("%03d", count);
        this.tourName = tourName;
        this.fromPlace = from;
        this.destination = destination;
        this.dayStart = dayStart;
        this.numberOfDays = numberOfDays;
        this.price = price;
        this.maxOfPassengers = maxOfPassengers;
        this.numberOfGuides = numberOfGuides;
        this.numberOfPassengers = 0;
        this.tourGuides = new ArrayList<>();
    }

    public Tour(String tourName, String from, String destination, String dayStart, String durationText, double price, int maxOfPassengers, int numberOfGuides) {
        this(tourName, from, destination, dayStart, ChuanHoa(durationText), price, maxOfPassengers, numberOfGuides);
    }

    private static float ChuanHoa(String durationText) {
        durationText = durationText.toLowerCase().trim();
        float totalDays = 0;
        Pattern dayPattern = Pattern.compile("(\\d+)\\s*ngày");
        Matcher dayMatcher = dayPattern.matcher(durationText);
        if (dayMatcher.find()) {
            totalDays += Float.parseFloat(dayMatcher.group(1));
        }
        Pattern nightPattern = Pattern.compile("(\\d+)\\s*đêm");
        Matcher nightMatcher = nightPattern.matcher(durationText);
        if (nightMatcher.find()) {
            totalDays += Float.parseFloat(nightMatcher.group(1)) * 0.5f;
        } else if (durationText.contains("một đêm")) {
            totalDays += 0.5f;
        }
        return totalDays > 0 ? totalDays : 1.0f;
    }

    public String getTourId() { return this.tourId; }
    public String getTourName() { return this.tourName; }
    public String getFromPlace() { return this.fromPlace; }
    public String getDestination() { return this.destination; }
    public String getDayStart() { return this.dayStart; }
    public float getNumberOfDays() { return this.numberOfDays; }
    public double getPrice() { return this.price; }
    public int getNumberOfPassengers() { return this.numberOfPassengers; }
    public String getTourState() { return checkCustomerAvailability() ? "Còn chỗ" : "Đã đầy"; }
    
    public boolean checkCustomerAvailability() { return this.numberOfPassengers < this.maxOfPassengers; }
    public boolean checkGuideAvailability() { return this.tourGuides.size() < this.numberOfGuides; }

    public boolean addCustomer() {
        if (checkCustomerAvailability()) {
            this.numberOfPassengers++;
            return true;
        }
        return false;
    }

    public boolean addGuide(String guideName) {
        if (checkGuideAvailability()) {
            this.tourGuides.add(guideName);
            return true;
        }
        return false;
    }

    @Override
    public String toString() {
        return
            "  Id: " + this.getTourId() + "\n" +
            "  Name: " + this.getTourName() + "\n" +
            "  Route: " + this.getFromPlace() + " -> " + this.getDestination() + "\n" +
            "  Start Day: " + this.getDayStart() + "\n" +
            "  Duration: " + this.getNumberOfDays() + " days\n" +
            "  Price: " + String.format("%,.0f", this.getPrice()) + " VND\n" +
            "  Status: " + this.getTourState() + " (" + this.getNumberOfPassengers() + "/" + this.maxOfPassengers + ")\n" +
            "  Guides (" + this.tourGuides.size() + "/" + this.numberOfGuides + "): " + String.join(", ", this.tourGuides) + "\n";
    }
}