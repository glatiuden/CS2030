public class RubikUp extends Rubik {
    public RubikUp(Rubik rubik) {
        super(rubik.upView());
    }

    @Override
    public Rubik right() {
        return super.right().downView();
    }

    @Override
    public Rubik left() {
        return super.right().right().right().downView();
    }

    @Override
    public Rubik half() {
        return super.right().right().downView();
    }
}
