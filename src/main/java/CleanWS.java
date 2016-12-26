import java.io.File;

/**
 * 清理工作空间或其他目�?如果目录不存在则创建一个�?
 * @author ljr
 * 
 */
public class CleanWS {
	public static void doclean(String WS){
		File localWS = new File(WS);
		if(localWS.exists()==true){
			if(localWS.isFile()){
				localWS.delete();
			}else{
				File[] WSList = localWS.listFiles();
				for(int i=0;i<WSList.length;i++){
					doclean(WSList[i].toString());
				}
				localWS.delete();
			}
		}else
			localWS.mkdir();
		
	}
	public static void doclean(File localWS){
		if(localWS.exists()==true){
			if(localWS.isFile()){
				localWS.delete();
			}else{
				File[] WSList = localWS.listFiles();
				for(int i=0;i<WSList.length;i++){
					doclean(WSList[i].toString());
				}
				localWS.delete();
			}
		}else
			localWS.mkdir();
		
	}
}