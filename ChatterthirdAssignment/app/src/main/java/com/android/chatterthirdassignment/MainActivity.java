package com.android.chatterthirdassignment;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {
    EditText editText, editText2;
    protected static FirebaseAuth auth;
    protected static String name, email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        editText = findViewById(R.id.editTextTextPersonName);
        editText2 = findViewById(R.id.editTextTextPersonName2);
        auth = FirebaseAuth.getInstance();
    }
    public void signup(View view){
        auth.createUserWithEmailAndPassword(editText.getText().toString(),editText2.getText().toString())
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            email = editText.getText().toString();
                            name = extractName();
                            String userID = auth.getCurrentUser().getUid();
                            FirebaseDatabase.getInstance().getReference().child("users").child(userID).setValue(name);
                            Toast.makeText(MainActivity.this,"Signed-up successfully",Toast.LENGTH_LONG).show();
                            Intent intent = getIntent();
                            finish();
                            startActivity(intent);
                        } else {
                            Toast.makeText(MainActivity.this, task.getException().getMessage(), Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }
    public void signin(View view){
        Intent intent = new Intent(this,MainActivity2.class);
        auth.signInWithEmailAndPassword(editText.getText().toString(),editText2.getText().toString())
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            email = editText.getText().toString();
                            name = extractName();
                            /*String userID = auth.getCurrentUser().getUid();
                            FirebaseDatabase.getInstance().getReference().child("users").child(userID).setValue(name);*/
                            finish();
                            startActivity(intent);
                        } else {
                            Toast.makeText(MainActivity.this, task.getException().getMessage(), Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }
    public String extractName(){
        for (int i=0;i<editText.length();i++){
            if (email.charAt(i)=='@' || email.charAt(i)=='.'){
                return email.substring(0,i);
            }
        }
        return "Couldn't extract name";
    }
}