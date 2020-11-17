package com.sharad.tgji;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

public class ChatActivity extends AppCompatActivity {
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    EditText rollnum;
    TextView roll;
    TextView name;
    TextView div;
    TextView atten;
    TextView dept;
    LinearLayout layout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        rollnum = findViewById(R.id.roll_num);
        roll = findViewById(R.id.roll);
        name = findViewById(R.id.name);
        div = findViewById(R.id.div);
        atten = findViewById(R.id.atten);
        dept = findViewById(R.id.dept);
        layout = findViewById(R.id.layout_id);
    }

    public void loadData(View view) {
        String rollid = rollnum.getText().toString();
        if (rollid.equals("")) {
            Toast.makeText(this, "Enter roll number", Toast.LENGTH_SHORT).show();
        } else {
            db.collection("student").whereEqualTo("roll_num", rollid)
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful()) {
                                layout.setVisibility(View.VISIBLE);
                                for (QueryDocumentSnapshot document : task.getResult()) {
                                    name.setText(document.get("name").toString());
                                    roll.setText("Roll Number: " + document.get("roll_num").toString());
                                    div.setText("Division: " + document.get("div").toString());
                                    dept.setText("Class: " + document.get("class").toString());
                                    atten.setText("Attendance: " + document.get("attendance").toString());
                                }

                            } else
                                Toast.makeText(ChatActivity.this, "Error getting Data", Toast.LENGTH_LONG).show();
                        }
                    });
        }
    }
}