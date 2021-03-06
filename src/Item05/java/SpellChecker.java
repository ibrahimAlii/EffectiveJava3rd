package Item05.java;

import java.util.List;
import java.util.Objects;

// Dependency injection provides flexibility and testability
public class SpellChecker {
    private final Lexicon dictionary;

    public SpellChecker(Lexicon dictionary){
        this.dictionary = Objects.requireNonNull(dictionary);
    }

    public boolean isValide(String word){.....}
    public List<String> suggestions(String type){.....}
}
