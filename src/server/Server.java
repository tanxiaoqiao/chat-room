package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

//=============================服务器
public class Server {
	public static void main(String[] args) {

		try {
			final ServerSocket server = new ServerSocket(9301);// 创建socket时，需要加final

			new Thread() {

				public void run() {
					try {
						while (true) {
							Socket socket = server.accept();// 创建socket时，最上面需要加final
							Admin admin = new Admin(socket);
							new ServerThread(socket).start();
						}

					} catch (IOException e) {
						e.printStackTrace();
					}
				};
			}.start();
			System.out.println("服务器开始接收~");
		} catch (IOException e) {
			// 此处省略，不然容易报错
		}

	}
}
