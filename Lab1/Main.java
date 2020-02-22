import java.util.Scanner;

public class Main 
{
    public static void main (String[] args)
    {
        Scanner sc = new Scanner(System.in);
        
        int n = sc.nextInt();
        Point points[] = new Point[n];

        double radius = 1;
        
        int answer = 0;
        
        for(int i = 0; i < n; i++)
        {
          double tempX = sc.nextDouble();
          double tempY = sc.nextDouble();
          points[i] = new Point(tempX, tempY);
        }

        sc.close();

        for(int i = 0; i< n; i++)
        {
            for(int j = 0; j < n; j++){
                if(points[i].distance(points[j]) < 2 && points[i] != points[j])
                {
                    Circle c = createCircle(points[i], points[j], radius);
                    int counter = 0;

                    for(int k=0; k <n; k++)
                    {
                        if(c.contains(points[k]))
                        {
                            counter++;
                        }
                    }

                    if(counter > answer)
                        answer = counter;
                }
            }
        }
        System.out.println("Maximum Disc Coverage: " + answer);
    }

    public static Circle createCircle(Point p, Point q, double radius)
    {
         //length of PQ
        double distance = p.distance(q);
        if(distance <= (2*radius) && distance != 0)
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
        else
            return null;        
    }
}
