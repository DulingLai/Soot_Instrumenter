package android.support.v4.app;

abstract class BaseFragmentActivityEclair extends BaseFragmentActivityDonut {
    BaseFragmentActivityEclair() throws  {
    }

    void onBackPressedNotHandled() throws  {
        super.onBackPressed();
    }
}
