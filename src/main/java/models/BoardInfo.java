package models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;

public class BoardInfo {
    @JsonProperty("LabelNames")
    LabelNames labelNames;
    @JsonProperty("Limits")
    Limits limits;
    @JsonProperty("Prefs")
    Prefs prefs;
    @JsonProperty("Root")
    Root root;
    @JsonProperty("SwitcherView")
    SwitcherView switcherView;
}
