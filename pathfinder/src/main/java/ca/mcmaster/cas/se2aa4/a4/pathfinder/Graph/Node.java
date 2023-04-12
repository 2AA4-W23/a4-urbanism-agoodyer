package ca.mcmaster.cas.se2aa4.a4.pathfinder.Graph; 

public class Node<T> {
    

private int id; 
private T data; 


private double elevation; 



public Node(T data){
    this.data = data; 
this.id = this.hashCode(); 
}    


public T getData(){
    return data; 
}


}
