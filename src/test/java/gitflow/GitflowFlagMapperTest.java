package gitflow;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class GitflowFlagMapperTest {

    @Test
    public void mapsCanonicalFetchToAvhShortFlag() {
        assertEquals("-F", GitflowFlagMapper.map(GitflowCliEdition.AVH, "FEATURE_fetchFromOrigin", "--fetch"));
        assertEquals("-F", GitflowFlagMapper.map(GitflowCliEdition.AVH, "RELEASE_fetchFromOrigin", "--fetch"));
        assertEquals("-F", GitflowFlagMapper.map(GitflowCliEdition.AVH, "HOTFIX_fetchFromOrigin", "--fetch"));
        assertEquals("-F", GitflowFlagMapper.map(GitflowCliEdition.AVH, "BUGFIX_fetchFromOrigin", "--fetch"));
    }

    @Test
    public void keepsCanonicalFetchForNext() {
        assertEquals("--fetch", GitflowFlagMapper.map(GitflowCliEdition.NEXT, "FEATURE_fetchFromOrigin", "--fetch"));
    }

    @Test
    public void mapsPushToAvhShortFlagForReleaseAndHotfix() {
        assertEquals("-p", GitflowFlagMapper.map(GitflowCliEdition.AVH, "RELEASE_pushOnFinish", "--push"));
        assertEquals("-p", GitflowFlagMapper.map(GitflowCliEdition.AVH, "HOTFIX_pushOnFinish", "--push"));
    }

    @Test
    public void keepsLongPushForAvhFeatureAndBugfix() {
        assertEquals("--push", GitflowFlagMapper.map(GitflowCliEdition.AVH, "FEATURE_pushOnFinish", "--push"));
        assertEquals("--push", GitflowFlagMapper.map(GitflowCliEdition.AVH, "BUGFIX_pushOnFinish", "--push"));
    }

    @Test
    public void keepsCanonicalPushForNext() {
        assertEquals("--push", GitflowFlagMapper.map(GitflowCliEdition.NEXT, "FEATURE_pushOnFinish", "--push"));
        assertEquals("--push", GitflowFlagMapper.map(GitflowCliEdition.NEXT, "RELEASE_pushOnFinish", "--push"));
    }

    @Test
    public void translatesLegacyShortFlagsForNext() {
        assertEquals("--fetch", GitflowFlagMapper.map(GitflowCliEdition.NEXT, "FEATURE_fetchFromOrigin", "-F"));
        assertEquals("--push", GitflowFlagMapper.map(GitflowCliEdition.NEXT, "RELEASE_pushOnFinish", "-p"));
    }

    @Test
    public void leavesOtherFlagsUntouched() {
        assertEquals("--no-ff", GitflowFlagMapper.map(GitflowCliEdition.NEXT, "FEATURE_noFastForward", "--no-ff"));
        assertEquals("-k", GitflowFlagMapper.map(GitflowCliEdition.AVH, "FEATURE_keepBranch", "-k"));
        assertNull(GitflowFlagMapper.map(GitflowCliEdition.NEXT, "FEATURE_fetchFromOrigin", null));
    }
}
