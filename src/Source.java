import java.util.ArrayList;
import java.util.HashMap;

public class Source {
	
	public char[] alphabet = "abcdefghijklmnopqrstuvwxyz0123456789".toCharArray();   //alfabe olu�turuldu. 
	
	public HashMap<Integer, Character> state = new HashMap<Integer, Character>();    //hashmap olusturuldu. state(Integer, character)
	
	public int stringMatcher(char[] line, char[] pattern)    //Tek tek b�t�n characterleri tarar
    {
        int n = line.length;
        int m = pattern.length;
        int count = 0;                             //(not void)
        boolean flag = false;                     //e�le�mi� character bulmak i�in flag 0'a atand� 
		
		for (int s = 0; s < ( n - m ) + 1; s++){  // line.length - pattern.length + 1

			int stringIndex = s;
			flag = true;
			
			//B�t�n istisnai ve olumsuz ko�ullar� flag yard�m�yla false yapt�k.Diger b�t�n ko�ullarda flag true olacak ve stringIndex bir artt�r�lacak.
			
			for(int patternIndex = 0; patternIndex < pattern.length; patternIndex++){ //pattern,pattern uzunlugunun bir az� kadar artt�r�ld�

				if(line[stringIndex] != pattern[patternIndex]){   //characterler e�le�medi.flag 0'a atand�     

					flag = false;
				}
	
				if (flag && patternIndex == ( pattern.length ) - 1 ){    //eger pattern'in okunan son characterinde e�leme varsa(flag true olmal�)

					 count++;	// count artt�r�ld� 						
				}
				stringIndex++;   
			}
		}
        return count;           //(dikkat et , not void)       
    } 
		
		public void naiveStringMatch(ArrayList<String> arraylist, String pattern){                                 
			for (int line = 0; line < arraylist.size(); line++ ){	 // ilk ko�ulumuz.line'i ba�tan sona oku.
				String lineOfArrayList = arraylist.get(line);	     //template'i(text) bir string array'in i�ine at.
				
				int count = stringMatcher( lineOfArrayList.toLowerCase().toCharArray(),    // stringMatcher kullan�larak her bir line tarand�.k���k harf kontrol� yap�ld�.
												 pattern.toLowerCase().toCharArray() );   //okunan her bir line count a atand�. 
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
        for (int i = 0; i < n; i++)          //ilk ko�ulumuz.line'i ba�tan sona kadar tara.
        {
        	if (q == m){                    //ikinci ko�ulumuz.state ile pattern de bir e�le�me s�z konusu.(for d�ng�s� bitene kadar)
            	flag++;			            
            	q = 0;	                   //currentState 0 olmal�,tekrardan kullan�lacak.(pattern arama)
        	}
        	
        	if ( state.get(q) == line[i] ){     //3. ko�ulumuz.eger line ile hashmapdeki stateler(q0=x,... (hashmap)  --->text) e�le�tiyse
        		q = q + 1;	                   //hashmapdeki currentState characterimize e�it ve currentState 1 artt�r�ld�.(for d�ng�s� bitene kadar)
        	}else{
        		q = 0;		                  // herhangi bir e�le�me bulunamad�.
        	}
            
        }
        return flag;                        // (dikkat et , not void)
 }  
	public void finiteAutomataMatcher(ArrayList<String> arraylist, String pattern){
		for (int line = 0; line < arraylist.size(); line++ ){	 // ilk ko�ulumuz.line'i ba�tan sona oku.
			String lineOfArrayList = arraylist.get(line);	    //template'i(text) bir string array'in i�ine at. 
			
			int count = faMatcher( lineOfArrayList.toLowerCase().toCharArray(),        // faMatcher kullan�larak her bir line tarand�.k���k harf kontrol� yap�ld�.
											 pattern.toLowerCase().toCharArray() );	   		
		}
	}
	
	//Hashmap i�in ,suffix currentState ile nextState ve pattern characterlerini(ornegin automata) bul , nextState'i Hashmap'e yazd�r.
	public void suffix(char[] pattern, char patternCharacter, int q, char alphabetCharacter){
		int k = 0;                 
		if (q < pattern.length)	   // ilk ko�ulumuz.currentState , pattern.length'den k���k olmak zorunda
		{
			if(alphabetCharacter == patternCharacter){	// ikinci ko�ulumuz.Pattern alphabet ile uyu�tu
				k = q + 1;                             // currentState'i bir artt�r ve nextState'e ata.(ilk patternCharacterimizi bulmu� olduk ++)
			}
		}
		state.put(k - 1, patternCharacter);	           // nextState - 1(Initial state'miz 0 old i�in ) --> Integer , patternCharacter --> character
	}                                                 //Hashmapimiz haz�r (put) ve statelerin ge�i�leri atand�.
	
	public void computeTransitionFunction(char[] pattern)
    {
		
		for (int q = 0; q < pattern.length; q++){    //ilk ko�ulumuz.her bir character'i pattern'den oku.(state)
			
			
    		for (int i = 0; i < alphabet.length; i++){    //ikinci ko�ulumuz.her bir character'i alfabeden oku.
    			
    			if( pattern[q] == alphabet[i] ){                   // eger pattern character  alfabe characterine e�itse
    	    		suffix(pattern, pattern[q], q, alphabet[i]);  //suffix(pattern,p character,state,alfabe character) function'u haz�r.
    	    		break;
    			}
    		}
        }        	
    }
}