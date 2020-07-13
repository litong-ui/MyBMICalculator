package sg.com.republicworkz.safety.mybmicalculator;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {
    EditText etWeight,etHeight;
    Button btnCalculate,btnReset;
    TextView tvDate,tvBMI,tvOutcome;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etWeight = findViewById(R.id.editTextWeight);
        etHeight = findViewById(R.id.editTextHeight);
        btnCalculate = findViewById(R.id.buttonCalculate);
        btnReset = findViewById(R.id.buttonReset);
        tvDate = findViewById(R.id.textViewDate);
        tvBMI = findViewById(R.id.textViewBMI);
        tvOutcome = findViewById(R.id.textViewOutcome);

        btnCalculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                double weight = Double.parseDouble( etWeight.getText().toString().trim() );
                double height = Double.parseDouble( etHeight.getText().toString().trim() );
                double bmi = weight/(height*height);

                Calendar now = Calendar.getInstance();  //Create a Calendar object with current date and time
                String datetime = now.get(Calendar.DAY_OF_MONTH) + "/" +
                        (now.get(Calendar.MONTH)+1) + "/" +
                        now.get(Calendar.YEAR) + " " +
                        now.get(Calendar.HOUR_OF_DAY) + ":" +
                        now.get(Calendar.MINUTE);

                tvDate.setText("Last Calculated Date: "+datetime);
                tvBMI.setText("Last Calculated BMI: "+String.format("%.2f", bmi));

                if(bmi>0 && bmi<=18.5){
                    tvOutcome.setText("You are underweight");
                }else if(bmi>=18.5 && bmi<=24.9){
                    tvOutcome.setText("Your BMI is normal");
                }else if(bmi>=25 && bmi<=29.9){
                    tvOutcome.setText("You are overweight");
                }else{
                    tvOutcome.setText("You are obese");
                }
            }
        });

        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                etWeight.setText("");
                etHeight.setText("");
                tvDate.setText("Last Calculated Date: ");
                tvBMI.setText("Last Calculated BMI: ");
                tvOutcome.setText(" ");
            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        //Step 1a: Get the user input from the ditText and store it in a variable
        float bmi = Float.parseFloat(tvBMI.getText().toString());
        float dateTimeStr = Float.parseFloat(tvDate.getText().toString());

        //Step 1b: Obtain an instance of the SharePreference
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);

        //Step 1c: Obtain an instance of the SharePreference Editor for update later
        SharedPreferences.Editor prefEdit = prefs.edit();

        //Step 1d: Add the key-value pair
        prefEdit.putFloat("dateTime",dateTimeStr);
        prefEdit.putFloat("lastBMIValue",bmi);

        //Step 1e: Call commit() to save the changes into SharedPreferences
        prefEdit.commit();

    }

    @Override
    protected void onResume() {
        super.onResume();

        //Step 2a: Obtain an instance of the SharePreference
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);

        //Step 2b: Retrieve the saved data from the SharePreferences object
        String dateTimeStr = prefs.getString("datetime","");
        float lastBMIValue = prefs.getFloat("lastBMIValue",0);

        //Step 2c: Update the UI element with the value
        tvDate.setText("Last Calculated Date: ");
        tvBMI.setText("Last Calculated BMI: "+lastBMIValue);
    }

}
