package com.example.oldpeoplehelp;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class EventAdapter extends RecyclerView.Adapter<EventAdapter.EventViewHolder> {

    Context context;
    static ArrayList<Event> eventsList;
    private OnItemClickListener mListener;

    public EventAdapter(Context context, ArrayList<Event> eventsList) {
        this.context = context;
        this.eventsList = eventsList;
    }

    @NonNull
    @Override
    public EventAdapter.EventViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.activity_info_card, parent, false);
        return new EventViewHolder(v, mListener);
    }

    @Override
    public void onBindViewHolder(@NonNull EventAdapter.EventViewHolder holder, int position) {
        Event event = eventsList.get(position);
        holder.activity_name.setText(event.getEventName());
        holder.activity_desc.setText(event.getEventDescription());
        holder.activity_time.setText(event.getEventTime());
    }

    @Override
    public int getItemCount() {
        return eventsList.size();
    }

    public interface OnItemClickListener {
        void onDeleteClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }

    public static class EventViewHolder extends RecyclerView.ViewHolder{
        TextView activity_name, activity_desc, activity_time;
        public ImageView deleteImage;
        public EventViewHolder(@NonNull View itemView, final OnItemClickListener listener) {
            super(itemView);
            activity_name = itemView.findViewById(R.id.event_name_card);
            activity_desc = itemView.findViewById(R.id.event_desc_card);
            activity_time = itemView.findViewById(R.id.event_time_card);
            deleteImage = itemView.findViewById(R.id.delete_activity);

            deleteImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AlertDialog.Builder alertBuilder = new AlertDialog.Builder(deleteImage.getContext());
                    alertBuilder.setTitle("Delete Alert");
                    alertBuilder.setMessage("Are you sure you want to delete this event?");
                    alertBuilder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            if(listener != null){
                                int position = getAdapterPosition();
                                if(position != RecyclerView.NO_POSITION){
                                    listener.onDeleteClick(position);
                                }
                                /*DatabaseReference dbRef = FirebaseDatabase.getInstance().getReference("Event").child(eventsList.get(position).getIdEvent());
                                dbRef.removeValue();*/
                            }
                        }
                    });
                    alertBuilder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });

                    alertBuilder.show();
                }
            });
        }
    }
}