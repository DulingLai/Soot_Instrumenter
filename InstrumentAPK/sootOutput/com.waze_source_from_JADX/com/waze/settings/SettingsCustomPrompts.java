package com.waze.settings;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.Adapter;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.waze.AppService;
import com.waze.C1283R;
import com.waze.ConfigManager;
import com.waze.MsgBox;
import com.waze.analytics.AnalyticsBuilder;
import com.waze.analytics.AnalyticsEvents;
import com.waze.ifs.ui.ActivityBase;
import com.waze.map.CanvasFont;
import com.waze.settings.SettingsNativeManager.SettingsVoiceDataValuesListener;
import com.waze.strings.DisplayStrings;
import com.waze.view.title.TitleBar;
import com.waze.voice.CustomPromptManager;
import com.waze.voice.CustomPromptManager.PromptDefinition;
import com.waze.voice.RecordYourselfPopup;
import com.waze.voice.RecordYourselfPopup.RecordYourselfListener;
import com.waze.voice.VoiceData;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class SettingsCustomPrompts extends ActivityBase {
    public static final String ACTION_TAG = "voice_rec";
    public static final int DEFAULT_VOICE_INDEX = 1;
    private static final Object DELETE_ALL_ITEM = new Object();
    private static final int ITEM_TYPE_ACTION = 3;
    private static final int ITEM_TYPE_PROMPT = 1;
    private static final int ITEM_TYPE_SECTION = 0;
    private static final int ITEM_TYPE_SWITCH = 2;
    private static final int REQUEST_CODE_RECORD_AUDIO_PERMISSION = 4325;
    private static final int REQUEST_CODE_SELECT_FALLBACK = 63821;
    private static final Object SET_FALLBACK_ITEM = new Object();
    private List<Object> mDataSource;
    private RecordYourselfPopup mRecordYourselfPopup;
    private RecyclerView mRecycler;

    private class ActionViewHolder extends ViewHolder {
        private TextView mLabel;
        private TextView mSubtitle;

        class C26681 implements OnClickListener {

            class C26671 implements DialogInterface.OnClickListener {
                C26671() {
                }

                public void onClick(DialogInterface dialog, int which) {
                    if (which == 1) {
                        CustomPromptManager.getInstance().deleteAllPrompts();
                        AnalyticsBuilder.analytics(AnalyticsEvents.ANALYTICS_EVENT_CUSTOM_PROMPTS_ALL_DELETED).send();
                        SettingsCustomPrompts.this.mRecycler.getAdapter().notifyDataSetChanged();
                    }
                }
            }

            C26681() {
            }

            public void onClick(View v) {
                MsgBox.openConfirmDialogJavaCallback(DisplayStrings.displayString(DisplayStrings.DS_CUSTOM_PROMPTS_REMOVE_ALL_TITLE), "", false, new C26671(), DisplayStrings.displayString(DisplayStrings.DS_REMOVE), DisplayStrings.displayString(344), -1);
            }
        }

        class C26702 implements SettingsVoiceDataValuesListener {
            C26702() {
            }

            public void onComplete(VoiceData[] values) {
                if (values != null) {
                    for (VoiceData voiceData : values) {
                        if (voiceData.bIsSelected) {
                            final String subtitleLabel = voiceData.Name;
                            ActionViewHolder.this.mSubtitle.post(new Runnable() {
                                public void run() {
                                    ActionViewHolder.this.mSubtitle.setText(subtitleLabel);
                                    ActionViewHolder.this.mSubtitle.setVisibility(0);
                                }
                            });
                            return;
                        }
                    }
                }
            }
        }

        class C26713 implements OnClickListener {
            C26713() {
            }

            public void onClick(View v) {
                Intent fallbackSoundIntent = new Intent(SettingsCustomPrompts.this, SettingsNavigationGuidanceActivity.class);
                fallbackSoundIntent.putExtra(SettingsNavigationGuidanceActivity.EXTRA_FILTER_ONLY_PROMPTS, true);
                AnalyticsBuilder.analytics(AnalyticsEvents.ANALYTICS_EVENT_CUSTOM_PROMPTS_FALLBACK_CHOSEN).send();
                SettingsCustomPrompts.this.startActivityForResult(fallbackSoundIntent, SettingsCustomPrompts.REQUEST_CODE_SELECT_FALLBACK);
            }
        }

        public ActionViewHolder(View itemView) {
            super(itemView);
            this.mLabel = (TextView) itemView.findViewById(C1283R.id.lblActionName);
            this.mSubtitle = (TextView) itemView.findViewById(C1283R.id.lblSubtitle);
        }

        public void bind(Object action) {
            if (action == SettingsCustomPrompts.DELETE_ALL_ITEM) {
                this.mLabel.setTextColor(SettingsCustomPrompts.this.getResources().getColor(C1283R.color.RedSweet));
                this.mLabel.setText(DisplayStrings.displayString(DisplayStrings.DS_CUSTOM_PROMPTS_REMOVE_ALL));
                this.mSubtitle.setVisibility(8);
                this.itemView.setOnClickListener(new C26681());
            } else if (action == SettingsCustomPrompts.SET_FALLBACK_ITEM) {
                this.mLabel.setTextColor(SettingsCustomPrompts.this.getResources().getColor(C1283R.color.DarkBlue));
                this.mLabel.setText(DisplayStrings.displayString(DisplayStrings.DS_CUSTOM_PROMPTS_FALLBACK_VOICE));
                this.mSubtitle.setVisibility(8);
                SettingsNativeManager.getInstance().getVoices(new C26702());
                this.itemView.setOnClickListener(new C26713());
            }
        }
    }

    private class CategoryViewHolder extends ViewHolder {
        private TextView mTitleLabel;

        public CategoryViewHolder(View itemView) {
            super(itemView);
            this.mTitleLabel = (TextView) itemView.findViewById(C1283R.id.lblTitle);
        }

        public void bind(String title) {
            this.mTitleLabel.setText(title);
        }
    }

    private class PromptViewHolder extends ViewHolder {
        private PromptDefinition mData;
        private ImageView mDeleteButton;
        private TextView mDurationLabel;
        private LinearLayout mItemLabelContainer;
        private TextView mNameLabel;
        private ImageView mPreviewButton;

        public PromptViewHolder(View itemView) {
            super(itemView);
            this.mNameLabel = (TextView) itemView.findViewById(C1283R.id.lblItemName);
            this.mDeleteButton = (ImageView) itemView.findViewById(C1283R.id.imgDeleteItem);
            this.mPreviewButton = (ImageView) itemView.findViewById(C1283R.id.imgPreviewItem);
            this.mDurationLabel = (TextView) itemView.findViewById(C1283R.id.lblMaxDuration);
            this.mItemLabelContainer = (LinearLayout) itemView.findViewById(C1283R.id.itemLabelContainer);
            itemView.setOnClickListener(new OnClickListener(SettingsCustomPrompts.this) {

                class C26731 implements RecordYourselfListener {

                    class C26721 implements Runnable {
                        C26721() {
                        }

                        public void run() {
                            SettingsCustomPrompts.this.mRecycler.getAdapter().notifyDataSetChanged();
                        }
                    }

                    C26731() {
                    }

                    public void onRecordingComplete() {
                        SettingsCustomPrompts.this.postDelayed(new C26721(), 200);
                        SettingsCustomPrompts.this.mRecordYourselfPopup = null;
                    }
                }

                public void onClick(View v) {
                    if (SettingsCustomPrompts.this.mRecordYourselfPopup != null || !ConfigManager.getInstance().getConfigValueBool(415)) {
                        return;
                    }
                    if (ContextCompat.checkSelfPermission(AppService.getActiveActivity(), "android.permission.RECORD_AUDIO") == 0 && ContextCompat.checkSelfPermission(AppService.getActiveActivity(), "android.permission.WRITE_EXTERNAL_STORAGE") == 0) {
                        CustomPromptManager.getInstance().initialize(SettingsCustomPrompts.this);
                        SettingsCustomPrompts.this.mRecordYourselfPopup = RecordYourselfPopup.showRecordYourselfPopup(SettingsCustomPrompts.this, PromptViewHolder.this.mData, new C26731());
                        return;
                    }
                    ActivityCompat.requestPermissions(AppService.getActiveActivity(), new String[]{"android.permission.RECORD_AUDIO", "android.permission.WRITE_EXTERNAL_STORAGE"}, SettingsCustomPrompts.REQUEST_CODE_RECORD_AUDIO_PERMISSION);
                }
            });
            this.mDeleteButton.setOnClickListener(new OnClickListener(SettingsCustomPrompts.this) {
                public void onClick(View v) {
                    if (ConfigManager.getInstance().getConfigValueBool(415)) {
                        CustomPromptManager.getInstance().deletePrompt(PromptViewHolder.this.mData.getId(), false);
                        SettingsCustomPrompts.this.mRecycler.getAdapter().notifyDataSetChanged();
                        AnalyticsBuilder.analytics(AnalyticsEvents.ANALYTICS_EVENT_CUSTOM_PROMPTS_DELETED).addParam("ACTION", PromptViewHolder.this.mData.getId()).send();
                    }
                }
            });
            this.mPreviewButton.setOnClickListener(new OnClickListener(SettingsCustomPrompts.this) {
                public void onClick(View v) {
                    if (ConfigManager.getInstance().getConfigValueBool(415)) {
                        CustomPromptManager.getInstance().previewPrompt(PromptViewHolder.this.mData.getId(), false);
                        AnalyticsBuilder.analytics(AnalyticsEvents.ANALYTICS_EVENT_CUSTOM_PROMPTS_PREVIEWED).addParam("ACTION", PromptViewHolder.this.mData.getId()).send();
                    }
                }
            });
        }

        public void bind(PromptDefinition data) {
            this.mData = data;
            this.mNameLabel.setText(data.getDisplayText());
            this.mDurationLabel.setText(String.format(Locale.US, DisplayStrings.displayString(DisplayStrings.DS_CUSTOM_PROMPTS_X_SECONDS_MAX), new Object[]{Integer.valueOf(this.mData.getMaxSeconds())}));
            boolean userEnabled = ConfigManager.getInstance().getConfigValueBool(415);
            if (userEnabled && CustomPromptManager.getInstance().doesFileExist(this.mData.getId(), false)) {
                this.mDeleteButton.setVisibility(0);
                this.mPreviewButton.setVisibility(0);
            } else {
                this.mDeleteButton.setVisibility(4);
                this.mPreviewButton.setVisibility(4);
            }
            if (userEnabled) {
                this.mItemLabelContainer.setAlpha(1.0f);
            } else {
                this.mItemLabelContainer.setAlpha(CanvasFont.OUTLINE_GLYPH_HEIGHT_FACTOR);
            }
        }
    }

    private class PromptsAdapter extends Adapter<ViewHolder> {
        private PromptsAdapter() {
        }

        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            if (viewType == 2) {
                return new SwitchViewHolder(LayoutInflater.from(SettingsCustomPrompts.this).inflate(C1283R.layout.custom_prompts_switch_item, null));
            }
            if (viewType == 0) {
                return new CategoryViewHolder(LayoutInflater.from(SettingsCustomPrompts.this).inflate(C1283R.layout.custom_prompt_section_title, null));
            }
            if (viewType == 3) {
                return new ActionViewHolder(LayoutInflater.from(SettingsCustomPrompts.this).inflate(C1283R.layout.custom_prompt_action_item, null));
            }
            return new PromptViewHolder(LayoutInflater.from(SettingsCustomPrompts.this).inflate(C1283R.layout.custom_prompt_item_view, null));
        }

        public void onBindViewHolder(ViewHolder holder, int position) {
            if (position > 0) {
                Object dataItem = SettingsCustomPrompts.this.mDataSource.get(position - 1);
                if (holder instanceof CategoryViewHolder) {
                    ((CategoryViewHolder) holder).bind((String) dataItem);
                    return;
                } else if (holder instanceof PromptViewHolder) {
                    ((PromptViewHolder) holder).bind((PromptDefinition) dataItem);
                    return;
                } else if (holder instanceof ActionViewHolder) {
                    ((ActionViewHolder) holder).bind(dataItem);
                    return;
                } else {
                    return;
                }
            }
            ((SwitchViewHolder) holder).refresh();
        }

        public int getItemViewType(int position) {
            if (position == 0) {
                return 2;
            }
            Object dataItem = SettingsCustomPrompts.this.mDataSource.get(position - 1);
            if (dataItem instanceof String) {
                return 0;
            }
            if (dataItem instanceof PromptDefinition) {
                return 1;
            }
            return 3;
        }

        public int getItemCount() {
            return SettingsCustomPrompts.this.mDataSource.size() + 1;
        }
    }

    private class SwitchViewHolder extends ViewHolder {
        private TextView mDescriptionLabel;
        private WazeSettingsView mSwitchView;

        public SwitchViewHolder(View itemView) {
            super(itemView);
            this.mSwitchView = (WazeSettingsView) itemView.findViewById(C1283R.id.customPromptsSwitch);
            this.mDescriptionLabel = (TextView) itemView.findViewById(C1283R.id.lblPromptsSwitchDescription);
            this.mDescriptionLabel.setText(DisplayStrings.displayString(DisplayStrings.DS_CUSTOM_PROMPTS_SWITCH_DESCRIPTION));
            this.mSwitchView.setText((int) DisplayStrings.DS_CUSTOM_PROMPTS_SWITCH_TITLE);
            refresh();
            this.mSwitchView.setOnClickListener(new OnClickListener(SettingsCustomPrompts.this) {

                class C26771 implements DialogInterface.OnClickListener {
                    C26771() {
                    }

                    public void onClick(DialogInterface dialog, int which) {
                        if (which == 1) {
                            ConfigManager.getInstance().setConfigValueBool(415, true);
                            SettingsNativeManager.getInstance().setVoice(1);
                            SwitchViewHolder.this.mSwitchView.setValue(true);
                            AnalyticsBuilder.analytics(AnalyticsEvents.ANALYTICS_EVENT_CUSTOM_PROMPTS_SWITCH_ON).send();
                            SettingsCustomPrompts.this.mRecycler.getAdapter().notifyDataSetChanged();
                        }
                    }
                }

                public void onClick(View v) {
                    if (ConfigManager.getInstance().getConfigValueBool(415)) {
                        ConfigManager.getInstance().setConfigValueBool(415, false);
                        SwitchViewHolder.this.mSwitchView.setValue(false);
                        SettingsCustomPrompts.this.mRecycler.getAdapter().notifyDataSetChanged();
                        AnalyticsBuilder.analytics(AnalyticsEvents.ANALYTICS_EVENT_CUSTOM_PROMPTS_SWITCH_OFF).send();
                        return;
                    }
                    MsgBox.openConfirmDialogJavaCallback(DisplayStrings.displayString(DisplayStrings.DS_CUSTOM_PROMPTS_SAFETY_WARNING_TITLE), DisplayStrings.displayString(DisplayStrings.DS_CUSTOM_PROMPTS_SAFETY_WARNING_DESCRIPTION), false, new C26771(), DisplayStrings.displayString(322), DisplayStrings.displayString(344), -1);
                }
            });
        }

        public void refresh() {
            this.mSwitchView.setValue(ConfigManager.getInstance().getConfigValueBool(415));
        }
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(C1283R.layout.settings_custom_prompts);
        buildDataSource();
        ((TitleBar) findViewById(C1283R.id.theTitleBar)).init((Activity) this, DisplayStrings.displayString(DisplayStrings.DS_CUSTOM_PROMPTS_TITLE));
        this.mRecycler = (RecyclerView) findViewById(C1283R.id.promptsRecycler);
        this.mRecycler.setLayoutManager(new LinearLayoutManager(this));
        this.mRecycler.setAdapter(new PromptsAdapter());
        AnalyticsBuilder.analytics(AnalyticsEvents.ANALYTICS_EVENT_CUSTOM_PROMPTS_SCREEN_ENTERED).send();
        CustomPromptManager.getInstance().initialize(this);
        SettingsNativeManager.getInstance().getVoices(null);
    }

    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_CODE_RECORD_AUDIO_PERMISSION && grantResults.length > 1 && grantResults[0] != 0 && grantResults[1] != 0) {
            finish();
        }
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_SELECT_FALLBACK) {
            this.mRecycler.getAdapter().notifyDataSetChanged();
        }
    }

    private void buildDataSource() {
        int i = 0;
        String unitSystemName = ConfigManager.getInstance().getConfigValueString(200);
        boolean isMetric;
        if (unitSystemName == null || !unitSystemName.equals("metric")) {
            isMetric = false;
        } else {
            isMetric = true;
        }
        PromptDefinition[] promptDefinitions = CustomPromptManager.getInstance().getPromptDefinitions();
        this.mDataSource = new ArrayList();
        String prevCategory = "";
        int length = promptDefinitions.length;
        while (i < length) {
            PromptDefinition prompt = promptDefinitions[i];
            if ((prompt.getMode() != 1 || isMetric) && !(prompt.getMode() == 2 && isMetric)) {
                if (!prevCategory.equals(prompt.getCategory())) {
                    this.mDataSource.add(prompt.getCategory());
                    prevCategory = prompt.getCategory();
                }
                this.mDataSource.add(prompt);
            }
            i++;
        }
        this.mDataSource.add("");
        this.mDataSource.add(SET_FALLBACK_ITEM);
        this.mDataSource.add("");
        this.mDataSource.add(DELETE_ALL_ITEM);
        this.mDataSource.add("");
    }

    public void onBackPressed() {
        if (this.mRecordYourselfPopup != null) {
            this.mRecordYourselfPopup.hide();
        } else {
            super.onBackPressed();
        }
    }
}
