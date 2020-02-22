public class RubikLeft extends Rubik {
    public RubikLeft(Rubik rubik) {
        super(rubik.leftView());
    }

    @Override
    public Rubik right() {
        return super.right().rightView();
    }

    @Override
    public Rubik left() {
        return super.right().right().right().rightView();
    }

    @Override
    public Rubik half() {
        return super.right().right().rightView();
    }
}
