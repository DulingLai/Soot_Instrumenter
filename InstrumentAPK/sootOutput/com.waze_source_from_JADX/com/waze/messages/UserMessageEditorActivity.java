package com.waze.messages;

import android.content.DialogInterface;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.TextView;
import com.waze.AppService;
import com.waze.C1283R;
import com.waze.Logger;
import com.waze.MsgBox;
import com.waze.NativeManager;
import com.waze.ifs.ui.ActivityBase;
import com.waze.strings.DisplayStrings;
import com.waze.user.UserData;
import com.waze.view.title.TitleBar;

public class UserMessageEditorActivity extends ActivityBase {
    private EditText mEditorView = null;
    private boolean mIsPrivate = false;
    private OnSendHandler mOnSendHandler = null;
    private final TextWatcher mTextWatcher = new C19773();
    private TitleBar mTitleBar;
    private UserData mUserData = null;

    class C19751 implements OnClickListener {
        C19751() throws  {
        }

        public void onClick(View v) throws  {
            if (AppService.isNetworkAvailable()) {
                String $r5 = UserMessageEditorActivity.this.mEditorView.getText().toString();
                if (!$r5.isEmpty()) {
                    if (UserMessageEditorActivity.this.mOnSendHandler != null) {
                        UserMessageEditorActivity.this.mOnSendHandler.onSend(UserMessageEditorActivity.this.mIsPrivate, UserMessageEditorActivity.this.mUserData, $r5);
                    }
                    UserMessageEditorActivity.this.setResult(3);
                    UserMessageEditorActivity.this.finish();
                    return;
                }
                return;
            }
            UserMessageEditorActivity.this.showErrorPopup(DisplayStrings.DS_SENDING_PING_FAILED__PLEASE_TRY_AGAIN_LATER);
        }
    }

    class C19762 implements DialogInterface.OnClickListener {
        C19762() throws  {
        }

        public void onClick(DialogInterface dialog, int which) throws  {
            UserMessageEditorActivity.this.setResult(3);
            UserMessageEditorActivity.this.finish();
        }
    }

    class C19773 implements TextWatcher {
        C19773() throws  {
        }

        public void onTextChanged(CharSequence s, int start, int before, int count) throws  {
        }

        public void beforeTextChanged(CharSequence s, int start, int count, int after) throws  {
        }

        public void afterTextChanged(Editable $r1) throws  {
            if ($r1.length() > 0) {
                UserMessageEditorActivity.this.mTitleBar.setCloseTextColor(114, 210, 238);
            } else {
                UserMessageEditorActivity.this.mTitleBar.setCloseTextColor(119, 119, 119);
            }
        }
    }

