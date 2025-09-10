public class ShapeFactory {
    public static Graphics getShape(String shapeType, String name) {
        if (shapeType == null) {
            return null;
        }
        if (shapeType.equalsIgnoreCase("CIRCLE")) {
            return new Circular(name);
        } else if (shapeType.equalsIgnoreCase("SQUARE")) {
            return new Square(name);
        } else if (shapeType.equalsIgnoreCase("EQUILATERAL_TRIANGLE")) {
            return new EquilateralTriangle(name);
        } else if (shapeType.equalsIgnoreCase("ISOSCELES_TRIANGLE")) {
            return new IsoscelesTriangle(name);
        }
        return null;
    }
}