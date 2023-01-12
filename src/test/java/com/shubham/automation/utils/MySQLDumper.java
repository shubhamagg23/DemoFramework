package com.shubham.automation.utils;

	import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;

	public class MySQLDumper {

	private static String ip="172.28.146.165";
	private static String port="3306";
	private static String database1="sakurai";
	private static String database2="sakurai_automation";
	private static String user="sakurai";
	private static String pass="sakurai";
	private static String path="E:\\MySQLDump\\dbname.sql";

	public static void export(){
	String dumpCommand = "mysqldump " + database1 + " -h " + ip + " -u " + user +" -p" + pass;
	Runtime rt = Runtime.getRuntime();
	File test=new File(path);
	PrintStream ps;

	try{
	Process child = rt.exec(dumpCommand);
	ps=new PrintStream(test);
	InputStream in = child.getInputStream();
	int ch;
	while ((ch = in.read()) != -1) {
	ps.write(ch);
	System.out.write(ch); //to view it by console
	}

	InputStream err = child.getErrorStream();
	while ((ch = err.read()) != -1) {
	System.out.write(ch);
	}
	}catch(Exception exc) {
	exc.printStackTrace();
	}
	}
	public static void export1() throws IOException, InterruptedException{
		String dumpCommand = "mysqldump " + database1 + " -h " + ip + " -u " + user +" -p" + pass +" > "+path;
		System.out.println("dumpCommand"+ dumpCommand);
//		Runtime rt = Runtime.getRuntime();
//		File test=new File(path);
//		PrintStream ps;
//		File f = new File("export.bat");
//		FileOutputStream fos = new FileOutputStream(f);
//		fos.write(dumpCommand.getBytes());
		//System.out.println("byte"+ executeCmd.getBytes());
//		fos.close();
//		Process run = Runtime.getRuntime().exec("cmd /C start /wait export.bat");
//
//		//Process run = Runtime.getRuntime().exec("cmd.exe" ,"/C", executeCmd);
//		int processComplete=run.waitFor();
//
//		if(processComplete == 0){
//			Runtime.getRuntime().exec("taskkill /IM cmd.exe");
//				System.out.println("success");
//
//				} else {
//
//					System.out.println("restore failure");
//
//				}
		}
	public static void importCompleteDatabaseDump() throws IOException, InterruptedException{
		String executeCmd = "";
		String user1="automation_user";
		String pass1="automationpass";
		String dumpPath="C:/Users/rohitsingh/Desktop/automation-fixed.sql";
		String dumpPath1="C:/Users/rohitsingh/Documents/dumps/Dump20150314.sql";
		String completeDatabaseDumpPath="E:\\MySQLDump\\dbname.sql";

		executeCmd = "mysql" + " -h " + ip + " -u " + user1 +" -p" + pass1 + " " + database2+ "< "+ completeDatabaseDumpPath;
		
		//mysql -h 172.28.146.165 -u automation_user -pautomationpass sakurai_automation  < automation-fixed.sql
System.out.println("executeCmd==="+ executeCmd);
//		Process runtimeProcess =Runtime.getRuntime().exec(executeCmd);
//		int processComplete = runtimeProcess.waitFor();
//		if(processComplete == 0){
//
//		System.out.println("success");
//
//		} else {
//
//			System.out.println("restore failure");
//
//		}
//File f = new File("completeDatabaseRestore.bat");
//FileOutputStream fos = new FileOutputStream(f);
//fos.write(executeCmd.getBytes());
////System.out.println("byte"+ executeCmd.getBytes());
//fos.close();
//Process run = Runtime.getRuntime().exec("cmd /C start /wait completeDatabaseRestore.bat");
//
////Process run = Runtime.getRuntime().exec("cmd.exe" ,"/C", executeCmd);
//int processComplete=run.waitFor();
//if(processComplete == 0){
//	Runtime.getRuntime().exec("taskkill /IM cmd.exe");
//		System.out.println("success");
//
//		} else {
//
//			System.out.println("restore failure");
//
//		}
//return true;
	}
	public static void importAutomationDump() throws IOException, InterruptedException{
		String executeCmd = "";
		String user1="automation_user";
		String pass1="automationpass";
		String dumpPath="C:/Users/rohitsingh/Desktop/automation-fixed.sql";
		String dumpPath1="C:/Users/rohitsingh/Documents/dumps/Dump20150314.sql";
		

		executeCmd = "mysql" + " -h " + ip + " -u " + user1 +" -p" + pass1 + " " + database2+ "< "+ dumpPath;
		
		//mysql -h 172.28.146.165 -u automation_user -pautomationpass sakurai_automation  < automation-fixed.sql
System.out.println("executeCmd==="+ executeCmd);
//		Process runtimeProcess =Runtime.getRuntime().exec(executeCmd);
//		int processComplete = runtimeProcess.waitFor();
//		if(processComplete == 0){
//
//		System.out.println("success");
//
//		} else {
//
//			System.out.println("restore failure");
//
//		}
//File f = new File("restore.bat");
//FileOutputStream fos = new FileOutputStream(f);
//fos.write(executeCmd.getBytes());
////System.out.println("byte"+ executeCmd.getBytes());
//fos.close();
//Process run = Runtime.getRuntime().exec("cmd /C start /wait restore.bat");
//
////Process run = Runtime.getRuntime().exec("cmd.exe" ,"/C", executeCmd);
//int processComplete=run.waitFor();
////
//if(processComplete == 0){
//Runtime.getRuntime().exec("taskkill /IM cmd.exe");
//System.out.println("success");
//		} else {
//
//			System.out.println("restore failure");
//
//		}
//return true;
	}
	public static void main(String args[]) throws IOException, InterruptedException{
	//export();
//		export1();
		//importCompleteDatabaseDump();
		importAutomationDump();
	}
	}
