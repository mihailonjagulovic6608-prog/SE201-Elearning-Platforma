package rs.se201.elearn.service;

import org.mindrot.jbcrypt.BCrypt;
import rs.se201.elearn.dao.LoginAttemptDao;
import rs.se201.elearn.dao.UserDao;
import rs.se201.elearn.model.User;
import rs.se201.elearn.util.Validation;

public class AuthService {
    private final UserDao userDao = new UserDao();
    private final LoginAttemptDao attemptDao = new LoginAttemptDao();

    public int register(String email, String password, String role) {
        Validation.requireEmail(email);
        Validation.requirePassword(password);
        Validation.requireNonBlank(role, "role");

        if (userDao.findByEmail(email) != null) throw new IllegalArgumentException("Email vec postoji.");

        String hash = BCrypt.hashpw(password, BCrypt.gensalt(10));
        return userDao.insert(email, hash, role);
    }

    public boolean login(String email, String password) {
        Validation.requireEmail(email);
        Validation.requireNonBlank(password, "password");

        User u = userDao.findByEmail(email);
        if (u == null || u.disabled) {
            System.out.println("Nepostojeci ili onemogucen korisnik.");
            return false;
        }

        if (attemptDao.locked(u.id)) {
            System.out.println("Nalog je zakljucan (previse pokusaja u poslednjih 10 min).");
            return false;
        }

        String hash = userDao.passwordHashByEmail(email);
        boolean ok = hash != null && BCrypt.checkpw(password, hash);

        attemptDao.insertAttempt(u.id, ok);

        System.out.println(ok ? "Login uspesan." : "Pogresna lozinka.");
        return ok;
    }
}
