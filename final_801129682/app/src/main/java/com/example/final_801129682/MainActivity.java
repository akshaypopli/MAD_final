package com.example.final_801129682;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    String TAG = "demo";
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    Button btn_addSchedule;
    ArrayList<Schedule> allSchedule = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getTrips();
        mRecyclerView = findViewById(R.id.mRecyclerView);

        mRecyclerView.setHasFixedSize(true);
        setTitle("Basic Scheduler");
        btn_addSchedule = findViewById(R.id.btn_addSchedule);
        btn_addSchedule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this, CreateSchedule.class);
                startActivity(i);
            }
        });


    }

    public void getTrips(){
        db.collection("Schedule").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    allSchedule.removeAll(allSchedule);
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        Log.d(TAG, document.getId() + " => " + document.getData());
                        Schedule tr = new Schedule(document.getData().get("name").toString(), document.getData().get("place").toString(),document.getData().get("date").toString(), document.getData().get("time").toString() );
                        allSchedule.add(tr);
                    }
                    if(allSchedule.size() >0){
                        mLayoutManager = new LinearLayoutManager(MainActivity.this);
                        mRecyclerView.setLayoutManager(mLayoutManager);

                        mAdapter = new ScheduleAdapter(allSchedule);

                        mRecyclerView.setAdapter(mAdapter);
                    }


                } else {
                    Log.d(TAG, "Error getting documents: ", task.getException());
                }
            }
        });
    }
}
