package es.ramondin.performance.modelOracle.views;


import java.math.BigDecimal;

import java.util.ArrayList;

import oracle.jbo.ApplicationModule;
import oracle.jbo.RowSetIterator;
import oracle.jbo.domain.Number;
import oracle.jbo.server.ViewObjectImpl;


// ---------------------------------------------------------------------
// ---    File generated by Oracle ADF Business Components Design Time.
// ---    Wed Jun 18 11:12:25 CEST 2014
// ---    Custom code may be added to this class.
// ---    Warning: Do not modify method signatures of generated methods.
// ---------------------------------------------------------------------
public class MarkerEspecialQueryVOImpl extends ViewObjectImpl {
    private static final String TXT_CONSULTA_BD = "SELECT 'MMES_TEXTO_MARKER' AS MMES_TEXTO_MARKER, 0 AS MMES_VALOR_MARKER FROM DUAL";
    private static final String TXT_PERFORMANCE_VO_BD = "PERFORMANCE_VO";    
    /**
     * This is the default constructor (do not remove).
     */
    public MarkerEspecialQueryVOImpl() {
    }
    
    public void executeWithParamsEdit(BigDecimal seccion, BigDecimal celula, Integer anoRuptura, Integer anosHistorico, Integer hastaFecha, Boolean desgloseMensual, 
                                      Boolean mismoPeriodo, Boolean romperPorTurno, String[] turnos) {
        Number seccionNumber = seccion == null ? null : new Number(seccion.intValue());
        Number celulaNumber = celula == null ? null : new Number(celula.intValue());
        String turnoTxt = null;
        int turnosLength = turnos != null ? turnos.length : 0;
        if (turnosLength > 0) {
            turnoTxt = "";
            for (int i = 0; i < turnosLength; i++) {
                turnoTxt += turnos[i];
            }
        }

        this.setPerfEmpresaBV(new Number(1));
        this.setPerfSeccionBV(seccionNumber);
        this.setPerfCelulaBV(celulaNumber);
        this.setAnoRupturaBV(new Number(anoRuptura.intValue()));
        this.setAnosHistoricoBV(new Number(anosHistorico.intValue()));
        this.setHastaFechaBV(new Number(hastaFecha.intValue()));
        this.setRomperPorTurnoBV(romperPorTurno ? 1 : 0);
        this.setPerfTurnosBV(turnoTxt);
        this.setVisualizarMensualBV(desgloseMensual ? 1 : 0);
        this.setMismoPeriodoBV(mismoPeriodo ? 1 : 0);

        this.executeQuery();
    }
    
    public void executeWithParamsEdit(BigDecimal seccion, BigDecimal celula, Integer anoRuptura, Integer anosHistorico, Integer hastaFecha) {
        executeWithParamsEdit(seccion, celula, anoRuptura, anosHistorico, hastaFecha, true, false, false, null);
    }
    
    public void setQuerySQL(String querySQL) {
        this.setQuery(querySQL);
    }

    public String getQuerySQL() {
        return this.getQuery();
    }
    
    public void preparaQueryFinal(Integer celulaAgrup, ArrayList celulasExcepcion, String consultaSQL) {
        if (consultaSQL != null) {
            if (consultaSQL.contains(TXT_PERFORMANCE_VO_BD)) {
                ApplicationModule am = this.getApplicationModule();
                
                PerformanceVOImpl performanceImp = (PerformanceVOImpl)am.findViewObject("Performance");
                String queryOrig = performanceImp.getQuerySQL();
                
                performanceImp.preparaQueryFinal(celulaAgrup, celulasExcepcion);
                
                consultaSQL = consultaSQL.replace(TXT_PERFORMANCE_VO_BD, "(" + performanceImp.getQuerySQL() + ")");
                
                performanceImp.setQuerySQL(queryOrig);
            }
            
            setCampoConsultaBD(consultaSQL);
        }
    }
    
    private void setCampoConsultaBD(String campoConsultaBD) {
        this.setQuery(this.getQuery().replace(TXT_CONSULTA_BD, campoConsultaBD));
    }

    public String getTextoMarkerEsp() {
        String res = null;
        
        RowSetIterator rsiMarkerEsp = this.getRowSetIterator();
        
        if (rsiMarkerEsp.getRowCount() != 0) {
            MarkerEspecialQueryVORowImpl rowMEQuery = (MarkerEspecialQueryVORowImpl)rsiMarkerEsp.first();
            
            if (rowMEQuery != null) {
                res = rowMEQuery.getMmesTextoMarker();
            }
        }
        
        return res;
    }
    
    public Double getValorMarkerEsp() {
        Double res = null;
        
        RowSetIterator rsiMarkerEsp = this.getRowSetIterator();
        
        if (rsiMarkerEsp.getRowCount() != 0) {
            MarkerEspecialQueryVORowImpl rowMEQuery = (MarkerEspecialQueryVORowImpl)rsiMarkerEsp.first();
            
            if (rowMEQuery != null) {
                res = rowMEQuery.getMmesValorMarker() != null ? rowMEQuery.getMmesValorMarker().doubleValue() : null;
            }
        }
        
        return res;
    }

