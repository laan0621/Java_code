package users;

import java.util.Scanner;
import java.sql.*;

public class input {
	public static void main(String[] agrs) {
		Connection conn = null;
		Statement stmt = null;
		String database = "users";
		String url = "jdbc:mysql://localhost:3306/" + database;
		String id = "root";
		String pw = "";
		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection(url, id, pw);
			stmt = conn.createStatement();
			String table = "users";
			StringBuilder sb = new StringBuilder();
			String sql = sb.append("create table if not exists " + table + "(").append("id varchar(20),")
					.append("name varchar(20),").append("age int,").append("gender varchar(7),")
					.append("pw varchar(20)").append(");").toString();
			stmt.execute(sql);
			Scanner scanner = new Scanner(System.in);
			int num = 0;
			System.out.print("1:사용자 추가\n2:사용자 정보 수정\n3:사용자 삭제\n4:사용자 검색\n5:사용자 전체보기\n6:종료\n");
			do {
				UserDB uDB = new UserDB(conn, table);
				System.out.println("입력:");
				num = scanner.nextInt();
				switch (num) {
				case 1:// 사용자 추가
					String checkpw = "";
					User u1 = new User();
					do {
						System.out.println("아이디 입력:");
						u1.setId(scanner.next());
						System.out.println("비밀번호 입력:");
						u1.setPw(scanner.next());
						System.out.println("비밀번호 확인:");
						checkpw = scanner.next();
						System.out.println("이름 입력:");
						u1.setName(scanner.next());
						System.out.println("성별 입력(1:남성, 2:여성, 기타:공개하지 않음):");
						u1.setGender(scanner.nextInt());
						System.out.println("나이 입력:");
						u1.setAge(scanner.nextInt());
						try {
							StringBuilder sb2 = new StringBuilder();
							String sql2 = sb2.append("select * from " + table).append(";").toString();
							ResultSet rs = stmt.executeQuery(sql2);
							int check = 1;
							while (rs.next()) {
								if (u1.getId().equals(rs.getString("id")))
									check = 0;
							}
							if (check == 1) {
								if (!(checkpw.equals(u1.getPw())))
									System.out.println("비밀번호를 잘못 입력하셨습니다.");
								else
									uDB.addUser(u1);
							} else
								System.out.println("중복된 아이디 입니다");
						} catch (SQLException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}

					} while (checkpw.equals(u1.getPw()) == false);
					break;
				case 2:// 사용자 정보 수정
					User u2 = new User();
					System.out.println("아이디 입력:");
					u2.setId(scanner.next());
					System.out.println("비밀번호 입력:");
					u2.setPw(scanner.next());
					System.out.println("수정할 이름 입력:");
					u2.setName(scanner.next());
					System.out.println("수정할 나이 입력:");
					u2.setAge(scanner.nextInt());
					uDB.upDateUser(u2);
					break;
				case 3:// 사용자 삭제
					String id2;
					String pw2;
					System.out.println("아이디 입력:");
					id2 = scanner.next();
					System.out.println("비밀번호 입력:");
					pw2 = scanner.next();
					uDB.delUser(id2, pw2);
					break;
				case 4:// 사용자 검색
					String id3;
					System.out.println("아이디 입력:");
					id3 = scanner.next();
					uDB.findUser(id3);
					break;
				case 5:// 사용자 전체보기
					System.out.println("사용자 리스트");
					uDB.printAllUser();
					break;
				case 6:// 종료
					break;
				default:
					System.out.println("잘못된 값을 입력하셨습니다.");
				}
			} while (num != 6);
			conn.close();
			scanner.close();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				// 사용한 connection 닫기
				if (conn != null && !conn.isClosed())
					conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
}
