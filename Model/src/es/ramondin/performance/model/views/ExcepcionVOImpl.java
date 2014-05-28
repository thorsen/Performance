package es.ramondin.performance.model.views;

import es.ramondin.performance.model.views.common.ExcepcionVO;

import java.math.BigDecimal;

import java.util.ArrayList;

import oracle.jbo.RowSetIterator;
import oracle.jbo.server.ViewObjectImpl;
// ---------------------------------------------------------------------
// ---    File generated by Oracle ADF Business Components Design Time.
// ---    Thu Apr 24 17:38:13 CEST 2014
// ---    Custom code may be added to this class.
// ---    Warning: Do not modify method signatures of generated methods.
// ---------------------------------------------------------------------
public class ExcepcionVOImpl extends ViewObjectImpl implements ExcepcionVO {
    /**
     * This is the default constructor (do not remove).
     */
    public ExcepcionVOImpl() {
        
    }
    
    public Boolean hayExcepciones() {
        return this.getRowCount() != 0;
    }
    
    public ArrayList<BigDecimal> getCelulasExcepcion() {
        ArrayList<BigDecimal> celulasExcepcion = new ArrayList<BigDecimal>();
        
        RowSetIterator rsiExcepciones = this.getRowSet();
        rsiExcepciones.reset();
        
        if (rsiExcepciones.hasNext()) {
            ExcepcionVORowImpl riExcepcion = (ExcepcionVORowImpl) rsiExcepciones.next();
            
            if (riExcepcion.getRvarCelula141().signum() == 1)
                celulasExcepcion.add(riExcepcion.getRvarCelula141());
            if (riExcepcion.getRvarCelula241().signum() == 1)
                celulasExcepcion.add(riExcepcion.getRvarCelula241());
            if (riExcepcion.getRvarCelula341().signum() == 1)
                celulasExcepcion.add(riExcepcion.getRvarCelula341());
            if (riExcepcion.getRvarCelula441().signum() == 1)
                celulasExcepcion.add(riExcepcion.getRvarCelula441());
            if (riExcepcion.getRvarCelula541().signum() == 1)
                celulasExcepcion.add(riExcepcion.getRvarCelula541());
        }
        
        return celulasExcepcion;
    }
}
