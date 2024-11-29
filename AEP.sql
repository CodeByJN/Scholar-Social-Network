CREATE TABLE Users (
    id SERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    email VARCHAR(255) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL,
    userType VARCHAR(255) NOT NULL CHECK (userType IN ('AcademicProfessional', 'AcademicInstitution')),
    currentInstitution VARCHAR(255),  -- Specific to AcademicProfessional
    academicPosition VARCHAR(255),   -- Specific to AcademicProfessional
    institutionName VARCHAR(255)     -- Specific to AcademicInstitution
);
