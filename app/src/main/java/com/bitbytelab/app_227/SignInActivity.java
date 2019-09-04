package com.bitbytelab.app_227;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignInActivity extends AppCompatActivity {

    EditText edtSignInEmail, edtSignInPass;
    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        edtSignInEmail = findViewById(R.id.edtSignInEmail);
        edtSignInPass = findViewById(R.id.edtSignInPass);
        findViewById(R.id.btnSignIn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String email= edtSignInEmail.getText().toString().trim();
                final String pass = edtSignInPass.getText().toString().trim();

                if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
                    edtSignInEmail.setError("Provide valid email!");
                    edtSignInEmail.requestFocus();
                    return;
                }
                if(TextUtils.isEmpty(pass)){
                    edtSignInPass.setError("Provide numeric unique password!");
                    edtSignInPass.requestFocus();
                    return;
                }

                firebaseAuth = FirebaseAuth.getInstance();
                firebaseAuth.signInWithEmailAndPassword(email,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(getApplicationContext(),"Signed in successfully!",Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(SignInActivity.this,UserActivity.class));
                            finish();
                        }else{
                            Toast.makeText(getApplicationContext(),"Signed up failed! Sign Up First!",Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(SignInActivity.this,SignUpActivity.class));
                            finish();
                        }
                    }
                });
            }
        });
    }
}
