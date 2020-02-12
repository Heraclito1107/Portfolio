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
public class NodoGato {
    private int minimax;
    private ArrayList<NodoGato> children;
    public String [][] board= new String[3][3];
    private NodoGato father;

    public NodoGato(String[][] input) {
        this.board = input;        
    }
    
    public int getMinimax() {
        return minimax;
    }

    public void setMinimax(int minimax) {
        this.minimax = minimax;
    }

    public NodoGato getFather() {
        return father;
    }

    public void setFather(NodoGato father) {
        this.father = father;
    }
    
    boolean finishGame(){  
        if(!board[0][0].equals(" ")){
            if ((board[0][0].equals(board[0][1]))&& (board[0][1].equals(board[0][2]))) 
            {
              if(board[0][0].equals("O"))
              {
               setMinimax(-1);
              }
              if(board[0][0].equals("X"))
              {
               setMinimax(1);
              }
              return true;
            }
            if ((board[0][0].equals(board[1][1]))&& (board[1][1].equals(board[2][2]))) 
            {
              if(board[0][0].equals("O"))
              {
               setMinimax(-1);
              }
              if(board[0][0].equals("X"))
              {
               setMinimax(1);
              }
              return true;
            }
            if ((board[0][0].equals(board[1][0]))&& (board[1][0].equals(board[2][0]))) 
            {
              if(board[0][0].equals("O"))
              {
               setMinimax(-1);
              }
              if(board[0][0].equals("X"))
              {
               setMinimax(1);
              }
              return true;
            }
        }
        if(!board[0][1].equals(" ")){
            if ((board[0][1].equals(board[1][1]))&& (board[1][1].equals(board[2][1]))) 
            {
              if(board[0][0].equals("O"))
              {
               setMinimax(-1);
              }
              if(board[0][0].equals("X"))
              {
               setMinimax(1);
              }
              return true;
            }  
        }
        if(!board[0][2].equals(" ")){
            if ((board[0][2].equals(board[1][1]))&& (board[1][1].equals(board[2][0]))) 
            {
              if(board[0][0].equals("O"))
              {
               setMinimax(-1);
              }
              if(board[0][0].equals("X"))
              {
               setMinimax(1);
              }
              return true;
            } 
            if ((board[0][2].equals(board[1][2]))&& (board[1][2].equals(board[2][2]))) 
            {
              if(board[0][0].equals("O"))
              {
               setMinimax(-1);
              }
              if(board[0][0].equals("X"))
              {
               setMinimax(1);
              }
              return true;
            } 
        }
        if(!board[1][0].equals(" ")){
            if ((board[1][0].equals(board[1][1]))&& (board[1][1].equals(board[1][2]))) 
            {
              if(board[0][0].equals("O"))
              {
               setMinimax(-1);
              }
              if(board[0][0].equals("X"))
              {
               setMinimax(1);
              }
              return true;
            }
        }
        if(!board[2][0].equals(" ")){
            if ((board[2][0].equals(board[2][1]))&& (board[2][1].equals(board[2][2]))) 
            {
              if(board[0][0].equals("O"))
              {
               setMinimax(-1);
              }
              if(board[0][0].equals("X"))
              {
               setMinimax(1);
              }
              return true;
            }
        int cont=0;
        for(int i=0; i<3; i++){
            for(int j=0; j<3; j++){
                    if(!board[i][j].equals(" ")){
                       cont++;
                    }
                }
            }
        if(cont==9){
            return true;
            }
        }  
        return false;
    }
    int winner(){
        if (finishGame()){
            if(getMinimax()==1){
                return 1;
            }
            if(getMinimax()==0){
                return 0;
            }
            if(getMinimax()==-1){
                return -1;
            }
        }
       return 234567890; 
    }

    public ArrayList<NodoGato> getChildren() {
        return children;
    }

    public void addChildren(NodoGato child) 
    {
        this.children.add(child);
    }
    
}
