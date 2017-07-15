package com.abhi.categories;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

/**
 * Read a file (name passed in as 1st argument to program) -- Input
 * Each line of the file has a category, a space, and a sub-category (sub-category can have whitespace within it)
 * Want to:
	-- Only process the pair (category, sub-category) once
	-- If a pair appears twice, ignore the second one
	-- Keep track of the order of the first occurrence of each pair
	-- Keep track of the count for each category
	-- Legal category values are: PERSON PLACE ANIMAL COMPUTER OTHER -- CONSTANT
	-- Illegal category values should be ignored
 *
 */
public class App 
{

	//final List of pre-populated unique categories and arraylist that maintains insertion order. 
	private final ArrayList<String> legalCategoriesList;
	
	public static final String COUNTS_ARR = "COUNTS_ARR";
	public static final String UNIQUE_CAT_LINE_SET = "UNIQUE_CAT_LINE_SET";
	
	public App(ArrayList<String> legalCategoriesList){
		this.legalCategoriesList = legalCategoriesList;
	}	
	
	/**
	 * run main pass input file argument as path of the file /categories/file4.txt
	 * @param args
	 */
    public static void main( String[] args ) {
    	//List of pre-populated unique categories and arraylist that maintains insertion order. 
    	ArrayList<String> legalCategoriesList = new ArrayList<String>();
		legalCategoriesList.add("PERSON");
		legalCategoriesList.add("PLACE");
		legalCategoriesList.add("ANIMAL");
		legalCategoriesList.add("COMPUTER");
		legalCategoriesList.add("OTHER");
		
    	App app = new App(legalCategoriesList);
    	if(args[0] != null){
    		app.outputWriter(app.populateCategories(new File(args[0])));
    	}
    }
    
    /**
     * 
     * @param file
     */
    public Map<String, Object> populateCategories(File file){
    	Scanner scanner = null;
    	Map<String, Object> resultMap = null;
    	try{ 
    		//Output count of each legal categories
    		//pre-initialized array of category counts will use legalCategoriesList index as an order and increase counter.
    		int[] counts = new int[legalCategoriesList.size()];
    		
    		//output : LinkedHashSet that keeps track of insertion order and set that store only unique Strings.
    		Set<String> uniqueCategofiesLine = new LinkedHashSet<String>();
    		
    		
    		//initializing file Scanner 
    		scanner = new Scanner(file);
	    	while (scanner.hasNextLine()) {
	    		String line = scanner.nextLine();
	    		//sending line to parse from legalCategoriesList and getting back legal category if present otherwise null
	        	String category = parseCategory(line);
	        	if(category != null && uniqueCategofiesLine.add(line)){//ignore in case of null or this line was already considered in earlier run
	        		int indexOf = this.legalCategoriesList.indexOf(category);
	        		if(indexOf != -1){
	        			counts[indexOf] = counts[indexOf]+1;//increase counter if found category
	        		}
	        	}
			}
	    	resultMap = new HashMap<String, Object>();
	    	resultMap.put(COUNTS_ARR, counts);
	    	resultMap.put(UNIQUE_CAT_LINE_SET, uniqueCategofiesLine);
    	}catch(FileNotFoundException e){
    		System.err.println("Supplied file is not found, please check name or provide correct path.");
    	}finally {
    		//close scanner to avoid resource leak.
			if(scanner != null){
				scanner.close();
			}
		}
    	return resultMap;
    }
    /**
     * method that parses String line and returns if already defined category in the static set exists.
     * if determined category does not exist in the line, returns null.
     * it determine category based on first occurrence of space in the line.
     * @param String new line
     * @param List<String> legalCategoriesList
     * @return String category
     */
    public String parseCategory(String line){
    	if(line != null && !line.isEmpty()){
    		int spaceIndex = line.indexOf(" ");
    		if(spaceIndex != -1){
    			String category = line.substring(0, spaceIndex);
    			if(this.legalCategoriesList.contains(category)){//ignore category that is not found in legalCategoriesList
    				return category;
    			}
    		}
    	}   
    	//case returning null if not worked with the business logic. 
    	return null;    	
    }
    
    /**
     * writing output to console 
     */
    @SuppressWarnings("unchecked")
	public void outputWriter(Map<String, Object> resultMap){
    	//Writing output 
    	System.out.println("Category\tCount");
    	int[] counts = (int[]) resultMap.get(COUNTS_ARR);
        for(String string:this.legalCategoriesList){
        	System.out.print(string+"\t");
        	int indexOf = this.legalCategoriesList.indexOf(string);
   			System.out.print(counts[indexOf]);
    		System.out.print("\n");
        }
        System.out.print("\n");
        Set<String> set = (Set<String>) resultMap.get(UNIQUE_CAT_LINE_SET);
        set.stream().forEach(line -> System.out.println(line));
      
    }
}
