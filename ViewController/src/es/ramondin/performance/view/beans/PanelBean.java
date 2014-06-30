package es.ramondin.performance.view.beans;


import component.CompDecGraficoComponent;


import es.ramondin.Encriptacion;
import es.ramondin.compdec.grafico.view.beans.GraficoBean;
import es.ramondin.compdec.grafico.view.util.GraficoUtil;
import es.ramondin.compdec.mosaico.view.component.CompDecMosaico;
import es.ramondin.performance.modelOracle.views.MarkerVOImpl;
import es.ramondin.performance.modelOracle.views.PanelLinVORowImpl;
import es.ramondin.performance.modelOracle.views.PerformanceVOImpl;
import es.ramondin.utilidades.JSFUtils;

import java.io.File;
import java.io.UnsupportedEncodingException;

import java.math.BigDecimal;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import java.text.SimpleDateFormat;

import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;
import javax.faces.model.SelectItem;

import oracle.adf.model.BindingContext;
import oracle.adf.view.faces.bi.component.graph.UIGraph;
import oracle.adf.view.rich.component.rich.fragment.RichDeclarativeComponent;
import oracle.adf.view.rich.component.rich.input.RichSelectBooleanCheckbox;
import oracle.adf.view.rich.component.rich.input.RichSelectOneChoice;
import oracle.adf.view.rich.component.rich.layout.RichPanelGroupLayout;
import oracle.adf.view.rich.component.rich.output.RichActiveImage;

import oracle.binding.BindingContainer;
import oracle.binding.OperationBinding;

import oracle.jbo.Key;
import oracle.jbo.domain.Number;
import oracle.jbo.uicli.binding.JUCtrlListBinding;


public class PanelBean {
    private Integer anoRuptura = this.ANO_RUPTURA_MAX;
    private Integer anosHistorico = 5;
    private Boolean mostrarCelulasAgrup;
    private Boolean romperPorTurno;
    private List listaCelulasAgrup;
    private RichSelectOneChoice socCelulas;
    private RichSelectOneChoice socCelulasAgrup;
    private String rutaDestino;
    private RichDeclarativeComponent cdGrafico;
    private RichDeclarativeComponent cdMosaico;
    private Integer numFilas;
    private Integer numColumnas;
    private UIComponent[] arrayComponentes;
    private Integer[][] arrayPosiciones;
    private String[] arrayHAlignComps;
    private String[] arrayVAlignComps;
    private Integer[] arrayColSpanComps;
    private Integer[] arrayRowSpanComps;
    private RichSelectBooleanCheckbox cbMesVencido;
    private RichSelectBooleanCheckbox cbMismoPeriodo;
    private RichSelectBooleanCheckbox cbVerMensual;
    private Integer[] turnosSelIndexes;
    private oracle.adfinternal.view.faces.dvt.model.binding.graph.ActiveGraphDataModel graphModel;

    private static final Integer ANO_RUPTURA_MAX = (new GregorianCalendar()).get(GregorianCalendar.YEAR);

    private static final String RUTA_FICHEROS_PDF = "DIR_FICHEROS_PDF";

    private static final String MARCA_BORRAR_PREF = "BORRAR_PANEL_";
    
    private static final Integer ANCHO_CELDA = 200;
    private static final Integer ALTO_CELDA = 150;

    public PanelBean() {
        OperationBinding operation = this.getBindings().getOperationBinding("getValor");
        operation.getParamsMap().put("param", RUTA_FICHEROS_PDF);
        this.setRutaDestino((String)operation.execute());
    }

    public BindingContainer getBindings() {
        return BindingContext.getCurrent().getCurrentBindingsEntry();
    }

    private String getRutaGrafico(String ruta) {
        String res = null;

        try {
            ruta = Encriptacion.encripta(ruta);
            res = "/muestraimagenservlet?archivo=" + ruta + "&modoEncrypt=" + Encriptacion.MODO_JAVA;
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        }

        return res;
    }

