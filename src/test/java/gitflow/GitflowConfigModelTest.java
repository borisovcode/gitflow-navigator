package gitflow;

import org.junit.Test;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class GitflowConfigModelTest {

    private static Map<String, List<String>> config(String... keyValues) {
        Map<String, List<String>> result = new HashMap<>();
        for (int i = 0; i < keyValues.length; i += 2) {
            result.put(keyValues[i], Collections.singletonList(keyValues[i + 1]));
        }
        return result;
    }

    private static Map<String, List<String>> nextConfig() {
        return config(
                "gitflow.branch.main.type", "base",
                "gitflow.branch.develop.type", "base",
                "gitflow.branch.develop.parent", "main",
                "gitflow.branch.feature.type", "topic",
                "gitflow.branch.feature.parent", "develop",
                "gitflow.branch.feature.startpoint", "develop",
                "gitflow.branch.feature.prefix", "feature/",
                "gitflow.branch.bugfix.type", "topic",
                "gitflow.branch.bugfix.prefix", "bugfix/",
                "gitflow.branch.release.type", "topic",
                "gitflow.branch.release.prefix", "release/",
                "gitflow.branch.release.tagprefix", "v",
                "gitflow.branch.hotfix.type", "topic",
                "gitflow.branch.hotfix.prefix", "hotfix/",
                "gitflow.branch.hotfix.tagprefix", "v",
                "gitflow.branch.support.type", "topic",
                "gitflow.branch.support.prefix", "support/"
        );
    }

    @Test
    public void parsesBaseBranches() {
        GitflowConfigModel model = GitflowConfigModel.parse(nextConfig());

        assertEquals("main", model.getTrunk());
        assertEquals("develop", model.getDevelop());
    }

    @Test
    public void parsesPrefixes() {
        GitflowConfigModel model = GitflowConfigModel.parse(nextConfig());

        assertEquals("feature/", model.prefix("feature"));
        assertEquals("bugfix/", model.prefix("bugfix"));
        assertEquals("release/", model.prefix("release"));
        assertEquals("hotfix/", model.prefix("hotfix"));
        assertEquals("support/", model.prefix("support"));
        assertNull(model.prefix("unknown"));
    }

    @Test
    public void parsesTagPrefix() {
        GitflowConfigModel model = GitflowConfigModel.parse(nextConfig());

        assertEquals("v", model.tagPrefix());
    }

    @Test
    public void supportsCustomTrunkName() {
        GitflowConfigModel model = GitflowConfigModel.parse(config(
                "gitflow.branch.master.type", "base",
                "gitflow.branch.develop.type", "base",
                "gitflow.branch.develop.parent", "master"
        ));

        assertEquals("master", model.getTrunk());
        assertEquals("develop", model.getDevelop());
    }

    @Test
    public void handlesEmptyAndNullInput() {
        assertNull(GitflowConfigModel.parse(null).getTrunk());
        assertNull(GitflowConfigModel.parse(Collections.emptyMap()).getDevelop());
        assertNull(GitflowConfigModel.parse(Collections.emptyMap()).tagPrefix());
    }
}
