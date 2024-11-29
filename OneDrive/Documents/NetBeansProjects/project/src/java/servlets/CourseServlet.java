/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import services.CourseService;
import models.*;

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

    
}