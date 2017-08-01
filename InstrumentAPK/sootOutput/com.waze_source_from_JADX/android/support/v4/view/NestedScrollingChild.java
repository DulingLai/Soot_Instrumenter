package android.support.v4.view;

public interface NestedScrollingChild {
    boolean dispatchNestedFling(float f, float f2, boolean z) throws ;

    boolean dispatchNestedPreFling(float f, float f2) throws ;

    boolean dispatchNestedPreScroll(int i, int i2, int[] iArr, int[] iArr2) throws ;

    boolean dispatchNestedScroll(int i, int i2, int i3, int i4, int[] iArr) throws ;

    boolean hasNestedScrollingParent() throws ;

    boolean isNestedScrollingEnabled() throws ;

    void setNestedScrollingEnabled(boolean z) throws ;

    boolean startNestedScroll(int i) throws ;

    void stopNestedScroll() throws ;
}
