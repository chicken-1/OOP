public class Rectangle extends Shape {
    protected double width;
    protected double length;

    public Rectangle() {
    }

    public Rectangle(double width, double length) {
        this.width = width;
        this.length = length;
    }

    /**constructor. */
    public Rectangle(double width, double length, String color, boolean filled){
        super(color, filled);
        this.width = width;
        this.length = length;
    }

    public double getWidth() {
        return width;
    }

    public void setWidth(double width) {
        this.width = width;
    }

    public double getLength() {
        return length;
    }

    public void setLength(double length) {
        this.length = length;
    }

    @Override
    public double getArea() {
        return width * length;
    }

    public double getPerimeter() {
        return 2 * (width + length);
    }

    public String toString() {
        String s = "Rectangle[width=" + width + ",length=" + length;
        return s + ",color=" + color + ",filled=" + filled + "]";
    }

    /**test. */
    public static void main(String[] args) {
        Rectangle r = new Rectangle(5.0, 6.0, "red", true);
        System.out.println(r.getArea());
        System.out.println(r.getPerimeter());
        System.out.println(r.toString());
    }
}
