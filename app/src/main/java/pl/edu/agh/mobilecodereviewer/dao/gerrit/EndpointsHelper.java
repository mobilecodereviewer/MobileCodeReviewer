package pl.edu.agh.mobilecodereviewer.dao.gerrit;

import java.util.Properties;

public class EndpointsHelper {

    public static enum EndpointProperties {
        CHANGE_ID("_CHANGE_ID_");

        String value;

        EndpointProperties(String value) {
            this.value = value;
        }

        public String valueOf() {
            return value;
        }
    }

    public static enum Endpoints {
        CHANGES("/changes"), CHANGE_DETAILS("/changes/" + EndpointProperties.CHANGE_ID.valueOf());

        private String value;

        Endpoints(String value) {
            this.value = value;
        }

        public String valueOf() {
            return value;
        }
    }

    public static String createEndpointFromProperties(Endpoints endpoint, Properties prop) {

        String convertedEndpoint = endpoint.valueOf();

        for (Object key : prop.keySet()) {
            convertedEndpoint = convertedEndpoint.replaceAll(((EndpointProperties) key).valueOf(), (String) prop.get(key));
        }

        return convertedEndpoint;
    }
}
