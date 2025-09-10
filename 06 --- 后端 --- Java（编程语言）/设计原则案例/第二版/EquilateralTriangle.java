public class EquilateralTriangle extends Triangle {
    public EquilateralTriangle(String name) {
        super(name);
    }

    @Override
    public void draw() {
        System.out.println("Drawing an equilateral triangle: " + getName());
    }
}