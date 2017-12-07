package com.noriginmedia.epg.presentation.epg;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.noriginmedia.epg.App;
import com.noriginmedia.epg.R;
import com.noriginmedia.epg.common.view.ProgressPanel;
import com.noriginmedia.epg.data.network.models.Channel;
import com.noriginmedia.epg.presentation.dagger.DaggerScreenComponent;
import com.noriginmedia.epg.presentation.epg.view.EpgView;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class EpgFragment extends Fragment implements EpgScreen {

    @Inject
    EpgPresenter presenter;

    @BindView(R.id.toolbar_left_button)
    ImageView leftMenuButton;

    @BindView(R.id.toolbar_center_image)
    ImageView centerIcon;

    @BindView(R.id.toolbar_right_button)
    ImageView rightMenuButton;

    @BindView(R.id.progress_panel)
    ProgressPanel progressPanel;

    @BindView(R.id.day_line)
    RecyclerView dayLine;
    private DayLineAdapter dayLineAdapter;

    @BindView(R.id.epg)
    EpgView epgView;

    private Unbinder unbinder;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DaggerScreenComponent.builder()
                .appComponent(((App) getContext().getApplicationContext()).getAppComponent())
                .build().inject(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_epg, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        leftMenuButton.setImageResource(R.drawable.ic_user);
        centerIcon.setImageResource(R.drawable.norigin_media);
        rightMenuButton.setImageResource(R.drawable.ic_search);

        dayLine.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        dayLine.setAdapter(dayLineAdapter = new DayLineAdapter());
        dayLineAdapter.setOnItemClickListener((v, item, position) -> {
            epgView.setTime(item);
            dayLineAdapter.setSelectedItem(item);
        });

        epgView.addOnEpgScrollEventListener(presenter::onEpgScrollEvent);

        presenter.onViewCreated(this);
    }

    @Override
    public void onDestroyView() {
        presenter.onDestroyView();
        unbinder.unbind();
        super.onDestroyView();
    }

    @Override
    public void showProgress() {
        progressPanel.showProgress();
    }

    @Override
    public void hideProgress() {
        progressPanel.hideProgress();
    }

    @Override
    public void setDates(List<Long> dates) {
        dayLineAdapter.setItems(dates);
    }

    @Override
    public int getSelectedDatePosition() {
        return dayLineAdapter.getSelectedItemPosition();
    }

    @Override
    public void setSelectedDate(long timestamp) {
        dayLineAdapter.setSelectedItem(timestamp);
    }

    @Override
    public void setEpg(List<Channel> schedule) {
        epgView.setEpg(schedule);
    }
}
