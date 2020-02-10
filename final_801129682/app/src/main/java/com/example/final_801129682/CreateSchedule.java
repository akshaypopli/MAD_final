package com.example.final_801129682;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class CreateSchedule extends AppCompatActivity {

    Button btn_addPlace;
    Button btn_addDate;
    Button btn_addTime;
    Button btn_save;
    TextView tv_place;
    TextView tv_date;
    TextView tv_time;
    EditText et_title;
    TimePickerDialog picker;
    DatePickerDialog pickerDate;
    int REQ = 200;
    final static String PLACE = "place";
    final FirebaseFirestore db = FirebaseFirestore.getInstance();
    String TAG = "demo";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_schedule);
        setTitle("Schedule A Meeting");

        btn_addDate = findViewById(R.id.btn_addDate);
        tv_date = findViewById(R.id.tv_date);

        btn_addDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar cldr1 = Calendar.getInstance();
                int day = cldr1.get(Calendar.DAY_OF_MONTH);
                int month = cldr1.get(Calendar.MONTH);
                int year = cldr1.get(Calendar.YEAR);
                // date picker dialog
                pickerDate = new DatePickerDialog(CreateSchedule.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                tv_date.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
                            }
                        }, year, month, day);
                pickerDate.show();
            }
        });

        btn_addTime = findViewById(R.id.btn_addTime);
        tv_time = findViewById(R.id.tv_time);

        btn_addTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar cldr = Calendar.getInstance();
                int hour = cldr.get(Calendar.HOUR_OF_DAY);
                int minutes = cldr.get(Calendar.MINUTE);
                // time picker dialog
                picker = new TimePickerDialog(CreateSchedule.this,
                        new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker tp, int sHour, int sMinute) {
                                tv_time.setText(sHour + ":" + sMinute);
                            }
                        }, hour, minutes, true);
                picker.show();
            }
        });

        btn_addPlace = findViewById(R.id.btn_addPlace);
//        tv_time = findViewById(R.id.tv_time);
        btn_addPlace.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i= new Intent(CreateSchedule.this, AddPlace.class);
                i.putExtra(PLACE, "");
                startActivityForResult(i, REQ);
            }
        });

        tv_place = findViewById(R.id.tv_place);
        et_title = findViewById(R.id.et_title);
        btn_save = findViewById(R.id.btn_save);
        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(et_title.getText().toString() == ""){
                    et_title.setError("Please add title");
                }else if(tv_place.getText().toString()== "") {
                    Toast.makeText(getApplicationContext(), "Select Place", Toast.LENGTH_SHORT).show();
                }else if(tv_date.getText().toString() =="") {
                    Toast.makeText(getApplicationContext(), "Select Date", Toast.LENGTH_SHORT).show();
                }
                else if(tv_time.getText().toString() == "") {
                    Toast.makeText(getApplicationContext(), "Select time", Toast.LENGTH_SHORT).show();
                }else {
                    Map<String, Object> createSchedule = new HashMap<>();
                    createSchedule.put("name", et_title.getText().toString());
                    createSchedule.put("place", tv_place.getText().toString());
                    createSchedule.put("date", tv_date.getText().toString());
                    createSchedule.put("time", tv_time.getText().toString());

                    db.collection("Schedule").document().set(createSchedule).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Log.d(TAG, "DocumentSnapshot successfully written!");
                            Intent i = new Intent();
                            setResult(RESULT_OK, i);
                            finish();
                        }
                    });
                }

            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == REQ){
            if(resultCode == RESULT_OK){
//                Toast.makeText(this, data.getExtras().getString(COLOR_KEY), Toast.LENGTH_LONG);
                Log.d("datagot", data.getExtras().getString(PLACE));
                String pl = data.getExtras().getString(PLACE);
                tv_place.setText(pl);
            }

        }
    }
}
