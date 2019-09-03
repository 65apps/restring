package com.ice.restring;

import android.content.Context;
import android.content.res.Resources;
import android.util.AttributeSet;
import android.view.View;

import androidx.recyclerview.widget.RecyclerView;
import androidx.test.core.app.ApplicationProvider;

import com.google.android.material.textfield.TextInputLayout;
import com.ice.restring.application.TestApplication;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verifyZeroInteractions;
import static org.mockito.Mockito.when;

@RunWith(RobolectricTestRunner.class)
@Config(application = TestApplication.class)
public class TextInputLayoutTransformerTest {

    private static final int HINT_ATTR_INDEX = 2;
    private static final int HINT_RES_ID = 0x7f0f0124;
    private static final String HINT_ATTR_KEY = "hint";
    private static final String HINT_ATTR_VALUE = "HINT_ATTR_VALUE";

    private TextInputLayoutTransformer transformer;

    @Before
    public void setUp() {
        transformer = new TextInputLayoutTransformer();
    }

    @Test
    public void shouldTransformTextInputLayout() {
        Context context = getContext();

        View view = transformer.transform(new TextInputLayout(context), getAttributeSet(false));

        assertTrue(view instanceof TextInputLayout);
        assertEquals(((TextInputLayout) view).getHint(), HINT_ATTR_VALUE);

        view = transformer.transform(new TextInputLayout(context), getAttributeSet(true));

        assertTrue(view instanceof TextInputLayout);
        assertEquals(((TextInputLayout) view).getHint(), HINT_ATTR_VALUE);
    }

    @Test
    public void shouldTransformExtendedViews() {
        Context context = getContext();

        View view = transformer.transform(new ExtendedTextInputLayout(context), getAttributeSet(false));

        assertTrue(view instanceof ExtendedTextInputLayout);
        assertEquals(((ExtendedTextInputLayout) view).getHint(), HINT_ATTR_VALUE);

        view = transformer.transform(new ExtendedTextInputLayout(context), getAttributeSet(true));

        assertTrue(view instanceof ExtendedTextInputLayout);
        assertEquals(((ExtendedTextInputLayout) view).getHint(), HINT_ATTR_VALUE);
    }

    @Test
    public void shouldRejectOtherViewTypes() {
        Context context = getContext();
        AttributeSet attributeSet = getAttributeSet(false);
        RecyclerView recyclerView = new RecyclerView(context);

        View view = transformer.transform(recyclerView, attributeSet);

        assertSame(view, recyclerView);
        verifyZeroInteractions(attributeSet);
    }

    private Context getContext() {
        Context context = Mockito.spy(ApplicationProvider.getApplicationContext());
        Resources resources = Mockito.spy(context.getResources());

        doReturn(resources).when(context).getResources();
        doReturn(HINT_ATTR_VALUE).when(resources).getString(HINT_RES_ID);

        return context;
    }

    private AttributeSet getAttributeSet(boolean withAndroidPrefix) {
        AttributeSet attributeSet = Mockito.mock(AttributeSet.class);
        when(attributeSet.getAttributeCount()).thenReturn(HINT_ATTR_INDEX + 2);

        when(attributeSet.getAttributeName(anyInt())).thenReturn("other_attribute");
        when(attributeSet.getAttributeName(HINT_ATTR_INDEX)).thenReturn((withAndroidPrefix ? "android:" : "") + HINT_ATTR_KEY);
        when(attributeSet.getAttributeValue(HINT_ATTR_INDEX)).thenReturn("@" + HINT_RES_ID);
        when(attributeSet.getAttributeResourceValue(eq(HINT_ATTR_INDEX), anyInt())).thenReturn(HINT_RES_ID);

        return attributeSet;
    }

    private static final class ExtendedTextInputLayout extends TextInputLayout {

        public ExtendedTextInputLayout(Context context) {
            super(context);
        }

        public ExtendedTextInputLayout(Context context, AttributeSet attrs) {
            super(context, attrs);
        }

        public ExtendedTextInputLayout(Context context, AttributeSet attrs, int defStyleAttr) {
            super(context, attrs, defStyleAttr);
        }
    }
}
