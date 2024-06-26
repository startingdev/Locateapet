package com.example.locateapet.ui.slideshow;

import static android.content.Context.MODE_PRIVATE;
import static androidx.appcompat.content.res.AppCompatResources.getDrawable;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.PickVisualMediaRequest;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.locateapet.MainActivity;
import com.example.locateapet.databinding.FragmentSlideshowBinding;
import com.example.locateapet.ui.home.HomeFragment;
import com.example.locateapet.ui.login.SMS_Conf_Page;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Objects;


public class CreateAdd extends Fragment {

    //binding establishment (element access variable)
    private FragmentSlideshowBinding binding;

    //FirebaseDatabase database = FirebaseDatabase.getInstance("https://vol-project-2d4b0-default-rtdb.europe-west1.firebasedatabase.app/");
    //DatabaseReference ReportRef = database.getReference("Report");



    //ReportRef.setValue("Hello, World!");

    //string species variable
    final String[] species_str = {""};

    //position of roller element
    final int[] species_list_index = new int[1];
    EditText header;
    EditText desc;
    EditText species;
    EditText tags_obj;

    //upload uri (pict hard drive adress)
    Uri global_Uri;

    Drawable picture;


    Button upload, cancel, picker;

    //auth system that is used to retrieve user
    FirebaseAuth mAuth;
    FirebaseStorage storage;
    ImageView selected;

    Spinner species_list;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        CreateAddViewModel slideshowViewModel =
                new ViewModelProvider(this).get(CreateAddViewModel.class);

        binding = FragmentSlideshowBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        /*final TextView textView = binding.textSlideshow;
        slideshowViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);*/

        //final int[] counter = new int[1];
        header = binding.header;
        desc = binding.desc;
        upload = binding.upload;
        picker = binding.picker;
        selected = binding.imageShowcase;
        species_list = binding.spinnerSpecies;
        tags_obj = binding.tags;

        //Google intent for grabbing picture (URI)
        ActivityResultLauncher<PickVisualMediaRequest> pickMedia =
                registerForActivityResult(new ActivityResultContracts.PickVisualMedia(), uri -> {
                    // Callback is invoked after the user selects a media item or closes the
                    // photo picker.
                    if (uri != null) {
                        Log.d("PhotoPicker", "Selected URI: " + uri);
                        global_Uri = uri;
                        selected.setImageURI(global_Uri);
                    } else {
                        Log.d("PhotoPicker", "No media selected");
                        //add global uri set to default no pict
                    }
                });


        /*final String[] header_str = new String[1];
        final String[] desc_str = new String[1];*/


