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
    public void getTokenConTokenNonValidoTest() {
        tokenRefreshInterceptor.validaRefreshToken("3rg53", "http://000.000.0.0:0000/");
        fail();
    }

    @Test(expected = IllegalArgumentException.class)
    public void getTokenConUrlVuotoTest() {
        Boolean controllo = tokenRefreshInterceptor.validaRefreshToken(
                "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJwLnNtdXNpQGdtYWlsLmNvbSIsImlhdCI6MTcwMjM3NDY4NiwiZXhwIjoxNzAyNDYxMDg2fQ.rGbglA9vL-W0m30HG7qV2QIzp8KxeWZHDcGkW5mLS6I",
                "");
        fail("errore url vuoto");
    }

    @Test(expected = IllegalArgumentException.class)
    public void getTokenConUrlNonValidoTest() {
        Boolean controllo = tokenRefreshInterceptor.validaRefreshToken(
                "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJwLnNtdXNpQGdtYWlsLmNvbSIsImlhdCI6MTcwMjM3NDY4NiwiZXhwIjoxNzAyNDYxMDg2fQ.rGbglA9vL-W0m30HG7qV2QIzp8KxeWZHDcGkW5mLS6I",
                "hattp://000.000.0.0:/0000/");

       fail("errore url non valido");
    }


    @Test
    public void getTokenDatiCorrettiTest() {
        Boolean token = tokenRefreshInterceptor.validaRefreshToken(
                "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJwLnNtdXNpQGdtYWlsLmNvbSIsImlhdCI6MTcwMjM3NDY4NiwiZXhwIjoxNzAyNDYxMDg2fQ.rGbglA9vL-W0m30HG7qV2QIzp8KxeWZHDcGkW5mLS6I",
                "http://000.000.0.0:0000/");

        assertTrue(token);
    }


}

