package telran.io;

import static org.junit.jupiter.api.Assertions.*;

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
	    int indexOfFirst = -1;
	    int indexOfSecond = -1;
	    int length = array.length - 1;
	    for (int i = 0; i < length; i++) {
	        if (!compare(array, i, i + 1)) {
	            if (indexOfFirst == -1) {
	            	indexOfFirst = i;
	            } else if(indexOfSecond != -1) {
					return false;
				} else {
	            	indexOfSecond = i + 1;
	            }  
	        }
	    }   
	    return indexOfFirst == -1 ? false : isSorted(array, indexOfFirst, indexOfSecond);
	}
	private <T> boolean isSorted(T[] array, int first, int second) {
		return second == -1 ? checkOneIndex(array, first) : checkTwoIndexes(array, first, second);
	}
	
	private static <T> boolean checkTwoIndexes(T[] array, int index1, int index2) {		
		return (index2 == array.length - 1 || compare(array, index1, index2 + 1) ) && 
				compare(array, index2, index1 + 1) && (index1 == 0 ||
						compare(array, index1 - 1, index2 ));
	}

	private <T> boolean checkOneIndex(T[] array, int first) {
		return (first == array.length - 2 || compare(array, first, first + 2)) && 
				(first == 0 || compare(array, first - 1, first + 1)); 
	}
	
	@SuppressWarnings("unchecked")
	private static <T> boolean compare(T[] array, int first, int second) {
		return ((Comparable<T>) array[first]).compareTo(array[second]) <= 0;
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
		
		Integer ar21[] = {1, 3, 20, 4, 5, 11, 2};
		Integer ar31[] = {1, 3, 20, 4, 5, 6, 10};
		Integer ar1[] = { 1, 2, 3, 10, -1, 5, 6 };
		Integer ar2[] = { 1, 2, 3, 4, 5, 10 };
		Integer ar20[] = { 5, 1, 2, 4, 6, 10 };
		Integer ar3[] = { 1, 5, 2, 4, 3, 10 };
		Integer ar4[] = { 1, 3, 2, 5, 4, 10, 8 };
		Integer ar5[] = { 10, 2, 3, 4, 1 };
		Integer ar6[] = { 1, 2, 4, 3, 5, 10 };
		Integer ar7[] = { 1, 2, 3, 10, 5, 4 };
		Integer ar8[] = { 1, 5, 3, 4, 2, 10 };
		Integer ar9[] = { 1, 2, 3, 4, 10, 5 };
		Integer ar10[] = { 2, 1, -3, 4, 5, 10 };
		Integer ar100[] = { 1, 2, 4, 3, 5, 10 };
		Integer ar11[] = { 1, 2, 3, 10, 5, 4 };
		Integer ar12[] = { 3, 2, 1, 4, 5, 6 };
		String ar13[] = { "lmn", "ab", "bc", "cd", "a" };

		assertFalse(isOneSwapForSorted(ar1));
		assertFalse(isOneSwapForSorted(ar21));
		assertFalse(isOneSwapForSorted(ar31));
		assertFalse(isOneSwapForSorted(ar2));
		assertFalse(isOneSwapForSorted(ar20));
		assertFalse(isOneSwapForSorted(ar3));
		assertFalse(isOneSwapForSorted(ar4));
		assertTrue(isOneSwapForSorted(ar5));
		assertTrue(isOneSwapForSorted(ar6));
		assertTrue(isOneSwapForSorted(ar7));
		assertTrue(isOneSwapForSorted(ar8));
		assertTrue(isOneSwapForSorted(ar9));
		assertTrue(isOneSwapForSorted(ar10));
		assertTrue(isOneSwapForSorted(ar100));
		assertTrue(isOneSwapForSorted(ar11));
		assertTrue(isOneSwapForSorted(ar12));
		assertTrue(isOneSwapForSorted(ar13));
		
	}

}