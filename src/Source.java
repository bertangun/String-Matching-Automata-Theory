import java.util.ArrayList;
import java.util.HashMap;

public class Source {
	
	public char[] alphabet = "abcdefghijklmnopqrstuvwxyz0123456789".toCharArray();   //alfabe oluþturuldu. 
	
	public HashMap<Integer, Character> state = new HashMap<Integer, Character>();    //hashmap olusturuldu. state(Integer, character)
	
	public int stringMatcher(char[] line, char[] pattern)    //Tek tek bütün characterleri tarar
    {
        int n = line.length;
        int m = pattern.length;
        int count = 0;                             //(not void)
        boolean flag = false;                     //eþleþmiþ character bulmak için flag 0'a atandý 
		
		for (int s = 0; s < ( n - m ) + 1; s++){  // line.length - pattern.length + 1

			int stringIndex = s;
			flag = true;
			
			//Bütün istisnai ve olumsuz koþullarý flag yardýmýyla false yaptýk.Diger bütün koþullarda flag true olacak ve stringIndex bir arttýrýlacak.
			
			for(int patternIndex = 0; patternIndex < pattern.length; patternIndex++){ //pattern,pattern uzunlugunun bir azý kadar arttýrýldý

				if(line[stringIndex] != pattern[patternIndex]){   //characterler eþleþmedi.flag 0'a atandý     

					flag = false;
				}
	
				if (flag && patternIndex == ( pattern.length ) - 1 ){    //eger pattern'in okunan son characterinde eþleme varsa(flag true olmalý)

					 count++;	// count arttýrýldý 						
				}
				stringIndex++;   
			}
		}
        return count;           //(dikkat et , not void)       
    } 
		
		public void naiveStringMatch(ArrayList<String> arraylist, String pattern){                                 
			for (int line = 0; line < arraylist.size(); line++ ){	 // ilk koþulumuz.line'i baþtan sona oku.
				String lineOfArrayList = arraylist.get(line);	     //template'i(text) bir string array'in içine at.
				
				int count = stringMatcher( lineOfArrayList.toLowerCase().toCharArray(),    // stringMatcher kullanýlarak her bir line tarandý.küçük harf kontrolü yapýldý.
												 pattern.toLowerCase().toCharArray() );   //okunan her bir line count a atandý. 
				if ( count > 0){
					System.out.print("Line " + Integer.toString(line + 1) + ": " + count);	// Line 1,2,3...(toString)
					if (count > 1){
						System.out.println(" occurrences" );                               //1 den fazla pattern bulundu
					}else {
						System.out.println(" occurrence" );                               //1 tane pattern bulundu.
					}
				}
			}
		}
	
	public int faMatcher(char[] line, char[] pattern){
		
		int flag = 0;                          //counter(return edilecek(not void))
    	int m = pattern.length;               
        int n = line.length; 
        int q = 0;
        for (int i = 0; i < n; i++)          //ilk koþulumuz.line'i baþtan sona kadar tara.
        {
        	if (q == m){                    //ikinci koþulumuz.state ile pattern de bir eþleþme söz konusu.(for döngüsü bitene kadar)
            	flag++;			            
            	q = 0;	                   //currentState 0 olmalý,tekrardan kullanýlacak.(pattern arama)
        	}
        	
        	if ( state.get(q) == line[i] ){     //3. koþulumuz.eger line ile hashmapdeki stateler(q0=x,... (hashmap)  --->text) eþleþtiyse
        		q = q + 1;	                   //hashmapdeki currentState characterimize eþit ve currentState 1 arttýrýldý.(for döngüsü bitene kadar)
        	}else{
        		q = 0;		                  // herhangi bir eþleþme bulunamadý.
        	}
            
        }
        return flag;                        // (dikkat et , not void)
 }  
	public void finiteAutomataMatcher(ArrayList<String> arraylist, String pattern){
		for (int line = 0; line < arraylist.size(); line++ ){	 // ilk koþulumuz.line'i baþtan sona oku.
			String lineOfArrayList = arraylist.get(line);	    //template'i(text) bir string array'in içine at. 
			
			int count = faMatcher( lineOfArrayList.toLowerCase().toCharArray(),        // faMatcher kullanýlarak her bir line tarandý.küçük harf kontrolü yapýldý.
											 pattern.toLowerCase().toCharArray() );	   		
		}
	}
	
	//Hashmap için ,suffix currentState ile nextState ve pattern characterlerini(ornegin automata) bul , nextState'i Hashmap'e yazdýr.
	public void suffix(char[] pattern, char patternCharacter, int q, char alphabetCharacter){
		int k = 0;                 
		if (q < pattern.length)	   // ilk koþulumuz.currentState , pattern.length'den küçük olmak zorunda
		{
			if(alphabetCharacter == patternCharacter){	// ikinci koþulumuz.Pattern alphabet ile uyuþtu
				k = q + 1;                             // currentState'i bir arttýr ve nextState'e ata.(ilk patternCharacterimizi bulmuþ olduk ++)
			}
		}
		state.put(k - 1, patternCharacter);	           // nextState - 1(Initial state'miz 0 old için ) --> Integer , patternCharacter --> character
	}                                                 //Hashmapimiz hazýr (put) ve statelerin geçiþleri atandý.
	
	public void computeTransitionFunction(char[] pattern)
    {
		
		for (int q = 0; q < pattern.length; q++){    //ilk koþulumuz.her bir character'i pattern'den oku.(state)
			
			
    		for (int i = 0; i < alphabet.length; i++){    //ikinci koþulumuz.her bir character'i alfabeden oku.
    			
    			if( pattern[q] == alphabet[i] ){                   // eger pattern character  alfabe characterine eþitse
    	    		suffix(pattern, pattern[q], q, alphabet[i]);  //suffix(pattern,p character,state,alfabe character) function'u hazýr.
    	    		break;
    			}
    		}
        }        	
    }
}