package com.bitbytelab.app_227;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignUpActivity extends AppCompatActivity {

    EditText edtName, edtEmail, edtPass;
    DatabaseReference mDatabase;
    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        edtName = findViewById(R.id.edtName);
        edtEmail= findViewById(R.id.edtEmail);
        edtPass = findViewById(R.id.edtPass);
        findViewById(R.id.tvSignIn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SignUpActivity.this, SignInActivity.class));
            }
        });

        findViewById(R.id.btnSignUp).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String name = edtName.getText().toString().trim();
                final String email= edtEmail.getText().toString().trim();
                final String pass = edtPass.getText().toString().trim();

                if(TextUtils.isEmpty(name)){
                    edtName.setError("Provide a name!");
                    edtName.requestFocus();
                    return;
                }
                if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
                    edtEmail.setError("Provide valid email!");
                    edtEmail.requestFocus();
                    return;
                }
                if(TextUtils.isEmpty(pass)){
                    edtPass.setError("Provide numeric unique password!");
                    edtPass.requestFocus();
                    return;
                }
                if(pass.length()<6){
                    edtPass.setError("Password length must be at least 6 digit!");
                    edtPass.requestFocus();
                    return;
                }
                firebaseAuth = FirebaseAuth.getInstance();
                firebaseAuth.createUserWithEmailAndPassword(email,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(getApplicationContext(),"Signed up successfully!",Toast.LENGTH_SHORT).show();

                            String uid = firebaseAuth.getCurrentUser().getUid();

                            mDatabase = FirebaseDatabase.getInstance().getReference("users").child(uid);

                            mDatabase.child("name").setValue(name);
                            mDatabase.child("email").setValue(email);
                            mDatabase.child("pass").setValue(pass);

                            startActivity(new Intent(SignUpActivity.this,UserActivity.class));
                            finish();
                        }
                        else {
                            if(task.getException()instanceof FirebaseAuthUserCollisionException){
                                Toast.makeText(getApplicationContext(),"User Exists!",Toast.LENGTH_SHORT).show();
                            }else{
                                Toast.makeText(getApplicationContext(),"Sign up failed! "+task.getException().getMessage(),Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                });

            }
        });


    }
}
