package test.phr;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class PartialReadArray
{
	
	/**
	 * 
	 * @param args
	 */
	private static int currPos = -1;
	
	public static void main(String[] args)
	{
		ArrayList<String> rxList = new ArrayList<String>();
		rxList.add("1");
		rxList.add("2");
		rxList.add("3");
		rxList.add("4");
		rxList.add("5");
		rxList.add("6");
		rxList.add("7");
		rxList.add("8");
		rxList.add("9");
		rxList.add("10");
		
//		currPos = 3;		
//		List<String> _subList = readPrev(rxList, currPos);
		List<String> _subList = rxList.subList(9,10);
		
		for (Iterator iterator = _subList.iterator(); iterator.hasNext();)
		{
			String _stringId = (String) iterator.next();
			System.out.println(" --------> _stringId = " + _stringId);			
		}		
	}
	
	/**
	 * 
	 * @param rxList
	 * @param currPos
	 * @return
	 */
	private static List<String> readNext(ArrayList<String> rxList, int currPos)
	{
		
		int _endofList = -1;
		int _startofList = -1;
		_startofList = currPos + 3;
		_endofList = _startofList + 3;
		currPos = _startofList;
		
		if (_endofList > rxList.size())
		{
			_endofList = rxList.size();
		}
		
		return rxList.subList(_startofList, _endofList);
	}
	
	/**
	 * 
	 * @param rxList
	 * @param currPos
	 * @return
	 */
	private static List<String> readPrev(ArrayList<String> rxList, int currPos)
	{
		int _endofList = -1;
		int _startofList = -1;
		_startofList = currPos - 3;
		
		if (_startofList < 0)
		{
			_startofList = 0;
		}
		
		_endofList = _startofList + 3;
		currPos = _startofList;
		
		if (_endofList > rxList.size())
		{
			_endofList = rxList.size();
		}
		
		return rxList.subList(_startofList, _endofList);
	}
	
}
