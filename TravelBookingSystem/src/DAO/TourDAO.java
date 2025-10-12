import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TourDAO {

    public void addTour(Tour tour) throws SQLException {
        String sql = "INSERT INTO tours (tourId, tourName, startFrom, destination, dayStart, numberOfDays, price, numberOfPassengers, numberOfGuides, languageGuideNeed, tourGuides, tourState) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, tour.getTourId());
            pstmt.setString(2, tour.getTourName());
            pstmt.setString(3, tour.getStartFrom());
            pstmt.setString(4, tour.getDestination());

            try {
                java.util.Date utilDate = new SimpleDateFormat("dd/MM/yyyy").parse(tour.getDayStart());
                java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());
                pstmt.setDate(5, sqlDate);
            } catch (ParseException e) {
                pstmt.setNull(5, Types.DATE);
            }
            
            pstmt.setFloat(6, tour.getNumberOfDays());
            pstmt.setDouble(7, tour.getPrice());
            pstmt.setInt(8, tour.getNumberOfPassengers()); 
            pstmt.setInt(9, tour.getNumberOfGuides());
            pstmt.setString(10, tour.getLanguageGuideNeed());
            pstmt.setString(11, String.join(",", tour.getTourGuides())); 
            pstmt.setString(12, tour.getTourState().name());

            pstmt.executeUpdate();
        }
    }

    public Tour getTourById(String tourId) throws SQLException {
        String sql = "SELECT * FROM tours WHERE tourId = ?";
        Tour tour = null;
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, tourId);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                tour = createTourFromResultSet(rs);
            }
        }
        return tour;
    }

    public List<Tour> getAllTours() throws SQLException {
        List<Tour> tours = new ArrayList<>();
        String sql = "SELECT * FROM tours";
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                tours.add(createTourFromResultSet(rs));
            }
        }
        return tours;
    }

    public void updateTour(Tour tour) throws SQLException {
        String sql = "UPDATE tours SET tourName = ?, startFrom = ?, destination = ?, dayStart = ?, numberOfDays = ?, price = ?, numberOfPassengers = ?, numberOfGuides = ?, languageGuideNeed = ?, tourGuides = ?, tourState = ? WHERE tourId = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, tour.getTourName());
            pstmt.setString(2, tour.getStartFrom());
            pstmt.setString(3, tour.getDestination());

            try {
                java.util.Date utilDate = new SimpleDateFormat("dd/MM/yyyy").parse(tour.getDayStart());
                java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());
                pstmt.setDate(4, sqlDate);
            } catch (ParseException e) {
                pstmt.setNull(4, Types.DATE);
            }

            pstmt.setFloat(5, tour.getNumberOfDays());
            pstmt.setDouble(6, tour.getPrice());
            pstmt.setInt(7, tour.getNumberOfPassengers());
            pstmt.setInt(8, tour.getNumberOfGuides());
            pstmt.setString(9, tour.getLanguageGuideNeed());
            pstmt.setString(10, String.join(",", tour.getTourGuides()));
            pstmt.setString(11, tour.getTourState().name()); 
            pstmt.setString(12, tour.getTourId()); 
            
            pstmt.executeUpdate();
        }
    }

    public void deleteTour(String tourId) throws SQLException {
        String sql = "DELETE FROM tours WHERE tourId = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, tourId);
            pstmt.executeUpdate();
        }
    }
    
    private Tour createTourFromResultSet(ResultSet rs) throws SQLException {
        java.sql.Date sqlDate = rs.getDate("dayStart");
        String dayStart = "";
        if (sqlDate != null) {
            dayStart = new SimpleDateFormat("dd/MM/yyyy").format(sqlDate);
        }

        Tour tour = new Tour(
            rs.getString("tourName"),
            rs.getString("startFrom"),
            rs.getString("destination"),
            dayStart,
            rs.getFloat("numberOfDays"),
            rs.getDouble("price"),
            rs.getInt("numberOfPassengers"),
            rs.getInt("numberOfGuides"),
            rs.getString("languageGuideNeed")
        );
        tour.setTourId(rs.getString("tourId")); 

        String stateStr = rs.getString("tourState");
        if (stateStr != null) {
            tour.setTourState(Tour.TourState.valueOf(stateStr));
        }

        String guidesDb = rs.getString("tourGuides");
        if (guidesDb != null && !guidesDb.isEmpty()) {
            tour.getTourGuides().addAll(Arrays.asList(guidesDb.split(",")));
        }

        return tour;
    }
}