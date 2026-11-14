package gitflow;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class GitflowCliEditionTest {

    @Test
    public void detectsAvh() {
        assertEquals(GitflowCliEdition.AVH, GitflowCliEdition.detect("1.12.3 (AVH Edition)"));
    }

    @Test
    public void detectsGitFlowNext() {
        assertEquals(GitflowCliEdition.NEXT, GitflowCliEdition.detect("2.1.0 (git-flow-next)"));
    }

    @Test
    public void detectsUnknownForMissingVersion() {
        assertEquals(GitflowCliEdition.UNKNOWN, GitflowCliEdition.detect(null));
    }

    @Test
    public void detectsUnknownForVanillaGitFlow() {
        assertEquals(GitflowCliEdition.UNKNOWN, GitflowCliEdition.detect("0.4.1"));
    }
}
