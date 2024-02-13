package group8;

import com.google.gson.Gson;
import org.apache.commons.io.IOUtils;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;


/**
 * Parses GitHub webhook push events which are JSON into native java object using Gson dependency. The structure of class
 * represents the layout of the received json data.
 *
 * To parse the webhook json data use the {@link #readJsonFromRequest(HttpServletRequest) readJsonFromRequest} method or
 * {@link #readJsonFromString(String) readJsonFromString} method. Additionally, the API exposes functions to retrieve
 * relevant necessary data.
 *
 * @author Atheer Salim
 * */
public class JsonRequest {
    private String ref;
    private String after;

    private repository repository;

    class repository {
        private String clone_url;;
        private String name;

        private owner owner;

        class owner {
            private String name;

        }
    }

    /**
     * Retrieves the branch name of the webhook request if the push events was made on a branch and not on a tag.
     *
     * @author Atheer Salim
     * @return the branch name of the webhook request
     * @throws IllegalArgumentException if the webhook push event was made on a tag
     */
    public String getBranchName() {
        String[] ref_split = ref.split("/");

        if (ref_split[1].equals("tags")) {
            System.err.println("The CI server does not support tags");
            throw new IllegalArgumentException("tags are not supported");
        }

        return ref_split[ref_split.length - 1];
    }

    /**
     * Retrieves the commit SHA hash of the most recent commit after the push event was made
     *
     * @author Atheer Salim
     * @return the commit SHA hash
     */
    public String getCommitId() {
        return after;
    }

    /**
     * Retrieves the https clone url of the repository
     *
     * @author Atheer Salim
     * @return the clone url of repository
     */
    public String getRepoCloneUrl() {
        return repository.clone_url;
    }


    /**
     * Retrieves the name of the repository
     *
     * @author Atheer Salim
     * @return name of the repository
     */
    public String getRepoName() {
        return repository.name;
    }

    /**
     * Retrieves the name of the owner for the repository
     *
     * @author Atheer Salim
     * @return owner name of repository
     */
    public String getOwnerName() {
        return repository.owner.name;
    }


    /**
     * Takes in the request that was made to the server and converts the HTTP body assumed to be in JSON format into
     * the JsonRequest object.
     *
     * @author Atheer Salim
     * @param request the HTTP request made to the server
     * @return JsonRequest the native java object of the json request
     */
    public static JsonRequest readJsonFromRequest(HttpServletRequest request) throws IOException {
        String json = IOUtils.toString(request.getReader());

        Gson gson = new Gson();
        return gson.fromJson(json, JsonRequest.class);
    }

    /**
     * Takes in the JSON data in string format, assumed to be valid and converts it into the JsonRequest object.
     *
     * @author Atheer Salim
     * @param json the json data
     * @return JsonRequest the native java object of the json request
     */
    protected static JsonRequest readJsonFromString(String json) {
        Gson gson = new Gson();
        return gson.fromJson(json, JsonRequest.class);
    }
}


