import java.util.Scanner;

public class Main 
{
    public static void main (String[] args)
    {
        Scanner sc = new Scanner(System.in);

        //Get point P
        double pX = sc.nextDouble();
        double pY = sc.nextDouble();

        //Get Point Q
        double qX = sc.nextDouble();
        double qY = sc.nextDouble();

        //Get radius
        double radius = sc.nextDouble();

        Circle c = createCircle(new Point(pX, pY), new Point(qX, qY), radius);
        if(c != null)
        {
            System.out.println("Created: " + c.toString());
        }
        else
        {
            System.out.println("No valid circle can be created");
        }

    }

    static Circle createCircle(Point p, Point q, double radius)
    {
         //length of PQ
        double distance = p.distance(q);
        if (distance > radius * 2 || distance == 0) {
            return null;
        } 
        else 
        {
        //Point m
        Point midPoint = p.midPoint(q);

        //angle of PQ
        double angle = p.angleTo(q) + Math.toRadians(90);
 
        //length of MQ
        double mqLength = midPoint.distance(q);
        //find length of MC
        double newDist = Math.sqrt((radius * radius) - (mqLength * mqLength));
        Point movedPoint = midPoint.moveTo(angle, newDist);
        return Circle.getCircle(movedPoint, radius);
        }
    }
}

