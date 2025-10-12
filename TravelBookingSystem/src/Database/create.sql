-- Tạo cơ sở dữ liệu
CREATE DATABASE TravelBookingSystemApp;
USE TravelBookingSystemApp;

-- Tạo bảng Tour
CREATE TABLE TOUR (
    tourId VARCHAR(10),
    tourName VARCHAR(255),
    startFrom VARCHAR(255),
    destination VARCHAR(255),
    dayStart DATE,
    numberOfDays DECIMAL,
    price DECIMAL,
    maxNumberOfPassenger INT,
    tourState ENUM('Full', 'Not Full'),
    maxNumberOfGuides INT,
    languageGuideNeed VARCHAR(255),
    PRIMARY KEY (tourId)
); 

-- Tạo bảng Customer
CREATE TABLE Customer (
    Id VARCHAR(10),
    `Name` VARCHAR(255) NOT NULL,
    Birthday DATE,
    phoneNumber VARCHAR(20),
    Email VARCHAR(255),
    TourBooking VARCHAR(10),
    BookingState ENUM('Pending', 'Confirmed'),
    BookingDate DATE,
    numberOfCustomers INT,
    price DECIMAL(10, 2),
    UNIQUE (phoneNumber, Email),
    PRIMARY KEY (Id),
    FOREIGN KEY (TourBooking) REFERENCES TOUR(tourId)
);

-- Tạo bảng Guide
CREATE TABLE Guide (
    Id VARCHAR(10),
    `Name` VARCHAR(255) NOT NULL,
    Birthday DATE,
    phoneNumber VARCHAR(20),
    Email VARCHAR(255),
    guideExperience DECIMAL,
    TourBooking VARCHAR(10),
    BookingState ENUM('cancelled', 'Confirmed'),
    BookingDate DATE,
    UNIQUE (phoneNumber, Email),
    PRIMARY KEY (Id),
    FOREIGN KEY (TourBooking) REFERENCES TOUR(tourId)
); 

-- Tạo bảng Foreign Language
CREATE TABLE ForeignLanguage (
	language VARCHAR(255) PRIMARY KEY
); 

-- Tạo bảng Guide Language
CREATE TABLE GuideLanguage (
	GuideId VARCHAR(10),
    foreignLanguage VARCHAR(255),
    PRIMARY KEY (GuideId, foreignLanguage),
    FOREIGN KEY (GuideId) REFERENCES Guide(Id),
    FOREIGN KEY (foreignLanguage) REFERENCES ForeignLanguage(language)
);

