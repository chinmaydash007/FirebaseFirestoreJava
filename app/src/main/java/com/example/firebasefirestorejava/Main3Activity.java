package com.example.firebasefirestorejava;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.DataCollectionDefaultChange;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.Transaction;
import com.google.firebase.firestore.WriteBatch;

public class Main3Activity extends AppCompatActivity {
    CollectionReference collectionReference;
    private static final String TAG = "mytag1";
    TextView textView;
    EditText name, priority;
    Button button;
    FirebaseFirestore firebaseFirestore;
    CollectionReference personsRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);
        collectionReference = FirebaseFirestore.getInstance().collection("Persons");
        textView = findViewById(R.id.textView5);
        name = findViewById(R.id.name);
        priority = findViewById(R.id.priority);
        button = findViewById(R.id.button3);
        firebaseFirestore = FirebaseFirestore.getInstance();
        personsRef = firebaseFirestore.collection("Persons");
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String namee = name.getText().toString();
                int priotito = Integer.parseInt(priority.getText().toString());
                Person person = new Person(namee, priotito);
                collectionReference.add(person);
            }
        });
        // writebatch();
        //executeTransaction();
        addPersonWithArray();


    }

    private void addPersonWithArray() {

    }

    private void executeTransaction() {
        firebaseFirestore.runTransaction(new Transaction.Function<Long>() {
            @Nullable
            @Override
            public Long apply(@NonNull Transaction transaction) throws FirebaseFirestoreException {
                DocumentReference documentReference = personsRef.document("New person");
                DocumentSnapshot documentSnapshot = transaction.get(documentReference);
                Long newpriority = documentSnapshot.getLong("priority") + 1;
                transaction.update(documentReference, "priority", newpriority);


                return newpriority;
            }
        }).addOnSuccessListener(new OnSuccessListener<Long>() {
            @Override
            public void onSuccess(Long aLong) {
                Toast.makeText(Main3Activity.this, aLong.toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void writebatch() {
        WriteBatch writeBatch = FirebaseFirestore.getInstance().batch();
        DocumentReference doc1 = collectionReference.document("New person");
        writeBatch.set(doc1, new Person("chinmay", 23));

        DocumentReference doc2 = collectionReference.document("DnABELZefOUWx7WDycXb");
        writeBatch.delete(doc2);

        writeBatch.commit().addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Toast.makeText(Main3Activity.this, "Done!!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
//        collectionReference.orderBy("date", Query.Direction.DESCENDING).addSnapshotListener(this, new EventListener<QuerySnapshot>() {
//            @Override
//            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
//                if (e != null) {
//                    Log.d(TAG, "onEvent: " + e.getMessage());
//                    return;
//                }
//                for (DocumentChange dc : queryDocumentSnapshots.getDocumentChanges()) {
//                    DocumentSnapshot documentSnapshot = dc.getDocument();
//                    String id = documentSnapshot.getId();
//                    int oldIndex = dc.getOldIndex();
//                    int newIndex = dc.getNewIndex();
//                    String data = "";
//                    switch (dc.getType()) {
//                        case ADDED:
//                            data = "Added :" + id + "\nold index:" + oldIndex + "\nNew Index " + newIndex + "\n\n\n ";
//                            textView.append(data);
//                            break;
//                        case REMOVED:
//                            data = "Removed :" + id + "\nold index: " + oldIndex + "\nNew Index" + newIndex + "\n\n\n";
//                            textView.append(data);
//                            break;
//                        case MODIFIED:
//                            data = "Modified :" + id + "\nold index: " + oldIndex + "\nNew Index" + newIndex + "\n\n\n";
//                            textView.append(data);
//                            break;
//                    }
//                }
//            }
//        });
    }
}
