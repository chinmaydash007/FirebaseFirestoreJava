package com.example.firebasefirestorejava;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Nullable;

public class Main2Activity extends AppCompatActivity {
    EditText name;
    EditText priority;
    Button button;
    CollectionReference collectionReference;
    TextView textView;
    private String TAG = "mytag";
    DocumentSnapshot documentSnapshot;
    Button load;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        name = findViewById(R.id.editText);
        priority = findViewById(R.id.editText3);
        button = findViewById(R.id.button);
        textView = findViewById(R.id.textView);
        load = findViewById(R.id.button2);


        collectionReference = FirebaseFirestore.getInstance().collection("Persons");

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String namee = name.getText().toString();
                int proirity = Integer.parseInt(priority.getText().toString());
                Person person = new Person(namee, proirity);
                collectionReference.add(person).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Log.d(TAG, "onSuccess: "+documentReference);
                        Toast.makeText(Main2Activity.this, "Uploaded!!", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
        textView.setText("");
        load.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Query query;
                if (documentSnapshot == null) {
                    query = collectionReference.orderBy("priority").limit(3);
                } else {
                    query = collectionReference.orderBy("priority").startAfter(documentSnapshot).limit(3);
                }
                query.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        String data = "";


                        for (QueryDocumentSnapshot queryDocumentSnapshot : queryDocumentSnapshots) {
                            Person person = queryDocumentSnapshot.toObject(Person.class);
                            person.setUid(queryDocumentSnapshot.getId());
                            data += person.getName() + "\n" + person.getPriority() + "\n" + person.getUid() + "\n\n";
                        }
                        textView.append(data);
                        if (queryDocumentSnapshots.getDocuments().size() > 0) {
                            documentSnapshot = queryDocumentSnapshots.getDocuments().get(queryDocumentSnapshots.size() - 1);

                        }
                    }
                });
            }
        });


    }
}
