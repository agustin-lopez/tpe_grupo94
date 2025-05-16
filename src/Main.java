import java.util.HashMap;

public class Main {

    public static void main(String[] args) {

        //SE CREA EL CSV READER Y SE LLENA EL MAPA CON LAS MÁQUINAS Y SUS VALORES DE FABRICACIÓN
        CSVreader CSVreader = new CSVreader("./data.csv");

        int totalPieces = CSVreader.getTotalPieces();
        HashMap<String, Integer> machines = CSVreader.fillMap();

        System.out.println(machines.toString());

        //SE CREAN LAS CLASES BACKTRACKING Y GREEDY PARA RESOLVER LAS CONSIGNAS
        Backtracking backtracking = new Backtracking(machines, totalPieces);
        backtracking.solve();

        System.out.println(backtracking.getBestSolution().toString());

    }

}
