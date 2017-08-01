package com.waze.messages;

import com.waze.ifs.ui.ActivityBase;
import com.waze.messages.MessagesNativeManager.EditorContext;
import com.waze.user.UserData;

public class UserMessage {
    public static void startPrivate(ActivityBase $r0, UserData $r1) throws  {
        MessagesNativeManager.getInstance().startPrivate(new EditorContext($r0, 1, $r1));
    }

    public static void startPublic(ActivityBase $r0, UserData $r1) throws  {
        MessagesNativeManager.getInstance().startPublic(new EditorContext($r0, 0, $r1));
    }
}
