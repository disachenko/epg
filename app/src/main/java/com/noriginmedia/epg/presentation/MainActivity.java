package com.noriginmedia.epg.presentation;

import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import com.noriginmedia.epg.R;
import com.noriginmedia.epg.common.AndroidUtils;
import com.noriginmedia.epg.presentation.epg.EpgFragment;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.navigation_view)
    BottomNavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        AndroidUtils.removeShiftMode(navigationView);
        navigationView.setOnNavigationItemSelectedListener(item -> {
            Fragment fragment;
            switch (item.getItemId()) {
                case R.id.schedule:
                    fragment = new EpgFragment();
                    break;
                default:
                    fragment = new Fragment();
            }
            final FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.container, fragment).commit();
            return true;
        });
    }
}
