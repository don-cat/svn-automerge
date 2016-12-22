import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

import org.tmatesoft.svn.core.ISVNLogEntryHandler;
import org.tmatesoft.svn.core.SVNDepth;
import org.tmatesoft.svn.core.SVNException;
import org.tmatesoft.svn.core.SVNLogEntry;
import org.tmatesoft.svn.core.SVNURL;
import org.tmatesoft.svn.core.auth.ISVNAuthenticationManager;
import org.tmatesoft.svn.core.internal.wc.DefaultSVNOptions;
import org.tmatesoft.svn.core.io.SVNRepository;
import org.tmatesoft.svn.core.io.SVNRepositoryFactory;
import org.tmatesoft.svn.core.wc.SVNClientManager;
import org.tmatesoft.svn.core.wc.SVNDiffClient;
import org.tmatesoft.svn.core.wc.SVNRevision;
import org.tmatesoft.svn.core.wc.SVNWCUtil;


public class Merge {
	private final static Logger logger = Logger.getLogger("Merge");
	
	public void doMerge(SVNClientManager ourClientManager,String desturl,String srcurl,String localWS) throws SVNException{
		logger.info("��ʼ����merge������From:"+srcurl+" To:"+desturl + " In:" + localWS);
			
		SVNURL destURL=SVNURL.parseURIEncoded(desturl);
		SVNURL srcURL=SVNURL.parseURIEncoded(srcurl);
		File localWorkSpace = new File(localWS);
		
		ourClientManager.setEventHandler(new SimpleISVNEventHandler());
		
		SVNDiffClient diffClient = ourClientManager.getDiffClient();
		
		diffClient.setIgnoreExternals(false);
		DefaultSVNOptions options = (DefaultSVNOptions)diffClient.getOptions();
		options.setConflictHandler(new SimpleConflictHandler());
//		diffClient.setOptions(options);
		
		SVNRepository svnRepository = SVNRepositoryFactory.create(srcURL);
		String username = "doncat";
        String password = "123456";
        ISVNAuthenticationManager authenticationManager = SVNWCUtil.createDefaultAuthenticationManager(username,password);
        svnRepository.setAuthenticationManager(authenticationManager);
//		System.out.println(svnRepository.getDsatedRevision(new Date(1480641661131L)));
		 final SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		 final List<Long> history = new ArrayList<Long>();
		 try {   
			
			 final Date	begin = format.parse("2014-02-13");
			 final	Date end = format.parse("2050-02-14");
			
			//String[] Ϊ���˵��ļ�·��ǰ׺��Ϊ�ձ�ʾ�����й���
		 svnRepository.log(new String[]{""},
	                       1,
	                       -1,
	                       true,
	                       true,
	                       new ISVNLogEntryHandler() {
	                           @Override
	                           public void handleLogEntry(SVNLogEntry svnlogentry)
	                                   throws SVNException {
					//�����ύʱ����й���
	                               if (svnlogentry.getDate().after(begin)
	                                   && svnlogentry.getDate().before(end)) {
									// �����ύ�˹���
	                                       fillResult(svnlogentry);
	                               }
	                           }

	                           public void fillResult(SVNLogEntry svnlogentry) {
	                          //getChangedPathsΪ�ύ����ʷ��¼MAP keyΪ�ļ�����valueΪ�ļ�����
	                        	   if(svnlogentry.getRevision()>0){
		                               history.add(svnlogentry.getRevision());
	                        	   }
	                           }
	                       });
		 } catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			SVNRevision re = SVNRevision.create(history.get(0));
		diffClient.doMerge(srcURL, re, srcURL, SVNRevision.HEAD, localWorkSpace,SVNDepth.UNKNOWN, true, false, false, false);
		logger.info("�����merge");
		
	}
}
