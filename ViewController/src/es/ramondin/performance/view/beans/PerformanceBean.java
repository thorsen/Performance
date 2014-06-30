package es.ramondin.performance.view.beans;

import es.ramondin.compdec.grafico.view.util.GraficoUtil;
import es.ramondin.performance.modelOracle.views.MarkerVOImpl;
import es.ramondin.performance.modelOracle.views.PerformanceVOImpl;
import es.ramondin.utilidades.JSFUtils;

import java.math.BigDecimal;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.ResourceBundle;

import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;
import javax.faces.model.SelectItem;

import oracle.adf.model.BindingContext;
import oracle.adf.view.rich.component.rich.input.RichSelectBooleanCheckbox;
import oracle.adf.view.rich.component.rich.input.RichSelectOneChoice;

import oracle.binding.BindingContainer;
import oracle.binding.OperationBinding;

import oracle.jbo.domain.Number;
import oracle.jbo.uicli.binding.JUCtrlListBinding;


public class PerformanceBean {
    private static final Integer ANO_RUPTURA_MAX = (new GregorianCalendar()).get(GregorianCalendar.YEAR);
    
    private Integer anoRuptura = ANO_RUPTURA_MAX;
    private Integer anosHistorico = 5;
    private String tituloGrafico;
    private String subtituloGrafico;
    private String textoPie;
    private Integer sentidoMejor;
    private String[] textoMarkers;
    private Double[] valorMarkers;
    private String[] colorMarkers;
    private Boolean[] displayMarkers;
    private String tipoMarkerSeriesSet;
    private String[] tipoMarkerSeries;
    private List listaCelulasAgrup;
    private RichSelectOneChoice socCelulas;
    private RichSelectOneChoice socCelulasAgrup;
    private Boolean mostrarCelulasAgrup;
    private Boolean romperPorTurno;
    private String rutaDestino;
    private String rutaFicheroXSL;
    private String convNumberType;
    private String convNumberCurrencySymbol;
    private Integer maxFractionDigits;
    private String tituloEjeY1;
    private String textoEsquinaSupIzq;
    private String textoEsquinaSupDer;
    private String fileName;
    private RichSelectBooleanCheckbox cbMesVencido;
    private RichSelectBooleanCheckbox cbMismoPeriodo;
    private RichSelectBooleanCheckbox cbVerMensual;
    private Integer[] turnosSelIndexes;
    private oracle.adfinternal.view.faces.dvt.model.binding.graph.ActiveGraphDataModel graphModel;

    private static final String RUTA_FICHEROS_PDF = "DIR_FICHEROS_PDF";
    private static final String RUTA_FICHEROS_XSL = "DIR_FICHEROS_XSL";
    private static final String FICHERO_XSL = "graficoA4Landscape.xsl";

    public PerformanceBean() {
        OperationBinding operation = this.getBindings().getOperationBinding("getValor");
        operation.getParamsMap().put("param", RUTA_FICHEROS_PDF);
        this.setRutaDestino((String)operation.execute());

        operation.getParamsMap().put("param", RUTA_FICHEROS_XSL);
        this.setRutaFicheroXSL((String)operation.execute() + FICHERO_XSL);
    }

    public BindingContainer getBindings() {
        return BindingContext.getCurrent().getCurrentBindingsEntry();
    }

