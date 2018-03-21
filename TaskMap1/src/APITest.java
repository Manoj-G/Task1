
import static org.junit.Assert.*;

import java.util.TreeMap;

import org.junit.Test;

public class APITest {

	//Main mainClass = new Main();
	API apiClass = new API();
	
	@Test
	public void test() {
		String line="INFY - 100, GOOG - 50, MS - 10";
		String line1="  INFY   -   100   ,   GOOG - 50  ,   MS   -     10";
		String array[] = {"INFY","100","GOOG","50","MS","10"};
		String emptyLine = "               ";
		String emptyArray[]= {""};
		
		assertArrayEquals(array, Main.removingAdditionalCharacters(line));
	    assertArrayEquals(array, Main.removingAdditionalCharacters(line1));
	    assertArrayEquals(emptyArray, Main.removingAdditionalCharacters(emptyLine) );
	}

		
	@Test
	public void test1() {
	
		String emptyArray[] = {};
		TreeMap<String,Double> map1 = new TreeMap<String,Double>();

		/*for (int i = 0; i < emptyArray.length; i += 2) 
		{
			if (map1.containsKey(emptyArray[i])) 
			{
				Double value = map1.get(emptyArray[i]);
				map1.put(emptyArray[i], Double.parseDouble(emptyArray[i + 1]) + value);
			} 
			else 
			{
				map1.put(emptyArray[i], Double.parseDouble(emptyArray[i + 1]));
			}
		}*/
		
		String symbolsAndPrices[] = {"INFY","100","GOOG","50","MS","10"};
		TreeMap<String,Double> map = new TreeMap<String,Double>();
		for (int i = 0; i < symbolsAndPrices.length; i += 2) 
		{
			if (map.containsKey(symbolsAndPrices[i])) 
			{
				Double value = map.get(symbolsAndPrices[i]);
				map.put(symbolsAndPrices[i], Double.parseDouble(symbolsAndPrices[i + 1]) + value);
			} 
			else 
			{
				map.put(symbolsAndPrices[i], Double.parseDouble(symbolsAndPrices[i + 1]));
			}
		}
		assertEquals(map, Main.mappingStocksToItsPrices(symbolsAndPrices) );
	    
		assertEquals(map1,Main.mappingStocksToItsPrices(emptyArray));
	}
}
