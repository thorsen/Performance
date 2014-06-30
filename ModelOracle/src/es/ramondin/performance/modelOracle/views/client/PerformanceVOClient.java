package es.ramondin.performance.modelOracle.views.client;

import es.ramondin.performance.modelOracle.views.common.PerformanceVO;

import java.math.BigDecimal;

import java.util.ArrayList;

import oracle.jbo.client.remote.ViewUsageImpl;
import oracle.jbo.domain.Number;
// ---------------------------------------------------------------------
// ---    File generated by Oracle ADF Business Components Design Time.
// ---    Thu Apr 24 16:03:28 CEST 2014
// ---    Custom code may be added to this class.
// ---    Warning: Do not modify method signatures of generated methods.
// ---------------------------------------------------------------------
public class PerformanceVOClient extends ViewUsageImpl implements PerformanceVO {
    /**
     * This is the default constructor (do not remove).
     */
    public PerformanceVOClient() {
    }


    public void executeWithParamsEdit(BigDecimal seccion, BigDecimal celula,
                                      Integer celulaAgrup,
                                      ArrayList celulasExcepcion) {
        Object _ret =
            getApplicationModuleProxy().riInvokeExportedMethod(this,"executeWithParamsEdit",new String [] {"java.math.BigDecimal","java.math.BigDecimal","java.lang.Integer","java.util.ArrayList"},new Object[] {seccion, celula, celulaAgrup, celulasExcepcion});
        return;
    }


    public void setCamposAnualMensualBD(Number idGrafico) {
        Object _ret =
            getApplicationModuleProxy().riInvokeExportedMethod(this,"setCamposAnualMensualBD",new String [] {"oracle.jbo.domain.Number"},new Object[] {idGrafico});
        return;
    }


    public void executeWithParamsEdit(BigDecimal seccion, BigDecimal celula,
                                      Integer celulaAgrup,
                                      ArrayList celulasExcepcion,
                                      Integer anoRuptura,
                                      Integer anosHistorico) {
        Object _ret =
            getApplicationModuleProxy().riInvokeExportedMethod(this,"executeWithParamsEdit",new String [] {"java.math.BigDecimal","java.math.BigDecimal","java.lang.Integer","java.util.ArrayList","java.lang.Integer","java.lang.Integer"},new Object[] {seccion, celula, celulaAgrup, celulasExcepcion, anoRuptura, anosHistorico});
        return;
    }


    public void executeWithParamsEdit(BigDecimal seccion, BigDecimal celula, Integer celulaAgrup, ArrayList celulasExcepcion, Integer anoRuptura,
                                      Integer anosHistorico, Integer hastaFecha, Boolean mismoPeriodo, Boolean romperPorTurno, String turno) {
        Object _ret =
            getApplicationModuleProxy().riInvokeExportedMethod(this,"executeWithParamsEdit",new String [] {"java.math.BigDecimal","java.math.BigDecimal","java.lang.Integer","java.util.ArrayList","java.lang.Integer","java.lang.Integer","java.lang.Integer","java.lang.Boolean","java.lang.Boolean","java.lang.String"},new Object[] {seccion, celula, celulaAgrup, celulasExcepcion, anoRuptura, anosHistorico, hastaFecha, mismoPeriodo, romperPorTurno, turno});
        return;
    }


    public void executeWithParamsEdit(BigDecimal seccion, BigDecimal celula, Integer celulaAgrup, ArrayList celulasExcepcion, Integer anoRuptura,
                                      Integer anosHistorico, Integer hastaFecha, Boolean desgloseMensual, Boolean mismoPeriodo, Boolean romperPorTurno,
                                      String[] turnos) {
        Object _ret =
            getApplicationModuleProxy().riInvokeExportedMethod(this,"executeWithParamsEdit",new String [] {"java.math.BigDecimal","java.math.BigDecimal","java.lang.Integer","java.util.ArrayList","java.lang.Integer","java.lang.Integer","java.lang.Integer","java.lang.Boolean","java.lang.Boolean","java.lang.Boolean","[Ljava.lang.String;"},new Object[] {seccion, celula, celulaAgrup, celulasExcepcion, anoRuptura, anosHistorico, hastaFecha, desgloseMensual, mismoPeriodo, romperPorTurno, turnos});
        return;
    }

    public void executeWithParamsEdit(BigDecimal seccion, BigDecimal celula, Integer celulaAgrup, ArrayList celulasExcepcion, Integer anoRuptura,
                                      Integer anosHistorico, Integer hastaFecha) {
        Object _ret =
            getApplicationModuleProxy().riInvokeExportedMethod(this,"executeWithParamsEdit",new String [] {"java.math.BigDecimal","java.math.BigDecimal","java.lang.Integer","java.util.ArrayList","java.lang.Integer","java.lang.Integer","java.lang.Integer"},new Object[] {seccion, celula, celulaAgrup, celulasExcepcion, anoRuptura, anosHistorico, hastaFecha});
        return;
    }

    public void setQuerySQL(String querySQL) {
        Object _ret = getApplicationModuleProxy().riInvokeExportedMethod(this,"setQuerySQL",new String [] {"java.lang.String"},new Object[] {querySQL});
        return;
    }

    public String getQuerySQL() {
        Object _ret = getApplicationModuleProxy().riInvokeExportedMethod(this,"getQuerySQL",null,null);
        return (String)_ret;
    }

    public void preparaQueryFinal(Integer celulaAgrup, ArrayList celulasExcepcion) {
        Object _ret =
            getApplicationModuleProxy().riInvokeExportedMethod(this,"preparaQueryFinal",new String [] {"java.lang.Integer","java.util.ArrayList"},new Object[] {celulaAgrup, celulasExcepcion});
        return;
    }

    public void executeWithParamsEdit(BigDecimal seccion, BigDecimal celula, Integer anoRuptura, Integer anosHistorico, Integer hastaFecha,
                                      Boolean desgloseMensual, Boolean mismoPeriodo, Boolean romperPorTurno, String[] turnos) {
        Object _ret =
            getApplicationModuleProxy().riInvokeExportedMethod(this,"executeWithParamsEdit",new String [] {"java.math.BigDecimal","java.math.BigDecimal","java.lang.Integer","java.lang.Integer","java.lang.Integer","java.lang.Boolean","java.lang.Boolean","java.lang.Boolean","[Ljava.lang.String;"},new Object[] {seccion, celula, anoRuptura, anosHistorico, hastaFecha, desgloseMensual, mismoPeriodo, romperPorTurno, turnos});
        return;
    }

    public void executeWithParamsEdit(BigDecimal seccion, BigDecimal celula, Integer anoRuptura, Integer anosHistorico, Integer hastaFecha) {
        Object _ret =
            getApplicationModuleProxy().riInvokeExportedMethod(this,"executeWithParamsEdit",new String [] {"java.math.BigDecimal","java.math.BigDecimal","java.lang.Integer","java.lang.Integer","java.lang.Integer"},new Object[] {seccion, celula, anoRuptura, anosHistorico, hastaFecha});
        return;
    }
}
