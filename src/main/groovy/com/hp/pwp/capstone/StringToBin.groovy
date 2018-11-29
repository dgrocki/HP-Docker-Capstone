package com.hp.pwp.capstone

class Byte {
	def bits = new int[8];
	boolean isSet = false;


	void setBits(int [] b) {
		if(b.length != 8) {
			println "Not enough bits\nChanges not saved";
		
			return;
		}

		for(int i = 0; i < 8; i++) {
			if(b[i] != 0 && b[i] != 1) {
				println "Bit string does not contain correct values.\nChanges not saved."

				isSet = false;
				return;
			} else {
				bits[i] = b[i]
			}
		}			

		isSet = true;
		
	}

	void setBits(int ascii) {
		if(ascii > 255 || ascii < 0) {
			println "Provided value is less than or greater than the value a single bit can contain."

			return;
		} else {
			for(int i = 7; i >= 0; i--) {
				println "power: $i | ascii: $ascii"
				if(2**i < ascii) {
					bits[7] = 1;
					ascii -= 2**i;
				} 
			}

			isSet = true;
		}
	}

	void printBitString() {
		if(isSet) {
			print bits;
		} else {
			println "This bit string is not set.";
		}
	}

	static int main(String [] args) {
			
		def input;
		boolean validInput = false;

		while(!(validInput)) {
			input = System.console().readLine 'Enter a number: ';
			
			if(!(input.isInteger())) {
				println "Please enter an integer";
			}

			int num = input as Integer;

			if(num > 255 || num < 0) { 
				println "The number is more or less than a bit."	
			} else {
				validInput = true;
			}
		}

		Byte b = new Byte();

		b.setBits(input as Integer);
		b.printBitString();

		return 0;
	}

}
