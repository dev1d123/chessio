/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package source;

import java.util.ArrayList;

/**
 *
 * @author Windows
 */
public class Rey extends Pieza implements PiezaInterfaz{
        //Inicia el juego y cuando se corona
    //x, y, signo
    public Rey(int x, int y) {
        super(x, y, 'R');
    }

    @Override
    public ArrayList<Pair> getMovimientos() {
        ArrayList<Pair> res = new ArrayList<>();
        for(int i = 0; i < 8; i++){
            for(int j = 0; j < 8; j++){
                
                if(Math.abs(getX()-i) == 1 && getY() == j){
                    Pair par = new Pair(i, j);
                    res.add(par);
                }
                if(Math.abs(getY()-j) == 1 && getX() == i){
                    Pair par = new Pair(i, j);
                    res.add(par);
                }
                if(Math.abs(getX()-i) == 1 && Math.abs(getY()-j) == 1){
                    Pair par = new Pair(i, j);
                    res.add(par);
                }
            }
        }
        return res;
    }
    public String getM(){
        ArrayList<Pair> res = getMovimientos();
        String ans = "";
        for(Pair p: res){
            ans+= "("+p.X+", "+p.Y+")\n";
        }
        return ans;
    }
    public static void main(String[] args){
        Rey test = new Rey(4,4);
        System.out.println(test.getM());
    }
    
}
