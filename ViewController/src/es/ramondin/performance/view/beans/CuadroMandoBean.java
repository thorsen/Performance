package es.ramondin.performance.view.beans;

import oracle.adf.view.rich.component.rich.data.RichTable;

public class CuadroMandoBean {
    private RichTable tabla;

    public CuadroMandoBean() {
    }
    
    public String getSummaryTabla() {
        return this.tabla.getSummary();
    }

    public void setTabla(RichTable tabla) {
        this.tabla = tabla;
    }

    public RichTable getTabla() {
        return tabla;
    }
}
