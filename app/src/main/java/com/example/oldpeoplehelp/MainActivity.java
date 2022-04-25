package com.example.oldpeoplehelp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView register,forgotpassword;
    private EditText email,password;
    private Button login;

    //private ProgressBar progressBar;
    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        register = findViewById(R.id.register);
        register.setOnClickListener(this);
        login = findViewById(R.id.login_btn);
        login.setOnClickListener(this);
        forgotpassword = findViewById(R.id.forgot_password);
        forgotpassword.setOnClickListener(this);
        email = findViewById(R.id.email_login);
        password = findViewById(R.id.password_login);
        //progressBar = findViewById(R.id.progress_login);
        mAuth = FirebaseAuth.getInstance();
    }

    @Override
    public void onClick(View view) {
        switch(view.getId()){
            case R.id.register:{
                startActivity(new Intent(this,RegistrationActivity.class));
                break;
            }
            case R.id.login_btn:{
                userLogin();
                break;
            }
            case R.id.forgot_password:{
                startActivity(new Intent(this,ForgotPasswordActivity.class));
                break;
            }
        }
    }

    private void userLogin() {
        String email_ = email.getText().toString().trim();
        String password_ = password.getText().toString().trim();
        if(email_.isEmpty()){
            email.setError("Email is Required !");
            email.requestFocus();
            return;
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(email_).matches()){
            email.setError("Please enter a valid address email !");
            email.requestFocus();
            return;
        }
        if(password_.isEmpty()){
            password.setError("Password is Required !");
            password.requestFocus();
            return;
        }
        //progressBar.setVisibility(View.VISIBLE);
        mAuth.signInWithEmailAndPassword(email_,password_).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                    if(user.isEmailVerified()){
                        //redirect to user profil or menu
                        startActivity(new Intent(MainActivity.this,MenuNavigationActivity.class));
                    }else{
                        user.sendEmailVerification();
                        Toast.makeText(MainActivity.this,"Check your email to verify your account!",Toast.LENGTH_LONG).show();
                    }

                }else{
                    Toast.makeText(MainActivity.this,"Failed to login ! Check your crendentials please",Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}