package pl.edu.agh.mobilecodereviewer.view.activities.utilities;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.Spinner;

public class SavedConfigurationsCustomSpinner extends Spinner {

    public SavedConfigurationsCustomSpinner(Context context)
    { super(context); }

    public SavedConfigurationsCustomSpinner(Context context, AttributeSet attrs)
    { super(context, attrs); }

    public SavedConfigurationsCustomSpinner(Context context, AttributeSet attrs, int defStyle)
    { super(context, attrs, defStyle); }

    @Override
    public void setSelection(int position)
    {
        boolean sameSelected = position == getSelectedItemPosition();
        super.setSelection(position);
        if (sameSelected) {
            getOnItemSelectedListener().onItemSelected(this, getSelectedView(), position, getSelectedItemId());
        }
    }

}