    public String refrescarGrafico() {
        GregorianCalendar gc = new GregorianCalendar();

        //Establecemos la query
        OperationBinding operation = this.getBindings().getOperationBinding("getQuerySQL");
        String queryOrig = (String)operation.execute();

        operation = this.getBindings().getOperationBinding("getTipoEjeY");
        this.setConvNumberType((String)operation.execute());

        operation = this.getBindings().getOperationBinding("getUnidadesEjeY");
        this.setConvNumberCurrencySymbol((String)operation.execute());

        operation = this.getBindings().getOperationBinding("getMaxDecimales");
        this.setMaxFractionDigits((Number)operation.execute());

        operation = this.getBindings().getOperationBinding("getTituloEjeY");
        this.setTituloEjeY1((String)operation.execute());

        Integer seccion = (Integer)JSFUtils.resolveExpression("#{bindings.MvarSecuen3.attributeValue}");
        String txtSeccion = (String)JSFUtils.resolveExpression("#{bindings.Seccion.label}");
        String descSeccion = (String)JSFUtils.resolveExpression("#{bindings.Seccion.selectedValue}");

        BigDecimal celula = (BigDecimal)JSFUtils.resolveExpression("#{bindings.MvarSecuen13.attributeValue}");
        String txtCelula = (String)JSFUtils.resolveExpression("#{bindings.Celula.label}");
        String descCelula = (String)JSFUtils.resolveExpression("#{bindings.Celula.selectedValue}");

        Integer celulaAgrup = null;
        ArrayList celulasExcepcion = null;

        if (getMostrarCelulasAgrup()) {
            celula = null;
            celulaAgrup = (Integer)this.socCelulasAgrup.getValue();

            SelectItem siAux;
            for (int i = 0; i < this.listaCelulasAgrup.size(); i++) {
                siAux = (SelectItem)this.listaCelulasAgrup.get(i);
                if (celulaAgrup.equals(siAux.getValue())) {
                    descCelula = siAux.getLabel();
                    break;
                }
            }

            operation = this.getBindings().getOperationBinding("getCelulasExcepcion");
            celulasExcepcion = (ArrayList<BigDecimal>)operation.execute();
        }

        Integer anoRupturaParam = this.getAnoRuptura();
        GregorianCalendar gcAux = new GregorianCalendar();

        if (anoRupturaParam == ANO_RUPTURA_MAX) {
            if (this.cbMesVencido.isSelected()) {
                if (gcAux.get(GregorianCalendar.MONTH) == GregorianCalendar.JANUARY) { //Estamos en Enero, el mes vencido es diciembre del año anterior
                    gcAux.roll(GregorianCalendar.YEAR, -1);
                }

                gcAux.roll(GregorianCalendar.MONTH, -1);
                gcAux.roll(GregorianCalendar.DAY_OF_MONTH, gcAux.getActualMaximum(GregorianCalendar.DAY_OF_MONTH) - gcAux.get(GregorianCalendar.DAY_OF_MONTH));
            }
        } else {
            gcAux.roll(GregorianCalendar.YEAR, anoRupturaParam - gcAux.get(GregorianCalendar.YEAR));
            gcAux.roll(GregorianCalendar.MONTH, GregorianCalendar.DECEMBER - gcAux.get(GregorianCalendar.MONTH));
            gcAux.roll(GregorianCalendar.DAY_OF_MONTH, gcAux.getActualMaximum(GregorianCalendar.DAY_OF_MONTH) - gcAux.get(GregorianCalendar.DAY_OF_MONTH));
        }

        Integer hastaFecha = Integer.parseInt((new SimpleDateFormat("yyyyMMdd")).format(gcAux.getTime()));

        JUCtrlListBinding turnosLista = (JUCtrlListBinding)this.getBindings().get("Turno");
        
        operation = this.getBindings().getOperationBinding("preparaQueryFinal");
        operation.getParamsMap().put("celulaAgrup", celulaAgrup);
        operation.getParamsMap().put("celulasExcepcion", celulasExcepcion);
        operation.execute();

        operation = this.getBindings().getOperationBinding("executeWithParamsEdit");
        operation.getParamsMap().put("seccion", seccion);
        operation.getParamsMap().put("celula", celula);
        operation.getParamsMap().put("anoRuptura", gcAux.get(GregorianCalendar.YEAR));
        operation.getParamsMap().put("anosHistorico", this.getAnosHistorico());
        operation.getParamsMap().put("hastaFecha", hastaFecha);
        operation.getParamsMap().put("desgloseMensual", this.cbVerMensual.isSelected());
        operation.getParamsMap().put("mismoPeriodo", this.cbMismoPeriodo.isSelected());
        operation.getParamsMap().put("romperPorTurno", this.romperPorTurno);
        operation.getParamsMap().put("turnos", turnosLista.getSelectedValues());
        operation.execute();

        //Restablecemos la query
        operation = this.getBindings().getOperationBinding("setQuerySQL");
        operation.getParamsMap().put("querySQL", queryOrig);
        operation.execute();

        //Preparamos el gráfico
        String tipoMarkerSeriesSetAux = null;
        String[] tipoMarkerSeriesAux = null;
        oracle.adfinternal.view.faces.dvt.model.binding.graph.ActiveGraphDataModel graphModelAux = null;
        
        if (this.cbVerMensual.isSelected()) {
            tipoMarkerSeriesSetAux = "MT_DEFAULT";
            tipoMarkerSeriesAux = new String[]{"MT_BAR", "MT_MARKER"};
            
            if (romperPorTurno)
                graphModelAux = (oracle.adfinternal.view.faces.dvt.model.binding.graph.ActiveGraphDataModel)JSFUtils.resolveExpression("#{bindings.PerformanceTurnos.graphModel}");
            else
                graphModelAux = (oracle.adfinternal.view.faces.dvt.model.binding.graph.ActiveGraphDataModel)JSFUtils.resolveExpression("#{bindings.Performance.graphModel}");
        } else {
            tipoMarkerSeriesSetAux = "MT_BAR";
            tipoMarkerSeriesAux = new String[]{"MT_BAR"};
            
            if (romperPorTurno)
                graphModelAux = (oracle.adfinternal.view.faces.dvt.model.binding.graph.ActiveGraphDataModel)JSFUtils.resolveExpression("#{bindings.PerformanceSinMensualTurnos.graphModel}");
            else
                graphModelAux = (oracle.adfinternal.view.faces.dvt.model.binding.graph.ActiveGraphDataModel)JSFUtils.resolveExpression("#{bindings.PerformanceSinMensual.graphModel}");
        }
        
        this.setGraphModel(graphModelAux);
        this.setTipoMarkerSeriesSet(tipoMarkerSeriesSetAux);
        this.setTipoMarkerSeries(tipoMarkerSeriesAux);
                
        operation = this.getBindings().getOperationBinding("getSentidoMejor");
        this.setSentidoMejor((Number)operation.execute());

        operation = this.getBindings().getOperationBinding("cargaMarkers");
        operation.getParamsMap().put("idSeccion", seccion);
        operation.getParamsMap().put("idCelula", celula != null ? celula : celulaAgrup);
        operation.getParamsMap().put("desdeFecha", null);
        operation.getParamsMap().put("sentidoMejor", this.getSentidoMejor());
        operation.execute();
        
        operation = this.getBindings().getOperationBinding("getTextoMarkers");
        String[] textoMarkersAux = (String[])operation.execute();
        
        operation = this.getBindings().getOperationBinding("getValorMarkers");
        Double[] valorMarkersAux = (Double[])operation.execute();
        
        operation = this.getBindings().getOperationBinding("getColorMarkers");
        String[] colorMarkersAux = (String[])operation.execute();
        
        //Markers especiales
        //Establecemos la query
        operation = this.getBindings().getOperationBinding("cargaMarkersEsp");
        operation.getParamsMap().put("seccion", seccion);
        operation.getParamsMap().put("celula", celula);
        operation.getParamsMap().put("celulaAgrup", celulaAgrup);
        operation.getParamsMap().put("celulasExcepcion", celulasExcepcion);
        operation.getParamsMap().put("anoRuptura", gcAux.get(GregorianCalendar.YEAR));
        operation.getParamsMap().put("anosHistorico", 10);
        operation.getParamsMap().put("hastaFecha", hastaFecha);
        operation.getParamsMap().put("desgloseMensual", this.cbVerMensual.isSelected());
        operation.getParamsMap().put("mismoPeriodo", this.cbMismoPeriodo.isSelected());
        operation.getParamsMap().put("romperPorTurno", this.romperPorTurno);
        operation.getParamsMap().put("turnos", turnosLista.getSelectedValues());
        operation.execute();
        
        operation = this.getBindings().getOperationBinding("getTextoMarkersEsp");
        String[] textoMarkersEsp = (String[])operation.execute();
        
        operation = this.getBindings().getOperationBinding("getValorMarkersEsp");
        Double[] valorMarkersEsp = (Double[])operation.execute();
        
        operation = this.getBindings().getOperationBinding("getColorMarkersEsp");
        String[] colorMarkersEsp = (String[])operation.execute();
        
        int numValorMarkersAux = valorMarkersAux != null ? valorMarkersAux.length : 0;
        int numValorMarkersEsp =  valorMarkersEsp != null ? valorMarkersEsp.length : 0;
        Double[] valorMarkersFinal = null;
        String[] textoMarkersFinal = null;
        String[] colorMarkersFinal = null;
        
        if (numValorMarkersAux + numValorMarkersEsp > 0) {
            String textoAux = null;
            String colorAux = null;
            int pos = -1;
            
            valorMarkersFinal = new Double[numValorMarkersAux + numValorMarkersEsp];
            textoMarkersFinal = new String[numValorMarkersAux + numValorMarkersEsp];
            colorMarkersFinal = new String[numValorMarkersAux + numValorMarkersEsp];
            
            for (int i = 0; i < valorMarkersFinal.length; i++) {
                if (i < numValorMarkersAux) {
                    valorMarkersFinal[i] = valorMarkersAux[i];
                    
                    textoAux = textoMarkersAux[i];
                    if (textoAux != null && textoAux.contains(MarkerVOImpl.TXT_TEXTO_DEF)) {
                        pos = -1;
                        try {
                            pos = Integer.parseInt(textoAux.substring(MarkerVOImpl.TXT_TEXTO_DEF.length()));
                        } catch (NumberFormatException ex) {
                            ex.getMessage();    
                        }
                        
                        if (pos >= 0  && pos < GraficoUtil.TEXTO_MARKERS_DEF.length)
                            textoAux = GraficoUtil.TEXTO_MARKERS_DEF[pos];
                        else
                            textoAux = null;
                    }
                    textoMarkersFinal[i] = textoAux;
                    
                    colorAux = colorMarkersAux[i];
                    if (colorAux != null && colorAux.contains(MarkerVOImpl.TXT_COLOR_DEF)) {
                        pos = -1;
                        try {
                            pos = Integer.parseInt(colorAux.substring(MarkerVOImpl.TXT_COLOR_DEF.length()));
                        } catch (NumberFormatException ex) {
                            ex.getMessage();    
                        }
                        
                        if (pos >= 0  && pos < GraficoUtil.COLOR_MARKERS_DEF.length)
                            colorAux = GraficoUtil.COLOR_MARKERS_DEF[pos];
                        else
                            colorAux = null;
                    }
                    colorMarkersFinal[i] = colorAux;
                } else {
                    valorMarkersFinal[i] = valorMarkersEsp[i - numValorMarkersAux];
                    textoMarkersFinal[i] = textoMarkersEsp[i - numValorMarkersAux];
                    colorMarkersFinal[i] = colorMarkersEsp[i - numValorMarkersAux];
                }
            }
        }
        this.setValorMarkers(valorMarkersFinal);
        this.setTextoMarkers(textoMarkersFinal);
        this.setColorMarkers(colorMarkersFinal);

        this.setTituloGrafico((String)JSFUtils.resolveExpression("#{bindings.MgraTitulo.attributeValue}"));
        this.setSubtituloGrafico((String)JSFUtils.resolveExpression("#{bindings.MgraSubtitulo.attributeValue}"));

        String idTipoGraIdGra = (String)JSFUtils.resolveExpression("#{bindings.IdTipoGraIdGraTrans.inputValue}");

        this.setTextoEsquinaSupIzq(idTipoGraIdGra + " | " + txtSeccion + ": " + descSeccion + " " + txtCelula + ": " + descCelula);

        this.setTextoEsquinaSupDer(DateFormat.getDateInstance(DateFormat.SHORT).format(gc.getTime()) + " " +
                                   DateFormat.getTimeInstance(DateFormat.MEDIUM).format(gc.getTime()));

        this.setFileName(idTipoGraIdGra + "_" + (new SimpleDateFormat("yyyyMMdd_HHmmss")).format(gc.getTime()));
        
        return null;
    }
    
