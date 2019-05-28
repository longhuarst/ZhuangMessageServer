package Database;


import java.sql.*;


public class Database {
	

	private static class Holder {
		private static Database instance = new Database();
	}

	private Database() {

	}

	public static Database getInstance() {
		return Holder.instance;
	}

	// =======================================

	
	// JDBC 驱动名及数据库 URL
    static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";  
    static final String DB_URL = "jdbc:mysql://www.hongyiweichuang.com:3306/WaterGetter";
	
 // 数据库的用户名与密码，需要根据自己的设置
    static final String USER = "root";
    static final String PASS = "zhuang1234@@";
    
    
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		Connection conn = null;
        Statement stmt = null;
        try{
            // 注册 JDBC 驱动
            Class.forName("com.mysql.cj.jdbc.Driver");
        
            // 打开链接
            System.out.println("连接数据库...");
            conn = DriverManager.getConnection(DB_URL,USER,PASS);
        
            // 执行查询
            System.out.println(" 实例化Statement对象...");
            stmt = conn.createStatement();
            String sql;
            sql = "select id,message,date from WaterGetter.`085e2d2c-77ed-416e-b852-5f83b0392fd6`";
            ResultSet rs = stmt.executeQuery(sql);
        
            // 展开结果集数据库
            while(rs.next()){
                // 通过字段检索
                int id  = rs.getInt("id");
                String message = rs.getString("message");
                Date date = rs.getDate("timestamp");
    
                // 输出数据
                System.out.print("ID: " + id);
                System.out.print(", message: " + message);
                System.out.print(", date: " + date);
                System.out.print("\n");
            }
            // 完成后关闭
            rs.close();
            stmt.close();
            conn.close();
        }catch(SQLException se){
            // 处理 JDBC 错误
            se.printStackTrace();
        }catch(Exception e){
            // 处理 Class.forName 错误
            e.printStackTrace();
        }finally{
            // 关闭资源
            try{
                if(stmt!=null) stmt.close();
            }catch(SQLException se2){
            }// 什么都不做
            try{
                if(conn!=null) conn.close();
            }catch(SQLException se){
                se.printStackTrace();
            }
        }
        System.out.println("Goodbye!");
    }
	

	

}
