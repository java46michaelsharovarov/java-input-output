package telran.io;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;

import org.junit.jupiter.api.Test;

class ArrayTests {	
	/**
	 * The method doesn't update a given array
	 * @param <T>
	 * @param array
	 * @return true if there is exactly one swap for getting sorted array
	 * examples: {1, 2, 3, 10, -1, 5, 6} -> false
	 * {1, 2, 3, 5, 6, 10} -> false
	 * {1,3,2,4, 3, 10} -> false
	 * {10, 2, 3, 4, 1} -> true
	 * {1, 2, 4, 3, 5, 10} -> true
	 * {1, 5, 3, 4, 2, 10} -> true
	 * {"lmn", "ab", "bc", "cd", "a"} -> true
	 * An Array should contain Comparable elements
	 */
	<T> boolean isOneSwapForSorted(T [] array) {
		if(array.length < 2) {
			return false;
		}
	    int indexOfNextElAfterFirst = 0, indexOfSecondEl = 0, numElOutOfOrder = 0;
	    for (int i = 1; i < array.length; i++) {
	        if (compare(array[i], array[i - 1]) < 0) {
	            numElOutOfOrder++;
	            if (indexOfNextElAfterFirst == 0) {
	                indexOfNextElAfterFirst = i;
	            } else {
	            	indexOfSecondEl = i;
	            }  
	        }
	    }
	    if (numElOutOfOrder == 0 || numElOutOfOrder > 2) {
	        return false;
	    }	   
	    return isSorted(array, indexOfNextElAfterFirst - 1, indexOfSecondEl, numElOutOfOrder);
	}
	private <T> boolean isSorted(T[] array, int indexOfFirstEl, int indexOfSecondEl, int numElOutOfOrder) {
		T[] helper = Arrays.copyOf(array, array.length);
	    if (numElOutOfOrder == 2) {
	    	helper = swap(helper, indexOfFirstEl, indexOfSecondEl);
	    } else {
	    	helper = swap(helper, indexOfFirstEl, indexOfFirstEl + 1);
	    }
	    for (int i = 1; i < helper.length; i++) {
	    	if (compare(helper[i], helper[i - 1]) < 0) {
	            return false;
	    	}
	    } 
		return true;
	}		
	private <T> T[] swap(T[] array, int i, int j) {
		T temp = array[i];
		array[i] = array[j];
		array[j] = temp;
	    return array;
	}
	@SuppressWarnings("unchecked")
	private <T> int compare(T el1, T el2) {
		return ((Comparable<T>) el1).compareTo(el2);
	}
	
	@Test
	void isOneSwapTest() {		
		Integer arTrue1[] = {1, 6, 3, 4, 2, 10};
		Integer arTrue2[] = {1, 2, 4, 3, 6, 10};
		Integer arTrue3[] = {10, 2, 3, 4, 6, 1};
		Integer arTrue4[] = {3, 2, 3, 4, 6, 10};
		Integer arTrue5[] = {1, 2, 3, 4, 6, 10, 7};
		String[] arTrue6 = {"lmn", "ab", "bc", "cd", "a"};
		Integer arFalse1[] = {1, 10, 2, 3, 6, 4};
		Integer arFalse2[] = {1, 2, 4, 2, 10, 6};
		Integer arFalse3[] = {1, 2, 3, 4, 6, 10};
		
		assertTrue(isOneSwapForSorted(arTrue1));
		assertTrue(isOneSwapForSorted(arTrue2));
		assertTrue(isOneSwapForSorted(arTrue3));
		assertTrue(isOneSwapForSorted(arTrue4));
		assertTrue(isOneSwapForSorted(arTrue5));
		assertTrue(isOneSwapForSorted(arTrue6));
		assertFalse(isOneSwapForSorted(arFalse1));
		assertFalse(isOneSwapForSorted(arFalse2));
		assertFalse(isOneSwapForSorted(arFalse3));
				
		assertFalse(isOneSwapForSorted(new Integer[]{1}));
		assertFalse(isOneSwapForSorted(new Integer[]{1, 1, 1, 1, 1, 1}));
		assertFalse(isOneSwapForSorted(new String[]{"a", "ab", "bc", "cd", "lmn"}));
		assertFalse(isOneSwapForSorted(new String[]{"lmn", "ab", "cd", "bc", "a"}));
		assertFalse(isOneSwapForSorted(new String[]{"ab", "ab", "ab", "ab", "ab"}));
		assertTrue(isOneSwapForSorted(new String[]{"a", "ab", "cd", "bc", "lmn"}));		
	}

}