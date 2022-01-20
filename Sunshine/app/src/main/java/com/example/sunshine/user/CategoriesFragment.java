package com.example.sunshine.user;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.example.sunshine.R;

public class CategoriesFragment extends Fragment {

    public CategoriesFragment()
    {

    }

    Button btnAdventure, btnComic;
    Button btnHistory, btnHorror;
    Button btnPoetry, btnRomance;

    Button btnDetective, btnScifi;
    Button btnReligion, btnAutobiography;
    Button btnFanfic, btnLiterature;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.user_fragment_categories, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        btnAdventure = (Button) view.findViewById(R.id.adventure);
        btnComic = (Button) view.findViewById(R.id.comics);

        btnHistory = (Button) view.findViewById(R.id.history);
        btnHorror = (Button) view.findViewById(R.id.horror) ;

        btnPoetry = (Button) view.findViewById(R.id.poetry);
        btnRomance = (Button) view.findViewById(R.id.romance);

        btnDetective = (Button) view.findViewById(R.id.detective);
        btnScifi = (Button) view.findViewById(R.id.science_fiction);

        btnReligion = (Button) view.findViewById(R.id.religion);
        btnAutobiography = (Button) view.findViewById(R.id.autobiographies);

        btnFanfic = (Button) view.findViewById(R.id.fanfiction);
        btnLiterature = (Button) view.findViewById(R.id.literary);



        btnAdventure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String value="Adventures";
                Intent i = new Intent(getContext(), ViewCategoryActivity.class);
                i.putExtra("key",value);
                startActivity(i);
                //startActivity(new Intent(getContext(), ViewCategoryActivity.class));
            }
        });

        btnComic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String value="Comics";
                Intent i = new Intent(getContext(), ViewCategoryActivity.class);
                i.putExtra("key",value);
                startActivity(i);
                //startActivity(new Intent(getContext(), ViewCategoryActivity.class));
            }
        });

        btnHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String value="History";
                Intent i = new Intent(getContext(), ViewCategoryActivity.class);
                i.putExtra("key",value);
                startActivity(i);
                //startActivity(new Intent(getContext(), ViewCategoryActivity.class));
            }
        });

        btnHorror.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String value="Horror";
                Intent i = new Intent(getContext(), ViewCategoryActivity.class);
                i.putExtra("key",value);
                startActivity(i);
                //startActivity(new Intent(getContext(), ViewCategoryActivity.class));
            }
        });

        btnPoetry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String value="Poetry";
                Intent i = new Intent(getContext(), ViewCategoryActivity.class);
                i.putExtra("key",value);
                startActivity(i);
                //startActivity(new Intent(getContext(), ViewCategoryActivity.class));
            }
        });

        btnRomance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String value="Romance";
                Intent i = new Intent(getContext(), ViewCategoryActivity.class);
                i.putExtra("key",value);
                startActivity(i);
                //startActivity(new Intent(getContext(), ViewCategoryActivity.class));
            }
        });

        btnDetective.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String value="Detective";
                Intent i = new Intent(getContext(), ViewCategoryActivity.class);
                i.putExtra("key",value);
                startActivity(i);
                //startActivity(new Intent(getContext(), ViewCategoryActivity.class));
            }
        });

        btnScifi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String value="Sci-fi";
                Intent i = new Intent(getContext(), ViewCategoryActivity.class);
                i.putExtra("key",value);
                startActivity(i);
                //startActivity(new Intent(getContext(), ViewCategoryActivity.class));
            }
        });

        btnReligion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String value="Religion";
                Intent i = new Intent(getContext(), ViewCategoryActivity.class);
                i.putExtra("key",value);
                startActivity(i);
                //startActivity(new Intent(getContext(), ViewCategoryActivity.class));
            }
        });

        btnAutobiography.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String value="Autobiography";
                Intent i = new Intent(getContext(), ViewCategoryActivity.class);
                i.putExtra("key",value);
                startActivity(i);
                //startActivity(new Intent(getContext(), ViewCategoryActivity.class));
            }
        });

        btnFanfic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String value="Fanfiction";
                Intent i = new Intent(getContext(), ViewCategoryActivity.class);
                i.putExtra("key",value);
                startActivity(i);
                //startActivity(new Intent(getContext(), ViewCategoryActivity.class));
            }
        });

        btnLiterature.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String value="Literary Fiction";
                Intent i = new Intent(getContext(), ViewCategoryActivity.class);
                i.putExtra("key",value);
                startActivity(i);
                //startActivity(new Intent(getContext(), ViewCategoryActivity.class));
            }
        });
    }
}
