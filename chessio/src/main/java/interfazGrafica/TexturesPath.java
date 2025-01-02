package interfazGrafica;


public class TexturesPath {
    public static String getPath(boolean color, int kind, int piece){
        String path = "chessio/src/main/resources/piecesImage/";
        if(color){
            path += "black/tile";
        }else{
            path += "white/tile";
        }
        int num = kind*6 + piece;
        String select = String.format("%03d", num);
    
        return path+select+".png";
    }
}

 
