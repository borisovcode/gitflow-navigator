package gitflow;

/**
 * Translates the canonical option flags used by the UI into the form accepted
 * by the detected git-flow CLI.
 *
 * <p>git-flow AVH uses short flags for fetch and push ({@code -F}, {@code -p}),
 * while git-flow-next accepts only the long forms ({@code --fetch},
 * {@code --push}).</p>
 */
public final class GitflowFlagMapper {

    private GitflowFlagMapper() {
    }

    public static String map(GitflowCliEdition edition, String optionId, String flag) {
        if (flag == null) {
            return null;
        }
        if (edition == GitflowCliEdition.NEXT) {
            if ("-F".equals(flag)) {
                return "--fetch";
            }
            if ("-p".equals(flag)) {
                return "--push";
            }
        } else if (edition == GitflowCliEdition.AVH) {
            if ("--fetch".equals(flag)) {
                return "-F";
            }
            if ("--push".equals(flag) && isReleaseOrHotfix(optionId)) {
                return "-p";
            }
        }
        return flag;
    }

    private static boolean isReleaseOrHotfix(String optionId) {
        return optionId != null
                && (optionId.startsWith("RELEASE_") || optionId.startsWith("HOTFIX_"));
    }
}
