CREATE TABLE Users (
                       id SERIAL PRIMARY KEY,
                       email VARCHAR(255) UNIQUE NOT NULL,
                       password VARCHAR(255) NOT NULL,
                       userType VARCHAR(255) NOT NULL CHECK (userType IN ('AcademicProfessional', 'AcademicInstitution')),

    -- Fields specific to AcademicProfessional
                       name VARCHAR(255),
                       currentInstitution VARCHAR(255),
                       academicPosition VARCHAR(255),
                       educationBackground VARCHAR(255),
                       areaOfExpertise VARCHAR(255),

    -- Fields specific to AcademicInstitution
                       institutionName VARCHAR(255),
                       address VARCHAR(255),
                       coursesOfferedByTerm VARCHAR(255)
);
