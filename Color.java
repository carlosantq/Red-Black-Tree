/**
 * Enum to store the colors of a Red-Black Tree.
 * @author carlosant
 */
public enum Color{
	BLACK(0),
	RED(1);

	private final int color;

    private Color(int color) {
        this.color = color;
    }

    public int getColor() {
        return this.color;
    }
}