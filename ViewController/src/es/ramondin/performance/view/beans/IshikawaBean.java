package es.ramondin.performance.view.beans;

import component.CompDecGraficoComponent;

import es.ramondin.Encriptacion;
import es.ramondin.compdec.grafico.view.beans.GraficoBean;
import es.ramondin.compdec.grafico.view.util.GraficoUtil;
import es.ramondin.performance.modelOracle.views.MarkerVOImpl;
import es.ramondin.performance.modelOracle.views.PerformanceVOImpl;
import es.ramondin.utilidades.JSFUtils;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import java.io.File;
import java.io.IOException;
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

import javax.imageio.ImageIO;

import oracle.adf.model.BindingContext;
import oracle.adf.view.faces.bi.component.graph.UIGraph;
import oracle.adf.view.rich.component.rich.fragment.RichDeclarativeComponent;
import oracle.adf.view.rich.component.rich.input.RichSelectBooleanCheckbox;
import oracle.adf.view.rich.component.rich.input.RichSelectOneChoice;
import oracle.adf.view.rich.component.rich.layout.RichGridCell;
import oracle.adf.view.rich.component.rich.layout.RichGridRow;
import oracle.adf.view.rich.component.rich.layout.RichPanelGridLayout;
import oracle.adf.view.rich.component.rich.layout.RichPanelGroupLayout;
import oracle.adf.view.rich.component.rich.output.RichActiveImage;
import oracle.adf.view.rich.component.rich.output.RichSpacer;
import oracle.adf.view.rich.context.AdfFacesContext;

import oracle.binding.BindingContainer;
import oracle.binding.OperationBinding;

import oracle.jbo.Key;
import oracle.jbo.domain.Number;
import oracle.jbo.uicli.binding.JUCtrlListBinding;

public class IshikawaBean {
    private Integer anoRuptura = this.ANO_RUPTURA_MAX;
    private Integer anosHistorico = 5;
    private RichSelectBooleanCheckbox cbMesVencido;
    private Boolean mostrarCelulasAgrup;
    private Boolean romperPorTurno;
    private List listaCelulasAgrup;
    private RichSelectOneChoice socCelulas;
    private RichSelectOneChoice socCelulasAgrup;
    private RichPanelGridLayout panelGridLayout;
    private RichDeclarativeComponent cdGrafico;
    private String rutaDestino;
    private String rutaFicheroXSL;
    private RichSelectBooleanCheckbox cbMismoPeriodo;
    private RichSelectBooleanCheckbox cbVerMensual;
    private Integer[] turnosSelIndexes;
    private oracle.adfinternal.view.faces.dvt.model.binding.graph.ActiveGraphDataModel graphModel;

    private static final int ALTO_FINAL_MAX = (int)((297 - 2*13.9) * 4);
    private static final int ANCHO_FINAL_MAX = (int)((420 - 2*12.7) * 4);
    private static final int MAX_CAUSAS_POR_FILA = 3;
    private static final int MIN_NUM_FILAS = 3;
    private static int ANCHO_GRAF = 300;
    private static int ALTO_GRAF = (int)(ANCHO_GRAF * 3.0 / 4.0);
    private static final int ANCHO_SEPARADOR = 3;
    private static final int ALTO_SEPARADOR = 15;
    private static final int ANCHO_FLECHA = 4 * ANCHO_SEPARADOR;
    private static final int MARGEN_GRAFICO = 3;
    private static int ESPACIO_GRAFICO = 12;
    private static final int ALTO_VACIO = 1;

    private static final String[] HTML_COLORES = new String[] { "#097054", "#6599FF", "#FFDE00", "#FF9900", "#443266", "#C3C3E5", "#F1F0FF", "#8C489F" };


    private static final Integer ANO_RUPTURA_MAX = (new GregorianCalendar()).get(GregorianCalendar.YEAR);

    private static final String RUTA_FICHEROS_PDF = "DIR_FICHEROS_PDF";
    private static final String RUTA_FICHEROS_XSL = "DIR_FICHEROS_XSL";
    private static final String FICHERO_XSL = "graficoA4Landscape.xsl";

    private static final String MARCA_BORRAR_PREF = "BORRAR_ISHIKAWA_";

