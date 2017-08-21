package com.example.sergey.counterpartycrud;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    public void showCounterpartyList() {
        LinearLayout counterpartyListLayout = (LinearLayout) findViewById(R.id.counterpartyLinearLayout);
        counterpartyListLayout.removeAllViews();

        ArrayList<Counterparty> counterparties = new DatabaseHandler(this).getCounterpartyList();

        if (counterparties.size() > 0) {
            for (Counterparty counterparty : counterparties) {
                TextView counterpartyTextView = new TextView(this);
                counterpartyTextView.setPadding(30, 30, 30, 0);
                counterpartyTextView.setText(counterparty.getName());
                counterpartyTextView.setTag(counterparty.getId());
                counterpartyTextView.setOnLongClickListener(new CounterpartyRecordOnLongClickListener());
                counterpartyListLayout.addView(counterpartyTextView);
            }
        } else {
            TextView counterpartyTextView = new TextView(this);
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
        createCounterpartyButton.setOnClickListener(new CreateCounterpartyOnClickListener());

        showCounterpartyList();
    }

}
