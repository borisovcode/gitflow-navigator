package gitflow;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class GitflowOptionsFactoryTest {

    @Test
    public void exposesCanonicalFetchFlag() {
        assertEquals("--fetch", GitflowOptionsFactory.getOptionById("FEATURE_fetchFromOrigin").get("flag"));
        assertEquals("--fetch", GitflowOptionsFactory.getOptionById("RELEASE_fetchFromOrigin").get("flag"));
        assertEquals("--fetch", GitflowOptionsFactory.getOptionById("HOTFIX_fetchFromOrigin").get("flag"));
        assertEquals("--fetch", GitflowOptionsFactory.getOptionById("BUGFIX_fetchFromOrigin").get("flag"));
    }

    @Test
    public void exposesCanonicalPushFlag() {
        assertEquals("--push", GitflowOptionsFactory.getOptionById("FEATURE_pushOnFinish").get("flag"));
        assertEquals("--push", GitflowOptionsFactory.getOptionById("RELEASE_pushOnFinish").get("flag"));
        assertEquals("--push", GitflowOptionsFactory.getOptionById("HOTFIX_pushOnFinish").get("flag"));
    }
}
