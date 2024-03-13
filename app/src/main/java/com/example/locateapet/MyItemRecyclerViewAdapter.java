package com.example.locateapet;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.locateapet.placeholder.PlaceholderContent.PlaceholderItem;
import com.example.locateapet.databinding.FragmentItemBinding;
import com.example.locateapet.ui.gallery.GalleryFragment;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

/**
 * {@link RecyclerView.Adapter} that can display a {@link PlaceholderItem}.
 * TODO: Replace the implementation with code for your data type.
 */
public class MyItemRecyclerViewAdapter extends RecyclerView.Adapter<MyItemRecyclerViewAdapter.ViewHolder> {

    private final List<PlaceholderItem> mValues;
    int amount_cont = 0;

    DataSnapshot data;
    public MyItemRecyclerViewAdapter(List<PlaceholderItem> items) {
        mValues = items;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        
        //TextView parent.findViewById(R.id.counter_checker);
        /*do {
            getData(MainActivity.saved_count);
            Log.d("Saved_count val:", MainActivity.saved_count);
        }while(MainActivity.saved_count.equals("Empty"));*/

        /*try {
            TimeUnit.SECONDS.sleep(3);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }*/
        return new ViewHolder(FragmentItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));

    }
    public class Reports {
        public String description;
        public String header;
        public String species;
        public String picture;
        public Reports(String description,String header,String species,String picture) {
            this.description = description;
            this.header = header;
            this.species = species;
            this.picture = picture;
        }
    }
    List<Reports> reports = new ArrayList<>();
    Reports test_report = new Reports("desc", "head", "spec", "pict");
    ViewHolder[] second_holder = new ViewHolder[2];

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        try {
            TimeUnit.SECONDS.sleep(3);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        //TextView text_temp = holder.itemView.findViewById(R.id.counter_checker);
        TextView text_temp = MainActivity.checker;
        reports.add(test_report);
        Log.d("catcher_f", text_temp.getText().toString());
        holder.mItem = mValues.get(position);
        holder.desc_View.setText(reports.get(position).description);
        holder.head_View.setText(reports.get(position).header);
        holder.spec_View.setText(reports.get(position).species);
        second_holder[position] = holder;
        
        
    }

    public void getData(String saved_count) {

        Log.d("123 ","reports.get(0).description");
        DatabaseReference DBref = FirebaseDatabase.getInstance("https://vol-project-2d4b0-default-rtdb.europe-west1.firebasedatabase.app/").getReference().child("Reports");
        DBref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                final int[] count_data = {0};
                Log.d("kys ","reports.get(0).description");
                if (dataSnapshot.exists()) {
                 //   user_counter = Objects.requireNonNull(dataSnapshot.child("counter_of_uploads").getValue(Integer.class));
                    //upload_data(user_counter, species_str);
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        //User user = snapshot.getValue(User.class);
                        //System.out.println(user.email);
                        for (DataSnapshot secondSnapshot : snapshot.getChildren()) {
                            Reports report = new Reports("desc", "head", "spec", "pict");
                            report.description = secondSnapshot.child("description").getValue(String.class);
                            report.header = secondSnapshot.child("header").getValue(String.class);
                            report.species = secondSnapshot.child("species").getValue(String.class);
                            report.picture = secondSnapshot.child("picture").getValue(String.class);
                            if (count_data[0] < 2) {
                                reports.set(count_data[0], report);
                                Log.d("123 ","reports.get(0).description");
                                Log.d("desc ",reports.get(count_data[0]).description);
                                Log.d("head ",reports.get(count_data[0]).header);
                                Log.d("spec",reports.get(count_data[0]).species);
                                Log.d("pic",reports.get(count_data[0]).picture);
                                Log.d("amount ", saved_count);

                                second_holder[count_data[0]].mItem = mValues.get(count_data[0]);
                                second_holder[count_data[0]].desc_View.setText(reports.get(count_data[0]).description);
                                second_holder[count_data[0]].head_View.setText(reports.get(count_data[0]).header);
                                second_holder[count_data[0]].spec_View.setText(reports.get(count_data[0]).species);
                                //final ViewHolder holder = MainActivity.recyclerView.getChildViewHolder(MainActivity.recyclerView.getChildAt(count_data[0]));
                                count_data[0] += 1;
                            }

                        }
                    }
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.d("kys ", databaseError.toString());
            }
        });
    }
    //

    @Override
    public int getItemCount() {
        //return reports.size();
        DatabaseReference DBref = FirebaseDatabase.getInstance("https://vol-project-2d4b0-default-rtdb.europe-west1.firebasedatabase.app/").getReference().child("count");
        Log.d("Point 1 reached!", ":D");


        DBref.get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (task.isSuccessful()) {
                    DataSnapshot dataSnapshot = task.getResult();
                    data = dataSnapshot;
                    amount_cont = Objects.requireNonNull(dataSnapshot.getValue(Integer.class));
                    //text.setText(String.valueOf(amount_cont));
                    //checker.setText("Val found");
                    Log.d("Point 2 reached! Value loaded:", String.valueOf(amount_cont));
                    //Log.d("Value text:", text.getText().toString());
                }else {
                    Log.d("Specific error", Objects.requireNonNull(task.getException().getMessage())); //Never ignore potential errors!
                }

            }
            //return  Objects.requireNonNull(data.getValue(Integer.class));

        });




        /*while(amount_cont == 0) {
            Log.d("Nah", "Im outta here");
        }*/
        return amount_cont;
        //return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final TextView desc_View;
        public final TextView head_View;
        public final TextView spec_View;

        //public final TextView grab_data;
       // public final ImageView pic_View;
        public PlaceholderItem mItem;

        public ViewHolder(FragmentItemBinding binding) {
            super(binding.getRoot());
            desc_View = binding.description;
            head_View = binding.header;
            spec_View = binding.species;
        }

     //   @Override
   //     public String toString() {
 //           return super.toString() + " '" + mContentView.getText() + "'";
      //  }
    }
}