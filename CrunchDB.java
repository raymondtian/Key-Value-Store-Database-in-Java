import java.util.List;
import java.util.Map;
import java.util.*;

/** 
 * This is responsible for the overall management of the database.
 * CrunchDB should deal with taking input from the user and displaying the correct
 * output while passing off the more complicated work to the corresponding
 * classes.
 */

public class CrunchDB {

	// TODO the following data structures are responsible for storing the
	// entries and snapshots related to this instance of the database. You can
	// modify them if you wish, as long as you do not change any method
	// signatures.
	private int eyeD = 1; //snapshot id begins from 1
	private List<Entry> entries;
	private List<Snapshot> snapshots;

	public CrunchDB() {
		this.entries = new ArrayList<Entry>();
		this.snapshots = new ArrayList<Snapshot>();
	}

	/** 
	 * Displays all keys in current state.
	 */
	private void listKeys() {
		if (entries.size() == 0) { //entries is an empty list
			System.out.println("no keys");
		} else { //entries is not empty
			
			for (int i = this.entries.size() - 1; i >= 0; i--) { //reverse loop for correct format
				System.out.println(this.entries.get(i).getKey());
			}
		}
	}

	/**
	 * Displays all entries in the current state.
	 */
	private void listEntries() {
		List<Entry> entriesList = new ArrayList<Entry>();
		
		if (entries.size() == 0) { //entries is an empty list
			System.out.println("no entries");
		} else { //entries is not empty
			
			for (int i = this.entries.size() - 1; i >= 0; i--) { //reverse loop
				entriesList.add(this.entries.get(i));
			} System.out.println(Entry.listAllEntries(entriesList));
		}
	}
	
	/**
	 * Displays all snapshots in the current state.
	 */
	private void listSnapshot() {
		List<Snapshot> snapShot = new ArrayList<Snapshot>();
		
		if (this.snapshots.size() == 0) { //empty list
			System.out.println("no snapshots");
		} else { //snapshots is not empty
			for (int i = this.snapshots.size() - 1; i >= 0; i--) { //reverse loops
				snapShot.add(this.snapshots.get(i));
			} System.out.println(Snapshot.listAllSnapshots(snapShot));
		}
	}
	
	
	/**
	 * Displays entry values.
	 *
	 * @param key the key of the entry
	 */
	private void get(String key) {		
		int index = -1;
		
		for (int i = 0; i < entries.size(); i++) {
			
			if (entries.get(i).getKey().equals(key)) { //inputted key is equal to key within entries at specified index
				index = i;
			}
		} if (index == -1) { //if index is invalid
			System.out.println("no such key");
		} else {
			System.out.println(entries.get(index).get());
		}
	}
	
	/**
	 * Deletes entry from current state.
	 *
	 * @param key the key of the entry
	 */
	private void del(String key) {
		Boolean isValueEqual = false; //boolean value for determining whether a value is equal or not
		
		for (int i = 0; i < entries.size(); i++) {
			
			if (entries.get(i).getKey().equals(key)) { //inputted key equals to the key within entries at specified index
				isValueEqual = true; //value is equal
				entries.remove(i); //remove entry from current state
			}
		} if (isValueEqual == true) {
			System.out.println("ok");
		} if (isValueEqual == false) { //value does not exist
			System.out.println("no such key");
		}
	}

	/**
	 * Deletes entry from current state and snapshots.
	 *
	 * @param key the key of the entry
	 */
	private void purge(String key) {
		for (int i = 0; i < entries.size(); i++) {
			
			if (entries.get(i).getKey().equals(key)) { //inputted key equals to the key within entries at specified index
				entries.remove(i); //removing from current state
			}
		}
		
		for (int i = 0; i < snapshots.size(); i++) {
			
			for (int n = 0; n < snapshots.get(i).getEntries().size(); n++) {
				Entry presentEntry = snapshots.get(i).getEntries().get(n);
				
				if (presentEntry.getKey().equals(key)) {
					snapshots.get(i).getEntries().remove(n); //removing from snapshots
				}
			}
		}
		System.out.println("ok");
	}

