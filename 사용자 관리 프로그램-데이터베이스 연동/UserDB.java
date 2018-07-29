package users;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class UserDB {
	Connection conn = null;
	Statement stmt = null;
	String table;
	
	public UserDB(Connection conn, String table){
		this.conn = conn;
		this.table = table;
		try {
			this.stmt = conn.createStatement();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	//사용자 추가
	public void addUser(User u){
		String stgender = new String();
		if (u.getGender() == 1)
			stgender = "남성";
		else if (u.getGender() == 2)
			stgender = "여성";
		else
			stgender = "공개하지않음";
		StringBuilder sb = new StringBuilder();
		String sql = sb.append("insert into " + this.table + " values('"+u.getId()+"','"+u.getName()+"',"+u.getAge()+",'"+stgender+"','"+u.getPw()+"');").toString();
		try {
			stmt.executeUpdate(sql);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	//사용자 삭제
	public void delUser(String id, String pw){
		StringBuilder sb = new StringBuilder();
        String sql = sb.append("delete from " + table + " where id = ")
                .append("'"+id+"'"+"and pw = ")
                .append("'"+pw+"'")
                .append(";")
                .toString();
        try {
            stmt.executeUpdate(sql);
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        	}
	}
	//사용자 정보 수정
	public void upDateUser(User u){
		StringBuilder sb = new StringBuilder();
        String sql = sb.append("update " + table + " set")
                .append(" name = ")
                .append("'" + u.getName() + "',")
                .append(" age = ")
                .append(u.getAge())
                .append(" where id = ")
                .append("'"+u.getId()+"'"+"and pw = ")
                .append("'"+u.getPw()+"'")
                .append(";").toString();
        try {
            stmt.executeUpdate(sql);
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
       		}
	}
	//전체 사용자 출력
	public void printAllUser(){
		StringBuilder sb = new StringBuilder();
        String sql = sb.append("select * from " + table)
                .append(";").toString();
        try {
			ResultSet rs = stmt.executeQuery(sql);
			while(rs.next()){
                System.out.print("아이디:"+rs.getString("id"));
                System.out.print(", 이름:"+rs.getString("name"));
                System.out.print(", 나이:"+rs.getInt("age"));
                System.out.print(", 성별:"+rs.getString("gender")+"\n");
           }
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	//사용자 
	public void findUser(String id){
		StringBuilder sb = new StringBuilder();
        String sql = sb.append("select * from " + table + " where")
                .append(" id = ")
                .append("'"+id+"'")
                .append(";").toString();
        try {
			ResultSet rs = stmt.executeQuery(sql);
			if (rs.next()){
			System.out.print("아이디:"+rs.getString("id"));
            System.out.print(", 이름:"+rs.getString("name"));
            System.out.print(", 나이:"+rs.getInt("age"));
            System.out.print(", 성별:"+rs.getString("gender")+"\n");
			}
			} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
	}
}
