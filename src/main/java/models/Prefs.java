package models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;

@Getter @Setter
public class Prefs{
    @JsonProperty
    public String permissionLevel;
    @JsonProperty
    public boolean hideVotes;
    @JsonProperty
    public String voting;
    @JsonProperty
    public String comments;
    @JsonProperty
    public String invitations;
    @JsonProperty
    public boolean selfJoin;
    @JsonProperty
    public boolean cardCovers;
    @JsonProperty
    public boolean isTemplate;
    @JsonProperty
    public String cardAging;
    @JsonProperty
    public boolean calendarFeedEnabled;
    @JsonProperty
    public ArrayList<Object> hiddenPluginBoardButtons;
    @JsonProperty
    public ArrayList<SwitcherView> switcherViews;
    @JsonProperty
    public String background;
    @JsonProperty
    public String backgroundColor;
    @JsonProperty
    public Object backgroundImage;
    @JsonProperty
    public Object backgroundImageScaled;
    @JsonProperty
    public boolean backgroundTile;
    @JsonProperty
    public String backgroundBrightness;
    @JsonProperty
    public String backgroundBottomColor;
    @JsonProperty
    public String backgroundTopColor;
    @JsonProperty
    public boolean canBePublic;
    @JsonProperty
    public boolean canBeEnterprise;
    @JsonProperty
    public boolean canBeOrg;
    @JsonProperty
    public boolean canBePrivate;
    @JsonProperty
    public boolean canInvite;
}