    public Boolean getVerMensual() {
        return this.cbVerMensual.isSelected();
    }

    public Integer getFormatoSalida() {
        return GraficoUtil.FORMATO_SALIDA_PDF;
    }

    public Integer getCuentaCelulas() {
        Object[] celulaItems = (Object[])JSFUtils.resolveExpression("#{bindings.Celula.allRowsInRange}");

        return celulaItems.length;
    }

    public Boolean getMostrarCheckCelulasAgrup() {
        BigDecimal celula = (BigDecimal)JSFUtils.resolveExpression("#{bindings.MvarSecuen13.attributeValue}");

        this.setMostrarCelulasAgrup(false);

        return (celula != null && getCuentaCelulas() != 0);
    }

    public void setMostrarCelulasAgrup(Boolean mostrarCelulasAgrup) {
        this.mostrarCelulasAgrup = mostrarCelulasAgrup;
    }

    public Boolean getMostrarCelulasAgrup() {
        return this.mostrarCelulasAgrup;
    }

    public void setTituloGrafico(String tituloGrafico) {
        this.tituloGrafico = tituloGrafico;
    }

    public String getTituloGrafico() {
        return tituloGrafico;
    }

    public void setSubtituloGrafico(String subtituloGrafico) {
        this.subtituloGrafico = subtituloGrafico;
    }

