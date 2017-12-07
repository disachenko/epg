package com.noriginmedia.epg.presentation;

import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.noriginmedia.epg.R;
import com.noriginmedia.epg.common.AndroidUtils;
import com.noriginmedia.epg.presentation.epg.EpgFragment;
import com.noriginmedia.epg.presentation.home.HomeFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.navigation_view)
    BottomNavigationView navigationView;

    private Unbinder unbinder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        unbinder = ButterKnife.bind(this);

        AndroidUtils.removeShiftMode(navigationView);
        navigationView.setOnNavigationItemSelectedListener(this::showScreen);
        navigationView.setSelectedItemId(R.id.schedule);
    }

    @Override
    protected void onDestroy() {
        unbinder.unbind();
        super.onDestroy();
    }

    private boolean showScreen(MenuItem item) {
        Fragment fragment;
        switch (item.getItemId()) {
            case R.id.home:
                fragment = HomeFragment.newInstance("Home");
                break;
            case R.id.tv:
                fragment = HomeFragment.newInstance("TV");
                break;
            case R.id.schedule:
                fragment = new EpgFragment();
                break;
            case R.id.history:
                fragment = HomeFragment.newInstance("History");
                break;
            case R.id.profile:
                fragment = HomeFragment.newInstance("Profile");
                break;
            default:
                throw new IllegalStateException("Invalid navigation menu item id: " + item.getItemId());
        }
        final FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.container, fragment).commit();
        return true;
    }
}
