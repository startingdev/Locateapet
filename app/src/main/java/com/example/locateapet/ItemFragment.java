// ItemFragment.java
package com.example.locateapet;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;

public class ItemFragment extends Fragment {

    private RecyclerView recyclerView;
    private MyItemRecyclerViewAdapter adapter;
    private List<Item> itemList = new ArrayList<>();

    public ItemFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Initialize itemList with your data
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_item_list, container, false);

        EditText searchBar = view.findViewById(R.id.search_bar);
        Button searchButton = view.findViewById(R.id.search_button);
        recyclerView = view.findViewById(R.id.list);

        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        adapter = new MyItemRecyclerViewAdapter(itemList);
        recyclerView.setAdapter(adapter);

        searchButton.setOnClickListener(v -> {
            String query = searchBar.getText().toString().toLowerCase();
            List<Item> filteredList = new ArrayList<>();
            for (Item item : itemList) {
                if (item.getHeader().toLowerCase().contains(query) ||
                        item.getSpecies().toLowerCase().contains(query) ||
                        item.getDescription().toLowerCase().contains(query) || item.getTags().toLowerCase().contains(query))  {
                    filteredList.add(item);
                }
            }
            adapter.updateData(filteredList);
        });

        return view;
    }
}
