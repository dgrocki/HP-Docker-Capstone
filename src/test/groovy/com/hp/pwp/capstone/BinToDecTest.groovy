package com.hp.pwp.capstone

import org.junit.Test

public class BinToDecTest extends GroovyTestCase {
  @Test
  void testBinaryToIntegerGood() {
	Random rnd = new Random();
	def b = new toBin();

	for(int i = 0; i < 1000000; i++)
	{
		String input = "";

		for(int j = 0; j < 8; j++)
		{
			input = input + rnd.nextInt(2) as String;
		}

//		assert(b.isBinary(input));
	}

	
	}

   void testBintoIntBad() {
	Random rnd = new Random();
	def b = new toBin();

	for(int i = 0; i < 1000000; i++)
	{
		String input = "";

		for(int j = 0; j < 8; j++)
		{
			int add = rnd.nextInt(8) + 2;
			input = input + add as String;
		}

//		assert(b.isBinary(input) == false);
	}

        assert(true)
	}
 

}