    /**
     * Returns the bind variable value for PerfEmpresaBV.
     * @return bind variable value for PerfEmpresaBV
     */
    public Number getPerfEmpresaBV() {
        return (Number)getNamedWhereClauseParam("PerfEmpresaBV");
    }

    /**
     * Sets <code>value</code> for bind variable PerfEmpresaBV.
     * @param value value to bind as PerfEmpresaBV
     */
    public void setPerfEmpresaBV(Number value) {
        setNamedWhereClauseParam("PerfEmpresaBV", value);
    }

    /**
     * Returns the bind variable value for PerfSeccionBV.
     * @return bind variable value for PerfSeccionBV
     */
    public Number getPerfSeccionBV() {
        return (Number)getNamedWhereClauseParam("PerfSeccionBV");
    }

    /**
     * Sets <code>value</code> for bind variable PerfSeccionBV.
     * @param value value to bind as PerfSeccionBV
     */
    public void setPerfSeccionBV(Number value) {
        setNamedWhereClauseParam("PerfSeccionBV", value);
    }

    /**
     * Returns the bind variable value for PerfCelulaBV.
     * @return bind variable value for PerfCelulaBV
     */
    public Number getPerfCelulaBV() {
        return (Number)getNamedWhereClauseParam("PerfCelulaBV");
    }

    /**
     * Sets <code>value</code> for bind variable PerfCelulaBV.
     * @param value value to bind as PerfCelulaBV
     */
    public void setPerfCelulaBV(Number value) {
        setNamedWhereClauseParam("PerfCelulaBV", value);
    }

    /**
     * Returns the bind variable value for AnoRupturaBV.
     * @return bind variable value for AnoRupturaBV
     */
    public Number getAnoRupturaBV() {
        return (Number)getNamedWhereClauseParam("AnoRupturaBV");
    }

    /**
     * Sets <code>value</code> for bind variable AnoRupturaBV.
     * @param value value to bind as AnoRupturaBV
     */
    public void setAnoRupturaBV(Number value) {
        setNamedWhereClauseParam("AnoRupturaBV", value);
    }

    /**
     * Returns the bind variable value for AnosHistoricoBV.
     * @return bind variable value for AnosHistoricoBV
     */
    public Number getAnosHistoricoBV() {
        return (Number)getNamedWhereClauseParam("AnosHistoricoBV");
    }

    /**
     * Sets <code>value</code> for bind variable AnosHistoricoBV.
     * @param value value to bind as AnosHistoricoBV
     */
    public void setAnosHistoricoBV(Number value) {
        setNamedWhereClauseParam("AnosHistoricoBV", value);
    }

    /**
     * Returns the bind variable value for HastaFechaBV.
     * @return bind variable value for HastaFechaBV
     */
    public Number getHastaFechaBV() {
        return (Number)getNamedWhereClauseParam("HastaFechaBV");
    }

    /**
     * Sets <code>value</code> for bind variable HastaFechaBV.
     * @param value value to bind as HastaFechaBV
     */
    public void setHastaFechaBV(Number value) {
        setNamedWhereClauseParam("HastaFechaBV", value);
    }

    /**
     * Returns the bind variable value for RomperPorTurnoBV.
     * @return bind variable value for RomperPorTurnoBV
     */
    public Integer getRomperPorTurnoBV() {
        return (Integer)getNamedWhereClauseParam("RomperPorTurnoBV");
    }

    /**
     * Sets <code>value</code> for bind variable RomperPorTurnoBV.
     * @param value value to bind as RomperPorTurnoBV
     */
    public void setRomperPorTurnoBV(Integer value) {
        setNamedWhereClauseParam("RomperPorTurnoBV", value);
    }

    /**
     * Returns the bind variable value for PerfTurnosBV.
     * @return bind variable value for PerfTurnosBV
     */
    public String getPerfTurnosBV() {
        return (String)getNamedWhereClauseParam("PerfTurnosBV");
    }

    /**
     * Sets <code>value</code> for bind variable PerfTurnosBV.
     * @param value value to bind as PerfTurnosBV
     */
    public void setPerfTurnosBV(String value) {
        setNamedWhereClauseParam("PerfTurnosBV", value);
    }

    /**
     * Returns the bind variable value for VisualizarMensualBV.
     * @return bind variable value for VisualizarMensualBV
     */
    public Integer getVisualizarMensualBV() {
        return (Integer)getNamedWhereClauseParam("VisualizarMensualBV");
    }

    /**
     * Sets <code>value</code> for bind variable VisualizarMensualBV.
     * @param value value to bind as VisualizarMensualBV
     */
    public void setVisualizarMensualBV(Integer value) {
        setNamedWhereClauseParam("VisualizarMensualBV", value);
    }

    /**
     * Returns the bind variable value for MismoPeriodoBV.
     * @return bind variable value for MismoPeriodoBV
     */
    public Integer getMismoPeriodoBV() {
        return (Integer)getNamedWhereClauseParam("MismoPeriodoBV");
    }

    /**
     * Sets <code>value</code> for bind variable MismoPeriodoBV.
     * @param value value to bind as MismoPeriodoBV
     */
    public void setMismoPeriodoBV(Integer value) {
        setNamedWhereClauseParam("MismoPeriodoBV", value);
    }
}
