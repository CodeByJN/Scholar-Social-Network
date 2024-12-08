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
@WebServlet(name = "EditCourseServlet", urlPatterns = {"/EditCourseServlet"})
public class EditCourseServlet extends HttpServlet {
  private final CourseService courseService = new CourseService();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Retrieve form data
        String code = request.getParameter("code");
        String title = request.getParameter("title");
        String term = request.getParameter("term");
        String qualifications = request.getParameter("qualifications");

        try {
            // Fetch the existing course
            Course existingCourse = courseService.fetchCourseByCode(code);

            if (existingCourse != null) {
                // Use CourseBuilder to update the course
                CourseBuilder builder = new CourseBuilder();
                Course updatedCourse = builder.setTitle(title)
                                              .setCode(code)
                                              .setTerm(term)
                                              .build();

                // If qualifications are provided, decorate the course
                if (qualifications != null && !qualifications.isEmpty()) {
                    updatedCourse = new PreferredQualificationsDecorator(updatedCourse, qualifications);
                }

                // Update the course using CourseService
                boolean success = courseService.updateCourse(updatedCourse);

                // Redirect based on success or failure
                if (success) {
                    response.sendRedirect("success.jsp");
                } else {
                    response.sendRedirect("error.jsp");
                }
            } else {
                response.sendRedirect("error.jsp"); // Course not found
            }
        } catch (IOException e) {
            response.sendRedirect("error.jsp");
        }
    }
}