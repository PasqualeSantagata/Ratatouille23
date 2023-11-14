package com.example.springclient.view.analyticsView;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.util.Pair;

import com.anychart.AnyChart;
import com.anychart.AnyChartView;
import com.anychart.chart.common.dataentry.DataEntry;
import com.anychart.chart.common.dataentry.ValueDataEntry;
import com.anychart.charts.Cartesian;
import com.anychart.core.cartesian.series.Column;
import com.anychart.enums.Anchor;
import com.anychart.enums.HoverMode;
import com.anychart.enums.Position;
import com.anychart.enums.TooltipPositionMode;
import com.example.springclient.R;
import com.example.springclient.analytics.AnalyticsData;
import com.example.springclient.presenter.AnalyticsPresenter;
import com.google.android.material.datepicker.MaterialDatePicker;

import java.util.ArrayList;
import java.util.List;

public class StatisticheActivity extends AppCompatActivity {
    private AnalyticsPresenter analyticsPresenter;
    private List<AnalyticsData> analyticsDataList;
    private List<String> cuochi;
    private AnyChartView anyChartView;
    private List<DataEntry> datiGrafo;
    private EditText editTextData;
    private Button buttonSelezionaDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_statistiche);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        anyChartView = findViewById(R.id.any_chart_view);
        anyChartView.setProgressBar(findViewById(R.id.progress_bar));
        analyticsPresenter = new AnalyticsPresenter(this);
        analyticsPresenter.getAnalytics();

    }

    public void initializeComponents() {
        buttonSelezionaDate = findViewById(R.id.buttonSelezionaDateStatistiche);
        editTextData = findViewById(R.id.editTextDate);
        mostraPicker();
    }


    private void mostraPicker(){
        MaterialDatePicker materialDatePicker=MaterialDatePicker.Builder.dateRangePicker().
                setSelection(Pair.create(MaterialDatePicker.thisMonthInUtcMilliseconds(), MaterialDatePicker.todayInUtcMilliseconds())).build();

        buttonSelezionaDate.setOnClickListener(view -> {
            materialDatePicker.show(getSupportFragmentManager(), "picker_date_analytics");
            materialDatePicker.addOnPositiveButtonClickListener(view2 -> {
                editTextData.setText(materialDatePicker.getHeaderText());
            });
        });

    }

    public void preparaDati(){
        datiGrafo = new ArrayList<>();
        List<String> cuochiAnalitics = new ArrayList<>();
        for(AnalyticsData analyticsData: analyticsDataList){
            cuochiAnalitics.add(analyticsData.getCuoco());
        }
        for(String cuoco: cuochi){
            if(!cuochiAnalitics.contains(cuoco)){
                analyticsDataList.add(new AnalyticsData(cuoco, 0));
            }
        }
        for(AnalyticsData a: analyticsDataList){
            datiGrafo.add(new ValueDataEntry(a.getCuoco(), a.getOrdiniEvasi()));
        }
        costruisciGrafo();
    }
    public void costruisciGrafo(){
        Cartesian cartesian = AnyChart.column();
        Column column = cartesian.column(datiGrafo);

        column.tooltip()
                .titleFormat("{%X}")
                .position(Position.CENTER_BOTTOM)
                .anchor(Anchor.CENTER_BOTTOM)
                .offsetX(0d)
                .offsetY(5d)
                .format("{%Value}{groupsSeparator: }");

        cartesian.animation(true);
        cartesian.title("Portate preparate");

        cartesian.yScale().minimum(0d);

        cartesian.yAxis(0).labels().format("{%Value}{groupsSeparator: }");

        cartesian.tooltip().positionMode(TooltipPositionMode.POINT);
        cartesian.interactivity().hoverMode(HoverMode.BY_X);

        cartesian.xAxis(0).title("Email cuochi");
        cartesian.yAxis(0).title("Portate preparate");

        anyChartView.setChart(cartesian);

    }

    public void setAnalyticsDataList(List<AnalyticsData> analyticsDataList){
        this.analyticsDataList = analyticsDataList;
    }

    public void setCuochi(List<String> cuochi){
        this.cuochi = cuochi;
    }

}