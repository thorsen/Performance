package es.ramondin.performance.modelOracle.views;

import oracle.jbo.RowIterator;
import oracle.jbo.RowSet;
import oracle.jbo.domain.Number;
import oracle.jbo.server.AttributeDefImpl;
import oracle.jbo.server.ViewRowImpl;
// ---------------------------------------------------------------------
// ---    File generated by Oracle ADF Business Components Design Time.
// ---    Thu Apr 24 15:47:55 CEST 2014
// ---    Custom code may be added to this class.
// ---    Warning: Do not modify method signatures of generated methods.
// ---------------------------------------------------------------------
public class GraficoVORowImpl extends ViewRowImpl {
    /**
     * AttributesEnum: generated enum for identifying attributes and accessors. DO NOT MODIFY.
     */
    public enum AttributesEnum {
        MgraIdTipoGra {
            public Object get(GraficoVORowImpl obj) {
                return obj.getMgraIdTipoGra();
            }

            public void put(GraficoVORowImpl obj, Object value) {
                obj.setMgraIdTipoGra((String)value);
            }
        }
        ,
        MgraIdGra {
            public Object get(GraficoVORowImpl obj) {
                return obj.getMgraIdGra();
            }

            public void put(GraficoVORowImpl obj, Object value) {
                obj.setMgraIdGra((Number)value);
            }
        }
        ,
        MgraTitulo {
            public Object get(GraficoVORowImpl obj) {
                return obj.getMgraTitulo();
            }

            public void put(GraficoVORowImpl obj, Object value) {
                obj.setMgraTitulo((String)value);
            }
        }
        ,
        MgraSubtitulo {
            public Object get(GraficoVORowImpl obj) {
                return obj.getMgraSubtitulo();
            }

            public void put(GraficoVORowImpl obj, Object value) {
                obj.setMgraSubtitulo((String)value);
            }
        }
        ,
        MgraSentidoMejor {
            public Object get(GraficoVORowImpl obj) {
                return obj.getMgraSentidoMejor();
            }

            public void put(GraficoVORowImpl obj, Object value) {
                obj.setMgraSentidoMejor((Number)value);
            }
        }
        ,
        MgraCampoAnual {
            public Object get(GraficoVORowImpl obj) {
                return obj.getMgraCampoAnual();
            }

            public void put(GraficoVORowImpl obj, Object value) {
                obj.setMgraCampoAnual((String)value);
            }
        }
        ,
        MgraCampoMensual {
            public Object get(GraficoVORowImpl obj) {
                return obj.getMgraCampoMensual();
            }

            public void put(GraficoVORowImpl obj, Object value) {
                obj.setMgraCampoMensual((String)value);
            }
        }
        ,
        MgraTipoEjey {
            public Object get(GraficoVORowImpl obj) {
                return obj.getMgraTipoEjey();
            }

            public void put(GraficoVORowImpl obj, Object value) {
                obj.setMgraTipoEjey((String)value);
            }
        }
        ,
        MgraUdsEjey {
            public Object get(GraficoVORowImpl obj) {
                return obj.getMgraUdsEjey();
            }

            public void put(GraficoVORowImpl obj, Object value) {
                obj.setMgraUdsEjey((String)value);
            }
        }
        ,
        MgraMaxDecimales {
            public Object get(GraficoVORowImpl obj) {
                return obj.getMgraMaxDecimales();
            }

            public void put(GraficoVORowImpl obj, Object value) {
                obj.setMgraMaxDecimales((Number)value);
            }
        }
        ,
        MgraTituloEjey {
            public Object get(GraficoVORowImpl obj) {
                return obj.getMgraTituloEjey();
            }

            public void put(GraficoVORowImpl obj, Object value) {
                obj.setMgraTituloEjey((String)value);
            }
        }
        ,
        IdTipoGraIdGraTrans {
            public Object get(GraficoVORowImpl obj) {
                return obj.getIdTipoGraIdGraTrans();
            }

            public void put(GraficoVORowImpl obj, Object value) {
                obj.setIdTipoGraIdGraTrans((String)value);
            }
        }
        ,
        DescGraficoTrans {
            public Object get(GraficoVORowImpl obj) {
                return obj.getDescGraficoTrans();
            }

            public void put(GraficoVORowImpl obj, Object value) {
                obj.setDescGraficoTrans((String)value);
            }
        }
        ,
        Marker {
            public Object get(GraficoVORowImpl obj) {
                return obj.getMarker();
            }

            public void put(GraficoVORowImpl obj, Object value) {
                obj.setAttributeInternal(index(), value);
            }
        }
        ,
        MarkerEspecial {
            public Object get(GraficoVORowImpl obj) {
                return obj.getMarkerEspecial();
            }

            public void put(GraficoVORowImpl obj, Object value) {
                obj.setAttributeInternal(index(), value);
            }
        }
        ;
        private static AttributesEnum[] vals = null;
        private static final int firstIndex = 0;

