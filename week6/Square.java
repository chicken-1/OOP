public class Square extends Rectangle {
    public Square() {
    }

    public Square(double side) {
        super(side, side);
    }

    public Square(double side, String color, boolean filled) {
        super(side, side, color, filled);
    }

    public double getSide() {
        return length;
    }

    public void setSide(double side) {
        this.length = side;
        this.width = side;

    } 

    @Override
    public void setWidth(double side) {
        this.width = side;
    }

    public void setLength(double side) {
        this.length = side;
    }

    public String toString() {
        return "Square[side=" + length + ",color=" + color + ",filled=" + filled + "]";
    }

    /**test. */
    public static void main(String[] args) {
        Square s = new Square(5.0, "red", true);
        System.out.println(s.getArea());
        System.out.println(s.getPerimeter());
        System.out.println(s.toString());
    }
}
