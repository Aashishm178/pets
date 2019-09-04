package com.example.android.pets;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import com.example.android.pets.data.PetDbHelper;
import com.example.android.pets.data.petContract.petEntry;
import java.util.ArrayList;

public class CatalogActivity extends AppCompatActivity {
    private PetDbHelper dbHelper = new PetDbHelper(this);
    private PetAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_catalog);

        FloatingActionButton fab =  findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(CatalogActivity.this,EditorActivity.class);
                startActivity(i);
            }
        });
    }

    private void InsertPet()
    {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        ContentValues value = new ContentValues();
        value.put(petEntry.COLUMN_PET_BREED,"STREET");
        value.put(petEntry.COLUMN_PET_GENDER,"MALE");
        value.put(petEntry.COLUMN_PET_NAME,"DOG");
        value.put(petEntry.COLUMN_PET_WEIGHT,"150");
        value.put(petEntry.COLUMN_PET_HEIGHT,"100");
        long newRowId = db.insert(petEntry.TABLE_NAME,null,value);
        Log.i("DUMMY DATA","IS ENTERED");

        RecyclerView recyclerView =  findViewById(R.id.recycler_view_catlog);
        recyclerView.setLayoutManager(new GridLayoutManager(getApplicationContext(),2));
        final ArrayList<PetRecord> petRecords = new ArrayList<>(dbHelper.getAllRecords());
        adapter = new PetAdapter(petRecords,this);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_catalog, menu);
        MenuItem searchitem = menu.findItem(R.id.search_m);
        SearchView searchView = (SearchView) searchitem.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                adapter.getFilter().filter(s);
                return false;
            }
        });
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {

        switch(item.getItemId()){

            case R.id.action_insert_dummy_data:
                InsertPet();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {
        super.onResume();
        RecyclerView recyclerView =  findViewById(R.id.recycler_view_catlog);
        recyclerView.setLayoutManager(new GridLayoutManager(getApplicationContext(),2));
        final ArrayList<PetRecord> petRecords = new ArrayList<>(dbHelper.getAllRecords());
        adapter = new PetAdapter(petRecords,this);
        recyclerView.setAdapter(adapter);
    }
}
