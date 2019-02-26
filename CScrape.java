import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.Scanner;


public class CScrape {
	
	static Scanner in = new Scanner(System.in);
	static String file;
	
	
	
	public static void main(String[] args) 
	{	
			System.out.println("Enter the name of your .java file.");
			
			//input for the String file which takes the directory input to search the file
			file = in.nextLine();
			directory();
	}
	
	
	public static void directory()
	{
		try
		{
			//use FileInputStream to open file designated in the ()
			FileInputStream test = new FileInputStream(file);
			//wrapping FileInputStream in an object of class DataInputStream
			DataInputStream input = new DataInputStream(test);
			BufferedReader reader = new BufferedReader(new InputStreamReader(input));
			//fileName String object created to split "fileName.java" from the directory
			String fileName = file.substring(file.lastIndexOf("\\") + 1);
			//Split "fileName.java" even further to just "fileName"
			PrintWriter write = new PrintWriter(fileName.split(".java" , 2)[0] + ".txt");
			String comments = reader.readLine();
			//states to check whether in multi-line comment or single line comment
			boolean inMLComment = false;
			boolean inSLComment = false;
			//build a string to deposit the read file into
			StringBuilder sb = new StringBuilder();
			//counter to count lines being read
			int counter = 1; 
			
			//loop to add the lines being read into sb
			while (comments != null)
			{
				sb.append(comments.trim());
				sb.append("\n");
				comments = reader.readLine();
			}
			
			//put  my name in the text document
			write.println("Comment Scraper - Ivan Duarte" +"\n");
			
			for (int i = 0; i < sb.length() - 1; i++)
			{	
				
				char a = sb.charAt(i);
				//counter to count the number of new lines to append into the text document
				if (a == '\n')
				{
					counter++;
				}
				
				//checks whether I' m in a ML/SL comment
				if (a == '/')
				{
					char b = sb.charAt(i + 1);
					if (b == '*')
					{
						write.print((counter) + ":");
						inMLComment = true;
					}
					else if(b == '/')
					{
						inSLComment = true;
						if(inSLComment == true & inMLComment == false)
						{
							write.print((counter) + ":");
						}
					}
				
				}
				if (inMLComment == true)
				{
					if (sb.charAt(i) == '*' && sb.charAt(i+1) == '/')
					{
						write.print("*/");
						write.println("");
						
						inMLComment = false;
						inSLComment = false;
					}
					else
					{
						write.print(sb.charAt(i));
						if(sb.charAt(i) == '\n')
						{
							write.println();
							write.print((counter) + ":");
						}
						

					}
				}
				else if (inSLComment == true)
				{
					
					if (sb.charAt(i) == '\n')
					{
						write.println();
						
						inSLComment = false;
						inMLComment = false;
							
					}
					else
					{
						write.print(sb.charAt(i));
					}
				}
			}
			write.close();
			input.close();
			in.close();
		}
		
			catch (Exception e) 
			{
				System.out.println("Error: " + e.getMessage());
				System.out.println("Make sure your file is in the correct directory or type in the absolute path of your file.");
				System.out.println("If using a relative path, start your input with a '.'. e.g. .\\src\\fileName.java");
				boolean doAgain = true;
				if(doAgain = true)
				{
					restart();
				}
			}
		
	}
		
		public static void restart()
		{
			System.out.println("Enter the name of your .java file.");
			
				file = in.nextLine();
				directory();
		}
}
