package android.support.v4.app;

import android.os.Bundle;

class RemoteInputCompatBase {

    public static abstract class RemoteInput {

        public interface Factory {
            RemoteInput build(String str, CharSequence charSequence, CharSequence[] charSequenceArr, boolean z, Bundle bundle) throws ;

            RemoteInput[] newArray(int i) throws ;
        }

        protected abstract boolean getAllowFreeFormInput() throws ;

        protected abstract CharSequence[] getChoices() throws ;

        protected abstract Bundle getExtras() throws ;

        protected abstract CharSequence getLabel() throws ;

        protected abstract String getResultKey() throws ;
    }

    RemoteInputCompatBase() throws  {
    }
}
