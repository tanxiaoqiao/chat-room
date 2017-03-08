package server;

import java.io.BufferedInputStream;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.ObjectInputStream;
import java.net.Socket;

import bean.ExportObject;

//多线程
public class ServerThread extends Thread {
	Socket socket;
	private OutputStream os; // /?????定义全局变量是方便调用,需要传值给Method，==============？？？？？
	private static final int LOG = 1;// 只能在类里面写，不能再方法里面写
	private static final int REG = 2;
	private static final int CHAT_ALL = 3;
	private static final int CHAT_ONE = 4;
	private static final int CHAT_RANDOM = 5;
	private static final int CHECT_ONLINE = 6;
	private static final int EXIT_SYSTEM = 7;
	private static final int HEART_BEAT = 8;
	ServerMethod method = null;

	public void run() {
		method = new ServerMethod(socket);
		try {
			os = socket.getOutputStream();
			InputStream is = socket.getInputStream();
			BufferedInputStream bis = new BufferedInputStream(is);
			ObjectInputStream ois = new ObjectInputStream(bis);
			ExportObject eo = null;
			while ((eo = (ExportObject) ois.readObject()) != null) {// while
				String content = eo.getNamePwd();
				int type = eo.getType();
				switch (type) {
				case LOG:
					String[] arr = content.split(",");
					method.log(arr[0], arr[1], os);
					break;
				case REG:
					String[] arr1 = content.split(",");
					method.reg(arr1[0], arr1[1], os);
					break;
				case CHAT_ALL:
					method.chatAll(eo.getContent(), os);
					break;
				case CHAT_ONE:
					method.chatOne(eo.getNamePwd(), eo.getContent(), os);
					break;
				case CHECT_ONLINE:
					method.checkOnline(eo, os);
					break;
				case CHAT_RANDOM:
					method.chatRandom(eo.getContent(), os);
					break;
				case EXIT_SYSTEM:
					method.exitSystem(os);
					break;
				case HEART_BEAT:
					method.heartBeat(eo.getContent());
					System.out.println();
					break;
				default:
					break;

				}

			}

		} catch (ClassNotFoundException e) {
			// e.printStackTrace();
		} catch (IOException e) {
			// e.printStackTrace();

		}
	};

	public ServerThread(Socket socket) {// 构造方法，使得创建对象能够传值socket进来
		super();
		this.socket = socket;
	}

}
