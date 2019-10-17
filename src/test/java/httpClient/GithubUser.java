package httpClient;

import com.google.api.client.util.Key;

public class GithubUser {

    @Key
    private String login;

    @Key
    private long id;

    @Key("html_url")
    private String htmlUrl;

    @Key("site_admin")
    private boolean site_admin;

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("GithubUser [login=").append(this.login).append(", id=").append(this.id).append(", htmlUrl=")
                .append(this.htmlUrl).append(", site_admin=").append(this.site_admin).append("]");
        return builder.toString();
    }

}
