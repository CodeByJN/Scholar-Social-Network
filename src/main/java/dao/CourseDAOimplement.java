/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;
import models.Course;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
/**
 *
 * @author baljo
 */

/**
 * Implementation of the CourseDAO interface for database operations.
 */

public class CourseDAOimplement implements CourseDAO {
    private static final String DB_URL = "jdbc:mysql://localhost:3306/coursemanagement";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "Saini@2021";

    // Static block to load the JDBC driver
    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * Establishes a database connection.
     *
     * @return Connection object
     * @throws SQLException if connection fails
     */
    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
    }

    @Override
    public boolean insertCourse(Course course) {
        String query = "INSERT INTO courses (title, code, term) VALUES (?, ?, ?)";
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, course.getTitle());
            preparedStatement.setString(2, course.getCode());
            preparedStatement.setString(3, course.getTerm());

            int rowsInserted = preparedStatement.executeUpdate();
            return rowsInserted > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public Course fetchCourseByCode(String code) {
        String query = "SELECT title, code, term FROM courses WHERE code = ?";
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, code);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return new Course(
                            resultSet.getString("title"),
                            resultSet.getString("code"),
                            resultSet.getString("term")
                    );
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean updateCourse(Course course) {
        String query = "UPDATE courses SET title = ?, term = ? WHERE code = ?";
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, course.getTitle());
            preparedStatement.setString(2, course.getTerm());
            preparedStatement.setString(3, course.getCode());

            int rowsUpdated = preparedStatement.executeUpdate();
            return rowsUpdated > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public List<Course> findCourses(String criteria) {
        List<Course> courses = new ArrayList<>();
        String query = "SELECT title, code, term FROM courses WHERE title LIKE ? OR term LIKE ?";
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, "%" + criteria + "%");
            preparedStatement.setString(2, "%" + criteria + "%");

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    courses.add(new Course(
                            resultSet.getString("title"),
                            resultSet.getString("code"),
                            resultSet.getString("term")
                    ));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return courses;
    }

    /**
     * Retrieves paginated courses from the database.
     *
     * @param offset starting index for pagination
     * @param limit  number of records to fetch
     * @return List of Course objects
     */
    public List<Course> getPagedCourses(int offset, int limit) {
        List<Course> courses = new ArrayList<>();
        String query = "SELECT title, code, term FROM courses LIMIT ? OFFSET ?";
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setInt(1, limit);
            preparedStatement.setInt(2, offset);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    courses.add(new Course(
                            resultSet.getString("title"),
                            resultSet.getString("code"),
                            resultSet.getString("term")
                    ));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return courses;
    }
}


