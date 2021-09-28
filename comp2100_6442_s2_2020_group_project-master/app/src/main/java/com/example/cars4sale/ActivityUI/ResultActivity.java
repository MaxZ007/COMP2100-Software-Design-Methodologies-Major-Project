package com.example.cars4sale.ActivityUI;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

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

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import static com.example.cars4sale.DataStructure.SearchUtils.groupList;
import static com.example.cars4sale.DataStructure.SearchUtils.node;
import static com.example.cars4sale.DataStructure.SearchUtils.return_list;

public class ResultActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private static NodeList listUltra;
    private static Map mapUltra = new HashMap();
    private DrawerLayout drawer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

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

        // Read XML file from Assets in Android.
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        try {
            DocumentBuilder builder = factory.newDocumentBuilder();
            InputStream is = getAssets().open("carData.xml");
            Document d = builder.parse(is);
            listUltra = d.getElementsByTagName("car");
            node(listUltra);
            mapUltra = groupList(return_list(listUltra));
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Get "Query" from main activity.
        final String query = Objects.requireNonNull(getIntent().getExtras()).getString("Query");

        // Use MyTokenizer to tokenize the query. It can also pre-treat the fuzzy (partially correct) queries.
        MyTokenizer _tokenizer = new MyTokenizer(query);
        Exp _exp = new Parser(_tokenizer).parseExp();
        Map mapResult = _exp.evaluate(mapUltra, listUltra);

        // Build an ArrayList to store car details for the ListView display.
        final ArrayList<String> arr = new ArrayList<>();

        // Explicit and reformat the four properties of each car and add them to the ArrayList.
        for (Object object : mapResult.values()) {
            List<String> str = (List<String>) object;
            String name = str.get(1);
            String location = str.get(2);
            String price = str.get(3);
            String year = str.get(4);
            arr.add(name + "\n" + "$" + price + "\n" + location + "\n" + year);
        }

        // Display the search results in the ListView
        final ArrayAdapter arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, arr);
        final ListView listView = findViewById(R.id.listViewResult);
        listView.setAdapter(arrayAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String carDetails = listView.getItemAtPosition(i).toString();

                Intent intent = new Intent(ResultActivity.this, DetailActivity.class);
                intent.putExtra("CarDetails", carDetails);
                startActivity(intent);

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
                MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(ResultActivity.this)
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
}