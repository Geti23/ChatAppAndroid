package com.android.chatterthirdassignment;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;
import java.util.Collections;

import static com.android.chatterthirdassignment.MainActivity2.reciever;
import static com.android.chatterthirdassignment.MainActivity.name;

public class MainActivity3 extends AppCompatActivity {
    FirebaseDatabase database;
    DatabaseReference reference;
    TextView textView;
    EditText editText;
    Calendar time;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);
        database = FirebaseDatabase.getInstance();
        Collections.sort(reciever);
        reference = database.getReference().child("messages").child(reciever.get(0)+"_"+reciever.get(1));
        textView = findViewById(R.id.textView3);
        editText = findViewById(R.id.editTextTextPersonName4);
        time = Calendar.getInstance();

        reference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                textView.setText(textView.getText()+"\n"+snapshot.getValue(String.class));
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    public void send(View view){
        int hour = time.get(Calendar.HOUR_OF_DAY);
        int minute = time.get(Calendar.MINUTE);
        reference.push().setValue(hour+":"+minute+"  "+name+":\n"+editText.getText().toString()+"\n");
        editText.setText("");
    }
}