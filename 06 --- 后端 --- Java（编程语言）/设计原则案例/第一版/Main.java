public class Main {
    public static void main(String[] args) {
        Graphics circular = new Circular("圆形");
        Graphics square = new Square("正方形");
        Graphics triangle = new Triangle("三角形");
        Graphics equilateralTriangle = new EquilateralTriangle("等边三角形");
        Graphics isoscelesTriangle = new IsoscelesTriangle("等腰三角形");

        triangle.draw();
        equilateralTriangle.draw();
    }
}
