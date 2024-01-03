package com.example.locateapet.ui.gallery;

import android.content.Intent;
import android.os.Bundle;
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

public class GalleryFragment extends Fragment {

    private FragmentGalleryBinding binding;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        GalleryViewModel galleryViewModel =
                new ViewModelProvider(this).get(GalleryViewModel.class);

        binding = FragmentGalleryBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final TextView textView = binding.textGallery;
        galleryViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);


        return root;
    }

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

    public void switch_activ(){
        Intent i = new Intent(getContext(), LoginActivity.class);

        startActivity(i);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}