package com.ad.sample.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.ad.sample.R;

import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by binderbyte on 11/01/17.
 */

public class VerificationCodeActivity extends AppCompatActivity {

    @BindView(R.id.set1)
    EditText set1;
    @BindView(R.id.set2)
    EditText set2;
    @BindView(R.id.set3)
    EditText set3;
    @BindView(R.id.set4)
    EditText set4;
    @BindView(R.id.set5)
    EditText set5;
    @BindView(R.id.set6)
    EditText set6;
    @BindView(R.id.get1)
    TextView get1;
    @BindView(R.id.get2)
    TextView get2;
    @BindView(R.id.get3)
    TextView get3;
    @BindView(R.id.get4)
    TextView get4;
    @BindView(R.id.get5)
    TextView get5;
    @BindView(R.id.get6)
    TextView get6;
    @BindView(R.id.get7)
    TextView get7;
    @BindView(R.id.get8)
    TextView get8;
    @BindView(R.id.get9)
    TextView get9;
    @BindView(R.id.get0)
    TextView get0;
    @BindView(R.id.delete)
    ImageView delete;
    @BindView(R.id.time_display)
    TextView timeDisplay;

    private Handler handler;
    private Thread myThread;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verification_code);
        ButterKnife.bind(this);

        setTimer();
        cekValid();
    }

    @OnClick({R.id.get1, R.id.get2, R.id.get3, R.id.get4, R.id.get5, R.id.get6, R.id.get7, R.id.get8, R.id.get9, R.id.get0, R.id.delete})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.get1:

                String no1 = get1.getText().toString();
                setValid(no1);
                break;
            case R.id.get2:

                String no2 = get2.getText().toString();
                setValid(no2);
                break;
            case R.id.get3:

                String no3 = get3.getText().toString();
                setValid(no3);
                break;
            case R.id.get4:

                String no4 = get4.getText().toString();
                setValid(no4);
                break;
            case R.id.get5:

                String no5 = get5.getText().toString();
                setValid(no5);
                break;
            case R.id.get6:

                String no6 = get6.getText().toString();
                setValid(no6);
                break;
            case R.id.get7:

                String no7 = get7.getText().toString();
                setValid(no7);
                break;
            case R.id.get8:

                String no8 = get8.getText().toString();
                setValid(no8);
                break;
            case R.id.get9:

                String no9 = get9.getText().toString();
                setValid(no9);
                break;
            case R.id.get0:

                String no0 = get0.getText().toString();
                setValid(no0);
                break;
            case R.id.delete:
                deleteNumber();
                break;
        }
    }

    private void setTimer() {
        myThread = new Thread(new Runnable() {
            @Override
            public void run() {
                // TODO Auto-generated method stub
                for (int i = 60; i > 0; i--) {
                    try {
                        Thread.sleep(1000);
                        //handler.sendMessage(handler.obtainMessage());
                        handler.sendMessage(handler.obtainMessage());

                    } catch (InterruptedException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }

                }
            }
        });
        myThread.start();

        handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                // TODO Auto-generated method stub
                super.handleMessage(msg);
                String string = timeDisplay.getText().toString();
                int num = Integer.parseInt(string);
                num = num - 1;
                if (num == 0) {
                    Toast.makeText(VerificationCodeActivity.this, "Pindah ke activity", Toast.LENGTH_SHORT).show();
                }
                string = Integer.toString(num);
                timeDisplay.setText(string);
            }

        };
    }

    private void setValid(String huruf) {

        String h1 = set1.getText().toString();
        String h2 = set2.getText().toString();
        String h3 = set3.getText().toString();
        String h4 = set4.getText().toString();
        String h5 = set5.getText().toString();
        String h6 = set6.getText().toString();

        if (huruf.equals(huruf) && h1.equals("")) {
            set1.setText(huruf);
        } else if (huruf.equals(huruf) && h2.equals("")) {
            set2.setText(huruf);
        } else if (huruf.equals(huruf) && h3.equals("")) {
            set3.setText(huruf);
        } else if (huruf.equals(huruf) && h4.equals("")) {
            set4.setText(huruf);
        } else if (huruf.equals(huruf) && h5.equals("")) {
            set5.setText(huruf);
        } else if (huruf.equals(huruf) && h6.equals("")) {
            set6.setText(huruf);
            Toast.makeText(this, "Waiting...", Toast.LENGTH_SHORT).show();
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    cekValid();
                }
            }, 1000);
        }
    }

    private void cekValid() {
        String valid = set1.getText().toString() +
                set2.getText().toString() +
                set3.getText().toString() +
                set4.getText().toString() +
                set5.getText().toString() +
                set6.getText().toString();
        Log.d("Valid", valid);

        String cekout = "123456";

        if (valid.equals(cekout)) {
            startActivity(new Intent(this, HomeActivity.class));
            Toast.makeText(this, "Verifikasi berhasil !!!", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Verifikasi gagal !!!", Toast.LENGTH_SHORT).show();
        }
    }

    private void deleteNumber() {
        String h1 = set1.getText().toString();
        String h2 = set2.getText().toString();
        String h3 = set3.getText().toString();
        String h4 = set4.getText().toString();
        String h5 = set5.getText().toString();
        String h6 = set6.getText().toString();

        if (!h6.equals("")) {
            set6.setText("");
        } else if (!h5.equals("")) {
            set5.setText("");
        } else if (!h4.equals("")) {
            set4.setText("");
        } else if (!h3.equals("")) {
            set3.setText("");
        } else if (!h2.equals("")) {
            set2.setText("");
        } else if (!h1.equals("")) {
            set1.setText("");
        }
    }
}
