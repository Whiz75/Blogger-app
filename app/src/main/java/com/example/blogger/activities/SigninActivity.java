package com.example.blogger.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.blogger.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputLayout;
import com.google.android.material.textview.MaterialTextView;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Objects;

public class SigninActivity extends AppCompatActivity {

    private TextInputLayout email_txt, password_txt;

    private MaterialButton login_button;
    private ImageButton google_icon;
    private MaterialTextView signup_tv,blogger_tv;

    //firebase objects
    private FirebaseAuth auth;
    private FirebaseUser user;

    float v = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);

        auth = FirebaseAuth.getInstance();

        linkComponents();
        GoToSignUp();

        login_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String user_email = Objects.requireNonNull(email_txt.getEditText()).getText().toString().trim();
                String user_password = Objects.requireNonNull(password_txt.getEditText()).getText().toString().trim();

                if (user_email.isEmpty())
                {
                    email_txt.getEditText().setError("Please enter an email");
                }else if(user_password.isEmpty())
                {
                    password_txt.getEditText().setError("Please enter password");
                }else if (TextUtils.isEmpty(user_email) || TextUtils.isEmpty(user_password))
                {
                    email_txt.getEditText().setError("Please enter an email");
                    password_txt.getEditText().setError("Please enter password");
                    Toast.makeText(getApplicationContext(), "Email and password field can be empty", Toast.LENGTH_LONG).show();
                }else
                {
                    auth.signInWithEmailAndPassword(user_email, user_password)
                            .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                                @Override
                                public void onSuccess(AuthResult authResult) {

                                    Toast.makeText(getApplicationContext(), "Login successful...", Toast.LENGTH_SHORT).show();
                                    Intent intentMainActivity = new Intent(getApplicationContext(), MainActivity.class);
                                    startActivity(intentMainActivity);
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(getApplicationContext(), ""+e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });

    }

    private void linkComponents()
    {
        //editetxts
        email_txt = (TextInputLayout)findViewById(R.id.signin_email);
        password_txt = (TextInputLayout)findViewById(R.id.signin_password);
        //buttons
        login_button = (MaterialButton) findViewById(R.id.btn_login);
        google_icon = (ImageButton)findViewById(R.id.google_signin);
        signup_tv = (MaterialTextView) findViewById(R.id.link_signup);
        blogger_tv = (MaterialTextView)findViewById(R.id.blogger_text);

        //try animation
        animateComponents();
    }

    private void animateComponents()
    {
        email_txt.setTranslationX(300);
        password_txt.setTranslationX(300);

        login_button.setTranslationX(300);
        google_icon.setTranslationY(300);

        signup_tv.setTranslationY(300);
        blogger_tv.setTranslationY(-300);

        //set opacity
        email_txt.setAlpha(v);
        password_txt.setAlpha(v);

        login_button.setAlpha(v);
        google_icon.setAlpha(v);

        signup_tv.setAlpha(v);
        blogger_tv.setAlpha(v);

        email_txt.animate().translationX(0).alpha(1).setDuration(1000).setStartDelay(2000).start();
        password_txt.animate().translationX(0).alpha(1).setDuration(1000).setStartDelay(2000).start();

        login_button.animate().translationX(0).alpha(1).setDuration(1000).setStartDelay(2000).start();
        google_icon.animate().translationY(0).alpha(1).setDuration(1000).setStartDelay(2000).start();

        signup_tv.animate().translationY(0).alpha(1).setDuration(1000).setStartDelay(2000).start();
        blogger_tv.animate().translationY(0).alpha(1).setDuration(1000).setStartDelay(2000).start();
    }


    private void GoToMainActivity(FirebaseAuth auth)
    {
    }

    //validations
    private boolean validateEmail() {
        String val = email_txt.getEditText().getText().toString().trim();
        String checkEmail = "[a-zA-Z0-9._-]+@[a-z]+.+[a-z]+";

        if (val.isEmpty()) {
            email_txt.setError("Field can not be empty");
            return false;
        } else if (!val.matches(checkEmail)) {
            email_txt.setError("Invalid Email!");
            return false;
        } else {
            email_txt.setError(null);
            email_txt.setErrorEnabled(false);
            return true;
        }
    }
    private boolean validatePassword() {
        String val = password_txt.getEditText().getText().toString().trim();
       /* String checkPassword = "^" +
                "(?=.*[0-9])" +         //at least 1 digit
                //"(?=.*[a-z])" +         //at least 1 lower case letter
                //"(?=.*[A-Z])" +         //at least 1 upper case letter
                //"(?=.*[a-zA-Z])" +      //any letter
                //"(?=.*[@#$%^&+=])" +    //at least 1 special character
                //"(?=S+$)" +           //no white spaces
                //".{7,}" +               //at least 4 characters
                "$";*/
        String verifyPassword = "(?=.*[0-9])";

        if (val.isEmpty()) {
            password_txt.setError("Field can not be empty");
            return false;
        }else {
            password_txt.setError(null);
            password_txt.setErrorEnabled(false);
            return true;
        }
    }

    private void GoToSignUp()
    {
        signup_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try
                {
                    Intent intentRegisterActivity = new Intent(getApplicationContext(), SignupActivity.class);
                    //overridePendingTransition(R.anim.fade_in_anim, R.anim.fade_out_anim);
                    startActivity(intentRegisterActivity);
                    finish();

                } catch (Exception e) {
                    Toast.makeText(SigninActivity.this, "" + e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
