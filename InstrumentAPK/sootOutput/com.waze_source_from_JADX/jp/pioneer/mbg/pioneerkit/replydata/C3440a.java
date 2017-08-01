package jp.pioneer.mbg.pioneerkit.replydata;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class C3440a {
    public static long m786a(byte[] bArr) {
        DataInputStream dataInputStream = new DataInputStream(new ByteArrayInputStream(bArr));
        long j = -1;
        try {
            j = dataInputStream.readLong();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return j;
    }

    public static byte[] m787a(int i, TrackInfoReplyDataBase trackInfoReplyDataBase) {
        switch (i) {
            case 2:
                return C3440a.m788a(trackInfoReplyDataBase);
            case 3:
                return C3440a.m790b(trackInfoReplyDataBase);
            case 4:
                return C3440a.m791c(trackInfoReplyDataBase);
            case 5:
                return C3440a.m792d(trackInfoReplyDataBase);
            case 6:
                return C3440a.m793e(trackInfoReplyDataBase);
            default:
                return null;
        }
    }

    public static byte[] m788a(TrackInfoReplyDataBase trackInfoReplyDataBase) {
        if (trackInfoReplyDataBase instanceof TrackInfoReplyData) {
            TrackInfoReplyData trackInfoReplyData = (TrackInfoReplyData) trackInfoReplyDataBase;
            OutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            DataOutputStream dataOutputStream = new DataOutputStream(byteArrayOutputStream);
            try {
                dataOutputStream.writeByte(trackInfoReplyData.m779a());
                dataOutputStream.writeLong(trackInfoReplyData.getTrackToken());
                dataOutputStream.writeShort(trackInfoReplyData.getTrackNumber());
                dataOutputStream.writeShort(trackInfoReplyData.getDurationTime());
                return byteArrayOutputStream.toByteArray();
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
        }
        throw new IllegalArgumentException("param class type unmatched");
    }

    public static byte[] m789a(C3439b c3439b) {
        if (c3439b instanceof TrackSettingInfoReplyData) {
            TrackSettingInfoReplyData trackSettingInfoReplyData = (TrackSettingInfoReplyData) c3439b;
            OutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            DataOutputStream dataOutputStream = new DataOutputStream(byteArrayOutputStream);
            try {
                if (trackSettingInfoReplyData.m779a() != 1) {
                    return null;
                }
                dataOutputStream.writeByte(trackSettingInfoReplyData.m779a());
                if (trackSettingInfoReplyData.m782b()) {
                    dataOutputStream.writeByte(1);
                } else {
                    dataOutputStream.writeByte(0);
                }
                int i = trackSettingInfoReplyData.hasTrackInformation() ? (byte) 1 : (byte) 0;
                i = trackSettingInfoReplyData.hasTrackTitle() ? (byte) (i | 2) : (byte) (i & -1);
                i = trackSettingInfoReplyData.hasArtistName() ? (byte) (i | 4) : (byte) (i & -1);
                i = trackSettingInfoReplyData.hasAlbumTitle() ? (byte) (i | 8) : (byte) (i & -1);
                i = trackSettingInfoReplyData.m783c() ? (byte) (i | 16) : (byte) (i & -1);
                i = trackSettingInfoReplyData.m784d() ? (byte) (i | 32) : (byte) (i & -1);
                i = trackSettingInfoReplyData.hasElapsedTime() ? (byte) (i | 64) : (byte) (i & -1);
                dataOutputStream.writeByte(trackSettingInfoReplyData.m785e() ? (byte) (i | -128) : (byte) (i & -1));
                dataOutputStream.writeByte(0);
                return byteArrayOutputStream.toByteArray();
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
        }
        throw new IllegalArgumentException("param class type unmatched");
    }

    public static byte[] m790b(TrackInfoReplyDataBase trackInfoReplyDataBase) {
        if (trackInfoReplyDataBase instanceof TrackTitleReplyData) {
            TrackTitleReplyData trackTitleReplyData = (TrackTitleReplyData) trackInfoReplyDataBase;
            OutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            DataOutputStream dataOutputStream = new DataOutputStream(byteArrayOutputStream);
            try {
                dataOutputStream.writeByte(trackTitleReplyData.m779a());
                dataOutputStream.writeLong(trackTitleReplyData.getTrackToken());
                String trackTitle = trackTitleReplyData.getTrackTitle();
                if (trackTitleReplyData.getTrackToken() == -1 || trackTitle == null) {
                    dataOutputStream.writeShort(0);
                    dataOutputStream.writeByte(0);
                } else {
                    trackTitle = new StringBuilder(String.valueOf(trackTitle)).append('\u0000').toString();
                    dataOutputStream.writeShort(trackTitle.getBytes("utf8").length);
                    dataOutputStream.write(trackTitle.getBytes("utf8"));
                }
                return byteArrayOutputStream.toByteArray();
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
        }
        throw new IllegalArgumentException("param class type unmatched");
    }

    public static byte[] m791c(TrackInfoReplyDataBase trackInfoReplyDataBase) {
        if (trackInfoReplyDataBase instanceof TrackArtistNameReplyData) {
            TrackArtistNameReplyData trackArtistNameReplyData = (TrackArtistNameReplyData) trackInfoReplyDataBase;
            OutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            DataOutputStream dataOutputStream = new DataOutputStream(byteArrayOutputStream);
            try {
                dataOutputStream.writeByte(trackArtistNameReplyData.m779a());
                dataOutputStream.writeLong(trackArtistNameReplyData.getTrackToken());
                String artistName = trackArtistNameReplyData.getArtistName();
                if (trackArtistNameReplyData.getTrackToken() == -1 || artistName == null) {
                    dataOutputStream.writeShort(0);
                    dataOutputStream.writeByte(0);
                } else {
                    artistName = new StringBuilder(String.valueOf(artistName)).append('\u0000').toString();
                    dataOutputStream.writeShort(artistName.getBytes("utf8").length);
                    dataOutputStream.write(artistName.getBytes("utf8"));
                }
                return byteArrayOutputStream.toByteArray();
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
        }
        throw new IllegalArgumentException("param class type unmatched");
    }

    public static byte[] m792d(TrackInfoReplyDataBase trackInfoReplyDataBase) {
        if (trackInfoReplyDataBase instanceof TrackAlbumTitleReplyData) {
            TrackAlbumTitleReplyData trackAlbumTitleReplyData = (TrackAlbumTitleReplyData) trackInfoReplyDataBase;
            OutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            DataOutputStream dataOutputStream = new DataOutputStream(byteArrayOutputStream);
            try {
                dataOutputStream.writeByte(trackAlbumTitleReplyData.m779a());
                dataOutputStream.writeLong(trackAlbumTitleReplyData.getTrackToken());
                String albumTitle = trackAlbumTitleReplyData.getAlbumTitle();
                if (trackAlbumTitleReplyData.getTrackToken() == -1 || albumTitle == null) {
                    dataOutputStream.writeShort(0);
                    dataOutputStream.writeByte(0);
                } else {
                    albumTitle = new StringBuilder(String.valueOf(albumTitle)).append('\u0000').toString();
                    dataOutputStream.writeShort(albumTitle.getBytes("utf8").length);
                    dataOutputStream.write(albumTitle.getBytes("utf8"));
                }
                return byteArrayOutputStream.toByteArray();
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
        }
        throw new IllegalArgumentException("param class type unmatched");
    }

    public static byte[] m793e(TrackInfoReplyDataBase trackInfoReplyDataBase) {
        if (trackInfoReplyDataBase instanceof TrackElapsedTimeNotificationData) {
            TrackElapsedTimeNotificationData trackElapsedTimeNotificationData = (TrackElapsedTimeNotificationData) trackInfoReplyDataBase;
            OutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            DataOutputStream dataOutputStream = new DataOutputStream(byteArrayOutputStream);
            try {
                dataOutputStream.writeByte(trackElapsedTimeNotificationData.m779a());
                dataOutputStream.writeLong(trackElapsedTimeNotificationData.getTrackToken());
                dataOutputStream.writeShort(trackElapsedTimeNotificationData.getElapsedTime());
                return byteArrayOutputStream.toByteArray();
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
        }
        throw new IllegalArgumentException("param class type unmatched");
    }
}
