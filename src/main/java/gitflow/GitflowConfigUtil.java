package gitflow;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.util.Disposer;
import com.intellij.openapi.vcs.VcsException;
import com.intellij.openapi.vfs.VirtualFile;
import git4idea.config.GitConfigUtil;
import git4idea.repo.GitRepository;
import gitflow.ui.NotifyUtil;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 *
 *
 * @author Opher Vishnia / opherv.com / opherv@gmail.com
 */

public class GitflowConfigUtil {

    public static final String BRANCH_MASTER = "gitflow.branch.master";
    public static final String BRANCH_DEVELOP = "gitflow.branch.develop";
    public static final String PREFIX_FEATURE = "gitflow.prefix.feature";
    public static final String PREFIX_RELEASE = "gitflow.prefix.release";
    public static final String PREFIX_HOTFIX = "gitflow.prefix.hotfix";
    public static final String PREFIX_BUGFIX = "gitflow.prefix.bugfix";
    public static final String PREFIX_SUPPORT = "gitflow.prefix.support";
    public static final String PREFIX_VERSIONTAG = "gitflow.prefix.versiontag";

    private static  Map<Project, Map<String, GitflowConfigUtil>> gitflowConfigUtilMap = new HashMap<Project, Map<String, GitflowConfigUtil>>();

    Project project;
    GitRepository repo;
    private volatile Map<String, Map<String, String>> nextBranches = new HashMap<>();
    public String masterBranch;
    public String developBranch;
    public String featurePrefix;
    public String releasePrefix;
    public String hotfixPrefix;
    public String bugfixPrefix;
    public String supportPrefix;
    public String versiontagPrefix;

    public static GitflowConfigUtil getInstance(@NotNull Project project_, @NotNull GitRepository repo_)
    {
        GitflowConfigUtil instance;
        if (gitflowConfigUtilMap.containsKey(project_) && gitflowConfigUtilMap.get(project_).containsKey(repo_.getPresentableUrl())) {
            instance = gitflowConfigUtilMap.get(project_).get(repo_.getPresentableUrl());
        } else {
            Map<String, GitflowConfigUtil> innerMap = new HashMap<String, GitflowConfigUtil>();
            instance = new GitflowConfigUtil(project_, repo_);

            gitflowConfigUtilMap.put(project_, innerMap);
            innerMap.put(repo_.getPresentableUrl(), instance);

            //cleanup
            Disposer.register(repo_, () -> innerMap.remove(repo_));
            Disposer.register(project_, () -> gitflowConfigUtilMap.remove(project_));
        }

        return instance;
    }

    GitflowConfigUtil(Project project_, GitRepository repo_){
        project = project_;
        repo = repo_;

        update();
    }

    public void update(){
        final VirtualFile root = repo.getRoot();

        try{
            Future<Void> f = (Future<Void>) ApplicationManager.getApplication().executeOnPooledThread(new Runnable() {
                @Override
                public void run() {
                    try{
                        // git-flow AVH keys. They are also present in repositories
                        // that were initialized with AVH and later used with
                        // git-flow-next (which imports but does not delete them).
                        masterBranch = GitConfigUtil.getValue(project, root, BRANCH_MASTER);
                        developBranch = GitConfigUtil.getValue(project, root, BRANCH_DEVELOP);
                        featurePrefix = GitConfigUtil.getValue(project,root,PREFIX_FEATURE);
                        releasePrefix = GitConfigUtil.getValue(project,root,PREFIX_RELEASE);
                        hotfixPrefix = GitConfigUtil.getValue(project,root,PREFIX_HOTFIX);
                        bugfixPrefix = GitConfigUtil.getValue(project,root,PREFIX_BUGFIX);
                        supportPrefix = GitConfigUtil.getValue(project,root,PREFIX_SUPPORT);
                        versiontagPrefix = GitConfigUtil.getValue(project,root,PREFIX_VERSIONTAG);

                        // git-flow-next keys.
                        applyGitFlowNextConfig(root);
                    } catch (VcsException e) {
                        NotifyUtil.notifyError(project, "Config error", e);
                    }
                }});
                f.get();

        }  catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }

    /**
     * Reads the git-flow-next configuration model:
     * {@code gitflow.branch.<name>.type/parent/startpoint/prefix/tagprefix}.
     * AVH keys take precedence when both models are present.
     */
    private void applyGitFlowNextConfig(VirtualFile root) throws VcsException {
        Map<String, List<String>> raw = GitConfigUtil.getValues(project, root, "^gitflow\\.branch\\.");
        if (raw == null || raw.isEmpty()) {
            return;
        }

        Map<String, Map<String, String>> branches = new HashMap<>();
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
            branches.computeIfAbsent(name, k -> new HashMap<>()).put(property, entry.getValue().get(0));
        }
        nextBranches = branches;

        String trunk = null;
        String develop = null;
        for (Map.Entry<String, Map<String, String>> entry : branches.entrySet()) {
            Map<String, String> properties = entry.getValue();
            if (!"base".equalsIgnoreCase(properties.getOrDefault("type", ""))) {
                continue;
            }
            String parent = properties.get("parent");
            if (parent == null || parent.isEmpty()) {
                if (trunk == null) {
                    trunk = entry.getKey();
                }
            } else if (develop == null) {
                develop = entry.getKey();
            }
        }

        if (masterBranch == null) {
            masterBranch = trunk != null ? trunk : findBase(branches, "main", "master");
        }
        if (developBranch == null) {
            developBranch = develop != null ? develop : findBase(branches, "develop");
        }
        if (featurePrefix == null) {
            featurePrefix = propertyOf(branches, "feature", "prefix");
        }
        if (bugfixPrefix == null) {
            bugfixPrefix = propertyOf(branches, "bugfix", "prefix");
        }
        if (releasePrefix == null) {
            releasePrefix = propertyOf(branches, "release", "prefix");
        }
        if (hotfixPrefix == null) {
            hotfixPrefix = propertyOf(branches, "hotfix", "prefix");
        }
        if (supportPrefix == null) {
            supportPrefix = propertyOf(branches, "support", "prefix");
        }
        if (versiontagPrefix == null) {
            versiontagPrefix = propertyOf(branches, "release", "tagprefix");
            if (versiontagPrefix == null) {
                versiontagPrefix = propertyOf(branches, "hotfix", "tagprefix");
            }
        }
    }

    private static String findBase(Map<String, Map<String, String>> branches, String... names) {
        for (String name : names) {
            if (branches.containsKey(name)) {
                return name;
            }
        }
        return null;
    }

    private static String propertyOf(Map<String, Map<String, String>> branches, String type, String property) {
        Map<String, String> properties = branches.get(type);
        return properties == null ? null : properties.get(property);
    }


    public String getFeatureNameFromBranch(String branchName){
        return branchName.substring(branchName.indexOf(featurePrefix)+featurePrefix.length(),branchName.length());
    }

    public String getReleaseNameFromBranch(String branchName){
        return branchName.substring(branchName.indexOf(releasePrefix) + releasePrefix.length(), branchName.length());
    }

    public String getHotfixNameFromBranch(String branchName){
        return branchName.substring(branchName.indexOf(hotfixPrefix) + hotfixPrefix.length(), branchName.length());
    }

    public String getBugfixNameFromBranch(String branchName){
        return branchName.substring(branchName.indexOf(bugfixPrefix)+bugfixPrefix.length(),branchName.length());
    }

    public String getRemoteNameFromBranch(String branchName){
        return branchName.substring(0,branchName.indexOf("/"));
    }

    public void setMasterBranch(String branchName)
    {
        masterBranch = branchName;
        VirtualFile root = repo.getRoot();
        try {
            GitConfigUtil.setValue(project, root, BRANCH_MASTER, branchName);
        } catch (VcsException e) {
            NotifyUtil.notifyError(project, "Config error", e);
        }
    }

    public void setDevelopBranch(String branchName) {
        developBranch = branchName;
        VirtualFile root = repo.getRoot();
        try {
            GitConfigUtil.setValue(project, root, BRANCH_DEVELOP, branchName);
        } catch (VcsException e) {
            NotifyUtil.notifyError(project, "Config error", e);
        }
    }

    public void setReleasePrefix(String prefix) {
        releasePrefix = prefix;
        VirtualFile root = repo.getRoot();

        try {
            GitConfigUtil.setValue(project, root, PREFIX_RELEASE, prefix);
        } catch (VcsException e) {
            NotifyUtil.notifyError(project, "Config error", e);
        }
    }

    public void setFeaturePrefix(String prefix) {
        featurePrefix = prefix;
        VirtualFile root = repo.getRoot();

        try {
            GitConfigUtil.setValue(project, root, PREFIX_FEATURE, prefix);
        } catch (VcsException e) {
            NotifyUtil.notifyError(project, "Config error", e);
        }
    }

    public void setHotfixPrefix(String prefix) {
        hotfixPrefix = prefix;
        VirtualFile root = repo.getRoot();

        try {
            GitConfigUtil.setValue(project, root, PREFIX_HOTFIX, prefix);
        } catch (VcsException e) {
            NotifyUtil.notifyError(project, "Config error", e);
        }
    }

    public void setBugfixPrefix(String prefix) {
        bugfixPrefix = prefix;
        VirtualFile root = repo.getRoot();

        try {
            GitConfigUtil.setValue(project, root, PREFIX_BUGFIX, prefix);
        } catch (VcsException e) {
            NotifyUtil.notifyError(project, "Config error", e);
        }
    }

    public void setSupportPrefix(String prefix) {
        supportPrefix = prefix;
        VirtualFile root = repo.getRoot();

        try {
            GitConfigUtil.setValue(project, root, PREFIX_SUPPORT, prefix);
        } catch (VcsException e) {
            NotifyUtil.notifyError(project, "Config error", e);
        }
    }

    public void setVersionPrefix(String prefix) {
        versiontagPrefix = prefix;
        VirtualFile root = repo.getRoot();

        try {
            GitConfigUtil.setValue(project, root, PREFIX_VERSIONTAG, prefix);
        } catch (VcsException e) {
            NotifyUtil.notifyError(project, "Config error", e);
        }
    }

    // this still reads from config because it always runs from an action and never from the EDT
    public String getBaseBranch(String branchName){

        VirtualFile root = repo.getRoot();

        String baseBranch=null;
        try{
            baseBranch = GitConfigUtil.getValue(project, root, "gitflow.branch."+branchName+".base");
        }
        catch (VcsException e) {
            NotifyUtil.notifyError(project, "Config error", e);
        }

        if (baseBranch == null) {
            // git-flow-next stores the start point per topic type, not per branch.
            if (featurePrefix != null && branchName.startsWith(featurePrefix)) {
                baseBranch = topicBase("feature");
            } else if (bugfixPrefix != null && branchName.startsWith(bugfixPrefix)) {
                baseBranch = topicBase("bugfix");
            } else if (releasePrefix != null && branchName.startsWith(releasePrefix)) {
                baseBranch = topicBase("release");
            } else if (hotfixPrefix != null && branchName.startsWith(hotfixPrefix)) {
                baseBranch = topicBase("hotfix");
            }
        }

        return baseBranch;
    }

    private String topicBase(String type) {
        String startpoint = propertyOf(nextBranches, type, "startpoint");
        if (startpoint != null && !startpoint.isEmpty()) {
            return startpoint;
        }
        String parent = propertyOf(nextBranches, type, "parent");
        if (parent != null && !parent.isEmpty()) {
            return parent;
        }
        return ("feature".equals(type) || "bugfix".equals(type)) ? developBranch : masterBranch;
    }
}
