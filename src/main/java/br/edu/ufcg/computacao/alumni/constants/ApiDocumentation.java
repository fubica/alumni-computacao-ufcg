package br.edu.ufcg.computacao.alumni.constants;

public class ApiDocumentation {
    public static class ApiInfo {
        public static final String CONTACT_NAME = "Computação@UFCG";
        public static final String CONTACT_URL = "https://computacao.ufcg.edu.br/";
        public static final String CONTACT_EMAIL = "fubica@computacao.ufcg.edu.br";
        public static final String API_TITLE = "Computação@UFCG Alumni Service API";
        public static final String API_DESCRIPTION = "This API allows management of information about Computação@UFCG alumni.";
    }

    public static class Model {
        public static final String MEMBERS_LIST = "[\n" +
                "    \"name1\",\n" +
                "    \"name2\"\n" +
                "  ]\n";
    }

    public static class Alumni {
        public static final String API = "Queries the names of the alumni.";
        public static final String DESCRIPTION = "Lists the names of the alumni.";
    }

    public static class Version {
        public static final String API = "Queries the version of the service's API.";
        public static final String GET_OPERATION = "Returns the version of the service's API.";
    }
}
