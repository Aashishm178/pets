package com.example.android.pets;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import java.util.ArrayList;
import java.util.List;
import com.example.android.pets.data.PetDbHelper;
import com.example.android.pets.data.petContract.petEntry;

public class SinglePetRecord extends AppCompatActivity {

    List<PetRecord> list = new ArrayList<>();
    EditText name,breed,weight,height,gender;
    PetRecord petRecord;
    AlertDialog.Builder builder;
    PetDbHelper mPetdbHelper = new PetDbHelper(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_pet_record);

        Intent i = getIntent();
        list = (List<PetRecord>) i.getSerializableExtra("LIST");
        int position = getIntent().getIntExtra("position",0);
        petRecord = list.get(position);

        name = findViewById(R.id.editext_pet_name_single_record);
        name.setText(petRecord.getPet_Name_Database());

        breed = findViewById(R.id.editext_breed_pet_single_record);
        breed.setText(petRecord.getPet_Breed_Database());

        weight = findViewById(R.id.editext_pet_weight_single_record);
        weight.setText(petRecord.getPet_Weight_Database());

        height = findViewById(R.id.editext_pet_height_single_record);
        height.setText(petRecord.getPet_Height_Database());

        gender = findViewById(R.id.editext_pet_gender_single_record);
        gender.setText(petRecord.getPet_Gender_Database());

        assert getSupportActionBar() != null;
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_pet_single_record,menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.save_option:
                update_database();
                return true;
            case R.id.delete_button:
                delete_database();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void update_database(){
        Intent i = getIntent();
        list = (List<PetRecord>) i.getSerializableExtra("LIST");
        int position = getIntent().getIntExtra("position",0);
        petRecord = list.get(position);

         mPetdbHelper = new PetDbHelper(this);
        SQLiteDatabase db = mPetdbHelper.getWritableDatabase();

        String db_name,db_breed,db_height,db_weight,db_gender;
        name = findViewById(R.id.editext_pet_name_single_record);
        breed = findViewById(R.id.editext_breed_pet_single_record);
        weight = findViewById(R.id.editext_pet_weight_single_record);
        height = findViewById(R.id.editext_pet_height_single_record);
        gender = findViewById(R.id.editext_pet_gender_single_record);

        db_name = name.getText().toString().toUpperCase();
        db_breed = breed.getText().toString().toUpperCase();
        db_height = height.getText().toString();
        db_weight = weight.getText().toString();
        db_gender = gender.getText().toString().toUpperCase();

        ContentValues data = new ContentValues();
        data.put(petEntry.COLUMN_PET_NAME,db_name);
        data.put(petEntry.COLUMN_PET_HEIGHT,db_height);
        data.put(petEntry.COLUMN_PET_BREED,db_breed);
        data.put(petEntry.COLUMN_PET_WEIGHT,db_weight);
        data.put(petEntry.COLUMN_PET_GENDER,db_gender);

        db.update(petEntry.TABLE_NAME,data,"_id="+petRecord.getID_Databse(),null);
        finish();
    }

    private void delete_database(){
        builder = new AlertDialog.Builder(this);
        builder.setMessage("Are you sure you want to delete this?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        Intent i = getIntent();
                        list = (List<PetRecord>) i.getSerializableExtra("LIST");
                        int position = getIntent().getIntExtra("position",0);
                        petRecord = list.get(position);

                        SQLiteDatabase db = mPetdbHelper.getWritableDatabase();
                        db.delete(petEntry.TABLE_NAME,"_id="+petRecord.getID_Databse(),null);
                        finish();

                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                    }
                });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }
}
