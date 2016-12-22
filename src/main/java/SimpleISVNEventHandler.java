
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import org.tmatesoft.svn.core.SVNCancelException;
import org.tmatesoft.svn.core.SVNException;
import org.tmatesoft.svn.core.wc.ISVNDiffStatusHandler;
import org.tmatesoft.svn.core.wc.ISVNEventHandler;
import org.tmatesoft.svn.core.wc.SVNDiffStatus;
import org.tmatesoft.svn.core.wc.SVNEvent;

public class SimpleISVNEventHandler implements ISVNEventHandler{
	
	@Override
	public void checkCancelled() throws SVNCancelException{
//		System.out.println("checkCancelled");
	}
	
	@Override
	public void handleEvent(SVNEvent event,double progress){
		System.out.println(event.getAction()+","+event.getFile());
	}
}