    public String getSubtituloGrafico() {
        return subtituloGrafico;
    }

    public void setTextoPie(String textoPie) {
        this.textoPie = textoPie;
    }

    public String getTextoPie() {
        return textoPie;
    }

    public void setTextoMarkers(String[] textoMarkers) {
        this.textoMarkers = textoMarkers;
    }

    public String[] getTextoMarkers() {
        return textoMarkers;
    }

    public void setValorMarkers(Double[] valorMarkers) {
        this.valorMarkers = valorMarkers;
    }

    public Double[] getValorMarkers() {
        return valorMarkers;
    }

    public void setColorMarkers(String[] colorMarkers) {
        this.colorMarkers = colorMarkers;
    }

    public String[] getColorMarkers() {
        return colorMarkers;
    }

    public void setDisplayMarkers(Boolean[] displayMarkers) {
        this.displayMarkers = displayMarkers;
    }

    public Boolean[] getDisplayMarkers() {
        return displayMarkers;
    }

    public void setSentidoMejor(Number sentidoMejor) {
        this.sentidoMejor = sentidoMejor == null ? null : sentidoMejor.intValue();
    }

    public void setSentidoMejor(Integer sentidoMejor) {
        this.sentidoMejor = sentidoMejor;
    }

    public Integer getSentidoMejor() {
        return sentidoMejor;
    }

