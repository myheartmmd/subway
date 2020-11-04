package work;

import java.awt.List;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Stack;

import javax.sound.sampled.Line;

public class newtrain {
	public static void read(String path,ArrayList<station> stationall) {
		try {
			File file=new File(path);
			BufferedReader reader=new BufferedReader(new FileReader(file));
			String temp=null;
			while ((temp=reader.readLine())!=null) {
				String[] data=temp.split(" ");
				for(int i=1;i<data.length;i++) {
					boolean a=true;
					station statemp=new station();
					statemp.addLine(data[0]);
					statemp.setname(data[i]);
					for(station temp1:stationall) {
						if((temp1.getname()).equals(statemp.getname())) {
							temp1.addLine(data[0]);
							a=false;
						}
					}
					if(a==true)
						stationall.add(statemp);
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("error");
			e.printStackTrace();
		}
		
	}
	public static void readline(String path,ArrayList<line> lineall) {
		try {
			File file=new File(path);
			BufferedReader reader=new BufferedReader(new FileReader(file));
			String temp=null;
			int a=0;
			while ((temp=reader.readLine())!=null) {
				String[] data=temp.split(" ");
				line temp2=new line();
				temp2.setname(data[0]);
				for(int i=1;i<data.length;i++) {
					temp2.linename.add(data[i]);
				}
				lineall.add(temp2);
			}
			
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("error");
			e.printStackTrace();
		}
	}
	
	public static void findaround(station old,ArrayList<line> lineset,ArrayList<station> openlist,ArrayList<station> stationSet,int distance) {
		for(String temp:old.getLine()) {
			for(line temp2:lineset) {
				if((temp2.getname()).equals(temp)) {
					for(String temp3:temp2.linename) {
						if(temp3.equals(old.getname())) {
							int result=temp2.linename.indexOf(temp3);
							for(station newtemp:stationSet) {
								if (result==0) {
									if (newtemp.getname().equals(temp2.linename.get(result+1))){
										if(!openlist.contains(newtemp)) {
											openlist.add(newtemp);
											newtemp.cnt=distance;
											newtemp.lastation=old;
										}
									}	
								}
								else if (result==temp2.linename.size()-1 ) {
									if (newtemp.getname().equals(temp2.linename.get(result-1))){
										if(!openlist.contains(newtemp)) {
											openlist.add(newtemp);
											newtemp.cnt=distance;
											newtemp.lastation=old;
										}
									}	
								}
								else {
									if (newtemp.getname().equals(temp2.linename.get(result+1))||newtemp.getname().equals(temp2.linename.get(result-1))){
										if(!openlist.contains(newtemp)) {
											openlist.add(newtemp);
											newtemp.cnt=distance;
											newtemp.lastation=old;
										}
									}	
								}	
							}
						}
					}
				}
			}
		}
	}

	public static boolean isnear(station a,station b,ArrayList<line> lineset) {
		ArrayList<String> a1=a.getLine();
		ArrayList<String> b1=b.getLine();
		for(String temp1:a1) {
			for(String temp2:b1) {
				if(temp1.equals(temp2)) {
					for(line x:lineset) {
						if(x.getname().equals(temp1)) {
							for(int i=0;i<x.linename.size()-1;i++) {
//								System.out.println(x.linename.get(i)+" "+temp1+" "+x.linename.get(i+1)+" "+temp2);
								if((x.linename.get(i).equals(a.getname())&&(x.linename.get(i+1).equals(b.getname())))||(x.linename.get(i).equals(b.getname())&&(x.linename.get(i+1).equals(a.getname())))){
									return true;
								}
							}
						}
					}
				}
			}
		}
		return false;
	}
	
	
	public static boolean findend(ArrayList<station> openlist,station end) {
		for(station temp:openlist) {
			if(end.getname().equals(temp.getname())) {
				return true;
			}
		}
		return false;
	}
	public static void main(String[] args) {
		// TODO 自动生成的方法存根
		ArrayList<station> stationSet= new ArrayList<>(); 
		ArrayList<line> lineset=new ArrayList<>();
		ArrayList<station> openlist=new ArrayList<>();
		Stack<station> printlist=new Stack<>();
		String path="D:/train.txt";
		read(path,stationSet);
		readline(path, lineset);
		System.out.println("起点站");
		Scanner scan=new Scanner(System.in);
		String input=scan.next();
		System.out.println("终点站");
		String input2=scan.next();
		station start=new station();
		station end=new station();
		int distance=1;
		for(station temp:stationSet) {
			if(temp.getname().equals(input)) {
				start=temp;
				System.out.println("已找到起点站");
			}
		}
		for(station temp:stationSet) {
			if(temp.getname().equals(input2)) {
				end=temp;
				System.out.println("已找到终点站");
			}
		}
//		System.out.println(isnear(start, end, lineset));
		openlist.add(start);
		start.cnt=0;
		findaround(start, lineset, openlist, stationSet,distance);
		while(!findend(openlist, end)) {
			ArrayList<station> openlist2=new ArrayList<>(); 
			for(station x2:openlist) {
				openlist2.add(x2);
			}
			for(station x1:openlist2) {
				findaround(x1, lineset, openlist, stationSet,distance);
			}
			distance++;
		}
		System.out.println("一共需要乘坐"+distance+"站");
//		for(station a:openlist) {
//			System.out.println(a.getname()+a.cnt);
//		}
		station temp=new station();
		temp=end;
		while (distance>=0) {
			printlist.push(temp);
			temp=temp.lastation;
			distance--;
		}
		while (!(printlist.size()==1)) {
			System.out.print(printlist.pop().getname()+"-->");
		}
		System.out.print(printlist.pop().getname());
//		for(line temp3:lineset) {
//			System.out.println(temp3.getname()+temp3.linename);
//		}
//		for(station temp:stationSet) {
//			System.out.println(temp.getname()+temp.getLine());
//		}
	}

}
