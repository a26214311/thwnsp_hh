import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;


public class t {

	static int c=0;
	static int sn=0;
	public static void main(String[] args) {
		String folder = "T/TK/";
		c=0;
		sn=0;
		exptalk(folder);
		System.out.println("alllines:"+c);
		System.out.println("allwords:"+sn);
		System.out.println("finish");
	}

	
	public static void exttalk(String folderpath) {
		File f = null;
		f = new File(folderpath);
		File[] files = f.listFiles(); 
		for(int i=0;i<files.length;i++)
		{
			if(files[i].isDirectory()){
				String newfolderpath = folderpath+"/"+files[i].getName();
				exttalk(newfolderpath);
			}else{
				ArrayList<String> r = readfile(files[i]);
				try {
					String newfilepath = files[i].getPath().replaceAll("/TK/", "/tkf1/");
		    		File newfile = new File(newfilepath);
		    		new File(newfile.getParent()).mkdirs();
		    		FileOutputStream fo = new FileOutputStream(newfile);
		    		OutputStreamWriter out = new OutputStreamWriter(fo,"utf-8");	
					out.write(r.get(1));
					out.close();
		    		if(r.get(0).length()>3){
		    			String newfilepath2 = files[i].getPath().replaceAll("/TK/", "/tkf2/");
			    		File newfile2 = new File(newfilepath2);
			    		new File(newfile2.getParent()).mkdirs();
			    		FileOutputStream fo2 = new FileOutputStream(newfile2);
			    		OutputStreamWriter out2 = new OutputStreamWriter(fo2,"utf-8");	
						out2.write(r.get(0));
						out2.close();
		    		}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
    	try {
    		
		} catch (Exception e) {
			e.printStackTrace();
		}	
	}
	
	public static void exptalk(String folderpath){
		File f = null;
		f = new File(folderpath);
		File[] files = f.listFiles(); 
		for(int i=0;i<files.length;i++)
		{
			if(files[i].isDirectory()){
				String newfolderpath = folderpath+"/"+files[i].getName();
				exptalk(newfolderpath);
			}else{
				ArrayList<String> r = readexpfile(files[i]);
				try {
					String newfilepath = files[i].getPath().replaceAll("/TK/", "/tkf1/");
		    		File newfile = new File(newfilepath);
		    		new File(newfile.getParent()).mkdirs();
		    		FileOutputStream fo = new FileOutputStream(newfile);
		    		OutputStreamWriter out = new OutputStreamWriter(fo,"utf-8");	
					out.write(r.get(1));
					out.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
    	try {
    		
		} catch (Exception e) {
			e.printStackTrace();
		}	
	}
	
	
	
	
	
	
	
	public static ArrayList<String> readfile(File filename)
	{
		String rn="\r\n";
		String content = new String();
		String jc = new String();
		try{

		BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(filename),"Shift-JIS"));

		String s;
		while ((s = br.readLine()) != null) {
			if(s.indexOf("SetTalkEx(")>=0){
				String[] a = s.split("\"");
				if(a.length==3){
					String chat = a[1];
					
					chat = chat + "						////	"+ chat;
					jc=jc+chat+rn;
					content=content+s+rn;
					c++;
					sn=sn+chat.length();
				}else{
					System.out.println(s);
				}
			}else if(s.indexOf("return(\"")>=0){
				String[] a = s.split("\"");
				if(a.length==3){
					String spellname = a[1];
					spellname = spellname + "						////	" + spellname;
					jc=jc+spellname+rn;
					content=content+s+rn;
					c++;
					sn=sn+spellname.length();
				}else{
					System.out.println(s);
				}
			}else{
				content = content + s + rn;
			}
		}

        br.close();   
		}
		catch (Exception ex)
		{
			System.out.println("exception in reading chat content :"+filename.toString()+":"+ex.toString());
		}
		ArrayList<String> r = new ArrayList<String>();
		r.add(jc);
		r.add(content);
		return r;
	}
	
	
	public static ArrayList<String> readexpfile(File filename)
	{
		String rn="\r\n";
		String content = new String();
		String jc = new String();
		
		String translatedfilepath = filename.getPath().replace("/TK/", "/tkf2/");
		File translatedfile = new File(translatedfilepath);
		try{
			BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(filename),"Shift-JIS"));
			BufferedReader br2 = null;
			try {
				br2 = new BufferedReader(new InputStreamReader(new FileInputStream(translatedfile),"utf-8"));
			} catch (Exception e) {
				
			}
			String s;
			while ((s = br.readLine()) != null) {
				if(s.indexOf("SetTalkEx(")>=0){
					String[] a = s.split("\"");
					if(a.length==3){
						String translatedchat = br2.readLine();
						String[] e = translatedchat.split("////");
						if(e.length==2){
							String tc = e[0];
							String tcr=tc.trim();
							content=content+a[0]+"\""+tcr+"\""+a[2]+rn;
						}else{
							System.out.println("error e length:"+translatedchat);
							content=content+a[0]+"\""+translatedchat+"\""+a[2]+rn;
						}
						
					}else{
						System.out.println(s);
					}
				}else if(s.indexOf("return(\"")>=0){
					String[] a = s.split("\"");
					if(a.length==3){
						String translatedspellname = br2.readLine();
						content=content+a[0]+"\""+translatedspellname+"\""+a[2]+rn;
					}else{
						System.out.println(s);
					}
				}else{
					content = content + s + rn;
				}
			}
			if(br2!=null){
				if(br2.readLine()!=null){
					System.out.println("exception!!!different lines!!!!!"+filename.getPath());
				}
				br2.close();
			}
	        br.close(); 
	        
	        
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
		}
		ArrayList<String> r = new ArrayList<String>();
		r.add(jc);
		r.add(content);
		return r;
	}
	
	
	
	
	
	
	
	
}
