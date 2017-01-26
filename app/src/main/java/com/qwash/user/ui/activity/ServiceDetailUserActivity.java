package com.qwash.user.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import com.joanzapata.iconify.IconDrawable;
import com.joanzapata.iconify.fonts.MaterialIcons;
import com.qwash.user.R;
import com.qwash.user.Sample;
import com.qwash.user.model.PrepareOrder;
import com.qwash.user.ui.widget.RobotoRegularButton;
import com.qwash.user.utils.Utils;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;
import com.wdullaer.materialdatetimepicker.time.TimePickerDialog;

import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnCheckedChanged;
import butterknife.OnClick;

/**
 * Created by aan on 12/21/16.
 */

public class ServiceDetailUserActivity extends AppCompatActivity implements TimePickerDialog.OnTimeSetListener, DatePickerDialog.OnDateSetListener {

    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.btn_pickup_date)
    Button btnPickupDate;
    @BindView(R.id.btn_pickup_time)
    Button btnPickupTime;
    @BindView(R.id.space_2)
    CardView space2;

    @BindView(R.id.perfumed)
    CheckBox perfumed;
    @BindView(R.id.interior_vaccum)
    CheckBox interiorVaccum;


    @BindView(R.id.total_price)
    TextView totalPrice;

    @BindView(R.id.btn_order)
    RobotoRegularButton btnOrder;


    @BindView(R.id.price)
    TextView price;


    private PrepareOrder prepareOrder;
    private int estimated_price = 0;
    private int perfumed_price = 0;
    private int interior_vaccum_price = 0;
    private int price_CAR = 25000;
    private int price_MOTOR = 10000;
    private String date = null;
    private String time = null;

    @OnClick({R.id.btn_pickup_date})
    void onClickPickupDate() {

        Calendar now = Calendar.getInstance();
        DatePickerDialog dpd = DatePickerDialog.newInstance(
                ServiceDetailUserActivity.this,
                now.get(Calendar.YEAR),
                now.get(Calendar.MONTH),
                now.get(Calendar.DAY_OF_MONTH)
        );
        dpd.show(getFragmentManager(), "Datepickerdialog");


    }

    @OnClick({R.id.btn_pickup_time})
    void onClickPickupTime() {

        Calendar now = Calendar.getInstance();
        TimePickerDialog dpd = TimePickerDialog.newInstance(
                ServiceDetailUserActivity.this,
                now.get(Calendar.HOUR_OF_DAY),
                now.get(Calendar.MINUTE),
                true
        );
        dpd.show(getFragmentManager(), "TimepickerDialog");

    }

    @OnClick({R.id.layout_perfumed, R.id.layout_interior_vacuum, R.id.btn_order})
    void onClickedOrder(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.layout_perfumed:
                if (perfumed.isChecked())
                    perfumed.setChecked(false);
                else
                    perfumed.setChecked(true);
                calculate();
                break;
            case R.id.layout_interior_vacuum:
                if (interiorVaccum.isChecked())
                    interiorVaccum.setChecked(false);
                else
                    interiorVaccum.setChecked(true);
                calculate();
                break;
            case R.id.btn_order:
                if (date == null)
                    Toast.makeText(this, "Please select pick_date for Washer will wash your vehicel", Toast.LENGTH_SHORT).show();
                else if (time == null)
                    Toast.makeText(this, "Please select pick_time for Washer will wash your vehicel", Toast.LENGTH_SHORT).show();
                else {

                    prepareOrder.pick_date = date;
                    prepareOrder.pick_time = time;
                    if (prepareOrder.vId.equalsIgnoreCase("1")) {
                        prepareOrder.price = price_CAR;
                        prepareOrder.perfumed_price = perfumed_price;
                        prepareOrder.interior_vaccum_price = interior_vaccum_price;
                        prepareOrder.estimated_price = estimated_price;
                    } else if (prepareOrder.vId.equalsIgnoreCase("2")) {
                        prepareOrder.price = price_MOTOR;
                        prepareOrder.perfumed_price = 0;
                        prepareOrder.perfumed_status = 0;
                        prepareOrder.interior_vaccum_price = 0;
                        prepareOrder.interior_vaccum_status = 0;
                        prepareOrder.estimated_price = 25000;
                    }
                    Intent intent = new Intent();
                    intent.putExtra(Sample.PREPARE_ORDER_OBJECT, prepareOrder);
                    setResult(Activity.RESULT_OK, intent);
                    finish();
                }
                break;
        }

    }


    @OnCheckedChanged(R.id.perfumed)
    void onPerfumed(boolean b) {
        calculate();
    }

    @OnCheckedChanged(R.id.interior_vaccum)
    void onInteriorVaccum(boolean b) {
        calculate();
    }


    private void calculate() {
        checkPerfurmed();
        checkInteriorVaccum();
        estimated_price = prepareOrder.vId.equalsIgnoreCase("1") ? (price_CAR + perfumed_price + interior_vaccum_price) : price_MOTOR;
        totalPrice.setText("IDR " + Utils.Rupiah(estimated_price));
        btnOrder.setText("IDR " + Utils.Rupiah(estimated_price) + " > ORDER NOW");
    }

    private void checkInteriorVaccum() {
        if (interiorVaccum.isChecked()) {
            interior_vaccum_price = 3000;
            prepareOrder.interior_vaccum_status = 1;
        } else {
            interior_vaccum_price = 0;
            prepareOrder.interior_vaccum_status = 0;
        }
    }


    private void checkPerfurmed() {
        if (perfumed.isChecked()) {
            perfumed_price = 3000;
            prepareOrder.perfumed_status = 1;
        } else {
            perfumed_price = 0;
            prepareOrder.perfumed_status = 0;
        }
    }


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.service_detail_user);
        ButterKnife.bind(this);

        prepareOrder = (PrepareOrder) getIntent().getSerializableExtra(Sample.PREPARE_ORDER_OBJECT);

        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(
                new IconDrawable(this, MaterialIcons.md_arrow_back)
                        .colorRes(R.color.black_424242)
                        .actionBarSize());
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent returnIntent = new Intent();
                setResult(Activity.RESULT_CANCELED, returnIntent);
                finish();
            }
        });
        getSupportActionBar().setTitle("");
        toolbarTitle.setText(getResources().getString(R.string.our_service));

        if (prepareOrder.vId.equalsIgnoreCase("1")) {
            space2.setVisibility(View.VISIBLE);
            price.setText(price_CAR + "");
        } else if (prepareOrder.vId.equalsIgnoreCase("2")) {
            space2.setVisibility(View.GONE);
            price.setText(price_MOTOR + "");
        }

        calculate();

    }


    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
        date = year + "-" + change2Digit((monthOfYear + 1)) + "-" + change2Digit(dayOfMonth);
        btnPickupDate.setText(date);
    }

    @Override
    public void onTimeSet(TimePickerDialog view, int hourOfDay, int minute, int second) {
        time = change2Digit(hourOfDay) + ":" + change2Digit(minute) + ":" + change2Digit(second);
        btnPickupTime.setText(time);
    }

    private String change2Digit(int var) {
        return (String.valueOf(var).length()) > 1 ? var + "" : "0" + var;
    }


    @Override
    public void onResume() {
        super.onResume();

        DatePickerDialog dpd = (DatePickerDialog) getFragmentManager().findFragmentByTag("Datepickerdialog");
        TimePickerDialog tpd = (TimePickerDialog) getFragmentManager().findFragmentByTag("TimepickerDialog");

        if (tpd != null) tpd.setOnTimeSetListener(this);
        if (dpd != null) dpd.setOnDateSetListener(this);
    }
}
