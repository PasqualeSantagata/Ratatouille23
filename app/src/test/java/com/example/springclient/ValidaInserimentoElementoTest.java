package com.example.springclient;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import com.example.springclient.contract.InserisciElementoContract;
import com.example.springclient.presenter.InserisciElementoPresenter;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class ValidaInserimentoElementoTest {

    @Mock
    InserisciElementoContract.InserisciElementoViewContract inserisciElementoViewContract;

    @InjectMocks
    InserisciElementoPresenter inserisciElementoPresenter = new InserisciElementoPresenter(inserisciElementoViewContract);

    @Before
    public void setUp() {
        inserisciElementoPresenter = new InserisciElementoPresenter(inserisciElementoViewContract);
    }

    @Test
    public void nomeVuotoTest() {
        boolean ret = inserisciElementoPresenter.validaInserimentoElemento("", "10.50", "pasta nostrana dai sapori forti");
        assertFalse(ret);
    }

    @Test
    public void prezzoVuotoTest() {
        boolean ret = inserisciElementoPresenter.validaInserimentoElemento("Pasta cacio e pepe", "", "pasta nostrana dai sapori forti");
        assertFalse(ret);
    }

    @Test
    public void prezzoNonValido() {
        //prezzo che non rispetta la regex
        boolean ret = inserisciElementoPresenter.validaInserimentoElemento("Pasta cacio e pepe", "-100000", "pasta nostrana dai sapori forti");
        assertFalse(ret);
    }

    @Test
    public void descrizioneVuota() {
        boolean ret = inserisciElementoPresenter.validaInserimentoElemento("Pasta cacio e pepe", "10.50", "");
        assertFalse(ret);
    }

    @Test
    public void inputValido() {
        boolean ret = inserisciElementoPresenter.validaInserimentoElemento("Pasta cacio e pepe", "10.50", "pasta nostrana dai sapori forti");
        assertTrue(ret);
    }
}
