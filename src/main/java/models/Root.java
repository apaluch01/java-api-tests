package models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class Root{
    @JsonProperty
    public String id;
    @JsonProperty
    public String name;
    @JsonProperty
    public String desc;
    @JsonProperty
    public Object descData;
    @JsonProperty
    public boolean closed;
    @JsonProperty
    public String idOrganization;
    @JsonProperty
    public Object idEnterprise;
    @JsonProperty
    public boolean pinned;
    @JsonProperty
    public String url;
    @JsonProperty
    public String shortUrl;
    @JsonProperty
    public Prefs prefs;
    @JsonProperty
    public LabelNames labelNames;
    @JsonProperty
    public Limits limits;
}