	/**
	 * Sets entry values.
	 *
	 * @param key    the key of the entry
	 * @param values the values to set
	 */
	private void set(String key, List<Integer> values) {
		Boolean isValueEqual = false; //boolean value for determining whether a value is equal or not
		
		for (int i = 0; i < entries.size(); i++) {
			
			if (entries.get(i).getKey().equals(key)) { //inputted key and value is equal to key and its value that already exists in entries
				entries.get(i).set(values); //replace the value of the inputted key
				isValueEqual = true;
			}
		} if (isValueEqual == false) { //value is not equal
			Entry recentEntry = new Entry(key, values); //creating entry object
			this.entries.add(recentEntry); //adding entry object to entries
		}
		System.out.println("ok");
	}

	/**
	 * Pushes values to the front.
	 *
	 * @param key    the key of the entry
	 * @param values the values to push
	 */
	private void push(String key, List<Integer> values) {
		Boolean isValueEqual = false; //boolean value for determining whether a value is equal or not
		
		for (int i = 0; i < entries.size(); i++) {
			if (entries.get(i).getKey().equals(key)) { //inputted key and values equal to key and its value that already exists in entries
				isValueEqual = true;
				entries.get(i).push(values);
			} 
		} if (isValueEqual == true) {
			System.out.println("ok");
		} if (isValueEqual == false) { //key and value are not equal to existing key and values in entries
			System.out.println("no such key");
		}
	}
	
	

	/**
	 * Appends values to the back.
	 *
	 * @param key    the key of the entry
	 * @param values the values to append
	 */
	private void append(String key, List<Integer> values) {
		Boolean isValueEqual = false; //boolean value for determining whether a value is equal or not
		
		if (entries.size() == 0 && isValueEqual == false) { //entries list is empty
			System.out.println("no such key");
		} else {
			for (int i = 0; i < entries.size(); i++) {
				if (entries.get(i).getKey().equals(key)) { //key that is inputted equals to existing key in entries
					entries.get(i).append(values);
					isValueEqual = true;
				} if (isValueEqual == true) {
					System.out.println("ok");
				} 
			}
		}
	}

	/**
	 * Displays value at index.
	 *
	 * @param key   the key of the entry
	 * @param index the index to display
	 */
	private void pick(String key, int index) {
		Boolean isValueEqual = false; //boolean value for determining whether a value is equal or not
		String valueAtIndex = ""; //empty string
	
		for (int i = 0; i < entries.size(); i++) {
			
			if (entries.get(i).getKey().equals(key)) { //inputted key and value is equal to a key and its value that already exists in entries
				Integer num = entries.get(i).pick(index); //calling pick method to acquire value at index
				isValueEqual = true;
				valueAtIndex += num;
			} 
		} if (isValueEqual == true) {
				if (valueAtIndex.equals("null")) {
					System.out.println("index out of range");
				} else {
				System.out.println(valueAtIndex);
				}
			} if (isValueEqual == false) { //key and index don't equal key and index existing in entries
				System.out.println("no such key");
			} 
	}
	
	/**
	 * Displays and removes the value at index.
	 *
	 * @param key   the key of the entry
	 * @param index the index to pluck
	 */
	private void pluck(String key, int index) {
		Boolean isValueEqual = false; //boolean value for determining whether a value is equal or not
		String valueAtIndex = "";
		
		for (int i = 0; i < entries.size(); i++) {
			
			if (entries.get(i).getKey().equals(key)) { //key and index inputted equal to key and its index existing in entries
				Integer num = entries.get(i).pluck(index);
				isValueEqual = true;
				valueAtIndex += num;
			}
		} if (isValueEqual == true) {
			if (valueAtIndex.equals("null")) {
				System.out.println("index out of range");	
			} else {
				System.out.println(valueAtIndex);
			}
		} if (isValueEqual == false) { //key and index does not equal to key and index existing in entries
			System.out.println("no such key");
		}
	}

