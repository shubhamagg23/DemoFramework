package com.shubham.automation.utils;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;


import com.mysql.jdbc.Statement;
public class DatabaseUtility {
	final String _dbFile         = "automation-fixed.sql";
    final String _driverClass    = "com.mysql.jdbc.Driver";
//    final static String _url  = "jdbc:mysql://172.28.146.165:3306/sakurai_automation";
    final static String _url  = "jdbc:mysql://e2e-sak-db-databasecluster-30g7tshcq0g3.cluster-cokyol2yvogt.us-east-1.rds.amazonaws.com:3306/sakurai";
    private static String _testDir        = "testData";
    
    public static HashMap<String ,String> getAnswersForMLQuiz(String quizId) throws SQLException, ClassNotFoundException  {
    	final String query="select distinct q.question_text, a.text, (a.correct_response_id is not null) correct from " +
    		    "answer_choices a, "+
    		    "interactions i, "+ 
    		    "quiz_questions qq, "+
    		    "questions q "+
    		    "where" + " "+
    		    "qq.quiz_id = "+quizId+" and"+ " "+
    		    "qq.question_id = q.id and"+ " "+
    		    "qq.question_id = i.question_id and"+ " "+
    		    "a.interaction_id = i.id and"+ " "+
    		    "a.correct_response_id is true"+" "+
    		    "order by q.question_text";
    HashMap<String , String> questionAnswers=new HashMap<String, String>();
    Class driverClass = Class.forName("com.mysql.jdbc.Driver");
    Connection jdbcConnection = DriverManager.getConnection(
                _url,  "automation_user", "automationpass");
     
     Statement statement = (Statement) jdbcConnection.createStatement();
     ResultSet rs=statement.executeQuery(query);
     String questions,answers;
     while (rs.next()){
    	 questions=rs.getString(1);	 
     System.out.println("questions===" +rs.getString(1));
     questions=questions.replace("</sub>", "");
     questions=questions.replace("<sub>", "");
     questions=questions.replace("<hint>", "");
     questions=questions.replace("</hint>", "");
 	//System.out.println("questions=="+ questions);
 	questions=questions.replace("&rarr;", "?");
 	questions=questions.replace("&minus;", "?");
 	//System.out.println("questions=="+ questions);
 	questions=questions.replace("</sup>", "");
 	questions=questions.replace("<sup>", "");
 	//System.out.println("questions=="+ questions);
 	questions=questions.replace("&#8652;", "?");
 	questions=questions.replace("&minus;", "?");
 	questions=questions.replace("<i>", "");
 	questions=questions.replace("</i>", "");
 	questions=questions.replace("<br /><br />","\n\n");
//    System.out.println("questions=="+ questions);
    questions=questions.replace("<br />","\n");
//    System.out.println("questions=="+ questions);
 	System.out.println("questions=="+ questions);
     answers=rs.getString(2);
     System.out.println("answers==" +rs.getString(2));
     answers=answers.replace("</sub>", "");
 	answers=answers.replace("<sub>", "");
 	//System.out.println("answers=="+ answers);
 	answers=answers.replace("&rarr;", "?");
 	answers=answers.replace("&#8652;", "?");
 	answers=answers.replace("&minus;", "-");
 	//System.out.println("answers=="+ answers);
 	answers=answers.replace("</sup>", "");
 	answers=answers.replace("<sup>", "");
 	answers=answers.replace("<i>", "");
 	answers=answers.replace("</i>", "");
 	answers=answers.replace("&ndash;", "-");
 	System.out.println("answers=="+ answers);
 	answers=answers.trim();
    questionAnswers.put(questions, answers);
     
     }
    
           String q = "";
    //  File f = new File("C:\\Users\\rohitsingh\\Desktop\\automation-fixed.sql"); // source path is the absolute path of dumpfile.
      try {
      //    BufferedReader bf = new BufferedReader(new FileReader(f));
              String line = null;
        //      line = bf.readLine();
              while (line != null) {
                  q = q + line + "\n";
        //          line = bf.readLine();
              }
          } catch (Exception ex) {
              ex.printStackTrace();
          }
      // Now we have the content of the dumpfile in 'q'.
      // We must separate the queries, so they can be executed. And Java Simply does this:
//      String[] commands = q.split(";");
           try {
//        	   statement.execute("SET FOREIGN_KEY_CHECKS=0");
//        	   statement.execute("TRUNCATE TABLE sections");
//        	   rs=statement.executeQuery("select name from sections");
//        	   while (rs.next())
//        		     System.out.println(rs.getString(1));
//          for (String s : commands) {
//           System.out.println(s);
//              statement.execute(s);
//              System.out.println("****************************");
//              
//          }
          System.out.println("done");
      } catch (Exception ex) {
       System.out.println("inside catch");
       ex.printStackTrace();
      }
           //statement.execute("SET FOREIGN_KEY_CHECKS=1");
      jdbcConnection.close();
	return questionAnswers;
      
}
    public static HashMap<String ,String> getAnswersForPracticeExam(String quizId) throws SQLException, ClassNotFoundException  {
    	final String query="select distinct q.question_text, a.text, (a.correct_response_id is not null) correct from " +
    		    "answer_choices a, "+
    		    "interactions i, "+ 
    		    "nclex_exam_questions eq, "+
    		    "questions q "+
    		    "where" + " "+
    		    "eq.nclex_exam_id = "+quizId+" and"+ " "+
    		    "eq.question_id = q.id and"+ " "+
    		    "eq.question_id = i.question_id and"+ " "+
    		    "a.interaction_id = i.id and"+ " "+
    		    "a.correct_response_id is true"+" "+
    		    "order by q.question_text";
    	
    	final String query1 = "SELECT q.question_text, a.text, (a.correct_response_id IS NOT NULL) correct FROM "
    			+ "answer_choices a, "
    			+ "interactions i, "
    			+ "quiz_questions qq, "
    			+ "questions q "
    			+ "WHERE qq.quiz_id = "+quizId+" AND"
    					+ " qq.question_id = q.id AND "
    					+ "qq.question_id = i.question_id AND "
    					+ "a.interaction_id = i.id AND "
    					+ "a.correct_response_id IS TRUE "
    					+ "ORDER BY q.question_text";
    	
   HashMap<String , String> questionAnswers=new HashMap<String, String>();
    Class driverClass = Class.forName("com.mysql.jdbc.Driver");
    Connection jdbcConnection = DriverManager.getConnection(
                _url,  "sumita", "sumita123");
     
     Statement statement = (Statement) jdbcConnection.createStatement();
     ResultSet rs=statement.executeQuery(query1);
     String questions,answers;
     int i=0;
     while (rs.next()){
    	 i++;
    	 questions=rs.getString(1);	 
    	 System.out.println("questions:"+questions);
    // System.out.println("questions===" +rs.getString(1));
     questions=questions.replace("<b>", "");
     questions=questions.replace("</b>", "");
     questions=questions.replace("</sub>", "");
     questions=questions.replace("<sub>", "");
     questions=questions.replace("<hint>", "");
     questions=questions.replace("</hint>", "");
     questions=questions.replace("<gloss>", "");
     questions=questions.replace("</gloss>", "");
 	//System.out.println("questions=="+ questions);
 	questions=questions.replace("&rarr;", "?");
 	questions=questions.replace("&minus;", "?");
 	//System.out.println("questions=="+ questions);
 	questions=questions.replace("</sup>", "");
 	questions=questions.replace("<sup>", "");
 	//System.out.println("questions=="+ questions);
 	questions=questions.replace("&#8652;", "?");
 	questions=questions.replace("&minus;", "?");
 	questions=questions.replace("<i>", "");
 	questions=questions.replace("</i>", "");
 	questions=questions.replace("<br /><br />","\n\n");
//    System.out.println("questions=="+ questions);
    questions=questions.replace("<br />","\n");
//    System.out.println("questions=="+ questions);
 	System.out.println("questions["+i+"]==" + questions);
     answers=rs.getString(2);
   //  System.out.println("answers==" +rs.getString(2));
     answers=answers.replace("</sub>", "");
 	answers=answers.replace("<sub>", "");
 	//System.out.println("answers=="+ answers);
 	answers=answers.replace("&rarr;", "?");
 	answers=answers.replace("&#8652;", "?");
 	answers=answers.replace("&minus;", "-");
 	//System.out.println("answers=="+ answers);
 	answers=answers.replace("</sup>", "");
 	answers=answers.replace("<sup>", "");
 	answers=answers.replace("<i>", "");
 	answers=answers.replace("</i>", "");
 	answers=answers.replace("&ndash;", "-");
 	System.out.println("answers["+i+"]=="+ answers);
 	answers=answers.trim();
 	questions=questions.trim();
    questionAnswers.put(questions, answers);
     
     }
    
           String q = "";
    //  File f = new File("C:\\Users\\rohitsingh\\Desktop\\automation-fixed.sql"); // source path is the absolute path of dumpfile.
      try {
      //    BufferedReader bf = new BufferedReader(new FileReader(f));
              String line = null;
        //      line = bf.readLine();
              while (line != null) {
                  q = q + line + "\n";
        //          line = bf.readLine();
              }
          } catch (Exception ex) {
              ex.printStackTrace();
          }
      // Now we have the content of the dumpfile in 'q'.
      // We must separate the queries, so they can be executed. And Java Simply does this:
//      String[] commands = q.split(";");
           try {
//        	   statement.execute("SET FOREIGN_KEY_CHECKS=0");
//        	   statement.execute("TRUNCATE TABLE sections");
//        	   rs=statement.executeQuery("select name from sections");
//        	   while (rs.next())
//        		     System.out.println(rs.getString(1));
//          for (String s : commands) {
//           System.out.println(s);
//              statement.execute(s);
//              System.out.println("****************************");
//              
//          }
          System.out.println("done");
      } catch (Exception ex) {
       System.out.println("inside catch");
       ex.printStackTrace();
      }
           //statement.execute("SET FOREIGN_KEY_CHECKS=1");
      jdbcConnection.close();
	return questionAnswers;
      
} 
//    public static void main(String [] args) throws ClassNotFoundException, SQLException {
//    	String quizId="4394";
//    	final String query1="select distinct q.question_text, a.text, (a.correct_response_id is not null) correct from " +
//    		    "answer_choices a, "+
//    		    "interactions i, "+ 
//    		    "quiz_questions qq, "+
//    		    "questions q "+
//    		    "where" + " "+
//    		    "qq.quiz_id = "+quizId+" and"+ " "+
//    		    "qq.question_id = q.id and"+ " "+
//    		    "qq.question_id = i.question_id and"+ " "+
//    		    "a.interaction_id = i.id and"+ " "+
//    		    "a.correct_response_id is true"+" "+
//    		    "order by q.question_text";
//    	HashMap<String ,String> a =getAnswersForMLQuiz(quizId);
//        
//    }
    