    private void borrarFicherosMarcados() {
        File dirRutaFicheros = new File(this.rutaDestino);

        if (dirRutaFicheros.isDirectory()) {
            File ficherosBorrar[] = dirRutaFicheros.listFiles();

            for (int i = 0; i < ficherosBorrar.length; i++) {
                if (ficherosBorrar[i].getName().startsWith(MARCA_BORRAR_PREF) &&
                    ficherosBorrar[i].lastModified() < GregorianCalendar.getInstance().getTimeInMillis() - 5 * 60 * 1000)
                    ficherosBorrar[i].delete();
            }
        }
    }

    private void preparaGrafico(String idTipoGrafico, Number idGrafico, Map<String, Object> map) {
        //Establecemos el gráfico seleccionado
        OperationBinding operation = this.getBindings().getOperationBinding("setCurrentRowTipoGrafico");
        operation.getParamsMap().put("key", new Key(new Object[] { idTipoGrafico }));
        operation.execute();

        operation = this.getBindings().getOperationBinding("setCurrentRowGrafico");
        operation.getParamsMap().put("key", new Key(new Object[] { idTipoGrafico, idGrafico }));
        operation.execute();

        //Establecemos la query
        operation = this.getBindings().getOperationBinding("getQuerySQL");
        String queryOrig = (String)operation.execute();

        operation = this.getBindings().getOperationBinding("getTipoEjeY");
        map.put("convNumberTypeY1", operation.execute());

        operation = this.getBindings().getOperationBinding("getUnidadesEjeY");
        map.put("convNumberCurrencySymbolY1", operation.execute());

        operation = this.getBindings().getOperationBinding("getMaxDecimales");
        map.put("maxFractionDigitsY1", ((Number)operation.execute()).intValue());
        
        operation = this.getBindings().getOperationBinding("getTituloEjeY");
        map.put("tituloEjeY1", operation.execute());

        Integer seccion = (Integer)JSFUtils.resolveExpression("#{bindings.MvarSecuen3.attributeValue}");

        BigDecimal celula = (BigDecimal)JSFUtils.resolveExpression("#{bindings.MvarSecuen13.attributeValue}");

        Integer celulaAgrup = null;
        ArrayList celulasExcepcion = null;

        if (getMostrarCelulasAgrup()) {
            celula = null;
            celulaAgrup = (Integer)this.socCelulasAgrup.getValue();

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
        
        map.put("graphModel", graphModelAux);
        map.put("tipoMarkerSeriesSet", tipoMarkerSeriesSetAux);
        map.put("arrayTipoMarkerSeries", tipoMarkerSeriesAux);
        
        Number sentidoMejor;
        operation = this.getBindings().getOperationBinding("getSentidoMejor");
        sentidoMejor = (Number)operation.execute();
        map.put("sentidoMejor", new Integer(sentidoMejor.intValue()));
        
        operation = this.getBindings().getOperationBinding("cargaMarkers");
        operation.getParamsMap().put("idSeccion", seccion);
        operation.getParamsMap().put("idCelula", celula != null ? celula : celulaAgrup);
        operation.getParamsMap().put("desdeFecha", null);
        operation.getParamsMap().put("sentidoMejor", sentidoMejor);
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
        map.put("arrayValorMarkers", valorMarkersFinal);
        map.put("arrayTextoMarkers", textoMarkersFinal);
        map.put("arrayColorMarkers", colorMarkersFinal);

        map.put("titulo", JSFUtils.resolveExpression("#{bindings.MgraTitulo.attributeValue}"));
        map.put("subtitulo", JSFUtils.resolveExpression("#{bindings.MgraSubtitulo.attributeValue}"));
    }
    
    private RichPanelGroupLayout generaGrafico(CompDecGraficoComponent cdgc, GraficoBean cdgb) {
        RichActiveImage rai = new RichActiveImage();
        
        String ficGenerado =
                GraficoUtil.generarFicheroExportar(FacesContext.getCurrentInstance(), cdgb.getComboGraph(), cdgc.getId(), cdgc.getPrintWidth(), cdgc.getPrintHeight(),
                                                   cdgc.getPrintRutaDestino(), cdgc.getRutaFicheroXSL(), cdgc.getTextoEsquinaSupIzq(),
                                                   cdgc.getTextoEsquinaSupDer(), cdgc.getTextoEsquinaInfIzq(), cdgc.getTextoEsquinaInfDer(),
                                                   GraficoUtil.FORMATO_SALIDA_PNG);
        File fRenombrado = new File(ficGenerado);
        String ficRenombrado = ficGenerado.substring(0, ficGenerado.lastIndexOf(fRenombrado.getName())) + MARCA_BORRAR_PREF + fRenombrado.getName();

        fRenombrado = new File(ficGenerado);
        fRenombrado.renameTo(new File(ficRenombrado));

        rai.setShortDesc(cdgc.getTitulo() + "\n" + cdgc.getSubtitulo());
        rai.setSource(getRutaGrafico(ficRenombrado));
        rai.setInlineStyle("width:" + ANCHO_CELDA + "px;height:" + ALTO_CELDA + "px;");
        
        
        RichPanelGroupLayout rpgl = new RichPanelGroupLayout();
        rpgl.getChildren().add(rai);

        return rpgl;
    }

    public String refrescarPanel() {
        borrarFicherosMarcados();
        
        OperationBinding operation = this.getBindings().getOperationBinding("getNumFilas");
        this.setNumFilas((Integer)operation.execute());
        
        operation = this.getBindings().getOperationBinding("getNumColumnas");
        this.setNumColumnas((Integer)operation.execute());
        
        operation = this.getBindings().getOperationBinding("getLineasPanel");
        ArrayList<PanelLinVORowImpl> lineasPanel = (ArrayList<PanelLinVORowImpl>)operation.execute();
        
        this.arrayComponentes = null;
        this.arrayPosiciones = null;
        this.arrayHAlignComps = null;
        this.arrayVAlignComps = null;
        this.arrayColSpanComps = null;
        this.arrayRowSpanComps = null;
        
        if (lineasPanel != null) {
            ArrayList<UIComponent> alComponentes = new ArrayList<UIComponent>();;
            ArrayList<Integer[]> alPosiciones = new ArrayList<Integer[]>();
            ArrayList<String> alHAlignComps = null;
            ArrayList<String> alVAlignComps = null;
            ArrayList<Integer> alColSpanComps = null;
            ArrayList<Integer> alRowSpanComps = null;
            
            CompDecGraficoComponent cdgc = (CompDecGraficoComponent)this.cdGrafico;
            Map<String, Object> map = cdgc.getAttributes();

            UIGraph comboGraph = null;
            List l = cdgc.getChildren();
            for (int i = 0; i < l.size(); i++) {
                if (l.get(i) instanceof UIGraph) {
                    comboGraph = (UIGraph)l.get(i);
                    break;
                }
            }

            GraficoBean cdgb = new GraficoBean();
            
            for (int i = 0; i < lineasPanel.size(); i++) {
                PanelLinVORowImpl rowPanelLin = lineasPanel.get(i);
                
                preparaGrafico(rowPanelLin.getMpanIdTipoGra(), rowPanelLin.getMpanIdGra(), map);
                cdgb.inicializaExterno(cdgc, comboGraph);
                alComponentes.add(generaGrafico(cdgc, cdgb));
                
                alPosiciones.add(new Integer[]{rowPanelLin.getMpanPosFila().intValue(), rowPanelLin.getMpanPosCol().intValue()});
                
                if (rowPanelLin.getMpanHAlign() != null) {
                    if (alHAlignComps == null)
                        alHAlignComps = new ArrayList<String>();
                        
                    for (int j = alHAlignComps.size(); j < i; j++) {
                        alHAlignComps.add(null);
                    }
                    
                    alHAlignComps.add(rowPanelLin.getMpanHAlign());
                }
                
                if (rowPanelLin.getMpanVAlign() != null) {
                    if (alVAlignComps == null)
                        alVAlignComps = new ArrayList<String>();
                        
                    for (int j = alVAlignComps.size(); j < i; j++) {
                        alVAlignComps.add(null);
                    }
                    
                    alVAlignComps.add(rowPanelLin.getMpanVAlign());
                }
                
                if (rowPanelLin.getMpanColSpan() != null) {
                    if (alColSpanComps == null)
                        alColSpanComps = new ArrayList<Integer>();
                        
                    for (int j = alColSpanComps.size(); j < i; j++) {
                        alColSpanComps.add(null);
                    }
                    
                    alColSpanComps.add(rowPanelLin.getMpanColSpan().intValue());
                }
                
                if (rowPanelLin.getMpanRowSpan() != null) {
                    if (alRowSpanComps == null)
                        alRowSpanComps = new ArrayList<Integer>();
                        
                    for (int j = alRowSpanComps.size(); j < i; j++) {
                        alRowSpanComps.add(null);
                    }
                    
                    alRowSpanComps.add(rowPanelLin.getMpanRowSpan().intValue());
                }
            }
            
            this.setArrayComponentes(alComponentes);
            this.setArrayPosiciones(alPosiciones);
            
            if (alHAlignComps != null) {
                for (int i = alHAlignComps.size(); i < lineasPanel.size(); i++) {
                    alHAlignComps.add(null);
                }
                
                this.setArrayHAlignComps(alHAlignComps);
            }
            
            if (alVAlignComps != null) {
                for (int i = alVAlignComps.size(); i < lineasPanel.size(); i++) {
                    alVAlignComps.add(null);
                }
                
                this.setArrayVAlignComps(alVAlignComps);
            }
            
            if (alColSpanComps != null) {
                for (int i = alColSpanComps.size(); i < lineasPanel.size(); i++) {
                    alColSpanComps.add(null);
                }
                
                this.setArrayColSpanComps(alColSpanComps);
            }
            
            if (alRowSpanComps != null) {
                for (int i = alRowSpanComps.size(); i < lineasPanel.size(); i++) {
                    alRowSpanComps.add(null);
                }
                
                this.setArrayRowSpanComps(alRowSpanComps);
            }
        }
        
        ((CompDecMosaico)this.cdMosaico).refrescarComponente();
        
        return null;
    }

    public Integer getFormatoSalida() {
        return GraficoUtil.FORMATO_SALIDA_PDF;
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

    public void setCbMesVencido(RichSelectBooleanCheckbox rcbMesVencido) {
        this.cbMesVencido = rcbMesVencido;
    }

    public RichSelectBooleanCheckbox getCbMesVencido() {
        return cbMesVencido;
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

    public void valueChangeListenerSeccion(ValueChangeEvent valueChangeEvent) {
        this.setMostrarCelulasAgrup(false);
    }

    public void setMostrarCelulasAgrup(Boolean mostrarCelulasAgrup) {
        this.mostrarCelulasAgrup = mostrarCelulasAgrup;
    }

    public Boolean getMostrarCelulasAgrup() {
        return this.mostrarCelulasAgrup;
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

    public void setRutaDestino(String rutaDestino) {
        this.rutaDestino = rutaDestino;
    }

    public String getRutaDestino() {
        return rutaDestino;
    }

    public void setCdGrafico(RichDeclarativeComponent cdGrafico) {
        this.cdGrafico = cdGrafico;
    }

    public RichDeclarativeComponent getCdGrafico() {
        return cdGrafico;
    }

    public void setArrayComponentes(UIComponent[] arrayComponentes) {
        this.arrayComponentes = arrayComponentes;
    }

    public void setArrayComponentes(ArrayList<UIComponent> alComponentes) {
        this.arrayComponentes = null;

        if (alComponentes != null) {
            int sizeALComp = alComponentes.size();
            this.arrayComponentes = new UIComponent[sizeALComp];

            for (int i = 0; i < sizeALComp; i++) {
                this.arrayComponentes[i] = alComponentes.get(i);
            }
        }
    }

    public UIComponent[] getArrayComponentes() {
        return arrayComponentes;
    }
    
    public void setArrayPosiciones(ArrayList<Integer[]> alPosiciones) {
        this.arrayPosiciones = null;

        if (alPosiciones != null) {
            int sizeALPos = alPosiciones.size();
            this.arrayPosiciones = new Integer[sizeALPos][2];

            for (int i = 0; i < sizeALPos; i++) {
                this.arrayPosiciones[i] = alPosiciones.get(i);
            }
        }
    }

    public void setArrayPosiciones(Integer[][] arrayPosiciones) {
        this.arrayPosiciones = arrayPosiciones;
    }

    public Integer[][] getArrayPosiciones() {
        return arrayPosiciones;
    }
    
    public void setArrayHAlignComps(ArrayList<String> alHAlignComps) {
        this.arrayHAlignComps = null;

        if (alHAlignComps != null) {
            int sizeALHAl = alHAlignComps.size();
            this.arrayHAlignComps = new String[sizeALHAl];

            for (int i = 0; i < sizeALHAl; i++) {
                this.arrayHAlignComps[i] = alHAlignComps.get(i);
            }
        }
    }

    public void setArrayHAlignComps(String[] arrayHAlign) {
        this.arrayHAlignComps = arrayHAlign;
    }

    public String[] getArrayHAlignComps() {
        return arrayHAlignComps;
    }
    
    public void setArrayVAlignComps(ArrayList<String> alVAlignComps) {
        this.arrayVAlignComps = null;

        if (alVAlignComps != null) {
            int sizeALVAl = alVAlignComps.size();
            this.arrayVAlignComps = new String[sizeALVAl];

            for (int i = 0; i < sizeALVAl; i++) {
                this.arrayVAlignComps[i] = alVAlignComps.get(i);
            }
        }
    }

    public void setArrayVAlignComps(String[] arrayVAlign) {
        this.arrayVAlignComps = arrayVAlign;
    }

    public String[] getArrayVAlignComps() {
        return arrayVAlignComps;
    }
    
    public void setArrayColSpanComps(ArrayList<Integer> alColSpanComps) {
        this.arrayColSpanComps = null;

        if (alColSpanComps != null) {
            int sizeALColSp = alColSpanComps.size();
            this.arrayColSpanComps = new Integer[sizeALColSp];

            for (int i = 0; i < sizeALColSp; i++) {
                this.arrayColSpanComps[i] = alColSpanComps.get(i);
            }
        }
    }

    public void setArrayColSpanComps(Integer[] arrayColSpan) {
        this.arrayColSpanComps = arrayColSpan;
    }

    public Integer[] getArrayColSpanComps() {
        return arrayColSpanComps;
    }
    
    public void setArrayRowSpanComps(ArrayList<Integer> alRowSpanComps) {
        this.arrayRowSpanComps = null;

        if (alRowSpanComps != null) {
            int sizeALRowSp = alRowSpanComps.size();
            this.arrayRowSpanComps = new Integer[sizeALRowSp];

            for (int i = 0; i < sizeALRowSp; i++) {
                this.arrayRowSpanComps[i] = alRowSpanComps.get(i);
            }
        }
    }

    public void setArrayRowSpanComps(Integer[] arrayRowSpan) {
        this.arrayRowSpanComps = arrayRowSpan;
    }

    public Integer[] getArrayRowSpanComps() {
        return arrayRowSpanComps;
    }

    public void setCdMosaico(RichDeclarativeComponent cdMosaico) {
        this.cdMosaico = cdMosaico;
    }

    public RichDeclarativeComponent getCdMosaico() {
        return cdMosaico;
    }
    
    public void setNumFilas(Number numFilas) {
        this.numFilas = numFilas != null ? numFilas.intValue() : null;
    }

    public void setNumFilas(Integer numFilas) {
        this.numFilas = numFilas;
    }

    public Integer getNumFilas() {
        return numFilas;
    }
    
    public void setNumColumnas(Number numColumnas) {
        this.numColumnas = numColumnas != null ? numColumnas.intValue() : null;
    }

    public void setNumColumnas(Integer numColumnas) {
        this.numColumnas = numColumnas;
    }

    public Integer getNumColumnas() {
        return numColumnas;
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
    
    public Boolean getVerMensual() {
        return this.cbVerMensual.isSelected();
    }

    public void setCbVerMensual(RichSelectBooleanCheckbox cbVerMensual) {
        this.cbVerMensual = cbVerMensual;
    }

    public RichSelectBooleanCheckbox getCbVerMensual() {
        return cbVerMensual;
    }

    public void setGraphModel(oracle.adfinternal.view.faces.dvt.model.binding.graph.ActiveGraphDataModel graphModel) {
        this.graphModel = graphModel;
    }

    public oracle.adfinternal.view.faces.dvt.model.binding.graph.ActiveGraphDataModel getGraphModel() {
        return graphModel;
    }
    
    public void setRomperPorTurno(Boolean romperPorTurno) {
        this.romperPorTurno = romperPorTurno;
    }

    public Boolean getRomperPorTurno() {
        return romperPorTurno;
    }
}