	/**
	 * Displays and removes the front value.
	 *
	 * @param key the key of the entry
	 */
	private void pop(String key) {
		Boolean isValueEqual = false; //boolean value for determining whether a value is equal or not
		String frontValue = "";
		
		for (int i = 0; i < entries.size(); i++) {
			
			if (entries.get(i).getKey().equals(key)) { //key inputted is equal to key that already exists in entries
				Integer num = entries.get(i).pop();
				isValueEqual = true;
				frontValue += num;
			}
		} if (isValueEqual == true) {
			if (frontValue.equals("null")) {
				System.out.println("nil");
			} else {
				System.out.println(frontValue);
			}
		} if (isValueEqual == false) { //key does not equal to key existing in entries
			System.out.println("no such key");
		}
	}
	
	/** 
	 * Deletes snapshot.
	 *
	 * @param id the id of the snapshot
	 */
	private void drop(int id) {
		Boolean isValueEqual = false; //boolean value for determining whether a value is equal or not
		
		for (int i = 0; i < snapshots.size(); i++) {
			if (snapshots.get(i).getID() == id) { //id inputted is equal to id that exists already in snapshot
				isValueEqual = true;
				snapshots.remove(i);
			}
		} if (isValueEqual == true) {
			System.out.println("ok");
		} if (isValueEqual == false) { //id, and thus its corresponding snapshot does not exist
			System.out.println("no such snapshot");
		}
	}

	/**
	 * Restores to snapshot and deletes newer snapshots.
	 *
	 * @param id the id of the snapshot
	 */
	private void rollback(int id) {
		List<Snapshot> snapShot = new ArrayList<Snapshot>();
		Snapshot snapshot_now = null;
		Boolean isValueEqual = false; //boolean value for determining whether a value is equal or not
		int ind = 0;
		
		for (int i = 0; i < snapshots.size(); i++) {
			if (snapshots.get(i).getID() == id) { //inputted id is equal to id that already exists in snapshots
				isValueEqual = true;
				ind = i;
				snapshot_now = snapshots.get(i);
			}
		} if (isValueEqual == true) {
			for (int i = 0; i < snapshots.size(); i++) {
				if (i < ind) {
					snapShot.add(snapshots.get(i));
				}
			} 
			snapShot.add(snapshot_now);
			this.entries = snapshot_now.getEntries();
		} 
		this.snapshots = snapShot;
		
		if (isValueEqual == true) {
			System.out.println("ok");
		} if (isValueEqual == false) { //id and thus its corresponding snapshot does not exist
			System.out.println("no such snapshot");
		}
	}

	/** 
	 * Replaces current state with a copy of snapshot.
	 *
	 * @param id the id of the snapshot
	 */
	private void checkout(int id) {
		Boolean isValueEqual = false; //boolean value for determining whether a value is equal or not
		
		for (int i = 0; i < snapshots.size(); i++) {
			
			if (snapshots.get(i).getID() == id) { //id inputted is equal to id that already exists in snapshot
				List<Entry> entryList = new ArrayList<Entry>(); //creating new list
				
				for (int n = 0; n <snapshots.get(i).getEntries().size(); n++) {
					Entry entry = snapshots.get(i).getEntries().get(n);
					Entry anotherEntry = new Entry(new String(entry.getKey()), new ArrayList<Integer>(entry.getValues()));
					entryList.add(entry);
				}
				isValueEqual = true;
				this.entries = entryList;
			}
		} if (isValueEqual == true) {
			System.out.println("ok");
		} if (isValueEqual == false) { //id and thus its corresponding snapshot does not exist
			System.out.println("no such snapshot");
		}
	}

	/** 
	 * Saves the current state as a snapshot.
	 */
	private void snapshot() {
		List<Entry> currentStateOfEntries = new ArrayList<Entry>(); //creating new list
		
		for (Entry e : this.entries) {
			List<Integer> currentValues = new ArrayList<Integer>(e.getValues());
			String entryKey = new String(e.getKey());
			currentStateOfEntries.add(new Entry(entryKey, currentValues));
		}
		Snapshot snapShot = new Snapshot(eyeD, currentStateOfEntries);
		this.snapshots.add(snapShot);
		System.out.println("saved as snapshot " + eyeD);
		eyeD++;
	}

