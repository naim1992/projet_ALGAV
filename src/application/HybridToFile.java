package application;


import java.io.FileWriter;
import java.io.IOException;
import java.util.Set;

public class HybridToFile {

	
	public static String ToFile(TrieHybrid arbre,FileWriter writer, String n) throws IOException
	{
		if(arbre == null)
		{
			writer.write(n + "[label=" + "\""+ "." + "\"" +  ", color=blue];\n");

			return n;
		}
		
		if(arbre.isFiniched())
			writer.write(n + "[label=" + "\""+ arbre.getCle() + "\"" +  ", color=red];\n");
		
		else
			writer.write(n + "[label=" + "\""+ arbre.getCle() + "\"" +  ", color=blue];\n");
		
		writer.write( n + " -> " + ToFile(arbre.getFilsG(),writer,  n + "G") + 
		" , " + ToFile(arbre.getFilsC(),writer, n + "C" ) + 
		" , " + ToFile(arbre.getFilsD(),writer, n + "D" )+ ";\n");
		
		
		
		return n;
		
	}
	
	public static String ToFile(PatriciaTrie arbre,FileWriter writer, String f) throws IOException
	{
		//StringBuilder builder = new StringBuilder();
		//builder.append(f+"[label=\"");
		String s = f + "[label=\"";
		
		
		
		Set<String> keys = arbre.getArbres().keySet();
		
		for(String k : keys) {
			//builder.append("<" + k.replaceAll(" ", "#") + ">" + k + "|");
			s += "<" + k.replaceAll(" ", "#") + ">" + k + "|";
		}
		
		s = s.substring(0, s.length() - 1);
		//builder.append("\"];");
		s += "\"];";
		writer.write(s + "\n");
		
		for(String k : keys)
		{
			if(arbre.getArbres().get(k) != null)
				writer.write(f + ":" + k.replaceAll(" ", "#")  + "->" + ToFile(arbre.getArbres().get(k), writer, f + k.replaceAll(" ", "#")) + ";\n");
		}
		
		
		return f;
	}
	
}
