package com.example.cars4sale.ActivityUI;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.example.cars4sale.Parser.Exp;
import com.example.cars4sale.Parser.Parser;
import com.example.cars4sale.R;
import com.example.cars4sale.Tokenizer.MyTokenizer;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout drawer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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

        // Contained button from Material Design
        final Button searchButton = findViewById(R.id.containedButton);

        // Search button click activity
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Read edit text contents as a string of search query
                EditText editText = findViewById(R.id.outlinedTextField);
                String query = editText.getText().toString();

                // Display a toast message when the search query is empty
                if (query.matches("")) {
                    Toast.makeText(getApplicationContext(), "Sry mate, plz don't leave it blank :)", Toast.LENGTH_SHORT).show();
                } else {
                    try {
                        // Test with tokenizer and parser. If the query is valid then start the result activity.
                        MyTokenizer _tokenizer = new MyTokenizer(query);
                        Exp _exp = new Parser(_tokenizer).parseExp();

                        // Start the result activity and put the query contents in extra
                        Intent intent = new Intent(getApplicationContext(), ResultActivity.class);
                        intent.putExtra("Query", query);
                        startActivity(intent);

                        // Clear the text field
                        editText.setText("");
                    }

                    // If the query is invalid (IllegalArgumentException), display a toast message.
                    catch (IllegalArgumentException e) {
                        Toast.makeText(getApplicationContext(), "Oops, try to fix ya query mate!", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
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
                MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(MainActivity.this)
                        .setTitle("About")
                        .setMessage("Cars4Sale is co-developed by " +
                                "Yuxuan Lin, Xinxin Li, and Tianxiang Zhang " +
                                "for COMP2100 (2020_S2) group project")
                        .setPositiveButton("Cheers!", (dialog, which) -> dialog.dismiss());
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    // Hide the keyboard when clicking outside of the text field
    public boolean dispatchTouchEvent(MotionEvent ev) {
        View v = getCurrentFocus();

        if (v != null && (ev.getAction() == MotionEvent.ACTION_UP || ev.getAction() == MotionEvent.ACTION_MOVE) &&
                v instanceof EditText &&
                !v.getClass().getName().startsWith("android.webkit.")) {
            int[] sourceCoordinates = new int[2];
            v.getLocationOnScreen(sourceCoordinates);
            float x = ev.getRawX() + v.getLeft() - sourceCoordinates[0];
            float y = ev.getRawY() + v.getTop() - sourceCoordinates[1];

            if (x < v.getLeft() || x > v.getRight() || y < v.getTop() || y > v.getBottom()) {
                hideKeyboard(this);
            }

        }
        return super.dispatchTouchEvent(ev);
    }

    // Hide the keyboard when clicking outside of the text fields
    private void hideKeyboard(Activity activity) {
        if (activity != null && activity.getWindow() != null) {
            activity.getWindow().getDecorView();
            InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
            if (imm != null) {
                imm.hideSoftInputFromWindow(activity.getWindow().getDecorView().getWindowToken(), 0);
            }
        }
    }
}
