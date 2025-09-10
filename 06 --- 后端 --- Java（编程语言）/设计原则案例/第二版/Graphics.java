public abstract class Graphics {
    private String name;

    public Graphics(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public abstract void draw();
}