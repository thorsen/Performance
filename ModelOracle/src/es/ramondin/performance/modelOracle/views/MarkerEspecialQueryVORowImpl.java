package es.ramondin.performance.modelOracle.views;

import oracle.jbo.domain.Number;
import oracle.jbo.server.AttributeDefImpl;
import oracle.jbo.server.ViewRowImpl;
// ---------------------------------------------------------------------
// ---    File generated by Oracle ADF Business Components Design Time.
// ---    Thu Jun 19 09:11:38 CEST 2014
// ---    Custom code may be added to this class.
// ---    Warning: Do not modify method signatures of generated methods.
// ---------------------------------------------------------------------
public class MarkerEspecialQueryVORowImpl extends ViewRowImpl {
    /**
     * AttributesEnum: generated enum for identifying attributes and accessors. DO NOT MODIFY.
     */
    public enum AttributesEnum {
        MmesTextoMarker {
            public Object get(MarkerEspecialQueryVORowImpl obj) {
                return obj.getMmesTextoMarker();
            }

            public void put(MarkerEspecialQueryVORowImpl obj, Object value) {
                obj.setMmesTextoMarker((String)value);
            }
        }
        ,
        MmesValorMarker {
            public Object get(MarkerEspecialQueryVORowImpl obj) {
                return obj.getMmesValorMarker();
            }

            public void put(MarkerEspecialQueryVORowImpl obj, Object value) {
                obj.setMmesValorMarker((Number)value);
            }
        }
        ;
        private static AttributesEnum[] vals = null;
        private static final int firstIndex = 0;

        public abstract Object get(MarkerEspecialQueryVORowImpl object);

        public abstract void put(MarkerEspecialQueryVORowImpl object, Object value);

        public int index() {
            return AttributesEnum.firstIndex() + ordinal();
        }

        public static final int firstIndex() {
            return firstIndex;
        }

        public static int count() {
            return AttributesEnum.firstIndex() + AttributesEnum.staticValues().length;
        }

        public static final AttributesEnum[] staticValues() {
            if (vals == null) {
                vals = AttributesEnum.values();
            }
            return vals;
        }
    }


    public static final int MMESTEXTOMARKER = AttributesEnum.MmesTextoMarker.index();
    public static final int MMESVALORMARKER = AttributesEnum.MmesValorMarker.index();

    /**
     * This is the default constructor (do not remove).
     */
    public MarkerEspecialQueryVORowImpl() {
    }

    /**
     * Gets the attribute value for the calculated attribute MmesTextoMarker.
     * @return the MmesTextoMarker
     */
    public String getMmesTextoMarker() {
        return (String) getAttributeInternal(MMESTEXTOMARKER);
    }

    /**
     * Sets <code>value</code> as the attribute value for the calculated attribute MmesTextoMarker.
     * @param value value to set the  MmesTextoMarker
     */
    public void setMmesTextoMarker(String value) {
        setAttributeInternal(MMESTEXTOMARKER, value);
    }

    /**
     * Gets the attribute value for the calculated attribute MmesValorMarker.
     * @return the MmesValorMarker
     */
    public Number getMmesValorMarker() {
        return (Number) getAttributeInternal(MMESVALORMARKER);
    }

    /**
     * Sets <code>value</code> as the attribute value for the calculated attribute MmesValorMarker.
     * @param value value to set the  MmesValorMarker
     */
    public void setMmesValorMarker(Number value) {
        setAttributeInternal(MMESVALORMARKER, value);
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
