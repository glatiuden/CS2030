class Point {

    private final double x;
    private final double y;

    public Point(double x, double y)
    {
        this.x = x;
        this.y = y;
    }

    double distance(Point q)
    {
        double dx = this.x - q.x;
        double dy = this.y - q.y;
        return Math.sqrt(dx * dx + dy * dy);
    }

    //level 1
    @Override
    public String toString()
    {
       return "point (" + String.format("%.3f", this.x) + ", " + String.format("%.3f", this.y) + ")"; 
    }    

    //level 2
    Point midPoint(Point q)
    {
        double midX = (this.x + q.x)/2;
        double midY = (this.y + q.y)/2;
        return new Point(midX, midY);
    }

    double angleTo(Point q)
    {
        double xDiff = q.x - this.x;
        double yDiff = q.y - this.y;

        return Math.atan2(yDiff, xDiff);
    }

    //level 3
    Point moveTo(double angle, double distance)
    {
        double newX = this.x + distance * Math.cos(angle);
        double newY = this.y + distance * Math.sin(angle);
        
        return new Point(newX, newY);
    }
}
