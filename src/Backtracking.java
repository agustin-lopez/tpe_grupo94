import java.util.ArrayList;
import java.util.LinkedHashMap;

public class Backtracking {

    private int totalPieces;
    private LinkedHashMap<String, Integer> machines;
    private ArrayList<String> bestSolution;
    private int waste;
    private int states;
    private ArrayList<String> machinesList;


    public Backtracking() {
        this.totalPieces = 0;
        this.waste = 0;
        this.machines = new LinkedHashMap<>();
        this.bestSolution = new ArrayList<>();
        this.machinesList = new ArrayList<>();
    }

    //GETTERS

    public int getStates() { return this.states; }

    public int getWaste() { return this.waste; }

    public ArrayList<String> getBestSolution() { return this.bestSolution; }


    //MÉTODOS

    private void clear() {
        this.bestSolution.clear();
        this.machines.clear();
        this.waste = 0;
        this.states = 0;
        this.totalPieces = 0;
    }

    public void solve(LinkedHashMap<String, Integer> machines, int totalPieces) {

        this.clear();

        this.machines.putAll(machines);
        this.totalPieces = totalPieces;
        this.waste = totalPieces;

        for(String m : machines.keySet()){
            machinesList.add(m);
        }


        //POR CADA MÁQUINA DE LA LISTA, SE LLAMA AL MÉTODO RECURSIVO
        // (PARA EVITAR ÁRBOLES REPETIDOS)
        int i = 0;
        while (i <= machinesList.size()-1) {
            this.solve(i, 0, new ArrayList<>());
            i++;
        }

        this.print();

    }

    //ESTA SOLUCIÓN TOMA COMO PRIORIDAD QUE SE DESPERDICIE LA MENOR CANTIDAD DE PIEZAS POSIBLES.
    //CUANDO ENCUENTRA UNA NUEVA SOLUCIÓN VÁLIDA QUE TIENE EL MISMO DESPERDICIO DE PIEZAS QUE LE MEJOR SOLUCIÓN,
    //SE QUEDA CON LA QUE USE MENOS CANTIDAD DE MÁQUINAS

    //-EL ÁRBOL DE EXPLORACIÓN SE GENERA A PARTIR DEL AGREGADO DE TODAS LAS MÁQUINAS DISPONIBLES PARA ANALIZAR TODAS LAS COMBINACIONES POSIBLES.
    //-LOS ESTADOS SE CONSIDERAN "FINALES" CUANDO LA CAPACIDAD TOTAL DE LAS MÁQUINAS SELECCIONADAS LLEGA (O SUPERA) LA CANTIDAD DE PIEZAS A FABRICAR.
    //-LOS ESTADOS SE CONSIDERAN "SOLCUIÓN" CUANDO SE LLEGA A UNA SOLUCIÓN FINAL QUE DESPERDICIE LA MENOR CANTIDAD DE PIEZAS,
    // Y TENGA UN TAMAÑO MENOR A LA ANTERIOR (EN CASO DE QUE TENGAN EL MISMO DESPERDICIO)
    private void solve( int machinePos, int sum, ArrayList<String> solution) {

        //SE SUMA UN ESTADO AL CONTADOR
        this.states++;

        //CONDICIÓN DE CORTE: QUE LA CAPACIDAD TOTAL DE LAS MÁQUINAS EN LA SOLUCIÓN ACTUAL
        //SEA MAYOR O IGUAL A LA CANTIDAD DE PIEZAS A FABRICAR
        if (sum >= totalPieces) {
            System.out.println(solution + ", sum = " + sum);
            //SE CALCULA CUÁNTAS PIEZAS SE DESPERDICIAN
            int currentWaste = sum - totalPieces;
            //SI EL DESPERDICIO ES MENOR AL DE LA MEJOR SOLUCIÓN ENCONTRADA HASTA EL MOMENTO...
            if (currentWaste < this.waste) {
                //SE TOMA COMO UNA NUEVA MEJOR SOLUCIÓN
                this.bestSolution.clear();
                this.bestSolution.addAll(solution);
                //Y SE ACTUALIZA EL DESPERDICIO
                this.waste = currentWaste;
            }
            //SI NO ES MENOR, PREGUNTA SI EL DESPERDICIO ES IGUAL Y SI LA SOLUCIÓN ES DE MENOR TAMAÑO QUE LA MEJOR HASTA EL MOMENTO
            else if ((currentWaste == this.waste) && (solution.size() < this.bestSolution.size())) {
                //EN CASO DE CUMPLIR ESA CONDICIÓN, SIGNIFICA QUE ESTA SOLUCIÓN DESPERDICIA LA MISMA CANTIDAD DE PIEZAS
                //PERO USA MENOS CANTIDAD DE MÁQUINAS (O SEA, ES MEJOR)
                this.bestSolution.clear();
                this.bestSolution.addAll(solution);
                //Y SE ACTUALIZA EL DESPERDICIO
                this.waste = currentWaste;
            }
            //(SI NO CUMPLE NINGUNA DE LAS CONDICIONES ANTERIORES ES PORQUE EL DESPERDICIO ES MAYOR, Y SE DESCARTA ESA SOLUCIÓN)
        }
        //SI NO CUMPLE LA CONDICIÓN DE CORTE...
        else {
            //SE AGREGA LA MÁQUINA ACTUAL A LA SOLUCIÓN
            solution.add(machinesList.get(machinePos));
            //SE SUMA SU CAPACIDAD DE FABRICACIÓN A LA SUMA TOTAL
            sum = sum + machines.get(machinesList.get(machinePos));
            //POR CADA MÁQUINA DE LA LISTA...

            while (machinePos <= machinesList.size()-1) {
                this.solve(machinePos, sum, solution);
                machinePos++;
                solution.removeLast();
            }

        }

    }

    public void print() {

        int total = this.totalPieces + this.waste;

        System.out.println();

        System.out.println("BACKTRACKING SOLUTION:");
        System.out.println("PIECES PRODUCED: " + total);
        System.out.print("BEST SOLUTION FOUND: ");

        for (String m : bestSolution) {
            System.out.print(m + " (" + this.machines.get(m) + ") ");
        }

        System.out.println();
        System.out.println("MACHINES RUNNING: " + this.bestSolution.size());
        System.out.println("PIECES WASTED: " + this.getWaste());
        System.out.println("STATES GENERATED: " + this.getStates());
    }

}
