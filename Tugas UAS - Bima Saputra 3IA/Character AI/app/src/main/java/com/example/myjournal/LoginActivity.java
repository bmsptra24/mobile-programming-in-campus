package com.example.myjournal;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myjournal.utils.Navigation;
import com.example.myjournal.utils.Validation;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.GoogleAuthProvider;

public class LoginActivity extends AppCompatActivity {
    private static final int RC_SIGN_IN = 9001;
    Button btnLogin,btnLoginWithGoogle;
    TextView gotoSignUp;
    EditText inputEmail, inputPassword;
    private FirebaseAuth mAuth;
    private GoogleSignInClient mGoogleSignInClient;
    private boolean rememberMeChecked = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Initialize Firebase Authentication
        mAuth = FirebaseAuth.getInstance();

        btnLogin = findViewById(R.id.btnLogIn);
        inputEmail = findViewById(R.id.inputEmailValue);
        inputPassword = findViewById(R.id.inputPasswordValue);
        btnLoginWithGoogle = findViewById(R.id.btnLogInWithGoogle);
        gotoSignUp = findViewById(R.id.gotoSignUp);
        SharedPreferences preferences = getPreferences(MODE_PRIVATE);
        rememberMeChecked = preferences.getBoolean("rememberMe", false);
        String pEmail = preferences.getString("email", "");
        String pPassword = preferences.getString("password", "");

        CheckBox rememberMeCheckBox = findViewById(R.id.rememberMeCheckBox);
        rememberMeCheckBox.setChecked(rememberMeChecked);

        if (rememberMeChecked) {
            handleLogin(pEmail,pPassword);
        }

        rememberMeCheckBox.setOnCheckedChangeListener((buttonView, isChecked) -> {
            rememberMeChecked = isChecked;
        });

        btnLogin.setOnClickListener(v -> {
            String tEmail = inputEmail.getText().toString().trim();
            String tPassword = inputPassword.getText().toString().trim();
            handleLogin(tEmail,tPassword);
        });

        TextView forgotPasswordTextView = findViewById(R.id.forgotPassword);
        forgotPasswordTextView.setOnClickListener(v -> {
            String email = inputEmail.getText().toString().trim();
            if (!TextUtils.isEmpty(email)) {
                resetPassword(email);
            } else {
                Toast.makeText(LoginActivity.this, "Please enter your email to reset password.", Toast.LENGTH_SHORT).show();
            }
        });


        btnLoginWithGoogle.setOnClickListener(v -> {
            signInWithGoogle();
        });

        gotoSignUp.setOnClickListener(v -> {
            Navigation.goToActivity(LoginActivity.this, SignUpActivity.class);
        });

        // Configure Google Sign-In
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
    }

    // Method to handle login with Firebase Authentication
    private void handleLogin(String email, String password) {

        if (!Validation.loginInput(inputEmail, inputPassword)) return;

        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        if (rememberMeChecked) {
                            saveRememberMeStatus(email,password);
                        }
                        Navigation.goToActivity(LoginActivity.this, MainActivity.class);
                    } else {
                        Toast.makeText(LoginActivity.this, "Authentication failed.",
                                Toast.LENGTH_SHORT).show();
                    }
                });
    }
    private void saveRememberMeStatus(String email, String password) {
        SharedPreferences preferences = getPreferences(MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean("rememberMe", true);
        editor.putString("email", email);
        editor.putString("password", password);
        editor.apply();
    }

    private void signInWithGoogle() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleGoogleSignInResult(task);
        }
    }

    private void handleGoogleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);
            firebaseAuthWithGoogle(account);
        } catch (ApiException e) {
            Toast.makeText(this, "Google Sign-In failed: " + e.getStatusCode(), Toast.LENGTH_SHORT).show();
        }
    }

    private void firebaseAuthWithGoogle(GoogleSignInAccount account) {
        AuthCredential credential = GoogleAuthProvider.getCredential(account.getIdToken(), null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        // Sign in success, update UI with the signed-in user's information
                        Navigation.goToActivity(LoginActivity.this, MainActivity.class);
                    } else {
                        // If sign in fails, display a message to the user.
                        Toast.makeText(LoginActivity.this, "Authentication failed.", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void resetPassword(String email) {
        mAuth.sendPasswordResetEmail(email)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        Toast.makeText(LoginActivity.this, "Reset password email sent.", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(LoginActivity.this, "Failed to send reset password email.", Toast.LENGTH_SHORT).show();
                    }
                });
    }
}