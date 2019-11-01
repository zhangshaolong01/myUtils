package com.zhang.utils.txt;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.ss.usermodel.Workbook;

import com.zhang.utils.file.IOSave;
import com.zhang.utils.offce.ExcelUtils;

public class ModifyTXT {
 
	public static void main(String[] args) { 
		System.out.println("---------执行开始-----------");
		//String path = "D:\\Workspaces\\aa";	//要遍历的路径
		//String path = "E:\\eclipse\\oxygen_2\\wooh-basic";
		//String path = "E:\\eclipse\\oxygen_2\\wooh-omp";
		//String path = "E:\\eclipse\\oxygen_2\\hecsp\\hecsp-web\\hecsp-backend\\gw-ec-set";
		//String path = "E:\\eclipse\\oxygen_2\\hecsp\\hecsp-common\\gw-ec-common";
		//String path = "E:\\eclipse\\oxygen_2\\hecsp\\hecsp-app\\app-service\\gw-ec-app-common";
		//String path = "E:\\eclipse\\oxygen_2\\hecsp\\hecsp-app\\app-service\\gw-ec-app-manager";
		//String path = "E:\\eclipse\\oxygen_2\\hecsp\\hecsp-app\\app-service\\gw-ec-app-nurse";
		//String path = "E:\\eclipse\\oxygen_2\\hecsp\\hecsp-app\\app-service\\gw-ec-app-nurse\\src\\main\\java\\com\\bjgoodwill\\nurse\\pcw\\dao\\ibatis";
		//String path = "E:\\eclipse\\oxygen_2\\hecsp\\hecsp-web\\hecsp-core";
		//String path = "E:\\eclipse\\oxygen_2\\hecsp";
		String path = "E:\\eclipse\\inst\\hecsp";
		
		String appCommon = "E:\\eclipse\\inst\\hecsp\\hecsp-app\\app-service\\gw-ec-app-common";
		String appCustomer = "E:\\eclipse\\inst\\hecsp\\hecsp-app\\app-service\\gw-ec-app-customer";
		String appFamily = "E:\\eclipse\\inst\\hecsp\\hecsp-app\\app-service\\gw-ec-app-family";
		String appManager = "E:\\eclipse\\inst\\hecsp\\hecsp-app\\app-service\\gw-ec-app-manager";
		String appNurse = "E:\\eclipse\\inst\\hecsp\\hecsp-app\\app-service\\gw-ec-app-nurse";
		String appSupplier = "E:\\eclipse\\inst\\hecsp\\hecsp-app\\app-service\\gw-ec-app-supplier";
		String appVolunteer = "E:\\eclipse\\inst\\hecsp\\hecsp-app\\app-service\\gw-ec-app-volunteer";
		String appWorker = "E:\\eclipse\\inst\\hecsp\\hecsp-app\\app-service\\gw-ec-app-worker";
		
		String hecspWeb = "E:\\eclipse\\inst\\hecsp\\hecsp-common";
		
		String assess = "E:\\eclipse\\inst\\assess";
		
		
		List<File> fileList = new ArrayList<>();
		Map<String,String> urlMap = new HashMap<>();
		/*getFileList(fileList,path,"struts.xml");
		for (File file : fileList) {
			XmlUtils.handleXML(file, urlMap);
		}*/
		//System.out.println(urlMap.toString());
		
		fileList.clear();
		//getFileList(fileList,path,"Action.java");
		getFileList(fileList,assess,"Controller.java");
		//getFileList(fileList,path,"action.xml");
		List<Map<String,String>> allList = new ArrayList<Map<String,String>>();
		for (File file : fileList) {
			String name = file.getName();
			String pathUrl = "";
			if(name.length()>0) {
				name = name.substring(0, name.indexOf("."));
				name = toLowerCaseFirstOne(name);
				if(urlMap.containsKey(name)) {
					pathUrl = urlMap.get(name);
				}
			}
			//updadeTxt(file,pathUrl);
			//System.out.println("删除="+file.getName());
			//file.delete();
			
			List<Map<String,String>> list = getInterface(file);
			allList.addAll(list);
		}
		
		try {
			Workbook wb = ExcelUtils.writeExcel(allList);
	        ByteArrayOutputStream bos = new ByteArrayOutputStream();
	        wb.write(bos);
	        bos.close();
			IOSave.ByteArrayToFile(bos.toByteArray(), "C:\\Users\\zhangshaolong\\Desktop\\新版\\app接口列表.xlsx");
		} catch (Exception e) {
			
		}
		System.out.println("---------执行结束-----------");
	}
	
	
	/**
	 * 遍历文件目录
	 * @param filelist
	 * @param path
	 */
	public static void getFileList(List<File> fileList,String path,String reg) {
        File dir = new File(path);
        if (dir.isDirectory()) {
        	File[] files = dir.listFiles(); // 该文件目录下文件全部放入数组
        	 if (files != null && files.length > 0) {
        		 for (File file : files) {
                     String fileName = file.getName();
                     if (file.isDirectory()) { // 判断是文件还是文件夹
                    	 getFileList(fileList, file.getAbsolutePath(), reg); // 获取文件绝对路径
                     } else if (fileName.endsWith(reg)) { // 判断文件名是否以reg结尾
                         fileList.add(file);
                     } else {
                         continue;
                     }
                 }
        	}
        }
    }
	
