import java.io.File;
import java.util.logging.Logger;

import org.tmatesoft.svn.core.SVNDepth;
import org.tmatesoft.svn.core.SVNException;
import org.tmatesoft.svn.core.SVNURL;
import org.tmatesoft.svn.core.internal.io.dav.DAVRepositoryFactory;
import org.tmatesoft.svn.core.internal.wc.DefaultSVNOptions;
import org.tmatesoft.svn.core.wc.ISVNOptions;
import org.tmatesoft.svn.core.wc.SVNClientManager;
import org.tmatesoft.svn.core.wc.SVNCommitPacket;
import org.tmatesoft.svn.core.wc.SVNRevision;
import org.tmatesoft.svn.core.wc.SVNUpdateClient;
import org.tmatesoft.svn.core.wc.SVNWCUtil;


public class MergebtwTrBr {
	private final static Logger logger = Logger.getLogger(MergebtwTrBr.class.getName());
	public static SVNClientManager ourClientManager; //svn 客户端管理类
	
	public void mergeTr2Br(String srcurl,String desturl,String localWS) throws SVNException{
		DAVRepositoryFactory.setup();
		ISVNOptions options = SVNWCUtil.createDefaultOptions(true);
		String name = "doncat";
		String password = "123456";
		ourClientManager = SVNClientManager.newInstance((DefaultSVNOptions)options, name, password);
		SVNURL destURL=SVNURL.parseURIEncoded(desturl);
		File localWorkSpace = new File(localWS);
//		ourClientManager.getCommitClient().G
		logger.info("清理工作空间目录："+localWorkSpace);
		CleanWS.doclean(localWorkSpace);
		logger.info("开始检出目标目录："+destURL+"到本地文件夹："+localWorkSpace);
		SVNUpdateClient updateClient = ourClientManager.getUpdateClient();
		updateClient.doCheckout(destURL, localWorkSpace, SVNRevision.HEAD, SVNRevision.HEAD,SVNDepth.INFINITY, true);
		logger.info("已检出目标目录"+localWorkSpace);
		
		String[] wsFileList = localWorkSpace.list();
		
		for(int i=0;i<wsFileList.length;i++){
			if(!wsFileList[i].equals(".svn")){
				logger.info("开始合并：from "+srcurl+" to "+desturl);
				Merge mer = new Merge();
				try {
					mer.doMerge(ourClientManager,desturl+"/"+wsFileList[i],srcurl,localWS+"/"+wsFileList[i]);
					
					logger.info("提交"+desturl);
					File[] commitFile= new File[1];
					commitFile[0] = new File(localWS+"/"+wsFileList[i]);
					for (File sdf : commitFile) {
						logger.info("commitFile:"+sdf);
					}
					ourClientManager.getCommitClient().doCommit(commitFile, false, "auto-merge from "+srcurl+" to "+desturl, null, null, false, true, SVNDepth.INFINITY);
					logger.info("已完成提交");
				} catch (SVNException e) {
					e.printStackTrace();
				}
			}	
		}
	}
	
	public static void main(String[] args) throws SVNException {
//		String trunkURL = args[0];//源URL（主干）
//		String branchrootURL = args[1];//目标URL（分支）
//		String localWorkspace = args[2];//本地checkout的路径
		MergebtwTrBr a = new MergebtwTrBr();
//		a.mergeTr2Br(trunkURL,branchrootURL,localWorkspace);
		a.mergeTr2Br("https://ljrxxx/svn/don-cat/trunk/testtr","https://ljrxxx/svn/don-cat/branches","G:\\svntest");
		
	}
}
