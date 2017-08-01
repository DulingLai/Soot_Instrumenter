package jp.pioneer.ce.aam2.AAM2Kit.replydata;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class C3387a {
    public static long m440a(byte[] bArr) {
        DataInputStream dataInputStream = new DataInputStream(new ByteArrayInputStream(bArr));
        long j = -1;
        try {
            j = dataInputStream.readLong();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return j;
    }

    public static byte[] m441a(int i, AAM2TrackInfoReplyDataBase aAM2TrackInfoReplyDataBase) {
        switch (i) {
            case 2:
                return C3387a.m442a(aAM2TrackInfoReplyDataBase);
            case 3:
                return C3387a.m444b(aAM2TrackInfoReplyDataBase);
            case 4:
                return C3387a.m445c(aAM2TrackInfoReplyDataBase);
            case 5:
                return C3387a.m446d(aAM2TrackInfoReplyDataBase);
            case 6:
                return C3387a.m447e(aAM2TrackInfoReplyDataBase);
            default:
                return null;
        }
    }

    public static byte[] m442a(AAM2TrackInfoReplyDataBase aAM2TrackInfoReplyDataBase) {
        if (aAM2TrackInfoReplyDataBase instanceof AAM2TrackInfoReplyData) {
            AAM2TrackInfoReplyData aAM2TrackInfoReplyData = (AAM2TrackInfoReplyData) aAM2TrackInfoReplyDataBase;
            OutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            DataOutputStream dataOutputStream = new DataOutputStream(byteArrayOutputStream);
            try {
                dataOutputStream.writeByte(aAM2TrackInfoReplyData.m423d());
                dataOutputStream.writeLong(aAM2TrackInfoReplyData.m424c());
                dataOutputStream.writeShort(aAM2TrackInfoReplyData.m428a());
                dataOutputStream.writeShort(aAM2TrackInfoReplyData.m429b());
                return byteArrayOutputStream.toByteArray();
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
        }
        throw new IllegalArgumentException("param class type unmatched");
    }

    public static byte[] m443a(C3386b c3386b) {
        if (c3386b instanceof AAM2TrackSettingInfoReplyData) {
            AAM2TrackSettingInfoReplyData aAM2TrackSettingInfoReplyData = (AAM2TrackSettingInfoReplyData) c3386b;
            OutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            DataOutputStream dataOutputStream = new DataOutputStream(byteArrayOutputStream);
            try {
                if (aAM2TrackSettingInfoReplyData.m423d() != 1) {
                    return null;
                }
                dataOutputStream.writeByte(aAM2TrackSettingInfoReplyData.m423d());
                if (aAM2TrackSettingInfoReplyData.m430a()) {
                    dataOutputStream.writeByte(1);
                } else {
                    dataOutputStream.writeByte(0);
                }
                int i = aAM2TrackSettingInfoReplyData.m431b() ? (byte) 1 : (byte) 0;
                i = aAM2TrackSettingInfoReplyData.m432c() ? (byte) (i | 2) : (byte) (i & -1);
                i = aAM2TrackSettingInfoReplyData.m433e() ? (byte) (i | 4) : (byte) (i & -1);
                i = aAM2TrackSettingInfoReplyData.m434f() ? (byte) (i | 8) : (byte) (i & -1);
                i = aAM2TrackSettingInfoReplyData.m435g() ? (byte) (i | 16) : (byte) (i & -1);
                i = aAM2TrackSettingInfoReplyData.m436h() ? (byte) (i | 32) : (byte) (i & -1);
                i = aAM2TrackSettingInfoReplyData.m437i() ? (byte) (i | 64) : (byte) (i & -1);
                dataOutputStream.writeByte(aAM2TrackSettingInfoReplyData.m438j() ? (byte) (i | -128) : (byte) (i & -1));
                dataOutputStream.writeByte(0);
                return byteArrayOutputStream.toByteArray();
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
        }
        throw new IllegalArgumentException("param class type unmatched");
    }

    public static byte[] m444b(AAM2TrackInfoReplyDataBase aAM2TrackInfoReplyDataBase) {
        if (aAM2TrackInfoReplyDataBase instanceof AAM2TrackTitleReplyData) {
            AAM2TrackTitleReplyData aAM2TrackTitleReplyData = (AAM2TrackTitleReplyData) aAM2TrackInfoReplyDataBase;
            OutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            DataOutputStream dataOutputStream = new DataOutputStream(byteArrayOutputStream);
            try {
                dataOutputStream.writeByte(aAM2TrackTitleReplyData.m423d());
                dataOutputStream.writeLong(aAM2TrackTitleReplyData.m424c());
                String a = aAM2TrackTitleReplyData.m439a();
                if (aAM2TrackTitleReplyData.m424c() == -1 || a == null) {
                    dataOutputStream.writeShort(0);
                    dataOutputStream.writeByte(0);
                } else {
                    a = new StringBuilder(String.valueOf(a)).append('\u0000').toString();
                    dataOutputStream.writeShort(a.getBytes("utf8").length);
                    dataOutputStream.write(a.getBytes("utf8"));
                }
                return byteArrayOutputStream.toByteArray();
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
        }
        throw new IllegalArgumentException("param class type unmatched");
    }

    public static byte[] m445c(AAM2TrackInfoReplyDataBase aAM2TrackInfoReplyDataBase) {
        if (aAM2TrackInfoReplyDataBase instanceof AAM2TrackArtistNameReplyData) {
            AAM2TrackArtistNameReplyData aAM2TrackArtistNameReplyData = (AAM2TrackArtistNameReplyData) aAM2TrackInfoReplyDataBase;
            OutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            DataOutputStream dataOutputStream = new DataOutputStream(byteArrayOutputStream);
            try {
                dataOutputStream.writeByte(aAM2TrackArtistNameReplyData.m423d());
                dataOutputStream.writeLong(aAM2TrackArtistNameReplyData.m424c());
                String a = aAM2TrackArtistNameReplyData.m426a();
                if (aAM2TrackArtistNameReplyData.m424c() == -1 || a == null) {
                    dataOutputStream.writeShort(0);
                    dataOutputStream.writeByte(0);
                } else {
                    a = new StringBuilder(String.valueOf(a)).append('\u0000').toString();
                    dataOutputStream.writeShort(a.getBytes("utf8").length);
                    dataOutputStream.write(a.getBytes("utf8"));
                }
                return byteArrayOutputStream.toByteArray();
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
        }
        throw new IllegalArgumentException("param class type unmatched");
    }

    public static byte[] m446d(AAM2TrackInfoReplyDataBase aAM2TrackInfoReplyDataBase) {
        if (aAM2TrackInfoReplyDataBase instanceof AAM2TrackAlbumTitleReplyData) {
            AAM2TrackAlbumTitleReplyData aAM2TrackAlbumTitleReplyData = (AAM2TrackAlbumTitleReplyData) aAM2TrackInfoReplyDataBase;
            OutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            DataOutputStream dataOutputStream = new DataOutputStream(byteArrayOutputStream);
            try {
                dataOutputStream.writeByte(aAM2TrackAlbumTitleReplyData.m423d());
                dataOutputStream.writeLong(aAM2TrackAlbumTitleReplyData.m424c());
                String a = aAM2TrackAlbumTitleReplyData.m425a();
                if (aAM2TrackAlbumTitleReplyData.m424c() == -1 || a == null) {
                    dataOutputStream.writeShort(0);
                    dataOutputStream.writeByte(0);
                } else {
                    a = new StringBuilder(String.valueOf(a)).append('\u0000').toString();
                    dataOutputStream.writeShort(a.getBytes("utf8").length);
                    dataOutputStream.write(a.getBytes("utf8"));
                }
                return byteArrayOutputStream.toByteArray();
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
        }
        throw new IllegalArgumentException("param class type unmatched");
    }

    public static byte[] m447e(AAM2TrackInfoReplyDataBase aAM2TrackInfoReplyDataBase) {
        if (aAM2TrackInfoReplyDataBase instanceof AAM2TrackElapsedTimeNotificationData) {
            AAM2TrackElapsedTimeNotificationData aAM2TrackElapsedTimeNotificationData = (AAM2TrackElapsedTimeNotificationData) aAM2TrackInfoReplyDataBase;
            OutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            DataOutputStream dataOutputStream = new DataOutputStream(byteArrayOutputStream);
            try {
                dataOutputStream.writeByte(aAM2TrackElapsedTimeNotificationData.m423d());
                dataOutputStream.writeLong(aAM2TrackElapsedTimeNotificationData.m424c());
                dataOutputStream.writeShort(aAM2TrackElapsedTimeNotificationData.m427a());
                return byteArrayOutputStream.toByteArray();
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
        }
        throw new IllegalArgumentException("param class type unmatched");
    }
}
