package org.eclipse.jcefswt.example;

import org.eclipse.jcefswt.JCEFSWTBrowser;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.part.ViewPart;

public class JCEFSWTViewPart extends ViewPart {

  private JCEFSWTBrowser jcefswtBrowser;

  public JCEFSWTViewPart() {
  }

  @Override
  public void createPartControl(Composite parent) {
    jcefswtBrowser = new JCEFSWTBrowser(parent, "https://github.com/RoiSoleil/jcefswt");
  }

  @Override
  public void setFocus() {
    jcefswtBrowser.setFocus();
  }

}
