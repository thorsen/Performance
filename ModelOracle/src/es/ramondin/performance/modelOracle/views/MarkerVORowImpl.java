package es.ramondin.performance.modelOracle.views;

import oracle.jbo.domain.Date;
import oracle.jbo.domain.Number;
import oracle.jbo.server.AttributeDefImpl;
import oracle.jbo.server.ViewRowImpl;
// ---------------------------------------------------------------------
// ---    File generated by Oracle ADF Business Components Design Time.
// ---    Thu Apr 24 15:52:14 CEST 2014
// ---    Custom code may be added to this class.
// ---    Warning: Do not modify method signatures of generated methods.
// ---------------------------------------------------------------------
public class MarkerVORowImpl extends ViewRowImpl {
    /**
     * AttributesEnum: generated enum for identifying attributes and accessors. DO NOT MODIFY.
     */
    public enum AttributesEnum {
        MmarIdTipoGra {
            public Object get(MarkerVORowImpl obj) {
                return obj.getMmarIdTipoGra();
            }

            public void put(MarkerVORowImpl obj, Object value) {
                obj.setMmarIdTipoGra((String)value);
            }
        }
        ,
        MmarIdGra {
            public Object get(MarkerVORowImpl obj) {
                return obj.getMmarIdGra();
            }

            public void put(MarkerVORowImpl obj, Object value) {
                obj.setMmarIdGra((Number)value);
            }
        }
        ,
        MmarDesdeFecha {
            public Object get(MarkerVORowImpl obj) {
                return obj.getMmarDesdeFecha();
            }

            public void put(MarkerVORowImpl obj, Object value) {
                obj.setMmarDesdeFecha((Date)value);
            }
        }
        ,
        MmarMejoraMant {
            public Object get(MarkerVORowImpl obj) {
                return obj.getMmarMejoraMant();
            }

            public void put(MarkerVORowImpl obj, Object value) {
                obj.setMmarMejoraMant((String)value);
            }
        }
        ,
        MmarValorNorma {
            public Object get(MarkerVORowImpl obj) {
                return obj.getMmarValorNorma();
            }

            public void put(MarkerVORowImpl obj, Object value) {
                obj.setMmarValorNorma((Number)value);
            }
        }
        ,
        MmarSigma {
            public Object get(MarkerVORowImpl obj) {
                return obj.getMmarSigma();
            }

            public void put(MarkerVORowImpl obj, Object value) {
                obj.setMmarSigma((Number)value);
            }
        }
        ,
        MmarSentidoMejor {
            public Object get(MarkerVORowImpl obj) {
                return obj.getMmarSentidoMejor();
            }

            public void put(MarkerVORowImpl obj, Object value) {
                obj.setMmarSentidoMejor((Number)value);
            }
        }
        ;
        private static AttributesEnum[] vals = null;
        private static final int firstIndex = 0;

        public abstract Object get(MarkerVORowImpl object);

        public abstract void put(MarkerVORowImpl object, Object value);

        public int index() {
            return MarkerVORowImpl.AttributesEnum.firstIndex() + ordinal();
        }

        public static final int firstIndex() {
            return firstIndex;
        }

        public static int count() {
            return MarkerVORowImpl.AttributesEnum.firstIndex() + MarkerVORowImpl.AttributesEnum.staticValues().length;
        }

        public static final AttributesEnum[] staticValues() {
            if (vals == null) {
                vals = MarkerVORowImpl.AttributesEnum.values();
            }
            return vals;
        }
    }


    public static final int MMARIDTIPOGRA = AttributesEnum.MmarIdTipoGra.index();
    public static final int MMARIDGRA = AttributesEnum.MmarIdGra.index();
    public static final int MMARDESDEFECHA = AttributesEnum.MmarDesdeFecha.index();
    public static final int MMARMEJORAMANT = AttributesEnum.MmarMejoraMant.index();
    public static final int MMARVALORNORMA = AttributesEnum.MmarValorNorma.index();
    public static final int MMARSIGMA = AttributesEnum.MmarSigma.index();
    public static final int MMARSENTIDOMEJOR = AttributesEnum.MmarSentidoMejor.index();

