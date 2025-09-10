public class Circular extends Graphics {
    public Circular(String name) {
        super(name);
    }

    @Override
    public void draw() {
        System.out.println("Drawing a circle: " + getName());
    }
}