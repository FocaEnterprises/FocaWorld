package test;

public class HexaTest
{
  public static void main(String[] args)
  {
    char[] chars = { 0x5b, 0x30, 0x31, 0x2f, 0x31, 0x32, 0x20, 0x2d, 0x20, 0x31, 0x39, 0x3a, 0x30, 0x31, 0x5d, 0x20, 0x5b, 0x45, 0x56, 0x45, 0x4e, 0x54, 0x53, 0x5d, 0x20, 0x4c, 0x6f, 0x61, 0x64, 0x69, 0x6e, 0x67, 0x20, 0x34, 0x20, 0x65, 0x76, 0x65, 0x6e, 0x74, 0x73, 0x2e, 0x0d, 0x0a, 0x5b, 0x30, 0x31, 0x2f, 0x31, 0x32};
    String string = new String(chars);

    System.out.println(string);
  }
}
