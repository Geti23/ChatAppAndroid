package com.android.chatterthirdassignment;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

import static com.android.chatterthirdassignment.MainActivity.auth;
import static com.android.chatterthirdassignment.MainActivity.name;

public class MainActivity2 extends AppCompatActivity {
    FirebaseDatabase database;
    DatabaseReference reference;
    EditText editText;
    TextView textView,textView2;
    List<String> availableNames = new ArrayList<>();
    protected static List<String> reciever = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        database = FirebaseDatabase.getInstance();
        reference = database.getReference("users");
        editText = findViewById(R.id.editTextTextPersonName3);
        textView = findViewById(R.id.textView);
        textView2 = findViewById(R.id.textView2);

        textView2.setText(textView2.getText()+name);

        reference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                textView.setText(textView.getText()+"\n\n \t"+snapshot.getValue(String.class));
                availableNames.add(snapshot.getValue(String.class));
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
    public void signout(View view){
        auth.signOut();
        reciever.removeAll(reciever);
        finish();
        startActivity(new Intent(this,MainActivity.class));
    }
    public void go(View view){
        if (availableNames.contains(editText.getText().toString())){
            reciever.removeAll(reciever);
            reciever.add(name);
            reciever.add(editText.getText().toString());
            startActivity(new Intent(this,MainActivity3.class));
        } else {
            Toast.makeText(this, "Such a contact does not exist in your list", Toast.LENGTH_LONG).show();
        }
    }
}