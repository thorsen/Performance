package es.ramondin.performance.modelOracle.views.client;

import es.ramondin.performance.modelOracle.views.common.PanelVO;

import oracle.jbo.client.remote.ViewUsageImpl;
import oracle.jbo.domain.Number;
// ---------------------------------------------------------------------
// ---    File generated by Oracle ADF Business Components Design Time.
// ---    Fri May 30 11:10:32 CEST 2014
// ---    Custom code may be added to this class.
// ---    Warning: Do not modify method signatures of generated methods.
// ---------------------------------------------------------------------
public class PanelVOClient extends ViewUsageImpl implements PanelVO {
    /**
     * This is the default constructor (do not remove).
     */
    public PanelVOClient() {
    }

    public Number getNumFilas() {
        Object _ret = getApplicationModuleProxy().riInvokeExportedMethod(this,"getNumFilas",null,null);
        return (Number)_ret;
    }

    public Number getNumColumnas() {
        Object _ret = getApplicationModuleProxy().riInvokeExportedMethod(this,"getNumColumnas",null,null);
        return (Number)_ret;
    }
}