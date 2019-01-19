package com.hp.pwp.capstone

class toBin {

	 int binaryToInteger(int binString) {
		int asciiValue=0;

		for(int i = 0; i < 8; i++) {
			if(binString % 10) {
				asciiValue += 2**i;
			}

			binString = binString.div(10);
		}

		return asciiValue;
	}

	 boolean isBinary(String binary) {
		//checks if there is enog=ugh bits
		if(binary.length() % 8 != 0) {
			println "You need to enter the binary number in strings of 8's"
			return false;
		}

		int bin = binary as Integer;

		//checks to see if all items in string are 1's and 0's
		while( bin > 0) { 
			int ones = bin % 10;
			
			if(ones != 0 && ones != 1) {
				println "String contained values besides 1's and 0's"
				return false;
			}

			bin = bin.div(10);
		}

		return true;
	}


	public static void main(String [] args) {

        while(true) {

            def converter = new toBin()

            boolean goodInput = true
            int binString
            
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in)) 
            print "Enter a binary string: " 
            String input = br.readLine() 

            goodInput = converter.isBinary(input)
            binString = input as Integer
            if(goodInput) {
                println "$input as an integer: " + converter.binaryToInteger(binString)
            }
            


        }
         
        
    }
}
