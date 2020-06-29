package com.hallelujah.newwave.worship.count.activity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.hallelujah.newwave.worship.count.BuildConfig;
import com.hallelujah.newwave.worship.count.R;
import com.hallelujah.newwave.worship.count.application.MyApplication;
import com.hallelujah.newwave.worship.count.component.OnSwipeTouchListener;
import com.hallelujah.newwave.worship.count.model.Count;
import com.hallelujah.newwave.worship.count.service.CountService;
import com.hallelujah.newwave.worship.count.service.SectorService;

import javax.inject.Inject;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";

    /* view */
    private Button manButton;
    private Button womanButton;

    /* service */
    @Inject
    CountService countService;
    @Inject
    SectorService sectorService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        ((MyApplication) getApplicationContext()).appComponent.inject(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
        initData();
    }

    @SuppressLint({"SetTextI18n", "ClickableViewAccessibility"})
    private void initView() {
        /* view */
        Spinner sectorSpinner = findViewById(R.id.sp_sector);
        manButton = findViewById(R.id.btn_man);
        womanButton = findViewById(R.id.btn_woman);
        TextView textView = findViewById(R.id.tv_version);
        textView.setText("v" + BuildConfig.VERSION_NAME);

        /* sector spinner */
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, sectorService.getSectorList());
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sectorSpinner.setAdapter(adapter);
        sectorSpinner.setSelection(sectorService.getCurrentSector(), false);
        sectorSpinner.setOnItemSelectedListener(onItemSelectedListener);

        /* listener */
        // OnSwipeListener 는 context 필요로 member 변수 설정 못함
        manButton.setOnTouchListener(new OnSwipeTouchListener(this) {
            @Override
            public void onSwipeLeft() {
                super.onSwipeLeft();
                Log.d(TAG, "man left");
                countService.removeCount(Count.Type.MAN);
                manButton.setText(countService.getCountString(MainActivity.this, Count.Type.MAN));
            }
        });
        womanButton.setOnTouchListener(new OnSwipeTouchListener(this) {
            @Override
            public void onSwipeLeft() {
                super.onSwipeLeft();
                countService.removeCount(Count.Type.WOMAN);
                womanButton.setText(countService.getCountString(MainActivity.this, Count.Type.WOMAN));
                Log.d(TAG, "woman left");
            }
        });
        manButton.setOnClickListener(onClickListener);
        womanButton.setOnClickListener(onClickListener);
        manButton.setOnLongClickListener(onLongClickListener);
        womanButton.setOnLongClickListener(onLongClickListener);
    }

    private void initData() {
        manButton.setText(countService.getCountString(MainActivity.this, Count.Type.MAN));
        womanButton.setText(countService.getCountString(MainActivity.this, Count.Type.WOMAN));
    }

    private View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.btn_man:
                    countService.addCount(Count.Type.MAN);
                    manButton.setText(countService.getCountString(MainActivity.this, Count.Type.MAN));
                    break;
                case R.id.btn_woman:
                    countService.addCount(Count.Type.WOMAN);
                    womanButton.setText(countService.getCountString(MainActivity.this, Count.Type.WOMAN));
                    break;
                default:
                    break;
            }
        }
    };

    private View.OnLongClickListener onLongClickListener = new View.OnLongClickListener() {
        @Override
        public boolean onLongClick(View view) {
            switch (view.getId()) {
                case R.id.btn_man:
                    countService.resetCount(Count.Type.MAN);
                    manButton.setText(getString(R.string.btn_man));
                    break;
                case R.id.btn_woman:
                    countService.resetCount(Count.Type.WOMAN);
                    womanButton.setText(getString(R.string.btn_woman));
                    break;
                default:
                    break;
            }
            return true;
        }
    };

    private AdapterView.OnItemSelectedListener onItemSelectedListener = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
            Log.d(TAG, "selected spinner: " + i);
            sectorService.setCurrentSector(i);
        }

        @Override
        public void onNothingSelected(AdapterView<?> adapterView) {

        }
    };
}