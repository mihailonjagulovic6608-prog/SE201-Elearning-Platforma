package rs.se201.elearn.service;

import rs.se201.elearn.dao.EnrollmentDao;

public class EnrollmentService {
    private final EnrollmentDao dao = new EnrollmentDao();

    public int enroll(int userId, int courseId) {
        if (userId <= 0 || courseId <= 0) throw new IllegalArgumentException("IDs moraju biti > 0");
        return dao.enroll(userId, courseId);
    }
}
