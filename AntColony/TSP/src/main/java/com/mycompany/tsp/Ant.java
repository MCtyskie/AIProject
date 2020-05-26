/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.mycompany.tsp;

import lombok.Data;

@Data
public class Ant {
    private int routeSize;
    private int route[];
    private boolean visited[];
    
    public Ant(int routeSize){
        this.routeSize = routeSize;
        this.route = new int[routeSize];
        this.visited = new boolean[routeSize];
    }
    
    public boolean getVisitedByIdx(int idx){
        return visited[idx];
    }
    
    public int getRouteByIdx(int idx){
        return route[idx];
    }
    
    public void visitCity(int currentIdx,int city){
        route[currentIdx+1]=city;
        visited[city]=true;
    }
    public double routeLength(double graph[][]){
        double length = graph[route[routeSize-1]][route[0]];
        for(int i=0;i<routeSize-1;i++){
            length += graph[route[i]][route[i+1]];
        }
        return length;
    }

    public void clear() {
        for(int i=0;i<routeSize;i++){
            visited[i]=false;
        }
    }
    
    
}
