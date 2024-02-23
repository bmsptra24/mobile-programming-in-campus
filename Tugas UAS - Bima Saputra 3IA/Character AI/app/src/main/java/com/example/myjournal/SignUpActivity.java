package com.example.myjournal;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myjournal.utils.Navigation;
import com.example.myjournal.utils.Validation;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SignUpActivity extends AppCompatActivity {
    private static final int RC_SIGN_IN = 9001;

    private Button btnLoginWithGoogle;
    private Button btnSignUp;
    private EditText inputEmail, inputPassword, inputConfirmPassword;
    private FirebaseAuth mAuth;
    private GoogleSignInClient mGoogleSignInClient;
    TextView gotoLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        mAuth = FirebaseAuth.getInstance();

        inputEmail = findViewById(R.id.inputEmailValue);
        inputPassword = findViewById(R.id.inputPasswordValue);
        inputConfirmPassword = findViewById(R.id.inputConfirmPasswordValue);
        btnLoginWithGoogle = findViewById(R.id.btnLogInWithGoogle);
        btnSignUp = findViewById(R.id.btnSignUp);
        gotoLogin = findViewById(R.id.gotoLogin);

        btnLoginWithGoogle.setOnClickListener(v -> signInWithGoogle());
        btnSignUp.setOnClickListener(v -> handleSignUp());
        gotoLogin.setOnClickListener(v -> {
            Navigation.goToActivity(this, LoginActivity.class);
        });

        // Configure Google Sign-In
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
    }

    private void handleSignUp() {
        String email = inputEmail.getText().toString().trim();
        String password = inputPassword.getText().toString().trim();
        String confirmPassword = inputConfirmPassword.getText().toString().trim();

        if (!Validation.signupInput(inputEmail, inputPassword,inputConfirmPassword)) return;

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        // Sign up success, update UI with the signed-in user's information
                        FirebaseUser user = mAuth.getCurrentUser();
                        if (user != null) {
                            // You can add additional information to the user profile if needed
                            // For example, user.updateProfile(new UserProfileChangeRequest.Builder().setDisplayName("User123").build());
                        }
                        Navigation.goToActivity(this, MainActivity.class);
                    } else {
                        // If sign up fails, display a message to the user.
                        Toast.makeText(this, "Sign up failed. " + task.getException().getMessage(),
                                Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void signInWithGoogle() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }
}