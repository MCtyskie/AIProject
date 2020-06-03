/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.mycompany.tsp;

import static java.lang.Math.random;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.OptionalInt;
import java.util.Random;
import java.util.stream.IntStream;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import lombok.Setter;

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
    
    private static ObservableList<String> citiesList;
    
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
    
    public double[][] basicCitiesMatrix(){
        double[][] matrix=new double[20][20];
        matrix[0][0]=0;
        matrix[0][1]=294;
        matrix[0][2]=272;
        matrix[0][3]=581;
        matrix[0][4]=468;
        matrix[0][5]=460;
        matrix[0][6]=81;
        matrix[0][7]=344;
        matrix[0][8]=281;
        matrix[0][9]=160;
        matrix[0][10]=263;
        matrix[0][11]=571;
        matrix[0][12]=798;
        matrix[0][13]=378;
        matrix[0][14]=43;
        matrix[0][15]=142;
        matrix[0][16]=656;
        matrix[0][17]=308;
        matrix[0][18]=111;
        matrix[0][19]=647;
        
        matrix[1][0]=294;
        matrix[1][1]=0;
        matrix[1][2]=349;
        matrix[1][3]=339;
        matrix[1][4]=311;
        matrix[1][5]=311;
        matrix[1][6]=294;
        matrix[1][7]=202;
        matrix[1][8]=134;
        matrix[1][9]=211;
        matrix[1][10]=151;
        matrix[1][11]=282;
        matrix[1][12]=536;
        matrix[1][13]=476;
        matrix[1][14]=292;
        matrix[1][15]=401;
        matrix[1][16]=439;
        matrix[1][17]=263;
        matrix[1][18]=408;
        matrix[1][19]=566;
        
        matrix[2][0]=272;
        matrix[2][1]=349;
        matrix[2][2]=0;
        matrix[2][3]=555;
        matrix[2][4]=312;
        matrix[2][5]=182;
        matrix[2][6]=191;
        matrix[2][7]=539;
        matrix[2][8]=246;
        matrix[2][9]=457;
        matrix[2][10]=427;
        matrix[2][11]=573;
        matrix[2][12]=424;
        matrix[2][13]=122;
        matrix[2][14]=244;
        matrix[2][15]=412;
        matrix[2][16]=575;
        matrix[2][17]=578;
        matrix[2][18]=371;
        matrix[2][19]=394;
        
        matrix[3][0]=581;
        matrix[3][1]=339;
        matrix[3][2]=555;
        matrix[3][3]=0;
        matrix[3][4]=173;
        matrix[3][5]=314;
        matrix[3][6]=519;
        matrix[3][7]=394;
        matrix[3][8]=340;
        matrix[3][9]=558;
        matrix[3][10]=486;
        matrix[3][11]=191;
        matrix[3][12]=200;
        matrix[3][13]=576;
        matrix[3][14]=528;
        matrix[3][15]=684;
        matrix[3][16]=101;
        matrix[3][17]=598;
        matrix[3][18]=695;
        matrix[3][19]=357;
        
        matrix[4][0]=468;
        matrix[4][1]=311;
        matrix[4][2]=312;
        matrix[4][3]=173;
        matrix[4][4]=0;
        matrix[4][5]=140;
        matrix[4][6]=507;
        matrix[4][7]=502;
        matrix[4][8]=226;
        matrix[4][9]=444;
        matrix[4][10]=426;
        matrix[4][11]=286;
        matrix[4][12]=206;
        matrix[4][13]=404;
        matrix[4][14]=414;
        matrix[4][15]=570;
        matrix[4][16]=257;
        matrix[4][17]=568;
        matrix[4][18]=581;
        matrix[4][19]=258;
        
        matrix[5][0]=460;
        matrix[5][1]=311;
        matrix[5][2]=182;
        matrix[5][3]=314;
        matrix[5][4]=140;
        matrix[5][5]=0;
        matrix[5][6]=383;
        matrix[5][7]=502;
        matrix[5][8]=207;
        matrix[5][9]=452;
        matrix[5][10]=434;
        matrix[5][11]=411;
        matrix[5][12]=250;
        matrix[5][13]=270;
        matrix[5][14]=429;
        matrix[5][15]=597;
        matrix[5][16]=404;
        matrix[5][17]=576;
        matrix[5][18]=556;
        matrix[5][19]=264;
        
        matrix[6][0]=81;
        matrix[6][1]=294;
        matrix[6][2]=191;
        matrix[6][3]=519;
        matrix[6][4]=507;
        matrix[6][5]=383;
        matrix[6][6]=0;
        matrix[6][7]=492;
        matrix[6][8]=203;
        matrix[6][9]=265;
        matrix[6][10]=303;
        matrix[6][11]=544;
        matrix[6][12]=721;
        matrix[6][13]=301;
        matrix[6][14]=41;
        matrix[6][15]=221;
        matrix[6][16]=609;
        matrix[6][17]=387;
        matrix[6][18]=180;
        matrix[6][19]=570;
        
        matrix[7][0]=344;
        matrix[7][1]=202;
        matrix[7][2]=539;
        matrix[7][3]=394;
        matrix[7][4]=502;
        matrix[7][5]=502;
        matrix[7][6]=492;
        matrix[7][7]=0;
        matrix[7][8]=319;
        matrix[7][9]=358;
        matrix[7][10]=258;
        matrix[7][11]=214;
        matrix[7][12]=626;
        matrix[7][13]=665;
        matrix[7][14]=490;
        matrix[7][15]=599;
        matrix[7][16]=523;
        matrix[7][17]=330;
        matrix[7][18]=606;
        matrix[7][19]=757;
        
        matrix[8][0]=281;
        matrix[8][1]=134;
        matrix[8][2]=216;
        matrix[8][3]=340;
        matrix[8][4]=226;
        matrix[8][5]=207;
        matrix[8][6]=203;
        matrix[8][7]=319;
        matrix[8][8]=0;
        matrix[8][9]=242;
        matrix[8][10]=254;
        matrix[8][11]=355;
        matrix[8][12]=449;
        matrix[8][13]=343;
        matrix[8][14]=212;
        matrix[8][15]=369;
        matrix[8][16]=430;
        matrix[8][17]=396;
        matrix[8][18]=380;
        matrix[8][19]=473;
        
        matrix[9][0]=160;
        matrix[9][1]=211;
        matrix[9][2]=457;
        matrix[9][3]=558;
        matrix[9][4]=444;
        matrix[9][5]=452;
        matrix[9][6]=265;
        matrix[9][7]=358;
        matrix[9][8]=242;
        matrix[9][9]=0;
        matrix[9][10]=91;
        matrix[9][11]=515;
        matrix[9][12]=674;
        matrix[9][13]=562;
        matrix[9][14]=191;
        matrix[9][15]=187;
        matrix[9][16]=648;
        matrix[9][17]=135;
        matrix[9][18]=284;
        matrix[9][19]=705;
        
        matrix[10][0]=263;
        matrix[10][1]=151;
        matrix[10][2]=427;
        matrix[10][3]=786;
        matrix[10][4]=426;
        matrix[10][5]=434;
        matrix[10][6]=303;
        matrix[10][7]=258;
        matrix[10][8]=254;
        matrix[10][9]=91;
        matrix[10][10]=0;
        matrix[10][11]=366;
        matrix[10][12]=623;
        matrix[10][13]=553;
        matrix[10][14]=262;
        matrix[10][15]=280;
        matrix[10][16]=586;
        matrix[10][17]=145;
        matrix[10][18]=371;
        matrix[10][19]=689;
        
        matrix[11][0]=571;
        matrix[11][1]=282;
        matrix[11][2]=573;
        matrix[11][3]=191;
        matrix[11][4]=286;
        matrix[11][5]=411;
        matrix[11][6]=544;
        matrix[11][7]=214;
        matrix[11][8]=355;
        matrix[11][9]=515;
        matrix[11][10]=366;
        matrix[11][11]=0;
        matrix[11][12]=407;
        matrix[11][13]=701;
        matrix[11][14]=572;
        matrix[11][15]=681;
        matrix[11][16]=292;
        matrix[11][17]=479;
        matrix[11][18]=688;
        matrix[11][19]=509;
        
        matrix[12][0]=798;
        matrix[12][1]=536;
        matrix[12][2]=424;
        matrix[12][3]=200;
        matrix[12][4]=206;
        matrix[12][5]=250;
        matrix[12][6]=721;
        matrix[12][7]=626;
        matrix[12][8]=449;
        matrix[12][9]=674;
        matrix[12][10]=623;
        matrix[12][11]=407;
        matrix[12][12]=0;
        matrix[12][13]=552;
        matrix[12][14]=639;
        matrix[12][15]=939;
        matrix[12][16]=233;
        matrix[12][17]=793;
        matrix[12][18]=898;
        matrix[12][19]=168;
        
        matrix[13][0]=378;
        matrix[13][1]=476;
        matrix[13][2]=122;
        matrix[13][3]=576;
        matrix[13][4]=404;
        matrix[13][5]=270;
        matrix[13][6]=301;
        matrix[13][7]=665;
        matrix[13][8]=343;
        matrix[13][9]=562;
        matrix[13][10]=553;
        matrix[13][11]=701;
        matrix[13][12]=552;
        matrix[13][13]=0;
        matrix[13][14]=347;
        matrix[13][15]=515;
        matrix[13][16]=665;
        matrix[13][17]=681;
        matrix[13][18]=474;
        matrix[13][19]=400;
        
        matrix[14][0]=43;
        matrix[14][1]=292;
        matrix[14][2]=244;
        matrix[14][3]=528;
        matrix[14][4]=414;
        matrix[14][5]=429;
        matrix[14][6]=41;
        matrix[14][7]=490;
        matrix[14][8]=212;
        matrix[14][9]=191;
        matrix[14][10]=262;
        matrix[14][11]=572;
        matrix[14][12]=639;
        matrix[14][13]=347;
        matrix[14][14]=0;
        matrix[14][15]=192;
        matrix[14][16]=618;
        matrix[14][17]=358;
        matrix[14][18]=151;
        matrix[14][19]=619;
        
        matrix[15][0]=142;
        matrix[15][1]=401;
        matrix[15][2]=412;
        matrix[15][3]=684;
        matrix[15][4]=570;
        matrix[15][5]=597;
        matrix[15][6]=221;
        matrix[15][7]=599;
        matrix[15][8]=369;
        matrix[15][9]=187;
        matrix[15][10]=280;
        matrix[15][11]=681;
        matrix[15][12]=939;
        matrix[15][13]=515;
        matrix[15][14]=192;
        matrix[15][15]=0;
        matrix[15][16]=765;
        matrix[15][17]=299;
        matrix[15][18]=125;
        matrix[15][19]=787;
        
        matrix[16][0]=656;
        matrix[16][1]=439;
        matrix[16][2]=575;
        matrix[16][3]=101;
        matrix[16][4]=257;
        matrix[16][5]=404;
        matrix[16][6]=609;
        matrix[16][7]=523;
        matrix[16][8]=430;
        matrix[16][9]=648;
        matrix[16][10]=586;
        matrix[16][11]=292;
        matrix[16][12]=233;
        matrix[16][13]=665;
        matrix[16][14]=618;
        matrix[16][15]=765;
        matrix[16][16]=0;
        matrix[16][17]=698;
        matrix[16][18]=785;
        matrix[16][19]=319;
        
        matrix[17][0]=308;
        matrix[17][1]=263;
        matrix[17][2]=578;
        matrix[17][3]=598;
        matrix[17][4]=568;
        matrix[17][5]=576;
        matrix[17][6]=387;
        matrix[17][7]=330;
        matrix[17][8]=396;
        matrix[17][9]=135;
        matrix[17][10]=145;
        matrix[17][11]=479;
        matrix[17][12]=793;
        matrix[17][13]=681;
        matrix[17][14]=358;
        matrix[17][15]=299;
        matrix[17][16]=698;
        matrix[17][17]=0;
        matrix[17][18]=404;
        matrix[17][19]=830;
        
        matrix[18][0]=111;
        matrix[18][1]=408;
        matrix[18][2]=371;
        matrix[18][3]=695;
        matrix[18][4]=581;
        matrix[18][5]=556;
        matrix[18][6]=180;
        matrix[18][7]=606;
        matrix[18][8]=380;
        matrix[18][9]=284;
        matrix[18][10]=371;
        matrix[18][11]=688;
        matrix[18][12]=898;
        matrix[18][13]=474;
        matrix[18][14]=151;
        matrix[18][15]=125;
        matrix[18][16]=785;
        matrix[18][17]=404;
        matrix[18][18]=0;
        matrix[18][19]=745;
        
        matrix[19][0]=647;
        matrix[19][1]=566;
        matrix[19][2]=394;
        matrix[19][3]=357;
        matrix[19][4]=258;
        matrix[19][5]=264;
        matrix[19][6]=570;
        matrix[19][7]=757;
        matrix[19][8]=473;
        matrix[19][9]=705;
        matrix[19][10]=689;
        matrix[19][11]=509;
        matrix[19][12]=168;
        matrix[19][13]=400;
        matrix[19][14]=619;
        matrix[19][15]=787;
        matrix[19][16]=390;
        matrix[19][17]=830;
        matrix[19][18]=745;
        matrix[19][19]=0;
        
        
        return matrix;
    }

    public double[][] generateCitiesMatrix(int numOfCities) {
        double[][] matrix=new double[numOfCities][numOfCities];
        citiesList= FXMLController.getObservableList1();
        
        return null;
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
