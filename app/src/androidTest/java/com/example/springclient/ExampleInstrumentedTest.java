package com.example.springclient;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import android.content.Context;
import android.os.Looper;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;

import com.example.springclient.RetrofitService.UtenteAPI;
import com.example.springclient.authentication.TokenRefreshInterceptor;
import com.example.springclient.presenter.AutenticazionePresenter;
import com.example.springclient.view.MainActivity;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {
    TokenRefreshInterceptor tokenRefreshInterceptor;
    AutenticazionePresenter mainActivityPresenter;

    @Before
    public void setUp(){
         tokenRefreshInterceptor = new TokenRefreshInterceptor();

        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .addInterceptor(loggingInterceptor)
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.1.18:8080/")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
                .client(okHttpClient)
                .build();
        UtenteAPI service = retrofit.create(UtenteAPI.class);

        MainActivity mainActivity = new MainActivity();
        mainActivityPresenter = new AutenticazionePresenter(mainActivity);
        tokenRefreshInterceptor.setMainActivityPresenter(mainActivityPresenter);

    }

    @Test
    public void useAppContext() {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        assertEquals("com.example.springclient", appContext.getPackageName());
    }

    @Test
    public void getTokenDatiCorrettiVuotoTest() {
        Looper.prepare();

        try {
            String token = tokenRefreshInterceptor.getNewToken(
                    "fevvfsdcwv3rvvwrtbjej340j343rtnrkonro3409geog33gajdcn8ww9thrmq380239cm3cm32998m3t2h89chchmu923m9uxmxh93uxmfg2gxm3498xg2m93h2893ghm283h38ghx308931mhgt8rr1",
                    "http://192.168.1.18:8080/");

            fail();
        }
        catch(IOException e) {
            //Fail poichè vuol dire che non funzionano i controlli
            //questa exepction occorre se c'è un problema di connessione al server
            fail();
        }

    }
}