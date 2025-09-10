public abstract class Graphics{
    private String name;

    public Graphics(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    void draw() {
        System.out.println("画了一个" + getName());
    }
}
