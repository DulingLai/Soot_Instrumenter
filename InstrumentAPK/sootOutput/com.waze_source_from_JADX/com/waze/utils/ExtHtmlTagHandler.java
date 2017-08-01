package com.waze.utils;

import android.text.Editable;
import android.text.Html.TagHandler;
import android.text.style.StrikethroughSpan;
import org.xml.sax.XMLReader;

public class ExtHtmlTagHandler implements TagHandler {
    public void handleTag(boolean opening, String tag, Editable output, XMLReader xmlReader) {
        if (tag.equalsIgnoreCase("strike") || tag.equals("s")) {
            processStrike(opening, output);
        }
    }

    private void processStrike(boolean opening, Editable output) {
        int len = output.length();
        if (opening) {
            output.setSpan(new StrikethroughSpan(), len, len, 17);
            return;
        }
        Object obj = getLast(output, StrikethroughSpan.class);
        int where = output.getSpanStart(obj);
        output.removeSpan(obj);
        if (where != len) {
            output.setSpan(new StrikethroughSpan(), where, len, 33);
        }
    }

    private Object getLast(Editable text, Class kind) {
        Object[] objs = text.getSpans(0, text.length(), kind);
        if (objs.length == 0) {
            return null;
        }
        for (int i = objs.length; i > 0; i--) {
            if (text.getSpanFlags(objs[i - 1]) == 17) {
                return objs[i - 1];
            }
        }
        return null;
    }
}
