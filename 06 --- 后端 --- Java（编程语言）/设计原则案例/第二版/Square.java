public class Square extends Graphics {
    public Square(String name) {
        super(name);
    }

    @Override
    public void draw() {
        System.out.println("Drawing a square: " + getName());
    }
}