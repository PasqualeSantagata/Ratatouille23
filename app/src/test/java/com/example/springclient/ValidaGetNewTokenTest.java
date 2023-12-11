package com.example.springclient;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import com.example.springclient.authentication.TokenRefreshInterceptor;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class ValidaGetNewTokenTest {
    @InjectMocks
    TokenRefreshInterceptor tokenRefreshInterceptor = new TokenRefreshInterceptor();
    @Before
    public void setUp() {
        tokenRefreshInterceptor = new TokenRefreshInterceptor();
    }

    @Test(expected = IllegalArgumentException.class)
    public void getTokenConTokenVuotoTest() {
        tokenRefreshInterceptor.validaRefreshToken("", "http://000.000.0.0:0000/");
        fail();
    }

    @Test(expected = IllegalArgumentException.class)
    public void getTokenConTokenLunghezzaInferioreTest() {
        tokenRefreshInterceptor.validaRefreshToken("3rg53", "http://000.000.0.0:0000/");
        fail();
    }

    @Test(expected = IllegalArgumentException.class)
    public void getTokenConTokenLunghezzaSuperioreTest() {
        tokenRefreshInterceptor.validaRefreshToken("fevvfsdcwv3rvvwrtbjej340j343rtnrkonro3409geog33gajdcn8ww9thrmq380239cm3cm32998m3t2h89chchmu923m9uxmxh93uxmfg2gxm3498xg2m93h2893ghm283h38ghx308931mhgt8rr12434343", "http://000.000.0.0:0000/");
        fail();
    }

    @Test(expected = IllegalArgumentException.class)
    public void getTokenConUrlVuotoTest() {
        Boolean controllo = tokenRefreshInterceptor.validaRefreshToken(
                "fevvfsdcwv3rvvwrtbjej340j343rtnrkonro3409geog33gajdcn8ww9thrmq380239cm3cm32998m3t2h89chchmu923m9uxmxh93uxmfg2gxm3498xg2m93h2893ghm283h38ghx308931mhgt8rr1",
                "");
        fail("errore url vuoto");
    }

    @Test(expected = IllegalArgumentException.class)
    public void getTokenConUrlNonValidoTest() {
        Boolean controllo = tokenRefreshInterceptor.validaRefreshToken(
                "fevvfsdcwv3rvvwrtbjej340j343rtnrkonro3409geog33gajdcn8ww9thrmq380239cm3cm32998m3t2h89chchmu923m9uxmxh93uxmfg2gxm3498xg2m93h2893ghm283h38ghx308931mhgt8rr1",
                "hattp://000.000.0.0:/0000/");

       fail("errore url non valido");
    }


    @Test
    public void getTokenDatiCorrettiTest() {
        Boolean token = tokenRefreshInterceptor.validaRefreshToken(
                "fevvfsdcwv3rvvwrtbjej340j343rtnrkonro3409geog33gajdcn8ww9thrmq380239cm3cm32998m3t2h89chchmu923m9uxmxh93uxmfg2gxm3498xg2m93h2893ghm283h38ghx308931mhgt8rr1",
                "http://000.000.0.0:0000/");

        assertTrue(token);
    }


}

