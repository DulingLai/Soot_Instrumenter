package com.facebook.share.model;

import android.os.Parcel;
import dalvik.annotation.Signature;
import java.util.ArrayList;

public final class GameRequestContent implements ShareModel {
    private final ActionType actionType;
    private final String data;
    private final Filters filters;
    private final String message;
    private final String objectId;
    private final ArrayList<String> suggestions;
    private final String title;
    private final String to;

    public enum ActionType {
        SEND,
        ASKFOR,
        TURN
    }

    public static class Builder implements ShareModelBuilder<GameRequestContent, Builder> {
        private ActionType actionType;
        private String data;
        private Filters filters;
        private String message;
        private String objectId;
        private ArrayList<String> suggestions;
        private String title;
        private String to;

        public Builder setMessage(String $r1) throws  {
            this.message = $r1;
            return this;
        }

        public Builder setTo(String $r1) throws  {
            this.to = $r1;
            return this;
        }

        public Builder setData(String $r1) throws  {
            this.data = $r1;
            return this;
        }

        public Builder setTitle(String $r1) throws  {
            this.title = $r1;
            return this;
        }

        public Builder setActionType(ActionType $r1) throws  {
            this.actionType = $r1;
            return this;
        }

        public Builder setObjectId(String $r1) throws  {
            this.objectId = $r1;
            return this;
        }

        public Builder setFilters(Filters $r1) throws  {
            this.filters = $r1;
            return this;
        }

        public Builder setSuggestions(@Signature({"(", "Ljava/util/ArrayList", "<", "Ljava/lang/String;", ">;)", "Lcom/facebook/share/model/GameRequestContent$Builder;"}) ArrayList<String> $r1) throws  {
            this.suggestions = $r1;
            return this;
        }

        public GameRequestContent build() throws  {
            return new GameRequestContent();
        }

        public Builder readFrom(GameRequestContent $r0) throws  {
            return $r0 == null ? this : setMessage($r0.getMessage()).setTo($r0.getTo()).setTitle($r0.getTitle()).setData($r0.getData()).setActionType($r0.getActionType()).setObjectId($r0.getObjectId()).setFilters($r0.getFilters()).setSuggestions($r0.getSuggestions());
        }

        public Builder readFrom(Parcel $r1) throws  {
            return readFrom((GameRequestContent) $r1.readParcelable(GameRequestContent.class.getClassLoader()));
        }
    }

    public enum Filters {
        APP_USERS,
        APP_NON_USERS
    }

    public int describeContents() throws  {
        return 0;
    }

    private GameRequestContent(Builder $r1) throws  {
        this.message = $r1.message;
        this.to = $r1.to;
        this.title = $r1.title;
        this.data = $r1.data;
        this.actionType = $r1.actionType;
        this.objectId = $r1.objectId;
        this.filters = $r1.filters;
        this.suggestions = $r1.suggestions;
    }

    GameRequestContent(Parcel $r1) throws  {
        this.message = $r1.readString();
        this.to = $r1.readString();
        this.title = $r1.readString();
        this.data = $r1.readString();
        this.actionType = ActionType.valueOf($r1.readString());
        this.objectId = $r1.readString();
        this.filters = Filters.valueOf($r1.readString());
        this.suggestions = new ArrayList();
        $r1.readStringList(this.suggestions);
    }

    public String getMessage() throws  {
        return this.message;
    }

    public String getTo() throws  {
        return this.to;
    }

    public String getTitle() throws  {
        return this.title;
    }

    public String getData() throws  {
        return this.data;
    }

    public ActionType getActionType() throws  {
        return this.actionType;
    }

    public String getObjectId() throws  {
        return this.objectId;
    }

    public Filters getFilters() throws  {
        return this.filters;
    }

    public ArrayList<String> getSuggestions() throws  {
        return this.suggestions;
    }

    public void writeToParcel(Parcel $r1, int flags) throws  {
        $r1.writeString(this.message);
        $r1.writeString(this.to);
        $r1.writeString(this.title);
        $r1.writeString(this.data);
        $r1.writeString(getActionType().toString());
        $r1.writeString(getObjectId());
        $r1.writeString(getFilters().toString());
        $r1.writeStringList(getSuggestions());
    }
}
