package com.example.sergey.counterpartycrud.activities;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.sergey.counterpartycrud.entities.Counterparty;
import com.example.sergey.counterpartycrud.database.DatabaseHandler;
import com.example.sergey.counterpartycrud.R;

import java.util.regex.Pattern;

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
    private ImageView photoImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_counterparty_edit);

        counterparty = new Counterparty();
        photoImageView = (ImageView) findViewById(R.id.photoImageView);
        photoEdiText = (EditText) findViewById(R.id.photoTextEdit);
        nameEditText = (EditText) findViewById(R.id.nameTextEdit);
        addressEditText = (EditText) findViewById(R.id.addressTextEdit);
        phoneEditText = (EditText) findViewById(R.id.phoneTextEdit);
        emailEditText = (EditText) findViewById(R.id.emailTextEdit);
        websiteEditText = (EditText) findViewById(R.id.websiteTextEdit);
        descriptionEditText = (EditText) findViewById(R.id.descriptionTextEdit);

        Button selectPhotoButton = (Button) findViewById(R.id.selectPhotoButton);
        selectPhotoButton.setOnClickListener(this);
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
            showPhoto();
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
            photoEdiText.setVisibility(View.GONE);
            saveButton.setVisibility(View.GONE);
        }
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.selectPhotoButton) {
            Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(intent, 1);
            return;
        }
        else if (view.getId() == R.id.saveButton) {
            if (!textIsValid())
                return;
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1 && resultCode == RESULT_OK && data != null) {
            Uri selectedImage = data.getData();
            String[] filePathColumn = {MediaStore.Images.Media.DATA};

            Cursor cursor = getContentResolver().query(selectedImage, filePathColumn, null, null, null);
            cursor.moveToFirst();

            photoEdiText.setText(cursor.getString(cursor.getColumnIndex(filePathColumn[0])));

            cursor.close();

            showPhoto();

        }
    }

    private void showPhoto() {
        photoImageView.setImageBitmap(BitmapFactory.decodeFile(photoEdiText.getText().toString()));
    }

    private boolean textIsValid() {
        boolean result = false;
        if (nameEditText.getText().toString().trim().equals("")) {
            nameEditText.setError("Name must not be empty");
        } else if (!Patterns.PHONE.matcher(phoneEditText.getText().toString()).matches()) {
            phoneEditText.setError("Phone is not valid");
        } else if (!Patterns.EMAIL_ADDRESS.matcher(emailEditText.getText().toString()).matches()) {
            emailEditText.setError("Email is not valid");
        } else if (!websiteEditText.getText().toString().trim().equals("") &&
                !Patterns.WEB_URL.matcher(websiteEditText.getText().toString()).matches()) {
            websiteEditText.setError("Website is not valid");
        } else {
            result = true;
        }
        return result;
    }

    private void buildCounterparty() {
        counterparty.setPhoto(photoEdiText.getText().toString().trim());
        counterparty.setName(nameEditText.getText().toString().trim());
        counterparty.setAddress(addressEditText.getText().toString().trim());
        counterparty.setPhone(phoneEditText.getText().toString().trim());
        counterparty.setEmail(emailEditText.getText().toString().trim());
        counterparty.setWebsite(websiteEditText.getText().toString().trim());
        counterparty.setDescription(descriptionEditText.getText().toString().trim());
    }
}