	/**
	 * Saves snapshot to file.
	 *
	 * @param id       the id of the snapshot 
	 * @param filename the name of the file
	 */
	private void archive(int id, String filename) {
		Boolean isValueEqual = false; //boolean value for determining whether a value is equal or not
		
		for (int i = 0; i < snapshots.size(); i++) {
			
			if (snapshots.get(i).getID() == id) { //id and filename inputted both exisst in snapshots
				isValueEqual = true;
				snapshots.get(i).archive(filename);
			}
		}if (isValueEqual == true) {
			System.out.println("ok");
		} if (isValueEqual == false) {
			System.out.println("no such snapshot");
		}
	}

	/**
	 * Loads and restores snapshot from file.
	 *
	 * @param filename the name of the file
	 */
	private void restore(String filename) {
		this.entries = Snapshot.restore(filename);
		System.out.println("ok");
	}
	
	/**
	 * Displays minimum value.
	 *
	 * @param key the key of the entry
	 */
	private void min(String key) {
		Boolean isValueEqual = false; //boolean value for determining whether a value is equal or not
		Integer minValue = null; //setting minimum value to null
		
		for (int i = 0; i < entries.size(); i++) {
			
			if (entries.get(i).getKey().equals(key)) { //inputted key equal to key in entries that already exists
				isValueEqual = true;
				minValue = entries.get(i).min(); //calling min method
			}
		} if (isValueEqual == true) {
			System.out.println(minValue);
		} if (isValueEqual == false) { //key does not exist
			System.out.println("no such key");
		}
	}

	/**
	 * Displays maximum value.
	 *
	 * @param key the of the entry
	 */
	private void max(String key) {
		Boolean isValueEqual = false; //boolean value for determining whether a value is equal or not
		Integer maxValue = null; //setting max value to null
		
		for (int i = 0; i < entries.size(); i++) {
			
			if (entries.get(i).getKey().equals(key)) { //inputted key is equal to key that already exists in entries
				isValueEqual = true;
				maxValue = entries.get(i).max(); //calling max method
			}
		} if (isValueEqual == true) {
			System.out.println(maxValue);
		} if (isValueEqual == false) { //key does not exist
			System.out.println("no such key");
		}
	}

	/**
	 * Displays the sum of values.
	 *
	 * @param key the key of the entry
	 */
	private void sum(String key) {
		Boolean isValueEqual = false; //boolean value for determining whether a value is equal or not
		Integer total = null; //setting total/sum to null
		
		for (int i = 0; i < entries.size(); i++) {
			
			if (entries.get(i).getKey().equals(key)) { //inputted key is equal to a key already existing in entries
				isValueEqual = true;
				total = entries.get(i).sum(); //calling sum method
			}
		} if (isValueEqual == true) {
			System.out.println(total);
		} if (isValueEqual == false) { //key does not exist
			System.out.println("no such key");
		}
	}

	/**
	 * Displays the number of values.
	 *
	 * @param key the key of the entry
	 */
	private void len(String key) {
		Boolean isValueEqual = false; //boolean value for determining whether a value is equal or not
		Integer numOfValues = null; //setting number of values to null
		
		for (int i = 0; i < entries.size(); i++) {
			
			if (entries.get(i).getKey().equals(key)) { //inputted key equals to existing key in entries
				isValueEqual = true;
				numOfValues = entries.get(i).len(); //calling len method
			}
		} if (isValueEqual == true) {
			System.out.println(numOfValues);
		} if (isValueEqual == false) { //key does not exist
			System.out.println("no such key");
		}
	}

	/**
	 * Reverses the order of values.
	 *
	 * @param key the key of the entry
	 */
	private void rev(String key) {
		Boolean isValueEqual = false; //boolean value for determining whether a value is equal or not
		
		for (int i = 0; i < entries.size(); i++) {
			
			if (entries.get(i).getKey().equals(key)) { //inputted key equal to existing key in entries
				isValueEqual = true;
				entries.get(i).rev(); //calling rev method to reverse the order
			}
		} if (isValueEqual == true) {
			System.out.println("ok");
		} if (isValueEqual == false) { //key doesnt exist
			System.out.println("no such key");
		}
	}

