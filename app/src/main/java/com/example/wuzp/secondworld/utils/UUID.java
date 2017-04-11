package com.example.wuzp.secondworld.utils;

import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.provider.Settings.Secure;
import android.telephony.TelephonyManager;
import android.text.TextUtils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * 获取设备唯一标识
 */
public class UUID {

    private Context context;
    private static UUID instance;

    private UUID(Context context) {
        this.context = context;
    }

    public static UUID getInstance(Context context) {
        if (instance == null) {
            synchronized (UUID.class) {
                if (instance == null) {
                    instance = new UUID(context.getApplicationContext());
                }
            }
        }
        return instance;
    }

    private String mUUID = "";

    public String getUUID() {
        if (!TextUtils.isEmpty(mUUID)) {
            return mUUID;
        }
        // 1 compute IMEI
        String m_szImei = "";
        try {
            TelephonyManager TelephonyMgr = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
            m_szImei = TelephonyMgr.getDeviceId(); // Requires
        } catch (Exception e) {
        }
        // READ_PHONE_STATE

        // 2 compute DEVICE ID
        String m_szDevIDShort = "35" + // we make this look like a valid IMEI
                Build.BOARD.length() % 10 + Build.BRAND.length() % 10 +
                Build.CPU_ABI.length() % 10 + Build.DEVICE.length() % 10 +
                Build.DISPLAY.length() % 10 + Build.HOST.length() % 10 +
                Build.ID.length() % 10 + Build.MANUFACTURER.length() % 10 +
                Build.MODEL.length() % 10 + Build.PRODUCT.length() % 10 +
                Build.TAGS.length() % 10 + Build.TYPE.length() % 10 +
                Build.USER.length() % 10; // 13 digits
        // 3 android ID - unreliable
        String m_szAndroidID = Secure.getString(context.getContentResolver(), Secure.ANDROID_ID);

        // 4 wifi manager, read MAC address - requires
        // android.permission.ACCESS_WIFI_STATE or comes as null

        String m_szWLANMAC = "";
        try {
            WifiManager wm = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
            m_szWLANMAC = wm.getConnectionInfo().getMacAddress();
        } catch (Exception e) {
        }

        // 5 Bluetooth MAC address android.permission.BLUETOOTH required
        BluetoothAdapter m_BluetoothAdapter = null; // Local Bluetooth adapter
        String m_szBTMAC = "";
        try {
            m_BluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
            if (m_BluetoothAdapter != null) {
                m_szBTMAC = m_BluetoothAdapter.getAddress();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        // 6 SUM THE IDs
        String m_szLongID = m_szImei + m_szDevIDShort + m_szAndroidID + m_szWLANMAC + m_szBTMAC;
        MessageDigest m = null;
        try {
            m = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        m.update(m_szLongID.getBytes(), 0, m_szLongID.length());
        byte p_md5Data[] = m.digest();

        String m_szUniqueID = new String();
        for (int i = 0; i < p_md5Data.length; i++) {
            int b = (0xFF & p_md5Data[i]);
            // if it is a single digit, make sure it have 0 in front (proper
            // padding)
            if (b <= 0xF)
                m_szUniqueID += "0";
            // add number to string
            m_szUniqueID += Integer.toHexString(b);
        }
        m_szUniqueID = m_szUniqueID.toUpperCase();
        mUUID = m_szUniqueID;
        return m_szUniqueID;
    }
}
