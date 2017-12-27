package com.stiei.administrator.train_ticket;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private Spinner mStartLocationSpinner;
    private Spinner mEndLocationSpinner;
    private Button mSubmitButton;
    private TextView mResultTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        String[] locations = getResources().getStringArray(R.array.locations);

        mStartLocationSpinner = findViewById(R.id.activity_main_start_location_spinner);
        mEndLocationSpinner = findViewById(R.id.activity_main_end_location_spinner);
        mSubmitButton = findViewById(R.id.activity_main_submit_button);
        mResultTextView = findViewById(R.id.activity_main_result_text_view);

        mSubmitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new TrainTicketInfoTask().execute();
            }
        });


    }

    private class TrainTicketInfoTask extends AsyncTask<Void, Void, String> {

        private String url = SystemFinal.HOST + "/api/trainTicket/info";

        @Override
        protected String doInBackground(Void... params) {

            String startLocation = mStartLocationSpinner.getSelectedItem().toString();
            String endLocation = mEndLocationSpinner.getSelectedItem().toString();

            Map<String, Object> json = new HashMap<>();
            json.put("startLocation", startLocation);
            json.put("endLocation", endLocation);

            try {
                return HttpUtil.postJson(url, new ObjectMapper().writeValueAsString(json));
            } catch (IOException e) {
                e.printStackTrace();
            }
            return "";
        }

        @Override
        protected void onPostExecute(String json) {
            try {
                Map map = new ObjectMapper().readValue(json, Map.class);
                Double price = (Double) ((Map) map.get("data")).get("price");

                mResultTextView.setText("价格: " + price);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


}
