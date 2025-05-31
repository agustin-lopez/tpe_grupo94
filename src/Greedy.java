import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class Greedy {


    //ESTA SOLUCIÓN CREA EL CONJUNTO SOLUCIÓN TOMANDO LA MÁQUINA DE MAYOR CAPACIDAD EN CADA ITERACIÓN (SIN QUE SE PASE DE LA CANTIDAD DE PIEZAS A FABRICAR)
    //HASTA LLEGAR A LA CANTIDAD REQUERIDA DE PIEZAS.
    //-LOS CANDIDATOS ESTÁN COMPUESTOS POR TODAS LAS MÁQUINAS DISPONIBLES.
    //-PARA LA SELECCIÓN DEL MEJOR CANDIDATO BUSCAMOS LA MÁQUINA QUE TENGA MAYOR PRODUCCIÓN DE PIEZAS Y QUE NO EXCEDA LA CANTIDAD A FABRICAR.
    // EN CASO DE QUE NO HAYA NINGUNA, ELEGIMOS LA QUE MENOR DESPERDICIO NOS DEJE.

    private int totalPieces;
    private LinkedHashMap<String, Integer> machines;
    private ArrayList<String> solution;
    private int candidates;
    private ArrayList<String> machinesList;

    public Greedy(){
        this.totalPieces = 0;
        this.machines = new LinkedHashMap<>();
        this.solution = new ArrayList<>();
        this.machinesList = new ArrayList<>();
    }

    public int getCandidates() { return this.candidates; }

    public ArrayList<String> getSolution() { return this.solution; }

    private void clear() {
        this.machines.clear();
        this.candidates = 0;
        this.totalPieces = 0;
    }

    public void solve(LinkedHashMap<String, Integer> machines, int totalPieces) {

        this.clear();
        this.sort(machines);
        this.totalPieces = totalPieces;


        for(String m : this.machines.keySet()){
            machinesList.add(m);
        }

        this.solve();
        this.print();

    }

    private void solve() {


        int total = 0;

        //MIENTRAS QUE LA CANTIDAD DE PIEZAS ACUMULADAS NO SUPERE LA CANTIDAD DE PIEZAS A FABRICAR...
        while (total < this.totalPieces) {
            //SE SELECCIONA LA POSICIÓN DE MEJOR MÁQUINA DISPONIBLE, ENVIANDO COMO PARÁMETRO LA CANTIDAD DE PIEZAS QUE QUEDA POR FABRICAR
            int m = this.select(totalPieces - total);
            //SI LA POSICIÓN ES -1, SIGNIFICA QUE TODAS LAS MÁQUINAS DESPERDICIAN PIEZAS...
            if (m == -1) {
                //ENTONCES SE AGREGA LA PRIMERA DE LA LISTA, QUE ES LA DE MENOR CAPACIDAD DE FABRICACIÓN (POR ENDE, LA QUE MENOS DESPERDICIA)
                this.solution.add(machinesList.getFirst());
                //Y SE SUMA AL TOTAL DE PIEZAS
                total += machines.get(machinesList.getFirst());
            }
            //SI NO ES -1, SIGNIFICA QUE LA MÁQUNIA SELECCIONADA NO EXCEDE EL LÍMITE...
            else
                //SE AGREGA A LA SOLUCIÓN
                this.solution.add(machinesList.get(m));
                //Y SE SUMA AL TOTAL DE PIEZAS
                total+= machines.get(machinesList.get(m));
        }

    }

    private int select(int diff) {

        //INICIALIZAMOS EL INDICE CON EL TAMAÑO DEL ARREGLO DE MAQUINAS
        int i = this.machinesList.size()-1;
        int selected = -1;
        boolean stop = false;

        //MIENTRAS EL INDICE SEA MAYOR A 0 Y NO SE ENCONTRÓ UNA SOLUCION
        while(i > 0 && !stop) {
            //EN CASO DE QUE EL ESPACIO DE LA MAQUINA SEA MENOR O IGUAL A LOS RESTOS...
            if (this.machines.get(machinesList.get(i)) <= diff) {
                //SUMAMOS LOS CANDIDATOS VISTOS Y LA TOMAMOS COMO LA MEJOR
                this.candidates++;
                selected = i;
                stop = true;
            }
            i--;
        }

        //RETORNAMOS LA MEJOR MAQUINA ELEGIDA PARA LA OPERACION
        return selected;
    }

    private void sort(LinkedHashMap<String, Integer> machines){
        //ORDENAMOS LAS MAQUINAS POR SU CAPACIDAD DE FABRICACIÓN PARA FACILITAR LA BUSQUEDA DE LA MEJOR...
        Map<String, Integer> sorted = machines.entrySet()
                .stream()
                .sorted(Map.Entry.comparingByValue())
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (e1, e2) -> e1,
                        LinkedHashMap::new
                ));

        this.machines.putAll(sorted);

    }

    private void print() {
        System.out.println();
        System.out.println("PIECES TO BE FABRICATED: " + this.totalPieces);
        System.out.print("BEST SOLUTION FOUND: ");

        for (String m : solution) {
            System.out.print(m + " (" + this.machines.get(m) + ") ");
        }

        System.out.println();
        System.out.println("MACHINES CONSIDERED: " + this.getCandidates());
    }

}
