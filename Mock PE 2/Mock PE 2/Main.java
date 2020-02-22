import java.util.Arrays;

public class Main
{
    public static void main(String[] args) {
        String testOutput = "According   to  a  research  at  Cambridge  University,  it  doesn't  matter  in what order the letters in a word are, the only important thing is that the first and last letter be at the right place. The rest can be a total mess and you  can still read it without problem.      This is because the human mind does not read every     letter     by     itself     but     the     word    as    a    whole.";
        System.out.println(Parser.parse(Arrays.asList(testOutput)).shuffle());
    }
}