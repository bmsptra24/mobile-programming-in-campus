package com.example.myjournal.utils;

import android.util.Patterns;
import android.widget.EditText;

public class Validation {

    private static final int MIN_PASSWORD_LENGTH = 6;

    public static boolean isValidEmail(EditText inputEmail) {
        String email = inputEmail.getText().toString().trim();
        if (email.isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            inputEmail.setError("Invalid email address");
            return false;
        }
        return true;
    }

    public static boolean isValidPassword(EditText inputPassword) {
        String password = inputPassword.getText().toString().trim();
        if (password.isEmpty() || password.length() < MIN_PASSWORD_LENGTH) {
            inputPassword.setError("Password must be at least " + MIN_PASSWORD_LENGTH + " characters");
            return false;
        }
        return true;
    }

    public static boolean loginInput(EditText inputEmail, EditText inputPassword) {
        return isValidEmail(inputEmail) && isValidPassword(inputPassword);
    }

    public static boolean signupInput(EditText inputEmail, EditText inputPassword, EditText inputConfirmPassword) {
        return isValidEmail(inputEmail) &&
                isValidPassword(inputPassword) &&
                isValidPasswordConfirmation(inputPassword, inputConfirmPassword);
    }

    private static boolean isValidPasswordConfirmation(EditText inputPassword, EditText inputConfirmPassword) {
        String confirmPassword = inputConfirmPassword.getText().toString().trim();
        if (confirmPassword.isEmpty() || confirmPassword.length() < MIN_PASSWORD_LENGTH) {
            inputConfirmPassword.setError("Password must be at least " + MIN_PASSWORD_LENGTH + " characters");
            return false;
        }

        String password = inputPassword.getText().toString().trim();
        if (!password.equals(confirmPassword)) {
            inputConfirmPassword.setError("Invalid Confirm Password");
            return false;
        }

        return true;
    }
}
