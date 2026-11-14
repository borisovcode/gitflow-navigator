package gitflow;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * Parsed view of the git-flow-next configuration model:
 * {@code gitflow.branch.<name>.<property>}.
 *
 * <p>This class is intentionally free of IntelliJ Platform dependencies so it
 * can be unit-tested in isolation.</p>
 */
public final class GitflowConfigModel {

    public static final class Branch {
        private final Map<String, String> properties = new HashMap<>();

        public String get(String property) {
            return properties.get(property);
        }

        public boolean isBase() {
            return "base".equalsIgnoreCase(get("type"));
        }

        public String getParent() {
            return get("parent");
        }

        public String getStartPoint() {
            return get("startpoint");
        }

        public String getPrefix() {
            return get("prefix");
        }

        public String getTagPrefix() {
            return get("tagprefix");
        }
    }

    private final Map<String, Branch> branches = new HashMap<>();

    private GitflowConfigModel() {
    }

    /**
     * @param raw values as returned by {@code git config --get-regexp}
     *            (key to list of values)
     */
    public static GitflowConfigModel parse(Map<String, List<String>> raw) {
        GitflowConfigModel model = new GitflowConfigModel();
        if (raw == null) {
            return model;
        }
        for (Map.Entry<String, List<String>> entry : raw.entrySet()) {
            String key = entry.getKey();
            if (key == null || !key.startsWith("gitflow.branch.") || entry.getValue().isEmpty()) {
                continue;
            }
            String rest = key.substring("gitflow.branch.".length());
            int lastDot = rest.lastIndexOf('.');
            if (lastDot <= 0) {
                continue;
            }
            String name = rest.substring(0, lastDot);
            String property = rest.substring(lastDot + 1).toLowerCase(Locale.ROOT);
            model.branches
                    .computeIfAbsent(name, k -> new Branch())
                    .properties.put(property, entry.getValue().get(0));
        }
        return model;
    }

    public Map<String, Branch> getBranches() {
        return branches;
    }

    /**
     * The root base branch (the one without a parent): {@code main} or
     * {@code master} in a standard git-flow setup.
     */
    public String getTrunk() {
        for (Map.Entry<String, Branch> entry : branches.entrySet()) {
            Branch branch = entry.getValue();
            if (!branch.isBase()) {
                continue;
            }
            String parent = branch.getParent();
            if (parent == null || parent.isEmpty()) {
                return entry.getKey();
            }
        }
        return findFirst("main", "master");
    }

    /**
     * The base branch that has a parent: {@code develop} in a standard
     * git-flow setup.
     */
    public String getDevelop() {
        Branch develop = branches.get("develop");
        if (develop != null && develop.isBase()) {
            return "develop";
        }
        for (Map.Entry<String, Branch> entry : branches.entrySet()) {
            Branch branch = entry.getValue();
            if (branch.isBase()) {
                String parent = branch.getParent();
                if (parent != null && !parent.isEmpty()) {
                    return entry.getKey();
                }
            }
        }
        return null;
    }

    public String prefix(String type) {
        Branch branch = branches.get(type);
        return branch == null ? null : branch.getPrefix();
    }

    /**
     * Version tag prefix is stored per branch type; release and hotfix are the
     * tagging types in git-flow.
     */
    public String tagPrefix() {
        Branch release = branches.get("release");
        if (release != null && release.getTagPrefix() != null) {
            return release.getTagPrefix();
        }
        Branch hotfix = branches.get("hotfix");
        return hotfix == null ? null : hotfix.getTagPrefix();
    }

    private String findFirst(String... names) {
        for (String name : names) {
            if (branches.containsKey(name)) {
                return name;
            }
        }
        return null;
    }
}
