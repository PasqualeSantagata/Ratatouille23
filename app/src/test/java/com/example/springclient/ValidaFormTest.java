package com.example.springclient;

import com.example.springclient.contract.CreaUtenzaContract;
import com.example.springclient.presenter.AdminPresenter;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import static org.junit.Assert.*;
@RunWith(MockitoJUnitRunner.class)
public class ValidaFormTest {
    @Mock
    CreaUtenzaContract.ViewContract creaUtenzaView;

    @InjectMocks
    CreaUtenzaContract.Presenter presenter = new AdminPresenter(creaUtenzaView);

    @Before
    public void setUp(){
        presenter = new AdminPresenter(creaUtenzaView);
    }



    @Test
    public void validaFormEmailCorrettaPasswordCorretta(){
        assertTrue(presenter.validaForm("p.santagata@gmail.com", "buonGiorno#12"));
    }

    @Test
    public void validaFormEmailCarattereSpecialeErratoPasswordCorretta(){
        assertFalse(presenter.validaForm("p.san#tagata@gmail.com", "buonGiorno#12"));
    }

    @Test
    public void validaFormEmailCarattereSpecialeNelDominioErratoPasswordCorretta(){
        assertFalse(presenter.validaForm("p.santagata@gma*il.com", "buonGiorno#12"));
    }
    @Test
    public void validaFormEmailConNumeroDiversoDaUnoDiChiocciolaPasswordCorretta(){
        assertFalse(presenter.validaForm("p.santagata@gma@il.com", "buonGiorno#12"));
    }

    @Test
    public void validaFormEmailSenzaPuntoNelDominioPasswordCorretta(){
        assertFalse(presenter.validaForm("p.santagata@gmailcom", "buonGiorno#12"));
    }

    @Test
    public void validaFormEmailConCaratteriNonAlfabeticiDopoPuntoDelDominioPasswordCorretta(){
        assertFalse(presenter.validaForm("p.santagata@gmail.124", "buonGiorno#12"));
    }

    @Test
    public void validaFormEmailCorrettaPasswordSenzaCifre(){
        assertFalse(presenter.validaForm("p.santagata@gmail.com", "buonGiorno#uno"));
    }
    @Test
    public void validaFormEmailCorrettaPasswordSenzaMinuscole(){
        assertFalse(presenter.validaForm("p.santagata@gmail.com", "BUONGIORNO#DUE66"));
    }
    @Test
    public void validaFormEmailCorrettaPasswordSenzaMaiuscole(){
        assertFalse(presenter.validaForm("p.santagata@gmail.com", "buongiorno#due66"));
    }

    @Test
    public void validaFormEmailCorrettaPasswordSenzaCaratteriSpeciali(){
        assertFalse(presenter.validaForm("p.santagata@gmail.com", "buongiornodue66"));
    }

    @Test
    public void validaFormEmailCorrettaPasswordConSpazi(){
        assertFalse(presenter.validaForm("p.santagata@gmail.com", "Buongiorno due66"));
    }
    @Test
    public void validaFormEmailCorrettaPasswordDiLunghezzaMinoreDiOtto(){
        assertFalse(presenter.validaForm("p.santagata@gmail.com", "Buong#2"));
    }






}
