/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package observers;
import model.Course;
import java.util.ArrayList;
import java.util.List;
/**
 *
 * @author baljo
 */


/**
 * Observer to monitor course search results.
 */
public class SearchObserver {
    private final List<Course> observedResults = new ArrayList<>();

    public void update(List<Course> newResults) {
        observedResults.clear();
        observedResults.addAll(newResults);
        notifyUsers();
    }

    private void notifyUsers() {
        System.out.println("Search results updated!");
        observedResults.forEach(System.out::println);
    }
}
