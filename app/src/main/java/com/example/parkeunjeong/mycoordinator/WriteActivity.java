package com.example.parkeunjeong.mycoordinator;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class WriteActivity extends AppCompatActivity implements View.OnClickListener {

    private FirebaseFirestore mStore = FirebaseFirestore.getInstance();

    private EditText mWriteTitle;
    private EditText mWirteContents;
    private EditText mWriteName;

    private String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write);

        mWriteTitle = findViewById(R.id.write_title);
        mWirteContents = findViewById(R.id.write_contents);
        mWriteName = findViewById(R.id.write_name);

        findViewById(R.id.write_upload_btn).setOnClickListener(this);



    }

    @Override
    public void onClick(View v) {
        id = mStore.collection("Board").document().getId();
        Map<String, Object> post = new HashMap<>();
        post.put("id",id);
        post.put("title", mWriteTitle.getText().toString());
        post.put("contents", mWirteContents.getText().toString());
        post.put("name", mWriteName.getText().toString());

        mStore.collection("Board").document(id).set(post)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(WriteActivity.this,"업로드 성공", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(WriteActivity.this,"업로드 실패", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
