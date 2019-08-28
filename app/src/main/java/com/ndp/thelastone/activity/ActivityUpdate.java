package com.ndp.thelastone.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.ndp.thelastone.DbHelper;
import com.ndp.thelastone.R;

public class ActivityUpdate extends AppCompatActivity implements View.OnClickListener {

    private DbHelper dbHelper;
    private int _id;
    private EditText etName;
    private EditText etGender;
    private EditText etDes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);

        dbHelper = new DbHelper(this);

        initView();

        Intent intent = getIntent();
        _id = intent.getIntExtra(DbHelper.ID, 0);
        String name = intent.getStringExtra(DbHelper.NAME);
        String gender = intent.getStringExtra(DbHelper.GENDER);
        String des = intent.getStringExtra(DbHelper.DES);

        etName.setText(name);
        etGender.setText(gender);
        etDes.setText(des);
    }

    private void initView() {
        etName = findViewById(R.id.etName);
        etGender = findViewById(R.id.etGender);
        etDes = findViewById(R.id.etDes);

        Button btnUpdate = findViewById(R.id.btnUpdate);
        btnUpdate.setOnClickListener(this);

        Button btnDelete = findViewById(R.id.btnDelete);
        btnDelete.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.btnUpdate:
                onUpdate();
                break;
            case R.id.btnDelete:
                onDelete();
                break;
                default:break;
        }
    }

    private void onUpdate() {
        String isUpdate = dbHelper.updateUser(_id, etName.getText().toString(), etGender.getText().toString(), etDes.getText().toString());
        Toast.makeText(this, isUpdate, Toast.LENGTH_LONG).show();
        finish();
    }

    private void onDelete() {
        String isDelete = dbHelper.deleteUser(_id);
        Toast.makeText(this, isDelete, Toast.LENGTH_LONG).show();
        finish();
    }
}
