package com.example.firebasefirestorejava;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.ListenerRegistration;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.SetOptions;
import com.google.firebase.firestore.auth.User;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Nullable;

public class MainActivity extends AppCompatActivity {
    EditText name_editext, number_editext, editText_priority;
    Button submit;
    FirebaseFirestore db;
    TextView textView;
    private static final String TAG = "mytag";
    CollectionReference collectionReference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        name_editext = findViewById(R.id.editText);
        number_editext = findViewById(R.id.editText2);
        submit = findViewById(R.id.button);
        textView = findViewById(R.id.textView);
        editText_priority = findViewById(R.id.editText3);


        db = FirebaseFirestore.getInstance();
        collectionReference = db.collection("Notebooks");


        //        collectionReference.whereLessThanOrEqualTo("priority",5).whereGreaterThanOrEqualTo("priority",2)  *** This will work because the range is illusive.
        //        collectionReference.whereLessThanOrEqualTo("priority",2).whereGreaterThanOrEqualTo("priority",5)  *** This will not work because the range is Exclusive.
//        Task<QuerySnapshot> querySnapshotTask1 = collectionReference.whereLessThan("priority", 2).orderBy("priority").get();
//        Task<QuerySnapshot> querySnapshotTask2 = collectionReference.whereGreaterThan("priority", 2).orderBy("priority").get();
//        Task<List<Object>> listTask = Tasks.whenAllSuccess(querySnapshotTask1, querySnapshotTask2);
//        listTask.addOnSuccessListener(new OnSuccessListener<List<Object>>() {
//            @Override
//            public void onSuccess(List<Object> objects) {
//                for (Object querySnapshot : objects) {
//                    String data = "";
//                    for (QueryDocumentSnapshot queryDocumentSnapshot : (QuerySnapshot) querySnapshot) {
//                        Note note = queryDocumentSnapshot.toObject(Note.class);
//                        note.setUid(queryDocumentSnapshot.getId());
//                        data = note.getName() + "\n" + note.getPriority() + "\n" + note.getUid() + "\n" + note.getNumber() + "\n\n\n";
//                        textView.append(data);
//                    }
//                }
//            }
//        });

//        Task<List<QuerySnapshot>> listTask = Tasks.whenAllSuccess(querySnapshotTask1, querySnapshotTask2);
//        listTask.addOnSuccessListener(new OnSuccessListener<List<QuerySnapshot>>() {
//            @Override
//            public void onSuccess(List<QuerySnapshot> querySnapshots) {
//                String data = "";
//                for (QuerySnapshot queryDocumentSnapshots : querySnapshots) {
//                    for (QueryDocumentSnapshot queryDocumentSnapshot : queryDocumentSnapshots) {
//                        Note note = queryDocumentSnapshot.toObject(Note.class);
//                        note.setUid(queryDocumentSnapshot.getId());
//                        data = note.getName() + "\n" + note.getPriority() + "\n" + note.getUid() + "\n" + note.getNumber() + "\n\n\n";
//                        textView.append(data);
//                    }
//
//                }
//
//            }
//        });


        collectionReference.orderBy("priority").startAt(3).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                String data="";
                for(QueryDocumentSnapshot queryDocumentSnapshot:queryDocumentSnapshots){
                    Note note = queryDocumentSnapshot.toObject(Note.class);
                        note.setUid(queryDocumentSnapshot.getId());
                        data = note.getName() + "\n" + note.getPriority() + "\n" + note.getUid() + "\n" + note.getNumber() + "\n\n\n";
                        textView.append(data);
                }
            }
        });


        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = name_editext.getText().toString();
                String number = number_editext.getText().toString();
                if (editText_priority.length() == 0) {
                    editText_priority.setText("0");
                }
                int priority = Integer.parseInt(editText_priority.getText().toString());
                Note note = new Note(name, number, priority);
                collectionReference.add(note).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Log.d(TAG, "onSuccess: " + documentReference.getPath());
                    }
                });


            }
        });


    }


}
