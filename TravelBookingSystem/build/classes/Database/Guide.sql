-- Bảng Guide
CREATE TABLE Guide (
    Id VARCHAR(50) PRIMARY KEY,
    Name VARCHAR(100),
    Birthday DATE,
    phoneNumber VARCHAR(20),
    Email VARCHAR(100),
    guideExperience FLOAT,
    foreignLanguage VARCHAR(50),
    touristList VARCHAR(255)
);
