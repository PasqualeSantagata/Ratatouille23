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
        assertTrue(presenter.validaForm("san.tagata149@gmail.com", "Francescone23#"));

    }





}
