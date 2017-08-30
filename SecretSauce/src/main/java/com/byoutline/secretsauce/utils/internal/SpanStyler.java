package com.byoutline.secretsauce.utils.internal;

import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.text.style.ImageSpan;
import android.widget.TextView;
import com.byoutline.secretsauce.utils.CustomClickableSpan;
import com.byoutline.secretsauce.utils.CustomTypefaceSpan;

import java.util.List;
import java.util.Map;

/**
 * @author Sebastian Kacprzak <sebastian.kacprzak at byoutline.com>
 */
public final class SpanStyler {
    private SpanStyler() {
    }

    public static void setStyledMsg(TextView styledTv, String source, String stylingText, CustomTypefaceSpan customSpan, ForegroundColorSpan foregroundDarkColor) {
        int interlocutorStartNamePosition = source.indexOf(stylingText);
        int interlocurotEndNamePos = interlocutorStartNamePosition + stylingText.length();
        if (!TextUtils.isEmpty(stylingText) && interlocutorStartNamePosition != -1) {
            SpannableStringBuilder styledText = new SpannableStringBuilder(source);
            styledText.setSpan(customSpan, interlocutorStartNamePosition, interlocurotEndNamePos, Spanned.SPAN_EXCLUSIVE_INCLUSIVE);
            styledText.setSpan(foregroundDarkColor, interlocutorStartNamePosition, interlocurotEndNamePos, Spannable.SPAN_INCLUSIVE_INCLUSIVE);
            styledTv.setText(styledText);
        } else {
            styledTv.setText(source);
        }
    }

    public static void setStyledMsg(TextView styledTv, String source, List<String> stylingTexts, List<CustomTypefaceSpan> customSpans, List<ForegroundColorSpan> foregroundDarkColors) {
        int i = 0;
        boolean formatted = false;
        SpannableStringBuilder styledText = new SpannableStringBuilder(source);
        CustomTypefaceSpan customSpan;
        ForegroundColorSpan foregroundColorSpan;

        for (String stylingText : stylingTexts) {
            int interlocutorStartNamePosition = source.indexOf(stylingText);
            int interlocurotEndNamePos = interlocutorStartNamePosition + stylingText.length();
            if (!TextUtils.isEmpty(stylingText) && interlocutorStartNamePosition != -1) {

                customSpan = customSpans.get(i);
                foregroundColorSpan = foregroundDarkColors.get(i);

                styledText.setSpan(customSpan, interlocutorStartNamePosition, interlocurotEndNamePos, Spanned.SPAN_EXCLUSIVE_INCLUSIVE);
                styledText.setSpan(foregroundColorSpan, interlocutorStartNamePosition, interlocurotEndNamePos, Spannable.SPAN_INCLUSIVE_INCLUSIVE);
                formatted = true;
                i++;
            }
        }
        if (formatted) {
            styledTv.setText(styledText);
        } else {
            styledTv.setText(source);
        }
    }

    public static SpannableStringBuilder getStyledText(String source, List<String> stylingTexts,
                                                       Map<String, CustomTypefaceSpan> typeFaceSpan,
                                                       Map<String, ForegroundColorSpan> textSpanColor,
                                                       Map<String, CustomClickableSpan> clickableSpans,
                                                       Map<String, ImageSpan> iconsSpans) {
        SpannableStringBuilder wholeStyleText = new SpannableStringBuilder(source);


        boolean customSpansEmpty = isNullOrEmpty(typeFaceSpan);
        boolean foregroundColorSpansEmpty = isNullOrEmpty(textSpanColor);
        boolean clickableSpansEmpty = isNullOrEmpty(clickableSpans);
        boolean iconSpanEmpty = isNullOrEmpty(iconsSpans);


        for (String stylingText : stylingTexts) {
            int textStartPos = source.indexOf(stylingText);
            int textEndPos = textStartPos + stylingText.length();
            if (!TextUtils.isEmpty(stylingText) && textStartPos != -1) {
                SetSpanDelegate delegate = new SetSpanDelegate(wholeStyleText, textStartPos, textEndPos);

                if (!customSpansEmpty && typeFaceSpan.containsKey(stylingText)) {
                    delegate.setSpan(typeFaceSpan.get(stylingText));
                }

                if (!foregroundColorSpansEmpty && textSpanColor.containsKey(stylingText)) {
                    delegate.setSpan(textSpanColor.get(stylingText));
                }

                if (!clickableSpansEmpty && clickableSpans.containsKey(stylingText)) {
                    delegate.setSpan(clickableSpans.get(stylingText));
                }

                if (!iconSpanEmpty && iconsSpans.containsKey(stylingText)) {
                    delegate.setSpan(iconsSpans.get(stylingText));
                }
            }
        }
        return wholeStyleText;
    }

    private static boolean isNullOrEmpty(final Map<?, ?> m) {
        return m == null || m.isEmpty();
    }
}
