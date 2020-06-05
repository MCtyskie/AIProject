/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.mycompany.tsp;

import static java.lang.Math.random;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.OptionalInt;
import java.util.Random;
import java.util.Set;
import java.util.stream.IntStream;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import lombok.Setter;
import org.apache.commons.collections4.BidiMap;

//VU6qX1mGKHLEZkb5E1b1~T3DaZuyK6Dn8qF6HxvwggA~AhJvdjQpVUVUVPXMCeEmVYV893RrJ_1cQDQrqNsDvRsxyVMZIMm6C9_7fDvoKGV-

@Setter
public class ACO {
    private double c =1.0;
    private double alpha;//= 1;
    private double beta; //= 5;
    private double evaporation; //= 0.5;
    private double Q; //= 500;
    private double randomFactor= 0.01;
    
    private int iterations;// = 1000;
    
    private List<Ant> ants = new ArrayList<>();
    private int numOfCities;
    private int numOfAnts;
    private double graph[][];
    private double routes[][];
    private Random random = new Random();
    private double probabilities[];
    
    private int currentIdx;
    private int[] bestOrder;
    private double bestLength;
    
    private static BidiMap<Integer,String> citiesMap;
    
    public ACO(int numOfSelectedCities, int colonySize){
        //graph = generateCitiesMatrix(numOfCities); TO DO MATRIX OF CITIES
        graph = generateRandomMatrix(numOfSelectedCities); //FOR TESTING
        numOfCities = graph.length;
        numOfAnts=colonySize;
        System.out.println("CITIES : "+numOfCities+" | ANTS : "+numOfAnts);
        routes = new double[numOfCities][numOfCities];
        probabilities = new double[numOfCities];
        createAnts(numOfAnts);
    }
    
 
    
    public void createAnts(int numOfAnts){
        IntStream.range(0, numOfAnts).forEach(i -> ants.add(new Ant(numOfCities)));
    }
    
    public double[][] allCitiesMatrix(){
        //double[][] matrix=new double[20][20];
        double[][]matrix={ {0,294,272,581,4468,460,81,344,281,160,263,571,798,378,43,142,656,308,111,647},
                           {294,0,349,339,311,311,294,202,134,211,151,282,536,478,292,401,439,263,408,566},
                           {272,349,0,555,312,182,191,539,246,457,427,573,424,122,244,412,575,578,371,394},
                           {581,339,555,0,173,314,519,394,340,558,486,191,200,576,528,684,101,598,695,357},
                           {468,311,312,173,0,140,507,502,226,444,426,286,206,404,414,570,257,568,581,258},
                           {460,311,182,314,140,0,383,502,207,452,434,411,250,270,429,597,404,576,556,264},
                           {81,294,191,519,507,383,0,492,203,265,303,544,721,301,41,221,609,387,180,570},
                           {344,202,539,394,502,502,492,0,319,358,258,214,626,665,490,599,523,330,606,757},
                           {281,134,216,340,226,207,203,319,0,242,254,355,449,343,212,369,430,396,380,473},
                           {160,211,457,558,444,452,265,358,242,0,91,515,674,562,191,187,648,435,284,705},
                           {263,151,427,486,426,434,303,258,254,91,0,366,623,553,262,280,586,145,371,689},
                           {571,282,573,191,286,411,544,214,355,515,366,0,407,701,572,681,292,479,688,509},
                           {798,536,424,200,206,250,721,626,449,674,625,407,0,552,639,939,233,793,898,168},
                           {378,476,122,576,404,270,301,665,343,562,553,701,552,0,347,515,665,681,474,400},
                           {43,292,244,528,414,429,41,490,212,191,262,572,639,347,0,192,618,358,151,619},
                           {142,401,412,684,570,597,221,599,369,187,280,681,939,515,192,0,765,299,125,787},
                           {656,439,575,101,257,404,609,523,403,648,586,292,233,665,618,765,0,698,785,319},
                           {308,263,578,598,568,576,387,303,396,135,145,479,793,681,358,299,698,0,404,830},
                           {111,408,371,695,581,556,180,606,380,284,371,688,898,474,151,125,785,404,0,745},
                           {647,566,394,357,258,264,570,757,473,705,689,509,168,400,619,787,390,830,745,0}};
        return matrix;
    }

