package com.abhi.categories;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;

/**
 * Hello world!
 *
 */
public class App 
{
	private static Set<String> set = null;
	private static SortedMap<String, Integer> sortedMap = new TreeMap<String, Integer>();

	
	static{
		sortedMap.put("Cat1", 0);
		sortedMap.put("Cat2", 0);
		sortedMap.put("Cat3", 0);
		set = sortedMap.keySet();
	}
	
    public static void main( String[] args )
    {
        System.out.println( "Hello World!" );
    }
    /**
     * 
     * @param file
     */
    public static void populateCategories(String file){
    	Scanner scanner = null;
    	try{ 
    		scanner = new Scanner(new File(file));
	    	while (scanner.hasNextLine()) {
	        	String category = parseCategory(scanner.nextLine());
	        	if(category != null){
	        		sortedMap.put(category, (sortedMap.get(category)+1));
	        	}
			}
    	}catch(FileNotFoundException e){
    		
    	}finally {
			if(scanner != null){
				scanner.close();
			}
		}
    }
    /**
     * utility method that parses String line and returns if already defined category in the static set exists.
     * if determined category does not exist in the line, returns null.
     * it determine category based on first occurrence of space in the line.
     * @param String new line
     * @return String category
     */
    public static String parseCategory(String line){
    	if(line != null && !line.isEmpty()){
    		int spaceIndex = line.indexOf(" ");
    		if(spaceIndex != -1){
    			String category = line.substring(0, spaceIndex);
    			sortedMap.get(category);
    			if(set.contains(category)){
    				return category;
    			}
    		}
    	}    	
    	return null;    	
    }
}
