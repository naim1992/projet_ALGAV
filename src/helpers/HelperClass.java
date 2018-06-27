package helpers;

import java.util.ArrayList;

public abstract class HelperClass {
	public static Long countTime(ArrayList<Long> times) {
		long sum = 0;
		for (Long time : times) {
			sum+= time ;
		}
		return sum ;
	}
}
