package com.example.sergey.counterpartycrud;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by sergey on 21.08.17.
 */

public class CounterpartyRecordOnLongClickListener implements View.OnLongClickListener {

    final CharSequence[] buttons = {"View", "Edit", "Delete"};

    private void viewRecord(final int counterpartyId, Context context) {
        Counterparty counterparty = new DatabaseHandler(context).getCounterparty(counterpartyId);

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View counterpartyView = inflater.inflate(R.layout.counterparty_view_form, null, false);

        TextView photoTextView = counterpartyView.findViewById(R.id.photoTextView);
        TextView nameTextView = counterpartyView.findViewById(R.id.nameTextView);
        TextView addressTextView = counterpartyView.findViewById(R.id.addressTextView);
        TextView phoneTextView = counterpartyView.findViewById(R.id.phoneTextView);
        TextView emailTextView = counterpartyView.findViewById(R.id.emailTextView);
        TextView websiteTextView = counterpartyView.findViewById(R.id.websiteTextView);
        TextView descriptionTextView = counterpartyView.findViewById(R.id.descriptionTextView);


        photoTextView.setText("Photo: " + counterparty.getPhoto());
        nameTextView.setText("Name: " + counterparty.getName());
        addressTextView.setText("Address: " + counterparty.getAddress());
        phoneTextView.setText("Phone: " + counterparty.getPhone());
        emailTextView.setText("Email: " + counterparty.getEmail());
        websiteTextView.setText("Website: " + counterparty.getWebsite());
        descriptionTextView.setText("Description: " + counterparty.getDescription());

        new AlertDialog.Builder(context)
                .setView(counterpartyView)
                .setTitle("View counterparty")
                .setPositiveButton("Close",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.cancel();
                            }
                        }).show();
    }

    private void editRecord(int counterpartyId, final Context context) {
        final DatabaseHandler databaseHandler = new DatabaseHandler(context);
        final Counterparty counterparty = databaseHandler.getCounterparty(counterpartyId);

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View counterpartyEditView = inflater.inflate(R.layout.counterparty_edit_form, null, false);

        final EditText photoEditText = counterpartyEditView.findViewById(R.id.photoTextEdit);
        final EditText nameEditText = counterpartyEditView.findViewById(R.id.nameTextEdit);
        final EditText addressEditText = counterpartyEditView.findViewById(R.id.addressTextEdit);
        final EditText phoneEditText = counterpartyEditView.findViewById(R.id.phoneTextEdit);
        final EditText emailEditText = counterpartyEditView.findViewById(R.id.emailTextEdit);
        final EditText websiteEditText = counterpartyEditView.findViewById(R.id.websiteTextEdit);
        final EditText descriptionEditText = counterpartyEditView.findViewById(R.id.descriptionTextEdit);

        photoEditText.setText(counterparty.getPhoto());
        nameEditText.setText(counterparty.getName());
        addressEditText.setText(counterparty.getAddress());
        phoneEditText.setText(counterparty.getPhone());
        emailEditText.setText(counterparty.getEmail());
        websiteEditText.setText(counterparty.getWebsite());
        descriptionEditText.setText(counterparty.getDescription());

        new AlertDialog.Builder(context)
                .setView(counterpartyEditView)
                .setTitle("Edit counterparty")
                .setPositiveButton("Save",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                counterparty.setPhoto(photoEditText.getText().toString());
                                counterparty.setName(nameEditText.getText().toString());
                                counterparty.setAddress(addressEditText.getText().toString());
                                counterparty.setPhone(phoneEditText.getText().toString());
                                counterparty.setEmail(emailEditText.getText().toString());
                                counterparty.setWebsite(websiteEditText.getText().toString());
                                counterparty.setDescription(descriptionEditText.getText().toString());

                                int updatedRow = databaseHandler.updateCounterparty(counterparty);

                                if (updatedRow > 0)
                                    Toast.makeText(context, "Counterparty updated", Toast.LENGTH_SHORT).show();
                                else
                                    Toast.makeText(context, "Unable to update counterparty", Toast.LENGTH_SHORT).show();

                                dialogInterface.cancel();

                                ((MainActivity) context).showCounterpartyList();
                            }
                        }).show();

    }

    private void deleteRecord(int counterpartyId, Context context) {
        int deletedRow = new DatabaseHandler(context).deleteCounteroarty(counterpartyId);
        if (deletedRow  > 0 )
            Toast.makeText(context, "Counterparty deleted", Toast.LENGTH_SHORT).show();
        else
            Toast.makeText(context, "Unable to delete counterparty", Toast.LENGTH_SHORT).show();

        ((MainActivity) context).showCounterpartyList();
    }

    @Override
    public boolean onLongClick(View view) {
        Log.d("Info", "On long click listenter");
        final Context context = view.getContext();
        final int id = Integer.parseInt(view.getTag().toString());
        new AlertDialog.Builder(context).setTitle("Counterparty")
                .setItems(buttons, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if (i == 0) {
                            viewRecord(id, context);
                        } else if (i == 1) {
                            editRecord(id, context);
                        } else if (i == 2)  {
                            deleteRecord(id, context);
                        }
                        dialogInterface.dismiss();
                    }
                }).show();
        return false;
    }
}
