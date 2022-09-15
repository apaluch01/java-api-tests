package models;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class BoardInfo {
    public String id;
    public String name;
    public String desc;
    public Object descData;
    public boolean closed;
    public String idOrganization;
    public Object idEnterprise;
    public boolean pinned;
    public String url;
    public String shortUrl;
    Prefs prefs;
    LabelNames labelNames;
    Limits limits;
}
