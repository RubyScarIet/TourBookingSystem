import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Tour {
    
    public enum TourState {
        PLANNED,
        ACTIVE,
        COMPLETED,
        CANCELLED
    }
    
    private static int count = 0;

    private String tourId, tourName, startFrom, destination, dayStart, languageGuideNeed;
    private float numberOfDays;
    private double price;
    private int numberOfPassengers, numberOfGuides, currentPassengers; 
    private ArrayList<String> tourGuides;
    private TourState tourState;

    public Tour() {
        this.tourId = "";
        this.tourName = "";
        this.startFrom = "";
        this.destination = "";
        this.dayStart = "";
        this.numberOfDays = 0.0f;
        this.price = 0.0;
        this.numberOfPassengers = 0;
        this.currentPassengers = 0;
        this.numberOfGuides = 0;
        this.languageGuideNeed = "";
        this.tourGuides = new ArrayList<>();
        this.tourState = TourState.PLANNED;
    }

    public Tour(String tourName, String startFrom, String destination, String dayStart, float numberOfDays, double price, int numberOfPassengers, int numberOfGuides, String languageGuideNeed) {
        if (price < 0 || numberOfPassengers <= 0 || numberOfGuides <= 0) {
            throw new IllegalArgumentException("Giá, số lượng khách và số lượng HDV phải là số dương.");
        }
        count++;
        this.tourId = "TOUR" + String.format("%03d", count);
        this.tourName = tourName;
        this.startFrom = startFrom;
        this.destination = destination;
        this.dayStart = dayStart;
        this.numberOfDays = numberOfDays;
        this.price = price;
        this.numberOfPassengers = numberOfPassengers;
        this.numberOfGuides = numberOfGuides;
        this.languageGuideNeed = languageGuideNeed;
        this.currentPassengers = 0;
        this.tourGuides = new ArrayList<>();
        this.tourState = TourState.PLANNED;
    }
    
    public Tour(String tourName, String startFrom, String destination, String dayStart, String durationText, double price, int numberOfPassengers, int numberOfGuides, String languageGuideNeed) {
        this(tourName, startFrom, destination, dayStart, parseDuration(durationText), price, numberOfPassengers, numberOfGuides, languageGuideNeed);
    }

    private static float parseDuration(String durationText) {
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
    public String getStartFrom() { return this.startFrom; }
    public String getDestination() { return this.destination; }
    public String getDayStart() { return this.dayStart; }
    public float getNumberOfDays() { return this.numberOfDays; }
    public double getPrice() { return this.price; }
    public int getNumberOfPassengers() { return this.numberOfPassengers; }
    public String getAvailabilityStatus() { return checkCustomerAvailability() ? "Còn chỗ" : "Đã đầy"; }
    public String getLanguageGuideNeed() { return this.languageGuideNeed; }
    public int getCurrentPassengers() { return this.currentPassengers; }
    public ArrayList<String> getTourGuides() { return this.tourGuides; }
    public int getNumberOfGuides() { return this.numberOfGuides; } 
    public TourState getTourState() { return this.tourState; }

    public void setTourId(String tourId) { this.tourId = tourId; }
    public void setPrice(double price) {
        if (price >= 0) {
            this.price = price;
        }
    }
    public void setDayStart(String dayStart) { this.dayStart = dayStart; }
    public void setLanguageGuideNeed(String languageGuideNeed) { this.languageGuideNeed = languageGuideNeed; }
    
    public void startTour() {
        if (this.tourState == TourState.PLANNED) {
            this.tourState = TourState.ACTIVE;
        }
    }

    public void setTourState(TourState tourState) {
        this.tourState = tourState;
}

    public void completeTour() {
        if (this.tourState == TourState.ACTIVE) {
            this.tourState = TourState.COMPLETED;
        }
    }

    public void cancelTour() {
        this.tourState = TourState.CANCELLED;
    }

    public boolean checkCustomerAvailability() {
        return this.currentPassengers < this.numberOfPassengers;
    }
    
    public boolean checkGuideAvailability() {
        return this.tourGuides.size() < this.numberOfGuides;
    }

    public boolean addCustomer(int numberOfPeopleToAdd) {
        if (this.tourState != TourState.PLANNED) return false;
        if (numberOfPeopleToAdd <= 0) return false;
        if (this.currentPassengers + numberOfPeopleToAdd <= this.numberOfPassengers) {
            this.currentPassengers += numberOfPeopleToAdd;
            return true;
        }
        return false;
    }

    public boolean removeCustomer(int numberOfPeopleToRemove) {
        if (numberOfPeopleToRemove <= 0) return false;
        if (this.currentPassengers - numberOfPeopleToRemove >= 0) {
            this.currentPassengers -= numberOfPeopleToRemove;
            return true;
        }
        return false;
    }

    public boolean addGuide(String guideName) {
        if (this.tourState != TourState.PLANNED) return false;
        if (checkGuideAvailability() && !this.tourGuides.contains(guideName)) {
            this.tourGuides.add(guideName);
            return true;
        }
        return false;
    }

    public boolean removeGuide(String guideName) {
        return this.tourGuides.remove(guideName);
    }
    
    public double calculateTotalRevenue() {
        return this.price * this.currentPassengers;
    }

    @Override
    public String toString() {
        return
            "   Id: " + this.getTourId() + "\n" +
            "   Name: " + this.getTourName() + "\n" +
            "   Route: " + this.getStartFrom() + " -> " + this.getDestination() + "\n" +
            "   Language Need: " + this.getLanguageGuideNeed() + "\n" +
            "   State: " + this.getTourState() + "\n" +
            "   Availability: " + this.getAvailabilityStatus() + " (" + this.getCurrentPassengers() + "/" + this.getNumberOfPassengers() + ")\n" +
            "   Guides (" + this.tourGuides.size() + "/" + this.getNumberOfGuides() + "): " + String.join(", ", this.tourGuides) + "\n";
    }
}