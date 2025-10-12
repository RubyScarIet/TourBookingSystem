-- Bảng Booking
CREATE TABLE Booking (
    bookingId VARCHAR(50) PRIMARY KEY,
    tourId VARCHAR(50),
    customerId VARCHAR(50),
    bookingDate DATE,
    bookingState ENUM('Chưa thanh toán', 'Đã thanh toán'),
    FOREIGN KEY (tourId) REFERENCES Tour(tourId),
    FOREIGN KEY (customerId) REFERENCES Customer(Id)
);