package com.hallelujah.newwave.worship.count.activity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.hallelujah.newwave.worship.count.BuildConfig;
import com.hallelujah.newwave.worship.count.R;
import com.hallelujah.newwave.worship.count.application.MyApplication;
import com.hallelujah.newwave.worship.count.listener.OnSwipeTouchListener;
import com.hallelujah.newwave.worship.count.model.Count;
import com.hallelujah.newwave.worship.count.service.CountService;

import javax.inject.Inject;

import io.realm.Realm;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    /* view */
    private Button manButton;
    private Button womanButton;
    /* service */
    @Inject
    CountService countService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Realm.init(this);
        ((MyApplication) getApplicationContext()).appComponent.inject(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
        initData();
    }

    @SuppressLint({"SetTextI18n", "ClickableViewAccessibility"})
    private void initView() {
        /* view */
        manButton = findViewById(R.id.btn_man);
        womanButton = findViewById(R.id.btn_woman);
        TextView textView = findViewById(R.id.tv_version);
        textView.setText("v" + BuildConfig.VERSION_NAME);

        /* listener */
        manButton.setOnTouchListener(new OnSwipeTouchListener(this) {
            @Override
            public void onSwipeLeft() {
                super.onSwipeLeft();
                Log.d(TAG, "man left");
                countService.removeCount(Count.Type.MAN);
                manButton.setText(getCountString(Count.Type.MAN));
            }
        });
        womanButton.setOnTouchListener(new OnSwipeTouchListener(this) {
            @Override
            public void onSwipeLeft() {
                super.onSwipeLeft();
                countService.removeCount(Count.Type.WOMAN);
                womanButton.setText(getCountString(Count.Type.WOMAN));
                Log.d(TAG, "woman left");
            }
        });
        manButton.setOnClickListener(onClickListener);
        womanButton.setOnClickListener(onClickListener);
        manButton.setOnLongClickListener(onLongClickListener);
        womanButton.setOnLongClickListener(onLongClickListener);
    }

    private void initData() {
        manButton.setText(getCountString(Count.Type.MAN));
        womanButton.setText(getCountString(Count.Type.WOMAN));
    }

    private String getCountString(Count.Type type) {
        int count = 0;
        switch (type) {
            case MAN:
                count = countService.get().getManCount();
                return count == 0 ? getString(R.string.btn_man) : getString(R.string.btn_man) + System.lineSeparator() + count;
            case WOMAN:
                count = countService.get().getWomanCount();
                return count == 0 ? getString(R.string.btn_woman) : getString(R.string.btn_woman) + System.lineSeparator() + count;
            default:
                break;
        }
        return String.valueOf(count);
    }

    private View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.btn_man:
                    countService.addCount(Count.Type.MAN);
                    manButton.setText(getCountString(Count.Type.MAN));
                    break;
                case R.id.btn_woman:
                    countService.addCount(Count.Type.WOMAN);
                    womanButton.setText(getCountString(Count.Type.WOMAN));
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
}