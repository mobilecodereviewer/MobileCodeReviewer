package pl.edu.agh.mobilecodereviewer.dao.gerrit.utilities;

import com.google.common.base.Function;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.LinkedList;

import retrofit.RestAdapter;
import retrofit.client.Client;
import retrofit.client.Header;
import retrofit.client.Request;
import retrofit.client.Response;
import retrofit.mime.TypedInput;


public class GerritTestHelper {

    public static Function<Request,Void> doNothing = new Function<Request, Void>() {
        @Override
        public Void apply(Request from) {
            return null;
        }
    };

    public static TypedInput createServerAnswer(final String httpMessage) {
        return new TypedInput() {
            @Override
            public String mimeType() {
                return "application/json";
            }

            @Override
            public long length() {
                return httpMessage.length();
            }

            @Override
            public InputStream in() throws IOException {
                return new ByteArrayInputStream(httpMessage.getBytes() );
            }
        };
    }

    public static Response createResponse(final String url, int status, String reason, final String httpMessage) {
        return new Response(
                url,
                status,
                reason,
                new LinkedList<Header>(),
                createServerAnswer(httpMessage)
        );
    }

    public static Client createClient(final Function<Request, Void> requestValidator, final String httpMessage) {
        return new Client() {
            @Override
            public Response execute(Request request) throws IOException {
                requestValidator.apply(request);

                return createResponse("",200,"",httpMessage);
            }
        };
    }

    public static RestAdapter createRestAdapter(final Client client) {
        return new RestAdapter.Builder()
                .setClient(client)
                .setLogLevel(RestAdapter.LogLevel.NONE)
                .setEndpoint("http://0.0.0.0:8080")
                .build();
    }

    public static <T> T createSimpleRestServiceForTest(Class<T> objclass,
                                                       final Function<Request, Void> requestValidator,
                                                       final String httpMessage) {

        RestAdapter restAdapter = createRestAdapter( createClient(requestValidator,httpMessage)  );
        return restAdapter.create(objclass);
    }
}
