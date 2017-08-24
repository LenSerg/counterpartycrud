package com.example.sergey.counterpartycrud.activities;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.example.sergey.counterpartycrud.adapters.CounterpartiesListAdapter;
import com.example.sergey.counterpartycrud.utils.Operation;
import com.example.sergey.counterpartycrud.entities.Counterparty;
import com.example.sergey.counterpartycrud.database.DatabaseHandler;
import com.example.sergey.counterpartycrud.R;

public class MainActivity extends AppCompatActivity implements View.OnClickListener,
        AdapterView.OnItemClickListener {

    private DatabaseHandler handler;
    private CounterpartiesListAdapter counterpartiesAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        handler = new DatabaseHandler(this);

        Button createCounterpartyButton = (Button) findViewById(R.id.createCounterpartyButton);
        createCounterpartyButton.setOnClickListener(this);

        ListView counterpartiesListView = (ListView) findViewById(R.id.counterpartiesListView);
        counterpartiesAdapter = new CounterpartiesListAdapter(this, handler.getCounterpartyList());
        counterpartiesListView.setAdapter(counterpartiesAdapter);
        counterpartiesListView.setOnItemClickListener(this);
    }

    private void refreshCounterpartiesListView() {
        counterpartiesAdapter.setCounterparties(handler.getCounterpartyList());
        counterpartiesAdapter.notifyDataSetChanged();
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.createCounterpartyButton) {
            Intent intent = new Intent(this, CounterpartyEditActivity.class);
            intent.putExtra("type", Operation.CREATE);
            startActivityForResult(intent, 1);
        }
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, final View view, int position,
                            final long counterpartyId) {
        Counterparty counterparty = handler.getCounterparty(counterpartyId);

        final Intent intent = new Intent(this, CounterpartyEditActivity.class);
        intent.putExtra("counterparty", counterparty);

        new AlertDialog.Builder(this).setTitle(R.string.counterpartyMenuTitle)
                .setItems(R.array.counterpartyMenuButtons, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if (i == 0) {
                            intent.putExtra("type", Operation.VIEW);
                            startActivityForResult(intent, 1);
                        } else if (i == 1) {
                            intent.putExtra("type", Operation.EDIT);
                            startActivityForResult(intent, 1);

                        } else if (i == 2)  {
                            Context context = view.getContext();
                            if (handler.deleteCounterparty(counterpartyId) > 0) {
                                Toast.makeText(context, R.string.successfulDeletingCounterpartyMessage,
                                        Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(context, R.string.failedDeletingCounterPartyMessage,
                                        Toast.LENGTH_SHORT).show();
                            }
                            refreshCounterpartiesListView();
                        }
                    }
                }).show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (data == null)
            return;
        if (resultCode == RESULT_OK) {
            Toast.makeText(this, R.string.successfulSavingCounterpartyMessage, Toast.LENGTH_SHORT).show();
            refreshCounterpartiesListView();
        } else {
            Toast.makeText(this, R.string.failedSavingCounterpartyMessage, Toast.LENGTH_SHORT).show();
        }
    }
}