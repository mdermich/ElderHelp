package com.example.oldpeoplehelp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.content.Intent;

import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toolbar;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;


public class ContactListActivity extends AppCompatActivity
{
    private Toolbar mToolbar;
    private RecyclerView FindFriendsRecyclerList;
    private DatabaseReference UsersRef;
    private FirebaseUser currentuser;
    TextView setting;
    DrawerLayout drawerLayout;
    private static final int REQUEST_RECORD_AUDIO_PERMISSION = 200;
    private SpeechRecognizer speechRecognizer;
    private Intent intentRecognizer;
    private ImageButton imgbtn;

    public void ClickSet(View view) {
        redirectActivity(this,SettingsActivity.class);
    }
    public void StartButton(View view){
        view.setBackgroundResource(R.drawable.buttonstyle_mic_clicked);
        ImageButton imageButton = (ImageButton) view;
        imageButton.setImageResource(R.drawable.ic_mic_clicked);
        speechRecognizer.startListening(intentRecognizer);
    }
    public static void redirectActivity(Activity source, Class destination) {
        Intent intent = new Intent(source,destination);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        source.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_list);
        drawerLayout = findViewById(R.id.drawer_layout_contactList);

        UsersRef = FirebaseDatabase.getInstance().getReference().child("Users");


        FindFriendsRecyclerList = (RecyclerView) findViewById(R.id.find_friends_recycler_list);
        FindFriendsRecyclerList.setLayoutManager(new LinearLayoutManager(this));

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


    @Override
    protected void onStart()
    {
        super.onStart();

        FirebaseRecyclerOptions<User> options =
                new FirebaseRecyclerOptions.Builder<User>()
                        .setQuery(UsersRef, User.class)
                        .build();

        FirebaseRecyclerAdapter<User, FindFriendViewHolder> adapter =
                new FirebaseRecyclerAdapter<User, FindFriendViewHolder>(options) {
                    @Override
                    protected void onBindViewHolder(@NonNull FindFriendViewHolder holder, final int position, @NonNull final User user) {
                        currentuser = FirebaseAuth.getInstance().getCurrentUser();

                        if(!currentuser.getEmail().equals(user.getEmail())){
                            holder.userName.setText(user.getFullname());}
                        else{
                            holder.itemView.setVisibility(View.GONE);
                            holder.itemView.setLayoutParams(new RecyclerView.LayoutParams(0, 0));
                        }

                        /////for image profile
                        if(user.getImage()!=null) {
                            Picasso.get().load(user.getImage()).placeholder(R.drawable.ic_user).into(holder.profileImage);
                        }

                        holder.itemView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view)
                            {
                                String visit_user_id = getRef(position).getKey();

                                Intent chatIntent = new Intent(ContactListActivity.this, ChatActivity.class);
                                chatIntent.putExtra("visit_user_id", visit_user_id);
                                chatIntent.putExtra("visit_user_name", user.getFullname());

                                chatIntent.putExtra("visit_user_image", user.getImage());

                                startActivity(chatIntent);
                            }
                        });
                    }


                    @NonNull
                    @Override
                    public FindFriendViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i)
                    {
                        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.user_item, viewGroup, false);
                        FindFriendViewHolder viewHolder = new FindFriendViewHolder(view);
                        return viewHolder;
                    }
                };

        FindFriendsRecyclerList.setAdapter(adapter);

        adapter.startListening();
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
        redirectActivity(this, TestMenuActivity.class);
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
        recreate();
    }
    public void ClickEmergencyCalls(View view){
        // Redirect activity to Chat : just Test
        redirectActivity(this,CallsActivity.class);
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


    @Override
    protected void onPause() {
        super.onPause();
        // Fermer le drawer
        closeDrawer(drawerLayout);
    }


    public static class FindFriendViewHolder extends RecyclerView.ViewHolder
    {
        TextView userName;
        CircleImageView profileImage;


        public FindFriendViewHolder(@NonNull View itemView)
        {
            super(itemView);

            userName = itemView.findViewById(R.id.fullname);

            profileImage = itemView.findViewById(R.id.users_profile_image);
        }
    }

}