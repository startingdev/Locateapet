package com.example.locateapet;

import static java.lang.Integer.parseInt;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;

import androidx.annotation.NonNull;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.locateapet.databinding.ActivityMainBinding;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;
import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity {
    static RecyclerView recyclerView;

    public static String saved_count = "";

    static TextView checker;
    TextView check_two;
    private AppBarConfiguration mAppBarConfiguration;
    private ActivityMainBinding binding;
    int amount_cont;
    /*public void updateUserCounter(TextView text, TextView checker) {
        DatabaseReference DBref = FirebaseDatabase.getInstance("https://vol-project-2d4b0-default-rtdb.europe-west1.firebasedatabase.app/").getReference().child("count");
        Log.d("Point 1 reached!", ":D");
        DBref.get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (task.isSuccessful()) {
                    DataSnapshot dataSnapshot = task.getResult();
                    amount_cont = Objects.requireNonNull(dataSnapshot.getValue(Integer.class));
                    text.setText(String.valueOf(amount_cont));
                    checker.setText("Val found");
                    Log.d("Point 2 reached! Value loaded:", String.valueOf(amount_cont));
                    Log.d("Value text:", text.getText().toString());

                }else {
                    Log.d("Specific error", Objects.requireNonNull(task.getException().getMessage())); //Never ignore potential errors!
                }
                //return 0;
            }
        });
    }*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        checker = findViewById(R.id.counter_checker);
        check_two = findViewById(R.id.checker_change);
        recyclerView = findViewById(R.id.recycle_view);
        setSupportActionBar(binding.appBarMain.toolbar);
        binding.appBarMain.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        //updateUserCounter(checker, check_two);

        /*try {
            TimeUnit.SECONDS.sleep(3);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }*/

        /*do{
            Log.d("Saved_count (1st) val:", MainActivity.saved_count);
            saved_count = (String) checker.getText();
        }while(checker.getText().equals("Empty"));*/

        /*while (true){
            Log.d("Value_tracker", saved_count);
        }*/

        /*try {
            TimeUnit.SECONDS.sleep(3);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }*/
        DrawerLayout drawer = binding.drawerLayout;
        NavigationView navigationView = binding.navView;
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow)
                .setOpenableLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }
}