package com.example.locateapet;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.locateapet.placeholder.PlaceholderContent.PlaceholderItem;
import com.example.locateapet.databinding.FragmentItemBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

/**
 * {@link RecyclerView.Adapter} that can display a {@link PlaceholderItem}.
 * TODO: Replace the implementation with code for your data type.
 */

public class MyItemRecyclerViewAdapter extends RecyclerView.Adapter<MyItemRecyclerViewAdapter.ViewHolder> {


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

    private int countFromFirebase = 0;
    private DatabaseReference databaseReference;
    private ValueEventListener valueEventListener;
    private List<PlaceholderItem> mValues;

    public MyItemRecyclerViewAdapter(List<PlaceholderItem> items) {
        mValues = items;
        databaseReference = FirebaseDatabase.getInstance("https://vol-project-2d4b0-default-rtdb.europe-west1.firebasedatabase.app/").getReference().child("count");
        setupFirebaseListener();
    }

    private void setupFirebaseListener() {
        valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    countFromFirebase = dataSnapshot.getValue(Integer.class);
                    notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e(TAG, "Error reading containers data: " + error.getMessage());
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
        PlaceholderItem item = mValues.get(position);
        holder.bind(item);
    }

    @Override
    public int getItemCount() {
        return countFromFirebase;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
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
        public void bind(PlaceholderItem item) {
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
                                    //Log.d("amount ", saved_count);

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
            binding.description.setText(String.valueOf(binding.description));
            binding.header.setText(String.valueOf(binding.header));
            binding.species.setText(String.valueOf(binding.species));

        }
    }
}

/*public class MyItemRecyclerViewAdapter extends RecyclerView.Adapter<MyItemRecyclerViewAdapter.ViewHolder> {

    private String container_number = "-1";
    private int countFromFirebase = 0;

    public interface MyCallback {
        void onCallback(int value);
    }



    private final List<PlaceholderItem> mValues;
    //String amount_cont;

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


        /*Log.e("start checkmark:", "onCreateViewHolder");
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
        /*try {
            TimeUnit.SECONDS.sleep(3);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }*/
        //TextView text_temp = holder.itemView.findViewById(R.id.counter_checker);
        /*TextView text_temp = MainActivity.checker;
        reports.add(test_report);
        Log.d("catcher_f", text_temp.getText().toString());
        holder.mItem = mValues.get(position);
        holder.desc_View.setText(reports.get(position).description);
        holder.head_View.setText(reports.get(position).header);
        holder.spec_View.setText(reports.get(position).species);
        second_holder[position] = holder;

        readData(new MyCallback() {
            @Override
            public void onCallback(int value) {
                countFromFirebase = value;
                Log.d("Fire in the hole!", "Code fired!");
                Log.d("Current value of CountFrFirebase:", String.valueOf(countFromFirebase));
                notifyDataSetChanged();
                // Вызов notifyDataSetChanged() из активности
                /* Handler(Looper.getMainLooper()).post(new Runnable() {
                    @Override
                    public void run() {
                        notifyDataSetChanged(); // Обновление RecyclerView с новыми данными
                    }
                });*/
           /*}
        });
    }

    public void readData(MyCallback myCallback) {
        DatabaseReference databaseReference = FirebaseDatabase.getInstance("https://vol-project-2d4b0-default-rtdb.europe-west1.firebasedatabase.app/").getReference().child("count");

        /*databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    int num = dataSnapshot.getValue(Integer.class);
                    container_number = String.valueOf(num);
                    Log.d("Retrieved value", String.valueOf(num));

                    myCallback.onCallback(num);
                }
                Log.d("My shi jammed!", "bruh");

                //else {
                    //Log.d("Error reading containers data", task.getException().getMessage());
                //}
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.d(TAG, error.getMessage());
            }
        });*/

        /*databaseReference.get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            //@Override
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (task.isSuccessful()) {
                    int num = task.getResult().getValue(Integer.class);
                    container_number = String.valueOf(num);
                    Log.d("Retrieved value", String.valueOf(num));
                    myCallback.onCallback(num); // Вызываем колбэк с полученными данными
                } else {
                    Log.d("Error reading containers data", task.getException().getMessage());
                }
            }
        });
    }*/

    /*public void ReturnTriggered() {
        //Log.d( "Container_number triggered", container_number);

        //InitReturn();
        //return Integer.parseInt(container_number);
    }*/

    /*void readData(new MyCallback() {
        @Override
        public void onCallback(int value) {
            countFromFirebase = value;
            RecyclerView.Adapter.notifyDataSetChanged(); // Обновление RecyclerView с новым значением
        }
    });*/

    /*public int InitReturn(){
        while (Objects.equals(container_number, "-1")){
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        return Integer.parseInt(container_number);
    }*/

    /*public void getData(String saved_count) {

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
                                //Log.d("amount ", saved_count);

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
    }*/

    // Использование метода readData


    /*public int readData(MyCallback myCallback) {
        DatabaseReference databaseReference = FirebaseDatabase.getInstance("https://vol-project-2d4b0-default-rtdb.europe-west1.firebasedatabase.app/").getReference().child("count");

        final int[] num = {0};
        databaseReference.get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (task.isSuccessful()) {
                    num[0] = task.getResult().getValue(Integer.class);
                    container_number = String.valueOf(num[0]);
                    Log.d("Check 1", container_number);

                    myCallback.onCallback(String.valueOf(num[0]));
                } else {
                    Log.d("Error reading containers data", task.getException().getMessage());
                }
            }
        });

        Log.d("Check 2", container_number);

        return 0;

    }*/

    /*@Override
    public int getItemCount() {
        Log.d("Return count from firebase:", String.valueOf(countFromFirebase));
        return countFromFirebase;
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
        }*/


     //   @Override
   //     public String toString() {
 //           return super.toString() + " '" + mContentView.getText() + "'";
      //  }
    /*}
}*/