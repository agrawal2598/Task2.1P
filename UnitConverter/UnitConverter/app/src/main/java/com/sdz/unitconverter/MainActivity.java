package com.sdz.unitconverter;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextWatcher;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.sdz.unitconverter.models.LengthProtocolFunc;
import com.sdz.unitconverter.models.protocolFunc;
import com.sdz.unitconverter.models.TemperatureProtocolFunc;
import com.sdz.unitconverter.models.Units;
import com.sdz.unitconverter.models.WeightProtocolFunc;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    Spinner unitSpinner;
    EditText editTxt;
    ViewGroup resultLayout;

    ImageView kgImage;
    ImageView lenImage;
    ImageView tempImage;

    protocolFunc protocolFunc;

    LengthProtocolFunc lengthStrategy = new LengthProtocolFunc();
    WeightProtocolFunc weightStrategy = new WeightProtocolFunc();
    TemperatureProtocolFunc temperatureStrategy = new TemperatureProtocolFunc();


    List<String> units = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        unitSpinner = findViewById(R.id.unit_spinner);
        editTxt = findViewById(R.id.editText);
        resultLayout = findViewById(R.id.resultLayout);

        kgImage = findViewById(R.id.kg_image);
        lenImage = findViewById(R.id.meter_image);
        tempImage = findViewById(R.id.temperature_image);

        initializeUnits();
        initializeStrategy();
        initializeUnitSpinner();
        initializeEditText();
        initializeIcons();
    }

    private void initializeUnits() {
        units.add("Meter");
        units.add("Kilogram");
        units.add("Celsius");
    }

    private void initializeIcons() {
        kgImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeUnitSelection("Kilogram");
            }
        });

        lenImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeUnitSelection("Meter");
            }
        });

        tempImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeUnitSelection("Celsius");
            }
        });
    }

    private void changeUnitSelection(String newUnit) {
        unitSpinner.setSelection(units.indexOf(newUnit));
    }

    private void initializeStrategy() {
        protocolFunc = lengthStrategy;
    }

    private void updateStrategy(protocolFunc newProtocolFunc) {
        protocolFunc = newProtocolFunc;
    }

    private void initializeEditText() {
        editTxt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.length() == 0)
                    return;

                if (protocolFunc == null)
                    return;

                try {
                    double input = Double.parseDouble(charSequence.toString());
                    doConversion(input);
                } catch (Exception ex) {
                    return;
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    private void doConversion(double input) {
        switch (protocolFunc.getType()) {
            case "length":
                double cm = protocolFunc.Convert(Units.METER, Units.CENTIMETER, input);
                double inch = protocolFunc.Convert(Units.METER, Units.INCH, input);
                double ft = protocolFunc.Convert(Units.METER, Units.FEET, input);

                updateLengthConversionResults(cm, inch, ft);
                break;
            case "weight":

                double gram = protocolFunc.Convert(Units.KG, Units.GRAM, input);
                double ounce = protocolFunc.Convert(Units.KG, Units.OUNCE, input);
                double pound = protocolFunc.Convert(Units.KG, Units.POUND, input);

                updateWeightConversionResults(gram, ounce, pound);
                break;
            case "temperature":

                double farhenheit = protocolFunc.Convert(Units.CELSIUS, Units.FARHENHEIT, input);
                double kelvin = protocolFunc.Convert(Units.CELSIUS, Units.KELVIN, input);

                updateTemperatureConversionResults(farhenheit, kelvin);

                break;
        }
    }

    private void updateTemperatureConversionResults(double farhenheit, double kelvin) {

        //check if the view is already inflated,
        //if no, then inflate
        //populate results

        View resultView;
        String farhenheitResult;
        String kelvinResult;

        if(resultLayout.getChildAt(0) == null){
            resultView = LayoutInflater.from(this).inflate(R.layout.temperature_conversion_result, null);
            resultLayout.addView(resultView);
        }else{
            if ( resultLayout.getChildAt(0).getId() != R.id.celsius_conversion_results) {
                resultLayout.removeAllViews();
                resultView = LayoutInflater.from(this).inflate(R.layout.temperature_conversion_result, null);
                resultLayout.addView(resultView);
            } else
                resultView = resultLayout.getChildAt(0);

        }

        farhenheitResult = farhenheit + " F";
        kelvinResult = kelvin + " K";

        Spannable farhenheitSpannable = new SpannableString(farhenheitResult);
        Spannable kelvinSpannable = new SpannableString(kelvinResult);

        farhenheitSpannable.setSpan(new ForegroundColorSpan(Color.RED), 0, (farhenheitResult.split(" ")[0].length()), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        kelvinSpannable.setSpan(new ForegroundColorSpan(Color.RED), 0, (kelvinResult.split(" ")[0].length()), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        ((TextView) resultView.findViewById(R.id.result_f)).setText(farhenheitSpannable, TextView.BufferType.SPANNABLE);
        ((TextView) resultView.findViewById(R.id.result_k)).setText(kelvinSpannable, TextView.BufferType.SPANNABLE);

    }

    private void updateWeightConversionResults(double gram, double ounce, double pound) {

        View resultView;
        String gramResult;
        String ounceResult;
        String poundResult;

        if(resultLayout.getChildAt(0) == null){
            resultView = LayoutInflater.from(this).inflate(R.layout.kg_conversion_result, null);
            resultLayout.addView(resultView);
        }else{
            if ( resultLayout.getChildAt(0).getId() != R.id.kg_conversion_results) {
                resultLayout.removeAllViews();
                resultView = LayoutInflater.from(this).inflate(R.layout.kg_conversion_result, null);
                resultLayout.addView(resultView);
            } else
                resultView = resultLayout.getChildAt(0);

        }

        gramResult = gram + " Grams";
        ounceResult = ounce + " Ounces";
        poundResult = pound + " Pounds";

        Spannable gSpannable = new SpannableString(gramResult);
        Spannable oSpannable = new SpannableString(ounceResult);
        Spannable pSpannable = new SpannableString(poundResult);

        gSpannable.setSpan(new ForegroundColorSpan(Color.RED), 0, (gramResult.split(" ")[0].length()), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        oSpannable.setSpan(new ForegroundColorSpan(Color.RED), 0, (ounceResult.split(" ")[0].length()), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        pSpannable.setSpan(new ForegroundColorSpan(Color.RED), 0, (poundResult.split(" ")[0].length()), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        ((TextView) resultView.findViewById(R.id.result_gram)).setText(gSpannable, TextView.BufferType.SPANNABLE);
        ((TextView) resultView.findViewById(R.id.result_ounce)).setText(oSpannable, TextView.BufferType.SPANNABLE);
        ((TextView) resultView.findViewById(R.id.result_pound)).setText(pSpannable, TextView.BufferType.SPANNABLE);
    }

    private void updateLengthConversionResults(double cm, double inch, double ft) {
        View resultView;
        String cmResult;
        String inchResult;
        String ftResult;

        if(resultLayout.getChildAt(0) == null){
            resultView = LayoutInflater.from(this).inflate(R.layout.kg_conversion_result, null);
            resultLayout.addView(resultView);
        }else{
            if ( resultLayout.getChildAt(0).getId() != R.id.kg_conversion_results) {
                resultLayout.removeAllViews();
                resultView = LayoutInflater.from(this).inflate(R.layout.kg_conversion_result, null);
                resultLayout.addView(resultView);
            } else
                resultView = resultLayout.getChildAt(0);

        }

        cmResult = cm + " cm";
        inchResult = inch + " inches";
        ftResult = ft + " ft";

        Spannable cmSpannable = new SpannableString(cmResult);
        Spannable inchSpannable = new SpannableString(inchResult);
        Spannable ftSpannable = new SpannableString(ftResult);

        cmSpannable.setSpan(new ForegroundColorSpan(Color.RED), 0, (cmResult.split(" ")[0].length()), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        inchSpannable.setSpan(new ForegroundColorSpan(Color.RED), 0, (inchResult.split(" ")[0].length()), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        ftSpannable.setSpan(new ForegroundColorSpan(Color.RED), 0, (ftResult.split(" ")[0].length()), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        ((TextView) resultView.findViewById(R.id.result_gram)).setText(cmSpannable, TextView.BufferType.SPANNABLE);
        ((TextView) resultView.findViewById(R.id.result_ounce)).setText(inchSpannable, TextView.BufferType.SPANNABLE);
        ((TextView) resultView.findViewById(R.id.result_pound)).setText(ftSpannable, TextView.BufferType.SPANNABLE);
    }

    private void initializeUnitSpinner() {


        ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(this,
                R.layout.unit_spinner_item, units);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        unitSpinner.setAdapter(dataAdapter);
        unitSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String selected = ((TextView) view).getText().toString();
                if (selected.isEmpty())
                    return;

                switch (selected.toLowerCase()) {
                    case "meter":
                        resultLayout.removeAllViews();
                        updateStrategy(lengthStrategy);
                        editTxt.setText("1");
                        break;

                    case "kilogram":
                        resultLayout.removeAllViews();
                        updateStrategy(weightStrategy);
                        editTxt.setText("1");

                        break;

                    case "celsius":
                        resultLayout.removeAllViews();
                        updateStrategy(temperatureStrategy);
                        editTxt.setText("1");
                        break;
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                //do nothing
            }
        });
    }
}
