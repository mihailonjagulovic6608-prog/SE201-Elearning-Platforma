package rs.se201.elearn.util;

import java.util.regex.Pattern;

public class Validation {
    private static final Pattern EMAIL = Pattern.compile("^[A-Za-z0-9+_.-]{1,64}@[A-Za-z0-9.-]{1,120}$");
    private static final Pattern PASSWORD = Pattern.compile("^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d).{8,72}$");

    public static void requireEmail(String email) {
        if (email == null || !EMAIL.matcher(email).matches()) {
            throw new IllegalArgumentException("Neispravan email.");
        }
    }

    public static void requirePassword(String password) {
        if (password == null || !PASSWORD.matcher(password).matches()) {
            throw new IllegalArgumentException("Lozinka mora imati min 8 karaktera i bar 1 veliko slovo i 1 broj.");
        }
    }

    public static void requireNonBlank(String s, String field) {
        if (s == null || s.trim().isEmpty()) throw new IllegalArgumentException("Prazno polje: " + field);
    }
}