	/**
	 * Removes repeated adjacent values.
	 *
	 * @param key the key of the entry
	 */
	private void uniq(String key) {
		Boolean isValueEqual = false; //boolean value for determining whether a value is equal or not
		
		for (int i = 0; i < entries.size(); i++) {
			
			if (entries.get(i).getKey().equals(key)) { //inputted key equal to existing key in entries
				isValueEqual = true;
				entries.get(i).uniq(); //calling uniq method
			}
		} if (isValueEqual == true) {
			System.out.println("ok");
		} if (isValueEqual == false) { //key doesnt exist
			System.out.println("no such key");
		}
	}

	/** 
	 * Sorts values in ascending order.
	 *
	 * @param key the key of the entry
	 */
	private void sort(String key) {
		Boolean isValueEqual = false; //boolean value for determining whether a value is equal or not
		
		for (int i = 0; i < entries.size(); i++) {
			
			if (entries.get(i).getKey().equals(key)) { //inputted key equals to existing key in entries
				isValueEqual = true;
				entries.get(i).sort(); //calling sort method
			}
		} if (isValueEqual == true) {
			System.out.println("ok");
		} if (isValueEqual == false) { //key doesnt exist
			System.out.println("no such key");
		}
	}

	/**
	 * Displays set difference of values in keys. 
	 * Needs at least two keys.
	 *
	 * @param keys the keys of the entries
	 */
	private void diff(List<String> keys) {
		List<Entry> presentEntries = new ArrayList<Entry>();
		
		for (int i = 0; i < entries.size(); i++) { //loop through entries
			
			for (int n = 0; n < keys.size(); n++) { //loop through keys
				
				if (entries.get(i).getKey().equals(keys.get(n))) {
					presentEntries.add(entries.get(i));
				}
			}
		} if (keys.size() != presentEntries.size()) {
			System.out.println("no such key");
		} else {
			List<Integer> outcome = Entry.diff(presentEntries);
			String formattedOutcome = outcome.toString()
				.replace(",", " "); //converting outcome to a string and replacing each "," with an empty string
			System.out.println(formattedOutcome);
		}
	}

	/**
	 * Displays set intersection of values in keys.
	 * Needs at least two keys.
	 *
	 * @param keys the keys of the entries
	 */
	private void inter(List<String> keys) {
		List<Entry> presentEntries = new ArrayList<Entry>();
		
		for (int i = 0; i < entries.size(); i++) { //loop through entries
			
			for (int n = 0; n < keys.size(); n++) { //loop through keys
				
				if (entries.get(i).getKey().equals(keys.get(n))) {
					presentEntries.add(entries.get(i));
				}
			}
		} if (keys.size() != presentEntries.size()) {
			System.out.println("no such key");
		} else {
			List<Integer> outcome = Entry.inter(presentEntries);
			String formattedOutcome = outcome.toString()
				.replaceAll(",", ""); //converting outcome to a string and replacing each "," with an empty string
			System.out.println(formattedOutcome);
		}
	}

	/**
	 * Displays set union of values in keys.
	 * Needs at least two keys.
	 *
	 * @param keys the keys of the entries
	 */
	private void union(List<String> keys) {
		List<Entry> presentEntries = new ArrayList<Entry>();
		
		for (int i = 0; i < entries.size(); i++) { //loop through entries
			
			for (int n = 0; n < keys.size(); n++) { //loop through keys
				
				if (entries.get(i).getKey().equals(keys.get(n))) {
					presentEntries.add(entries.get(i));
				}
			}
		} if (keys.size() != presentEntries.size()) {
			System.out.println("no such key");
		} else {
			List<Integer> outcome = Entry.union(presentEntries);
			String formattedOutcome = outcome.toString()
				.replace(",", ""); //converting outcome to a string and replacing each "," with an empty string
			System.out.println(formattedOutcome);
		}
	}

