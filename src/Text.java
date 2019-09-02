import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class Text {

	private File file;
	
	public Text(String fileName) throws IOException{
		this.file = new File( new File(".").getCanonicalPath() + File.separator + fileName); //text içinde hatalý bir character(s) girildiyse canonicalPath otomatik düzeltir. 
	}
	
	public ArrayList<String> getArrayList() throws IOException {
		
		ArrayList<String> arrayList = new ArrayList<String>();   //line lar text file dan okunacak
		FileInputStream fileInputStream = new FileInputStream(this.file);
		BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(fileInputStream));
		
		String line = null;             //line null olmak zorunda,degilse controller ata(text(addLine))
		while ((line = bufferedReader.readLine()) != null) {
			arrayList.add(line);
		}
	 
		bufferedReader.close();
		return arrayList;       //(dikkat et,not void)
	}
	
}
