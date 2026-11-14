package gitflow;

import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.diagnostic.Logger;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.util.Disposer;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class GitflowVersionTester {

	private static final Logger logger = Logger.getInstance(GitflowVersionTester.class);

	private static final Map<Project, GitflowVersionTester> testers = new ConcurrentHashMap<>();

	public static GitflowVersionTester forProject(@NotNull Project project) {
		return testers.computeIfAbsent(
			project,
			p -> {
				Disposer.register(p, () -> testers.remove(p));
				return new GitflowVersionTester(ApplicationManager.getApplication().getService(Gitflow.class), p);
			}
		);
	}

	/**
	 * Returns the CLI edition, detecting it (synchronously) on first use.
	 * Callers must not invoke this on the EDT.
	 */
	@NotNull
	public static GitflowCliEdition getEdition(@NotNull Project project) {
		GitflowVersionTester tester = forProject(project);
		if (!tester.hasVersionBeenTested()) {
			tester.init();
		}
		return tester.getEdition();
	}

	@NotNull private final Gitflow gitflow;
	@NotNull private final Project project;

	private volatile String version = null;

	private GitflowVersionTester(@NotNull Gitflow gitflow, @NotNull Project project) {
		this.gitflow = gitflow;
		this.project = project;
	}

	/**
	 * <p>Returns the installed {@code git-flow} version. The version
	 * is loaded on the first call to this method and is determined
	 * by looking at the output of a {@code git flow version} command.</p>
	 * <p>If the command fails, {@code null} is returned.</p>
	 *
	 * @return the {@code git flow} version, or {@code null}
	 */
	@Nullable
	public String getVersion() {
		return version;
	}

	/**
	 * Returns which CLI implementation produced the version string.
	 * git-flow-next prints e.g. {@code 2.1.0 (git-flow-next)}, while
	 * git-flow-avh prints e.g. {@code 1.12.3 (AVH Edition)}.
	 */
	@NotNull
	public GitflowCliEdition getEdition() {
		return GitflowCliEdition.detect(version);
	}

	/**
	 * Returns true if the {@code git flow} CLI is either git-flow-avh or
	 * git-flow-next.
	 */
	public boolean isSupportedVersion() {
		return getEdition() != GitflowCliEdition.UNKNOWN;
	}

	public synchronized void init(){
		if (version != null) {
			return;
		}
		String returnedVersion = null;
		try {
			returnedVersion = gitflow.version(project).getOutputOrThrow();
			logger.info("git flow version: " + returnedVersion);
		} catch (Exception e) {
			logger.error("Could not determine git flow version", e);
		}
		if (returnedVersion != null){
			version = returnedVersion;
		}
	}
	
	public boolean hasVersionBeenTested(){
		return version != null;
	}
}