    public double[][] generateCitiesMatrix(int numOfCities) {
        double[][] citiesMatrix=new double[numOfCities][numOfCities];
        citiesMap = FXMLController.getSelectedCitiesMap();
        ArrayList<Integer> keys=(ArrayList<Integer>) citiesMap.keySet();
        for(int i=0;i<numOfCities;i++){
            int x = keys.get(i);
            for(int j=0;j<numOfCities;j++){
                int y = keys.get(j);
                // TO DO  fetch from default matrix 
                //citiesMatrix[i][j] = defaultmatrix[x][y];
            }
        }
        return citiesMatrix;
    }
    
    //FOR TESTING
    public double[][] generateRandomMatrix(int n) {
        double[][] randomMatrix = new double[n][n];
        IntStream.range(0, n)
            .forEach(i -> IntStream.range(0, n)
                .forEach(j -> randomMatrix[i][j] = Math.abs(random.nextInt(100) + 1)));
        return randomMatrix;
    }
    
    public void optimize(){
        IntStream.rangeClosed(1, 3)
            .forEach(i -> {
                //TO DO GUI 
                System.out.println("Attempt #" + i);
                solve();
            });
    }
    
    public int[] solve(){
        setupAnts();
        clearRoutes();
        IntStream.range(0,iterations).forEach(i->{
            moveAnts();
            updateRoutes();
            updateBest();
        });
        FXMLController.setLengthResult(String.valueOf(bestLength-numOfCities));
        FXMLController.setOrderResult(String.valueOf(Arrays.toString(bestOrder)));
        return bestOrder.clone();        
    }
    
    public void setupAnts(){
        ants.stream().
                forEach(ant -> {
                    ant.clear();
                    ant.visitCity(-1, random.nextInt(numOfCities));
                });
        currentIdx=0;
    }
    
    public void moveAnts(){
        IntStream.range(currentIdx,numOfCities -1).
                forEach(i->{
                    ants.forEach(ant-> ant.visitCity(currentIdx, selectNextCity(ant)));
                    currentIdx++;
                });
    }
    
    public int selectNextCity(Ant ant){
        int randInt = random.nextInt(numOfCities - currentIdx);
        if(random.nextDouble() < randomFactor){
            OptionalInt cityIdx = IntStream.range(0,numOfCities).filter(i->i==randInt && !ant.getVisitedByIdx(i)).findFirst();
            if(cityIdx.isPresent()){
                return cityIdx.getAsInt();
            }
        }
        calcProbabilty(ant);
        double randDouble = random.nextDouble();
        double total =0;
        for(int i=0;i<numOfCities;i++){
            total +=probabilities[i];
            if(total >= randDouble){
                return i;
            }
        }
        return -1;
    }
    
    public void calcProbabilty(Ant ant){
        int i = ant.getRouteByIdx(currentIdx);
        double pheromone = 0.0;
        for (int j=0;j<numOfCities;j++){
            if(!ant.getVisitedByIdx(j)){
                pheromone += Math.pow(routes[i][j], alpha) * Math.pow(1.0/graph[i][j], beta);
            }
        }
        for(int k=0;k<numOfCities;k++){
            if(ant.getVisitedByIdx(k)){
                probabilities[k]=0.0;
            }else{
                double numerator = Math.pow(routes[i][k], alpha) * Math.pow(1.0 / graph[i][k], beta);
                probabilities[k] = numerator/pheromone;
            }
        }
    }

    public void updateRoutes(){
        for (int i = 0; i < numOfCities; i++) {
            for (int j = 0; j < numOfCities; j++) {
                routes[i][j] *= evaporation;
            }
        }
        ants.forEach((ant) -> {
            double contribution = Q/ant.routeLength(graph);
            for(int i=0;i<numOfCities-1;i++){
                routes[ant.getRouteByIdx(i)][ant.getRouteByIdx(+1)] += contribution;
            }
            routes[ant.getRouteByIdx(numOfCities-1)][ant.getRouteByIdx(0)] += contribution;
        });
    }
    
    public void updateBest(){
        if(bestOrder == null){
            bestOrder=ants.get(0).getRoute();
            bestLength=ants.get(0).routeLength(graph);
        }
        for(Ant ant:ants){
            if(ant.routeLength(graph)<bestLength){
                bestLength = ant.routeLength(graph);
                bestOrder = ant.getRoute().clone();
            }
        }
    }
    
    public void clearRoutes(){
        IntStream.range(0, numOfCities)
            .forEach(i -> {
                IntStream.range(0, numOfCities)
                    .forEach(j -> routes[i][j] = c);
            });
    }
}
