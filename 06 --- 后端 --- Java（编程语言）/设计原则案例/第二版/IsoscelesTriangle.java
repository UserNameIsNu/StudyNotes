public class IsoscelesTriangle extends Triangle {
    public IsoscelesTriangle(String name) {
        super(name);
    }

    @Override
    public void draw() {
        System.out.println("Drawing an isosceles triangle: " + getName());
    }
}