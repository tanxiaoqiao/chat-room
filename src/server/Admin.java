package server;

import java.io.IOException;
import java.net.Socket;
import java.util.Arrays;
import java.util.Scanner;

//����Ա����
public class Admin extends ServerMethod {

	public Admin(Socket socket) {
		super(socket);

	}

	private void mian() throws IOException {
		Scanner sc = new Scanner(System.in);
		System.out.println("������ָ�1.ֹͣ������ʾ 2�Ͽ�ָ���ͻ������� ");
		String choose = sc.nextLine();
		if (choose.matches("^[1-2]$")) {
			System.out.println("������1-2֮�������");
			return;
		}
		int i = Integer.parseInt(choose);
		while (true) {

			switch (i) {
			case 1:
				heartBeat(choose);
				break;
			case 2:
				for (String name : onlineUserMap.keySet()) {
					System.out.println("��ǰ�����û��У�" + onlineUserMap.keySet()
							+ "\n" + "������Ҫǿ�����ߵ��û����ԣ�����");
					String line = sc.nextLine();
					String[] arr = line.split(",");
					onlineUserMap.get(Arrays.toString(arr)).close();
					break;
				}
			}
		}
	}

	@Override
	public void heartBeat(String content) {
		System.out.print("");
	}

}
