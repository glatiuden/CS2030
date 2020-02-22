public class Dice implements SideViewable {
    char[] sides;

    // U F R B L D
    public Dice() {
        sides = new char[] { 'U', 'F', 'R', 'B', 'L', 'D' };
    }

    public Dice(char[] sides) {
        this.sides = sides;
    }

    @Override
    public String toString() {
        return String.format("\n%c\n%c%c%c%c\n   %c", this.sides[0], this.sides[1], this.sides[2], this.sides[3],
                this.sides[4], this.sides[5]);
    }

    // U F R B L D vs U R B L F D
    @Override
    public Dice rightView() {
        char[] newSides = this.sides.clone();
        newSides[1] = this.sides[2];
        newSides[2] = this.sides[3];
        newSides[3] = this.sides[4];
        newSides[4] = this.sides[1];
        return new Dice(newSides);
    }

    // U F R B L D vs U L F R B D (0 and 5 no change)
    @Override
    public Dice leftView() {
        char[] newSides = this.sides.clone();
        newSides[1] = this.sides[4];
        newSides[2] = this.sides[1];
        newSides[3] = this.sides[2];
        newSides[4] = this.sides[3];
        return new Dice(newSides);
    }

    // U F R B L D vs B U R D L F (2 and 4 no change)
    @Override
    public Dice upView() {
        char[] newSides = this.sides.clone();
        newSides[0] = this.sides[3];
        newSides[1] = this.sides[0];
        newSides[3] = this.sides[5];
        newSides[5] = this.sides[1];
        return new Dice(newSides);
    }

    // U F R B L D vs F D R U L B (2 and 4 no change)
    @Override
    public Dice downView() {
        char[] newSides = this.sides.clone();
        newSides[0] = this.sides[1];
        newSides[1] = this.sides[5];
        newSides[3] = this.sides[0];
        newSides[5] = this.sides[3];
        return new Dice(newSides);
    }

    // U F R B L D vs U B L F R D (0 and 4 no change)
    @Override
    public Dice backView() {
        char[] newSides = this.sides.clone();
        newSides[1] = this.sides[3];
        newSides[2] = this.sides[4];
        newSides[3] = this.sides[1];
        newSides[4] = this.sides[2];
        return new Dice(newSides);
    }

    @Override
    public Dice frontView() {
        char[] newSides = this.sides.clone();
        return new Dice(newSides);
    }
}