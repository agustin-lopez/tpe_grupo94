import java.util.ArrayList;
import java.util.HashMap;

public class Backtracking {

    private int totalPieces;
    private HashMap<String, Integer> machines;
    private ArrayList<String> machinesList;
    private HashMap<String, Integer> solution;
    private HashMap<String, Integer> bestSolution;
    private int states;

    public Backtracking(HashMap<String, Integer> machines, Integer totalPieces) {
        this.totalPieces = totalPieces;
        this.machines = machines;
        this.machinesList = new ArrayList<>();
        this.machinesList.addAll(machines.keySet());
        this.solution = new HashMap<>();
        this.bestSolution = new HashMap<>();
    }

    public Backtracking solve() {

        solve(0, 0);

        return this;

    }

    private void solve(int currentMachine, int sum) {
        //SE AGREGA LA MÁQUINA ACTUAL A LA SOLUCIÓN
        this.solution.put(machinesList.get(currentMachine), machines.get(currentMachine));
        //SE SUMA SU CAPACIDAD A LA SUMA TOTAL
        sum += machines.get(currentMachine);

        //CONDICIÓN DE CORTE: QUE LA CAPACIDAD TOTAL DE LAS MÁQUINAS SEA MAYOR O IGUAL A LA CAPACIDAD NECESARIA
        if (sum >= totalPieces) {
            if (solution.size() < bestSolution.size()) bestSolution = solution;
        }
        else {
            solve(currentMachine, sum);//gg ez
            if (currentMachine + 1 <= machinesList.size()-1) solve(currentMachine + 1, sum);
        }

        solution.remove(currentMachine);

    }

    //Backtraking (estado e)
    //  {
    //      Condición de Corte:
    //      ¿e es una posible solución?
    //  SI:
    //      operar con la solución
    //      Ej.: fijarse si es la mejor hasta el momento, o
    //      agregarla a una lista de soluciones, o
    //      imprimir, etc ,etc
    //  NO:
    //      Para cada hijo c del estado actual e:
    //      Backtraking(c) /// EXPLORAR recursivamente a partir de c
    //}


    public int getStates() { return states; }

    public HashMap<String, Integer> getBestSolution() { return this.bestSolution; }
}
