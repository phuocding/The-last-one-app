package com.ndp.thelastone.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.ndp.thelastone.DbHelper;
import com.ndp.thelastone.R;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText etName;
    private EditText etDes;
    private Spinner sGender;
    private CheckBox cb;
    private Button btnRegister;
    private DbHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();

        dbHelper = new DbHelper(this);
        dbHelper.getReadableDatabase();
    }

    private void initView() {
        etName = findViewById(R.id.etName);
        etDes = findViewById(R.id.etDes);
        cb = findViewById(R.id.cb);
        btnRegister = findViewById(R.id.btnRegister);
        btnRegister.setOnClickListener(this);


        // List gender dropdown
        List<String> listGender = new ArrayList<String>();
        listGender.add("Male");
        listGender.add("Female");
        listGender.add("Others");

        sGender = findViewById(R.id.sGender);
//        sGender.setOnItemSelectedListener((AdapterView.OnItemSelectedListener) this);
        //adapter for spinner
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, listGender);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sGender.setAdapter(arrayAdapter);

        sGender.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
//                String item = adapterView.getItemAtPosition(i).toString();
//                Toast.makeText(adapterView.getContext(), "Selected: " + item, Toast.LENGTH_LONG).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    @Override
    public void onClick(View view) {
        if (view == btnRegister) {
            onRegister();
        }
    }

    public void onRegister() {
        if (etName.getText().toString().isEmpty()) {
            Toast.makeText(this,"Please choose an username",Toast.LENGTH_LONG).show();
            return;
        }

        if (!cb.isChecked()) {
            Toast.makeText(this, "You must agree before register.", Toast.LENGTH_LONG).show();
            return;
        }

        String isAdd = dbHelper.addUser(etName.getText().toString(), sGender.getSelectedItem().toString(), etDes.getText().toString());
        Toast.makeText(this, isAdd, Toast.LENGTH_LONG).show();

        Intent intent = new Intent(MainActivity.this, ListUserActivity.class);
        startActivity(intent);
    }
}
