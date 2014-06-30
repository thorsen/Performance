package es.ramondin.performance.modelOracle.views.common;

import java.math.BigDecimal;

import java.util.ArrayList;

import oracle.jbo.ViewObject;
// ---------------------------------------------------------------------
// ---    File generated by Oracle ADF Business Components Design Time.
// ---    Thu Jun 19 09:21:49 CEST 2014
// ---------------------------------------------------------------------
public interface MarkerEspecialVO extends ViewObject {


    void cargaMarkersEsp(BigDecimal seccion, BigDecimal celula, Integer celulaAgrup, ArrayList celulasExcepcion, Integer anoRuptura, Integer anosHistorico,
                         Integer hastaFecha, Boolean desgloseMensual, Boolean mismoPeriodo, Boolean romperPorTurno, String[] turnos);

    Double[] getValorMarkersEsp();

    String[] getTextoMarkersEsp();

    String[] getColorMarkersEspIndependiente();

    String[] getColorMarkersEsp();
}