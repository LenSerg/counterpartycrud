package com.example.sergey.counterpartycrud;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Created by sergey on 20.08.17.
 */

public class OnClickListenerCreateCounterparty implements View.OnClickListener {
    @Override
    public void onClick(View view) {
        final Context context = view.getRootView().getContext();

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View formView = inflater.inflate(R.layout.counterparty_edit_form, null, false);

        final EditText photoEditText = formView.findViewById(R.id.photoTextEdit);
        final EditText nameEditText = formView.findViewById(R.id.nameTextEdit);
        final EditText addressEditText = formView.findViewById(R.id.addressTextEdit);
        final EditText phoneEditText = formView.findViewById(R.id.phoneTextEdit);
        final EditText emailEditText = formView.findViewById(R.id.emailTextEdit);
        final EditText websiteEditText = formView.findViewById(R.id.websiteTextEdit);
        final EditText descriptionEditText = formView.findViewById(R.id.descriptionTextEdit);

        new AlertDialog.Builder(context)
                .setView(formView)
                .setTitle("Create counterparty")
                .setPositiveButton("Ok",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                Counterparty counterParty = new Counterparty(photoEditText.getText().toString(),
                                        nameEditText.getText().toString(),
                                        addressEditText.getText().toString(),
                                        phoneEditText.getText().toString(),
                                        emailEditText.getText().toString(),
                                        websiteEditText.getText().toString(),
                                        descriptionEditText.getText().toString());

                                DatabaseHandler databaseHandler = new DatabaseHandler(context);
                                long insertId = databaseHandler.addCounterparty(counterParty);

                                if (insertId > 0)
                                    Toast.makeText(context, "Counterparty added", Toast.LENGTH_SHORT).show();
                                else
                                    Toast.makeText(context, "Unable to add counterparty", Toast.LENGTH_SHORT).show();
                                dialogInterface.cancel();
                            }
                        }).show();
    }
}
