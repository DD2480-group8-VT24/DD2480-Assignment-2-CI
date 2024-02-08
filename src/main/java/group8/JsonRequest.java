package group8;

import com.google.gson.Gson;
import org.apache.commons.io.IOUtils;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

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

    public String getBranchName() {
        String[] ref_split = ref.split("/");

        if (ref_split[1].equals("tags")) {
            System.err.println("The CI server does not support tags");
            throw new IllegalArgumentException("tags are not supported");
        }

        return ref_split[ref_split.length - 1];
    }

    public String getCommitId() {
        return after;
    }

    public String getRepoCloneUrl() {
        return repository.clone_url;
    }


    public String getRepoName() {
        return repository.name;
    }

    public String getOwnerName() {
        return repository.owner.name;
    }

    public static JsonRequest readJsonFromRequest(HttpServletRequest request) throws IOException {
        String json = IOUtils.toString(request.getReader());

        Gson gson = new Gson();
        return gson.fromJson(json, JsonRequest.class);
    }

    protected static JsonRequest readJsonFromString(String json) {
        Gson gson = new Gson();
        return gson.fromJson(json, JsonRequest.class);
    }
}


