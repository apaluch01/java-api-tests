package models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class BoardInfo {
    @JsonProperty
    private String id;

    @JsonProperty
    private String name;

    @JsonProperty
    private String url;

    @JsonProperty
    private String shortUrl;
}