        public abstract Object get(GraficoVORowImpl object);

        public abstract void put(GraficoVORowImpl object, Object value);

        public int index() {
            return GraficoVORowImpl.AttributesEnum.firstIndex() + ordinal();
        }

        public static final int firstIndex() {
            return firstIndex;
        }

        public static int count() {
            return GraficoVORowImpl.AttributesEnum.firstIndex() + GraficoVORowImpl.AttributesEnum.staticValues().length;
        }

        public static final AttributesEnum[] staticValues() {
            if (vals == null) {
                vals = GraficoVORowImpl.AttributesEnum.values();
            }
            return vals;
        }
    }


    public static final int MGRAIDTIPOGRA = AttributesEnum.MgraIdTipoGra.index();
    public static final int MGRAIDGRA = AttributesEnum.MgraIdGra.index();
    public static final int MGRATITULO = AttributesEnum.MgraTitulo.index();
    public static final int MGRASUBTITULO = AttributesEnum.MgraSubtitulo.index();
    public static final int MGRASENTIDOMEJOR = AttributesEnum.MgraSentidoMejor.index();
    public static final int MGRACAMPOANUAL = AttributesEnum.MgraCampoAnual.index();
    public static final int MGRACAMPOMENSUAL = AttributesEnum.MgraCampoMensual.index();
    public static final int MGRATIPOEJEY = AttributesEnum.MgraTipoEjey.index();
    public static final int MGRAUDSEJEY = AttributesEnum.MgraUdsEjey.index();
    public static final int MGRAMAXDECIMALES = AttributesEnum.MgraMaxDecimales.index();
    public static final int MGRATITULOEJEY = AttributesEnum.MgraTituloEjey.index();
    public static final int IDTIPOGRAIDGRATRANS = AttributesEnum.IdTipoGraIdGraTrans.index();
    public static final int DESCGRAFICOTRANS = AttributesEnum.DescGraficoTrans.index();
    public static final int MARKER = AttributesEnum.Marker.index();
    public static final int MARKERESPECIAL = AttributesEnum.MarkerEspecial.index();

    /**
     * This is the default constructor (do not remove).
     */
    public GraficoVORowImpl() {
    }

    /**
     * Gets the attribute value for the calculated attribute MgraIdGra.
     * @return the MgraIdGra
     */
    public Number getMgraIdGra() {
        return (Number) getAttributeInternal(MGRAIDGRA);
    }

    /**
     * Sets <code>value</code> as the attribute value for the calculated attribute MgraIdGra.
     * @param value value to set the  MgraIdGra
     */
    public void setMgraIdGra(Number value) {
        setAttributeInternal(MGRAIDGRA, value);
    }

    /**
     * Gets the attribute value for the calculated attribute MgraIdTipoGra.
     * @return the MgraIdTipoGra
     */
    public String getMgraIdTipoGra() {
        return (String) getAttributeInternal(MGRAIDTIPOGRA);
    }

