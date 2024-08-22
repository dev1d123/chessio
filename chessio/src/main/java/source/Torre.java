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
    public Torre(int x, int y, Player player) {
        super(x, y, 'T', player);
    }

    @Override
    public ArrayList<Pair> getMovimientos(Tablero tabla) {
        ArrayList<Pair> res = new ArrayList<>();
        //Son 4 casos, sea la posicion, x, y -> xa, xb, cy, dy....
        boolean c1=true, c2=true, c3=true, c4=true;
        //Comenzar en la posicion centrar, luego expandirse
        for(int i = this.getX()+1; i < 8; i++){
            //si hay un amigo ahi...falta, verificar que ninguno sea nulo
            if(this.getPlayer() == tabla.tabla[i][this.getY()].getPieza().getPlayer()){
                c1 = false;
            }
            if(c1){
                Pair par = new Pair(i, this.getY());
                res.add(par);
            }
            //si hay un enemigo ahi
            if(this.getPlayer() != tabla.tabla[i][this.getY()].getPieza().getPlayer()){
                c1 = false;
            }
        }
        for(int i = this.getX()-1; i >= 0; i--){
            if(this.getPlayer() == tabla.tabla[i][this.getY()].getPieza().getPlayer()){
                c1 = false;
            }
            if(c1){
                Pair par = new Pair(i, this.getY());
                res.add(par);
            }
            //si hay un enemigo ahi
            if(this.getPlayer() != tabla.tabla[i][this.getY()].getPieza().getPlayer()){
                c1 = false;
            }
        }
        for(int i = this.getY()+1; i < 8; i++){
            if(this.getPlayer() == tabla.tabla[this.getX()][i].getPieza().getPlayer()){
                c1 = false;
            }
            if(c1){
                Pair par = new Pair(this.getX(), i);
                res.add(par);
            }
            //si hay un enemigo ahi
            if(this.getPlayer() != tabla.tabla[this.getX()][i].getPieza().getPlayer()){
                c1 = false;
            }
        }
        for(int i = this.getY()-1; i >= 0; i--){
            if(this.getPlayer() == tabla.tabla[i][this.getY()].getPieza().getPlayer()){
                c1 = false;
            }
            if(c1){
                Pair par = new Pair(i, this.getY());
                res.add(par);
            }
            //si hay un enemigo ahi
            if(this.getPlayer() != tabla.tabla[i][this.getY()].getPieza().getPlayer()){
                c1 = false;
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
