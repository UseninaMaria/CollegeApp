package com.example.restapp.servlet;

import com.example.restapp.dto.SubjectDTO;
import com.example.restapp.model.Subject;
import com.example.restapp.service.SubjectService;
import com.google.gson.Gson;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

import static com.example.restapp.mappers.SubjectMapper.toSubjectDto;
import static com.example.restapp.service.SubjectService.getSubjectService;

@WebServlet("/college/subject")
public class SubjectServlet extends HttpServlet {
    private static final String CONTENT_TYPE = "application/json";
    private static final String NAME_REQUEST_PARAMETER = "name";
    private static final String NEW_NAME_REQUEST_PARAMETER = "newName";
    private final SubjectService subjectService = getSubjectService();
    private Gson gson = new Gson();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType(CONTENT_TYPE);

        List<SubjectDTO> allSubjects = subjectService.getAllSubjects();

        resp.getWriter().write("\"respone\": \"\"All Subjects\":\"}\n");

        String json = gson.toJson(allSubjects);

        resp.getWriter().write(json);
        resp.setStatus(HttpServletResponse.SC_OK);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType(CONTENT_TYPE);

        String subjectName = req.getParameter(NAME_REQUEST_PARAMETER);

        if (subjectName == null) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            resp.getWriter().write("\"respone\": \"\"SubjectsName does not be empty \"}");
        } else {
            SubjectDTO subjectDTO = new SubjectDTO();
            subjectDTO.setNameSubjectDto(subjectName);

            boolean createFlag = subjectService.createSubject(subjectDTO);

            if (createFlag) {
                resp.setStatus(HttpServletResponse.SC_CREATED);
                resp.getWriter().write("Subject this name: " + subjectName + " created successfully");
            } else {
                resp.setStatus(HttpServletResponse.SC_OK);
                resp.getWriter().write("Subject this name: " + subjectName + " already exist");
            }
        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType(CONTENT_TYPE);

        String name = req.getParameter(NAME_REQUEST_PARAMETER);
        String newName = req.getParameter(NEW_NAME_REQUEST_PARAMETER);
        if (name == null || newName == null) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            resp.getWriter().write("\"respone\": \"\"Subjects name or New name does not be empty \"}");
        }

        boolean updateFlag = subjectService.updateSubject(name, newName);

        if (updateFlag) {
            resp.setStatus(HttpServletResponse.SC_OK);
            resp.getWriter().write("Subjects this name: " + name + " update successfully");
        } else {
            resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
            resp.getWriter().write("Subjects this name: " + name + "  does not exist");
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType(CONTENT_TYPE);

        String subjectName = req.getParameter(NAME_REQUEST_PARAMETER);

        if (subjectName == null) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            resp.getWriter().write("\"respone\": \"\"Subjects Name does not be empty \"}");
        } else {
            boolean deleteFlag = subjectService.deleteSubject(toSubjectDto(new Subject(subjectName)));

            if (deleteFlag) {
                resp.setStatus(HttpServletResponse.SC_OK);
                resp.getWriter().write("This subject: " + subjectName + " deleted successfully");
            } else {
                resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
                resp.getWriter().write("Subject this name: " + subjectName + " does not exist");
            }
        }
    }
}
