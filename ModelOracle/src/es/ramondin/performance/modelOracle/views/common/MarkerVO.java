package es.ramondin.performance.modelOracle.views.common;

import java.sql.Date;

import oracle.jbo.ViewObject;
import oracle.jbo.domain.Number;
// ---------------------------------------------------------------------
// ---    File generated by Oracle ADF Business Components Design Time.
// ---    Thu Apr 24 16:04:35 CEST 2014
// ---------------------------------------------------------------------
public interface MarkerVO extends ViewObject {


    Double[] getValorMarkers(Date desdeFecha);

    Number getSentidoMejor(Date desdeFecha);
}
