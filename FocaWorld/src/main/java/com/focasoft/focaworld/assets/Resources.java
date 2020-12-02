package com.focasoft.focaworld.assets;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

public final class Resources
{
  private static File root;
  private static File assets;

  public static boolean setup()
  {
    try {
      root = new File(System.getProperty("user.dir"));
      assets = new File(root, "assets");

      if(!assets.exists())
        assets.mkdir();

    } catch(Exception e) {
      e.printStackTrace();
      return false;
    }

    return true;
  }

  public static InputStream getImageStream(String imageName)
  {
    return getResource("sprites" + File.separator + imageName);
  }

  public static InputStream getResource(String path)
  {
    try {
      URL url = new File(assets, path).toURI().toURL();
      return url.openStream();
    } catch(IOException e) {
      e.printStackTrace();
    }

    return null;
  }
}
