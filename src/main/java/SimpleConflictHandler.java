import java.io.File;
import java.util.logging.Logger;

import org.tmatesoft.svn.core.SVNException;
import org.tmatesoft.svn.core.wc.ISVNConflictHandler;
import org.tmatesoft.svn.core.wc.SVNConflictChoice;
import org.tmatesoft.svn.core.wc.SVNConflictDescription;
import org.tmatesoft.svn.core.wc.SVNConflictReason;
import org.tmatesoft.svn.core.wc.SVNConflictResult;
import org.tmatesoft.svn.core.wc.SVNMergeFileSet;


public class SimpleConflictHandler implements ISVNConflictHandler {
	private final static Logger logger = Logger.getLogger(SimpleConflictHandler.class.getName());

	@Override
	public SVNConflictResult handleConflict(SVNConflictDescription conflictDescription) throws SVNException {
		System.out.println("===============================");
		logger.info("===================================================");
		logger.info(" ’µΩ≥ÂÕª£∫action:"+conflictDescription.getConflictAction() + ",info:"+conflictDescription.getConflictReason()+",file:"+conflictDescription.getMergeFiles());
		SVNConflictReason reason = conflictDescription.getConflictReason();
		SVNMergeFileSet mergeFiles = conflictDescription.getMergeFiles();
		SVNConflictChoice choice = SVNConflictChoice.POSTPONE;
		File file = mergeFiles.getResultFile();
		SVNConflictResult result = new SVNConflictResult(choice, file);
		return result;
	}

}
