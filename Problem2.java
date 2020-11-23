
public class Problem2 {
	public static void main(String[] args) {
		int sum = 0;
		int fib1 = 1;
		int fib2 = 1;
		int fibTemp;
		while(fib2 < 4000000) {
			System.out.print("\n"+fib2);
			if(fib2 % 2 == 0) {
				sum+=fib2;
				System.out.print("\tthis is even");
			}
			fibTemp = fib2;
			fib2 += fib1;
			fib1 = fibTemp;
		}
		System.out.println("\n\nSum of even values:\t"+sum);
	}
}
