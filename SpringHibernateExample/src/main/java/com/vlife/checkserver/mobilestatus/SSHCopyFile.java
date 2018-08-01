package com.vlife.checkserver.mobilestatus;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.SftpException;
import com.jcraft.jsch.UserInfo;

/**
 * java远程上传文件
 * 
 * @author lenovo
 * 
 */
public class SSHCopyFile {

	private String host;

	private String user;

	private String password;

	private int port;

	private Session session;

	/**
	 * 创建一个连接
	 * 
	 * @param host     地址
	 * @param user     用户名
	 * @param password 密码
	 * @param port     ssh2端口
	 */
	public SSHCopyFile(String host, String user, String password, int port) {
		this.host = host;
		this.user = user;
		this.password = password;
		this.port = port;
	}

	private void initialSession() throws Exception {
		if (session == null) {
			JSch jsch = new JSch();
			session = jsch.getSession(user, host, port);
			session.setUserInfo(new UserInfo() {

				public String getPassphrase() {
					return null;
				}

				public String getPassword() {
					return null;
				}

				public boolean promptPassword(String arg0) {
					return false;
				}

				public boolean promptPassphrase(String arg0) {
					return false;
				}

				public boolean promptYesNo(String arg0) {
					return true;
				}

				public void showMessage(String arg0) {
				}

			});
			session.setPassword(password);
			session.connect();
		}
	}

	/**
	 * 关闭连接
	 * 
	 * @throws Exception
	 */
	public void close() throws Exception {
		if (session != null && session.isConnected()) {
			session.disconnect();
			session = null;
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
		this.initialSession();
		Channel channelSftp = session.openChannel("sftp");
		channelSftp.connect();
		ChannelSftp c = (ChannelSftp) channelSftp;
		String remoteFile = null;
		if (remotePath != null && remotePath.trim().length() > 0) {
			try {
				c.mkdir(remotePath);
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
		c.put(file, remoteFile);

		channelSftp.disconnect();
	}

	/**
	 * 删除文件
	 *
	 * @param directory  要删除文件所在目录
	 * @param deleteFile 要删除的文件
	 * @param sftp
	 * @throws Exception
	 */
	public void deleteFileOrDirector(String fileOrDirectory) {
		try {
			this.initialSession();
			Channel channelSftp = session.openChannel("sftp");
			channelSftp.connect();
			ChannelSftp sftp = (ChannelSftp) channelSftp;
			// sftp.rmdir(fileOrDirectory);
//			sftp.cd(fileOrDirectory);
			sftp.rm(fileOrDirectory);
		} catch (JSchException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		} catch (SftpException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		} catch (Exception e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}

	}
	
	

}