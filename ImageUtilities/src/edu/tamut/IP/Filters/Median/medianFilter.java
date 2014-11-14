package edu.tamut.IP.Filters.Median;
import java.util.List;
import java.util.ArrayList;
import java.awt.Color;
import java.util.Collections;

public class medianFilter {

	// overloading median filter for the integer parameter
	public static int medianFilter(ArrayList<Integer> intList, Object[] otherParameters){
		
	    // sort the list
	    Collections.sort(intList);
	    
	    // find the median of channel
	    int intMedian = median(intList);
		
	    // return median
	    return intMedian;
	} // end method medianFilter
	
	private static int median(List list){
	
		// middle-most positioned number is needed for finding median; sorting already done
		int middle = list.size()/2;
	
		// if the list is odd, return the middle as the median,
		// otherwise, if even, return the two middle numbers averaged
		if (list.size() % 2 == 1)
			return (int)list.get(middle);
		else
			return ((int)list.get(middle) + (int)list.get(middle-1)) / 2;
	} // end method median

} // end class medianFilter