    /**
     * Sets <code>value</code> as the attribute value for the calculated attribute MgraIdTipoGra.
     * @param value value to set the  MgraIdTipoGra
     */
    public void setMgraIdTipoGra(String value) {
        setAttributeInternal(MGRAIDTIPOGRA, value);
    }

    /**
     * Gets the attribute value for the calculated attribute MgraTitulo.
     * @return the MgraTitulo
     */
    public String getMgraTitulo() {
        return (String) getAttributeInternal(MGRATITULO);
    }

    /**
     * Sets <code>value</code> as the attribute value for the calculated attribute MgraTitulo.
     * @param value value to set the  MgraTitulo
     */
    public void setMgraTitulo(String value) {
        setAttributeInternal(MGRATITULO, value);
    }

    /**
     * Gets the attribute value for the calculated attribute MgraSubtitulo.
     * @return the MgraSubtitulo
     */
    public String getMgraSubtitulo() {
        return (String) getAttributeInternal(MGRASUBTITULO);
    }

    /**
     * Sets <code>value</code> as the attribute value for the calculated attribute MgraSubtitulo.
     * @param value value to set the  MgraSubtitulo
     */
    public void setMgraSubtitulo(String value) {
        setAttributeInternal(MGRASUBTITULO, value);
    }

    /**
     * Gets the attribute value for the calculated attribute MgraSentidoMejor.
     * @return the MgraSentidoMejor
     */
    public Number getMgraSentidoMejor() {
        return (Number) getAttributeInternal(MGRASENTIDOMEJOR);
    }

    /**
     * Sets <code>value</code> as the attribute value for the calculated attribute MgraSentidoMejor.
     * @param value value to set the  MgraSentidoMejor
     */
    public void setMgraSentidoMejor(Number value) {
        setAttributeInternal(MGRASENTIDOMEJOR, value);
    }

    /**
     * Gets the attribute value for the calculated attribute MgraCampoAnual.
     * @return the MgraCampoAnual
     */
    public String getMgraCampoAnual() {
        return (String) getAttributeInternal(MGRACAMPOANUAL);
    }

    /**
     * Sets <code>value</code> as the attribute value for the calculated attribute MgraCampoAnual.
     * @param value value to set the  MgraCampoAnual
     */
    public void setMgraCampoAnual(String value) {
        setAttributeInternal(MGRACAMPOANUAL, value);
    }

    /**
     * Gets the attribute value for the calculated attribute MgraCampoMensual.
     * @return the MgraCampoMensual
     */
    public String getMgraCampoMensual() {
        return (String) getAttributeInternal(MGRACAMPOMENSUAL);
    }

    /**
     * Sets <code>value</code> as the attribute value for the calculated attribute MgraCampoMensual.
     * @param value value to set the  MgraCampoMensual
     */
    public void setMgraCampoMensual(String value) {
        setAttributeInternal(MGRACAMPOMENSUAL, value);
    }

    /**
     * Gets the attribute value for the calculated attribute MgraTipoEjey.
     * @return the MgraTipoEjey
     */
    public String getMgraTipoEjey() {
        return (String) getAttributeInternal(MGRATIPOEJEY);
    }

    /**
     * Sets <code>value</code> as the attribute value for the calculated attribute MgraTipoEjey.
     * @param value value to set the  MgraTipoEjey
     */
    public void setMgraTipoEjey(String value) {
        setAttributeInternal(MGRATIPOEJEY, value);
    }

    /**
     * Gets the attribute value for the calculated attribute MgraUdsEjey.
     * @return the MgraUdsEjey
     */
    public String getMgraUdsEjey() {
        return (String) getAttributeInternal(MGRAUDSEJEY);
    }

    /**
     * Sets <code>value</code> as the attribute value for the calculated attribute MgraUdsEjey.
     * @param value value to set the  MgraUdsEjey
     */
    public void setMgraUdsEjey(String value) {
        setAttributeInternal(MGRAUDSEJEY, value);
    }

