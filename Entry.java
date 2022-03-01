import java.util.List;
import java.util.*;
import java.util.Collections;

/**
 * Entry deals with storing the key and value associated with entries in the
 * database. 
 * As well as storing the data the entry class should manage operations
 * associated with any Entry.
 */

public class Entry {
	private String key;
	private List<Integer> values;

	public Entry(String key, List<Integer> values) {
		this.key = key;
		this.values = values;
	}
	
	/**
	 * Getter method
	 *
	 * @return  this.values
	 */
	public List<Integer> getValues() {
		return this.values;
	}
	
	/**
	 * Formats the Entry for display
	 *
	 * @return  the String of values
	 */
	public String get() {
		String valueS = this.values.toString(); //converting List to a string representation 
		String stringOfValues = valueS.replaceAll(",", ""); //replacing all "," with an empty string
		return stringOfValues;
	}
	
	/**
	 * Sets the values of this Entry.
	 *
	 * @param values the values to set
	 */
	public void set(List<Integer> values) {
		this.values = values;
	}

	/**
	 * Adds the values to the start.
	 *
	 * @param values the values to add
	 */
	public void push(List<Integer> values) {
		List<Integer> reversedNum = new ArrayList<Integer>(); //creating a new list of numbers
		
		for (int i = values.size()-1; i >= 0; i--) { //reverse loop
			reversedNum.add(values.get(i)); //adding values in reverse order to reversedNum arraylist
		}
		reversedNum.addAll(this.values);
		set(reversedNum);
	}

	/**
	 * Adds the values to the end.
	 *
	 * @param values the values to add
	 */
	public void append(List<Integer> values) {
		this.values.addAll(values);
	}

	/**
	 * Finds the value at the given index.
	 *
	 * @param  index the index
	 * @return       the value found 
	 */
	public Integer pick(int index) {
		if (index > 0 && index <= this.values.size()) { //index must be valid
			return this.values.get(index-1);
		} else {
			return null; //otherwise return null
		}
	}

	/**
	 * Finds and removes the value at the given index.
	 *
	 * @param  index the index
	 * @return       the value found
	 */
	public Integer pluck(int index) {		
		if (index > 0 && index <= this.values.size()) { //index must be valid
			Integer val = this.values.get(index-1);
			this.values.remove(index-1);
			return val;
		} else {
			return null; //otherwise return null
		}
		
	}

	/**
	 * Finds and removes the first value.
	 *
	 * @return the first value
	 */
	public Integer pop() {
		if (this.values.size() == 0) {
			return null;
		} else {
			Integer firstValue = this.values.get(0); //finding the first value at index 0
			this.values.remove(0); //removing first value at index 0
			return firstValue;
		}
	}

	/**
	 * Finds the minimum value.
	 *
	 * @return the minimum value
	 */
	public Integer min() {
		if (this.values.size() > 0) {
			int minNum = this.values.get(0); //assume first index value is smallest value
			for (int i = 0; i < this.values.size(); i++) {
				if (this.values.get(i) < minNum) { //if chosen value at index i is less than the smallest value
					minNum = this.values.get(i); //set minNum as the smaller value
				}
			}
			return minNum;
		}
		return null;
	}

	/**
	 * Finds the maximum value.
	 *
	 * @return the maximum value
	 */
	public Integer max() {
		if (this.values.size() > 0) {
			int maxNum = this.values.get(0); //assume largest number is first index
			for (int i = 0; i < this.values.size(); i++) {
				if (this.values.get(i) > maxNum) { //if chosen value at index i is larger than the largest value
					maxNum = this.values.get(i); //set maxNum as the larger value
				}
			}
			return maxNum;
		}
		return null;
	}

	/**
	 * Computes the sum of all values.
	 *
	 * @return the sum
	 */
	public Integer sum() {
		Integer total = 0;
		for (Integer i : this.values) {
			total += i;
		}
		return total;
	}

	/**
	 * Finds the number of values.
	 *
	 * @return the number of values.
	 */
	public Integer len() {
		return this.values.size();
	}

	/**
	 * Reverses the order of values.
	 */
	public void rev() {
		List<Integer> reversedValues = new ArrayList<Integer>();
		
		for (int i = this.values.size() - 1; i >= 0; i--) { //reverse looping
			reversedValues.add(this.values.get(i)); //adding values in reverse order to the reversedValues arraylist
		}
		this.values = reversedValues;
	}

	/**
	 * Removes adjacent duplicate values.
	 */
	public void uniq() {
		int i = 0;
		
		while (i < values.size() - 1) {
			if (values.get(i).equals(values.get(i + 1))) { //value equals to adjacent value
				values.remove(i); //remove value
				i -= 1;
			} i++;
		}
	}
	

	/**
	 * Sorts the list in ascending order.
	 */
	public void sort() {
		Collections.sort(this.values);
	}

