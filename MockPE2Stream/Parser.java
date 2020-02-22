import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Parser {
    class Pair<U, V> {
        private U first;
        private V second;

        public Pair(U first, V second) {
            this.first = first;
            this.second = second;
        }
    }

    private List<String> lines;

    private Parser(List<String> lines) {
        this.lines = lines;
    }

    private Parser(int number) {
        this.lines = Arrays.asList(String.valueOf(number));
    }

    private Parser(String word) {
        this.lines = Arrays.asList(word);
    }

    public static Parser parse(List<String> newLines) {
        return new Parser(newLines);
    }

    public Parser linecount() {
        return new Parser(lines.size());
    }

    public Parser wordcount() {
        return new Parser((int) lines.stream().flatMap(word -> Arrays.stream(word.split("\\s")))
                .filter(word -> !word.isEmpty()).count());
    }

    public Parser grab(String grabString) {
        return new Parser(lines.stream().filter(word -> word.contains(grabString)).collect(Collectors.toList()));
    }

    public Parser echo() {
        return new Parser(lines.stream().flatMap(word -> Arrays.stream(word.split("\\s")))
                .filter(word -> !word.isEmpty()).reduce((x, y) -> x + " " + y).orElse(""));
    }

    public Parser chop(int start, int end) {
        return new Parser(lines.stream().map(word -> {
            int beginIndex = start < 1 ? 0 : start - 1;
            int endIndex = end > word.length() ? word.length() : end;
            return start > word.length() ? "" : word.substring(beginIndex, endIndex);
        }).collect(Collectors.toList()));
    }

    // public Parser chop(String chopString, int... indexes) {
    //     Function<String, Pair<List<String>, Boolean>> chopping = word -> {
    //         String newWord = word;
    //         boolean chopped = false;
    //         List<String> choppedList = new ArrayList<>();
    //         while (newWord.contains(chopString)) {
    //             chopped = true;
    //             choppedList.add(newWord.substring(0, newWord.indexOf(chopString)));
    //             newWord = newWord.substring(newWord.indexOf(chopString) + chopString.length(), newWord.length());
    //         }
    //         choppedList.add(newWord);
    //         return new Pair<>(choppedList, chopped);
    //     };

    //     Function<Pair<List<String>, Boolean>, List<String>> filtering = choppedList -> {
    //         Arrays.sort(indexes);
    //         List<String> filteredList = Arrays.stream(indexes).map(i -> i - 1).filter(i -> i < choppedList.first.size())
    //                 .mapToObj(i -> choppedList.first.get(i)).collect(Collectors.toList());
    //         return choppedList.second ? filteredList : choppedList.first;
    //     };

    //     return new Parser(lines.stream().map(word -> String.join(chopString, filtering.apply(chopping.apply(word))))
    //             .collect(Collectors.toList()));
    // }

    public Parser chop(String chopString, int... indexes) {
        Function<String, List<String>> chopping = word -> {
            String newWord = word.replace(chopString, chopString + " ");
            List<String> choppedList = Arrays.asList(newWord.split(chopString));
            return choppedList;
        };

        Function<List<String>, List<String>> filtering = choppedList -> {
            Arrays.sort(indexes);
            List<String> filteredList = Arrays.stream(indexes).map(i -> i - 1).filter(i -> i < choppedList.size())
                    .mapToObj(i -> choppedList.get(i).trim()).collect(Collectors.toList());
            return filteredList.size() > 0 ? filteredList : choppedList;
        };

        return new Parser(lines.stream().map(word -> String.join(chopString, filtering.apply(chopping.apply(word))))
                .collect(Collectors.toList()));
    }

    public Parser shuffle() {
        Function<String, String> shuffleWords = word -> {
            if (word.length() > 3) {
                if (!Character.isLetterOrDigit(word.charAt(word.length() - 1))
                        || !Character.isLetterOrDigit(word.charAt(word.length() - 2))) {
                    return word.charAt(0) + word.substring(2, word.length() - 2) + word.charAt(1)
                            + word.substring(word.length() - 2);
                } else {
                    return word.charAt(0) + word.substring(2, word.length() - 1) + word.charAt(1)
                            + word.charAt(word.length() - 1);
                }
            }
            return word;
        };
        return new Parser(lines.stream().map(word -> Arrays.stream(word.split("\\s+")).map(x -> shuffleWords.apply(x))
                .reduce((x, y) -> x + " " + y).orElse("")).collect(Collectors.toList()));
    }

    @Override
    public String toString() {
        return String.join("\n", lines);
    }
}