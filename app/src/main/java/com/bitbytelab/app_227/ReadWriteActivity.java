package com.bitbytelab.app_227;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bitbytelab.app_227.Models.Student;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.firebase.ui.database.SnapshotParser;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

public class ReadWriteActivity extends AppCompatActivity {

    Button btnWrite;
    EditText edtName, edtReg;
    Student student;
    DatabaseReference mDatabase;
    RecyclerView recyclerView;
    LinearLayoutManager linearLayoutManager;
    FirebaseRecyclerAdapter adapter;

    @Override
    protected void onStart() {
        super.onStart();
        adapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        adapter.stopListening();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_read_write);

        mDatabase = FirebaseDatabase.getInstance().getReference("students");

        btnWrite= findViewById(R.id.btn_write);
        edtName = findViewById(R.id.edt_name);
        edtReg = findViewById(R.id.edt_reg);
        btnWrite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = edtName.getText().toString().trim();
                String reg = edtReg.getText().toString().trim();
                String id = mDatabase.push().getKey();

                if(name.isEmpty() || reg.isEmpty()){
                    Toast.makeText(getApplicationContext(),"invalid data!",Toast.LENGTH_SHORT).show();

                }else{
                    student = new Student(id,name,reg);

                    mDatabase.child(id).setValue(student)
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Toast.makeText(getApplicationContext(),"write success!",Toast.LENGTH_SHORT).show();
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(getApplicationContext(),"write failed!",Toast.LENGTH_SHORT).show();
                                }
                            });
                }
            }
        });

        recyclerView = findViewById(R.id.recyclerview);
        linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setHasFixedSize(true);
        fetch();

    }
    public class ViewHolder extends RecyclerView.ViewHolder{
        LinearLayout root;
        TextView name, reg;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            root = itemView.findViewById(R.id.list_root);
            name = itemView.findViewById(R.id.tv_name);
            reg = itemView.findViewById(R.id.tv_reg);
        }
        public void setName(String nm){
            name.setText(nm);
        }
        public void setReg(String rg){
            reg.setText(rg);
        }
    }
    private void fetch() {
        Query query = FirebaseDatabase.getInstance()
                .getReference()
                .child("students");

        FirebaseRecyclerOptions<Student> options =
                new FirebaseRecyclerOptions.Builder<Student>()
                .setQuery(query, new SnapshotParser<Student>() {
                    @NonNull
                    @Override
                    public Student parseSnapshot(@NonNull DataSnapshot snapshot) {
                        return new Student(snapshot.child("id").getValue().toString(),
                                snapshot.child("name").getValue().toString(),
                                snapshot.child("reg").getValue().toString());
                    }
                }).build();
        adapter = new FirebaseRecyclerAdapter<Student, ViewHolder>(options) {

            @NonNull
            @Override
            public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item,parent,false);
                return new ViewHolder(view);
            }

            @Override
            protected void onBindViewHolder(@NonNull ViewHolder viewHolder, final int i, @NonNull Student student) {
                viewHolder.setName(student.getName());
                viewHolder.setReg(student.getReg());
                viewHolder.root.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Toast.makeText(ReadWriteActivity.this,String.valueOf(i),Toast.LENGTH_SHORT).show();
                    }
                });
            }
        };
        recyclerView.setAdapter(adapter);
    }
}








