package es.ramondin.performance.modelOracle.views.client;

import es.ramondin.performance.modelOracle.views.common.TipoGraficoVO;

import oracle.jbo.Key;
import oracle.jbo.client.remote.ViewUsageImpl;
// ---------------------------------------------------------------------
// ---    File generated by Oracle ADF Business Components Design Time.
// ---    Thu May 15 15:40:48 CEST 2014
// ---    Custom code may be added to this class.
// ---    Warning: Do not modify method signatures of generated methods.
// ---------------------------------------------------------------------
public class TipoGraficoVOClient extends ViewUsageImpl implements TipoGraficoVO {
    /**
     * This is the default constructor (do not remove).
     */
    public TipoGraficoVOClient() {
    }

    public void setCurrentRowTipoGrafico(Key key) {
        Object _ret = getApplicationModuleProxy().riInvokeExportedMethod(this,"setCurrentRowTipoGrafico",new String [] {"oracle.jbo.Key"},new Object[] {key});
        return;
    }
}