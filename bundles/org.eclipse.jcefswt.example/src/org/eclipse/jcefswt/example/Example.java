package org.eclipse.jcefswt.example;

import org.eclipse.jcefswt.JCEFSWTBrowser;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

public class Example {

  public static void main(String[] args) {
    final Display display = new Display();
    final Shell shell = new Shell(display);
    shell.setText("SwtCefBrowser example");
    shell.setLayout(new FillLayout());
    JCEFSWTBrowser jcefswtBrowser = new JCEFSWTBrowser(shell, "https://github.com/RoiSoleil/jcefswt");
    shell.open();
    while (!shell.isDisposed()) {
      if (!display.readAndDispatch())
        display.sleep();
    }
    display.dispose();
  }

}