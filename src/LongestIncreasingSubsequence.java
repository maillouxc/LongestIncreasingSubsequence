import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class LongestIncreasingSubsequence
{	
	private static final int MAX_ELEMENT_VALUE = 10000;
	
	private static final Random rng = new Random();
	
	public static void main(String args[])
	{	
		int testSequenceLength = getTestSequenceLengthFromUser();
		List<Integer> testSequence = 
				generateRandomIntSequence(testSequenceLength);
		System.out.println("Running...");
		List<Integer> result;
		long startTime = System.currentTimeMillis();
		result = getLongestIncreasingSubsequence(testSequence);
		long elapsedTime = System.currentTimeMillis() - startTime;
		notifyComplete();
		printTestSequenceInfo(testSequence);
		printResultsInfo(result, elapsedTime);
	}
	
	/**
	 * Returns the longest increasing subsequence of the provided sequence.
	 * 
	 * This solution runs in O(N^2), where N is the number of elements in the
	 * provided sequence.
	 * 
	 * @return The longest increasing subsequence of integer values present in
	 * the provided sequence.
	 */
	private static List<Integer> getLongestIncreasingSubsequence(
			List<Integer> sequence
			)
	{
		ArrayList<ArrayList<Integer>> cache = 
				new ArrayList<ArrayList<Integer>>();
		// Populate the first element first to avoid a NullPointerException
		cache.add(new ArrayList<Integer>());
		
		for(int i = 0; i < sequence.size(); i++)
		{
			// Add element if greater than tail of all existing subsequences
			for(int j = 0; j < i; j++)
			{
				int tail = cache.get(j).size() - 1;
				if(cache.get(j).get(tail) < sequence.get(i) 
						&& (cache.get(i).size() < (cache.get(j).size() + 1) ))
				{
					cache.set(i, cache.get(j));
				}
			}
			cache.add(new ArrayList<Integer>());
			cache.get(i).add(sequence.get(i));
		}
	
		// Find the longest subsequence stored in the cache and return it
		List<Integer> longestIncreasingSubsequence = new ArrayList<Integer>();
		for(List<Integer> subsequence : cache)
		{
			if(subsequence.size() > longestIncreasingSubsequence.size())
			{
				longestIncreasingSubsequence = subsequence;
			}
		}
		return longestIncreasingSubsequence;
	}
	
	/**
	 * Generates a sequence of the provided length, populated with random 
	 * Integer values.
	 * 
	 * @param length The length of the sequence to generate.
	 * @return A random List<Integer> sequence of the desired length.
	 */
	private static List<Integer> generateRandomIntSequence(int length)
	{
		List<Integer> sequence = new ArrayList<Integer>();
		for(int i = 0; i < length; i++)
		{
			int randomNumber = rng.nextInt(MAX_ELEMENT_VALUE);
			sequence.add(randomNumber);
		}
		return sequence;
	}
	
	private static int getTestSequenceLengthFromUser()
	{
		System.out.println("Enter test sequence length:");
		System.out.println("Things tend to get O(scary) at values above 2^16");
		Scanner inputScanner = new Scanner(System.in);
		int testSequenceLength = inputScanner.nextInt();
		inputScanner.close();
		return testSequenceLength;
	}
	
	private static void notifyComplete()
	{
		System.out.println("Done! \n");
		// Pause for a second so the user has time to read the message
		try
		{
			Thread.sleep(1000); // milliseconds
		}
		catch (InterruptedException e)
		{
			// Don't need to do anything here
		}
	}
	
	private static void printTestSequenceInfo(List<Integer> testSequence)
	{
		System.out.println("Test sequence used: ");
		System.out.println(testSequence.toString());
		System.out.println("Length: " + testSequence.size() + "\n");
	}
	
	private static void printResultsInfo(List<Integer> result, long elapsedTime)
	{
		System.out.println("Longest increasing subsequence found: ");
		System.out.println(result.toString());
		System.out.println("Length: " + result.size());
		System.out.println("Elapsed time: ~" + elapsedTime + " ms" 
				+ " (" + ((double)elapsedTime / 1000) + " s)");
	}
}