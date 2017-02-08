import java.util.HashMap;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.Arrays;
import java.io.File;
import java.io.FileNotFoundException;

public class Spelling{
	public static void main(String[] args){

		// load elements csv into map
		HashMap<String, String> map;
		try{
			map = loadElems("elements.csv");
		} catch(FileNotFoundException e){
			System.out.println("Fatal: elements file not found.");
			return;
		}

		// load words into array list
		ArrayList<String> words;
		try{
			words = loadWords("words.txt");
		} catch(FileNotFoundException e){
			System.out.println("Fatal: words file not found.");
			return;
		}


		for(String word : words){
			String camel = "";
			ArrayList<String> elements = new ArrayList<>();

			for(int i=0; i<word.length(); i++){

				// try to find a 2-letter symbol first
				if(i+2 < word.length() && map.get(word.substring(i, i+2)) != null){
					String symbol = word.substring(i, i+2);
					String elem = map.get(symbol);
					symbol = symbol.substring(0, 1).toUpperCase() + symbol.substring(1);
					camel += symbol;
					elements.add(elem);
					i++;
				} else {
					String symbol = word.substring(i, i+1);
					String elem = map.get(symbol);
					symbol = symbol.toUpperCase();
					camel += symbol;
					elements.add(elem);
				}
			}
			
			print(camel, elements);

		}


	}// end main method

	private static void print(String word, ArrayList<String> list){
		System.out.print(word + " ");
		String elems = Arrays.toString(list.toArray());
		System.out.println(elems.replace("[", "(").replace("]", ")"));
	}

	private static HashMap<String, String> loadElems(String filename) throws FileNotFoundException{
		Scanner s = new Scanner(new File(filename));
		HashMap<String, String> map = new HashMap<String, String>();
		while (s.hasNextLine()){
			String[] words = s.nextLine().split(",");
			map.put(words[1].trim().toLowerCase(), words[0].trim().toLowerCase());
		}
		s.close();
		//System.out.format("Loaded %s lines into map.\n", map.size());
		return map;
	}

	private static ArrayList<String> loadWords(String filename) throws FileNotFoundException{
		Scanner s = new Scanner(new File(filename));
		ArrayList<String> words = new ArrayList<>();
		while(s.hasNextLine()){
			words.add(s.nextLine().trim());
		}
		s.close();
		//System.out.format("Loaded %s lines into word list.\n", words.size());
		return words;
	}
}