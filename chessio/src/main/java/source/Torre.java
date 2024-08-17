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
public class Torre extends Pieza implements PiezaInterfaz{
    
    //Inicia el juego y cuando se corona
    //x, y, signo
    public Torre(int x, int y) {
        super(x, y, 'T');
    }

    @Override
    public ArrayList<Pair> getMovimientos() {
        ArrayList<Pair> res = new ArrayList<>();
        //...64, enfoques...
        //FB, PD, DyV -> Subproblemas que al resolverse ayudan a resolver el problema general
        for(int i = 0; i < 8; i++){
            for(int j = 0; j < 8; j++){
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
    public static void main(String[] args){
        Torre test = new Torre(4,4);
        System.out.println(test.getM());
    }
    
    
  
}
