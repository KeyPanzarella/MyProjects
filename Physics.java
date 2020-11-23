
import java.util.Scanner;
/**
 *
 * @author kijan_000
 */
public class Physics {

    private static Scanner k = new Scanner(System.in);
    private static final double g = 9.808;
    private static double F, KE, KEr, PE, ThE, W, p, L, N, f, x;
    private static final String[] list = {"Force", 
                                    "Kinetic Energy", 
                                    "Rotational Kinetic Energy", 
                                    "Potential Energy", 
                                    "Thermal Energy", 
                                    "Work", 
                                    "Momentum", 
                                    "Angular Momentum", 
                                    "Normal Force", 
                                    "Friction Force(Kinetic)", 
                                    "Distance"};//torque is next bruh  (and angular mass)
    private static double mass, position, velocity, acceleration, angularMass, 
                            radius, height, time, Xo, Vo, angle, springk, 
                            springx, u;
    private static double angularVelocity, angularAcceleration;
    private static double Newtons, Joules, Meters, Watts, KgMpersec;
    //private static int velocity;
   
    
    protected static Physics Phy = new Physics();
    
    public static void main(String[] args) {

        //*
        do{
            //String FindThis = FindWhat();
            int c = ohThis(FindWhat());
            withWhat(c);
        
        
            switch(c){
                case 0: Newtons = Force(mass, acceleration);
                        found(Newtons);
                        System.out.print("in Newtons.\n");
                    break;
                case 1: Joules = KineticEnergy(mass, velocity);
                        found(Joules);
                        System.out.print("in Joules.\n");
                    break;
                case 2:     Joules = RotationalKineticEnergy(angularMass, angularVelocity);
                        found(Joules);
                        System.out.print("in Joules.\n");
                    break;
                case 3: Joules = PotentialEnergy(mass, height);
                        found(Joules);
                        System.out.print("in Joules.\n");
                    break;
                case 4: Joules = ThermalEnergy(f, x);
                        found(Joules);
                        System.out.print("in Joules.\n");
                    break;
                case 5: Joules = Work(F, x);
                        found(Joules);
                        System.out.print("in Joules.\n");
                    break;
                case 6: KgMpersec = Momentum(mass, velocity);
                        found(KgMpersec);
                        System.out.print("in Kg*m/s.\n");
                    break;
                case 7: KgMpersec = AngularMomentum(angularMass, angularVelocity);
                        found(KgMpersec);
                        System.out.print("in Kg*m/s^2.\n");
                    break;
                case 8: Newtons = NormalForce(angle, F);
                        found(Newtons);
                        System.out.print("in Newtons.\n");
                    break;
                case 9: Newtons = FrictionForce(N, x);
                        found(Newtons);
                        System.out.print("in Newtons.\n");
                    break;
                case 10:    Meters = Distance(Xo, Vo, time, acceleration);
                            found(Meters);
                            System.out.print("in Meters.\n");
                    break;
            }
            
        }while(UseAgainCheck());
        
        //*/
        //System.out.println(FindThis);
    }
    
    protected void ListingList(){
        int c = 0;
        int listLen = list.length;
        System.out.print("Options:");
        while (c < listLen) {
            System.out.print("\n\t" + list[c]);
            c++;
        }
    }


    protected static String FindWhat(){
        Phy.ListingList();
        System.out.print("\nFinding:\t");
        String FindThis = new Scanner(System.in).nextLine();
        
        return FindThis;
    }
    
    
    
    private static int ohThis(String that){
        int c = 0;
        
        for(String term : list){
            if(that.substring(0, 2).equalsIgnoreCase(term.substring(0, 2))){
            
                break;
            }
            c++;
        }
        return c;
    }
    
