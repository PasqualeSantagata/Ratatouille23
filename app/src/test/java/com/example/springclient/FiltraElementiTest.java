package com.example.springclient;


import static org.junit.Assert.assertEquals;

import com.example.springclient.contract.GestioneElementiContract;
import com.example.springclient.entity.ElementoMenu;
import com.example.springclient.presenter.GestioneElementiPresenter;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

@RunWith(MockitoJUnitRunner.class)
public class FiltraElementiTest {
    @Mock
    GestioneElementiContract.CercaElementoViewContract cercaElementoView;

    @InjectMocks
    GestioneElementiContract.Presenter gestioneElementiPresenter = new GestioneElementiPresenter(cercaElementoView);

    @Test
    public void filtraElementiNodeCoverageSet1Test(){
        List<ElementoMenu> elementoMenuListApp = new ArrayList<>();
        ElementoMenu e1 = new ElementoMenu("Pasta con tonno", "Desc1", "Italiano" );
        ElementoMenu e2 = new ElementoMenu("Frittata", "Desc2", "Italiano" );
        ElementoMenu e3 = new ElementoMenu("Spaghetti", "Desc3", "Italiano" );
        ElementoMenu e4 = new ElementoMenu("Orecchiette", "Desc4", "Italiano" );
        elementoMenuListApp.add(e1);
        elementoMenuListApp.add(e2);
        elementoMenuListApp.add(e3);
        elementoMenuListApp.add(e4);
        List<ElementoMenu> elementoMenuList = new ArrayList<>(elementoMenuListApp);
        gestioneElementiPresenter.filtraElementi("Orecchiette", elementoMenuList, elementoMenuListApp);
        List<ElementoMenu> risultato = new ArrayList<>();
        risultato.add(e4);
        assertEquals(risultato, elementoMenuList);
    }

    @Test
    public void filtraElementiNodeCoverageSet2Test(){
        List<ElementoMenu> elementoMenuListApp = new ArrayList<>();
        ElementoMenu e1 = new ElementoMenu("Pasta con tonno", "Desc1", "Italiano" );
        ElementoMenu e2 = new ElementoMenu("Frittata", "Desc2", "Italiano" );
        ElementoMenu e3 = new ElementoMenu("Spaghetti", "Desc3", "Italiano" );
        ElementoMenu e4 = new ElementoMenu("Orecchiette", "Desc4", "Italiano" );
        elementoMenuListApp.add(e1);
        elementoMenuListApp.add(e2);
        elementoMenuListApp.add(e3);
        elementoMenuListApp.add(e4);
        List<ElementoMenu> elementoMenuList = new ArrayList<>(elementoMenuListApp);
        gestioneElementiPresenter.filtraElementi("", elementoMenuList, elementoMenuListApp);
        List<ElementoMenu> risultato = new ArrayList<>();
        risultato.add(e1);
        risultato.add(e2);
        risultato.add(e3);
        risultato.add(e4);
        assertEquals(risultato, elementoMenuList);
    }

    @Test
    public void filtraElementiPath82_83_84_85_86_84_101(){
        ElementoMenu e1 = new ElementoMenu("b", "Desc1", "Italiano" );
        List<ElementoMenu> elementoMenuListApp = new ArrayList<>();
        List<ElementoMenu> elementoMenuList = new ArrayList<>();
        elementoMenuList.add(e1);
        elementoMenuListApp.add(e1);
        gestioneElementiPresenter.filtraElementi("a", elementoMenuList, elementoMenuListApp);
        List<ElementoMenu> risultato = new ArrayList<>();
        assertEquals(risultato, elementoMenuList);
    }

    @Test
    public void filtraElementiPath82_83_84_85_87_88_84_101(){
        ElementoMenu e1 = new ElementoMenu("ab", "Desc1", "Italiano" );
        List<ElementoMenu> elementoMenuListApp = new ArrayList<>();
        List<ElementoMenu> elementoMenuList = new ArrayList<>();
        elementoMenuListApp.add(e1);
        gestioneElementiPresenter.filtraElementi("a", elementoMenuList, elementoMenuListApp);
        List<ElementoMenu> risultato = new ArrayList<>();
        risultato.add(e1);
        assertEquals(risultato, elementoMenuList);
    }
    @Test
    public void filtraElementiPath82_83_84_101(){
        ElementoMenu e1 = new ElementoMenu("ab", "Desc1", "Italiano" );
        List<ElementoMenu> elementoMenuListApp = new ArrayList<>();
        List<ElementoMenu> elementoMenuList = new ArrayList<>();
        elementoMenuList.add(e1);
        gestioneElementiPresenter.filtraElementi("a", elementoMenuList, elementoMenuListApp);
        List<ElementoMenu> risultato = new ArrayList<>();
        risultato.add(e1);
        assertEquals(risultato, elementoMenuList);
    }

    @Test
    public void filtraElementiPath82_83_92_101(){
        ElementoMenu e1 = new ElementoMenu("ab", "Desc1", "Italiano" );
        List<ElementoMenu> elementoMenuListApp = new ArrayList<>();
        List<ElementoMenu> elementoMenuList = new ArrayList<>();
        elementoMenuList.add(e1);
        gestioneElementiPresenter.filtraElementi("a", elementoMenuList, elementoMenuListApp);
        List<ElementoMenu> risultato = new ArrayList<>();
        risultato.add(e1);
        assertEquals(risultato, elementoMenuList);
    }

    @Test
    public void filtraElementiPath82_83_92_93_92_101(){
        ElementoMenu e1 = new ElementoMenu("ab", "Desc1", "Italiano" );
        List<ElementoMenu> elementoMenuListApp = new ArrayList<>();
        List<ElementoMenu> elementoMenuList = new ArrayList<>();
        elementoMenuList.add(e1);
        elementoMenuListApp.add(e1);
        gestioneElementiPresenter.filtraElementi("", elementoMenuList, elementoMenuListApp);
        List<ElementoMenu> risultato = new ArrayList<>();
        risultato.add(e1);
        assertEquals(risultato, elementoMenuList);
    }

    @Test
    public void filtraElementiPath82_83_92_93_94_92_101(){
        ElementoMenu e1 = new ElementoMenu("ab", "Desc1", "Italiano" );
        List<ElementoMenu> elementoMenuListApp = new ArrayList<>();
        List<ElementoMenu> elementoMenuList = new ArrayList<>();
        elementoMenuListApp.add(e1);
        gestioneElementiPresenter.filtraElementi("", elementoMenuList, elementoMenuListApp);
        List<ElementoMenu> risultato = new ArrayList<>();
        risultato.add(e1);
        assertEquals(risultato, elementoMenuList);
    }

    @Test
    public void filtraElementiPath82_83_84_85_87_84_101(){
        ElementoMenu e1 = new ElementoMenu("a", "Desc1", "Italiano" );
        List<ElementoMenu> elementoMenuListApp = new ArrayList<>();
        List<ElementoMenu> elementoMenuList = new ArrayList<>();
        elementoMenuListApp.add(e1);
        elementoMenuList.add(e1);
        gestioneElementiPresenter.filtraElementi("a", elementoMenuList, elementoMenuListApp);
        List<ElementoMenu> risultato = new ArrayList<>();
        risultato.add(e1);
        assertEquals(risultato, elementoMenuList);
    }

}