        //setting up choice for species and saving picked parameter
        species_list.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //Log.e("check_data", String.valueOf(position));
                species_list_index[0] = position;
                species_str[0] = (String) species_list.getItemAtPosition(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        //initialisation of picker

        picker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                pickMedia.launch(new PickVisualMediaRequest.Builder()
                        .setMediaType(ActivityResultContracts.PickVisualMedia.ImageOnly.INSTANCE)
                        .build());

                //do {
                //selected.setImageURI(global_Uri);
                //}while (global_Uri != null);



                /*Intent imageDownload = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                imageDownload.setAction(Intent.ACTION_GET_CONTENT);
                imageDownload.setType("image/*");
                imageDownload.putExtra("crop", "true");
                imageDownload.putExtra("aspectX", 1);
                imageDownload.putExtra("aspectY", 1);
                imageDownload.putExtra("outputX", 200);
                imageDownload.putExtra("outputY", 200);
                imageDownload.putExtra("return-data", true);*/

                // Registers a photo picker activity launcher in single-select mode.
                //startActivityForResult(imageDownload, 1);
                //selected.setImageURI(global_Uri);
            }
        });


        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*header_str[0] = header.getText().toString();
                desc_str[0] = desc.getText().toString();

                picture = selected.getDrawable();

                mAuth = FirebaseAuth.getInstance();

                FirebaseDatabase database =  FirebaseDatabase.getInstance("https://vol-project-2d4b0-default-rtdb.europe-west1.firebasedatabase.app/");
                FirebaseUser user = mAuth.getCurrentUser();
                String reportId;
                reportId = user.getUid();*/
                //counter[0] = Integer.parseInt(database.getReference().child("Users").child(reportId).child("counter_of_uploads").get().toString());

                //new user created (upload action started)
                User_uploads user_e = new User_uploads(species_str);
                user_e.updateUserCounter();


                /*storage = FirebaseStorage.getInstance();
                DatabaseReference mRef =  database.getReference().child("Reports").child(reportId).child(String.valueOf(0));
                mRef.child("header").setValue(header_str[0]);
                //mRef.child("age").setValue(header_str);
                //mRef.child("gender").setValue(f);

                mRef.child("description").setValue(desc_str[0]);
                mRef.child("species").setValue(species_str[0]);
                StorageReference storageRef = storage.getReference();
                StorageReference mountainsRef = storageRef.child(reportId).child("photo.jpg");
                mountainsRef.putFile(global_Uri);
                mRef.child("picture").setValue(mountainsRef.toString());

                database.getReference().child("Users").child(reportId).child("counter_of_uploads").setValue(u_counter + 1);
                Toast.makeText(getContext(), "Report uploaded!", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(getContext(), MainActivity.class);
                startActivity(intent);*/
            }
        });

        //cancel

        return root;
    }


    //method that initiates data variables and proceed to upload data, while also reading upload count
    public void upload_data(int u_counter, final String[] species_str){

        //variables init
        final int[] species_list_index = new int[1];
        final String[] header_str = new String[1];
        final String[] desc_str = new String[1];
        final String[] tags_str = new String[1];

        //getting data
        header_str[0] = header.getText().toString();
        desc_str[0] = desc.getText().toString();
        tags_str[0] = tags_obj.getText().toString();

        picture = selected.getDrawable();

        mAuth = FirebaseAuth.getInstance();

        //sending data to firebase
        FirebaseDatabase database =  FirebaseDatabase.getInstance("https://vol-project-2d4b0-default-rtdb.europe-west1.firebasedatabase.app/");
        FirebaseUser user = mAuth.getCurrentUser();
        String reportId;
        reportId = user.getUid();
        storage = FirebaseStorage.getInstance();
        DatabaseReference mRef =  database.getReference().child("Reports").child(reportId).child(String.valueOf(u_counter));

        //checking whether the header and tags grabber is empty
        if (!(header_str[0].equals("") && tags_str[0].equals(""))){
            mRef.child("header").setValue(header_str[0]);
            mRef.child("species").setValue(species_str[0]);
            mRef.child("tags").setValue(tags_str[0]);
            //setting the desc value to default one (only if it is empty)
            if (!desc_str[0].equals("")) {
                mRef.child("description").setValue(desc_str[0]);
            }else{
                mRef.child("description").setValue("[Описание не предоставлено]");
            }
            //mRef.child("tags").setValue(tags_str);
            StorageReference storageRef = storage.getReference();
            StorageReference mountainsRef = storageRef.child(reportId).child("photo"+ String.valueOf(u_counter) + ".jpg");

            //seeing whether picture is set or not
            if (global_Uri != null){
                mountainsRef.putFile(global_Uri);
                mRef.child("picture").setValue(mountainsRef.toString());
            }else{
                mRef.child("picture").setValue("gs://vol-project-2d4b0.appspot.com/default-img/not-available.jpg");
            }

            //updating counter of uploads
            database.getReference().child("Users").child(reportId).child("counter_of_uploads").setValue(u_counter + 1);


            //DatabaseReference countRef = database.getReference().child("count");
            DatabaseReference countRef = database.getReference("count");
            countRef.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists()) {
                        int currentCountUpdates = dataSnapshot.getValue(Integer.class);
                        countRef.setValue(currentCountUpdates + 1);
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    // Обработка ошибок, если необходимо
                }
            });

            //@Override
            //@Override
            //public void onComplete(@NonNull Task<DataSnapshot> task) {

            //}

            /*@Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("Reading global counter fail", error.toString());
            }*/
            /*});*/

            Toast.makeText(getContext(), "Report uploaded!", Toast.LENGTH_LONG).show();
            Intent intent = new Intent(getContext(), MainActivity.class);
            startActivity(intent);

            //database.getReference().child("count").setValue(Integer.parseInt(database.getReference().child("count").get().toString()) + 1);
            //process finished successfully!
        }else {
            Toast.makeText(getContext(), "Please type in header for the report!", Toast.LENGTH_LONG).show();
        }


    }


    //aftermath of grabbing picture
    @Override
    public void onActivityResult(int reqCode, int resultCode, Intent data) {
        super.onActivityResult(reqCode, resultCode, data);

        Toast.makeText(getContext(), String.valueOf(reqCode), Toast.LENGTH_LONG).show();

        if (reqCode == 1) {
            try {
                final Uri imageUri = data.getData();
                final InputStream imageStream = getContext().getContentResolver().openInputStream(imageUri);
                final Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
                selected.setImageBitmap(selectedImage);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
                Toast.makeText(getContext(), "Something went wrong", Toast.LENGTH_LONG).show();
            }

        }else {
            //Toast.makeText(getContext(), "You haven't picked image",Toast.LENGTH_LONG).show();
        }
    }


    //the class that reads uploads and needed to start the uploading process

    class User_uploads {
        private int user_counter;
        final String[] species_str;

        User_uploads(final String[] species_str){
            user_counter = 0;
            this.species_str = species_str;
        }

        private void updateUserCounter() {
            mAuth = FirebaseAuth.getInstance();
            FirebaseUser user = mAuth.getCurrentUser();
            String reportId;
            reportId = user.getUid();
            DatabaseReference DBref = FirebaseDatabase.getInstance("https://vol-project-2d4b0-default-rtdb.europe-west1.firebasedatabase.app/").getReference().child("Users").child(reportId);
            DBref.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    //user_counter=Integer.parseInt(Objects.requireNonNull(dataSnapshot.child("counter_of_uploads").getValue(String.class)));
                    if (dataSnapshot.exists()) {
                        user_counter = Objects.requireNonNull(dataSnapshot.child("counter_of_uploads").getValue(Integer.class));
                        upload_data(user_counter, species_str);
                    }else{
                        FirebaseDatabase.getInstance("https://vol-project-2d4b0-default-rtdb.europe-west1.firebasedatabase.app/").getReference().child("Users").child(reportId).setValue(0);
                        upload_data(0, species_str);
                    }
                }
                @Override
                public void onCancelled(DatabaseError databaseError) {
                }
            });
        }

        /*int getUser_counter(){
            return user_counter;
        }*/
    }

    /*void update_picker(ImageView picker, Uri uri){
        new Thread(() -> {
            while
            picker.setImageURI(uri);
        }).start();
    }*/



    //exiting of current fragment
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}