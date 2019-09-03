package com.ice.restring;

import android.content.res.Resources;
import android.util.AttributeSet;
import android.view.View;

import com.google.android.material.textfield.TextInputLayout;

class TextInputLayoutTransformer implements ViewTransformerManager.Transformer {

    private static final String ATTRIBUTE_HINT = "hint";
    private static final String ATTRIBUTE_ANDROID_HINT = "android:hint";

    @Override
    public Class<? extends View> getViewType() {
        return TextInputLayout.class;
    }

    @Override
    public View transform(View view, AttributeSet attrs) {
        if (view == null || !getViewType().isInstance(view)) {
            return view;
        }
        Resources resources = view.getContext().getResources();

        for (int index = 0; index < attrs.getAttributeCount(); index++) {
            String attributeName = attrs.getAttributeName(index);
            String value;
            switch (attributeName) {
                case ATTRIBUTE_ANDROID_HINT:
                case ATTRIBUTE_HINT:
                    value = attrs.getAttributeValue(index);
                    if (value != null && value.startsWith("@")) {
                        setHintForView(view, resources.getString(
                                attrs.getAttributeResourceValue(index, 0)));

                    }
                    break;
                default:
                    break;
            }
        }
        return view;
    }

    private void setHintForView(View view, String text) {
        ((TextInputLayout) view).setHint(text);
    }
}
