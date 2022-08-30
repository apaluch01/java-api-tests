package models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;

public class BoardInfo {
    @Getter @Setter
    public static class LabelNames{
        @JsonProperty
        public String green;
        @JsonProperty
        public String yellow;
        @JsonProperty
        public String orange;
        @JsonProperty
        public String red;
        @JsonProperty
        public String purple;
        @JsonProperty
        public String blue;
        @JsonProperty
        public String sky;
        @JsonProperty
        public String lime;
        @JsonProperty
        public String pink;
        @JsonProperty
        public String black;
        @JsonProperty
        public String green_dark;
        @JsonProperty
        public String yellow_dark;
        @JsonProperty
        public String orange_dark;
        @JsonProperty
        public String red_dark;
        @JsonProperty
        public String purple_dark;
        @JsonProperty
        public String blue_dark;
        @JsonProperty
        public String sky_dark;
        @JsonProperty
        public String lime_dark;
        @JsonProperty
        public String pink_dark;
        @JsonProperty
        public String black_dark;
        @JsonProperty
        public String green_light;
        @JsonProperty
        public String yellow_light;
        @JsonProperty
        public String orange_light;
        @JsonProperty
        public String red_light;
        @JsonProperty
        public String purple_light;
        @JsonProperty
        public String blue_light;
        @JsonProperty
        public String sky_light;
        @JsonProperty
        public String lime_light;
        @JsonProperty
        public String pink_light;
        @JsonProperty
        public String black_light;
    }

    @Getter @Setter
    public static class Limits{
    }

    @Getter @Setter
    public static class Prefs{
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

    @Getter @Setter
    public static class Root{
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

    @Getter @Setter
    public static class SwitcherView{
        @JsonProperty
        public String _id;
        @JsonProperty
        public String viewType;
        @JsonProperty
        public boolean enabled;
    }
}
