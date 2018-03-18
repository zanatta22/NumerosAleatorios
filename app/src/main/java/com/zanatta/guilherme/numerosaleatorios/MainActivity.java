package com.zanatta.guilherme.numerosaleatorios;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SwitchCompat;
import android.support.v7.widget.Toolbar;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity {
    private EditText mEditTextMinNumber, mEditTextMaxNumber, mEditTextQuantidade;
    private TextView mTextViewDrawn, mTextViewDrawnNumbers;
    private Button mButtonDoRaffle, mButtonReset;
    private Random mRandom;
    private SwitchCompat mSwtich;
    private ArrayList<Integer> mList;
    private ArrayList<Integer> mTeste;
    private int number;
    static final String LIST = "ListNumber";
    static final String NUMBER = "lastNumber";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        AdView mAdView = (AdView) findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        Toolbar myToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(myToolbar);

        mEditTextMinNumber = (EditText) findViewById(R.id.editTextMinNumber);
        mEditTextMaxNumber = (EditText) findViewById(R.id.editTextMaxNumber);
        mEditTextQuantidade = (EditText)findViewById(R.id.edtQuantidade);
        mTextViewDrawn = (TextView) findViewById(R.id.textViewDrawn);
        mTextViewDrawnNumbers = (TextView) findViewById(R.id.textViewDrawnNumbers);
        mButtonDoRaffle = (Button) findViewById(R.id.buttonDoRaffle);
        mButtonReset = (Button) findViewById(R.id.buttonReset);
        mRandom = new Random();
        mSwtich = (SwitchCompat) findViewById(R.id.switchRepetition);




        if(savedInstanceState != null) {
            mList = savedInstanceState.getIntegerArrayList(LIST);
            if(mList.size()>0) {
                mTextViewDrawnNumbers.setText(Arrays.toString(mList.toArray()));
                number = savedInstanceState.getInt(NUMBER);
                mTextViewDrawn.setText("" + number);
            }

        }
        else {
            mList = new ArrayList<Integer>();
        }

        mButtonDoRaffle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                InputMethodManager inputManager = (InputMethodManager)
                        getSystemService(Context.INPUT_METHOD_SERVICE);

                inputManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),
                        InputMethodManager.HIDE_NOT_ALWAYS);
                buttonRaffleClicked();
            }
        });
        mButtonReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                InputMethodManager inputManager = (InputMethodManager)
                        getSystemService(Context.INPUT_METHOD_SERVICE);

                inputManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),
                        InputMethodManager.HIDE_NOT_ALWAYS);
                reset();
            }
        });
        mSwtich.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mEditTextMaxNumber.clearFocus();
                mEditTextMinNumber.clearFocus();
                mList.clear();
                mTextViewDrawn.setText("");
                mTextViewDrawnNumbers.setText("");
                InputMethodManager inputManager = (InputMethodManager)
                        getSystemService(Context.INPUT_METHOD_SERVICE);

                inputManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),
                        InputMethodManager.HIDE_NOT_ALWAYS);
            }
        });

        mEditTextQuantidade.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                if (event.getAction() == MotionEvent.ACTION_DOWN) {

                    mEditTextQuantidade.setText("");
                }
                return false;
            }
        });
    }

    protected void buttonRaffleClicked(){
        mEditTextMaxNumber.clearFocus();
        mEditTextMinNumber.clearFocus();
        mEditTextQuantidade.clearFocus();
        try {
            doRaffle(Integer.parseInt(mEditTextMinNumber.getText().toString()), Integer.parseInt(mEditTextMaxNumber.getText().toString()));
        }catch (NumberFormatException e){
            if(mEditTextMaxNumber.getText().toString().equals("") && mEditTextMinNumber.getText().toString().equals("")){
                new AlertDialog.Builder(MainActivity.this)
                        .setMessage(R.string.noLimits)
                        .setNeutralButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                            }
                        })
                        .show();
                return;
            }

            else if(mEditTextMaxNumber.getText().toString().equals("")){
                new AlertDialog.Builder(MainActivity.this)
                        .setMessage(R.string.noSuperiorLimit)
                        .setNeutralButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                            }
                        })
                        .show();
                return;
            }
            else if(mEditTextMinNumber.getText().toString().equals("")){
                new AlertDialog.Builder(MainActivity.this)
                        .setMessage(R.string.noInferiorLimit)
                        .setNeutralButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                            }
                        })
                        .show();
                return;
            }
            else if(Integer.parseInt(mEditTextMinNumber.getText().toString()) >= Integer.MAX_VALUE) {
                new AlertDialog.Builder(MainActivity.this)
                        .setMessage("O limite inferior inserido é maior do que o máximo permitido\nValor Máximo Permitido: " + Integer.MAX_VALUE)
                        .setNeutralButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                            }
                        })
                        .show();
            }
            else if(Integer.parseInt(mEditTextMaxNumber.getText().toString()) >= Integer.MAX_VALUE) {
                new AlertDialog.Builder(MainActivity.this)
                        .setMessage("O limite superior inserido é maior do que o máximo permitido\nValor Máximo Permitido: " + Integer.MAX_VALUE)
                        .setNeutralButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                            }
                        })
                        .show();
            }
        }
        catch (IllegalArgumentException e){
            new AlertDialog.Builder(MainActivity.this)
                    .setMessage("O limite superior informado é menor do que o limite inferior informado")
                    .setNeutralButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                        }
                    })
                    .show();
        }
    }

    protected void reset(){
        mEditTextMaxNumber.clearFocus();
        mEditTextMinNumber.clearFocus();
        mEditTextQuantidade.clearFocus();
        mEditTextQuantidade.setText("");
        mEditTextMinNumber.setText("");
        mEditTextMaxNumber.setText("");
        mList.clear();
        mTextViewDrawn.setText("");
        mTextViewDrawnNumbers.setText("");
    }

    protected void doRaffle(int min, int max){
        if(mSwtich.isChecked()) {
            for (int i = 0; i < Integer.parseInt(mEditTextQuantidade.getText().toString()); i++) {
                do {

                    number = mRandom.nextInt((max - min) + 1) + min;

                    if (mList.size() == (max - min) + 1) {
                        new AlertDialog.Builder(MainActivity.this)
                                .setMessage(R.string.allNumbers)
                                .setNeutralButton("OK", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {

                                    }
                                })
                                .show();
                        return;
                    }


                } while (mList.contains(number));


                mList.add(0, number);
                Collections.sort(mList);
                mTextViewDrawn.setText("" + number);
                mTextViewDrawnNumbers.setText(Arrays.toString(mList.toArray()));
            }
    }
        else
                for (int i = 0; i < Integer.parseInt(mEditTextQuantidade.getText().toString()); i++) {
                    number = mRandom.nextInt((max - min) + 1) + min;
                    mList.add(0, number);
                    Collections.sort(mList);
                    mTextViewDrawn.setText("" + number);
                    mTextViewDrawnNumbers.setText(Arrays.toString(mList.toArray()));
                }



    }

    @Override
    protected void onSaveInstanceState(Bundle savedInstanceState) {
        savedInstanceState.putIntegerArrayList(LIST,mList);
        if(mList.size()>0)
            savedInstanceState.putInt(NUMBER,mList.get(0));
        super.onSaveInstanceState(savedInstanceState);
    }



}