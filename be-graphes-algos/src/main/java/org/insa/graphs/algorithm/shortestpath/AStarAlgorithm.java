package org.insa.graphs.algorithm.shortestpath;

import org.insa.graphs.model.*;
import java.util.List;
import org.insa.graphs.algorithm.AbstractInputData;

public class AStarAlgorithm extends DijkstraAlgorithm {
	
    public AStarAlgorithm(ShortestPathData data) {
        super(data);
    }
    
   protected Label[] InitialiseLabels() {
	   LabelStar ArrayLabels[] = new LabelStar[nbNodes] ;
	   List<Node> nodes = graph.getNodes();
	   
	   double Cost = 0;

	   int MaxSpeed = Speed() ; 
	   
	   Point DestinationP = data.getDestination().getPoint() ; 
	   
	   for (Node node : nodes) {
		   ArrayLabels[node.getId()] = new LabelStar(node);
		   
		   // the cost is the distance between this point and the destination point, in meters
		   if(data.getMode() == AbstractInputData.Mode.LENGTH) {
			   Cost = node.getPoint().distanceTo(DestinationP);
			   
			   //or it's the time (ie Distance divided by speed) 
	       	} else {
	       		Cost = 3.6* node.getPoint().distanceTo(DestinationP) / MaxSpeed; 
	       	}
		   
		   ArrayLabels[node.getId()].setEstimatedCost(Cost);
	   }
	   return ArrayLabels ; 
    }
  // Pour éviter le problème des sommets marqués qui font des cercle dasn toute la carte
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