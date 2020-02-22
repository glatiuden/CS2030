import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Parser {
    private List<String> lines;

    public Parser() {
        this.lines = new ArrayList<String>();
    }

    public Parser(List<String> lines) {
        this.lines = lines;
    }

    public static Parser parse(List<String> lines) {
        Parser newParser = new Parser();
        lines.stream().forEach(newParser.lines::add);
        return newParser;
    }

    public Parser linecount() {
        return new Parser(Arrays.asList(String.valueOf(lines.size())));
    }

    public Parser wordcount() {
        List<String> newList = new ArrayList<>();
        lines.stream().forEach(x -> {
            List<String> newArr = Arrays.asList(x.split(" "));
            newArr.forEach(s -> {
                if (!s.isEmpty()) {
                    newList.add(s);
                }
            });
        });
        return new Parser(Arrays.asList(String.valueOf(newList.size())));
    }

    public Parser grab(String str) {
        List<String> newList = new ArrayList<>();
        lines.stream().forEach(x -> {
            if (x.contains(str))
                newList.add(x);
        });
        // return new Parser(newList);
        return new Parser(lines.stream().filter(x -> x.contains(str)).collect(Collectors.toList()));
    }

    public Parser echo() {
        List<String> newList = new ArrayList<>();
        lines.stream().forEach(x -> {
            List<String> newArr = Arrays.asList(x.split(" "));
            newArr.forEach(s -> {
                if (!s.isEmpty()) {
                    newList.add(s);
                }
            });
        });
        return new Parser(Arrays.asList(String.join(" ", newList)));
    }

    public Parser chop(int start, int end) {
        int newStart = start - 1; // since start is 1, not 0
        List<String> newList = new ArrayList<>();
        for (String str : lines) {
            if (start <= 0) { // start = 0
                if (str.length() < end) { // if length of string is bigger than end, just return whole string
                    newList.add(str);
                } else { // else substring from index 0 to destination end
                    newList.add(str.substring(0, end));
                }
            } else if (start == str.length() + 1) { // if start Idx == length of String
                newList.add(str.substring(newStart)); // Substring from start instead
            } else if (start > str.length()) { // Invalid output
                newList.add("");
            } else {
                if (end > str.length()) {
                    newList.add(str.substring(newStart));
                } else {
                    newList.add(str.substring(newStart, end));
                }
            }
        }
        return new Parser(newList);
    }

    public Parser chop(String delStr, int... args) {
        List<String> newList = new ArrayList<>();
        Arrays.sort(args);
        for(String item : lines)
        {
            String newItem = item;
            boolean chopped = false;
            List<String> choppedList = new ArrayList<>();

            while(newItem.contains(delStr))
            {
                chopped = true;
                choppedList.add(newItem.substring(0, newItem.indexOf(delStr)));
                newItem = newItem.substring(newItem.indexOf(delStr) + delStr.length(), newItem.length());
            }
            choppedList.add(newItem);
            
            List<String> filteredList = new ArrayList<>();
            for(int i : args)
            {
                if(i - 1 < choppedList.size())
                {
                    filteredList.add(choppedList.get(i - 1));
                }
            }
            newList.add(String.join(delStr, chopped ? filteredList : choppedList));
        }
        return new Parser(newList);
    }

    public Parser shuffle() {
        List<String> newList = new ArrayList<>();
        lines.stream().forEach(x -> {
            List<String> newArr = Arrays.asList(x.split("\\s+"));
            List<String> semiNewList = new ArrayList<>();
            for (String str : newArr) {
                String strippedInput = str.replaceAll("\\W", "");
                if (strippedInput.length() <= 3) {
                    semiNewList.add(str);
                } else {
                    semiNewList.add(reinsert(str, shuffle(strippedInput)));
                }
            }
            newList.add(String.join(" ", semiNewList));
        });
        return new Parser(newList);
    }

    public static String shuffle(String str) {
        return str.charAt(0) + str.substring(2, str.length() - 1) + str.charAt(1) + str.charAt(str.length() - 1);
    }

    private static String reinsert(String old, String shuffled) {
        StringBuilder result = new StringBuilder(shuffled);
        for (char ch : old.toCharArray()) {
            if (!Character.isLetterOrDigit(ch)) {
                result.insert(old.indexOf(ch), ch);
            }
        }
        return result.toString();
    }

    public String toString() {
        return String.join("\n", lines);
    }
}