	/** 
	 * Displays cartesian product of the sets.
	 * Needs at least two keys.
	 *
	 * @param keys the keys of the entries
	 */
	private void cartprod(List<String> keys) {
		List<Entry> presentEntries = new ArrayList<Entry>();

		for (int i = 0; i < entries.size(); i++) { //loop through entries

			for (int n = 0; n < keys.size(); n++) { //loop through keys

				if (entries.get(i).getKey().equals(keys.get(n))) {
					presentEntries.add(entries.get(i));
				}
			}
		} if (keys.size() != presentEntries.size()) {
			System.out.println("no such key");
		} else {
			List<List<Integer>> finalList = Entry.cartprod(presentEntries);
		
			if (finalList.size() > 0) {
				StringBuilder format1 = new StringBuilder("[ ");
			
				for (int i = 0; i < finalList.size(); i++) {
					StringBuilder format2 = new StringBuilder("[");
				
					for (int n = 0; n < finalList.get(i).size() - 1; n++) {
						format2.append(finalList.get(i).get(n) + " ");
					} format2.append(finalList.get(i).get(finalList.get(i).size() - 1) + "]");
				
					if (i < finalList.size() - 1) {
						format1.append(format2 + " ");
					} else {
						format1.append(format2 + " ]");
					}
				}
				System.out.println(format1.toString());
			}
		}
	}
	
	
	private static final String HELP =
		"BYE   clear database and exit\n"+
		"HELP  display this help message\n"+
		"\n"+
		"LIST KEYS       displays all keys in current state\n"+
		"LIST ENTRIES    displays all entries in current state\n"+
		"LIST SNAPSHOTS  displays all snapshots in the database\n"+
		"\n"+
		"GET <key>    displays entry values\n"+
		"DEL <key>    deletes entry from current state\n"+
		"PURGE <key>  deletes entry from current state and snapshots\n"+
		"\n"+
		"SET <key> <value ...>     sets entry values\n"+
		"PUSH <key> <value ...>    pushes values to the front\n"+
		"APPEND <key> <value ...>  appends values to the back\n"+
		"\n"+
		"PICK <key> <index>   displays value at index\n"+
		"PLUCK <key> <index>  displays and removes value at index\n"+
		"POP <key>            displays and removes the front value\n"+
		"\n"+
		"DROP <id>      deletes snapshot\n"+
		"ROLLBACK <id>  restores to snapshot and deletes newer snapshots\n"+
		"CHECKOUT <id>  replaces current state with a copy of snapshot\n"+
		"SNAPSHOT       saves the current state as a snapshot\n"+
		"\n"+
		"ARCHIVE <id> <filename> saves snapshot to file\n"+
		"RESTORE <filename> loads snapshot from file\n"+
		"\n"+
		"MIN <key>  displays minimum value\n"+
		"MAX <key>  displays maximum value\n"+
		"SUM <key>  displays sum of values\n"+
		"LEN <key>  displays number of values\n"+
		"\n"+
		"REV <key>   reverses order of values\n"+
		"UNIQ <key>  removes repeated adjacent values\n"+
		"SORT <key>  sorts values in ascending order\n"+
		"\n"+
		"DIFF <key> <key ...>   displays set difference of values in keys\n"+
		"INTER <key> <key ...>  displays set intersection of values in keys\n"+
		"UNION <key> <key ...>  displays set union of values in keys\n"+
		"CARTPROD <key> <key ...>  displays set union of values in keys";
	
	public static void bye() {
		System.out.println("bye");
	}
	
