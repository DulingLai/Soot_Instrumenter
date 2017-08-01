package android.support.v7.app;

import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;

public class AppCompatDialogFragment extends DialogFragment {
    public Dialog onCreateDialog(Bundle savedInstanceState) throws  {
        return new AppCompatDialog(getActivity(), getTheme());
    }

    public void setupDialog(Dialog $r1, int $i0) throws  {
        if ($r1 instanceof AppCompatDialog) {
            AppCompatDialog $r3 = (AppCompatDialog) $r1;
            switch ($i0) {
                case 1:
                case 2:
                    break;
                case 3:
                    $r1.getWindow().addFlags(24);
                    break;
                default:
                    return;
            }
            $r3.supportRequestWindowFeature(1);
            return;
        }
        super.setupDialog($r1, $i0);
    }
}
