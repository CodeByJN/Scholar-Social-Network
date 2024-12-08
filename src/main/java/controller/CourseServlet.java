/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import model.Course;
import model.CourseBuilder;
import service.CourseService;

/**
 *
 * @author baljo
 */
@WebServlet(name = "CourseServlet", urlPatterns = {"/CourseServlet"})
public class CourseServlet extends HttpServlet {
private final CourseService courseService = new CourseService();  // Instantiate CourseService

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Retrieve form data from the request
        String title = request.getParameter("title");
        String code = request.getParameter("code");
        String term = request.getParameter("term");

        try {
            // Use CourseBuilder to create a Course object
            CourseBuilder builder = new CourseBuilder();
            Course course = builder.setTitle(title)
                                    .setCode(code)
                                    .setTerm(term)
                                    .build();

            // Use CourseService to save the course
            boolean success = courseService.insertCourse(course);

            // Redirect based on success or failure
            if (success) {
                response.sendRedirect("success.jsp");  // Redirect to success page
            } else {
                response.sendRedirect("error.jsp");    // Redirect to error page
            }
        } catch (IOException e) {
            response.sendRedirect("error.jsp");        // Redirect to error page in case of an exception
        }
    }

    // Optional: You could handle GET requests if needed, for example, to show a course creation form
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // You can forward the request to a JSP form where the user enters course data
        request.getRequestDispatcher("Course.jsp").forward(request, response);
    }
}