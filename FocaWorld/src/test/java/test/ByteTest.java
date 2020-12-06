package test;

import java.util.Arrays;

public class ByteTest
{
  public static void main(String[] args)
  {
    String name = "Giver";
    byte[] data = name.getBytes();
    byte[] bt = new byte[name.length()];
    char[] chars = new char[name.length()];
    char[] chs = new char[name.length()];

    for(int i = 0; i < chars.length; i++) {
      chars[i]=name.charAt(i);
    }

    for(int i = 0; i < chars.length; i++)
    {
      chs[i] = (char)(data[i]);
    }

    for(int i = 0; i < chars.length; i++) {
      bt[i] = (byte) chars[i];
    }

    System.out.println("String: " + name);
    System.out.println("Length: " + name.length());
    System.out.println("Chars: " + Arrays.toString(chars));
    System.out.println("Bytes: " + Arrays.toString(data));
    System.out.println("Byte length: " + data.length);
    System.out.println("Cast:" + Arrays.toString(chs));
    System.out.println("Cast 2:" + Arrays.toString(bt));

  }
}
