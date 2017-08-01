package com.waze.planned_drive;

public interface PlannedDriveListListener {
    void onPlannedDriveEtaResponse(String str, int i);

    void onPlannedDriveRemoveFailed();

    void onPlannedDriveRemoveSuccess();
}
