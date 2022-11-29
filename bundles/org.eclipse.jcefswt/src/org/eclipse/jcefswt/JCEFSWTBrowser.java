package org.eclipse.jcefswt;

import java.awt.Frame;
import java.io.IOException;

import org.cef.*;
import org.cef.CefApp.CefAppState;
import org.cef.browser.CefBrowser;
import org.cef.browser.CefMessageRouter;
import org.eclipse.swt.SWT;
import org.eclipse.swt.awt.SWT_AWT;
import org.eclipse.swt.widgets.Composite;

import me.friwi.jcefmaven.*;

public class JCEFSWTBrowser extends Composite {

  private static CefApp cefApp;
  private static CefClient cefClient;
  private final CefBrowser cefBrowser;

  private Frame frame;

  public JCEFSWTBrowser(Composite parent, String url) {
    this(parent, SWT.None, url);
  }

  public JCEFSWTBrowser(Composite parent, int style, String url) {
    super(parent, SWT.EMBEDDED | style);
    cefApp = initCefApp();
    cefClient = initCefClient();
    cefBrowser = initCefBrowser(url);
    initCefBrowserWidget();
  }

  protected CefApp initCefApp() {
    if (cefApp == null) {
      CefAppBuilder builder = new CefAppBuilder();
      // windowless_rendering_enabled must be set to false if not wanted.
      builder.getCefSettings().windowless_rendering_enabled = false;
      // USE builder.setAppHandler INSTEAD OF CefApp.addAppHandler!
      // Fixes compatibility issues with MacOSX
      builder.setAppHandler(new MavenCefAppHandlerAdapter() {
        @Override
        public void stateHasChanged(org.cef.CefApp.CefAppState state) {
          // Shutdown the app if the native CEF part is terminated
          if (state == CefAppState.TERMINATED)
            System.exit(0);
        }
      });
      try {
        return builder.build();
      } catch (IOException | UnsupportedPlatformException | InterruptedException | CefInitializationException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
      }
      // String[] args = Platform.getApplicationArgs();
      // CefApp.startup(args);
      // if (CefApp.getState() != CefApp.CefAppState.INITIALIZED) {
      // CefSettings settings = getCefSettings();
      // return CefApp.getInstance(settings);
      // } else {
      // return CefApp.getInstance();
      // }
    }
    return cefApp;
  }

  protected CefSettings getCefSettings() {
    CefSettings settings = new CefSettings();
    settings.windowless_rendering_enabled = false;
    return settings;
  }

  protected CefClient initCefClient() {
    if (cefClient == null) {
      CefClient cefClient = cefApp.createClient();
      cefClient.addMessageRouter(CefMessageRouter.create());
      return cefClient;
    }
    return cefClient;
  }

  protected CefBrowser initCefBrowser(String url) {
    return cefClient.createBrowser(url, isOSR(), isTransparent());
  }

  protected boolean isOSR() {
    return false;
  }

  protected boolean isTransparent() {
    return false;
  }

  protected void initCefBrowserWidget() {
    frame = SWT_AWT.new_Frame(this);
    frame.add(cefBrowser.getUIComponent());
  }

  public CefApp getCefApp() {
    return cefApp;
  }

  public CefClient getCefClient() {
    return cefClient;
  }

  public CefBrowser getCefBrowser() {
    return cefBrowser;
  }

  public Frame getFrame() {
    return frame;
  }

}