package es.ramondin.performance.modelOracle.views;

import oracle.jbo.server.AttributeDefImpl;
import oracle.jbo.server.ViewRowImpl;
// ---------------------------------------------------------------------
// ---    File generated by Oracle ADF Business Components Design Time.
// ---    Wed Apr 30 10:45:09 CEST 2014
// ---    Custom code may be added to this class.
// ---    Warning: Do not modify method signatures of generated methods.
// ---------------------------------------------------------------------
public class ParametroVORowImpl extends ViewRowImpl {
    /**
     * AttributesEnum: generated enum for identifying attributes and accessors. DO NOT MODIFY.
     */
    public enum AttributesEnum {
        Param {
            public Object get(ParametroVORowImpl obj) {
                return obj.getParam();
            }

            public void put(ParametroVORowImpl obj, Object value) {
                obj.setParam((String)value);
            }
        }
        ,
        Valor {
            public Object get(ParametroVORowImpl obj) {
                return obj.getValor();
            }

            public void put(ParametroVORowImpl obj, Object value) {
                obj.setValor((String)value);
            }
        }
        ,
        Empresa {
            public Object get(ParametroVORowImpl obj) {
                return obj.getEmpresa();
            }

            public void put(ParametroVORowImpl obj, Object value) {
                obj.setEmpresa((String)value);
            }
        }
        ,
        Descripcion {
            public Object get(ParametroVORowImpl obj) {
                return obj.getDescripcion();
            }

            public void put(ParametroVORowImpl obj, Object value) {
                obj.setDescripcion((String)value);
            }
        }
        ;
        private static AttributesEnum[] vals = null;
        private static final int firstIndex = 0;

        public abstract Object get(ParametroVORowImpl object);

        public abstract void put(ParametroVORowImpl object, Object value);

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
    public static final int PARAM = AttributesEnum.Param.index();
    public static final int VALOR = AttributesEnum.Valor.index();
    public static final int EMPRESA = AttributesEnum.Empresa.index();
    public static final int DESCRIPCION = AttributesEnum.Descripcion.index();

    /**
     * This is the default constructor (do not remove).
     */
    public ParametroVORowImpl() {
    }

    /**
     * Gets the attribute value for the calculated attribute Param.
     * @return the Param
     */
    public String getParam() {
        return (String) getAttributeInternal(PARAM);
    }

    /**
     * Sets <code>value</code> as the attribute value for the calculated attribute Param.
     * @param value value to set the  Param
     */
    public void setParam(String value) {
        setAttributeInternal(PARAM, value);
    }

    /**
     * Gets the attribute value for the calculated attribute Valor.
     * @return the Valor
     */
    public String getValor() {
        return (String) getAttributeInternal(VALOR);
    }

    /**
     * Sets <code>value</code> as the attribute value for the calculated attribute Valor.
     * @param value value to set the  Valor
     */
    public void setValor(String value) {
        setAttributeInternal(VALOR, value);
    }

    /**
     * Gets the attribute value for the calculated attribute Empresa.
     * @return the Empresa
     */
    public String getEmpresa() {
        return (String) getAttributeInternal(EMPRESA);
    }

    /**
     * Sets <code>value</code> as the attribute value for the calculated attribute Empresa.
     * @param value value to set the  Empresa
     */
    public void setEmpresa(String value) {
        setAttributeInternal(EMPRESA, value);
    }

    /**
     * Gets the attribute value for the calculated attribute Descripcion.
     * @return the Descripcion
     */
    public String getDescripcion() {
        return (String) getAttributeInternal(DESCRIPCION);
    }

    /**
     * Sets <code>value</code> as the attribute value for the calculated attribute Descripcion.
     * @param value value to set the  Descripcion
     */
    public void setDescripcion(String value) {
        setAttributeInternal(DESCRIPCION, value);
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