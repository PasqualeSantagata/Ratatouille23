package com.example.springclient.view.analyticsView;

import android.app.Dialog;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.util.Pair;
import androidx.lifecycle.Lifecycle;

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
import com.example.springclient.contract.BaseViewContract;
import com.example.springclient.presenter.AnalyticsPresenter;
import com.google.android.material.datepicker.MaterialDatePicker;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

public class StatisticheActivity extends AppCompatActivity implements BaseViewContract {
    private AnalyticsPresenter analyticsPresenter;
    private List<AnalyticsData> analyticsDataList;
    private List<String> cuochi;
    private AnyChartView anyChartView;
    private List<DataEntry> datiGrafo;
    private EditText editTextData;
    private Button buttonSelezionaDate;
    private Column column;
    private ProgressBar progressBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_statistiche);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        anyChartView = findViewById(R.id.any_chart_view);
        anyChartView.setProgressBar(findViewById(R.id.progress_bar_stat));
        analyticsPresenter = new AnalyticsPresenter(this);
        String dataFine = LocalDate.now().toString();
        String dataInizio = LocalDate.now().minusMonths(1).toString();
        analyticsPresenter.getAnalytics(dataInizio, dataFine);

        inizializzaComponenti();

    }

    @Override
    public void inizializzaComponenti() {
        buttonSelezionaDate = findViewById(R.id.buttonSelezionaDateStatistiche);
        progressBar = findViewById(R.id.progress_bar_stat);
        editTextData = findViewById(R.id.editTextDate);
        editTextData.setClickable(false);
        editTextData.setVisibility(View.INVISIBLE);
        buttonSelezionaDate.setVisibility(View.INVISIBLE);
        mostraPicker();
    }

    @Override
    public void tornaIndietro() {

    }

    @Override
    public void mostraPorgressBar() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void nascondiProgressBar() {
        progressBar.setVisibility(View.INVISIBLE);

    }

    @Override
    public boolean isVisibile() {
        return getLifecycle().getCurrentState().isAtLeast(Lifecycle.State.STARTED);
    }

    public void impossibileComunicareServer(String messaggio){
        Dialog dialog = new Dialog(this);
        mostraDialogErroreOneBtn(dialog, messaggio, view -> dialog.dismiss());
    }


    private void mostraPicker(){
        MaterialDatePicker<Pair<Long, Long>> materialDatePicker = MaterialDatePicker.Builder.dateRangePicker().
                setSelection(Pair.create(MaterialDatePicker.thisMonthInUtcMilliseconds(), MaterialDatePicker.todayInUtcMilliseconds())).build();

        buttonSelezionaDate.setOnClickListener(view -> {
            materialDatePicker.show(getSupportFragmentManager(), "picker_date_analytics");
            materialDatePicker.addOnPositiveButtonClickListener(view2 -> {
                editTextData.setText(materialDatePicker.getHeaderText());
                String dataInizio = Instant.ofEpochMilli(view2.first).atZone(ZoneId.systemDefault()).toLocalDate().toString();
                String dataFine = Instant.ofEpochMilli(view2.second).atZone(ZoneId.systemDefault()).toLocalDate().toString();
                analyticsPresenter.getAnalytics(dataInizio, dataFine);

            });
        });

    }

    public void preparaDati(){
        datiGrafo = new ArrayList<>();
        List<String> cuochiAnalitics = new ArrayList<>();
        for(AnalyticsData analyticsData: analyticsDataList){
            if(!analyticsData.getCuoco().equals("")) {
                cuochiAnalitics.add(analyticsData.getCuoco());
            }
        }
        for(String cuoco: cuochi){
            if(!cuochiAnalitics.contains(cuoco)){
                analyticsDataList.add(new AnalyticsData(cuoco, 0));
            }
        }
        for(AnalyticsData a: analyticsDataList){
            datiGrafo.add(new ValueDataEntry(a.getCuoco(), a.getOrdiniEvasi()));
        }
    }
    public void costruisciGrafo(){
        if(column == null) {
            Cartesian cartesian = AnyChart.column();
            column = cartesian.column(datiGrafo);

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

        column.color("#66A865");
        anyChartView.setChart(cartesian);

        }
        else{
            column.data(datiGrafo);
        }
        editTextData.setVisibility(View.VISIBLE);
        buttonSelezionaDate.setVisibility(View.VISIBLE);

    }

    public void setAnalyticsDataList(List<AnalyticsData> analyticsDataList){
        this.analyticsDataList = analyticsDataList;
    }

    public void setCuochi(List<String> cuochi){
        this.cuochi = cuochi;
    }

}