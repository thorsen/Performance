package es.ramondin.performance.modelOracle.views;

import es.ramondin.performance.modelOracle.views.common.TurnoVO;

import java.util.ArrayList;

import oracle.jbo.RowSetIterator;
import oracle.jbo.server.ViewObjectImpl;


// ---------------------------------------------------------------------
// ---    File generated by Oracle ADF Business Components Design Time.
// ---    Fri Jun 13 10:14:50 CEST 2014
// ---    Custom code may be added to this class.
// ---    Warning: Do not modify method signatures of generated methods.
// ---------------------------------------------------------------------
public class TurnoVOImpl extends ViewObjectImpl implements TurnoVO {
    /**
     * This is the default constructor (do not remove).
     */
    public TurnoVOImpl() {
    }
    
    public String[] getClavesTurnos() {
        String[] res = null;
        
        RowSetIterator rsiLineas = this.getRowSetIterator();

        if (rsiLineas.getRowCount() != 0) {
            ArrayList<String> resAux = new ArrayList<String>();
            
            TurnoVORowImpl rowTurno = (TurnoVORowImpl)rsiLineas.first();
            while (rowTurno != null) {
                resAux.add(rowTurno.getPerfTurno());
                rowTurno = (TurnoVORowImpl)rsiLineas.next();
            }
            
            //Restablecemos el iterador
            rsiLineas.reset();
            
            int resSize = resAux.size();
            res = new String[resSize];
            
            for (int i = 0; i < resSize; i++) {
                res[i] = resAux.get(i);
            }
        }
        
        return res;
    }
    
    public Integer[] getIndicesTurnos() {
        Integer[] res = null;
        
        RowSetIterator rsiLineas = this.getRowSetIterator();

        if (rsiLineas.getRowCount() != 0) {
            ArrayList<Integer> resAux = new ArrayList<Integer>();
            
            TurnoVORowImpl rowTurno = (TurnoVORowImpl)rsiLineas.first();
            while (rowTurno != null) {
                resAux.add(getCurrentRowIndex());
                rowTurno = (TurnoVORowImpl)rsiLineas.next();
            }
            
            //Restablecemos el iterador
            rsiLineas.reset();
            
            int resSize = resAux.size();
            res = new Integer[resSize];
            
            for (int i = 0; i < resSize; i++) {
                res[i] = resAux.get(i);
            }
        }
        
        return res;
    }
}
