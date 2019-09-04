package com.example.android.pets;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import com.example.android.pets.data.PetDbHelper;
import com.example.android.pets.data.petContract.petEntry;

public class EditorActivity extends AppCompatActivity {

        private EditText mNameEditText;
        private EditText mBreedEditText;
        private EditText mWeightEditText;
        private EditText mHeightEditText;
        private Spinner mGenderSpinner;
        private String Pet_Name="";
        private String Pet_Breed="";
        private String Pet_Weight="";
        private String Pet_Height="";
        private int mGender = 0;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_editor);
            mGenderSpinner =  findViewById(R.id.spinner_gender);
            setupSpinner();

            assert getSupportActionBar() != null;
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

         @Override
         public boolean onSupportNavigateUp() {
            finish();
            return true;
        }


        private void insert_data_in_database()
        {
            PetDbHelper mPetdbHelper = new PetDbHelper(this);
            SQLiteDatabase db = mPetdbHelper.getReadableDatabase();

            ContentValues values = new ContentValues();

            values.put(petEntry.COLUMN_PET_NAME,Pet_Name);
            values.put(petEntry.COLUMN_PET_BREED,Pet_Breed);
            values.put(petEntry.COLUMN_PET_WEIGHT,Pet_Weight);
            if(mGender==0)
            values.put(petEntry.COLUMN_PET_GENDER,"Unknown");
            else if (mGender ==1)
                values.put(petEntry.COLUMN_PET_GENDER,"Male");
            else if (mGender ==2)
                values.put(petEntry.COLUMN_PET_GENDER,"Female");
            values.put(petEntry.COLUMN_PET_HEIGHT,Pet_Height);

            long newRow = db.insert(petEntry.TABLE_NAME,null,values);
        }



        private void setupSpinner(){

            ArrayAdapter genderSpinnerAdaptor = ArrayAdapter.createFromResource(this,
                    R.array.array_gender_options,android.R.layout.simple_spinner_item);
            genderSpinnerAdaptor.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
            mGenderSpinner.setAdapter(genderSpinnerAdaptor);

            mGenderSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    String selection = (String) parent.getItemAtPosition(position);
                    if (!TextUtils.isEmpty(selection)){
                        if (selection.equals(getString(R.string.gender_male))) {
                            mGender = petEntry.GENDER_MALE;
                        } else if (selection.equals(getString(R.string.gender_female))) {
                            mGender = petEntry.GENDER_FEMALE;
                        } else {
                            mGender = petEntry.GENDER_UNKNOWN;
                        }
                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {
                          mGender=0;
                }
            });
        }

        @Override
        public boolean onCreateOptionsMenu(Menu menu) {
            // Inflate the menu options from the res/menu/menu_editor.xml file.
            // This adds menu items to the app bar.
            getMenuInflater().inflate(R.menu.menu_editor, menu);
            return true;
        }

        @Override
        public boolean onOptionsItemSelected(MenuItem item) {
            // User clicked on a menu option in the app bar overflow menu
            switch (item.getItemId()) {
                // Respond to a click on the "Save" menu option
                case R.id.action_save:
                    get_details();
                    insert_data_in_database();
                    Toast.makeText(getApplicationContext(),"Data is saved",Toast.LENGTH_SHORT).show();
                    finish();
                    return true;
                // Respond to a click on the "Delete" menu option
                case R.id.action_delete:
                    // Do nothing for now
                    return true;
            }
            return super.onOptionsItemSelected(item);
        }

        private void get_details(){

            mNameEditText =  findViewById(R.id.edit_pet_name);
            Pet_Name =  mNameEditText.getText().toString();
            mBreedEditText =  findViewById(R.id.edit_pet_breed);
            Pet_Breed =  mBreedEditText.getText().toString();
            mWeightEditText =  findViewById(R.id.edit_pet_weight);
            Pet_Weight =  mWeightEditText.getText().toString();
            mHeightEditText =  findViewById(R.id.pet_height);
            Pet_Height = mHeightEditText.getText().toString();

        }
}
