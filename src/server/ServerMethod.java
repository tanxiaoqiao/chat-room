package server;

import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.text.SimpleDateFormat;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import bean.ExportObject;
import bean.User;

import file.FileDemo;

//�жϷ���
public class ServerMethod {
	private Socket socket;
	private String currentName;
	static FileDemo sfile = new FileDemo();// ��Ҫ��static
	public static List<User> Users = sfile.load();
	static Map<String, Socket> onlineUserMap = new HashMap<String, Socket>();// ���߼��ϣ�static֮��͹���һ��map
	static Map<String, Socket> groupUserMap = new HashMap<String, Socket>();// С�鼯��
	boolean isLog = false;
	int num = 0;

	// ====================��¼����
	public void log(String name, String pwd, OutputStream os)
			throws IOException {
		while (num < 3) {
			for (User u : Users) {
				if (name.equals(u.getName())) {
					if (onlineUserMap.containsKey(name)) {
						os.write("���û��Ѿ�����\n".getBytes());
						return;
					}

					if (onlineUserMap.containsValue(onlineUserMap
							.get(currentName))) {
						os.write("��ǰ�ͻ��������û�ʹ�ã�����ʹ�������ͻ��˵�¼\n".getBytes());
						isLog = true;
						return;
					}

					if (u.getName().equals(name) && u.getPwd().equals(pwd)) {
						os.write("��¼�ɹ�\n".getBytes());
						currentName = name;
						onlineUserMap.put(name, socket);// ��¼�ɹ������뼯��
						String content = userInf(1);
						System.out.println(content);
						isLog = true;
						return;
					} else {
						os.write(("�û��������������\n").getBytes());
						num++;
						os.write(("�������" + num + "�Σ�����" + (3 - num) + "����\n")
								.getBytes());
						
						if (num == 3) {
							os.write("�Զ��˳�ϵͳ\n".getBytes());
							socket.close();
							return;
						}
						continue;
					}
				}
			}
			return;
		}

	}

	// ======================ע�᷽��
	public void reg(String name, String pwd, OutputStream os)
			throws IOException {

		for (User u : Users) {
			if (u.getName().equals(name)) {
				os.write("���û��Ѿ���ע��\n".getBytes());
				return;
			}

		}
		os.write("ע��ɹ�\n".getBytes());
		Users.add(new User(name, pwd));
		sfile.save(Users);// ���浽�ı�
	}

	// ==============�鿴�û���Ϣ
	public String userInf(int i) throws IOException {

		socket.getInetAddress();
		long time = System.currentTimeMillis();
		Date date = new Date(time);
		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH��mm��ss");
		String now = sf.format(date);
		if (i == 1) {
			return ("IP:" + socket.getInetAddress() + "�û���" + currentName + " "
					+ now + "��¼");
		}
		if (i == 2) {
			return ("IP:" + socket.getInetAddress() + "�û���" + currentName + " "
					+ now + "����");
		}
		return null;
	}

	// =============Ⱥ��
	public void chatAll(String content, OutputStream os) throws IOException {// ֮ǰû��Ⱥ������Ϊsocket�õ�static���ԣ�����û�д�ֵ����

		if (!isLog) {
			os.write("��ǰ�ͻ���û�е�¼��������exit�˳����µ�¼\n".getBytes());
			return;
		}
		content.replace("\n", "");
		if (content.equals("exit")) {
			os.write("�˳�Ⱥ��\n".getBytes());
			return;
		}
		System.out.println(content + "����������");// �������ˡ�����仯��
		for (String name : onlineUserMap.keySet()) {
			if (!name.equals(currentName)) {
				content += "\n";
				onlineUserMap.get(name).getOutputStream()
						.write((currentName + "����Ⱥ��Ϣ:" + content).getBytes());

			}

		}
	}

	// ==============˽��
	public boolean chatOne(String name, String content, OutputStream os)
			throws IOException {
		if (!isLog) {
			os.write("��ǰ�ͻ���û�е�¼��������exit�˳����µ�¼\n".getBytes());
			return false;
		}

		if (Users.contains(name)) {
			os.write("û�и��û�,������exit�˳�����ѡ��\n".getBytes());
			return false;
		}
		if (!onlineUserMap.containsKey(name)) {
			os.write("���û������ߣ�������exit�˳�����ѡ��\n".getBytes());
			return false;
		}
		for (String num : onlineUserMap.keySet()) {
			if (num.equals(name)) {
				content = currentName + "������Ϣ��" + content + "\n";
				onlineUserMap.get(num).getOutputStream()
						.write(content.getBytes());
			}
		}
		return true;
	}

	// =============�鿴�����û�
	public void checkOnline(ExportObject eo, OutputStream os)
			throws IOException {
		if (!isLog) {
			os.write("��ǰ�ͻ���û�е�¼\n".getBytes());
			return;
		}
		if (onlineUserMap.isEmpty()) {
			os.write(("��ǰû���û���¼\n").getBytes());
			return;
		}
		os.write(("��ǰ���ߵ��û��У�" + onlineUserMap.keySet() + "\n").getBytes());
	}

	// ===================��������
	public void chatRandom(String content, OutputStream os) throws IOException {
		String NAME = randomName();
		if (!isLog) {
			os.write("��ǰ�ͻ���û�е�¼��������exit�˳����µ�¼\n".getBytes());
			return;
		}

		content.replace("\n", "");
		if (content.equals("exit")) {
			os.write("�˳�Ⱥ��\n".getBytes());
			return;
		}
		for (String name : onlineUserMap.keySet()) {
			if (!name.equals(currentName)) {
				content += "\n";
				onlineUserMap.get(name).getOutputStream()
						.write((NAME + "����Ⱥ��Ϣ:" + content).getBytes());

			}

		}
	}

	// =======================��������
	public String randomName() {
		String n1 = "�������ӡ�";
		String n2 = "����ȫ����";
		String n3 = "����ԯ������";
		String n4 = "��ŷ���仨��";
		String n5 = "��ʧɢ������׵���";
		String n6 = "��թʬ�û���";
		String n7 = "���ö��ˡ�";
		String n8 = "���������ꡱ";
		String n9 = "��Ұ����������";
		String[] arr = { n1, n2, n3, n4, n5, n6, n7, n8, n9 };
		int i = (int) (Math.random() * 9);
		String randomName = arr[i];
		return randomName;

	}

	// ================�˳�ϵͳ
	public void exitSystem(OutputStream os) throws IOException {
		if (!isLog) {
			os.write("��ǰ�ͻ���û�е�¼\n".getBytes());
			return;
		}

		onlineUserMap.remove(currentName);
		String content = userInf(2);
		System.out.println(content);
	}

	// ===================����
	public void heartBeat(String content) {
		System.out.println(socket.getInetAddress() + content);
	}

	public ServerMethod(Socket socket) {
		super();
		this.socket = socket;
	}

}