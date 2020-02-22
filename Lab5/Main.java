import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

class Main {
    public static void main(String[] args) {
        BufferedReader scroll = null;
        try {
            if (args.length != 0) {
                scroll = new BufferedReader(new FileReader(args[0]));
                Farm f = new Farm();
                String line = "";
                while ((line = scroll.readLine()) != null) {
                    try {
                        f.runInstruction(line);
                    } catch (IllegalInstructionException ex) {
                        System.err.println(ex.getMessage());
                    }
                }
            }
        } catch (IOException exception) {
            System.err.println("Unable to open file " + args[0] + " " + exception);
        }
    }
}