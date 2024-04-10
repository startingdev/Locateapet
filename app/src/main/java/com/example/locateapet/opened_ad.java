package com.example.locateapet;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class opened_ad extends AppCompatActivity {
/*
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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_opened_ad);
        DatabaseReference DBref = FirebaseDatabase.getInstance("https://vol-project-2d4b0-default-rtdb.europe-west1.firebasedatabase.app/").getReference().child("Reports");
        TextView op_header = findViewById(R.id.op_header);
        TextView op_species = findViewById(R.id.op_species);
        TextView op_desc = findViewById(R.id.op_desc);
        ImageView op_picture = findViewById(R.id.op_picture);
        int op_ad = 1;
        DBref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                final int[] count_data = {0};
                Log.d("kys ","reports.get(0).description");
                if (dataSnapshot.exists()) {
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        for (DataSnapshot secondSnapshot : snapshot.getChildren()) {
                            Reports report = new Reports("desc", "head", "spec", "pict");
                            report.description = secondSnapshot.child("description").getValue(String.class);
                            report.header = secondSnapshot.child("header").getValue(String.class);
                            report.species = secondSnapshot.child("species").getValue(String.class);
                            report.picture = secondSnapshot.child("picture").getValue(String.class);
                            if (count_data[0] < 2) {
                                reports.set(count_data[0], report);
                                //Log.d("amount ", saved_count);
                                second_holder[count_data[0]].desc_View.setText(report.description);
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
*/

}