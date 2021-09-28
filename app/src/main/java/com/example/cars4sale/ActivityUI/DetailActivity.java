package com.example.cars4sale.ActivityUI;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.example.cars4sale.R;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.navigation.NavigationView;

public class DetailActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout drawer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        // Navigation toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Navigation drawer
        drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.getMenu().getItem(0).setChecked(true);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);

        Intent intent = getIntent();

        String[] carDetails = intent.getStringExtra("CarDetails").split("\\n");

        final TextView name = findViewById(R.id.textName);
        final TextView price = findViewById(R.id.textPrice);
        final TextView location = findViewById(R.id.textLocation);
        final TextView year = findViewById(R.id.textYear);

        name.setText("Car Model: " + carDetails[0]);
        price.setText("Car Price: " + carDetails[1]);
        location.setText("Car Location: " + carDetails[2]);
        year.setText("Production Year: " + carDetails[3]);

    }


    @Override
    // Click on the navigation item and start new activity.
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.nav_home:
                Intent intent_home = new Intent(this, MainActivity.class);
                intent_home.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent_home);
                break;
            case R.id.nav_help:
                Intent intent_help = new Intent(this, HelpActivity.class);
                startActivity(intent_help);
                break;
            case R.id.nav_info:
                MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(DetailActivity.this)
                        .setTitle("About")
                        .setMessage("Cars4Sale is co-developed by " +
                                "Yuxuan Lin, Xinxin Li, and Tianxiang Zhang " +
                                "for COMP2100 (2020_S2) group project.")
                        .setPositiveButton("Cheers!", (dialog, which) -> dialog.dismiss());
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}