package ca.mcmaster.cas.se2aa4.a4.pathfinder.Graph; 

public class Node {
    

private int id; 


private double cost; 

private Node path; 


public Node(int id){
    
this.id = id;

this.cost = Double.MAX_VALUE; 
}    





public int getId(){
    return this.id; 

}


public void setCost(double cost){
    this.cost = cost; 
}

public void reset(){
    this.cost = Double.MAX_VALUE; 
    this.path = null; 
}


public double getCost(){
    return this.cost; 
}


public Node getPath(){
    return this.path; 
}

public void setPath(Node node){
    this.path = node; 
}



public String toString(){
    return ""+this.id;
}


}
