import java.util.LinkedHashMap;

public class Main {

    public static void main(String[] args) {

        //SE CREA EL CSV READER Y SE LLENA EL MAPA CON LAS MÁQUINAS Y SUS VALORES DE FABRICACIÓN
        CSVreader CSVreader = new CSVreader("./data.csv");

        int totalPieces = CSVreader.getTotalPieces();
        LinkedHashMap<String, Integer> machines = CSVreader.fillMap();


        //SE CREAN LAS CLASES BACKTRACKING Y GREEDY PARA RESOLVER LAS CONSIGNAS
        Backtracking backtracking = new Backtracking();
        Greedy greedy = new Greedy();

        //SE IMPRIMEN LAS SOLUCIONES FINALES
        backtracking.solve(machines, totalPieces);
        greedy.solve(machines, totalPieces);
    }

}
