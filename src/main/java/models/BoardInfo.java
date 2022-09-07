package models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;

public class BoardInfo {
    LabelNames labelNames;
    Limits limits;
    Prefs prefs;
    Root root;
    SwitcherView switcherView;
}
