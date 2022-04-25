package com.example.oldpeoplehelp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;

public class CallsActivity extends AppCompatActivity {

    DrawerLayout drawerLayout;
    private static final int REQUEST_CALL =1;
    private TextView callTextPolice,callTextAmbulance,callTextFire,callTextGendarmerie,callTextCovid;
    private Button callBtnPolice,callBtnAmbulance,callBtnFire,callBtnGendarmerie,callBtnCovid;
    private String numberCall;
    private static final int REQUEST_RECORD_AUDIO_PERMISSION = 200;
    private SpeechRecognizer speechRecognizer;
    private Intent intentRecognizer;
    private ImageButton imgbtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calls);

        drawerLayout = findViewById(R.id.drawer_layout_calls);
        // Emergency Calls
        // Police
        callTextPolice = findViewById(R.id.police_numb);
        callBtnPolice = findViewById(R.id.police_btn);
        callBtnPolice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                numberCall ="19";
                CallButton(numberCall);
            }
        });
        // Ambulance
        callTextAmbulance = findViewById(R.id.ambulance_numb);
        callBtnAmbulance = findViewById(R.id.ambulance_btn);
        callBtnAmbulance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                numberCall = "150";
                CallButton(numberCall);
            }
        });
        //Firefighters
        callTextFire = findViewById(R.id.fire_numb);
        callBtnFire = findViewById(R.id.fire_btn);
        callBtnFire.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                numberCall = "15";
                CallButton(numberCall);
            }
        });
        // Gendarmerie
        callTextGendarmerie = findViewById(R.id.gendarmerie_numb);
        callBtnGendarmerie = findViewById(R.id.gendarmerie_btn);
        callBtnGendarmerie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                numberCall = "177";
                CallButton(numberCall);
            }
        });
        // Covid
        callTextCovid = findViewById(R.id.covid_numb);
        callBtnCovid = findViewById(R.id.covid_btn);
        callBtnCovid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                numberCall = "0801 00 47 47";
                CallButton(numberCall);
            }
        });
        imgbtn = findViewById(R.id.mic);

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.RECORD_AUDIO}, PackageManager.PERMISSION_GRANTED);
        }

        intentRecognizer = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intentRecognizer.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        speechRecognizer = SpeechRecognizer.createSpeechRecognizer(this);
        speechRecognizer.setRecognitionListener(new RecognitionListener() {
            @Override
            public void onReadyForSpeech(Bundle bundle) {

            }

            @Override
            public void onBeginningOfSpeech() {

            }

            @Override
            public void onRmsChanged(float v) {

            }

            @Override
            public void onBufferReceived(byte[] bytes) {

            }

            @Override
            public void onEndOfSpeech() {
                speechRecognizer.stopListening();
                imgbtn.setBackgroundResource(R.drawable.buttonstyle_mic);
                imgbtn.setImageResource(R.drawable.ic_mic);
            }

            @Override
            public void onError(int i) {

            }

            private Boolean isActivated = false;
            private String[] activationKeyword = {"planner","medicine","map","text","call","settings"};


            @Override
            public void onResults(Bundle bundle) {
                ArrayList<String> matches = bundle.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);
                String string = "";
                if(matches != null){
                    string = matches.get(0);
                    for(String c : activationKeyword){
                        if(string.contains(c)){
                            imgbtn.setBackgroundResource(R.drawable.buttonstyle_mic);
                            imgbtn.setImageResource(R.drawable.ic_mic);
                            switch(c){
                                case "planner":{
                                    ClickPlanning(drawerLayout);
                                    break;
                                }
                                case "medicine":{
                                    ClickMedicinsReminder(drawerLayout);
                                    break;
                                }
                                case "map":{
                                    ClickZoneMapping(drawerLayout);
                                    break;
                                }
                                case "text":{
                                    ClickChat(drawerLayout);
                                    break;
                                }
                                case "call":{
                                    ClickEmergencyCalls(drawerLayout);
                                    break;
                                }
                                case "settings":{
                                    ClickSet(drawerLayout);
                                    break;
                                }
                                default: break;
                            }
                        } else{

                        }

                    }


                }

            }

            @Override
            public void onPartialResults(Bundle bundle) {

            }

            @Override
            public void onEvent(int i, Bundle bundle) {

            }
        });

    }

    private void CallButton(String number) {
        if(ContextCompat.checkSelfPermission(CallsActivity.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(CallsActivity.this,new String[] {Manifest.permission.CALL_PHONE},REQUEST_CALL);
        }else{
            String dial = "tel:"+number;
            startActivity(new Intent(Intent.ACTION_CALL, Uri.parse(dial)));
        }
    }

    public void ClickMenu(View view){
        MenuNavigationActivity.openDrawer(drawerLayout);
    }
    public void ClickLogo(View view){
        MenuNavigationActivity.closeDrawer(drawerLayout);
    }
    public void ClickHome(View view){
        redirectActivity(this,MenuNavigationActivity.class);
    }
    public void ClickPlanning(View view){
        // if planning is the test
        redirectActivity(this,PlanActivitiesActivity.class);
    }
    public void ClickMedicinsReminder(View view){
        redirectActivity(this,NotificationsMedicineActivity.class);
    }
    public void ClickZoneMapping(View view){
        redirectActivity(this,MapsActivity.class);
    }
    public void ClickChat(View view){
        redirectActivity(this,ContactListActivity.class);
    }
    public void ClickEmergencyCalls(View view){
        // Redirect activity to Chat : just Test
        recreate();
    }
    public void ClickSet(View view) {
        redirectActivity(this,SettingsActivity.class);
    }
    public void StartButton(View view){
        view.setBackgroundResource(R.drawable.buttonstyle_mic_clicked);
        ImageButton imageButton = (ImageButton) view;
        imageButton.setImageResource(R.drawable.ic_mic_clicked);
        speechRecognizer.startListening(intentRecognizer);
    }
    public void ClickLogout(View view){
        MenuNavigationActivity.logout(this);
    }
    public static void logout(final Activity activity) {
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setTitle("Logout");
        builder.setMessage("Are you sure you want to logout ?");
        builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                /*activity.finishAffinity();
                System.exit(0);*/
                FirebaseAuth.getInstance().signOut();
                redirectActivity(activity,MainActivity.class);
            }
        });
        builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        builder.show();
    }

    public static void redirectActivity(Activity source, Class destination) {
        Intent intent = new Intent(source,destination);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        source.startActivity(intent);
    }

    @Override
    protected void onPause() {
        super.onPause();
        MenuNavigationActivity.closeDrawer(drawerLayout);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(requestCode == REQUEST_CALL){
            if(grantResults.length >0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                CallButton(numberCall);
            }else{
                Toast.makeText(this,"permission DENIED",Toast.LENGTH_SHORT).show();
            }
        }
    }
}