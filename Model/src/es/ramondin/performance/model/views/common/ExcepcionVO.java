package es.ramondin.performance.model.views.common;

import java.math.BigDecimal;

import java.util.ArrayList;

import oracle.jbo.ViewObject;
// ---------------------------------------------------------------------
// ---    File generated by Oracle ADF Business Components Design Time.
// ---    Thu Apr 24 17:38:56 CEST 2014
// ---------------------------------------------------------------------
public interface ExcepcionVO extends ViewObject {
    Boolean hayExcepciones();

    ArrayList<BigDecimal> getCelulasExcepcion();
}
