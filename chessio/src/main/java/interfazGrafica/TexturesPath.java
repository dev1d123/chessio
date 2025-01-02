package interfazGrafica;


public class TexturesPath {
    public static String getPath(boolean color, int kind, int piece) {
        String path = "piecesImage/";
        if (color) { // Negro
            path += "black/tile";
            int[] blackOrder = {0, 1, 2, 3, 4, 5};
            piece = blackOrder[piece];
        } else { // Blanco
            path += "white/tile";
            int[] whiteOrder = {5, 4, 3, 2, 1, 0};
            piece = whiteOrder[piece];
        }
        int num = kind * 6 + piece;
        String select = String.format("%03d", num);
    
        return path + select + ".png";
    }
    


    public static String textureToInt(int code) {
        if (code == 0) {
            return "Classic";
        } else if (code == 1) {
            return "Medieval"; 
        } else if (code == 2) {
            return "Fantasy"; 
        } else if (code == 3) {
            return "Terror"; 
        } else if (code == 4) {
            return "Animals"; 
        } else if (code == 5) {
            return "Reptiles";
        } else if (code == 6) {
            return "Dino"; 
        } else if (code == 7) {
            return "Aquatic 1";
        } else if (code == 8) {
            return "Aquatic 2";
        } else if (code == 9) {
            return "Insects";
        } else {
            return "Unknown";
        }
    }
    
}

 