    /**
     * This is the default constructor (do not remove).
     */
    public MarkerVORowImpl() {
    }

    /**
     * Gets the attribute value for the calculated attribute MmarIdTipoGra.
     * @return the MmarIdTipoGra
     */
    public String getMmarIdTipoGra() {
        return (String) getAttributeInternal(MMARIDTIPOGRA);
    }

    /**
     * Sets <code>value</code> as the attribute value for the calculated attribute MmarIdTipoGra.
     * @param value value to set the  MmarIdTipoGra
     */
    public void setMmarIdTipoGra(String value) {
        setAttributeInternal(MMARIDTIPOGRA, value);
    }

    /**
     * Gets the attribute value for the calculated attribute MmarIdGra.
     * @return the MmarIdGra
     */
    public Number getMmarIdGra() {
        return (Number) getAttributeInternal(MMARIDGRA);
    }

    /**
     * Sets <code>value</code> as the attribute value for the calculated attribute MmarIdGra.
     * @param value value to set the  MmarIdGra
     */
    public void setMmarIdGra(Number value) {
        setAttributeInternal(MMARIDGRA, value);
    }

    /**
     * Gets the attribute value for the calculated attribute MmarDesdeFecha.
     * @return the MmarDesdeFecha
     */
    public Date getMmarDesdeFecha() {
        return (Date) getAttributeInternal(MMARDESDEFECHA);
    }

    /**
     * Sets <code>value</code> as the attribute value for the calculated attribute MmarDesdeFecha.
     * @param value value to set the  MmarDesdeFecha
     */
    public void setMmarDesdeFecha(Date value) {
        setAttributeInternal(MMARDESDEFECHA, value);
    }

    /**
     * Gets the attribute value for the calculated attribute MmarMejoraMant.
     * @return the MmarMejoraMant
     */
    public String getMmarMejoraMant() {
        return (String) getAttributeInternal(MMARMEJORAMANT);
    }

    /**
     * Sets <code>value</code> as the attribute value for the calculated attribute MmarMejoraMant.
     * @param value value to set the  MmarMejoraMant
     */
    public void setMmarMejoraMant(String value) {
        setAttributeInternal(MMARMEJORAMANT, value);
    }

    /**
     * Gets the attribute value for the calculated attribute MmarValorNorma.
     * @return the MmarValorNorma
     */
    public Number getMmarValorNorma() {
        return (Number) getAttributeInternal(MMARVALORNORMA);
    }

    /**
     * Sets <code>value</code> as the attribute value for the calculated attribute MmarValorNorma.
     * @param value value to set the  MmarValorNorma
     */
    public void setMmarValorNorma(Number value) {
        setAttributeInternal(MMARVALORNORMA, value);
    }

    /**
     * Gets the attribute value for the calculated attribute MmarSigma.
     * @return the MmarSigma
     */
    public Number getMmarSigma() {
        return (Number) getAttributeInternal(MMARSIGMA);
    }

    /**
     * Sets <code>value</code> as the attribute value for the calculated attribute MmarSigma.
     * @param value value to set the  MmarSigma
     */
    public void setMmarSigma(Number value) {
        setAttributeInternal(MMARSIGMA, value);
    }

    /**
     * Gets the attribute value for the calculated attribute MmarSentidoMejor.
     * @return the MmarSentidoMejor
     */
    public Number getMmarSentidoMejor() {
        return (Number) getAttributeInternal(MMARSENTIDOMEJOR);
    }

    /**
     * Sets <code>value</code> as the attribute value for the calculated attribute MmarSentidoMejor.
     * @param value value to set the  MmarSentidoMejor
     */
    public void setMmarSentidoMejor(Number value) {
        setAttributeInternal(MMARSENTIDOMEJOR, value);
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