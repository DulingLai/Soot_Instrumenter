package com.waze.navigate.social;

import android.content.Context;
import android.graphics.Bitmap;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.waze.AppService;
import com.waze.C1283R;
import com.waze.ifs.ui.ActivityBase;
import com.waze.ifs.ui.CircleShaderDrawable;
import com.waze.share.ShareUtility;
import com.waze.user.PersonBase;
import com.waze.utils.ImageRepository;
import com.waze.utils.ImageRepository.ImageRepositoryListener;
import com.waze.view.anim.MaterialDesignImageAnimationHelper;
import com.waze.view.anim.ViewPropertyAnimatorHelper;

public class AddFriendsUtils {
    public static View inflate(Context $r0, ViewGroup $r1) throws  {
        return ((LayoutInflater) $r0.getSystemService("layout_inflater")).inflate(C1283R.layout.add_friends_in_list, $r1, false);
    }

    public static void setNameAndImage(ActivityBase ab, View $r1, String $r2, String $r3) throws  {
        ImageView $r6 = (ImageView) $r1.findViewById(C1283R.id.addFriendsImage);
        TextView $r7 = (TextView) $r1.findViewById(C1283R.id.addFriendsName);
        TextView $r8 = (TextView) $r1.findViewById(C1283R.id.addFriendsInitials);
        if ($r2 == null || $r2.length() == 0) {
            $r7.setText("");
            $r8.setText("");
        } else {
            $r7.setText($r2);
            $r8.setText(ShareUtility.getInitials($r2));
        }
        $r8.setVisibility(0);
        $r8.animate().cancel();
        $r8.setAlpha(1.0f);
        $r6.setImageDrawable(null);
        $r6.animate().cancel();
        $r6.setAlpha(1.0f);
        $r6.setTag($r3);
        if (!TextUtils.isEmpty($r3)) {
            long $l1 = System.currentTimeMillis();
            final ImageView imageView = $r6;
            final String str = $r3;
            final long j = $l1;
            final TextView textView = $r8;
            ImageRepository.instance.getImage($r3, true, new ImageRepositoryListener() {
                public void onImageRetrieved(final Bitmap $r1) throws  {
                    if (imageView.getTag() == str) {
                        imageView.post(new Runnable() {
                            public void run() throws  {
                                if ($r1 != null) {
                                    CircleShaderDrawable $r1 = new CircleShaderDrawable($r1, 0);
                                    imageView.setImageDrawable($r1);
                                    if (System.currentTimeMillis() - j > 300) {
                                        MaterialDesignImageAnimationHelper.animateImageEntrance($r1, 1500);
                                        C21881.this.hideInitials(300);
                                        return;
                                    }
                                    C21881.this.hideInitials(0);
                                    return;
                                }
                                C21881.this.showInitials();
                            }
                        });
                    }
                }

                private void showInitials() throws  {
                    textView.setAlpha(1.0f);
                    textView.setVisibility(0);
                }

                private void hideInitials(long $l0) throws  {
                    ViewPropertyAnimatorHelper.initAnimation(textView, $l0).alpha(0.0f).setListener(ViewPropertyAnimatorHelper.createGoneWhenDoneListener(textView));
                }
            });
        }
    }

    public static View inflateFriendToken(PersonBase $r0, ViewGroup $r1) throws  {
        View $r5 = ((LayoutInflater) $r1.getContext().getSystemService("layout_inflater")).inflate(C1283R.layout.contacts_token, $r1, false);
        View view = (TextView) $r5.findViewById(C1283R.id.contactTokenInitials);
        view.setText(ShareUtility.getInitials($r0.getName()));
        ImageView imageView = (ImageView) $r5.findViewById(C1283R.id.contactTokenImage);
        ImageRepository.instance.getImage($r0.getImage(), 3, imageView, view, AppService.getActiveActivity());
        return $r5;
    }
}