    public List getListaCelulasAgrup() {
        FacesContext fctx = FacesContext.getCurrentInstance();
        ResourceBundle rb = ResourceBundle.getBundle("es.ramondin.performance.view.resources.ViewControllerBundle", fctx.getViewRoot().getLocale());

        OperationBinding operation = this.getBindings().getOperationBinding("hayExcepciones");

        this.listaCelulasAgrup = new ArrayList();

        if (getMostrarCelulasAgrup() && (Boolean)operation.execute()) {
            this.listaCelulasAgrup.add(new SelectItem(PerformanceVOImpl.INCLUIR_EXCEPCIONES, rb.getString("TODAS_INCLUYENDO_EXCEPCIONES")));
            this.listaCelulasAgrup.add(new SelectItem(PerformanceVOImpl.NO_INCLUIR_EXCEPCIONES, rb.getString("TODAS_NO_INCL_EXCEPCIONES")));
            this.listaCelulasAgrup.add(new SelectItem(PerformanceVOImpl.SOLO_EXCEPCIONES, rb.getString("SOLO_EXCEPCIONES")));
        } else {
            this.listaCelulasAgrup.add(new SelectItem(PerformanceVOImpl.INCLUIR_EXCEPCIONES, rb.getString("TODAS")));
        }

        this.socCelulasAgrup.setValue(PerformanceVOImpl.INCLUIR_EXCEPCIONES);

        return this.listaCelulasAgrup;
    }

    public void setSocCelulas(RichSelectOneChoice socCelulas) {
        this.socCelulas = socCelulas;
    }

    public RichSelectOneChoice getSocCelulas() {
        return socCelulas;
    }

    public void setSocCelulasAgrup(RichSelectOneChoice socCelulasAgrup) {
        this.socCelulasAgrup = socCelulasAgrup;
    }

    public RichSelectOneChoice getSocCelulasAgrup() {
        return socCelulasAgrup;
    }

    public void setRutaFicheroXSL(String rutaFicheroXSL) {
        this.rutaFicheroXSL = rutaFicheroXSL;
    }

    public String getRutaFicheroXSL() {
        return rutaFicheroXSL;
    }

    public void valueChangeListenerSeccion(ValueChangeEvent valueChangeEvent) {
        this.setMostrarCelulasAgrup(false);
    }

    public void setConvNumberType(String convNumberType) {
        this.convNumberType = convNumberType;
    }

    public String getConvNumberType() {
        return convNumberType;
    }

    public void setConvNumberCurrencySymbol(String convNumberCurrencySymbol) {
        this.convNumberCurrencySymbol = convNumberCurrencySymbol;
    }

    public String getConvNumberCurrencySymbol() {
        return convNumberCurrencySymbol;
    }

    public void setAnosHistorico(Integer anosHistorico) {
        this.anosHistorico = anosHistorico;
    }

