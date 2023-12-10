package com.example.springclient;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import com.example.springclient.RetrofitService.UtenteAPI;
import com.example.springclient.authentication.TokenRefreshInterceptor;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class validaGetNewTokenTest {
    @InjectMocks
    TokenRefreshInterceptor tokenRefreshInterceptor = new TokenRefreshInterceptor();
    @Mock
    UtenteAPI utenteAPI;

    @Before
    public void setUp() {
        tokenRefreshInterceptor = new TokenRefreshInterceptor();

    }

    @Test(expected = IllegalArgumentException.class)
    public void validaTokenCampiVuotiTest() {
        Boolean risultato = tokenRefreshInterceptor.validaRefreshToken("", "");
        fail();
    }

    @Test(expected = IllegalArgumentException.class)
    public void getTokenConTokenVuotoTest() {
        tokenRefreshInterceptor.validaRefreshToken("", "http://000.000.0.0:0000/");
        fail();
    }

    @Test(expected = IllegalArgumentException.class)
    public void getTokenConUrlVuotoTest() {

        Boolean controllo = tokenRefreshInterceptor.validaRefreshToken(
                "fevvfsdcwv3rvvwrtbjej340j343rtnrkonro3409geog33gajdcn8ww9thrmq380239cm3cm32998m3t2h89chchmu923m9uxmxh93uxmfg2gxm3498xg2m93h2893ghm283h38ghx308931mhgt8rr1",
                "");
        fail("errore url vuoto");
        assertEquals(controllo, true);

    }

    @Test(expected = IllegalArgumentException.class)
    public void getTokenDatiCorrettiVuotoTest() {
        Boolean token = tokenRefreshInterceptor.validaRefreshToken(
                "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJzYW50YWdhdGExOUBnbWFpbC5jb20iLCJpYXQiOjE3MDIxMjY4NTYsImV4cCI6MTcwMjczMTY1Nn0.KdIqTrVnszaBsX-T_fGro2QyS0amI8mvwN10sb2hTRY",
                "http://192.168.1.18:8080/");
        fail();
    }


}

