package com.example.oldpeoplehelp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;

public class MenuNavigationActivity extends AppCompatActivity {

    // Initialisation des variables
    DrawerLayout drawerLayout;
    private FirebaseUser user;
    private DatabaseReference reference;
    private String userID;
    private static final int REQUEST_RECORD_AUDIO_PERMISSION = 200;
    private SpeechRecognizer speechRecognizer;
    private Intent intentRecognizer;
    private ImageButton imgbtn;
    private Button startButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_navigation);
        // Instanciation des variables
        drawerLayout = findViewById(R.id.drawer_layout);
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
    public void ClickMenu(View view){
        // Ouvrir le drawer du menu
        openDrawer(drawerLayout);
    }

    public static void openDrawer(DrawerLayout drawerLayout) {
        // Open drawer Layout
        drawerLayout.openDrawer(GravityCompat.START);
    }
    public void ClickLogo(View view){
        // Fermer le drawer
        closeDrawer(drawerLayout);
    }

    public static void closeDrawer(DrawerLayout drawerLayout) {
        // Fermer le drawer layout
        if(drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START);
        }
    }
    public void ClickHome(View view){
        // Reecreer l'activite
        recreate();
    }
    public void ClickPlanning(View view){
        // Redirect activity to Planning ACtivity : just Test
        redirectActivity(this,PlanActivitiesActivity.class);
    }
    public void ClickMedicinsReminder(View view){
        // Redirect activity to Take Medicins ACtivity : just Test
        redirectActivity(this,NotificationsMedicineActivity.class);
    }
    public void ClickZoneMapping(View view){
        // Redirect activity to search arounds location : just Test
        redirectActivity(this,MapsActivity.class);
    }
    public void ClickChat(View view){
        // Redirect activity to Chat : just Test
        redirectActivity(this,ContactListActivity.class);
    }
    public void ClickEmergencyCalls(View view){
        // Redirect activity to Chat : just Test
        redirectActivity(this,CallsActivity.class);
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
        logout(this);
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
        // Fermer le drawer
        closeDrawer(drawerLayout);
    }
}