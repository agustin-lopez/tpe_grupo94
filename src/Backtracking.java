import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.LinkedHashMap;

public class Backtracking {

    private int totalPieces;
    private HashMap<String, Integer> machines;
    private HashMap<String, Integer> solution;
    private HashMap<String, Integer> bestSolution;
    private int states;

    public Backtracking(LinkedHashMap<String, Integer> machines, Integer totalPieces) {
        this.totalPieces = totalPieces;
        this.waste = totalPieces;
        this.machines = machines;
        this.bestSolution = new ArrayList<>();
    }

    public Backtracking solve() {

        //SE LIMPIAN LAS SOLUCIONES
        this.bestSolution.clear();

        //String first = machines.keySet().stream().findFirst().get();

        //SE LLAMA AL MÉTODO RECURSIVO, EMPEZANDO DESDE LA PRIMERA MÁQUINA
        for (String m : machines.keySet()) {
            solve(m, 0, new ArrayList<>());
        }

        return this;

    }

    //14;
    //MACHINE-A;5
    //MACHINE-B;7
    //MACHINE-C;1
    //MACHINE-D;2
    //MACHINE-E;6

    private void solve(String currentMachine, int sum, ArrayList<String> solution) {

        //SE AGREGA LA MÁQUINA ACTUAL A LA SOLUCIÓN
        solution.add(currentMachine);
        //SE SUMA SU CAPACIDAD A LA SUMA TOTAL
        sum = sum + machines.get(currentMachine);

        System.out.println(solution.toString() + ", sum = " + sum);

        //CONDICIÓN DE CORTE: QUE LA CAPACIDAD TOTAL DE LAS MÁQUINAS SEA MAYOR O IGUAL A LA CAPACIDAD NECESARIA
        if (sum >= totalPieces) {
            //SE CALCULA CUÁNTAS PIEZAS SE DESPERDICIAN
            int currentWaste = sum - totalPieces;
            //SI EL DESPERDICIO ES MENOR...
            if (currentWaste < this.waste) {
                //SE TOMA COMO MEJOR SOLUCIÓN
                this.bestSolution.clear();
                this.bestSolution.addAll(solution);
                //Y SE ACTUALIZA EL DESPERDICIO
                this.waste = currentWaste;
            }
            //SI NO ES MENOR, PREGUNTA SI EL DESPERDICIO ES IGUAL Y SI LA SOLUCIÓN ES DE MENOR TAMAÑO QUE LA MEJOR
            else if ((currentWaste == this.waste) && (solution.size() < this.bestSolution.size())) {
                this.bestSolution.clear();
                this.bestSolution.addAll(solution);
                //Y SE ACTUALIZA EL DESPERDICIO
                this.waste = currentWaste;
            }
            //(SI NO CUMPLE NINGUNA ES PORQUE EL DESPERDICIO ES MAYOR, Y SE DESCARTA ESA SOLUCIÓN)
        }


//        if (sum >= totalPieces) {
//            //SE CALCULA CUÁNTAS PIEZAS SE DESPERDICIAN
//            int currentWaste = sum - totalPieces;
//            System.out.println("desperdicio: " + currentWaste);
//            //LA SOLUCIÓN ACTUAL SE VUELVE LA MEJOR SI:
//            //1. LA MEJOR SOLUCIÓN ESTÁ VACÍA
//            //2. SI LA SOLUCIÓN ACTUAL ES MÁS CHICA QUE LA MEJOR ENCONTRADA HASTA EL MOMENTO Y TIENE MENOS DESPERDICIO DE PIEZAS
//            if ((bestSolution.size() == 0) || ((solution.size() < this.bestSolution.size()) && (this.waste >= currentWaste))) {
//                System.out.println("------------------------> nueva solución <------------------------");
//                this.bestSolution.clear();
//                this.bestSolution.addAll(solution);
//
//
//                System.out.println("Mejor Solucion: " + bestSolution.toString());
//            }
//        }
        //SI NO CUMPLE LA CONDICIÓN DE CORTE...
        else {

            for (String m : machines.keySet()) {
                this.solve(m, sum, solution);
                solution.removeLast();
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

    public ArrayList<String> getBestSolution() { return this.bestSolution; }
}
