// I301B, 017, Floyd et les rues de Pallo Alto 2016, E09, S03, Q1, 2017-2018

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

class FloydTarjanv2 {
    static Scanner scanner = new Scanner(System.in);

    private static class Node {
        private int ligne;
        private int colonne;

        public Node(int ligne, int colonne) {
            this.ligne = ligne;
            this.colonne = colonne;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            Node node = (Node) o;

            if (ligne != node.ligne) return false;
            return colonne == node.colonne;
        }

        @Override
        public int hashCode() {
            int result = ligne;
            result = 31 * result + colonne;
            return result;
        }
    }

    //Nombre de croquis
    static int t;
    //Nombre de lignes du croquis courant
    static int n;
    //Nombre de colonnes du croquis courant
    static int m;
    //croquis courant
    static char[][] map;

    // Num鲯 de ligne de Floyd
    static int nF;
    // Num鲯 de colonne de Floyd
    static int mF;
    // Num鲯 de ligne de Tarjan
    static int nT;
    // Num鲯 de colonne de Tarjan
    static int mT;

    // Table de pr鳥nce des 鴡ts visit鳍
    // rf est un tableau rectangulaire de n lignes et m colonnes.
    // rf[i][j] == true <==> la case dont le num鲯 de ligne vaut i et
    // dont le num鲯 de colonne vaut j a 鴩 visit饮
    static boolean[][] rf;

    // Coordonn饳 des cases appartenant ࠬa fronti貥.
    // f a une taille logique de n x m;
    // f[0] contient des num鲯s de lignes.
    // f[1] contient des num鲯s de colonnes.
    static int[][] f;

    // Taille logique de f
    // 0 <= f <= n x m;
    static int szf;

    static int nb;

    static Map<Node, Node> parents;

    public static void main(String[] args) {
        t = scanner.nextInt();
        int i = 0;
        while (i != t) {
            parents = new HashMap<>();
            readInputs();
            analyzeMap();
            i++;
        }
    }

    static void analyzeMap() {
        //Obtention des coordonnées GPS
        nb = 0;
        rf[nF][mF] = true;
        addPile(nF, mF);
        //Mais où est donc Tarjan ?
        boolean found = false;
        if(mF == mT && nF == nT) { //Ah il était juste sur nos coordonnées
            System.out.println("0 true"); //Quelle chance
        }
        //Tant que je n'ai pas fais chaque coordonnée possible, je continue
        while(szf != 0) {
            int c[][] = popPile(); //Obtention d'une coordonnée potentielle (Où suis je ?)
            succ(c[0][0], c[1][0]); //Obtention des coordonnées possible
            if(map[c[0][0]][c[1][0]] == 'T') { //Ho tiens voici Tarjan
                found = true;

            }
        }
        if(found) {
            System.out.println("true");
            createPath();
        } else {
            System.out.println("false");
        }
        printMap();
    }

    private static void printMap() {
        for(int i = 0; i < map.length; i++) {
            String ligne = "";
            for(int j = 0; j < map[i].length; j++) {
                ligne += map[i][j];
            }
            System.out.println(ligne);
        }
    }

    private static void createPath() {
        Node g = new Node(nT, mT);
        while(parents.containsKey(g)) {
            Node p = parents.get(g);
            if(map[p.ligne][p.colonne] == '.') {
                map[p.ligne][p.colonne] = '+';
            }
            g = p;
        }
    }

    static void addPile(int ligne, int colonne) {
        nb++;
        rf[ligne][colonne] = true;
        f[0][szf] = ligne;
        f[1][szf] = colonne;
        szf++;
    }

    static int[][] popPile(){
        int elem[][] = new int[2][1];
        elem[0][0] = f[0][szf - 1];
        elem[1][0] = f[1][szf - 1];
        szf--;
        return elem;
    }

    static void succ(int ligne, int colonne) {
        if(ligne < map.length - 1) { //Puis je aller en haut ?
            check(ligne + 1, colonne, ligne, colonne);
        }
        if(ligne != 0) { //Puis je aller en bas ?
            check(ligne-1, colonne, ligne, colonne);
        }
        if(colonne < map[ligne].length - 1) { //Puis je aller à droite ?
            check(ligne, colonne + 1, ligne, colonne);
        }
        if(colonne != 0) { //Puis je aller à gauche ?
            check(ligne, colonne - 1, ligne, colonne);
        }
    }

    private static void check(int ligne, int colonne, int lignePrec, int colonnePrec) {
        //Si je n'ai pas déjà visiter cette case et que je peux y accéder (qu'il s'agisse soit d'un . ou de Tarjan
        if(!rf[ligne][colonne] && (map[ligne][colonne] == '.' || map[ligne][colonne] == 'T')) {
            addPile(ligne, colonne); //Alors j'essaye de m'en souvenir
            parents.put(new Node(ligne, colonne), new Node(lignePrec, colonnePrec));
        }
    }

    // Cette m鴨ode lit les informations sur l'entr饠standard
    // et initialise les attributs relatifs au croquis courant
    // et ࠳on parcours.
    static void readInputs() {
        n = scanner.nextInt();
        m = scanner.nextInt();
        scanner.nextLine();

        map = new char[n][m];
        rf = new boolean[n][m];
        f = new int[2][n * m];
        szf = 0;

        int i = 0;
        while (i != n) {
            map[i] = scanner.nextLine().toCharArray();

            int j = 0;
            while (j != m) {
                if (map[i][j] == 'F') {
                    nF = i;
                    mF = j;
                }
                if (map[i][j] == 'T') {
                    nT = i;
                    mT = j;
                }
                j++;
            }
            i++;
        }
    }
}