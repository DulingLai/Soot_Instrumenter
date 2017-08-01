package com.waze.reports;

import android.text.Editable;
import android.text.TextWatcher;
import com.waze.ifs.ui.PointsView;
import java.util.regex.Pattern;

public class PointsViewTextWatcher implements TextWatcher {
    private final ValidatedPointsViewsMgr _mgr;
    private String _orig;
    private final int _pts;
    private final PointsView _ptsView;
    private TextValidator _validator = null;

    public interface ValidatedPointsViewsMgr {
        void addValidatedPointsView(PointsView pointsView);

        void onEdit();

        void updatePoints(int i);
    }

    public interface TextValidator {
        boolean isTextValid(String str);
    }

    public static class PaternValidator implements TextValidator {
        final boolean _emptyOk;
        final Pattern _pattern;

        public PaternValidator(String pattern, boolean emptyOk) {
            if (pattern == null) {
                this._pattern = null;
            } else {
                this._pattern = Pattern.compile(pattern, 2);
            }
            this._emptyOk = emptyOk;
        }

        public boolean isTextValid(String s) {
            if (this._emptyOk && s.isEmpty()) {
                return true;
            }
            return this._pattern.matcher(s.trim()).matches();
        }
    }

    public static class EmailValidator extends PaternValidator {
        private static final String EMAIL_PATTERN = "(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])";

        public EmailValidator(boolean emptyOk) {
            super(EMAIL_PATTERN, emptyOk);
        }
    }

    public static class HasContentValidator implements TextValidator {
        public boolean isTextValid(String s) {
            return !s.isEmpty();
        }
    }

    public static class LengthValidator implements TextValidator {
        final boolean _emptyOk;
        final int _minLength;

        public LengthValidator(int minLength, boolean emptyOk) {
            this._minLength = minLength;
            this._emptyOk = emptyOk;
        }

        public boolean isTextValid(String s) {
            if ((!this._emptyOk || !s.isEmpty()) && s.length() < this._minLength) {
                return false;
            }
            return true;
        }
    }

    public PointsViewTextWatcher(ValidatedPointsViewsMgr mgr, PointsView ptsView, int pts, TextValidator v, String originalText) {
        boolean z = true;
        this._mgr = mgr;
        this._ptsView = ptsView;
        boolean hidePoints = false;
        if (!(originalText == null || originalText.isEmpty())) {
            hidePoints = true;
        }
        this._pts = pts;
        this._orig = originalText;
        if (originalText == null) {
            this._orig = "";
        }
        this._validator = v;
        if (v == null) {
            this._ptsView.setValid(true);
        } else {
            this._mgr.addValidatedPointsView(ptsView);
            this._ptsView.setValid(v.isTextValid(this._orig));
        }
        this._ptsView.setPoints(pts, hidePoints);
        PointsView pointsView = this._ptsView;
        if (this._orig.isEmpty()) {
            z = false;
        }
        pointsView.setIsOn(false, z, false);
    }

    public void onTextChanged(CharSequence s, int start, int before, int count) {
    }

    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
    }

    public void afterTextChanged(Editable s) {
        boolean wasEdited;
        boolean hasContent;
        boolean isOn = false;
        int ptsMod = 0;
        if (this._ptsView.isOn()) {
            ptsMod = 0 - this._pts;
        }
        boolean isValid = true;
        if (this._validator != null) {
            isValid = this._validator.isTextValid(s.toString());
            this._ptsView.setValid(isValid);
        }
        if (this._orig.contentEquals(s)) {
            wasEdited = false;
        } else {
            wasEdited = true;
        }
        if (wasEdited) {
            this._mgr.onEdit();
        }
        if (s.length() > 0) {
            hasContent = true;
        } else {
            hasContent = false;
        }
        if (!(wasEdited && isValid)) {
            isOn = true;
        }
        this._ptsView.setIsOn(isOn, hasContent, true);
        if (isOn) {
            ptsMod += this._pts;
        }
        this._mgr.updatePoints(ptsMod);
    }
}
