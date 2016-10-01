package ua.naiksoftware.examplesticky.model.util;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import ua.naiksoftware.examplesticky.R;
import ua.naiksoftware.examplesticky.decorator.FirstLetterStickyHolder.ModelWithName;
import ua.naiksoftware.examplesticky.model.Team;

/**
 * Created by naik on 01.10.16.
 */

public class Generator {

    private static final SecureRandom RND = new SecureRandom();

    public static List<Team> createTeams(int size) {
        List<Team> teams = new ArrayList<>(size);
        for (int i = 0; i < size; i++) {
            teams.add(new Team(capitalize(randomString(5, 14), CapitalizeMode.FIRST_UPPER), R.drawable.ic_launcher));
        }
        return sort(teams);
    }

    public static String randomString(int min, int max) {
        StringBuilder builder = new StringBuilder(max - min);
        for (int i = min; i <= max; i++) {
            builder.append((char) ('a' + RND.nextInt('z' - 'a' + 1)));
        }
        return builder.toString();
    }

    private static int randomInt(int min, int max) {
        return min + RND.nextInt(max);
    }

    private static <T extends ModelWithName> List<T> sort(List<T> models) {
        Collections.sort(models, new Comparator<ModelWithName>() {
            @Override
            public int compare(ModelWithName lhs, ModelWithName rhs) {
                return lhs.getName().compareToIgnoreCase(rhs.getName());
            }
        });
        return models;
    }

    public static String capitalize(String string, CapitalizeMode mode) {
        switch (mode) {
            case FIRST_UPPER: return string.substring(0,1).toUpperCase() + string.substring(1).toLowerCase();
        }
        return string;
    }

    public enum CapitalizeMode {
        FIRST_UPPER
    }
}
