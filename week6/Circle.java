public class Circle extends Shape {
    protected double radius;

    public Circle() {
    }

    public Circle(double radius) {
        this.radius = radius;
    }

    public Circle(double radius, String color, boolean filled) {
        super(color, filled);
        this.radius = radius;
    }

    public double getRadius() {
        return radius;
    }

    public void setRadius(double radius) {
        this.radius = radius;
    }

    @Override
    public double getArea() {
        return Math.PI * radius * radius;
    }

    public double getPerimeter() {
        return 2 * Math.PI * radius;
    }

    public String toString() {
        return "Circle[radius=" + radius + ",color=" + color + ",filled=" + filled + "]";
    }

    /**test. */
    public static void main(String[] args) {
        Circle c = new Circle(11.0, "red", true);
        System.out.println(c.getArea());
        System.out.println(c.getPerimeter());
        System.out.println(c.toString());
    }
}
