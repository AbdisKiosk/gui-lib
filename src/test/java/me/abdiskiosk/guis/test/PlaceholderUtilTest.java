package me.abdiskiosk.guis.test;

import me.abdiskiosk.guis.placeholder.PlaceholderUtils;
import org.junit.jupiter.api.Test;

public class PlaceholderUtilTest {

    @Test
    public void withDotSeperatedPlaceholder_rootPlaceholderIsReturned() {
        String testPlaceholderString = "placeholder used is {selected.pretty}";

        assert PlaceholderUtils.getUsedPlaceholders(testPlaceholderString).contains("selected")
                && !PlaceholderUtils.getUsedPlaceholders(testPlaceholderString).contains("pretty");
    }
}
