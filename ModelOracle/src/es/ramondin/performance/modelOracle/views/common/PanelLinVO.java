package es.ramondin.performance.modelOracle.views.common;

import es.ramondin.performance.modelOracle.views.PanelLinVORowImpl;

import java.util.ArrayList;

import oracle.jbo.ViewObject;
// ---------------------------------------------------------------------
// ---    File generated by Oracle ADF Business Components Design Time.
// ---    Fri May 30 09:08:54 CEST 2014
// ---------------------------------------------------------------------
public interface PanelLinVO extends ViewObject {
    Integer getNumLineasPanel();

    ArrayList<PanelLinVORowImpl> getLineasPanel();
}
