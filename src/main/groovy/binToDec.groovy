class Example {

	static int binaryToInteger(int binString) {
		int asciiValue=0;

		for(int i = 0; i < 8; i++) {
			if(binString % 10) {
				asciiValue += 2**i;
			}

			binString = binString.div(10);
		}

		return asciiValue;
	}

	static boolean isBinary(String binary) {
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

	public static int main(String [] args) {
		boolean goodInput = false;
		boolean isBin = false;
		String input;

		while(!(isBin)) {
		
			while(!(goodInput)) {

				input = System.console().readLine 'Enter a number: ';

				if(!(input.isInteger())) {
					println "A number please";
				} else {
				//	println "good lad"
					goodInput = true;
				}

			}

			if(isBinary(input)) {
				isBin = true;
			} else {
				goodInput = false;
			}

		}


		print "Decimal Value: "
		println binaryToInteger(input as Integer);

		print "ASCII Value: "
		println binaryToInteger(input as Integer) as char

		return 0;
	}
}
