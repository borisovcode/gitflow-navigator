package gitflow;

/**
 * Which git-flow CLI implementation is installed.
 */
public enum GitflowCliEdition {
    AVH,
    NEXT,
    UNKNOWN;

    /**
     * Detects the edition from the output of {@code git flow version}.
     * <ul>
     *   <li>git-flow AVH prints e.g. {@code 1.12.3 (AVH Edition)}</li>
     *   <li>git-flow-next prints e.g. {@code 2.1.0 (git-flow-next)}</li>
     * </ul>
     */
    public static GitflowCliEdition detect(String version) {
        if (version == null) {
            return UNKNOWN;
        }
        if (version.contains("git-flow-next")) {
            return NEXT;
        }
        if (version.contains("AVH")) {
            return AVH;
        }
        return UNKNOWN;
    }
}
