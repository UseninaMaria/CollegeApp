package com.example.restapp.servlet;

import com.example.restapp.dto.StudentDTO;
import com.example.restapp.service.StudentService;
import com.google.gson.Gson;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

import static com.example.restapp.service.StudentService.getInstanceStudent;

@WebServlet("/college/students")
public class StudentServlet extends HttpServlet {
    private static final String CONTENT_TYPE = "application/json";
    private static final String STUDENT_NAME_REQUEST_PARAMETER = "name";
    private static final String STUDENT_NEW_NAME_REQUEST_PARAMETER = "newName";
    private static final String COLLEGE_ID_REQUEST_PARAMETER = "collegeId";
    private static final String STUDENT_GPA_REQUEST_PARAMETER = "gpa";
    private final StudentService studentService = getInstanceStudent();
    private Gson gson = new Gson();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType(CONTENT_TYPE);

        String studentName = req.getParameter(STUDENT_NAME_REQUEST_PARAMETER);
        StudentDTO studentDTO = studentService.getStudentByName(studentName);

        if (!(studentDTO == null)) {
            String json = gson.toJson(studentDTO);

            resp.setStatus(HttpServletResponse.SC_OK);
            resp.getWriter().write(json);
        } else {
            resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
            resp.getWriter().write("Student this name: " + studentName + " does not exist");
        }
    }


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType(CONTENT_TYPE);

        String name = req.getParameter(STUDENT_NAME_REQUEST_PARAMETER);
        Optional<String> nameOptional = Optional.ofNullable(name);

        int collegeId = Optional.ofNullable(req.getParameter(COLLEGE_ID_REQUEST_PARAMETER))
                .map(Integer::parseInt)
                .orElse(-1);

        double gpa = Optional.ofNullable(req.getParameter(STUDENT_GPA_REQUEST_PARAMETER))
                .map(Double::parseDouble)
                .orElse(-1.0);

        if (nameOptional.isEmpty() || collegeId < 0 || gpa < 0) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            resp.getWriter().write("{\"response\": \"Student name or student rating or gpa should not be empty" +
                    " and rating should be non-negative\"}");
        } else {
            StudentDTO studentDTO = new StudentDTO();

            studentDTO.setStudentNameDto(name);
            studentDTO.setCollegeId(collegeId);
            studentDTO.setGpa(gpa);

            boolean findFlag = studentService.createStudent(studentDTO);

            if (findFlag) {
                resp.setStatus(HttpServletResponse.SC_CREATED);
                resp.getWriter().write("Student created successfully " + studentDTO);
            } else {
                resp.setStatus(HttpServletResponse.SC_CREATED);
                resp.getWriter().write("Student this name: " + studentDTO + " already exist");
            }
        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType(CONTENT_TYPE);

        String oldName = req.getParameter(STUDENT_NAME_REQUEST_PARAMETER);
        String newName = req.getParameter(STUDENT_NEW_NAME_REQUEST_PARAMETER);
        String collegeIdParam = req.getParameter(COLLEGE_ID_REQUEST_PARAMETER);
        String gpaParam = req.getParameter(STUDENT_GPA_REQUEST_PARAMETER);

        if (oldName == null || newName == null || collegeIdParam == null || gpaParam == null) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            resp.getWriter().write("{\"response\": \"Student name or college id or student gpa " +
                    "should not be empty\"}");
        } else {
            try {
                int newCollegeId = Integer.parseInt(collegeIdParam);
                double newGpa = Double.parseDouble(gpaParam);

                if (newCollegeId < 0 || newGpa < 0) {
                    resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                    resp.getWriter().write("{\"response\": \"Student ID and GPA should be non-negative\"}");
                } else {
                    StudentDTO studentDTO = new StudentDTO();

                    studentDTO.setStudentNameDto(newName);
                    studentDTO.setCollegeId(newCollegeId);
                    studentDTO.setGpa(newGpa);

                    boolean updateFlag = studentService.updateStudent(studentDTO, oldName);

                    if (updateFlag) {
                        resp.setStatus(HttpServletResponse.SC_OK);
                        resp.getWriter().write("Student update successfully " + studentDTO);
                    } else {
                        resp.setStatus(HttpServletResponse.SC_CREATED);
                        resp.getWriter().write("Student this name: " + newName + " create successfully");
                    }
                }
            } catch (NumberFormatException e) {
                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                resp.getWriter().write("{\"response\": \"Invalid format for college ID or GPA\"}");
            }
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType(CONTENT_TYPE);
        String studentName = req.getParameter(STUDENT_NAME_REQUEST_PARAMETER);

        if (studentName == null) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            resp.getWriter().write("\"respone\": \"\"Student this name does not exist \"}");
        } else {

            boolean deleteFlag = studentService.deleteStudent(studentName);

            if (deleteFlag) {
                resp.setStatus(HttpServletResponse.SC_OK);
                resp.getWriter().write("Student this name: " + studentName + " deleted successfully");
            } else {
                resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
                resp.getWriter().write("Student this name does not exist");
            }
        }
    }
}