    public IshikawaBean() {
        OperationBinding operation = this.getBindings().getOperationBinding("getValor");
        operation.getParamsMap().put("param", RUTA_FICHEROS_PDF);
        this.setRutaDestino((String)operation.execute());

        operation.getParamsMap().put("param", RUTA_FICHEROS_XSL);
        this.setRutaFicheroXSL((String)operation.execute() + FICHERO_XSL);
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

    private String generaPixel(String colorHTML) {
        String res = getRutaDestino() + MARCA_BORRAR_PREF + "Pixel_" + colorHTML + ".png";
        File f = new File(res);

        if (!f.exists()) {
            BufferedImage img = new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB);
            Graphics2D g2d = img.createGraphics();

            g2d.setColor(Color.decode(colorHTML));
            g2d.fillRect(0, 0, 1, 1);
            g2d.dispose();

            try {
                ImageIO.write(img, "png", new File(res));
            } catch (IOException ex) {
                ex.printStackTrace();
                res = null;
            }
        }

        return res;
    }

    private String generaFlecha(String colorHTML, int grados) {
        String res = getRutaDestino() + MARCA_BORRAR_PREF + "Flecha_" + colorHTML + "_" + grados + ".png";
        File f = new File(res);

        if (!f.exists()) {
            BufferedImage img = new BufferedImage(ANCHO_FLECHA, ANCHO_FLECHA, BufferedImage.TYPE_INT_ARGB);
            Graphics2D g2d = img.createGraphics();

            int x[];
            int y[];

            switch (grados) {
            case 0:
                x = new int[] { 0, 0, ANCHO_FLECHA };
                y = new int[] { 0, ANCHO_FLECHA, ANCHO_FLECHA / 2 };
                break;
            case 90:
                x = new int[] { 0, ANCHO_FLECHA, ANCHO_FLECHA / 2 };
                y = new int[] { ANCHO_FLECHA, ANCHO_FLECHA, 0 };
                break;
            case 180:
                x = new int[] { ANCHO_FLECHA, ANCHO_FLECHA, 0 };
                y = new int[] { 0, ANCHO_FLECHA, ANCHO_FLECHA / 2 };
                break;
            case 270:
            default:
                x = new int[] { 0, ANCHO_FLECHA, ANCHO_FLECHA / 2 };
                y = new int[] { 0, 0, ANCHO_FLECHA };
                break;
            }

            g2d.setColor(Color.decode(colorHTML));
            g2d.fillPolygon(x, y, 3);
            g2d.dispose();

            try {
                ImageIO.write(img, "png", new File(res));
            } catch (IOException ex) {
                ex.printStackTrace();
                res = null;
            }
        }

        return res;
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

    private RichGridCell anadeEspacio(RichGridCell celda, Integer width, Integer height, String inlineStyle, Integer anchoMargen) {
        RichPanelGroupLayout rpgl = new RichPanelGroupLayout();
        rpgl.setLayout(RichPanelGroupLayout.LAYOUT_HORIZONTAL);

        if (anchoMargen != null) {
            RichSpacer rs = new RichSpacer();
            rs.setWidth("" + anchoMargen);

            rpgl.getChildren().add(rs);
        }

        RichActiveImage rai = new RichActiveImage();

        String inlineStyleOri = rai.getInlineStyle() != null ? rai.getInlineStyle() : "";

        if (width != null) {
            inlineStyleOri += " width:" + width + "px;";
        }
        if (height != null) {
            inlineStyleOri += " height:" + height + "px;";
        }
        if (inlineStyle != null) {
            String color = inlineStyle.substring(inlineStyle.lastIndexOf(":") + 1);
            color = color.replace(";", "");

            inlineStyleOri += inlineStyle;

            rai.setSource(getRutaGrafico(generaPixel(color)));
        }
        
        rai.setInlineStyle(inlineStyleOri);
        
        rpgl.getChildren().add(rai);

        celda.getChildren().add(rpgl);

        /*
            rs = new RichSpacer();
            if (width != null) {
                rs.setWidth(width.toString());
            }
            if (height != null) {
                rs.setHeight(height.toString());
            }
            if (inlineStyle != null) {
                rs.setInlineStyle(inlineStyle);
            }

            rpgl.getChildren().add(rs);
        celda.getChildren().add(rs);

        if (inlineStyle != null)
            celda.setInlineStyle(inlineStyle);
        */

        return celda;
    }

    private RichGridCell anadeGrafico(RichGridCell celda, String shortDesc, String source, String inlineStyle, Integer anchoMargen) {
        RichPanelGroupLayout rpgl = new RichPanelGroupLayout();
        rpgl.setLayout(RichPanelGroupLayout.LAYOUT_HORIZONTAL);

        if (anchoMargen != null) {
            RichSpacer rs = new RichSpacer();
            rs.setWidth("" + anchoMargen);

            rpgl.getChildren().add(rs);
        }

        RichActiveImage rai = new RichActiveImage();

        if (shortDesc != null)
            rai.setShortDesc(shortDesc);
        if (source != null)
            rai.setSource(source);
        if (inlineStyle != null)
            rai.setInlineStyle(inlineStyle);
        
        rpgl.getChildren().add(rai);

        celda.getChildren().add(rpgl);

        return celda;
    }

    public String refrescarIshikawa() {
        borrarFicherosMarcados();
        ArrayList<String> rutasCausas = new ArrayList<String>();
        ArrayList<String> titulosCausas = new ArrayList<String>();

        this.panelGridLayout.setDimensionsFrom(RichPanelGridLayout.DIMENSIONS_FROM_CHILDREN);

        List<UIComponent> filas = this.panelGridLayout.getChildren();
        filas.clear();

        AdfFacesContext.getCurrentInstance().addPartialTarget(this.panelGridLayout);

        OperationBinding operation = this.getBindings().getOperationBinding("getCausas");
        ArrayList<Object[]> graficosCausas = (ArrayList<Object[]>)operation.execute();
        operation = this.getBindings().getOperationBinding("getEfecto");
        ArrayList<Object[]> graficoEfectos = (ArrayList<Object[]>)operation.execute();
        Object[] graficoEfecto = graficoEfectos != null ? graficoEfectos.get(0) : null;

        int numCausas = graficosCausas != null ? graficosCausas.size() : 0;

        int numFilas = (int)Math.ceil(1.0 * numCausas / MAX_CAUSAS_POR_FILA) + 1;

        if (numFilas < MIN_NUM_FILAS)
            numFilas = MIN_NUM_FILAS;
        
        //Recalculamos los tamaños para maximizar el espacio
        Double altoGrafAux = (ALTO_FINAL_MAX - (numFilas - 1) * ALTO_SEPARADOR - numFilas * 2 * MARGEN_GRAFICO - 2 * ANCHO_FLECHA) / (1.0*numFilas);
        Double anchoGrafAux = altoGrafAux * 4.0 / 3.0;
                                                                                                                                  
        ALTO_GRAF = (int)(Math.floor(altoGrafAux));
        ANCHO_GRAF = (int)(Math.ceil(anchoGrafAux));
        
        ESPACIO_GRAFICO = (int)(Math.ceil((ANCHO_FINAL_MAX - ANCHO_GRAF - 2 * MARGEN_GRAFICO - ANCHO_FLECHA) / (1.0*numCausas)));
        
        //Cogemos el compenente declarativo y su gráfico interno
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

        String ficGenerado;
        String ficRenombrado;
        File fRenombrado;
        for (int i = 0; i < numCausas; i++) {
            preparaGrafico((String)graficosCausas.get(i)[0], (Number)graficosCausas.get(i)[1], map);
            cdgb.inicializaExterno(cdgc, comboGraph);

            ficGenerado =
                    GraficoUtil.generarFicheroExportar(FacesContext.getCurrentInstance(), cdgb.getComboGraph(), cdgc.getId(), cdgc.getPrintWidth(), cdgc.getPrintHeight(),
                                                       cdgc.getPrintRutaDestino(), cdgc.getRutaFicheroXSL(), cdgc.getTextoEsquinaSupIzq(),
                                                       cdgc.getTextoEsquinaSupDer(), cdgc.getTextoEsquinaInfIzq(), cdgc.getTextoEsquinaInfDer(),
                                                       GraficoUtil.FORMATO_SALIDA_PNG);

            fRenombrado = new File(ficGenerado);
            ficRenombrado = ficGenerado.substring(0, ficGenerado.lastIndexOf(fRenombrado.getName())) + MARCA_BORRAR_PREF + fRenombrado.getName();

            fRenombrado = new File(ficGenerado);
            fRenombrado.renameTo(new File(ficRenombrado));

            rutasCausas.add(ficRenombrado);
            titulosCausas.add(cdgc.getTitulo() + "\n" +
                    cdgc.getSubtitulo());
        }

        //Generamos el efecto
        preparaGrafico((String)graficoEfecto[0], (Number)graficoEfecto[1], map);
        cdgb.inicializaExterno(cdgc, comboGraph);

        ficGenerado =
                GraficoUtil.generarFicheroExportar(FacesContext.getCurrentInstance(), cdgb.getComboGraph(), cdgc.getId(), cdgc.getPrintWidth(), cdgc.getPrintHeight(),
                                                   cdgc.getPrintRutaDestino(), cdgc.getRutaFicheroXSL(), cdgc.getTextoEsquinaSupIzq(),
                                                   cdgc.getTextoEsquinaSupDer(), cdgc.getTextoEsquinaInfIzq(), cdgc.getTextoEsquinaInfDer(),
                                                   GraficoUtil.FORMATO_SALIDA_PNG);
        fRenombrado = new File(ficGenerado);
        ficRenombrado = ficGenerado.substring(0, ficGenerado.lastIndexOf(fRenombrado.getName())) + MARCA_BORRAR_PREF + fRenombrado.getName();

        fRenombrado = new File(ficGenerado);
        fRenombrado.renameTo(new File(ficRenombrado));

        rutasCausas.add(ficRenombrado);
        titulosCausas.add(cdgc.getTitulo() + "\n" +
                cdgc.getSubtitulo());

        RichGridRow rgRow = null;
        List columnas = null;
        RichGridCell rgCell = null;
        int colSpan;

        List<UIComponent> filasCausas = new ArrayList<UIComponent>();
        List<UIComponent> filasEfecto = new ArrayList<UIComponent>();

        //Filas de graficos
        int mostrarLinea = -1;
        for (int i = 0; i < numFilas; i++) {
            rgRow = new RichGridRow();
            columnas = rgRow.getChildren();
            colSpan = 1;

            //Causas
            if (i != numFilas - 1) {
                for (int j = 0; j < numCausas + numFilas; j++) {
                    if (colSpan <= 1) {
                        rgCell = new RichGridCell();

                        rgCell.setValign(RichGridCell.VALIGN_MIDDLE);
                        rgCell.setHalign(RichGridCell.HALIGN_START);

                        if (j % (numFilas - 1) == i && j < numCausas) {
                            rgCell =
                                    anadeGrafico(rgCell, titulosCausas.get(j), getRutaGrafico(rutasCausas.get(j)), "width:" + ANCHO_GRAF + "px; height:" +
                                                 ALTO_GRAF + "px; outline-color:" + HTML_COLORES[i % HTML_COLORES.length] +
                                                 "; outline-style:solid; outline-widht:" + MARGEN_GRAFICO + "px; margin:" + MARGEN_GRAFICO + "px;", null);

                            colSpan = numFilas - 1;
                            if (j >= numCausas - (numFilas - 2))
                                colSpan++;

                            if (colSpan > 1) {
                                rgCell.setColumnSpan(colSpan);
                            }
                        } else {
                            mostrarLinea = -1;
                            for (int k = i; k >= 0; k -= 2) {
                                if (j % (numFilas - 1) == k && j < numCausas) {
                                    mostrarLinea = k;
                                    break;
                                }
                            }

                            if (mostrarLinea != -1)
                                rgCell =
                                        anadeEspacio(rgCell, ANCHO_SEPARADOR, 2 * MARGEN_GRAFICO + ALTO_GRAF, "background-color:" + HTML_COLORES[mostrarLinea %
                                                     HTML_COLORES.length] + ";", ANCHO_GRAF / 6);
                            else {
                                if (j >= numCausas)
                                    rgCell = anadeEspacio(rgCell, ANCHO_FLECHA, ALTO_VACIO, null, null);
                                else
                                    rgCell = anadeEspacio(rgCell, ESPACIO_GRAFICO, ALTO_VACIO, null, null);
                            }
                        }

                        columnas.add(rgCell);
                    } else
                        colSpan--;
                }
                filasCausas.add(rgRow);
            } else { //Efecto
                for (int j = 0; j < numCausas + numFilas; j++) {
                    if (j < numCausas) {
                        rgCell = new RichGridCell();

                        rgCell.setValign(RichGridCell.VALIGN_MIDDLE);
                        rgCell.setHalign(RichGridCell.HALIGN_START);

                        rgCell =
                                anadeEspacio(rgCell, ESPACIO_GRAFICO, ANCHO_SEPARADOR, "background-color:#000000;", null);

                        columnas.add(rgCell);
                    }
                }
                filasEfecto.add(rgRow);
            }
        }

        //Filas de líneas de causas
        for (int i = 0; i < numFilas - 1; i++) {
            rgRow = new RichGridRow();
            columnas = rgRow.getChildren();

            for (int j = 0; j < numCausas + numFilas; j++) {
                rgCell = new RichGridCell();

                rgCell.setValign(RichGridCell.VALIGN_MIDDLE);
                rgCell.setHalign(RichGridCell.HALIGN_START);

                mostrarLinea = -1;

                for (int k = i; k >= 0; k -= 2) {
                    if (j % (numFilas - 1) == k && j < numCausas) {
                        mostrarLinea = k;
                        break;
                    }
                }

                if (mostrarLinea != -1)
                    rgCell =
                            anadeEspacio(rgCell, ANCHO_SEPARADOR, ALTO_SEPARADOR, "background-color:" + HTML_COLORES[mostrarLinea % HTML_COLORES.length] + ";",
                                         ANCHO_GRAF / 6);
                else if (j >= numCausas)
                    rgCell = anadeEspacio(rgCell, ANCHO_FLECHA, ALTO_VACIO, null, null);
                else
                    rgCell = anadeEspacio(rgCell, ESPACIO_GRAFICO, ALTO_VACIO, null, null);

                columnas.add(rgCell);
            }
            filasCausas.add(rgRow);
        }

        //Filas de líneas de efecto
        for (int i = numFilas - 3; i < numFilas - 1; i++) {
            rgRow = new RichGridRow();
            columnas = rgRow.getChildren();

            for (int j = 0; j <= numCausas; j++) {
                rgCell = new RichGridCell();

                rgCell.setValign(RichGridCell.VALIGN_MIDDLE);
                rgCell.setHalign(RichGridCell.HALIGN_START);

                mostrarLinea = -1;

                for (int k = i; k >= 0; k -= 2) {
                    if (j % (numFilas - 1) == k && j < numCausas) {
                        mostrarLinea = k;
                        break;
                    }
                }

                if (mostrarLinea != -1)
                    rgCell =
                            anadeEspacio(rgCell, ANCHO_SEPARADOR, (2 * MARGEN_GRAFICO + ALTO_GRAF) / 2, "background-color:" + HTML_COLORES[mostrarLinea %
                                         HTML_COLORES.length] + ";", ANCHO_GRAF / 6);
                else if (j >= numCausas)
                    rgCell = anadeEspacio(rgCell, ANCHO_FLECHA, ALTO_VACIO, null, null);
                else
                    rgCell = anadeEspacio(rgCell, ESPACIO_GRAFICO, ALTO_VACIO, null, null);

                columnas.add(rgCell);
            }

            if (i % 2 == 0) {
                rgCell = new RichGridCell();

                rgCell.setValign(RichGridCell.VALIGN_MIDDLE);
                rgCell.setHalign(RichGridCell.HALIGN_START);

                rgCell =
                        anadeGrafico(rgCell, titulosCausas.get(titulosCausas.size() - 1), getRutaGrafico(rutasCausas.get(rutasCausas.size() - 1)), "width:" + ANCHO_GRAF +
                                     "px; height:" + ALTO_GRAF + "px; outline-color:#000000; outline-style:solid; outline-widht:" + MARGEN_GRAFICO +
                                     "px; margin:" + MARGEN_GRAFICO + "px;", null);
                rgCell.setRowSpan(5);
                rgCell.setColumnSpan(numFilas - 1);

                columnas.add(rgCell);

                filasEfecto.add(0, rgRow); //La añadimos la primera
            } else
                filasEfecto.add(rgRow); //La añadimos la última

            //Filas de flechas
            rgRow = new RichGridRow();
            columnas = rgRow.getChildren();
            for (int j = 0; j <= numCausas; j++) {
                rgCell = new RichGridCell();

                rgCell.setValign(RichGridCell.VALIGN_MIDDLE);
                rgCell.setHalign(RichGridCell.HALIGN_START);

                mostrarLinea = -1;

                for (int k = i; k >= 0; k -= 2) {
                    if (j % (numFilas - 1) == k && j < numCausas) {
                        mostrarLinea = k;
                        break;
                    }
                }

                if (j == numCausas && i % 2 == 0) {
                    rgCell = anadeGrafico(rgCell, null, getRutaGrafico(generaFlecha("#000000", 0)), null, null);
                    rgCell.setRowSpan(3);
                } else {
                    if (mostrarLinea != -1)
                        rgCell =
                                anadeGrafico(rgCell, null, getRutaGrafico(generaFlecha(HTML_COLORES[mostrarLinea % HTML_COLORES.length], i % 2 == 0 ? 270 : 90)),
                                             null, ANCHO_GRAF / 6 - ANCHO_FLECHA / 2 + ANCHO_SEPARADOR / 2);
                    else if (j == numCausas)
                        continue;
                    else
                        rgCell = anadeEspacio(rgCell, ESPACIO_GRAFICO, ALTO_VACIO, null, null);
                }

                columnas.add(rgCell);
            }

            if (i % 2 == 0) {
                filasEfecto.add(1, rgRow); //La añadimos la segunda
            } else
                filasEfecto.add(filasEfecto.size() - 1, rgRow); //La añadimos la penúltima
        }

        //Reordenamos las líneas
        for (int i = 0; i < numFilas - 1; i++) {
            if (i % 2 == 0) {
                filas.add(i, filasCausas.get(i));
                filas.add(i + 1, filasCausas.get(i + numFilas - 1));
            } else {
                filas.add(i + 1, filasCausas.get(i));
                filas.add(i + 1, filasCausas.get(i + numFilas - 1));
            }
        }

        //Añadimos el efecto
        int posAnadir = 2 * (int)(Math.ceil((numFilas - 1) / 2.0));

        filas.add(posAnadir, filasEfecto.get(0)); //Linea que contiene el gráfico con span
        filas.add(posAnadir + 1, filasEfecto.get(1)); //Linea que contiene las flechas verticales superiores y horizontal
        filas.add(posAnadir + 2, filasEfecto.get(2)); //Linea que contiene la línea horizontal
        filas.add(posAnadir + 3, filasEfecto.get(3)); //Linea que contiene las flechas verticales inferiores
        filas.add(posAnadir + 4, filasEfecto.get(4)); //Linea que queda

        AdfFacesContext.getCurrentInstance().addPartialTarget(this.panelGridLayout);

        return null;
    }

    public Integer getFormatoSalida() {
        return GraficoUtil.FORMATO_SALIDA_PDF;
    }

    public void setPanelGridLayout(RichPanelGridLayout panelGridLayout) {
        this.panelGridLayout = panelGridLayout;
    }

    public RichPanelGridLayout getPanelGridLayout() {
        return panelGridLayout;
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

    public void setCdGrafico(RichDeclarativeComponent cdGrafico) {
        this.cdGrafico = cdGrafico;
    }

    public RichDeclarativeComponent getCdGrafico() {
        return cdGrafico;
    }

    public void setRutaDestino(String rutaDestino) {
        this.rutaDestino = rutaDestino;
    }

    public String getRutaDestino() {
        return rutaDestino;
    }

    public void setRutaFicheroXSL(String rutaFicheroXSL) {
        this.rutaFicheroXSL = rutaFicheroXSL;
    }

    public String getRutaFicheroXSL() {
        return rutaFicheroXSL;
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
