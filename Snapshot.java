import java.util.List;
import java.io.*;
import java.util.*;

/**
 * Snapshot deals with storing the id and current state of the database. 
 * As well as storing this data, the Snapshot class should manage operations
 * related to snapshots.
 */

public class Snapshot {
	private int id;
	private List<Entry> entries;

	public Snapshot(int id, List<Entry> entries) {
		// TODO: write this constructor
		this.id = id;
		this.entries = entries;
	}

	/**
	 * Getter method
	 *
	 * @return  this.entries
	 */
	public List<Entry> getEntries() {
		return this.entries;
	}
	/**
	 * Finds and removes the key.
	 *
	 * @param key the key to remove
	 */
	public void removeKey(String key) {
		for (int i = 0; i < entries.size(); i++) {
			if (key.equals(entries.get(i).getKey())) { //inputted key is equal to key within entries at the specified index
				entries.remove(i); //remove the key
			}
		}
	}

	/**
	 * Finds the list of entries to restore.
	 *
	 * @return the list of entries in the restored state
	 */
	public List<Entry> rollback() {
		return entries;
	}


	/**
	 * Saves the snapshot to file.
	 *
	 * @param filename the name of the file
	 */
	public void archive(String filename) {
		try {
			FileWriter fw = new FileWriter(filename);
			PrintWriter pw = new PrintWriter(fw);
			
			for (int i = 0; i < entries.size(); i++) {
				String key_now = entries.get(i).getKey(); //the current key
				key_now += "|"; //adding "|" for correct formatting
				
				for (int n = 0; n < entries.get(i).getValues().size(); n++) {
					key_now += entries.get(i).getValues().get(n);
					
					if (n != entries.get(i).getValues().size()-1) {
						key_now += ",";
					}
				}
				key_now += "\n";
				pw.print(key_now);
			}
			pw.close();
		} catch (IOException e) {
		}
	}

	/**
	 * Loads and restores a snapshot from file.
	 *
	 * @param  filename the name of the file
	 * @return          the list of entries in the restored state
	 */
	public static List<Entry> restore(String filename) {
		List<Entry> restoredEntries = new ArrayList<Entry>(); //creating new list
		
		try{
			File fi_le = new File(filename);
			Scanner sc = new Scanner(fi_le);
			
			while (sc.hasNextLine()) { //proceed with code only if file contains information
				String a = sc.nextLine();
				String presentKey = ""; //empty string
				String integerString = ""; //empty string
				List<Integer> integersList = new ArrayList<Integer>(); //creating a list
				
				for (int i = 0; i < a.length(); i++) {
					if (i >= 2) {
						integerString += Character.toString(a.charAt(i)); //adding the character at the specified index "i" to empty string
					} if (i == 0) {
						presentKey = Character.toString(a.charAt(i)); //adding the character at the specified index "i" to empty string
					}
				}
				
				String []ints = integerString.split(",");
				
				for (String s : ints) {
					Integer presentValue = Integer.parseInt(s);
					integersList.add(presentValue);
				}
				Entry e = new Entry(presentKey, integersList); //creating new entry object
				restoredEntries.add(e);
			} return restoredEntries;
		} catch (FileNotFoundException f) { //handing error in the case where a file does not exist
		}
		return null;
	}
	
	/**
	 * Getter method
	 *
	 * @return  this.id
	 */
	public int getID() {
		return this.id;
	}
	
	/**
	 * Formats all snapshots for display.
	 *
	 * @param  snapshots the snapshots to display
	 * @return           the snapshots ready to display
	 */
	public static String listAllSnapshots(List<Snapshot> snapshots) {
		if (snapshots.size() >= 1) { //valid snapshots size
			StringBuilder snapshotsList = new StringBuilder(); 
			
			for (int i = 0; i < snapshots.size() - 1; i++) {
				snapshotsList.append(snapshots.get(i).id + "\n"); //accessing id
			} snapshotsList.append(snapshots.get(snapshots.size() - 1).id); //
			return snapshotsList.toString(); //returning all snapshots
		} else {
			return null;
		}
	}
}
