package com.vlife.checkserver.mobilestatus;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.LinkedList;
import java.util.List;
import java.util.Properties;
import java.util.Vector;

/**
 * @author: gaoyaxuan
 * @date:2018年8月13日 下午5:28:43
 */
import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.ChannelSftp.LsEntry;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.SftpException;

/**
 * java远程上传文件
 * 
 * @author 高亚轩
 * 
 */
public class SshCopyFile {
	private Session sshSession =null;
	private ChannelSftp sftp=null;
	private Channel channel=null;
	/**
	 * 创建一个连接
	 * 
	 * @param host     地址
	 * @param user     用户名
	 * @param password 密码
	 * @param port     ssh2端口
	 */
	public SshCopyFile(String host, String username, String password, int port) {
		try {
			JSch jsch = new JSch();
			jsch.getSession(username, host, port);
			 sshSession = jsch.getSession(username, host, port);
			System.out.println("Session created.");
			sshSession.setPassword(password);
			Properties sshConfig = new Properties();
			sshConfig.put("StrictHostKeyChecking", "no");
			sshSession.setConfig(sshConfig);
			sshSession.connect();
			channel = sshSession.openChannel("sftp");
			channel.connect();
			sftp = (ChannelSftp) channel;
		} catch (Exception e) {

		}
	}

	/**
	 * 关闭连接 	使用完本类一定要调用close方法,否则会出现程序无法结束的情况
	 * 
	 * @throws Exception
	 */
	public void close()  {
		
		if(sftp!=null){
            sftp.disconnect();
            sftp.exit();
            sftp = null;
        }
        if(channel!=null){
            channel.disconnect();
            channel = null;
        }
        if( sshSession!=null){
        	sshSession.disconnect();
        	sshSession = null;
        }
	}

	/**
	 * 上传文件
	 * 
	 * @param localPath  本地路径，若为空，表示当前路径
	 * @param localFile  本地文件名，若为空或是“*”，表示目前下全部文件
	 * @param remotePath 远程路径，若为空，表示当前路径，若服务器上无此目录，则会自动创建
	 * @throws Exception
	 */
	public void putFile(String localPath, String localFile, String remotePath) throws Exception {

		String remoteFile = null;
		if (remotePath != null && remotePath.trim().length() > 0) {
			try {
				sftp.mkdir(remotePath);
			} catch (Exception e) {
			}
			remoteFile = remotePath + "/.";
		} else {
			remoteFile = ".";
		}
		String file = null;
		if (localFile == null || localFile.trim().length() == 0) {
			file = "*";
		} else {
			file = localFile;
		}
		if (localPath != null && localPath.trim().length() > 0) {
			if (localPath.endsWith("/")) {
				file = localPath + file;
			} else {
				file = localPath + "/" + file;
			}
		}
		sftp.put(file, remoteFile);

	}

	/**
	 * 删除文件----本版本禁止使用
	 *
	 * @param directory  要删除文件所在目录
	 * @param deleteFile 要删除的文件
	 * @param sftp
	 * @throws Exception
	 */
	@SuppressWarnings("unused")
	private void deleteFileOrDirector(String fileOrDirectory) {
		try {
			// sftp.rmdir(fileOrDirectory);
//			sftp.cd(fileOrDirectory);
			sftp.rm(fileOrDirectory);
		} catch (SftpException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		} catch (Exception e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}

	}

	/**
	 * 下载文件
	 * 
	 * @param directory    下载目录
	 * @param downloadFile 下载的文件
	 * @param saveFile     存在本地的路径----不需要文件名
	 */
	public void downloadFile(String directory, String downloadFile, String saveFilePath) {
		 OutputStream out =null;
		try {
			sftp.cd(directory); 
			 out = new FileOutputStream(new File(saveFilePath,downloadFile));
			 sftp.get(downloadFile, out);
			 out.flush();
		} catch (FileNotFoundException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		} catch (SftpException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		} catch (IOException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}finally {
			if(out!=null) {
				try {
					out.close();
				} catch (IOException e) {
					// TODO 自动生成的 catch 块
					e.printStackTrace();
				}
			}
	         
		}

         
         

	}

	/**
	 * 列出目录下的文件
	 * 
	 * @param directory 要列出的目录
	 * @param sftp
	 * @return
	 * @return
	 * @throws SftpException
	 */
	@SuppressWarnings("unchecked")
	public List<String> listFiles(String directory) throws SftpException {
		List<String> list = new LinkedList<>();
		Vector<LsEntry> file = sftp.ls(directory);
		for (LsEntry s : file) {
			list.add(s.getFilename());
		}
		return list;
	}

}