    /**
     * Gets the attribute value for the calculated attribute MgraMaxDecimales.
     * @return the MgraMaxDecimales
     */
    public Number getMgraMaxDecimales() {
        return (Number) getAttributeInternal(MGRAMAXDECIMALES);
    }

    /**
     * Sets <code>value</code> as the attribute value for the calculated attribute MgraMaxDecimales.
     * @param value value to set the  MgraMaxDecimales
     */
    public void setMgraMaxDecimales(Number value) {
        setAttributeInternal(MGRAMAXDECIMALES, value);
    }

    /**
     * Gets the attribute value for the calculated attribute MgraTituloEjey.
     * @return the MgraTituloEjey
     */
    public String getMgraTituloEjey() {
        return (String) getAttributeInternal(MGRATITULOEJEY);
    }

    /**
     * Sets <code>value</code> as the attribute value for the calculated attribute MgraTituloEjey.
     * @param value value to set the  MgraTituloEjey
     */
    public void setMgraTituloEjey(String value) {
        setAttributeInternal(MGRATITULOEJEY, value);
    }

    /**
     * Gets the attribute value for the calculated attribute DescGraficoTrans.
     * @return the DescGraficoTrans
     */
    public String getDescGraficoTrans() {
        return (String) getAttributeInternal(DESCGRAFICOTRANS);
    }

    /**
     * Sets <code>value</code> as the attribute value for the calculated attribute DescGraficoTrans.
     * @param value value to set the  DescGraficoTrans
     */
    public void setDescGraficoTrans(String value) {
        setAttributeInternal(DESCGRAFICOTRANS, value);
    }

    /**
     * Gets the attribute value for the calculated attribute IdTipoGraIdGraTrans.
     * @return the IdTipoGraIdGraTrans
     */
    public String getIdTipoGraIdGraTrans() {
        return (String) getAttributeInternal(IDTIPOGRAIDGRATRANS);
    }

    /**
     * Sets <code>value</code> as the attribute value for the calculated attribute IdTipoGraIdGraTrans.
     * @param value value to set the  IdTipoGraIdGraTrans
     */
    public void setIdTipoGraIdGraTrans(String value) {
        setAttributeInternal(IDTIPOGRAIDGRATRANS, value);
    }

    /**
     * Gets the associated <code>RowIterator</code> using master-detail link Marker.
     */
    public RowIterator getMarker() {
        return (RowIterator)getAttributeInternal(MARKER);
    }


    /**
     * Gets the associated <code>RowIterator</code> using master-detail link MarkerEspecial.
     */
    public RowIterator getMarkerEspecial() {
        return (RowIterator)getAttributeInternal(MARKERESPECIAL);
    }

    /**
     * getAttrInvokeAccessor: generated method. Do not modify.
     * @param index the index identifying the attribute
     * @param attrDef the attribute

     * @return the attribute value
     * @throws Exception
     */
    protected Object getAttrInvokeAccessor(int index,
                                           AttributeDefImpl attrDef) throws Exception {
        if ((index >= AttributesEnum.firstIndex()) && (index < AttributesEnum.count())) {
            return AttributesEnum.staticValues()[index - AttributesEnum.firstIndex()].get(this);
        }
        return super.getAttrInvokeAccessor(index, attrDef);
    }

    /**
     * setAttrInvokeAccessor: generated method. Do not modify.
     * @param index the index identifying the attribute
     * @param value the value to assign to the attribute
     * @param attrDef the attribute

     * @throws Exception
     */
    protected void setAttrInvokeAccessor(int index, Object value,
                                         AttributeDefImpl attrDef) throws Exception {
        if ((index >= AttributesEnum.firstIndex()) && (index < AttributesEnum.count())) {
            AttributesEnum.staticValues()[index - AttributesEnum.firstIndex()].put(this, value);
            return;
        }
        super.setAttrInvokeAccessor(index, value, attrDef);
    }
}
