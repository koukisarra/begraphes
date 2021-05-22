package org.insa.graphs.algorithm.shortestpath;


import java.util.*;
import org.insa.graphs.algorithm.utils.ElementNotFoundException;
import org.insa.graphs.model.*;
import org.insa.graphs.algorithm.AbstractSolution.Status;
import org.insa.graphs.algorithm.utils.BinaryHeap;


public class DijkstraAlgorithm extends ShortestPathAlgorithm {
	  protected final ShortestPathData data = getInputData();
    
    /* importing the graph from the data */
	  protected Graph graph = data.getGraph();
	  protected int nbNodes = graph.size();
	
  public DijkstraAlgorithm(ShortestPathData data) {
      super(data);
  }
  
	
  protected Label[] InitialiseLabels() {
  
  /* array of labels initialized with the values oft the given nodes */
	    Label[] ArrayLabels = new Label[nbNodes];
	
	    List<Node> nodes = graph.getNodes();
	
	    for (Node node : nodes) {
	    	ArrayLabels[node.getId()] = new Label(node);
	    }
	    
	    return ArrayLabels ; 
  }
  
  @Override
  public ShortestPathSolution doRun() {
  	Label[] ArrayLabels ; 
  	
  	ArrayLabels = InitialiseLabels() ; 
  	 int nb_explored = 0 ;
  	 int nb_marked = 0 ;
  	 
      // Notify observers about the first event (origin processed).
      notifyOriginProcessed(data.getOrigin());

      /* Initializing the heap */ 
      BinaryHeap<Label> Heap = new BinaryHeap<>();
      
      Node Origin = data.getOrigin();
      Label OriginLabel = ArrayLabels[Origin.getId()];
      
      Node Destination = data.getDestination();
      Label DestinationLabel = ArrayLabels[Destination.getId()];
     
      /* Variables to to compute number of arcs and iterations
      int nbrIter = 0;
      int nbrArcs = 0;  */ 
		
		
      /* inserting origin into the heap */ 
      OriginLabel.setCost(0);
      Heap.insert(OriginLabel);
      
      Label CurrentLabel = null;
      ShortestPathSolution solution = null;
      
      while (!DestinationLabel.isMarked() && !Heap.isEmpty() && solution == null ) {
      	// nbrIter += 1 ; 
      	
      	CurrentLabel = Heap.deleteMin() ; 
      	CurrentLabel.setMarkTrue();
      	nb_marked ++ ; 
      	
      	System.out.println("Cout :" + CurrentLabel.getCost());
      	
      	/*Notify observers about the node being marked*/ 
      	notifyNodeMarked(CurrentLabel.getCurrentNode());

          List<Arc> ListSuccessors = CurrentLabel.getCurrentNode().getSuccessors() ;
          
      	/* Running through successors of the current summit*/
      	for (Arc ArcIter: ListSuccessors ) {
      		// nbrArcs += 1 ;
   
      		/* Small test to check if the road is allowed */ 
      		if (data.isAllowed(ArcIter)) {
      			
	        		Label IterDestination = ArrayLabels[ArcIter.getDestination().getId()];
	        		
	        		/*Notify observers about the node being reached*/ 
	        		notifyNodeReached(ArcIter.getDestination());
	        		
	        		if(! IterDestination.isMarked()) {
	        			
		        			if (!IterDestination.isMarked() && IterDestination.getCost() > CurrentLabel.getCost() + data.getCost(ArcIter)) {
		        				
		        				try {
		        					Heap.remove(IterDestination);
		        				} catch(ElementNotFoundException e) {}
		        				
		        				nb_explored ++ ;
		        				IterDestination.setCost(CurrentLabel.getCost() + data.getCost(ArcIter));
		        				IterDestination.setFather(ArcIter);
		        			
		        				Heap.insert(IterDestination);
		        			}
	        		}
      		}
      	}
      }
     
      // Destination has no predecessor, the solution is infeasible...
		if((DestinationLabel.getFather()==null && (data.getOrigin().compareTo(data.getDestination())!= 0)  ) || !DestinationLabel.isMarked()) {
			System.out.println("Chemin impossible") ; 
			solution = new ShortestPathSolution(data, Status.INFEASIBLE);
      } else {
      	
      	// The destination has been found, notify the observers.
          notifyDestinationReached(data.getDestination());
          
          // Empty Path
          if(data.getOrigin().compareTo(data.getDestination()) == 0) { 
          	// Create the final solution.
          	System.out.println("Chemin Vide") ; 
             solution = new ShortestPathSolution(data, Status.OPTIMAL, new Path(graph));
              
          }else {
 
          // Create the path from the array of predecessors...
      	ArrayList<Arc> arcs = new ArrayList<>();
      	
      	while(!CurrentLabel.equals(OriginLabel)) {
      		// System.out.println("Cost of label " + CurrentLabel.getCost());
      		arcs.add(CurrentLabel.getFather());
      		CurrentLabel = ArrayLabels[CurrentLabel.getFather().getOrigin().getId()];
      		
      	}
      	// Reverse the path...
          Collections.reverse(arcs);
        
          Path path = new Path(graph, arcs) ; 
          
          if(path.isValid()) {
              // Create the final solution.
              solution = new ShortestPathSolution(data, Status.OPTIMAL, path);
              System.out.println("Chemin Valide") ; 
              
          } else {
          	System.out.println("Chemin non Valide") ; 
          }
    
          }
      	
      }

		/* System.out.println(" Iterations : " + nbrIter + "iterations"); 
      System.out.println(" Arcs: " + nbrArcs + "arcs"); */ 
		System.out.println(" Nombre Noeud visités " + nb_explored );
		System.out.println(" Nombre Noeud Marqués " + nb_marked );
		        return solution;
  }

}