package com.example.oldpeoplehelp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

public class ProfileActivity extends AppCompatActivity {

    private Button logout;
    private FirebaseUser user;
    private DatabaseReference reference;
    private String userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        logout = findViewById(R.id.logout);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(ProfileActivity.this,MainActivity.class));
            }
        });
        user = FirebaseAuth.getInstance().getCurrentUser();
        reference = FirebaseDatabase.getInstance().getReference("Users");
        userID = user.getUid();

        final TextView fullname = findViewById(R.id.fullname_profile);
        final TextView email = findViewById(R.id.email_profile);
        final TextView password = findViewById(R.id.password_profile);
        final TextView address = findViewById(R.id.Address_profile);
        final TextView age = findViewById(R.id.Age_profile);
        final TextView dateOfbirth = findViewById(R.id.DateOfBirth_profile);

        reference.child(userID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User userProfile = snapshot.getValue(User.class);
                if(userProfile != null){
                    // Recuperation des cordonnees
                    String fullName_ = userProfile.fullname;
                    String email_ = userProfile.email;
                    String password_ = userProfile.password;
                    String address_ = userProfile.address;
                    String age_ = userProfile.age;
                    String dateOfbirth_ = userProfile.dateOfBirth;
                    // Affectation des champs texte
                    fullname.setText(fullname.getText()+" "+fullName_);
                    email.setText(email.getText()+" "+email_);
                    password.setText(password.getText()+" "+password_);
                    address.setText(address.getText()+" "+address_);
                    age.setText(age.getText()+" "+age_);
                    dateOfbirth.setText(dateOfbirth.getText()+" "+dateOfbirth_);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(ProfileActivity.this,"Something wrong happened !",Toast.LENGTH_LONG).show();
            }
        });

    }
}