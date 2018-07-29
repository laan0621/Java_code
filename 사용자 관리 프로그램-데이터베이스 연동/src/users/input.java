package users;

import java.util.Scanner;
import java.sql.*;

public class input {
	public static void main(String[] agrs) {
		Connection conn = null;
		Statement stmt = null;
		String database = "users";
		String url = "jdbc:mysql://localhost:3306/"+database;
		String id = "root";
		String pw = "no040621";
		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection(url, id, pw);
			stmt = conn.createStatement();
			String table = "users";
			StringBuilder sb = new StringBuilder();
			String sql = sb.append("create table if not exists "+table+"(")
					.append("id varchar(20),")
					.append("name varchar(20),")
					.append("age int,")
					.append("gender varchar(7),")
					.append("pw varchar(20)")
					.append(");").toString();
			stmt.execute(sql);
			Scanner scanner = new Scanner(System.in);
			int num = 0;
			System.out.print("1:����� �߰�\n2:����� ���� ����\n3:����� ����\n4:����� �˻�\n5:����� ��ü����\n6:����\n");
			do {
				UserDB uDB = new UserDB(conn,table);
				System.out.println("�Է�:");
				num = scanner.nextInt();
				switch (num) {
				case 1://����� �߰�
						String checkpw = "";
						User u1 = new User();
						do {
						System.out.println("���̵� �Է�:");
						u1.setId(scanner.next());
						System.out.println("��й�ȣ �Է�:");
						u1.setPw(scanner.next());
						System.out.println("��й�ȣ Ȯ��:");
						checkpw = scanner.next();
						System.out.println("�̸� �Է�:");
						u1.setName(scanner.next());
						System.out.println("���� �Է�(1:����, 2:����, ��Ÿ:�������� ����):");
						u1.setGender(scanner.nextInt());
						System.out.println("���� �Է�:");
						u1.setAge(scanner.nextInt());
						try {
							StringBuilder sb2 = new StringBuilder();
							String sql2 = sb2.append("select * from " + table)
					                .append(";").toString();
							ResultSet rs = stmt.executeQuery(sql2);
							int check = 1;
							while(rs.next()){
								if(u1.getId().equals(rs.getString("id"))) check = 0;
							}
							if (check == 1){
								if (!(checkpw.equals(u1.getPw())))
										System.out.println("��й�ȣ�� �߸� �Է��ϼ̽��ϴ�.");
								else uDB.addUser(u1);
							}
							else System.out.println("�ߺ��� ���̵� �Դϴ�");
						} catch (SQLException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
						
					} while (checkpw.equals(u1.getPw()) == false);
					break;
				case 2://�����  ���� ����
					User u2 = new User();
					System.out.println("���̵� �Է�:");
					u2.setId(scanner.next());
					System.out.println("��й�ȣ �Է�:");
					u2.setPw(scanner.next());
					System.out.println("������ �̸� �Է�:");
					u2.setName(scanner.next());
					System.out.println("������ ���� �Է�:");
					u2.setAge(scanner.nextInt());
					uDB.upDateUser(u2);
					break;
				case 3://����� ����
					String id2;
					String pw2;
					System.out.println("���̵� �Է�:");
					id2 = scanner.next();
					System.out.println("��й�ȣ �Է�:");
					pw2 = scanner.next();
					uDB.delUser(id2, pw2);
					break;
				case 4://����� �˻�
					String id3;
					System.out.println("���̵� �Է�:");
					id3 = scanner.next();
					uDB.findUser(id3);
					break;
				case 5://����� ��ü����
					System.out.println("����� ����Ʈ");
					uDB.printAllUser();
					break;
				case 6://����
					break;
				default:
					System.out.println("�߸��� ���� �Է��ϼ̽��ϴ�.");
				}
			} while (num != 6);
			conn.close();
		} catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            try {
                //����� connection �ݱ�
                if(conn != null && !conn.isClosed())
                    conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
	}
}
