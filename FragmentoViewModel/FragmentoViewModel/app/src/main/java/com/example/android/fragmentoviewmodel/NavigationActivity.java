package com.example.android.fragmentoviewmodel;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.navigation.NavigationBarView;

public class NavigationActivity extends AppCompatActivity {

    NavigationBarView navigationBarView;

    FragmentoUno fragmentoUno;
    FragmentoDos fragmentoDos;

    CompartirViewModel compartirViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation);

        compartirViewModel = new ViewModelProvider(this).get(CompartirViewModel.class);

        fragmentoUno = new FragmentoUno();
        fragmentoDos = new FragmentoDos();

        getSupportFragmentManager().beginTransaction().replace(R.id.bottom_nav_layout, fragmentoUno).addToBackStack(null).commit();

        navigationBarView = findViewById(R.id.bottom_nav);
        navigationBarView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {

                    case (R.id.fragment_uno):

                        if (!fragmentoUno.isAdded()) {
                            getSupportFragmentManager().beginTransaction().replace(R.id.bottom_nav_layout, fragmentoUno).commit();
                        }

                        break;

                    case (R.id.fragment_dos):

                        if (!fragmentoDos.isAdded()) {
                            getSupportFragmentManager().beginTransaction().replace(R.id.bottom_nav_layout, fragmentoDos).commit();
                        }
                }
                return true;
            }
        });
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("ItemID", navigationBarView.getSelectedItemId());
        outState.putString("Nombre", compartirViewModel.getNombre().getValue());
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        int selectedItemId = savedInstanceState.getInt("ItemID");
        navigationBarView.setSelectedItemId(selectedItemId);
        compartirViewModel.setNombre(savedInstanceState.getString("Nombre"));
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
