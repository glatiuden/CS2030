public class RubikBack extends Rubik {
    public RubikBack(Rubik rubik) {
        super(rubik.backView());
    }

    @Override
    public Rubik right() {
        return super.right().backView();
    }

    @Override
    public Rubik left() {
        return super.right().right().right().backView();
    }

    @Override
    public Rubik half() {
        return super.right().right().backView();
    }
}
