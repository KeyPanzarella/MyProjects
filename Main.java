package OSPack;

public class Main {

    private final static int midpoint = 30;

    public static void main(String[] args) {

        for (int i = 1; i <= 3; i++){


            if(i == 1){                                                             //FIRST ROW OF STARS
                for(int c = 0; c < midpoint; c++) {                 //this spaces the single star in the middle of the row
                    System.out.print(" ");
                }
                System.out.print("*\n\n");
            }


            if(i == 2){                                                             //SECOND ROW OF STARS
                for (int c = 0; c < ((60/3)-1); c++){               //this is spacing the two stars approx 1/3 of the way into the row
                    System.out.print(" ");
                }
                System.out.print("**");

                for (int c = 0; c < ((60/3)-2); c++){                //this is spacing the three stars approx 2/3 of the way into the row (1/3 + 1/3 = 2/3)
                    System.out.print(" ");
                }
                System.out.print("***\n\n");
            }


            if(i == 3){                                                             //THIRD ROW OF STARS
                System.out.print("****");
                for (int c = 0; c < ((midpoint-4)-2); c++){         //this spaces the five stars approx halfway into the row (the subtraction accounts for already having the
                    System.out.print(" ");                          //four stars taking up four spaces and having the center of the five stars line up with the exact middle of the row
                }
                System.out.print("*****");
                for (int c = 0; c < ((((60-4)-24)-5)-6); c++){       //24 = (midpoint-4-2) the rest of the subtraction accounts for stars
                    System.out.print(" ");
                }
                System.out.print("******");
            }
        }
    }
}
