package dao;

import model.User;

/**
 * Interface for Data Access Object (DAO) for managing User entities.
 * Provides methods for inserting and retrieving users from the database.
 */
public interface UserDAO {

    /**
     * Inserts a user into the database.
     *
     * @param user the user to be inserted into the database.
     */
    void insertUser(User user);

    /**
     * Retrieves a user by their email address.
     *
     * @param email the email address of the user to retrieve.
     * @return a User object if a user with the specified email exists, otherwise null.
     */
    User getUserByEmail(String email);
}
