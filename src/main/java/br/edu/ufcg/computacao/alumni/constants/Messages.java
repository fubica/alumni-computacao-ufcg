package br.edu.ufcg.computacao.alumni.constants;

public class Messages {
    public static class Exception {
        public static final String OPERATION_FAILED_S = "Operation failed: HTTP code [%d].";
    }

    public static class Fatal {
    }

    public static class Warn {
    }

    public static class Info {
        public static final String HTTP_RESPONSE = "HTTP response code [%s].";
    }

    public static class Error {
        public static final String CONFIGURATION_FILE_NOT_FOUND = "Configuration file not found.";
        public static final String INTERNAL_SERVER_ERROR = "Internal server error.";
        public static final String UNABLE_TO_CLOSE_FILE_S = "Unable to close file [%s].";
        public static final String ERROR_READING_CONFIGURATION_FILE = "Error reading configuration file";
        public static final String INVALID_STATE_S_S = "Invalid state: [%s]!=[%s].";
    }
}