    private static void withWhat(int i){
        switch(i){//fill in the rest of the cases
            case 0: if(mass == 0){
                        System.out.print("Mass:\t");
                        mass = k.nextDouble();
                    }if(acceleration == 0){
                        System.out.print("Acceleration:\t");
                        acceleration = k.nextDouble();
                    }
                break;
            case 1: if(mass == 0){//this is for later  hopefully i will let the user go again to find another variable with previous information
                        System.out.print("Mass:\t");
                        mass = k.nextDouble();
                    }if(velocity == 0){
                        System.out.print("Velocity:\t");
                        velocity = k.nextDouble();
                    }
                break;
            case 2: if(angularMass == 0){
                    System.out.print("Rotational Inertia:\t");
                    angularMass = k.nextDouble();
                    }if(angularVelocity == 0){
                        System.out.print("Angular Velocity:\t");
                        angularVelocity = k.nextDouble();
                    }
                break;
            case 3: if(mass == 0){
                        System.out.print("Mass:\t");
                        mass = k.nextDouble();
                    }if(height == 0){
                        System.out.print("Height:\t");
                        height = k.nextDouble();
                    }
                break;
            case 4: if(f == 0){
                        System.out.print("Kinetic Friction:\t");//encase everything in ifs
                        f = k.nextDouble();
                    }if(x == 0){
                        System.out.print("Distance:\t");
                        x = k.nextDouble();
                    }
                break;
            case 5: if(F == 0){
                        System.out.print("Force:\t");
                        F = k.nextDouble();
                    }if(x == 0){
                        System.out.print("Distance:\t");
                        x = k.nextDouble();
                    }
                break;
            case 6: if(mass == 0){
                        System.out.print("Mass:\t");
                        mass = k.nextDouble();
                    }if(velocity == 0){
                        System.out.print("Velocity:\t");
                        velocity = k.nextDouble();
                    }
                break;
            case 7: if(angularMass == 0){
                        System.out.print("Angular Mass:\t");
                        angularMass = k.nextDouble();
                    }if(angularVelocity == 0){
                        System.out.print("Angular Velocity:\t");
                        angularVelocity = k.nextDouble();
                    }
                break;
            case 8: if(angle == 0){
                        System.out.print("Angle:\t");
                        angle = k.nextDouble();
                    }if(F == 0){
                        System.out.print("Force:\t");
                        F = k.nextDouble();
                    }
                break;
            case 9: if(u == 0){
                        System.out.print("Coefficient of Kinetic Friction:\t");
                        u = k.nextDouble();
                    }if(N == 0){
                        System.out.print("Normal Force:\t");
                        N = k.nextDouble();
                    }
                break;
            case 10:    if(Xo == 0){
                            System.out.print("Initial Distance:\t");
                            Xo = k.nextDouble();
                        }if(Vo == 0){
                            System.out.print("Initial Velocity:\t");
                            Vo = k.nextDouble();
                        }if(time == 0){
                            System.out.print("Time:\t");
                            time = k.nextDouble();
                        }if(acceleration == 0){
                            System.out.print("Acceleration:\t");
                            acceleration = k.nextDouble();
                        }
                break;
        }
        System.out.println();
    }
    
    private static double Force(double m, double a){
        System.out.println("F = m*a");
        F = m*a;
        return F;
    }
    
    private static double KineticEnergy(double m, double v){
        System.out.println("KE = (1/2)m*(v^2)");
        KE = (m/2*v*v);
        return KE;
    }
    private static double RotationalKineticEnergy(double I, double w){//fill in the rest of these
        System.out.println("KEr = (1/2)I*(w^2)");
        KEr = (I/2*w*w);
        return KEr;
    }
    private static double PotentialEnergy(double m, double h){
        System.out.println("PE = m*g*h");
        PE = m*g*h;
        return PE;
    }
    private static double ThermalEnergy(double f, double x){
        System.out.println("Integral of friction force over a distance");
        ThE = f*x;
        return ThE;
    }
    private static double Work(double F, double d){//need to make sure its ok with both options
        System.out.println("W = F*d OR dE");
        if(KE+PE+ThE == 0){
            W = F*d;
        }else{
            W = KE+PE+ThE;
        }

        return W;
    }
    private static double Momentum(double m, double v){
        System.out.println("p = mv");
        p = m*v;
        return p;
    }
    private static double AngularMomentum(double I, double w){
        System.out.println("L = Iw");
        L = I*w;
        return L;
    }
    private static double NormalForce(double theta, double F){
        System.out.println("N = F*cos(angle from vertical)");
        N = F*Math.cos(theta);
        return N;
    }
    private static double FrictionForce(double u, double N){
        System.out.println("f = uN");
        f = u*N;
        return f;
    }
    private static double Distance(double Xi, double Vi, double t, double a){
        System.out.println("x = Xo + Vo*t + (1/2)a*(t^2)");
        x = 0.5*a*t*t;
        x += (Vi*t);
        x += (Xi);
        return x;
    }
    
    
    private static void found(double value){
        System.out.println();
        System.out.print("I found " + value + " as your value ");
    }
    
    private static boolean UseAgainCheck(){
        System.out.println();
        System.out.print("Use this information to solve another problem?\t");
        String answer = k.next();
        return answer.startsWith("Y") || answer.startsWith("y");
    }
}
