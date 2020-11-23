
	import java.util.Random;
	public class Die {
	    int[] sides = {1,2,3,4,5,6};
	    int sideUp;
	    static Random r = new Random();

	    public void rollDie(){
	        this.sideUp = this.sides[r.nextInt(6)];
	    }
}
