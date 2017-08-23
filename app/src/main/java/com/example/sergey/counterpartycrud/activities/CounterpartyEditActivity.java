package com.example.sergey.counterpartycrud.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.sergey.counterpartycrud.entities.Counterparty;
import com.example.sergey.counterpartycrud.database.DatabaseHandler;
import com.example.sergey.counterpartycrud.R;

public class CounterpartyEditActivity extends AppCompatActivity implements View.OnClickListener {


    private String operationType;
    private Counterparty counterparty;
    private EditText photoEdiText;
    private EditText nameEditText;
    private EditText addressEditText;
    private EditText phoneEditText;
    private EditText emailEditText;
    private EditText websiteEditText;
    private EditText descriptionEditText;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_counterparty_edit);

        counterparty = new Counterparty();
        photoEdiText = (EditText) findViewById(R.id.photoTextEdit);
        nameEditText = (EditText) findViewById(R.id.nameTextEdit);
        addressEditText = (EditText) findViewById(R.id.addressTextEdit);
        phoneEditText = (EditText) findViewById(R.id.phoneTextEdit);
        emailEditText = (EditText) findViewById(R.id.emailTextEdit);
        websiteEditText = (EditText) findViewById(R.id.websiteTextEdit);
        descriptionEditText = (EditText) findViewById(R.id.descriptionTextEdit);

        Button selectPhotoButton = (Button) findViewById(R.id.selectPhotoButton);
        Button saveButton = (Button) findViewById(R.id.saveButton);
        saveButton.setOnClickListener(this);
        Button cancelButton = (Button) findViewById(R.id.cancelButton);
        cancelButton.setOnClickListener(this);

        Intent intent = getIntent();
        operationType = intent.getStringExtra("type");
        if (!operationType.equals("create")) {
            counterparty = (Counterparty) intent.getSerializableExtra("counterparty");

            photoEdiText.setText(counterparty.getPhoto());
            nameEditText.setText(counterparty.getName());
            addressEditText.setText(counterparty.getAddress());
            phoneEditText.setText(counterparty.getPhone());
            emailEditText.setText(counterparty.getEmail());
            websiteEditText.setText(counterparty.getWebsite());
            descriptionEditText.setText(counterparty.getDescription());
        }

        if (operationType.equals("view")) {
            photoEdiText.setKeyListener(null);
            nameEditText.setKeyListener(null);
            addressEditText.setKeyListener(null);
            phoneEditText.setKeyListener(null);
            emailEditText.setKeyListener(null);
            websiteEditText.setKeyListener(null);
            descriptionEditText.setKeyListener(null);
            selectPhotoButton.setVisibility(View.GONE);
            saveButton.setVisibility(View.GONE);
        }
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.saveButton) {
            buildCounterparty();
            Intent intent = new Intent();
            DatabaseHandler handler = new DatabaseHandler(this);
            if (operationType.equals("create")) {
                if (handler.addCounterparty(counterparty) > 0)
                    setResult(RESULT_OK, intent);
                else
                    setResult(RESULT_CANCELED, intent);
            } else if (operationType.equals("edit")) {
                if (handler.updateCounterparty(counterparty) > 0)
                    setResult(RESULT_OK, intent);
                else
                    setResult(RESULT_CANCELED, intent);

            }
        }
        finish();
    }

    private void buildCounterparty() {
        counterparty.setPhoto(photoEdiText.getText().toString());
        counterparty.setName(nameEditText.getText().toString());
        counterparty.setAddress(addressEditText.getText().toString());
        counterparty.setPhone(phoneEditText.getText().toString());
        counterparty.setEmail(emailEditText.getText().toString());
        counterparty.setWebsite(websiteEditText.getText().toString());
        counterparty.setDescription(descriptionEditText.getText().toString());
    }
}
