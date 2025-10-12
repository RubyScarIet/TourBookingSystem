-- Bảng trung gian giữa Tour và Guide
CREATE TABLE TourGuide (
    tourId VARCHAR(50),
    guideId VARCHAR(50),
    PRIMARY KEY (tourId, guideId),
    FOREIGN KEY (tourId) REFERENCES Tour(tourId),
    FOREIGN KEY (guideId) REFERENCES Guide(Id)
);
