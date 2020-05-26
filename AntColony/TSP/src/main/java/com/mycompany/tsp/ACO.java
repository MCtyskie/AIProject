/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.mycompany.tsp;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class ACO {
    private double c = 1.0;
    private double alpha = 1;
    private double beta = 5;
    private double evaporation = 0.5;
    private double Q = 500;
    private double antFactor = 0.8;
    private double randomFactor = 0.01;
    
    private List<Ant> ants = new ArrayList<>();
    private int numOfCities;
    private int numOfAnts;
    private double graph[][];
    private double routes[][];
    private Random rand = new Random();
    private double probabilities[];
    
    private int currentIdx;
    private int[] bestOrder;
    private double bestLength;
    
    public ACO(int numOfCities){
        graph = generateCitiesMatrix(numOfCities);
        numOfCities = graph.length;
    }

    private double[][] generateCitiesMatrix(int numOfCities) {
        //TODO
        return null;
    }
}