    private void setUserDetails() throws  {
        /* JADX: method processing error */
/*
Error: jadx.core.utils.exceptions.JadxRuntimeException: Unreachable block: B:22:0x01c1
	at jadx.core.dex.visitors.blocksmaker.BlockProcessor.modifyBlocksTree(BlockProcessor.java:248)
	at jadx.core.dex.visitors.blocksmaker.BlockProcessor.processBlocksTree(BlockProcessor.java:52)
	at jadx.core.dex.visitors.blocksmaker.BlockProcessor.visit(BlockProcessor.java:38)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:31)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:17)
	at jadx.core.ProcessClass.process(ProcessClass.java:37)
	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:306)
	at jadx.api.JavaClass.decompile(JavaClass.java:62)
	at jadx.api.JadxDecompiler$1.run(JadxDecompiler.java:199)
*/
        /*
        r37 = this;
        r7 = 2131692543; // 0x7f0f0bff float:1.901419E38 double:1.053196053E-314;
        r0 = r37;
        r6 = r0.findViewById(r7);
        r9 = r6;
        r9 = (android.widget.TextView) r9;
        r8 = r9;
        r7 = 2131692538; // 0x7f0f0bfa float:1.9014179E38 double:1.0531960505E-314;
        r0 = r37;
        r6 = r0.findViewById(r7);
        r11 = r6;
        r11 = (android.widget.ImageView) r11;
        r10 = r11;
        r7 = 2131692540; // 0x7f0f0bfc float:1.9014183E38 double:1.0531960515E-314;
        r0 = r37;
        r6 = r0.findViewById(r7);
        r13 = r6;
        r13 = (android.widget.ImageView) r13;
        r12 = r13;
        r7 = 2131692539; // 0x7f0f0bfb float:1.901418E38 double:1.053196051E-314;
        r0 = r37;
        r6 = r0.findViewById(r7);
        r15 = r6;
        r15 = (android.widget.ImageView) r15;
        r14 = r15;
        r7 = 2131692545; // 0x7f0f0c01 float:1.9014193E38 double:1.053196054E-314;
        r0 = r37;
        r6 = r0.findViewById(r7);
        r17 = r6;
        r17 = (android.widget.TextView) r17;
        r16 = r17;
        r7 = 2131692547; // 0x7f0f0c03 float:1.9014197E38 double:1.053196055E-314;
        r0 = r37;
        r6 = r0.findViewById(r7);
        r19 = r6;
        r19 = (android.widget.ImageView) r19;
        r18 = r19;
        r7 = 2131692549; // 0x7f0f0c05 float:1.9014201E38 double:1.053196056E-314;
        r0 = r37;
        r6 = r0.findViewById(r7);
        r21 = r6;
        r21 = (android.widget.ImageView) r21;
        r20 = r21;
        r7 = 2131692542; // 0x7f0f0bfe float:1.9014187E38 double:1.0531960525E-314;
        r0 = r37;
        r6 = r0.findViewById(r7);
        r23 = r6;
        r23 = (android.widget.ImageView) r23;
        r22 = r23;
        r7 = 2131692544; // 0x7f0f0c00 float:1.9014191E38 double:1.0531960535E-314;
        r0 = r37;
        r6 = r0.findViewById(r7);
        r7 = 2131692541; // 0x7f0f0bfd float:1.9014185E38 double:1.053196052E-314;
        r0 = r37;
        r24 = r0.findViewById(r7);
        r7 = 2131692546; // 0x7f0f0c02 float:1.9014195E38 double:1.0531960545E-314;
        r0 = r37;
        r25 = r0.findViewById(r7);
        r7 = 2131692548; // 0x7f0f0c04 float:1.90142E38 double:1.0531960555E-314;
        r0 = r37;
        r26 = r0.findViewById(r7);
        r27 = com.waze.NativeManager.getInstance();
        r0 = r37;
        r0 = r0.mUserData;
        r28 = r0;
        r0 = r0.mNickName;
        r29 = r0;
        r8.setText(r0);
        r0 = r37;
        r0 = r0.mUserData;
        r28 = r0;
        r0 = r0.mMood;
        r29 = r0;
        r0 = r37;
        r1 = r29;
        r30 = com.waze.MoodManager.getMoodDrawable(r0, r1);
        r0 = r30;
        r10.setImageDrawable(r0);
        r0 = r37;
        r0 = r0.mUserData;
        r28 = r0;
        r0 = r0.mAddonName;
        r29 = r0;
        if (r29 == 0) goto L_0x01e9;
    L_0x00c8:
        r0 = r37;
        r0 = r0.mUserData;
        r28 = r0;
        r0 = r0.mAddonName;
        r29 = r0;
        r31 = r0.length();
        if (r31 <= 0) goto L_0x01e9;
    L_0x00d8:
        r7 = 0;
        r14.setVisibility(r7);
        r32 = new java.lang.StringBuilder;
        r0 = r32;
        r0.<init>();
        r0 = r37;
        r0 = r0.mUserData;
        r28 = r0;
        r0 = r0.mAddonName;
        r29 = r0;
        r0 = r32;
        r1 = r29;
        r32 = r0.append(r1);
        r33 = ".png";
        r0 = r32;
        r1 = r33;
        r32 = r0.append(r1);
        r0 = r32;
        r29 = r0.toString();
        r0 = r29;
        r30 = com.waze.ResManager.GetSkinDrawable(r0);
        r0 = r30;
        r14.setImageDrawable(r0);
    L_0x0110:
        r7 = 8;
        r0 = r25;
        r0.setVisibility(r7);
        r7 = 8;
        r0 = r26;
        r0.setVisibility(r7);
        r0 = r37;
        r0 = r0.mUserData;
        r28 = r0;
        r0 = r0.mIsFbFriend;
        r34 = r0;
        if (r34 == 0) goto L_0x01ff;
    L_0x012a:
        r0 = r37;
        r0 = r0.mUserData;
        r28 = r0;
        r0 = r0.mFaceBookNickName;
        r29 = r0;
        r31 = r0.length();
        if (r31 <= 0) goto L_0x01ef;
    L_0x013a:
        r0 = r37;
        r0 = r0.mUserData;
        r28 = r0;
        r0 = r0.mFaceBookNickName;
        r29 = r0;
        r0 = r16;
        r1 = r29;
        r0.setText(r1);
    L_0x014b:
        r0 = r37;
        r0 = r0.mUserData;
        r28 = r0;
        r0 = r0.mShowGroupIcon;
        r34 = r0;
        if (r34 == 0) goto L_0x02cc;
    L_0x0157:
        r32 = new java.lang.StringBuilder;
        r0 = r32;
        r0.<init>();
        r0 = r37;
        r0 = r0.mUserData;
        r28 = r0;
        r0 = r0.mGroupIcon;
        r29 = r0;
        r0 = r32;
        r1 = r29;
        r32 = r0.append(r1);
        r33 = ".png";
        r0 = r32;
        r1 = r33;
        r32 = r0.append(r1);
        goto L_0x017e;
    L_0x017b:
        goto L_0x0110;
    L_0x017e:
        r0 = r32;
        r29 = r0.toString();
        r0 = r29;
        r30 = com.waze.ResManager.GetSkinDrawable(r0);
        r7 = 0;
        r0 = r22;
        r0.setVisibility(r7);
        r0 = r22;
        r1 = r30;
        r0.setImageDrawable(r1);
    L_0x0197:
        r0 = r37;
        r0 = r0.mUserData;
        r28 = r0;
        r0 = r0.mIsFbFriend;
        r34 = r0;
        goto L_0x01a5;
    L_0x01a2:
        goto L_0x014b;
    L_0x01a5:
        if (r34 != 0) goto L_0x01b3;
    L_0x01a7:
        r0 = r37;
        r0 = r0.mUserData;
        r28 = r0;
        r0 = r0.mShowFacebookPicture;
        r34 = r0;
        if (r34 == 0) goto L_0x02d4;
    L_0x01b3:
        r7 = 8;
        r10.setVisibility(r7);
        r7 = 8;
        r14.setVisibility(r7);
        goto L_0x01c5;
    L_0x01be:
        goto L_0x014b;
        goto L_0x01c5;
    L_0x01c2:
        goto L_0x014b;
    L_0x01c5:
        r7 = 8;
        r0 = r22;
        r0.setVisibility(r7);
        r35 = com.waze.utils.ImageRepository.instance;
        r0 = r37;
        r0 = r0.mUserData;
        r28 = r0;
        r29 = r0.getImage();
        r7 = 1;
        r36 = 0;
        r0 = r35;
        r1 = r29;
        r2 = r7;
        r3 = r12;
        r4 = r36;
        r5 = r37;
        r0.getImage(r1, r2, r3, r4, r5);
        return;
    L_0x01e9:
        r7 = 8;
        r14.setVisibility(r7);
        goto L_0x017b;
    L_0x01ef:
        r7 = 776; // 0x308 float:1.087E-42 double:3.834E-321;
        r0 = r27;
        r29 = r0.getLanguageString(r7);
        r0 = r16;
        r1 = r29;
        r0.setText(r1);
        goto L_0x01a2;
    L_0x01ff:
        r0 = r37;
        r0 = r0.mUserData;
        r28 = r0;
        r0 = r0.mNumFriendsOfFriends;
        r31 = r0;
        if (r31 != 0) goto L_0x0237;
    L_0x020b:
        r0 = r37;
        r0 = r0.mUserData;
        r28 = r0;
        r0 = r0.mFaceBookNickName;
        r29 = r0;
        if (r29 == 0) goto L_0x0231;
    L_0x0217:
        r0 = r29;
        r31 = r0.length();
        if (r31 <= 0) goto L_0x0231;
    L_0x021f:
        r0 = r37;
        r0 = r0.mUserData;
        r28 = r0;
        r0 = r0.mFaceBookNickName;
        r29 = r0;
        r0 = r16;
        r1 = r29;
        r0.setText(r1);
        goto L_0x01be;
    L_0x0231:
        r7 = 8;
        r6.setVisibility(r7);
        goto L_0x01c2;
    L_0x0237:
        r32 = new java.lang.StringBuilder;
        r0 = r32;
        r0.<init>();
        r7 = 777; // 0x309 float:1.089E-42 double:3.84E-321;
        r0 = r27;
        r29 = r0.getLanguageString(r7);
        r0 = r32;
        r1 = r29;
        r32 = r0.append(r1);
        r33 = " ";
        r0 = r32;
        r1 = r33;
        r32 = r0.append(r1);
        r0 = r32;
        r29 = r0.toString();
        r0 = r16;
        r1 = r29;
        r0.setText(r1);
        r0 = r37;
        r0 = r0.mUserData;
        r28 = r0;
        r0 = r0.mFriend1Url;
        r29 = r0;
        if (r29 == 0) goto L_0x0294;
    L_0x0271:
        r7 = 0;
        r0 = r25;
        r0.setVisibility(r7);
        r35 = com.waze.utils.ImageRepository.instance;
        r0 = r37;
        r0 = r0.mUserData;
        r28 = r0;
        r0 = r0.mFriend1Url;
        r29 = r0;
        r7 = 1;
        r36 = 0;
        r0 = r35;
        r1 = r29;
        r2 = r7;
        r3 = r18;
        r4 = r36;
        r5 = r37;
        r0.getImage(r1, r2, r3, r4, r5);
    L_0x0294:
        r0 = r37;
        r0 = r0.mUserData;
        r28 = r0;
        r0 = r0.mFriend2Url;
        r29 = r0;
        if (r29 == 0) goto L_0x014b;
    L_0x02a0:
        r7 = 0;
        r0 = r26;
        r0.setVisibility(r7);
        r35 = com.waze.utils.ImageRepository.instance;
        r0 = r37;
        r0 = r0.mUserData;
        r28 = r0;
        r0 = r0.mFriend2Url;
        r29 = r0;
        goto L_0x02b6;
    L_0x02b3:
        goto L_0x014b;
    L_0x02b6:
        r7 = 1;
        r36 = 0;
        r0 = r35;
        r1 = r29;
        r2 = r7;
        r3 = r20;
        r4 = r36;
        r5 = r37;
        r0.getImage(r1, r2, r3, r4, r5);
        goto L_0x02b3;
        goto L_0x02cc;
    L_0x02c9:
        goto L_0x0197;
    L_0x02cc:
        r7 = 8;
        r0 = r22;
        r0.setVisibility(r7);
        goto L_0x02c9;
    L_0x02d4:
        r7 = 8;
        r0 = r24;
        r0.setVisibility(r7);
        r7 = 8;
        r12.setVisibility(r7);
        return;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.waze.messages.UserMessageEditorActivity.setUserDetails():void");
    }

    protected void onCreate(Bundle $r1) throws  {
        super.onCreate($r1);
        setContentView(C1283R.layout.user_message_editor);
        this.mIsPrivate = getIntent().getBooleanExtra(MessagesConstants.EXTRA_ID_IS_PRIVATE_MSG, false);
        this.mUserData = (UserData) getIntent().getSerializableExtra(MessagesConstants.EXTRA_ID_USER_DATA);
        this.mOnSendHandler = (OnSendHandler) getIntent().getSerializableExtra(MessagesConstants.EXTRA_ID_ON_SEND_HANDLER);
        if (this.mUserData == null) {
            Logger.e_(MessagesConstants.LOG_TAG, "There is no user data in editor request");
            return;
        }
        setEditor();
        setDisclaimer();
        setTitle();
        setPhone();
        setUserDetails();
    }

    private void setPhone() throws  {
        String $r1 = this.mUserData.mPhone;
        TextView $r4 = (TextView) findViewById(C1283R.id.userMsgEditorPhone);
        View $r3 = findViewById(C1283R.id.userMsgEditorPhoneContainer);
        if ($r1 == null || $r1.length() == 0) {
            $r3.setVisibility(8);
        } else {
            $r4.setText($r1);
        }
    }

    private void setEditor() throws  {
        this.mEditorView = (EditText) findViewById(C1283R.id.userMsgEditorText);
        this.mEditorView.setHint(NativeManager.getInstance().getLanguageString((int) DisplayStrings.DS_ADD_A_MESSAGE_OPTIONAL));
        this.mEditorView.addTextChangedListener(this.mTextWatcher);
    }

    private void setDisclaimer() throws  {
        String $r2;
        if (this.mIsPrivate) {
            $r2 = NativeManager.getInstance().getLanguageString((int) DisplayStrings.DS_MESSAGES_ARE_PRIVATE);
        } else {
            $r2 = NativeManager.getInstance().getLanguageString(154);
        }
        ((TextView) findViewById(C1283R.id.userMsgEditorDisclaimerText)).setText($r2);
    }

    private void setTitle() throws  {
        String $r2;
        if (this.mIsPrivate) {
            $r2 = NativeManager.getInstance().getLanguageString((int) DisplayStrings.DS_MESSAGE);
        } else {
            $r2 = NativeManager.getInstance().getLanguageString((int) DisplayStrings.DS_PING);
        }
        String $r3 = NativeManager.getInstance().getLanguageString(293);
        this.mTitleBar = (TitleBar) findViewById(C1283R.id.userMsgEditorTitleBar);
        this.mTitleBar.init(this, $r2, $r3);
        this.mTitleBar.setCloseTextColor(119, 119, 119);
        this.mTitleBar.setOnClickCloseListener(new C19751());
    }

    private void showErrorPopup(int $i0) throws  {
        MsgBox.openMessageBoxWithCallback(NativeManager.getInstance().getLanguageString((int) DisplayStrings.DS_UHHOHE), NativeManager.getInstance().getLanguageString($i0), false, new C19762());
    }
}
