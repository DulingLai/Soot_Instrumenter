package com.waze.planned_drive;

public interface PlannedDriveOptionsListener {
    void onPlannedDriveCreationFailed();

    void onPlannedDriveCreationSuccess();

    void onPlannedDriveOptionsLoaded(int[] iArr, int[] iArr2, boolean z);

    void onPlannedDriveUpdatedFailed();

    void onPlannedDriveUpdatedSuccess();
}
