package com.example.openweather.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.VisibleForTesting;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.openweather.App;
import com.example.openweather.R;
import com.example.openweather.api.OpenWeatherService;
import com.example.openweather.api.model.City;
import com.example.openweather.api.model.WeatherResponse;
import com.example.openweather.injection.AppComponent;
import com.example.openweather.ui.adapter.ViewModelAdapter;
import com.example.openweather.ui.factory.ViewModelFactory;
import com.example.openweather.ui.presenter.DaggerMainFragmentComponent;
import com.example.openweather.ui.presenter.MainModule;
import com.example.openweather.ui.presenter.MainPresenter;
import com.example.openweather.ui.presenter.MainView;
import com.example.openweather.ui.viewmodel.CityWeatherImageViewModel;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindDimen;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.reactivex.disposables.CompositeDisposable;

public class MainFragment extends Fragment implements MainView {
    @Inject
    ViewModelAdapter adapter;
    @Inject
    OpenWeatherService openWeatherService;
    @Inject
    ViewModelFactory viewModelFactory;

    @Inject
    MainPresenter mainPresenter;

    @BindView(R.id.search_phrase)
    EditText searchView;
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    @BindView(R.id.progress_bar)
    ProgressBar progressBar;
    @BindDimen(R.dimen.image_space)
    int space;
    @VisibleForTesting
    CompositeDisposable disposables;
    Unbinder unbinder;

    private static final String DB_NAME = "cities.db";

    public static MainFragment newInstance() {
        return new MainFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        disposables = new CompositeDisposable();
        injectDaggerMembers();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        unbinder = ButterKnife.bind(this, view);

        searchView.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                mainPresenter.search(v.getText().toString());
                return true;
            }
            return false;
        });


        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);
        recyclerView.addItemDecoration(new DividerItemDecoration(recyclerView.getContext(), LinearLayout.VERTICAL));
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onStart() {
        super.onStart();
        mainPresenter.startPresenting();
    }

    @Override
    public void onStop() {
        mainPresenter.stopPresenting();
        super.onStop();
    }

    public void onImageLongPress(String id, String uri) {
        //todo - implement new feature
    }


    public void injectDaggerMembers() {
        DaggerMainFragmentComponent.builder()
                .appComponent(getInjectorComponent())
                .mainModule(new MainModule(this))
                .build()
                .inject(this);

    }

    private AppComponent getInjectorComponent() {
        return App.getInstance().getAppComponent();
    }

    @Override
    public boolean isDatabaseExists() {
        return getContext().getDatabasePath(DB_NAME).exists();
    }

    @Override
    public void showLoadingIndicator() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoadingIndicator() {
        progressBar.setVisibility(View.INVISIBLE);
    }

    @Override
    public void displayWeatherForCity(WeatherResponse weatherResponse) {
        hideLoadingIndicator();
        setData(weatherResponse.getWeatherList().get(0));
        Toast.makeText(this.getContext(), "Weather response"
                + weatherResponse.getWeatherList().get(0).getDescription(), Toast.LENGTH_LONG).show();
    }

    @Override
    public void displayError(Throwable throwable) {
        hideLoadingIndicator();
        Toast.makeText(this.getContext(), "Some thing went wrong", Toast.LENGTH_LONG).show();
    }

    @Override
    public void displayCity(City city) {
        Toast.makeText(this.getContext(), "City is :" + city.id, Toast.LENGTH_LONG).show();
        mainPresenter.fetchWeather(city);
    }

    @Override
    public void setData(WeatherResponse.Weather weather) {
        List<CityWeatherImageViewModel> models = new ArrayList<>();
        models.add(viewModelFactory.createViewModel(weather.getId(), weather, null));
        adapter.setViewModels(models);
    }
}
