package org.eclipse.jcefswt.example;

import org.eclipse.core.commands.*;
import org.eclipse.ui.handlers.HandlerUtil;

public class GoToGoogleHandler extends AbstractHandler {

  @Override
  public Object execute(ExecutionEvent event) throws ExecutionException {
    JCEFSWTViewPart part = (JCEFSWTViewPart) HandlerUtil.getActivePart(event);
    part.setUrl("https://www.google.com");
    return null;
  }
}
