import java.util.ArrayList;
import java.util.HashMap;

public class Backtracking {

    private int totalPieces;
    private HashMap<String, Integer> machines;
    private HashMap<String, Integer> solution;
    private HashMap<String, Integer> bestSolution;
    private int states;

    public Backtracking(HashMap<String, Integer> machines, Integer totalPieces) {
        this.totalPieces = totalPieces;
        this.machines = machines;
        this.solution = new HashMap<>();
        this.bestSolution = new HashMap<>();
    }

    public Backtracking solve() {

        solve(machines.keySet().stream().findFirst().get(), 0);

        return this;

    }

    private void solve(String currentMachine, int sum) {
        //SE AGREGA LA MÁQUINA ACTUAL A LA SOLUCIÓN
        this.solution.put(currentMachine, machines.get(currentMachine));
        //SE SUMA SU CAPACIDAD A LA SUMA TOTAL
        sum = sum + machines.get(currentMachine);


        System.out.println("sum = " + sum + ", " + solution.toString());

        //CONDICIÓN DE CORTE: QUE LA CAPACIDAD TOTAL DE LAS MÁQUINAS SEA MAYOR O IGUAL A LA CAPACIDAD NECESARIA
        if (sum >= totalPieces){
            //SI LA MEJOR SOLUCIÓN ESTÁ VACÍA (TODAVÍA NO HAY NINGUNA)
            //O SI LA SOLUCIÓN ACTUAL ES MÁS CHICA QUE LA MEJOR ENCONTRADA HASTA EL MOMENTO, SE REEMPLAZA
            if ((bestSolution.size() == 0) || (solution.size() < bestSolution.size())) {
                System.out.println("------------------------> nueva solución <------------------------");
                System.out.println(solution.toString());
                bestSolution = solution;
            }
        }
        //SI NO CUMPLE LA CONDICIÓN DE CORTE...
        else {

            for (String m : machines.keySet()) {
                this.solve(m, sum);
                this.solution.remove(m);
            }

        }


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