    public static String getMasteryIndexOfMLQuiz(String quizId) throws SQLException, ClassNotFoundException  {
    	String masteryIndex=null;
    	final String query="select q.quiz_id, q.status, s.mastery_index  from quiz_questions q, student_section_mastery s"+
" where q.quiz_id = s.quiz_id and q.quiz_id ="+ quizId;
    HashMap<String , String> questionAnswers=new HashMap<String, String>();
    Class driverClass = Class.forName("com.mysql.jdbc.Driver");
    Connection jdbcConnection = DriverManager.getConnection(
                _url,  "automation_user", "automationpass");
     
     Statement statement = (Statement) jdbcConnection.createStatement();
     ResultSet rs=statement.executeQuery(query);
     String questions,answers;
     while (rs.next()){
    	 questions=rs.getString(1);	 
     System.out.println("questions===" +rs.getString(1));
     
     System.out.println("answers==" +rs.getString(2));
     
     System.out.println("master index==" +rs.getString(3));
 
 	masteryIndex=answers=rs.getString(3).trim();
    questionAnswers.put(questions, answers);
     
     }
    
           String q = "";
    //  File f = new File("C:\\Users\\rohitsingh\\Desktop\\automation-fixed.sql"); // source path is the absolute path of dumpfile.
      try {
      //    BufferedReader bf = new BufferedReader(new FileReader(f));
              String line = null;
        //      line = bf.readLine();
              while (line != null) {
                  q = q + line + "\n";
        //          line = bf.readLine();
              }
          } catch (Exception ex) {
              ex.printStackTrace();
          }
 
           try {

          System.out.println("done");
      } catch (Exception ex) {
       System.out.println("inside catch");
       ex.printStackTrace();
      }
 
      jdbcConnection.close();
	return masteryIndex;
      
}
    
    public static void main(String [] args) throws ClassNotFoundException, SQLException {
    	
        
    }
    
}
