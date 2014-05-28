package es.ramondin.performance.modelOracle.views;

import oracle.jbo.RowIterator;
import oracle.jbo.domain.Number;
import oracle.jbo.server.AttributeDefImpl;
import oracle.jbo.server.ViewRowImpl;
// ---------------------------------------------------------------------
// ---    File generated by Oracle ADF Business Components Design Time.
// ---    Thu May 15 12:31:41 CEST 2014
// ---    Custom code may be added to this class.
// ---    Warning: Do not modify method signatures of generated methods.
// ---------------------------------------------------------------------
public class IshikawaVORowImpl extends ViewRowImpl {
    /**
     * AttributesEnum: generated enum for identifying attributes and accessors. DO NOT MODIFY.
     */
    public enum AttributesEnum {
        MishId {
            public Object get(IshikawaVORowImpl obj) {
                return obj.getMishId();
            }

            public void put(IshikawaVORowImpl obj, Object value) {
                obj.setMishId((Number)value);
            }
        },
        MishDesc {
            public Object get(IshikawaVORowImpl obj) {
                return obj.getMishDesc();
            }

            public void put(IshikawaVORowImpl obj, Object value) {
                obj.setMishDesc((String)value);
            }
        },
        MishIdTgrEfecto {
            public Object get(IshikawaVORowImpl obj) {
                return obj.getMishIdTgrEfecto();
            }

            public void put(IshikawaVORowImpl obj, Object value) {
                obj.setMishIdTgrEfecto((String)value);
            }
        },
        MishIdGraEfecto {
            public Object get(IshikawaVORowImpl obj) {
                return obj.getMishIdGraEfecto();
            }

            public void put(IshikawaVORowImpl obj, Object value) {
                obj.setMishIdGraEfecto((Number)value);
            }
        },
        DescIshikawaTrans {
            public Object get(IshikawaVORowImpl obj) {
                return obj.getDescIshikawaTrans();
            }

            public void put(IshikawaVORowImpl obj, Object value) {
                obj.setDescIshikawaTrans((String)value);
            }
        },
        IshikawaLin {
            public Object get(IshikawaVORowImpl obj) {
                return obj.getIshikawaLin();
            }

            public void put(IshikawaVORowImpl obj, Object value) {
                obj.setAttributeInternal(index(), value);
            }
        },
        Grafico {
            public Object get(IshikawaVORowImpl obj) {
                return obj.getGrafico();
            }

            public void put(IshikawaVORowImpl obj, Object value) {
                obj.setAttributeInternal(index(), value);
            }
        };
        private static AttributesEnum[] vals = null;
        private static final int firstIndex = 0;

        public abstract Object get(IshikawaVORowImpl object);

        public abstract void put(IshikawaVORowImpl object, Object value);

        public int index() {
            return IshikawaVORowImpl.AttributesEnum.firstIndex() + ordinal();
        }

        public static final int firstIndex() {
            return firstIndex;
        }

        public static int count() {
            return IshikawaVORowImpl.AttributesEnum.firstIndex() + IshikawaVORowImpl.AttributesEnum.staticValues().length;
        }

        public static final AttributesEnum[] staticValues() {
            if (vals == null) {
                vals = IshikawaVORowImpl.AttributesEnum.values();
            }
            return vals;
        }
    }

    public static final int MISHID = AttributesEnum.MishId.index();
    public static final int MISHDESC = AttributesEnum.MishDesc.index();
    public static final int MISHIDTGREFECTO = AttributesEnum.MishIdTgrEfecto.index();
    public static final int MISHIDGRAEFECTO = AttributesEnum.MishIdGraEfecto.index();
    public static final int DESCISHIKAWATRANS = AttributesEnum.DescIshikawaTrans.index();
    public static final int ISHIKAWALIN = AttributesEnum.IshikawaLin.index();
    public static final int GRAFICO = AttributesEnum.Grafico.index();

    /**
     * This is the default constructor (do not remove).
     */
    public IshikawaVORowImpl() {
    }


    /**
     * Gets the attribute value for the calculated attribute MishId.
     * @return the MishId
     */
    public Number getMishId() {
        return (Number)getAttributeInternal(MISHID);
    }

    /**
     * Sets <code>value</code> as the attribute value for the calculated attribute MishId.
     * @param value value to set the  MishId
     */
    public void setMishId(Number value) {
        setAttributeInternal(MISHID, value);
    }

    /**
     * Gets the attribute value for the calculated attribute MishDesc.
     * @return the MishDesc
     */
    public String getMishDesc() {
        return (String)getAttributeInternal(MISHDESC);
    }

    /**
     * Sets <code>value</code> as the attribute value for the calculated attribute MishDesc.
     * @param value value to set the  MishDesc
     */
    public void setMishDesc(String value) {
        setAttributeInternal(MISHDESC, value);
    }

    /**
     * Gets the attribute value for the calculated attribute MishIdTgrEfecto.
     * @return the MishIdTgrEfecto
     */
    public String getMishIdTgrEfecto() {
        return (String)getAttributeInternal(MISHIDTGREFECTO);
    }

    /**
     * Sets <code>value</code> as the attribute value for the calculated attribute MishIdTgrEfecto.
     * @param value value to set the  MishIdTgrEfecto
     */
    public void setMishIdTgrEfecto(String value) {
        setAttributeInternal(MISHIDTGREFECTO, value);
    }

    /**
     * Gets the attribute value for the calculated attribute MishIdGraEfecto.
     * @return the MishIdGraEfecto
     */
    public Number getMishIdGraEfecto() {
        return (Number)getAttributeInternal(MISHIDGRAEFECTO);
    }

    /**
     * Sets <code>value</code> as the attribute value for the calculated attribute MishIdGraEfecto.
     * @param value value to set the  MishIdGraEfecto
     */
    public void setMishIdGraEfecto(Number value) {
        setAttributeInternal(MISHIDGRAEFECTO, value);
    }

    /**
     * Gets the attribute value for the calculated attribute DescIshikawaTrans.
     * @return the DescIshikawaTrans
     */
    public String getDescIshikawaTrans() {
        return (String)getAttributeInternal(DESCISHIKAWATRANS);
    }

    /**
     * Sets <code>value</code> as the attribute value for the calculated attribute DescIshikawaTrans.
     * @param value value to set the  DescIshikawaTrans
     */
    public void setDescIshikawaTrans(String value) {
        setAttributeInternal(DESCISHIKAWATRANS, value);
    }

    /**
     * Gets the associated <code>RowIterator</code> using master-detail link IshikawaLin.
     */
    public RowIterator getIshikawaLin() {
        return (RowIterator)getAttributeInternal(ISHIKAWALIN);
    }

    /**
     * Gets the associated <code>RowIterator</code> using master-detail link Grafico.
     */
    public RowIterator getGrafico() {
        return (RowIterator)getAttributeInternal(GRAFICO);
    }

    /**
     * getAttrInvokeAccessor: generated method. Do not modify.
     * @param index the index identifying the attribute
     * @param attrDef the attribute

     * @return the attribute value
     * @throws Exception
     */
    protected Object getAttrInvokeAccessor(int index, AttributeDefImpl attrDef) throws Exception {
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
    protected void setAttrInvokeAccessor(int index, Object value, AttributeDefImpl attrDef) throws Exception {
        if ((index >= AttributesEnum.firstIndex()) && (index < AttributesEnum.count())) {
            AttributesEnum.staticValues()[index - AttributesEnum.firstIndex()].put(this, value);
            return;
        }
        super.setAttrInvokeAccessor(index, value, attrDef);
    }
}