public class RubikDown extends Rubik {
    public RubikDown(Rubik rubik) {
        super(rubik.downView());
    }

    @Override
    public Rubik right() {
        return super.right().upView();
    }

    @Override
    public Rubik left() {
        return super.right().right().right().upView();
    }

    @Override
    public Rubik half() {
        return super.right().right().upView();
    }
}