    public Integer getAnosHistorico() {
        return anosHistorico;
    }

    public void setAnoRuptura(Integer anoRuptura) {
        this.anoRuptura = anoRuptura;
    }

    public Integer getAnoRuptura() {
        return anoRuptura;
    }

    public Integer getAnoRupturaMax() {
        return this.ANO_RUPTURA_MAX;
    }

    public void setTextoEsquinaSupIzq(String textoEsquinaSupIzq) {
        this.textoEsquinaSupIzq = textoEsquinaSupIzq;
    }

    public String getTextoEsquinaSupIzq() {
        return textoEsquinaSupIzq;
    }

    public void setTextoEsquinaSupDer(String textoEsquinaSupDer) {
        this.textoEsquinaSupDer = textoEsquinaSupDer;
    }

    public String getTextoEsquinaSupDer() {
        return textoEsquinaSupDer;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFileName() {
        return fileName;
    }

    public void setRutaDestino(String rutaDestino) {
        this.rutaDestino = rutaDestino;
    }

    public String getRutaDestino() {
        return rutaDestino;
    }

    public void setCbMesVencido(RichSelectBooleanCheckbox rcbMesVencido) {
        this.cbMesVencido = rcbMesVencido;
    }

    public RichSelectBooleanCheckbox getCbMesVencido() {
        return cbMesVencido;
    }

    public void setMaxFractionDigits(Number maxFractionDigits) {
        this.maxFractionDigits = maxFractionDigits == null ? null : maxFractionDigits.intValue();
    }

    public Integer getMaxFractionDigits() {
        return maxFractionDigits;
    }

    public void setTituloEjeY1(String tituloEjeY1) {
        this.tituloEjeY1 = tituloEjeY1;
    }

    public String getTituloEjeY1() {
        return tituloEjeY1;
    }

    public void setRomperPorTurno(Boolean romperPorTurno) {
        this.romperPorTurno = romperPorTurno;
    }

    public Boolean getRomperPorTurno() {
        return romperPorTurno;
    }

    public void setCbMismoPeriodo(RichSelectBooleanCheckbox cbMismoPeriodo) {
        this.cbMismoPeriodo = cbMismoPeriodo;
    }

    public RichSelectBooleanCheckbox getCbMismoPeriodo() {
        return cbMismoPeriodo;
    }

    public void setTurnosSelIndexes(Integer[] turnosSelIndexes) {
        this.turnosSelIndexes = turnosSelIndexes;
        
        JUCtrlListBinding turnosLista = (JUCtrlListBinding)this.getBindings().get("Turno");
        if (turnosSelIndexes != null) {
            int[] turnosSelIndexesInt = new int[turnosSelIndexes.length];
            for (int i = 0; i < turnosSelIndexes.length; i++) {
                turnosSelIndexesInt[i] = turnosSelIndexes[i];
            }

            turnosLista.setSelectedIndices(turnosSelIndexesInt);
        } else
            turnosLista.setSelectedIndices(null);
    }

    public Integer[] getTurnosSelIndexes() {
        if (turnosSelIndexes == null) {
            OperationBinding operation = this.getBindings().getOperationBinding("getIndicesTurnos");
            turnosSelIndexes = (Integer[])operation.execute();
        }
        
        return turnosSelIndexes;
    }

    public void setCbVerMensual(RichSelectBooleanCheckbox cbVerMensual) {
        this.cbVerMensual = cbVerMensual;
    }

    public RichSelectBooleanCheckbox getCbVerMensual() {
        return cbVerMensual;
    }

    public void setTipoMarkerSeriesSet(String tipoMarkerSeriesSet) {
        this.tipoMarkerSeriesSet = tipoMarkerSeriesSet;
    }

    public String getTipoMarkerSeriesSet() {
        return tipoMarkerSeriesSet;
    }

    public void setGraphModel(oracle.adfinternal.view.faces.dvt.model.binding.graph.ActiveGraphDataModel graphModel) {
        this.graphModel = graphModel;
    }

    public oracle.adfinternal.view.faces.dvt.model.binding.graph.ActiveGraphDataModel getGraphModel() {
        return graphModel;
    }

    public void setTipoMarkerSeries(String[] tipoMarkerSeries) {
        this.tipoMarkerSeries = tipoMarkerSeries;
    }

    public String[] getTipoMarkerSeries() {
        return tipoMarkerSeries;
    }
}
