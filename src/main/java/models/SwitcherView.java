package models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class SwitcherView{
    @JsonProperty
    public String _id;
    @JsonProperty
    public String viewType;
    @JsonProperty
    public boolean enabled;
}
