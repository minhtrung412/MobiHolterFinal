package com.example.mobiholterfinal.fragment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.mobiholterfinal.LoginActivity;
import com.example.mobiholterfinal.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.ListenerRegistration;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

public class HomeFragment extends Fragment {
    Button signOut;
    TextView p_name, p_email, p_hospital, p_id, p_age, p_weight, p_height, p_gender, p_doctor;
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;
    String userId;
    String TAG= "Checkeddd";


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_home, container, false);
        signOut =v.findViewById(R.id.signOutBtn);
        p_name =v.findViewById(R.id.p_name);
        p_email =v.findViewById(R.id.p_email);
        p_hospital =v.findViewById(R.id.p_hos);
        p_id =v.findViewById(R.id.p_id);
        p_age =v.findViewById(R.id.p_age);
        p_weight =v.findViewById(R.id.p_weight);
        p_height =v.findViewById(R.id.p_height);
        p_gender =v.findViewById(R.id.p_gender);
        p_doctor =v.findViewById(R.id.p_doc);
        fAuth = FirebaseAuth.getInstance();
        userId = fAuth.getCurrentUser().getUid();
        fStore = FirebaseFirestore.getInstance();
        Log.d("userId", userId);

//        Query query = fStore.collection("users");
//        ListenerRegistration registration = query.addSnapshotListener(new EventListener<QuerySnapshot>() {
//            @Override
//            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
//                Log.d(TAG, "onEvent: ");
//            }
//        });
   //     registration.remove();
        DocumentReference documentReference = fStore.collection("users").document(userId);
        documentReference.addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@javax.annotation.Nullable DocumentSnapshot documentSnapshot, @javax.annotation.Nullable FirebaseFirestoreException e) {
                p_name.setText(documentSnapshot.getString("Name"));
                p_email.setText(documentSnapshot.getString("Email"));
                p_hospital.setText(documentSnapshot.getString("Hospital"));
                p_age.setText(documentSnapshot.getString("Age"));
                p_weight.setText(documentSnapshot.getString("Weight"));
                p_height.setText(documentSnapshot.getString("Height"));
                p_gender.setText(documentSnapshot.getString("Gender"));
                p_doctor.setText(documentSnapshot.getString("Doctor"));
                p_id.setText(documentSnapshot.getString("Patient ID"));
            }
        });




        signOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder signoutAlert =  new AlertDialog.Builder(getActivity());
                signoutAlert.setTitle("Sign out");
                signoutAlert.setMessage("Are you sure you want to sign out ?");
                signoutAlert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        FirebaseAuth.getInstance().signOut();//logout
                        Intent intent = new Intent(getActivity(), LoginActivity.class);
                        getActivity().startActivity(intent);
                    }
                });
                signoutAlert.setNeutralButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //do nothing
                    }
                });

                signoutAlert.show();


            }
        });



        return v;
    }

}