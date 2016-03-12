package floodfill;

import java.util.Arrays;

public class SplitSort {
	@SafeVarargs
	public static <T extends Comparable<T>> T[] sort(T... array) {
		return doSort(array , 0 , array.length - 1);
	}
	
	public static <T extends Comparable<T>> T[] doSort(T[] array , int start , int end) {
		if(start < end) {
			int middle = (end + start) / 2;
			doSort(array , start , middle);
			doSort(array , middle + 1 , end);
			merge(array , start , middle , end);
		}
		return array;
	}
	
	private static <T extends Comparable<T>> void merge(T[] array , int start , int middle , int end) {
		System.out.println(array.getClass().getTypeName());
		@SuppressWarnings("unchecked")
		T[] temp = (T[]) new Double[end - start + 1];
		int index1 = start;
		int index2 = middle;
		int indexTemp = 0;
		
		while(index1 <= middle && index2 <= end) {
			if(array[index1].compareTo(array[index2]) < 0) {
				temp[indexTemp] = array[index1];
				index1++;
			}
			else {
				temp[indexTemp] = array[index2];
				index2++;
			}
			indexTemp++;
		}
		for(int i = start; i <= end; i++) {
			array[i] = temp[i - start];
		}
	}
	
	public static void main(String[] args) {
		System.out.println(Arrays.toString(sort(0. , 1. , 38. , 6.)));
	}
}
