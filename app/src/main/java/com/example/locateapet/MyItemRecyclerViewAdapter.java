package com.example.locateapet;

import static androidx.fragment.app.FragmentManager.TAG;

import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.util.List;

public class MyItemRecyclerViewAdapter extends RecyclerView.Adapter<MyItemRecyclerViewAdapter.ViewHolder> {

    private List<Item> items;

    private DatabaseReference databaseReference;
    private ValueEventListener valueEventListener;

    public MyItemRecyclerViewAdapter(List<Item> items) {
        this.items = items;
        databaseReference = FirebaseDatabase.getInstance("https://vol-project-2d4b0-default-rtdb.europe-west1.firebasedatabase.app/").getReference().child("Reports");
        setupFirebaseListener();
    }

    private void setupFirebaseListener() {

        //loading reports onto device
        valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                items.clear();
                for (DataSnapshot userSnapshot : dataSnapshot.getChildren()) {
                    for (DataSnapshot reportSnapshot : userSnapshot.getChildren()) {
                        String description = reportSnapshot.child("description").getValue(String.class);
                        String header = reportSnapshot.child("header").getValue(String.class);
                        String species = reportSnapshot.child("species").getValue(String.class);
                        String picture = reportSnapshot.child("picture").getValue(String.class);
                        String tags = reportSnapshot.child("tags").getValue(String.class);
                        Item report = new Item(header, species, description, picture, tags);
                        if (report != null) {
                            items.add(report);
                        } else {
                            Log.e("Error registred!", "Failed to parse report");
                        }
                    }
                }
                //updating the recycler view (by calling onBindViewHolder)
                notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("Error registred!", "Failing to read reports data: " + error.getMessage());
            }
        };

        databaseReference.addValueEventListener(valueEventListener);
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        Item item = items.get(position);
        holder.header.setText(item.getHeader());
        holder.species.setText(item.getSpecies());
        holder.description.setText(item.getDescription());
        //holder.imageShowcase.setImageURI(item.getImage());

        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference storageRef = storage.getReferenceFromUrl(item.getImage().toString());

        // Loading the image in a thread
        storageRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                // Gettin URI of the selected image

                //holder.imageShowcase.setImageURI(uri);

                Picasso.with(holder.imageShowcase.getContext()).load(uri).into(holder.imageShowcase);

                // Loading selected image onto firebase
                /*Glide.with(binding.getRoot().getContext())
                        .load(imageUrl)
                        .into(binding.imageShowcase);*/
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                Log.e("Image parse failed", exception.toString());
            }
        });

    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public void updateData(List<Item> newItems) {
        items = newItems;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final TextView header;
        public final TextView species;
        public final TextView description;
        public final ImageView imageShowcase;

        public ViewHolder(View view) {
            super(view);
            header = view.findViewById(R.id.header);
            species = view.findViewById(R.id.species);
            description = view.findViewById(R.id.description);
            imageShowcase = view.findViewById(R.id.image_showcase);
        }
    }


    /*
    //list that contains all of the reports available
    private List<Reports> reports = new ArrayList<>();
    private DatabaseReference databaseReference;
    private ValueEventListener valueEventListener;

    public MyItemRecyclerViewAdapter() {
        databaseReference = FirebaseDatabase.getInstance("https://vol-project-2d4b0-default-rtdb.europe-west1.firebasedatabase.app/").getReference().child("Reports");
        setupFirebaseListener();
    }

    private void setupFirebaseListener() {

        //loading reports onto device
        valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                reports.clear();
                for (DataSnapshot userSnapshot : dataSnapshot.getChildren()) {
                    for (DataSnapshot reportSnapshot : userSnapshot.getChildren()) {
                        String description = reportSnapshot.child("description").getValue(String.class);
                        String header = reportSnapshot.child("header").getValue(String.class);
                        String species = reportSnapshot.child("species").getValue(String.class);
                        String picture = reportSnapshot.child("picture").getValue(String.class);
                        Reports report = new Reports(description, header, species, picture);
                        if (report != null) {
                            reports.add(report);
                        } else {
                            Log.e(TAG, "Failed to parse report");
                        }
                    }
                }
                //updating the recycler view (by calling onBindViewHolder)
                notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e(TAG, "Error reading reports data: " + error.getMessage());
            }
        };

        databaseReference.addValueEventListener(valueEventListener);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        return new ViewHolder(FragmentItemBinding.inflate(inflater, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(reports.get(position)); //Sending current report to be displayed
    }

    @Override
    public int getItemCount() {
        return reports.size(); // The amount of loaded reports
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public final TextView desc_View;
        public final TextView head_View;
        public final TextView spec_View;
        private final FragmentItemBinding binding;

        public ViewHolder(FragmentItemBinding binding) {
            super(binding.getRoot());
            desc_View = binding.description;
            head_View = binding.header;
            spec_View = binding.species;
            this.binding = binding;
        }

        public void bind(Reports report) {
            // Binding the report data to recycler view
            desc_View.setText(report.description);
            head_View.setText(report.header);
            spec_View.setText(report.species);

            FirebaseStorage storage = FirebaseStorage.getInstance();
            StorageReference storageRef = storage.getReferenceFromUrl(report.picture);

            // Loading the image in a thread
            storageRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                @Override
                public void onSuccess(Uri uri) {
                    // Gettin URI of the selected image
                    String imageUrl = uri.toString();

                    // Loading selected image onto firebase
                    Glide.with(binding.getRoot().getContext())
                            .load(imageUrl)
                            .into(binding.imageShowcase);
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception exception) {
                    Log.e("Image parse failed", exception.toString());
                }
            });
        }
    }

    public static class Reports {
        public String description;
        public String header;
        public String species;
        public String picture;

        public Reports() {
            // Default constructor required for calls to DataSnapshot.getValue(Reports.class)
        }

        public Reports(String description, String header, String species, String picture) {
            this.description = description;
            this.header = header;
            this.species = species;
            this.picture = picture;
        }
    }
     */
}
