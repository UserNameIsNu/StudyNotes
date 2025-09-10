public class Main {
    public static void main(String[] args) {
        ShapeFactory factory = new ShapeFactory();
        Graphics circle = factory.getShape("CIRCLE", "圆形");
        Graphics square = factory.getShape("SQUARE", "正方形");
        Graphics equilateralTriangle = factory.getShape("EQUILATERAL_TRIANGLE", "等边三角形");
        Graphics isoscelesTriangle = factory.getShape("ISOSCELES_TRIANGLE", "等腰三角形");

        circle.draw();
        square.draw();
        equilateralTriangle.draw();
        isoscelesTriangle.draw();
    }
}