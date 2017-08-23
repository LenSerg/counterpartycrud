package com.example.sergey.counterpartycrud.activities;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sergey.counterpartycrud.entities.Counterparty;
import com.example.sergey.counterpartycrud.database.DatabaseHandler;
import com.example.sergey.counterpartycrud.R;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, View.OnLongClickListener {

    public void showCounterpartyList() {
        LinearLayout counterpartyListLayout = (LinearLayout) findViewById(R.id.counterpartyLinearLayout);
        counterpartyListLayout.removeAllViews();

        ArrayList<Counterparty> counterparties = new DatabaseHandler(this).getCounterpartyList();
        TextView counterpartyTextView;

        if (counterparties.size() > 0) {
            for (Counterparty counterparty : counterparties) {
                counterpartyTextView = new TextView(this);
                counterpartyTextView.setPadding(30, 30, 30, 0);
                counterpartyTextView.setText(counterparty.getName());
                counterpartyTextView.setTag(counterparty.getId());
                counterpartyTextView.setOnLongClickListener(this);
                counterpartyListLayout.addView(counterpartyTextView);
            }
        } else {
            counterpartyTextView = new TextView(this);
            counterpartyTextView.setPadding(30, 30, 30, 0);
            counterpartyTextView.setText("Counterparties not found");
            counterpartyListLayout.addView(counterpartyTextView);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button createCounterpartyButton = (Button) findViewById(R.id.createCounterpartyButton);
        createCounterpartyButton.setOnClickListener(this);

        showCounterpartyList();
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.createCounterpartyButton) {
            Intent intent = new Intent(this, CounterpartyEditActivity.class);
            intent.putExtra("type", "create");
            startActivityForResult(intent, 1);
        }
    }

    @Override
    public boolean onLongClick(final View view) {
        final int counterpartyId = Integer.parseInt(view.getTag().toString());
        Counterparty counterparty = new DatabaseHandler(this).getCounterparty(counterpartyId);

        final Intent intent = new Intent(this, CounterpartyEditActivity.class);
        intent.putExtra("counterparty", counterparty);

        new AlertDialog.Builder(this).setTitle("Counterparty")
                .setItems(new String[] {"View", "Edit", "Delete"}, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if (i == 0) {
                            intent.putExtra("type", "view");
                            startActivityForResult(intent, 1);
                        } else if (i == 1) {
                            intent.putExtra("type", "edit");
                            startActivityForResult(intent, 1);

                        } else if (i == 2)  {
                            Context context = view.getContext();
                            if ((new DatabaseHandler(context).deleteCounterparty(counterpartyId)) > 0) {
                                Toast.makeText(context, "Counterparty was deleted", Toast.LENGTH_SHORT);
                            } else {
                                Toast.makeText(context, "Unable to delete the counterparty", Toast.LENGTH_SHORT);
                            }
                            ((MainActivity) context).showCounterpartyList();
                        }
                        dialogInterface.dismiss();
                    }
                }).show();


        return false;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (data == null)
            return;
        if (resultCode == RESULT_OK) {
            Toast.makeText(this, "Counterparty was saved", Toast.LENGTH_SHORT).show();
            showCounterpartyList();
        } else {
            Toast.makeText(this, "Unable to save counterparty", Toast.LENGTH_SHORT).show();
        }

    }
}