	/**
	 * Computes the set difference of the entries.
	 *
	 * @param  entries the entries
	 * @return         the resulting values
	 */
	public static List<Integer> diff(List<Entry> entries) {
		if (entries.size() > 0) { //valid entries size
			List<Integer> newList = new ArrayList<Integer>(); //creating new list
			
			for (int i = 0; i < entries.size(); i++) {
				Entry entry_now = entries.get(i); //creating entry object
				entry_now.sort(); //sorting in ascending order
				entry_now.uniq(); //removing adjacent duplicate values
				
				for (int n = 0; n < entry_now.values.size(); n++) { //looping through the entry object, entry_now
					newList.add(entry_now.values.get(n));
				}
			}
			List<Integer> differentList = new ArrayList<Integer>(); //creating a new list
			
			int i = 0;
			while (i < newList.size()) {
				Boolean isDuplicate = false;
				
				int n = 0;
				while (n < newList.size()) {
					
					if (newList.get(i) == newList.get(n) && i != n) {
						isDuplicate = true;
					} n++;
				} if (isDuplicate == false) {
					differentList.add(newList.get(i));
				} i++;
			}
			return differentList;
		} else { // entries size is invalid (entries.size() <= 0)
			return null;
		}
	}
	
	
	
	/**
	 * Computes the set intersection of the entries.
	 *
	 * @param  entries the entries
	 * @return         the resulting values
	 */
	public static List<Integer> inter(List<Entry> entries) {
		List<Integer> intersection = new ArrayList<Integer>();
		Boolean equal = false;
		
		for (int i = 0; i < entries.size(); i++) { //looping through each entry
			Entry presentValue = entries.get(i); //creating an Entry object
			if (equal == false) { 
				intersection.addAll(presentValue.values);
				equal = true;
			} else {
				intersection.retainAll(presentValue.values); //retain all intersecting values
			}
		}
		return intersection;
	}

	/**
	 * Computes the set union of the entries.
	 *
	 * @param  entries the entries
	 * @return         the resulting values
	 */
	public static List<Integer> union(List<Entry> entries) {
		List<Integer> unionList = new ArrayList<Integer>();
		
		for (Entry val : entries) { //looping through all entries
			unionList.addAll(val.values); //adding all 
		}
		
		for (int i = 0; i < unionList.size(); i++) { //looping through each entry
			Integer presentValue = unionList.get(i);
			for (int n = 0; n < unionList.size(); n++) {
				if (i == n) {
					continue;
				} if (presentValue == unionList.get(n)) {
					unionList.remove(n);
				}
			}
		}	
		return unionList;
	}

	/**
	 * Computes the Cartesian product of the entries.
	 *
	 * @param  entries the entries
	 * @return         the resulting values
	 */
	public static List<List<Integer>> cartprod(List<Entry> entries) {
		List<List<Integer>> newList = new ArrayList<List<Integer>>();
		
		for (int i = 0; i < entries.size(); i++) {
			newList.add(entries.get(i).values);
		}
		
		List<List<Integer>> finalList = new ArrayList<List<Integer>>();
		
		if (newList.size() == 0) { //if newList is empty because there are no entries
			List<Integer> vacantList = new ArrayList<Integer>(); //creating a new empty integer list
			finalList.add(vacantList); //the finalList will hold an empty list
			return finalList;
		} else {
			List<Integer> firstList = newList.get(0); //get first item in newList
			List<List<Integer>> lastList = cartprod(entries.subList(1, newList.size())); //excludes first item in newList
			
			for (int i : firstList) {
				
				for (List<Integer> list : lastList) {
					ArrayList<Integer> outcomeList = new ArrayList<Integer>();
					outcomeList.add(i);
					outcomeList.addAll(list);
					finalList.add(outcomeList);
					
				}
			}
		}
		return finalList;
	}

	/**
	 * Getter method
	 *
	 * @return  this.key
	 */
	public String getKey() {
		return this.key;
	}
	
	/**
	 * Formats all the entries for display.
	 *
	 * @param  entries the entries to display
	 * @return         the entries with their values
	 */
	public static String listAllEntries(List<Entry> entries) { 
		if (entries.size() >= 1) { //valid entries size
			StringBuilder entriesList = new StringBuilder();
			
			for (int i = 0; i < entries.size() - 1; i++) {
				Entry presentEntry = entries.get(i); //calling get method to get current entry
				entriesList.append(presentEntry.key + " " + presentEntry.get() + "\n"); //appending current entry's key and its value
			} Entry concludingEntry = entries.get(entries.size() - 1); //
			entriesList.append(concludingEntry.key + " " + concludingEntry.get()); //appending last entry's key and value
			return entriesList.toString(); //returning all entries
		} else {
			return null;
		}
	}
}
