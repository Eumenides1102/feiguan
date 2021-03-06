package nosqlalgorithm;



import java.util.*;
import java.util.Arrays;

public class Rank 
{	
	public static String[] reRank2(String[] input) {
		if(input == null || input.length == 0)
		{
			return new String[0];
		}
		
		int i = 0;
		int left = 0;
		int right = input.length - 1;
		while(i < right)
		{
			if(input[i].equals("r"))
			{
				String t = input[i];
				input[i] = input[left];
				input[left] = t;
				i++;
				left++;
			}
			else if(input[i].equals("g"))
			{
				i++;
			}
			else if(input[i].equals("b"))
			{
				String t1 = input[i];
				input[i] = input[right];
				input[right] = t1;
				right--;
			}
		}
		
		return input;
	}
	
	public static void main (String[] args)
	{
		String[] input = new String[] {"r","r","b","g","b","r","g"};
		
		System.out.print("The result of reRank1() is: ");
		System.out.print(reRank1(input));
		System.out.print("\nThe result of reRank2() is: ");
		Arrays.asList(reRank2(input)).forEach(b->System.out.prin(b));
	}

}
