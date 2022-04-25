package com.example.oldpeoplehelp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.net.sip.SipSession;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class RegistrationActivity extends AppCompatActivity{

    private EditText fullname,email,password,address,sexe,age,date_of_birth;
    private Button sign_up;
    //private ProgressBar progressBar;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        mAuth = FirebaseAuth.getInstance();

       // username = findViewById(R.id.username_register);
        fullname = findViewById(R.id.Fullname);
        email = findViewById(R.id.email);
        password = findViewById(R.id.password_register);
        address = findViewById(R.id.Address);
        sexe = findViewById(R.id.Sexe);
        age = findViewById(R.id.age);
        date_of_birth = findViewById(R.id.Date_of_Birth);

        sign_up = findViewById(R.id.register_btn);
        sign_up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                registerNewUser();
            }
        });

        //progressBar = findViewById(R.id.progress_register);

    }
    private void registerNewUser(){
        // Recuperaction des donnees entrees par user
        //final String username_ = username.getText().toString().trim();
        final String fullname_ = fullname.getText().toString().trim();
        final String email_ = email.getText().toString().trim();
        final String password_ = password.getText().toString().trim();
        final String address_ = address.getText().toString().trim();
        final String sexe_ = sexe.getText().toString().trim();
        final String age_ = age.getText().toString().trim();
        final String dateOfBirth_ = date_of_birth.getText().toString().trim();
        // Tous les champs sont requis
        /*if(username_.isEmpty()){
            username.setError("This Field is Required !");
            username.requestFocus();
            return;
        }*/
        if(fullname_.isEmpty()){
            fullname.setError("This Field is Required !");
            fullname.requestFocus();
            return;
        }
        // Test Email
        if(email_.isEmpty()){
            email.setError("This Field is Required !");
            email.requestFocus();
            return;
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(email_).matches()){
            email.setError("Retry with a valid address email !");
            email.requestFocus();
            return;
        }
        if(password_.isEmpty()){
            password.setError("This Field is Required !");
            password.requestFocus();
            return;
        }
        if(sexe_.isEmpty()){
            sexe.setError("This Field is Required !");
            sexe.requestFocus();
            return;
        }
        if(address_.isEmpty()){
            address.setError("This Field is Required !");
            address.requestFocus();
            return;
        }
        if(age_.isEmpty()){
            age.setError("This Field is Required !");
            age.requestFocus();
            return;
        }
        if(dateOfBirth_.isEmpty()){
            date_of_birth.setError("This Field is Required !");
            date_of_birth.requestFocus();
            return;
        }
        // Affichage de progress bar
        //progressBar.setVisibility(View.VISIBLE);
        // Creation du compte ar Firebase
        mAuth.createUserWithEmailAndPassword(email_,password_).addOnCompleteListener(
                new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            User user = new User(fullname_,email_,password_,sexe_,address_,age_,dateOfBirth_);
                            FirebaseDatabase.getInstance().getReference("Users").child(
                                    FirebaseAuth.getInstance().getCurrentUser().getUid()
                            ).setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if(task.isSuccessful()){
                                        Toast.makeText(RegistrationActivity.this,"User has been registered successfully ! ",Toast.LENGTH_LONG).show();
                                        //progressBar.setVisibility(View.GONE);
                                        // Rendez-vous Login :)
                                    }else{
                                        Toast.makeText(RegistrationActivity.this,"Registration failed ! Try Again ",Toast.LENGTH_LONG).show();
                                        //progressBar.setVisibility(View.GONE);
                                    }
                                }
                            });
                        }else{
                            Toast.makeText(RegistrationActivity.this,"Registration failed !",Toast.LENGTH_LONG).show();
                            //progressBar.setVisibility(View.GONE);
                        }
                    }
                });
    }

}