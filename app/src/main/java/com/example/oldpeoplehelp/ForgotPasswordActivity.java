package com.example.oldpeoplehelp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class ForgotPasswordActivity extends AppCompatActivity {

    private EditText email;
    private Button resetPassword;

    FirebaseAuth auth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        email = findViewById(R.id.email_resetpassword);
        resetPassword = findViewById(R.id.resetpassword_btn);
        auth = FirebaseAuth.getInstance();

        resetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                resetPassword();
            }
        });

    }

    private void resetPassword() {
        String email_ = email.getText().toString().trim();
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
        auth.sendPasswordResetEmail(email_).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    Toast.makeText(ForgotPasswordActivity.this,"Check your email to reset your password !",Toast.LENGTH_LONG).show();
                    startActivity(new Intent(ForgotPasswordActivity.this,MainActivity.class));
                }else{
                    Toast.makeText(ForgotPasswordActivity.this,"Something wrong happened ! Try again",Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}