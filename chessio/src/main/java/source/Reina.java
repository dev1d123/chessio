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
public class Reina extends Pieza implements PiezaInterfaz{
        //Inicia el juego y cuando se corona
    //x, y, signo
    public Reina(int x, int y, Player player) {
        super(x, y, 'Q', player);
    }

    @Override
    public ArrayList<Pair> getMovimientos() {
        ArrayList<Pair> res = new ArrayList<>();
        for(int i = 0; i < 8; i++){
            for(int j = 0; j < 8; j++){
                
                if(getX() == i && getY() ==j) continue;
                if(Math.abs(getX()-i) == Math.abs(getY()-j)){
                    Pair par = new Pair(i, j);
                    res.add(par);
                }
                if(this.getX() == i || this.getY() == j){
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
    
}
