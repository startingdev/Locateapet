package com.example.locateapet.ui.gallery;

import static android.content.ContentValues.TAG;

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
import androidx.lifecycle.ViewModelProvider;

import com.example.locateapet.MainActivity;
import com.example.locateapet.R;
import com.example.locateapet.databinding.FragmentGalleryBinding;
import com.example.locateapet.ui.login.LoginActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class GalleryFragment extends Fragment {

    //binding establishment (element access variable)
    private FragmentGalleryBinding binding;

    TextView phone;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        GalleryViewModel galleryViewModel =
                new ViewModelProvider(this).get(GalleryViewModel.class);

        binding = FragmentGalleryBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        phone = binding.phoneCens;

        //phone number variable
        final String[] mobile = {""};

        //database variables (including current user)
        FirebaseDatabase database =  FirebaseDatabase.getInstance("https://vol-project-2d4b0-default-rtdb.europe-west1.firebasedatabase.app/");
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();
        String reportId;
        reportId = user.getUid();
        DatabaseReference DBref = FirebaseDatabase.getInstance("https://vol-project-2d4b0-default-rtdb.europe-west1.firebasedatabase.app/").getReference().child("Users").child(reportId);

        //reading data (phone number) from database
        DBref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                 if (dataSnapshot.exists()){
                     mobile[0] = dataSnapshot.child("phone").getValue(String.class);
                     phone.setText("Current phone number: " + mobile[0]);
                 }else{
                     mobile[0] = "Error retrieving phone number!";
                     phone.setText(mobile[0]);
                 }
            }


            //error retrieving data
            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.d(TAG, databaseError.getMessage()); //Don't ignore errors!
            }
        });

        /*final TextView textView = binding.textGallery;
        galleryViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);*/

        return root;
    }


    //button for switching (changing) phone number
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        boolean auth_switch = true;

        if (auth_switch) {
            Button authbtn = view.findViewById(R.id.authbutton);

            authbtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    switch_activ();
                }
            });
        }
    }

    //startup for login actitvity
    public void switch_activ(){
        Intent i = new Intent(getContext(), LoginActivity.class);

        startActivity(i);
    }

    //exiting fragment
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}