	public static void help() {
		System.out.println(HELP);
	}
	
	
	public static void main(String[] args) {
		CrunchDB DBCrunch = new CrunchDB(); //creating CrunchDB object
		Scanner sc = new Scanner(System.in); //creating a Scanner object
		
		while (true) {
			System.out.print("> ");
			String []input = sc.nextLine().split(" "); //splitting input list up with a space
			input[0] = input[0].toUpperCase(); //coverting first element to all upper case letters
			
			if (input[0].equals("BYE")) {
				bye();
				break;
			} else if (input[0].equals("HELP")) {
				help();
			} else if (input[0].equals("LIST")) {
				String asd = input[1].toUpperCase();
				
				if (asd.equals("ENTRIES")) {
					DBCrunch.listEntries();
				} else if (asd.equals("SNAPSHOTS")) {
					DBCrunch.listSnapshot();
				} else if (asd.equals("KEYS")) {
					DBCrunch.listKeys();
				}
			} else if (input[0].equals("PURGE")) {
				String purGe = input[1];
				DBCrunch.purge(purGe);
			} else if (input[0].equals("DEL")) {
				String dEl = input[1];
				DBCrunch.del(dEl);
			} else if (input[0].equals("GET")) {
				String gEt = input[1];
				DBCrunch.get(gEt);
			} else if (input[0].equals("CHECKOUT")) {
				int checkOut = Integer.parseInt(input[1]);
				DBCrunch.checkout(checkOut);
			} else if (input[0].equals("DROP")) {
				int drOp = Integer.parseInt(input[1]);
				DBCrunch.drop(drOp);
			} else if (input[0].equals("ROLLBACK")) {
				int rollBack = Integer.parseInt(input[1]);
				DBCrunch.rollback(rollBack);
			} else if (input[0].equals("SNAPSHOT")) {
				DBCrunch.snapshot();
			} else if (input[0].equals("POP")) {
				String pOp = input[1];
				DBCrunch.pop(pOp);
			} else if (input[0].equals("PLUCK")) {
				int ind = Integer.parseInt(input[2]);
				String pluCk = input[1];
				DBCrunch.pluck(pluCk, ind);
			} else if (input[0].equals("PICK")) {
				int ind = Integer.parseInt(input[2]);
				String piCk = input[1];
				DBCrunch.pick(piCk, ind);
			} else if (input[0].equals("APPEND")) {
				String appeNd = input[1];
				List<Integer> appendedList = new ArrayList<Integer>();
				
				for (int i = 2; i < input.length; i++) {
					appendedList.add(Integer.parseInt(input[i]));
				} DBCrunch.append(appeNd, appendedList);
			} else if (input[0].equals("SET")) {
				String sEt = input[1];
				List<Integer> list = new ArrayList<Integer>();
				
				for (int i = 2; i < input.length; i++) {
					list.add(Integer.parseInt(input[i]));
				} DBCrunch.set(sEt, list);
			} else if (input[0].equals("PUSH")) {
				String puSh = input[1];
				List<Integer> list = new ArrayList<Integer>();
				
				for (int i = 2; i < input.length; i++) {
					list.add(Integer.parseInt(input[i]));
				} DBCrunch.push(puSh, list);
			} else if (input[0].equals("ARCHIVE")) {
				String archiVe = input[2];
				int eyeD = Integer.parseInt(input[1]);
				DBCrunch.archive(eyeD, archiVe);
			} else if (input[0].equals("RESTORE")) {
				String restoRe = input [1];
				DBCrunch.restore(restoRe);
			} else if (input[0].equals("SUM")) {
				String sUm = input[1];
				DBCrunch.sum(sUm);
			} else if (input[0].equals("MIN")) {
				String mIn = input[1];
				DBCrunch.min(mIn);
			} else if (input[0].equals("MAX")) {
				String mAx = input[1];
				DBCrunch.max(mAx);
			} else if (input[0].equals("LEN")) {
				String lEn = input[1];
				DBCrunch.len(lEn);
			} else if (input[0].equals("SORT")) {
				String soRt = input[1];
				DBCrunch.sort(soRt);
			} else if (input[0].equals("UNIQ")) {
				String unIq = input[1];
				DBCrunch.uniq(unIq);
			} else if (input[0].equals("REV")) {
				String rEv = input[1];
				DBCrunch.rev(rEv);
			} else if (input[0].equals("UNION")) {
				List<String> uniOn = new ArrayList<String>();
				for (int i = 1; i < input.length; i++) {
					uniOn.add(input[i]);
				} DBCrunch.union(uniOn);
			} else if (input[0].equals("INTER")) {
				List<String> intEr = new ArrayList<String>();
				for (int i = 1; i < input.length; i++) {
					intEr.add(input[i]);
				} DBCrunch.inter(intEr);
			} else if (input[0].equals("DIFF")) {
				List<String> diFf = new ArrayList<String>();
				for (int i = 1; i < input.length; i++) {
					diFf.add(input[i]);
				} DBCrunch.union(diFf);
			} else if (input[0].equals("CARTPROD")) {
				List<String> cartProd = new ArrayList<String>();
				for (int i = 1; i < input.length; i++) {
					cartProd.add(input[i]);
				} DBCrunch.cartprod(cartProd);
			} System.out.println("");
		}
	}
}
