package models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class SwitcherView{
    public String _id;
    public String viewType;
    public boolean enabled;
}
