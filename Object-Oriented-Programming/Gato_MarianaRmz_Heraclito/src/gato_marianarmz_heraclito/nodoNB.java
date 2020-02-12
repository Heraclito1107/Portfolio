
package gato_marianarmz_heraclito;

import java.util.ArrayList;


public class nodoNB<T> {
    public T dato;
    public nodoNB<T> padre;
    public ArrayList<nodoNB<T>> hijos;
    
    public nodoNB (T valor){
        dato=valor;
        padre=null;
        hijos = new ArrayList();
    }
    
    public boolean isLeaf(){
        return hijos.isEmpty();
    }
    
}
