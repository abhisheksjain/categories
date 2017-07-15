package com.abhi.categories;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.io.File;
import java.util.ArrayList;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;


/**
 * Unit test for simple App.
 */
public class AppTest    
{
	
	private App app;
	
	@Before
	public void init(){
    	//List of pre-populated unique categories and arraylist that maintains insertion order. 
    	ArrayList<String> legalCategoriesList = new ArrayList<String>();
		legalCategoriesList.add("PERSON");
		legalCategoriesList.add("PLACE");
		legalCategoriesList.add("ANIMAL");
		legalCategoriesList.add("COMPUTER");
		legalCategoriesList.add("OTHER");
		this.app = new App(legalCategoriesList);
	}


	@Test
    public void testParseCategoryPositive()
    {
		assertEquals("PLACE", this.app.parseCategory("PLACE San Francisco"));
		
		assertEquals("PERSON", this.app.parseCategory("PERSON Abhishek"));
		assertEquals("PERSON", this.app.parseCategory("PERSON Abhishek Jain "));
		
		assertEquals("PLACE", this.app.parseCategory("PLACE New Delhi and "));
		
		//not clear about this test case, is empty or null sub category is valid or not assuming empty is valid sub category

		assertEquals("OTHER", this.app.parseCategory("OTHER   "));
    }
	
	@Test
    public void testParseCategoryNegative()
    {
	
		assertNull(this.app.parseCategory("CAT San Francisco"));
		assertNull(this.app.parseCategory(" "));
		assertNull(this.app.parseCategory("null"));
		assertNull(this.app.parseCategory(" Abhi"));
		assertNull(this.app.parseCategory(" PERSON"));
    }
	
	@Test
    public void testPopulateCategories()
    {
		int[] countsfile1 = {2, 2, 2, 1, 1};
		Map<String, Object> map1 = this.app.populateCategories(new File(getClass().getClassLoader().getResource("testfiles/file1.txt").getFile()));	
		assertArrayEquals(countsfile1, (int[]) map1.get(App.COUNTS_ARR));
		
		int[] countsfile2 = {2, 2, 2, 1, 1};
		Map<String, Object> map2 = this.app.populateCategories(new File(getClass().getClassLoader().getResource("testfiles/file2.txt").getFile()));	
		assertArrayEquals(countsfile2, (int[]) map2.get(App.COUNTS_ARR));
		
		int[] countsfile3 = {2, 2, 2, 1, 0};
		Map<String, Object> map3 = this.app.populateCategories(new File(getClass().getClassLoader().getResource("testfiles/file3.txt").getFile()));	
		assertArrayEquals(countsfile3, (int[]) map3.get(App.COUNTS_ARR));
	
    }
	
	@Test
    public void testOutputWriter()
    {
		this.app.outputWriter(this.app.populateCategories(new File(getClass().getClassLoader().getResource("testfiles/file1.txt").getFile())));	
		this.app.outputWriter(this.app.populateCategories(new File(getClass().getClassLoader().getResource("testfiles/file2.txt").getFile())));
		this.app.outputWriter(this.app.populateCategories(new File(getClass().getClassLoader().getResource("testfiles/file3.txt").getFile())));	

    }

}
