/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.mycompany.tsp;

import static java.lang.Math.random;
import java.util.ArrayList;
import java.util.List;
import java.util.OptionalInt;
import java.util.Random;
import java.util.stream.IntStream;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

//VU6qX1mGKHLEZkb5E1b1~T3DaZuyK6Dn8qF6HxvwggA~AhJvdjQpVUVUVPXMCeEmVYV893RrJ_1cQDQrqNsDvRsxyVMZIMm6C9_7fDvoKGV-


public class ACO {
    //TO DO FROM GUI
    private double c = 1.0;
    private double alpha = 1;
    private double beta = 5;
    private double evaporation = 0.5;
    private double Q = 500;
    private double antFactor = 0.8;
    private double randomFactor = 0.01;
    
    private int iterations = 1000;
    
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
    
    private static ObservableList<CitiesEnum> citiesList;
    
    public ACO(int numOfCities){
        graph = generateCitiesMatrix(numOfCities);
        numOfCities = graph.length;
        numOfAnts = (int) (numOfCities * antFactor);//TO DO from GUI
        routes = new double[numOfCities][numOfCities];
        probabilities = new double[numOfCities];
        createAnts(numOfAnts);
    }
    
    public void createAnts(int numOfAnts){
        IntStream.range(0, numOfAnts).forEach(i -> ants.add(new Ant(numOfCities)));
    }

    public double[][] generateCitiesMatrix(int numOfCities) {
        double[][] matrix=new double[numOfCities][numOfCities];
        citiesList= FXMLController.getObservableList1();
        
        //return null;
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
        //TODO Syso Best route len and best route order
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
        for(Ant ant : ants){
            double contribution = Q/ant.routeLength(graph);
            for(int i=0;i<numOfCities-1;i++){
                routes[ant.getRouteByIdx(i)][ant.getRouteByIdx(+1)] += contribution;
            }
            routes[ant.getRouteByIdx(numOfCities-1)][ant.getRouteByIdx(0)] += contribution;
        }
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
