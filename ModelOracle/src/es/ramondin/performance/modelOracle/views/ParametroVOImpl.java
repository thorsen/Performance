package es.ramondin.performance.modelOracle.views;

import es.ramondin.performance.modelOracle.views.common.ParametroVO;

import oracle.jbo.Key;
import oracle.jbo.server.ViewObjectImpl;
// ---------------------------------------------------------------------
// ---    File generated by Oracle ADF Business Components Design Time.
// ---    Wed Apr 30 10:45:10 CEST 2014
// ---    Custom code may be added to this class.
// ---    Warning: Do not modify method signatures of generated methods.
// ---------------------------------------------------------------------
public class ParametroVOImpl extends ViewObjectImpl implements ParametroVO {
    /**
     * This is the default constructor (do not remove).
     */
    public ParametroVOImpl() {
    }
    
    public String getValor(String param) {
        String res = null;
        
        Key key = new Key(new Object[]{param});
        ParametroVORowImpl rowParametro = (ParametroVORowImpl)this.getRow(key);
        
        if (rowParametro != null)
            res = rowParametro.getValor();
        
        return res;
    }
}