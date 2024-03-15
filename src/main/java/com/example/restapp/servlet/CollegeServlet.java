package com.example.restapp.servlet;

import com.example.restapp.dto.CollegeDTO;
import com.example.restapp.service.CollegeService;
import com.google.gson.Gson;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

import static com.example.restapp.mappers.СollegeMapper.DTOtoDto;
import static com.example.restapp.service.CollegeService.getCollegeService;


@WebServlet("/college")
public class CollegeServlet extends HttpServlet {
    private static final String CONTENT_TYPE = "application/json";
    private static final String ACTION_REQUEST_PARAMETER = "action";
    private static final String ACTION_REQUEST_CREATE_COLLEGE = "create";
    private static final String ACTION_REQUEST_ADD_SUBJECT = "addSubject";
    private static final String ACTION_REQUEST_UPDATE_NAME = "updateName";
    private static final String ACTION_REQUEST_UPDATE_RATING = "updateRating";
    private static final String ACTION_REQUEST_GET_COLLEGES = "getColleges";
    private static final String ACTION_REQUEST_GET_COLLEGE = "getCollegeByName";
    private static final String COLLEGE_NAME_REQUEST_PARAMETER = "name";
    private static final String COLLEGE_SUBJECT_REQUEST_PARAMETER = "subjectName";
    private static final String COLLEGE_RATING_REQUEST_PARAMETER = "rating";
    private static final String NEW_NAME_REQUEST_PARAMETER = "newName";
    private final CollegeService collegeService = getCollegeService();
    private Gson gson = new Gson();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType(CONTENT_TYPE);
        String action = req.getParameter(ACTION_REQUEST_PARAMETER);

        if (ACTION_REQUEST_GET_COLLEGES.equals(action)) {
            List<CollegeDTO> allColleges = collegeService.getAllColleges();

            if (!allColleges.isEmpty()) {
                resp.getWriter().write("\"respone\": \"\"All Colleges\":\"}");

                String json = gson.toJson(allColleges);

                resp.setStatus(HttpServletResponse.SC_OK);
                resp.getWriter().write(json);

            } else {
                resp.setStatus(HttpServletResponse.SC_OK);
                resp.getWriter().write("List colleges is empty");
            }

        } else if (ACTION_REQUEST_GET_COLLEGE.equals(action)) {
            String collegeName = req.getParameter(COLLEGE_NAME_REQUEST_PARAMETER);
            CollegeDTO collegeDTO = collegeService.getCollegeByName(collegeName);

            if (!(collegeDTO == null)) {
                String json = gson.toJson(collegeDTO);

                resp.setStatus(HttpServletResponse.SC_OK);
                resp.getWriter().write(json);
            } else {
                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                resp.getWriter().write("College this name: " + collegeName + " does not exist");
            }

        } else {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            resp.getWriter().write("Invalid request");
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType(CONTENT_TYPE);
        String action = req.getParameter(ACTION_REQUEST_PARAMETER);

        if (ACTION_REQUEST_CREATE_COLLEGE.equals(action)) {
            String collegeName = req.getParameter(COLLEGE_NAME_REQUEST_PARAMETER);
            String collegeRating = req.getParameter(COLLEGE_RATING_REQUEST_PARAMETER);

            if (collegeName == null || collegeRating == null) {
                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                resp.getWriter().write("{\"response\": \"College name or college rating should not be empty\"}");

            } else {
                try {
                    double rating = Double.parseDouble(collegeRating);
                    if (rating < 0) {
                        resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                        resp.getWriter().write("{\"response\": \"College Rating should be non-negative\"}");
                    } else {
                        CollegeDTO collegeDTO = new CollegeDTO();
                        collegeDTO.setNameCollege(collegeName);
                        collegeDTO.setCollegeRating(rating);

                        boolean createFlag = collegeService.createCollege(collegeDTO);
                        CollegeDTO resCollege = DTOtoDto(collegeDTO);

                        if (createFlag) {
                            resp.setStatus(HttpServletResponse.SC_CREATED);
                            resp.getWriter().write("College created successfully " + resCollege);
                        } else {
                            resp.setStatus(HttpServletResponse.SC_OK);
                            resp.getWriter().write("College this name: " + collegeName + " already exist");
                        }
                    }
                } catch (NumberFormatException e) {
                    resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                    resp.getWriter().write("{\"response\": \"Invalid format for college ID or GPA\"}");
                }
            }
        } else if (ACTION_REQUEST_ADD_SUBJECT.equals(action)) {
            String collegeName = req.getParameter(COLLEGE_NAME_REQUEST_PARAMETER);
            String subjectName = req.getParameter(COLLEGE_SUBJECT_REQUEST_PARAMETER);

            if (collegeName == null || subjectName == null) {
                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                resp.getWriter().write("{\"response\": \"College name or subject name should not be empty\"}");
            } else {
                boolean createFlag = collegeService.addSubjectInCollege(collegeName, subjectName);
                if (createFlag) {
                    resp.setStatus(HttpServletResponse.SC_CREATED);
                    resp.getWriter().write("Subject " + subjectName + " added successfully in " + collegeName);
                } else {
                    resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
                    resp.getWriter().write("Duplicate key value or subject or colleges does not exist");
                }
            }
        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType(CONTENT_TYPE);

        String action = req.getParameter(ACTION_REQUEST_PARAMETER);

        if (ACTION_REQUEST_UPDATE_NAME.equals(action)) {
            String oldName = req.getParameter(COLLEGE_NAME_REQUEST_PARAMETER);
            String newName = req.getParameter(NEW_NAME_REQUEST_PARAMETER);

            if (oldName == null || newName == null) {
                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                resp.getWriter().write("College name cannot be empty. OldName: " + oldName + ", NewName: " + newName);
            } else {
                boolean updateNameFlag = collegeService.updateCollegeName(oldName, newName);

                if (updateNameFlag) {
                    resp.setStatus(HttpServletResponse.SC_OK);
                    resp.getWriter().write("College name updated successfully from " + oldName + " to " + newName);
                } else {
                    resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
                    resp.getWriter().write("College this name: " + oldName + " does not exist");
                }
            }
        } else if (ACTION_REQUEST_UPDATE_RATING.equals(action)) {
            String collegeName = req.getParameter(COLLEGE_NAME_REQUEST_PARAMETER);
            String collegeRating = req.getParameter(COLLEGE_RATING_REQUEST_PARAMETER);

            try {
                double rating = Double.parseDouble(collegeRating);
                boolean updateRatingFlag = collegeService.updateCollegeRating(collegeName, rating);
                if (updateRatingFlag) {
                    resp.setStatus(HttpServletResponse.SC_OK);
                    resp.getWriter().write("College rating updated successfully!");
                } else {
                    resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
                    resp.getWriter().write("College this name: " + collegeName + " does not exist");
                }
            } catch (NumberFormatException e) {
                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                resp.getWriter().write("{\"response\": \"Invalid format for college rating\"}");
            }
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType(CONTENT_TYPE);

        String collegeName = req.getParameter(COLLEGE_NAME_REQUEST_PARAMETER);

        if (collegeName == null) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            resp.getWriter().write("College name can not be empty. CollegeName: " + collegeName);
        } else {
            boolean deleteFlag = collegeService.deleteCollege(collegeName);

            if (deleteFlag) {
                resp.setStatus(HttpServletResponse.SC_OK);
                resp.getWriter().write("College " + collegeName + " was deleted");
            } else {
                resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
                resp.getWriter().write("College this name: " + collegeName + " does not exist");
            }
        }
    }
}