	/**
	 * 修改行
	 * @param file
	 */
	public static void updadeTxt(File file,String pathUrl){
		BufferedReader reader = null;
		try {
			//打开文件
			reader = new BufferedReader(new InputStreamReader(new FileInputStream(file),"utf-8"));
			//List<String> list = updateDetail(reader,pathUrl);
			//List<String> list = updateDetail_1(reader,pathUrl);
			//List<String> list = updateDetail_2(reader,pathUrl);
			List<String> list = updateDetail_3(reader,pathUrl);
			//writeTxt_1(file.getAbsolutePath(), list);
			
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			try {
				if(reader!=null) {
					reader.close();
				}
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
	}
	
	
	public static List<Map<String, String>> getInterface(File file){
		List<Map<String, String>> list = new ArrayList<Map<String,String>>(); 
		BufferedReader reader = null;
		try {
			//打开文件
			reader = new BufferedReader(new InputStreamReader(new FileInputStream(file),"utf-8"));
			//按行读取文件
	        String line = null;
	        //
	        String controller = null;
	        List<String> path = new ArrayList<String>();
	        List<String> method = new ArrayList<String>();
	        
			while ((line = reader.readLine()) != null) {
				String trimStr = line.trim();
	            if (trimStr.length() == 0) {
	                continue;
	            }
	            
	            if (trimStr.startsWith("public class")) {
	            	controller = trimStr.substring(trimStr.indexOf("public class ") + 13, trimStr.indexOf("extends") - 1);
	            }else {
	            	if (trimStr.contains("public") && trimStr.contains("{") && !trimStr.contains("static")) {
	            		if(trimStr.contains("public ResponseEntity<byte[]>")) {
	            			method.add(trimStr.substring(trimStr.indexOf("public ResponseEntity<byte[]>") + 30, trimStr.indexOf("(")));
	            		}else {
	            			method.add(trimStr.substring(trimStr.indexOf("public String") + 14, trimStr.indexOf("(")));
	            		}
		            }
	            }
	            
	            if (trimStr.startsWith("@PostMapping") || trimStr.startsWith("@RequestMapping")) {
	            	if(trimStr.contains("@PostMapping")) {
	            		path.add(trimStr.substring(trimStr.indexOf("@PostMapping") + 14, trimStr.indexOf(")") - 1));
	            	}else {
	            		if(trimStr.contains("@RequestMapping")) {
	            			path.add(trimStr.substring(trimStr.indexOf("@RequestMapping") + 17, trimStr.indexOf(")") - 1));
	            		}
	            	}
	            }
			}
			
			
			if(path.size() == method.size()) {
				for (int i = 0; i < path.size(); i++) {
					Map<String, String> map = new LinkedHashMap<>();
					map.put("controller", controller);
					map.put("method", method.get(i));
					map.put("path", path.get(i));
					list.add(map);
				}
			}else {
				System.err.println("对不上"+controller);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			try {
				if(reader!=null) {
					reader.close();
				}
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
		return list;
	}
	
	
	
	/**
	 * 修改行
	 * @param file
	 */
	public static void writeTxt(String path,List<String> lineList){
		BufferedWriter bw = null;
		try {
		 	path = path.replace("action", "controller").replace("Action", "Controller");
            File controllerFile = new File(path);
            if (controllerFile.exists()) {
                controllerFile.delete();
            }
            if (!controllerFile.getParentFile().exists()) {
                controllerFile.getParentFile().mkdirs();
            }
            
            bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(controllerFile), "UTF-8"));
            for (String lineStr : lineList) {
                bw.write(lineStr);
            }
            bw.flush();
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			try {
				if(bw!=null) {
					bw.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	public static void writeTxt_1(String path,List<String> lineList){
		BufferedWriter bw = null;
		try {
		 	path = path.replace("action.xml", "action.xml_");
            File controllerFile = new File(path);
            if (controllerFile.exists()) {
                controllerFile.delete();
            }
            if (!controllerFile.getParentFile().exists()) {
                controllerFile.getParentFile().mkdirs();
            }
            
            bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(controllerFile), "UTF-8"));
            for (String lineStr : lineList) {
                bw.write(lineStr);
            }
            bw.flush();
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			try {
				if(bw!=null) {
					bw.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * 替换详情
	 * @param buffer
	 * @param line
	 * @throws IOException 
	 */
	public static List updateDetail(BufferedReader reader,String pathUrl) throws IOException{
		 //内存流
        List<String> lineList = new ArrayList<>();
        String className = "";
        String listName = "";//返回数据名称
        String returnlist = "";//返回数据
        int index = 0;
        int funtionIndex = 0;//函数行数
        boolean pageFlag = false;//分页判断
        boolean NPFlag = false;//分页判断
        boolean catchFlag = false;//异常判断
        boolean returnFlag = false;//是否有返回判断
        String totalRows = null;
        String separator = System.getProperty("line.separator");
        
        //按行读取文件
        String line = null;
        while ((line = reader.readLine()) != null) {
            String trimStr = line.trim();
            if (trimStr.length() == 0) {
                continue;
            }
            //System.out.println("line: " + line);
            if (trimStr.startsWith("package")) {
                line = line.replace("action", "controller");
                System.out.println("文件包名:" + line);
                lineList.add(line + separator);
                lineList.add(separator);
            } else if (trimStr.startsWith("import")) {
                if (trimStr.contains("PageAction")) {
                    line = line.replaceAll("Action", "Controller");
                    System.out.println("更换继承类:" + line);
                }
                lineList.add(line + separator);
            } else if (trimStr.contains("PageAction")) {
                lineList.add(separator);
                lineList.add("@Controller" + separator);
                //lineList.add("@RequestMapping(value=\"/omp\")" + separator);
                line = line.replaceAll("Action", "Controller");
                lineList.add(line + separator);

                String[] trimStrs = trimStr.split("extends")[0].trim().split(" ");
                String actionName = trimStrs[trimStrs.length - 1];
                actionName = actionName.substring(0, 1).toLowerCase() + actionName.substring(1);
                className = actionName.replaceAll("Action", "");
                System.out.println("bean名称:" + actionName + "\t实体类名称:" + className);
            } else if (trimStr.startsWith("private") && line.contains("Service")) {
                lineList.add("\t@Autowired" + separator);
                lineList.add(line + separator);
                lineList.add(separator);
            } else if (trimStr.startsWith("private List")) {
            	returnlist = line.replace("private", "");
                String[] trimStrs = trimStr.split("=");
                String[] trimStrs2 = trimStrs[0].trim().split(" ");
                listName = trimStrs2[trimStrs2.length - 1];
                System.out.println("返回数据名称:" + listName);
            }else if (trimStr.startsWith("private Map")) {
            	returnlist = line.replace("private", "");
                String[] trimStrs = trimStr.split("=");
                String[] trimStrs2 = trimStrs[0].trim().split(" ");
                listName = trimStrs2[trimStrs2.length - 1];
                System.out.println("返回数据名称:" + listName);
            } else if (trimStr.startsWith("public String") && trimStr.contains("(") && !trimStr.contains(" get")) {
                String menthName = trimStr.split("\\(\\)")[0].replace("public String", "").trim();
                System.out.println("处理函数:" + menthName);
                lineList.add("\t@ResponseBody" + separator);
                //lineList.add("\t@PostMapping(\"/" + menthName + "_" + className + "\")" + separator);
                lineList.add("\t@PostMapping(\"" +pathUrl.replace("*", menthName) + "\")" + separator);
                if (trimStr.contains("throws") && trimStr.contains("{")) {
                	 line = line.substring(0, line.indexOf("throws"))+line.substring(line.indexOf("{"));
                }
                line = line.replace("()", "(String data)");
                funtionIndex = lineList.size();
                lineList.add(line + separator);
                //返回列表
                if(trimStr.contains("query") || trimStr.contains("check")) {
                	lineList.add("\t"+returnlist + separator);
                }
                
                if (trimStr.contains("{")) {
                    index++;
                }
            } else if (index > 0) {
                if (line.contains("setClass")) {
                	String reg = line.substring(line.indexOf("(")+1, line.indexOf(")"));
                    line = "\t\t\t" + reg + " " +line.replace("setClass(", "setClass(data,").trim();
                }else if (line.contains("requestDataToMap")) {
                    line = line.replace("()", "(data)");
                }else if (line.contains("requestDataToEntity")) {
                    line = line.replace("requestDataToEntity(", "requestDataToEntity(data,");
                }else if (line.contains("requestDataToList")) {
                    line = line.replace("requestDataToList(", "(data,");
                }else if (line.contains("this.data")) {
                    line = line.replace("this.data", "data");
                }
                
                if (trimStr.contains("this.getPagesize()")) {
                    line = line.replace("this.getPagesize()", "pagesize");
                }
                if (trimStr.contains("this.getPagerow()")) {
                    line = line.replace("this.getPagerow()", "pagerow");
                }
                if (trimStr.contains("this.getSort()")) {
                    line = line.replace("this.getSort()", "sort");
                }
                if (trimStr.contains("this.getSort()")) {
                    line = line.replace("this.getDir()", "dir");
                }
                if (trimStr.startsWith("Page")) {
                    pageFlag = true;
                    String linePage = lineList.get(funtionIndex);
                    linePage = linePage.replace("(String data)", "(String data,int pagesize,int pagerow,String sort,String dir)");
                    lineList.set(funtionIndex, linePage);
                    System.out.println("判断该函数为分页查询:" + linePage);
                }else if (trimStr.contains("page.getData()")) {
                    line = "\t\t\t\t" + trimStr;
                }else if (trimStr.endsWith(listName+".size();")) {
                	 NPFlag = true;
                	 continue;
                } else if (line.contains("pageInfo.getTotalRows()") || line.contains("page.getTotalRows()")) {
                    //line = "\t\t\tint " + trimStr;
                	totalRows = trimStr;
                    continue;
                } else if (line.contains("setTotalpage")) {
                    continue;
                }
                if (trimStr.contains("catch")) {
                    catchFlag = true;
                }
                if (trimStr.contains(listName)) {
                    returnFlag = true;
                }
                if (trimStr.contains("this.result") && trimStr.contains("=")) {
                	if(trimStr.contains("success") && trimStr.length() < 26) {
                		if (pageFlag) {
                			if(totalRows!=null) {
                				line = "\t\t\treturn success(" + listName + ","+totalRows+",\"" + toLowerCaseFirstOne(listName) + "\");";
                			}else {
                				line = "\t\t\treturn success(" + listName + ",pageInfo.getTotalRows(),\"" + toLowerCaseFirstOne(listName) + "\");";
                			}
                            pageFlag = false;
                        }else if (NPFlag) {
                        	line = "\t\t\treturn success(" + listName + ","+listName+".size(),\"" + toLowerCaseFirstOne(listName) + "\");";
                        	NPFlag = false;
                        	returnFlag = false;
                        } else if (catchFlag) {
                            line = "\t\t\treturn error(e);";
                            catchFlag = false;
                        } else if (returnFlag) {
                            line = "\t\t\treturn success(" + listName + ",\"" + toLowerCaseFirstOne(listName) + "\");";
                            returnFlag = false;
                        } else {
                            line = "\t\t\treturn success();";
                        }
                	}else {
                		String msg = trimStr.substring(trimStr.indexOf("=")+1, trimStr.indexOf(";"));
                		if (pageFlag) {
                			if(totalRows!=null) {
                				line = "\t\t\treturn success(" + listName + ","+totalRows+",\"" + toLowerCaseFirstOne(listName) + "\");";
                			}else {
                				line = "\t\t\treturn success(" + listName + ",pageInfo.getTotalRows(),\"" + toLowerCaseFirstOne(listName) + "\");";
                			}
                            pageFlag = false;
                        }else if (NPFlag) {
                        	line = "\t\t\treturn success(" + listName + ","+listName+".size(),\"" + toUpperCaseFirstOne(listName) + "\");";
                        	NPFlag = false;
                        } else if (catchFlag) {
                            line = "\t\t\treturn error("+msg+",e);";
                            catchFlag = false;
                        } else if (returnFlag) {
                            //line = "\t\t\treturn success(" + listName + ",\"" + toUpperCaseFirstOne(listName) + "\");";
                        	 line = "\t\t\treturn success("+msg+");";
                            returnFlag = false;
                        } else {
                            line = "\t\t\treturn success("+msg+");";
                        }
                	}
                    
                } else if (trimStr.startsWith("return")) {
                    continue;
                }
                if (!(trimStr.contains("TODO") || trimStr.contains("printStackTrace"))) {
                	lineList.add(line + separator);
                }
                if (trimStr.contains("{")) {
                    index++;
                }
                if (trimStr.contains("}")) {
                    index--;
                    if (index == 0) {
                        lineList.add(separator);
                    }
                }
            } else if (trimStr.startsWith("//") || trimStr.startsWith("/*") || trimStr.startsWith("*")) {
                   lineList.add(line + separator);
            } else {
            	   
            }
        }
        lineList.add("}");
        return lineList;
		//addDetail(buffer, line);
	}
	
	/**
	 * 首字母转小写
	 * @param reader
	 * @param pathUrl
	 * @return
	 * @throws IOException
	 */
	public static List updateDetail_1(BufferedReader reader,String pathUrl) throws IOException{
		 //内存流
       List<String> lineList = new ArrayList<>();
       String separator = System.getProperty("line.separator");
       
       //按行读取文件
       String line = null;
       while ((line = reader.readLine()) != null) {
           String trimStr = line.trim();
           if (trimStr.length() == 0) {
               continue;
           }
           if (trimStr.contains("return success(") && trimStr.contains("\")") && trimStr.contains(",")) {
        	   String str = trimStr.substring(trimStr.indexOf("\"")+1,trimStr.indexOf("\")"));
               line = line.replace(str, toLowerCaseFirstOne(str));
               System.out.println(str +" ---> "+toLowerCaseFirstOne(str));
               lineList.add(line + separator);
           }else {
        	   lineList.add(line + separator);
           }
       }
       return lineList;
	}
	
	public static List updateDetail_2(BufferedReader reader,String pathUrl) throws IOException{
		 //内存流
       List<String> lineList = new ArrayList<>();
       String separator = System.getProperty("line.separator");
       
       //按行读取文件
       String line = null;
       while ((line = reader.readLine()) != null) {
           String trimStr = line.trim();
           if (trimStr.length() == 0) {
               continue;
           }
           if (trimStr.contains("public class") && trimStr.contains("implements")) {
        	   String str = trimStr.substring(trimStr.indexOf("implements")+11,trimStr.indexOf("{")-1);
               lineList.add(line + separator + separator);
               lineList.add("\tprivate final String CLASS_NAME = \"com.bjgoodwill.nurse.pcw.dao."+str+".\";");
               lineList.add(separator);
               System.out.println("返回数据名称:" + line);
           }else if (trimStr.contains("queryForPage(") 
        		   || trimStr.contains("queryForList(")
        		   || trimStr.contains("queryForObject(")
        		   || trimStr.contains("update(")
        		   || trimStr.contains("delete(")
        		   || trimStr.contains("insert(")
        		   || trimStr.contains("queryForList(")) {
        	   lineList.add(line.replace("(\"", "(CLASS_NAME + \"") + separator);
        	   System.out.println("返回数据名称:" + line);
           }else {
        	   lineList.add(line + separator);
           }
       }
       return lineList;
	}
	
	public static List updateDetail_3(BufferedReader reader,String pathUrl) throws IOException{
		 //内存流
      List<String> lineList = new ArrayList<>();
      String separator = System.getProperty("line.separator");
      
      //按行读取文件
      String line = null;
      while ((line = reader.readLine()) != null) {
       	   lineList.add(line + separator);
      }
      return lineList;
	}
	
	/**添加行
	 * @param buffer
	 * @param line
	 */
	public static void addDetail(StringBuffer buffer,String line){
		buffer.append(line);
		buffer.append(System.getProperty("line.separator"));
	}
	
	/**
	 * 首字母转小写
	 * @param s
	 * @return
	 */
	public static String toLowerCaseFirstOne(String s){
		if(s!=null && s.length()>0) {
			if(Character.isLowerCase(s.charAt(0)))
			    return s;
			  else
			    return (new StringBuilder()).append(Character.toLowerCase(s.charAt(0))).append(s.substring(1)).toString();
		}
		return s;
	}


	/**
	 * 首字母转大写
	 * @param s
	 * @return
	 */
	public static String toUpperCaseFirstOne(String s){
		if(s!=null && s.length()>0) {
			if(Character.isUpperCase(s.charAt(0)))
				return s;
			else
				return (new StringBuilder()).append(Character.toUpperCase(s.charAt(0))).append(s.substring(1)).toString();
		}
		return s;
	}
}
