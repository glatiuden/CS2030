public class RubikRight extends Rubik {
    public RubikRight(Rubik rubik) {
        super(rubik.rightView());
    }

    @Override
    public Rubik right() {
        return super.right().leftView();
    }

    @Override
    public Rubik left() {
        return super.right().right().right().leftView();
    }

    @Override
    public Rubik half() {
        return super.right().right().leftView();
    }
}
