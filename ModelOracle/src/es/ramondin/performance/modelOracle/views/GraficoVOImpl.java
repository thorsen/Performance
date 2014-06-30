package es.ramondin.performance.modelOracle.views;

import es.ramondin.performance.modelOracle.views.common.GraficoVO;

import oracle.jbo.Key;
import oracle.jbo.domain.Number;
import oracle.jbo.server.ViewObjectImpl;
// ---------------------------------------------------------------------
// ---    File generated by Oracle ADF Business Components Design Time.
// ---    Thu Apr 24 15:47:55 CEST 2014
// ---    Custom code may be added to this class.
// ---    Warning: Do not modify method signatures of generated methods.
// ---------------------------------------------------------------------
public class GraficoVOImpl extends ViewObjectImpl implements GraficoVO {
    /**
     * This is the default constructor (do not remove).
     */
    public GraficoVOImpl() {
    }

    public String getTipoEjeY() {
        return ((GraficoVORowImpl)this.getCurrentRow()).getMgraTipoEjey();
    }

    public String getUnidadesEjeY() {
        return ((GraficoVORowImpl)this.getCurrentRow()).getMgraUdsEjey();
    }
    
    public Number getMaxDecimales() {
        return ((GraficoVORowImpl)this.getCurrentRow()).getMgraMaxDecimales();
    }
    
    public String getTituloEjeY() {
        return ((GraficoVORowImpl)this.getCurrentRow()).getMgraTituloEjey();
    }
    
    public Number getSentidoMejor() {
        return ((GraficoVORowImpl)this.getCurrentRow()).getMgraSentidoMejor();
    }

    public void setCurrentRowGrafico(Key key) {
        this.setCurrentRow(this.getRow(key));
    }
}