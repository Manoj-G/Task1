import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.Scanner;
import java.util.TreeMap;

import org.json.JSONException;
import org.json.JSONObject;

public class Main {

	public static void main(String args[]) throws IOException, JSONException {

		/* Getting file from user */

		File inFile = null;
		if (0 < args.length) {
			inFile = new File(args[0]);
		} else {
			System.err.println("Invalid arguments count:" + args.length);
			System.exit(0);
		}
		Scanner sc = new Scanner(new FileReader(inFile));

		/* Reading the file line by line */

		String line = "";
		ArrayList<String> Users = new ArrayList<String>();
		ArrayList<Double> totalStockPricePerUser = new ArrayList<Double>();
		
		HashMap<Integer,String> users = new HashMap<Integer,String>();
		Integer userCount = 0;
		
		while (sc.hasNextLine()) {
			line = sc.nextLine();
			Users.add(line);
			users.put(++userCount, line);

			/* Removing Additional/Special Characters */

			String symbolsAndPrices[] = removingAdditionalCharacters(line);

			/* Mapping the Stock prices to its Symbols  */
            
			TreeMap<String, Double> map = mappingStocksToItsPrices(symbolsAndPrices);

			/* Get Total Stock Price Of User  */
			double totalSumOfStocksPerUser = getSumOfStocksPerUser(map);
			
			totalStockPricePerUser.add(totalSumOfStocksPerUser);
		}
		sc.close();
		
		/*  Sorting the Users   */
		ArrayList<String> sortedStockPricesOfUsers = sortingPriceOfStocksForAllUser(totalStockPricePerUser, Users);
		
		/*   Printing the data */
		System.out.println("\nAfter Sorting:");
		
		for (String user : sortedStockPricesOfUsers) {
			System.out.println(user);
		}
	}

	public static Double calculateStockPriceOfSymbol(Double symbolValue, Double fetchedSymbolValue) {
		Double calculatedValue;
		calculatedValue = symbolValue * fetchedSymbolValue;
		return calculatedValue;
	}

	public static ArrayList<String> sortingPriceOfStocksForAllUser(ArrayList<Double> totalSumOfSymbolsPerUser,
			ArrayList<String> NumberOfUsers) {

		double temp;
		String SwapStrings = "";
		
		for (int i = 0; i < totalSumOfSymbolsPerUser.size(); i++) {
			for (int j = i + 1; j < totalSumOfSymbolsPerUser.size(); j++) {
				if (totalSumOfSymbolsPerUser.get(i) < totalSumOfSymbolsPerUser.get(j)) {
					temp = totalSumOfSymbolsPerUser.get(i);
					totalSumOfSymbolsPerUser.set(i, totalSumOfSymbolsPerUser.get(j));
					totalSumOfSymbolsPerUser.set(j, temp);

					SwapStrings = NumberOfUsers.get(i);
					NumberOfUsers.set(i, NumberOfUsers.get(j));
					NumberOfUsers.set(j, SwapStrings);

				}
			}
		}
		return NumberOfUsers;
	}
	
	public static String[] removingAdditionalCharacters(String line) {
		String symbolsAndPrices[] = line.split("-|,");

		for (int i = 0; i < symbolsAndPrices.length; i++) {
			String str = symbolsAndPrices[i];
			symbolsAndPrices[i] = str.trim(); //str.replaceAll("(^ )|( $)", "");
		}
		return symbolsAndPrices;
	}
	
	public static TreeMap<String,Double> mappingStocksToItsPrices(String[] symbolsAndPrices){
		
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
		return map;
	}
	
	public static double getSumOfStocksPerUser(TreeMap<String,Double> map) throws JSONException, IOException {
		
		API api = new API();

		double totalSumOfStocksPerUser = 0;
		for (String symbol : map.keySet()) {
			
			System.out.print(symbol + "=");
			
			Double symbolValue = map.get(symbol);
			double fetchedSymbolValue = api.GetCurrentStockPriceOfSymbol(symbol);
			
			System.out.print(fetchedSymbolValue + ", ");
			
			totalSumOfStocksPerUser += calculateStockPriceOfSymbol(symbolValue, fetchedSymbolValue);
		}
		System.out.print("; Total: " + totalSumOfStocksPerUser);
		System.out.println();
		return totalSumOfStocksPerUser;
	}
}
