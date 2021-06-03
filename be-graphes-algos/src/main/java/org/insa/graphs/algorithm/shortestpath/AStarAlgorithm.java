package org.insa.graphs.algorithm.shortestpath;

import org.insa.graphs.model.*;
import java.util.List;
import org.insa.graphs.algorithm.AbstractInputData;

public class AStarAlgorithm extends DijkstraAlgorithm {
	
    public AStarAlgorithm(ShortestPathData data) {
        super(data);
    }
    
   protected Label[] InitialiseLabels() {
	   /*Create an array of labels size nbnodes*/
	   LabelStar ArrayLabels[] = new LabelStar[nbNodes] ;
	   List<Node> nodes = graph.getNodes();
	   
	   double Cost = 0;

	   int MaxSpeed = Speed() ; 
	   
	   Point DestinationP = data.getDestination().getPoint() ; 
	   
	   for (Node node : nodes) {
		   ArrayLabels[node.getId()] = new LabelStar(node);
		   
		   // Case : length : the cost is the distance between this point and the destination point, in meters
		   if(data.getMode() == AbstractInputData.Mode.LENGTH) {
			   Cost = node.getPoint().distanceTo(DestinationP);
			   
			   //Case : Time i
	       	} else {
	       		Cost = 3.6* node.getPoint().distanceTo(DestinationP) / MaxSpeed; 
	       	}
		   
		   ArrayLabels[node.getId()].setEstimatedCost(Cost);
	   }
	   return ArrayLabels ; 
    }
  // To avoid the problem of marked vertices which make circles throughout the map
   private int Speed() {
	   int MaxSpeedData =  data.getMaximumSpeed() ; 
	   int MaxSpeedGraph = graph.getGraphInformation().getMaximumSpeed() ;
	   int Speed = Math.min(MaxSpeedData, MaxSpeedGraph) ; 
	   
	   if (MaxSpeedData ==  GraphStatistics.NO_MAXIMUM_SPEED && MaxSpeedGraph ==  GraphStatistics.NO_MAXIMUM_SPEED ) {
		   Speed = 130 ;
	   }
	   if (MaxSpeedData ==  GraphStatistics.NO_MAXIMUM_SPEED) {
		   Speed = MaxSpeedGraph; 
	   }
	   if (MaxSpeedGraph ==  GraphStatistics.NO_MAXIMUM_SPEED) {
		   Speed = MaxSpeedData; 
	   }
	return Speed ; 
   }
}