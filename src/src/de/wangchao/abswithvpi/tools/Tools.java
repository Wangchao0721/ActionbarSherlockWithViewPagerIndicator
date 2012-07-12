
package de.wangchao.abswithvpi.tools;

import android.os.Environment;
import android.util.Log;

public class Tools {
    static boolean DEBUG = true;

    public static boolean checkMediaAvailable() {

        boolean mExternalStorageAvailable = false;
        String state = Environment.getExternalStorageState();

        if (Environment.MEDIA_MOUNTED.equals(state)) {
            // We can read and write the media
            mExternalStorageAvailable = true;
        } else if (Environment.MEDIA_MOUNTED_READ_ONLY.equals(state)) {
            // We can only read the media
            mExternalStorageAvailable = true;
        } else {
            // Something else is wrong. It may be one of many other states, but
            // all we need
            // to know is we can neither read nor write
            mExternalStorageAvailable = false;
        }
        return mExternalStorageAvailable;
    }

    public static boolean checkMediaWriteable() {

        boolean mExternalStorageWriteable = false;
        String state = Environment.getExternalStorageState();

        if (Environment.MEDIA_MOUNTED.equals(state)) {
            // We can read and write the media
            mExternalStorageWriteable = true;
        } else if (Environment.MEDIA_MOUNTED_READ_ONLY.equals(state)) {
            // We can only read the media
            mExternalStorageWriteable = false;
        } else {
            // Something else is wrong. It may be one of many other states, but
            // all we need
            // to know is we can neither read nor write
            mExternalStorageWriteable = false;
        }
        return mExternalStorageWriteable;
    }

    public static void debugLog(String tag, String msg) {

        if (DEBUG) {
            Log.v(tag, msg);
        }
    }

}
