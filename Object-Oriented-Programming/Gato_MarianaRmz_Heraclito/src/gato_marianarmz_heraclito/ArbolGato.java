/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gato_marianarmz_heraclito;

import java.util.ArrayList;

/**
 *
 * @author CIAC
 */
public class ArbolGato {
    NodoGato root;

    public ArbolGato(NodoGato root) {
        this.root = root;
        root.setFather(null);
    }
    
    int crearArbol(NodoGato nodo){
        String [][] actual;
        actual = nodo.board;
        ArrayList<Integer> emptyX = new ArrayList();
        ArrayList<Integer> emptyY = new ArrayList();
        
        for(int i = 0; i < 3; i++)
        {
            for(int j = 0; j < 3; j++)
            {
                if(actual[i][j].equals(" ")){
                    emptyY.add(j);
                    emptyX.add(i);
                }
            }
        }
        for (int i = 0; i < emptyX.size(); i++)
        { 
            String [][] siguiente = actual;

            if(depth(nodo)%2==0){
                siguiente[emptyX.get(i)][emptyY.get(i)] = "X";
            }
            else
            {
                siguiente[emptyX.get(i)][emptyY.get(i)] = "O";
            }
            
            NodoGato nodito = new NodoGato(siguiente);
            nodito.setFather(nodo);
            nodo.addChildren(nodito);
        }
        
        if (!nodo.finishGame())
        {
            int temp = 0;
            for (int i = 0; i < nodo.getChildren().size(); i++)
            {
                int temp2 = crearArbol(nodo.getChildren().get(i));
                if(depth(nodo)%2==0)
                {
                    if (temp2 > temp)
                    {
                        temp = temp2;
                    }
                    
                }
                else
                {
                    if (temp2 < temp)
                    {
                        temp = temp2;
                    }
                }
                
            }
            nodo.setMinimax(temp);
            return temp;
        }
          
        return nodo.getMinimax();
    }
    int depth(NodoGato nodo){
        if(nodo.getFather()== null)
        {
            return 0;
        }
        return 1+depth(nodo.getFather());
    }
    
}
