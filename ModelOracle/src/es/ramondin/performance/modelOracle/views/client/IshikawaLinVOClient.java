package es.ramondin.performance.modelOracle.views.client;

import es.ramondin.performance.modelOracle.views.common.IshikawaLinVO;

import java.util.ArrayList;

import oracle.jbo.client.remote.ViewUsageImpl;
// ---------------------------------------------------------------------
// ---    File generated by Oracle ADF Business Components Design Time.
// ---    Mon May 12 16:14:40 CEST 2014
// ---    Custom code may be added to this class.
// ---    Warning: Do not modify method signatures of generated methods.
// ---------------------------------------------------------------------
public class IshikawaLinVOClient extends ViewUsageImpl implements IshikawaLinVO {
    /**
     * This is the default constructor (do not remove).
     */
    public IshikawaLinVOClient() {
    }

    public Integer getNumCausas() {
        Object _ret = getApplicationModuleProxy().riInvokeExportedMethod(this,"getNumCausas",null,null);
        return (Integer)_ret;
    }

    public ArrayList<Object[]> getCausas() {
        Object _ret = getApplicationModuleProxy().riInvokeExportedMethod(this,"getCausas",null,null);
        return (ArrayList<Object[]>)_ret;
    }
}
