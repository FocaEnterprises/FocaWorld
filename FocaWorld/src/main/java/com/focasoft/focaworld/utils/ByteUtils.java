package com.focasoft.focaworld.utils;

// DataInputStream and DataOutputStream
@SuppressWarnings("all")
public class ByteUtils
{ 
  public static void writeLong(byte[] data, long number, int start)
  {
    data[start + 0] = (byte)(number >>> 56);
    data[start + 1] = (byte)(number >>> 48);
    data[start + 2] = (byte)(number >>> 40);
    data[start + 3] = (byte)(number >>> 32);
    data[start + 4] = (byte)(number >>> 24);
    data[start + 5] = (byte)(number >>> 16);
    data[start + 6] = (byte)(number >>>  8);
    data[start + 7] = (byte)(number >>>  0);
  }
  
  public static long readLong(byte[] data, int start)
  {
    return (((long) data[start + 0] << 56) +
            ((long) (data[start + 1] & 255) << 48) +
            ((long) (data[start + 2] & 255) << 40) +
            ((long) (data[start + 3] & 255) << 32) +
            ((long) (data[start + 4] & 255) << 24) +
            ((data[start + 5] & 255) << 16) +
            ((data[start + 6] & 255) <<  8) +
            ((data[start + 7] & 255) <<  0)
    );
  }
  
  public static void writeInt(byte[] data, int number, int start)
  {
    data[start + 0] = (byte) ((number >>> 24) & 0xFF);
    data[start + 1] = (byte) ((number >>> 16) & 0xFF);
    data[start + 2] = (byte) ((number >>>  8) & 0xFF);
    data[start + 3] = (byte) ((number >>>  0) & 0xFF);
  }
  
  public static int readInt(byte[] data, int start)
  {
    int ch1 = data[start + 0];
    int ch2 = data[start + 1];
    int ch3 = data[start + 2];
    int ch4 = data[start + 3];

    return ((ch1 << 24) + (ch2 << 16) + (ch3 << 8) + (ch4 << 0));
  }

  public static void writeShort(byte[] data, short number, int start)
  {
    data[start + 0] = (byte) ((number >>> 8) & 0xFF);
    data[start + 1] = (byte) ((number >>> 0) & 0xFF);
  }

  public static short readShort(byte[] data, int start)
  {
    int ch1 = data[start + 0];
    int ch2 = data[start + 1];

    return (short) ((ch1 << 8) + (ch2 << 0));
  }

  public static void writeString(byte[] data, String str, int start)
  {
    for(int i = 0; i < str.length(); i++) {
      data[start + i] = (byte) str.charAt(i);
    }
  }

  public static String readString(byte[] data, int start, int len)
  {
    char[] chars = new char[len];

    for(int i = 0; i < len; i++) {
      chars[i] = (char) data[start + i];
    }

    return new String(chars);
  }
}
