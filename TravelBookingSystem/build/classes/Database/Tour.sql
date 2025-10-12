-- Bảng Tour
CREATE TABLE Tour (
    tourId VARCHAR(50) PRIMARY KEY,
    tourName VARCHAR(100),
    destination VARCHAR(100),
    dayStart DATE,
    numberOfDays FLOAT,
    price DECIMAL(10,2),
    numberOfPassengers INT,
    tourState ENUM('Đã đầy', 'Còn suất'),
    numberOfGuides INT,
    tourGuide VARCHAR(100)
);