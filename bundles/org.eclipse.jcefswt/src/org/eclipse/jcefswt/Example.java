package org.eclipse.jcefswt;

import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.*;

public class Example {

  public static void main(String[] args) {
    final Display display = new Display();
    final Shell shell = new Shell(display);
    shell.setText("SwtCefBrowser example");
    SwtCefBrowser cefBrowser = new SwtCefBrowser(shell, "https://github.com/RoiSoleil/swt-cef-browser");
    shell.setLayout(new FillLayout());
    shell.open();
    while (!shell.isDisposed()) {
      if (!display.readAndDispatch())
        display.sleep();
    }
    display.dispose();
  }

}