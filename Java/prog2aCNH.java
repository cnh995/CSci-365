import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.io.*;

public class prog2aCNH
{
	final static String[] ILLEGAL_IDS = {"input", "print"};

	static enum Token
	{
		assign('='),
		add('+'),
		sub('-'),
		mult('*'),
		div('/'),
		mod('%'),
		left_paren('('),
		right_paren(')'),
		less('<'),
		great('>'),
		hash('#'),;

		private final String lexeme;
		private final char matcher;

		Token(char matcher)
		{
		    this.matcher = matcher;
		    lexeme = String.valueOf(matcher);
		}

		Token(char matcher, String lexeme)
		{
		    this.matcher = matcher;
		    this.lexeme = lexeme;
		}

		public char matcher() {
		    return matcher;
		}

		public String lexeme() {
		    return lexeme;
		}

		public String toLexEntry()
		{
			if(toString().equals("add") || toString().equals("sub"))
			{
				return "<add_op>, " + lexeme;
			}
			else if(toString().equals("mult") || toString().equals("div") || toString().equals("mod"))
			{
				return "<mult_op>, " + lexeme;
			}
			else if(toString().equals("less") || toString().equals("great"))
			{
				return "<rel_op>, " + lexeme;
			}
			return "<" + toString() + ">" + ", " + lexeme;
		}
    	}

	public static void main(String[] args)
	{
		if (args.length > 0)
		{
			try
			{
				File inputFile = new File(args[0]);
				Scanner input = new Scanner(inputFile);
				while(input.hasNext())
				{
					iterativePrintln(lexify(input.nextLine()));
				}
			}
			catch (FileNotFoundException e)
			{
				System.err.println(e.getMessage());
				System.err.println("Cannot find input file, exiting program.");
			}
		}
		else
		{
			System.out.println("Enter a file name as a command line argument");
		}
	}

	public static List<String> lexify(String testCase)
	{
		String sanitized = testCase.replaceAll("\\s+", "");
		List<String> lexEntryList = new ArrayList<>();      
		StringBuilder identifierBuilder = new StringBuilder();
		StringBuilder numberBuilder = new StringBuilder();

		int balancedParantheses = 0;
		boolean buildingIdentifier = false;
		boolean buildingNumber = false;
		boolean singleLineCommenting = false;

		outer:
		for (int i = 0; i < sanitized.length(); i++)
		{
		   	char current = sanitized.charAt(i);
		  	char next = ' ';
		   	if (i != sanitized.length() - 1)
			{
		        next = sanitized.charAt(i + 1);
		    	}

		    	if (singleLineCommenting)
			{
		        	if (current == '\n')
				{
		            		singleLineCommenting = false;
		        	}
		        	continue;
		    	}

            		if (Character.isLetter(current))
			{
                		buildingIdentifier = true;
                		identifierBuilder.append(current);
                		continue;
            		}

            		if (isNumber(current))
			{
                		if (buildingIdentifier)
				{
                    			identifierBuilder.append(current);
                    			continue;
                		}
				else
				{
				    	buildingNumber = true;
				    	numberBuilder.append(current);
				    	continue;
                		}
            		}

            		for (Token token : Token.values())
			{
				if (current == token.matcher())
				{
				    	if (token == Token.assign)
					{
				        	if (next == '=')
						{
				            		i++;
				        	}
						else
						{
					    		i++;
				        	}
				    	}
					else if (token == Token.left_paren)
					{
				        	balancedParantheses++;
				    	}
					else if (token == Token.right_paren)
					{
				        	balancedParantheses--;
				    	}
					else if (token == Token.hash)
					{
						i++;
						singleLineCommenting = true;
						continue outer;
				    	}

                    			if (buildingNumber)
					{
						lexEntryList.add("<number>, " + numberBuilder.toString());
						numberBuilder.setLength(0);
						buildingNumber = false;
					}

				    	if (buildingIdentifier)
					{
						String id = identifierBuilder.toString();

						if (!isLegalId(id))
						{
							if(id.equals("input"))
							{
						    		System.out.println("<input>, " + id);
							}
							else if(id.equals("print"))
							{
								System.out.println("<print>, " + id);
							}
						}

				        	lexEntryList.add("<id>, " + id);
				        	identifierBuilder.setLength(0);
				        	buildingIdentifier = false;
				    	}

                    			lexEntryList.add(token.toLexEntry());
                		}
            		}
        	}

        	if (buildingNumber)
		{
            		lexEntryList.add("<number>, " + numberBuilder.toString());
        	}
		else if (buildingIdentifier)
		{
			String id = identifierBuilder.toString();

			if (!isLegalId(id))
			{
				haltPrint("Illegal ID: " + id);
			}
			lexEntryList.add("<id>, " + id);
        	}

		if (balancedParantheses != 0)
		{
		    haltPrint("Unbalanced Parantheses");
		}

		return lexEntryList;
	}

    	public static void iterativePrintln(List<String> list)
	{
        	for (String str : list)
		{
            		System.out.println(str);
        	}
    	}

    	public static void haltPrint(String str)
	{
		System.out.println(str);
		System.exit(0);
    	}

    	public static String toTestString(String[] args)
	{
        	return String.join("", args);
    	}

    	public static boolean isLegalId(String id)
	{
	        for (String illegalId : ILLEGAL_IDS)
		{
            		if (illegalId.equals(id))
			{
                		return false;
            		}
        	}

        	return true;
    	}

    	public static boolean isNumber(char c)
	{
        	try
		{
            		Double.parseDouble(String.valueOf(c));
        	}
		catch (NumberFormatException nfe)
		{
            		return false;
        	}

        	return true;
    	}
}
