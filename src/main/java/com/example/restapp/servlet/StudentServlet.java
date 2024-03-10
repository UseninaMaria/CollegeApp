package com.example.restapp.servlet;

import com.example.restapp.dto.StudentDTO;
import com.example.restapp.model.Student;
import com.example.restapp.service.StudentService;
import com.google.gson.Gson;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

import static com.example.restapp.mappers.StudentMapper.toStudentDto;
import static com.example.restapp.service.StudentService.getInstanceStudent;

@WebServlet("/college/students")
public class StudentServlet extends HttpServlet {
    private static final String CONTENT_TYPE = "application/json";
    private static final String STUDENT_ID_REQUEST_PARAMETER = "id";
    private static final String STUDENT_NAME_REQUEST_PARAMETER = "name";
    private static final String STUDENT_NEW_NAME_REQUEST_PARAMETER = "newName";
    private static final String COLLEGE_ID_REQUEST_PARAMETER = "collegeId";
    private static final String STUDENT_GPA_REQUEST_PARAMETER = "gpa";
    private final StudentService studentService = getInstanceStudent();
    private Gson gson = new Gson();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException {
        resp.setContentType(CONTENT_TYPE);

        Optional.ofNullable(req.getParameter(STUDENT_ID_REQUEST_PARAMETER))
                .map(id -> {
                    try {
                        return Long.parseLong(id);
                    } catch (NumberFormatException e) {
                        return null;
                    }
                })
                .filter(id -> id != null && id > 0)
                .ifPresentOrElse(
                        studentId -> {
                            StudentDTO studentDTO = studentService.readStudent(studentId);
                            String json = gson.toJson(studentDTO);

                            resp.setStatus(HttpServletResponse.SC_OK);

                            try {
                                resp.getWriter().write(json);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        },

                        () -> {
                            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                            try {
                                resp.getWriter().write("\"response\": \"Invalid student id\"");
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                );
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
            resp.getWriter().write("{\"response\": \"College name or college rating should not be empty" +
                    " and rating should be non-negative\"}");
        } else {
            StudentDTO studentDTO = toStudentDto(new Student(name, collegeId, gpa));
            boolean findFlag = studentService.createStudent(studentDTO);

            if (findFlag) {
                resp.setStatus(HttpServletResponse.SC_CREATED);
                resp.getWriter().write("College created successfully " + studentDTO);
            } else {
                resp.setStatus(HttpServletResponse.SC_CREATED);
                resp.getWriter().write("College this name: " + studentDTO + " already exist");
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
                    resp.getWriter().write("{\"response\": \"College ID and GPA should be non-negative\"}");
                } else {
                    StudentDTO studentDTO = toStudentDto(new Student(newName, newCollegeId, newGpa));
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
            resp.getWriter().write("\"respone\": \"\"Student this id does not exist \"}");
        } else {

            boolean deleteFlag = studentService.deleteStudent(studentName);

            if (deleteFlag) {
                resp.setStatus(HttpServletResponse.SC_OK);
                resp.getWriter().write("Student this name: " + studentName + " deleted successfully");
            } else {
                resp.setStatus(HttpServletResponse.SC_BAD_GATEWAY);
                resp.getWriter().write("Something went wrong");
            }
        }
    }
}
