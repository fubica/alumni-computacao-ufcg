package br.edu.ufcg.computacao.alumni.constants;

public class Messages {
    public static class Exception {
    }

    public static class Fatal {
    }

    public static class Warn {
    }

    public static class Info {
    }

    public static class Error {
        public static final String CONFIGURATION_FILE_NOT_FOUND = "Configuration file not found.";
        public static final String INTERNAL_SERVER_ERROR = "Internal server error.";
        public static final String UNABLE_TO_CLOSE_FILE_S = "Unable to close file [%s].";
        public static final Object ERROR_READING_CONFIGURATION_FILE = "Error reading configuration file";
    }
}
